/*
 * Copyright (c) 2018 Frank Hossfeld
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

import com.github.nalukit.nalu.client.component.event.HideBlockComponentEvent;
import com.github.nalukit.nalu.client.component.event.ShowBlockComponentEvent;
import com.github.nalukit.nalu.client.event.RouterStateEvent;
import com.github.nalukit.nalu.client.event.RouterStateEvent.RouterState;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import org.gwtproject.event.shared.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@NaluInternalUse
public class BlockControllerFactory {

  /* instance of the popup controller factory */
  public static  BlockControllerFactory               INSTANCE = new BlockControllerFactory();
  /* map of components (key: name of class, Value: controller instance */
  private final  Map<String, BlockControllerInstance> blockControllerInstanceStore;
  /* list of visibles blocks (using block name) */
  private final  List<String>                         visiblesBlocks;
  /* Nalu event bus to catch the RouteState-Event */
  private        EventBus                             eventBus;

  private BlockControllerFactory() {
    this.blockControllerInstanceStore = new HashMap<>();
    this.visiblesBlocks               = new ArrayList<>();
  }

  public void registerBlockController(String blockName,
                                      IsBlockControllerCreator creator) {
    BlockControllerInstance blockControllerInstance = creator.create();
    blockControllerInstance.getController()
                           .append();
    this.eventBus.fireEvent(HideBlockComponentEvent.hide(blockControllerInstance.getController()
                                                                                .getName()));
    this.blockControllerInstanceStore.put(blockName,
                                          blockControllerInstance);
  }

  public void register(EventBus eventBus) {
    this.eventBus = eventBus;
    // we will listen to the RouteSteEvent to show and hide blocks
    if (!Objects.isNull(this.eventBus)) {
      this.eventBus.addHandler(RouterStateEvent.TYPE,
                               this::onHandleRouting);
    }
  }

  private void onHandleRouting(RouterStateEvent e) {
    if (RouterState.ROUTING_DONE != e.getState()) {
      return;
    }
    this.blockControllerInstanceStore.keySet()
                                     .forEach(i -> {
                                       BlockControllerInstance blockControllerInstance = this.blockControllerInstanceStore.get(i);
                                       if (blockControllerInstance.showBlock(e.getRoute(),
                                                                             e.getParams())) {
                                         if (!this.visiblesBlocks.contains(blockControllerInstance.getController()
                                                                                                  .getName())) {
                                           this.eventBus.fireEvent(ShowBlockComponentEvent.show(blockControllerInstance.getController()
                                                                                                                       .getName()));
                                           visiblesBlocks.add(blockControllerInstance.getController()
                                                                                     .getName());
                                         }
                                       } else {
                                         if (this.visiblesBlocks.contains(blockControllerInstance.getController()
                                                                                                 .getName())) {
                                           this.eventBus.fireEvent(HideBlockComponentEvent.hide(blockControllerInstance.getController()
                                                                                                                       .getName()));
                                           visiblesBlocks.remove(blockControllerInstance.getController()
                                                                                        .getName());
                                         }
                                       }
                                     });
  }

}
