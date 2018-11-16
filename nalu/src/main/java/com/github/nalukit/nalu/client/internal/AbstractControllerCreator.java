/*
 * Copyright (c) 2018 - Frank Hossfeld
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

package com.github.nalukit.nalu.client.internal;

import com.github.nalukit.nalu.client.Router;
import com.github.nalukit.nalu.client.application.IsContext;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import com.github.nalukit.nalu.client.internal.application.ControllerInstance;
import org.gwtproject.event.shared.SimpleEventBus;

@NaluInternalUse
public abstract class AbstractControllerCreator<C extends IsContext> {

  protected StringBuilder sb;

  protected Router router;

  protected C context;

  protected SimpleEventBus eventBus;

  public AbstractControllerCreator(Router router,
                                   C context,
                                   SimpleEventBus eventBus) {
    super();
    this.router = router;
    this.context = context;
    this.eventBus = eventBus;
  }

  public abstract ControllerInstance create(String... parms);






//  public void create(String... parms) {
//    sb01.append("controller >>com.github.nalukit.example.nalu.loginapplication.client.ui.content.list.ListController<< --> will be created");
//    ClientLogger.get().logSimple(sb01.toString(), 3);
//    ListController controller = new ListController();
//    controllerInstance.setController(controller);
//    controllerInstance.setChached(false);
//    controller.setContext(context);
//    controller.setEventBus(eventBus);
//    controller.setRouter(router);
//    controller.setRestored(false);
//    sb01 = new StringBuilder();
//    sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> created and data injected");
//    ClientLogger.get().logDetailed(sb01.toString(), 4);
//    IListComponent component = new ListComponent();
//    sb01 = new StringBuilder();
//    sb01.append("component >>com.github.nalukit.example.nalu.loginapplication.client.ui.content.list.ListComponent<< --> created using new");
//    ClientLogger.get().logDetailed(sb01.toString(), 4);
//    component.setController(controller);
//    sb01 = new StringBuilder();
//    sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> created and controller instance injected");
//    ClientLogger.get().logDetailed(sb01.toString(), 4);
//    controller.setComponent(component);
//    sb01 = new StringBuilder();
//    sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> instance of >>").append(component.getClass().getCanonicalName()).append("<< injected");
//    ClientLogger.get().logDetailed(sb01.toString(), 4);
//    component.render();
//    sb01 = new StringBuilder();
//    sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> rendered");
//    ClientLogger.get().logDetailed(sb01.toString(), 4);
//    component.bind();
//    sb01 = new StringBuilder();
//    sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> bound");
//    ClientLogger.get().logDetailed(sb01.toString(), 4);
//    ClientLogger.get().logSimple("controller >>com.github.nalukit.example.nalu.loginapplication.client.ui.content.list.ListComponent<< created for route >>/applicationShell/person/list<<", 3);
//    if (parms != null) {
//      if (parms.length >= 1) {
//        sb01 = new StringBuilder();
//        sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> using method >>setName<< to set value >>").append(parms[0]).append("<<");
//        ClientLogger.get().logDetailed(sb01.toString(), 4);
//        controller.setName(parms[0]);
//      }
//      if (parms.length >= 2) {
//        sb01 = new StringBuilder();
//        sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> using method >>setCity<< to set value >>").append(parms[1]).append("<<");
//        ClientLogger.get().logDetailed(sb01.toString(), 4);
//        controller.setCity(parms[1]);
//      }
//    }
//  } else {
//    sb01.append("controller >>").append(storedController.getClass().getCanonicalName()).append("<< --> found in cache -> REUSE!");
//    ClientLogger.get().logDetailed(sb01.toString(), 4);
//    controllerInstance.setController(storedController);
//    controllerInstance.setChached(true);
//  }
}
