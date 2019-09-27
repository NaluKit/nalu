/*
 * Copyright (c) 2018 - 2019 - Frank Hossfeld
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

import com.github.nalukit.nalu.client.event.RouterStateEvent;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import com.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin;
import org.gwtproject.event.shared.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@NaluInternalUse
public class BlockControllerFactory {

  /* instance of the popup controller factory */
  private static BlockControllerFactory instance;

  /* map of components (key: name of class, Value: ControllerCreator */
  private Map<String, IsBlockControllerCreator> creatorStore;

  /* map of components (key: name of class, Value: controller instance */
  private Map<String, BlockControllerInstance> blockControllerStore;

  /* Nalu event bus to catch the RouteState-Event */
  private EventBus eventBus;

  /* Nalu plugin */
  private IsNaluProcessorPlugin plugin;

  private BlockControllerFactory() {
    this.creatorStore = new HashMap<>();
    this.blockControllerStore = new HashMap<>();
  }

  public static BlockControllerFactory get() {
    if (instance == null) {
      instance = new BlockControllerFactory();
    }
    return instance;
  }

  public void registerBlockController(String blockName,
                                      IsBlockControllerCreator creator) {
    this.creatorStore.put(blockName,
                          creator);
  }

  public void register(IsNaluProcessorPlugin plugin,
                       EventBus eventBus) {
    this.plugin = plugin;
    this.eventBus = eventBus;
    // we will listen to the RouteSteEvent to show and hide blocks
    if (!Objects.isNull(this.eventBus)) {
      this.eventBus.addHandler(RouterStateEvent.TYPE,
                               e -> onHandleRouting(e));
    }
  }

  private void onHandleRouting(RouterStateEvent e) {
    // TODO implementieren
    StringBuilder sb = new StringBuilder();
    sb.append("BlockControllerFactory: handle RouterStateEvent for route >>")
      .append(e.getRoute())
      .append("<<");
    this.creatorStore.keySet()
                     .forEach(k -> {
                       BlockControllerInstance instance;
                       if (Objects.isNull(blockControllerStore.get(k))) {
                         IsBlockControllerCreator creator = this.creatorStore.get(k);
                         if (!Objects.isNull(creator)) {
                           instance = creator.create();
                           this.blockControllerStore.put(k,
                                                         instance);
                         } else {
                           ClientLogger.get()
                                       .logDetailed("BlockControllerFactory: BlockController for name >>" + k + "<< not found",
                                                    0);
                           return;
                         }
                         ClientLogger.get()
                                     .logSimple("block controller >>" + instance.getBlockControllerClassName() + "<< --> call onBeforeShow",
                                                3);
                         instance.getController()
                                 .onBeforeShow();
                         ClientLogger.get()
                                     .logSimple("block controller >>" + instance.getBlockControllerClassName() + "<< --> onBeforeShow called",
                                                3);
                         ClientLogger.get()
                                     .logSimple("block controller >>" + instance.getBlockControllerClassName() + "<< --> call show",
                                                3);
                         instance.getController()
                                 .show();
                         ClientLogger.get()
                                     .logSimple("controller >>" + instance.getBlockControllerClassName() + "<< --> show called",
                                                3);
                         ClientLogger.get()
                                     .logSimple("block controller >>" + instance.getBlockControllerClassName() + "<< --> append to root",
                                                3);
//                         this.plu
                         instance.getController()
                                 .show();
                         ClientLogger.get()
                                     .logSimple("controller >>" + instance.getBlockControllerClassName() + "<< --> show called",
                                                3);

                       }
                       instance = this.blockControllerStore.get(k);
                       if (!Objects.isNull(instance)) {
                         instance.getController()
                                 .show();
                       }
                     });
  }

}
