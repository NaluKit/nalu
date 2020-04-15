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

import com.github.nalukit.nalu.client.component.event.ShowPopUpEvent;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import org.gwtproject.event.shared.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@NaluInternalUse
public class PopUpControllerFactory {
  
  /* instance of the popup controller factory */
  private static PopUpControllerFactory instance;
  
  /* map of components (key: name of class, Value: ControllerCreator */
  private Map<String, IsPopUpControllerCreator> creatorStore;
  
  /* map of components (key: name of class, Value: controller instance */
  private Map<String, PopUpControllerInstance> popUpControllerStore;
  
  /* Nalu event bus to catch the ShowPopUpEvents */
  private EventBus eventBus;
  
  private PopUpControllerFactory() {
    this.creatorStore         = new HashMap<>();
    this.popUpControllerStore = new HashMap<>();
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
  
  public void register(EventBus eventBus) {
    this.eventBus = eventBus;
    if (!Objects.isNull(this.eventBus)) {
      this.eventBus.addHandler(ShowPopUpEvent.TYPE,
                               e -> onShowPopUp(e));
    }
  }
  
  private void onShowPopUp(ShowPopUpEvent e) {
    StringBuilder sb = new StringBuilder();
    sb.append("PopUpControllerFactory: handle PopUpEvent for popup >>")
      .append(e.getName())
      .append("<<");
    if (!Objects.isNull(e.getDataStore())) {
      if (!e.getDataStore()
            .isEmpty()) {
        sb.append(" using: ");
        e.getDataStore()
         .keySet()
         .forEach(k -> sb.append(">>")
                         .append(k)
                         .append(": ")
                         .append(e.getDataStore()
                                  .get(k))
                         .append("<<   "));
        ClientLogger.get()
                    .logDetailed(sb.toString(),
                                 2);
      }
    }
    PopUpControllerInstance popUpComponentController = this.popUpControllerStore.get(e.getName());
    if (Objects.isNull(popUpComponentController)) {
      PopUpControllerInstance instance = this.popUpControllerStore.get(e.getName());
      if (Objects.isNull(instance)) {
        IsPopUpControllerCreator creator = this.creatorStore.get(e.getName());
        if (Objects.isNull(creator)) {
          ClientLogger.get()
                      .logDetailed("PopUpControllerFactory: PopUpController for name >>" + e.getName() + "<< not found",
                                   0);
          return;
        }
        instance = creator.create();
        this.popUpControllerStore.put(e.getName(),
                                      instance);
        popUpComponentController = instance;
      }
    }
    ClientLogger.get()
                .logSimple("controller >>" + popUpComponentController.getPopUpControllerClassName() + "<< --> initializing",
                           3);
    popUpComponentController.getController()
                            .setDataStore(e.getDataStore());
    popUpComponentController.getController()
                            .setCommandStore(e.getCommandStore());
    ClientLogger.get()
                .logSimple("controller >>" + popUpComponentController.getPopUpControllerClassName() + "<< --> initialized",
                           3);
    ClientLogger.get()
                .logSimple("controller >>" + popUpComponentController.getPopUpControllerClassName() + "<< --> call onBeforeShow",
                           3);
    popUpComponentController.getController()
                            .onBeforeShow();
    ClientLogger.get()
                .logSimple("controller >>" + popUpComponentController.getPopUpControllerClassName() + "<< --> onBeforeShow called",
                           3);
    ClientLogger.get()
                .logSimple("controller >>" + popUpComponentController.getPopUpControllerClassName() + "<< --> call show",
                           3);
    popUpComponentController.getController()
                            .show();
    ClientLogger.get()
                .logSimple("controller >>" + popUpComponentController.getPopUpControllerClassName() + "<< --> show called",
                           3);
  }
  
}
