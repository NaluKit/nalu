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
import com.github.nalukit.nalu.client.event.RouterStateEvent.RouterState;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import com.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin;
import org.gwtproject.event.shared.EventBus;

import java.util.*;

@NaluInternalUse
public class BlockControllerFactory {

  /* instance of the popup controller factory */
  private static BlockControllerFactory               instance;
  /* map of components (key: name of class, Value: controller instance */
  private        Map<String, BlockControllerInstance> blockControllerInstanceStore;
  /* Nalu event bus to catch the RouteState-Event */
  private        EventBus                             eventBus;
  /* list of visibles blocks (using block name) */
  private        List<String>                         visiblesBlocks;

  /* Nalu plugin */
  private IsNaluProcessorPlugin plugin;

  private BlockControllerFactory() {
    this.blockControllerInstanceStore = new HashMap<>();
    this.visiblesBlocks = new ArrayList<>();
  }

  public static BlockControllerFactory get() {
    if (instance == null) {
      instance = new BlockControllerFactory();
    }
    return instance;
  }

  public void registerBlockController(String blockName,
                                      IsBlockControllerCreator creator) {
    BlockControllerInstance blockControllerInstance = creator.create();
    ClientLogger.get()
                .logSimple("block controller >>" + blockControllerInstance.getBlockControllerClassName() + "<< --> append to root",
                           3);
    blockControllerInstance.getController()
                           .append();
    ClientLogger.get()
                .logSimple("controller >>" + blockControllerInstance.getBlockControllerClassName() + "<< --> appended to root",
                           3);
    blockControllerInstance.getController()
                           .hide();
    this.blockControllerInstanceStore.put(blockName,
                                          blockControllerInstance);
  }

  public void register(IsNaluProcessorPlugin plugin,
                       EventBus eventBus) {
    this.plugin = plugin;
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
    // TODO implementieren
    StringBuilder sb = new StringBuilder();
    sb.append("BlockControllerFactory: handle RouterStateEvent for route >>")
      .append(e.getRoute())
      .append("<<");
    this.blockControllerInstanceStore.keySet()
                                     .forEach(i -> {
                                       BlockControllerInstance blockControllerInstance = this.blockControllerInstanceStore.get(i);
                                       if (blockControllerInstance.showBlock(e.getRoute(),
                                                                             e.getParams())) {
                                         if (!this.visiblesBlocks.contains(blockControllerInstance.getController()
                                                                                                  .getName())) {
                                           ClientLogger.get()
                                                       .logSimple("block controller >>" + blockControllerInstance.getBlockControllerClassName() + "<< --> call onBeforeShow",
                                                                  3);
                                           blockControllerInstance.getController()
                                                                  .onBeforeShow();
                                           ClientLogger.get()
                                                       .logSimple("block controller >>" + blockControllerInstance.getBlockControllerClassName() + "<< --> onBeforeShow called",
                                                                  3);
                                           ClientLogger.get()
                                                       .logSimple("block controller >>" + blockControllerInstance.getBlockControllerClassName() + "<< --> call show",
                                                                  3);
                                           blockControllerInstance.getController()
                                                                  .show();
                                           ClientLogger.get()
                                                       .logSimple("controller >>" + blockControllerInstance.getBlockControllerClassName() + "<< --> show called",
                                                                  3);
                                           visiblesBlocks.add(blockControllerInstance.getController()
                                                                                     .getName());
                                         }
                                       } else {
                                         if (this.visiblesBlocks.contains(blockControllerInstance.getController()
                                                                                                 .getName())) {
                                           ClientLogger.get()
                                                       .logSimple("block controller >>" + blockControllerInstance.getBlockControllerClassName() + "<< --> call onBeforeHide",
                                                                  3);
                                           blockControllerInstance.getController()
                                                                  .onBeforeHide();
                                           ClientLogger.get()
                                                       .logSimple("block controller >>" + blockControllerInstance.getBlockControllerClassName() + "<< --> onBeforeHide called",
                                                                  3);
                                           ClientLogger.get()
                                                       .logSimple("block controller >>" + blockControllerInstance.getBlockControllerClassName() + "<< --> call hide",
                                                                  3);
                                           blockControllerInstance.getController()
                                                                  .hide();
                                           ClientLogger.get()
                                                       .logSimple("controller >>" + blockControllerInstance.getBlockControllerClassName() + "<< --> hide called",
                                                                  3);
                                           visiblesBlocks.remove(blockControllerInstance.getController()
                                                                                        .getName());
                                         }
                                       }
                                     });
  }

}
