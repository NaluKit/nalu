/*
 * Copyright (c) 2018 - 2020 - Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 */

package com.github.nalukit.nalu.client.internal.application;

import com.github.nalukit.nalu.client.application.event.LogEvent;
import com.github.nalukit.nalu.client.component.AbstractPopUpFilter;
import com.github.nalukit.nalu.client.component.event.ShowPopUpEvent;
import com.github.nalukit.nalu.client.filter.IsPopUpFilter;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import org.gwtproject.event.shared.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@NaluInternalUse
public class PopUpControllerFactory {

  /* instance of the popup controller factory */
  private static PopUpControllerFactory                instance;
  /* map of components (key: name of class, Value: ControllerCreator */
  private final  Map<String, IsPopUpControllerCreator> creatorStore;
  /* map of components (key: name of class, Value: controller instance */
  private final  Map<String, PopUpControllerInstance>  popUpControllerStore;
  /* map of filters (key: name of class, Value: filter instance */
  private final  Map<String, AbstractPopUpFilter<?>>   popUpFilterStore;
  /* Nalu event bus to catch the ShowPopUpEvents */
  private        EventBus                              eventBus;

  private PopUpControllerFactory() {
    this.creatorStore         = new HashMap<>();
    this.popUpControllerStore = new HashMap<>();
    this.popUpFilterStore     = new HashMap<>();
  }

  public static PopUpControllerFactory get() {
    if (instance == null) {
      instance = new PopUpControllerFactory();
    }
    return instance;
  }

  public void registerPopUpController(String popUpName,
                                      IsPopUpControllerCreator creator) {
    this.creatorStore.put(popUpName,
                          creator);
  }

  public void registerPopUpFilter(String popUpName,
                                  AbstractPopUpFilter<?> filter) {
    this.popUpFilterStore.put(popUpName,
                              filter);
  }

  public void register(EventBus eventBus) {
    this.eventBus = eventBus;
    if (!Objects.isNull(this.eventBus)) {
      this.eventBus.addHandler(ShowPopUpEvent.TYPE,
                               this::onShowPopUp);
    }
  }

  private void onShowPopUp(ShowPopUpEvent event) {
    List<String> cancelHandelerKeys = new ArrayList<>();
    for (String popUpFilterKey : this.popUpFilterStore.keySet()) {
      if (!this.popUpFilterStore.get(popUpFilterKey)
                                .filter(event)) {
        cancelHandelerKeys.add(popUpFilterKey);
      }
    }
    if (cancelHandelerKeys.size() > 0) {
      for (String key : cancelHandelerKeys) {
        IsPopUpFilter.CancelHandler handler = this.popUpFilterStore.get(key)
                                                                   .getCancelHandler();
        if (handler != null) {
          this.popUpFilterStore.get(key)
                               .getCancelHandler()
                               .onCancel();
        }
      }
      return;
    }

    if (!PopUpConditionFactory.get()
                              .showPopUp(event)) {
      return;
    }

    IsPopUpControllerCreator creator = this.creatorStore.get(event.getName());
    if (Objects.isNull(creator)) {
      LogEvent.create()
              .sdmOnly(false)
              .addMessage("PopUpControllerFactory: PopUpController for name >>" + event.getName() + "<< not found");
      return;
    }

    PopUpControllerInstance popUpComponentController = this.popUpControllerStore.get(event.getName());

    if (Objects.isNull(popUpComponentController)) {
      PopUpControllerInstance instance = this.popUpControllerStore.get(event.getName());
      if (Objects.isNull(instance)) {
        instance = creator.create();
        this.popUpControllerStore.put(event.getName(),
                                      instance);
        popUpComponentController = instance;
      }
    }

    popUpComponentController.getController()
                            .setDataStore(event.getDataStore());
    popUpComponentController.getController()
                            .setCommandStore(event.getCommandStore());
    PopUpControllerInstance finalPopUpComponentController = popUpComponentController;

    if (creator != null && !creator.isInitialShow()) {
      if (finalPopUpComponentController.isAlwaysRenderComponent()) {
        finalPopUpComponentController.getController()
                                     .getComponent()
                                     .removeHandlers();
        finalPopUpComponentController.getController()
                                     .getComponent()
                                     .render();
        finalPopUpComponentController.getController()
                                     .getComponent()
                                     .bind();
      }
      finalPopUpComponentController.getController()
                                   .onBeforeShow(() -> finalPopUpComponentController.getController()
                                                                                    .show());
    } else {
      IsPopUpControllerCreator finalCreator = creator;
      creator.initialShowDone();
      finalPopUpComponentController.getController()
                                   .bind(() -> {
                                     if (Objects.isNull(finalCreator)) {
                                       LogEvent.create()
                                               .sdmOnly(false)
                                               .addMessage("PopUpControllerFactory: PopUpController for name >>" +
                                                           event.getName() +
                                                           "<< not found");
                                       return;
                                     }
                                     finalCreator.onFinishCreating(finalPopUpComponentController.getController());
                                     finalPopUpComponentController.getController()
                                                                  .onBeforeShow(() -> {
                                                                    finalCreator.initialShowDone();
                                                                    finalPopUpComponentController.getController()
                                                                                                 .show();
                                                                  });
                                   });
    }
  }

}
