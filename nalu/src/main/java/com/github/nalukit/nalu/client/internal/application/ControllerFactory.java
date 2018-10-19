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

package com.github.nalukit.nalu.client.internal.application;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

import java.util.HashMap;
import java.util.Map;

@NaluInternalUse
public class ControllerFactory {

  /* instance of the controller factory */
  private static ControllerFactory instance;

  /* map of components (key: name of class, Value: ControllerCreator */
  private Map<String, ControllerCreator> controllerFactory;

  /* map of stored components (key: name of class, Value: instance of controller */
  private Map<String, AbstractComponentController<?, ?, ?>> controllerStore;

  private ControllerFactory() {
    this.controllerFactory = new HashMap<>();
    this.controllerStore = new HashMap<>();
  }

  public static ControllerFactory get() {
    if (instance == null) {
      instance = new ControllerFactory();
    }
    return instance;
  }

  public void registerController(String controller,
                                 ControllerCreator creator) {
    this.controllerFactory.put(controller,
                               creator);
  }

  public ControllerInstance controller(String controller,
                                       String... parms)
      throws RoutingInterceptionException {
    if (this.controllerFactory.containsKey(controller)) {
      return this.controllerFactory.get(controller)
                                   .create(parms);
    }
    return null;
  }

  public AbstractComponentController<?, ?, ?> getControllerFormStore(String controllerClassName) {
    return this.controllerStore.get(this.classFormatter(controllerClassName));
  }

  public <C extends AbstractComponentController<?, ?, ?>> void storeInCache(C controller) {
    String key = this.classFormatter(controller.getClass()
                                               .getCanonicalName());
    this.controllerStore.put(key,
                             controller);
  }

  public <C extends AbstractComponentController<?, ?, ?>> void removeFromCache(C controller) {
    String key = this.classFormatter(controller.getClass()
                                               .getCanonicalName());
    this.controllerStore.remove(key);
  }

  public void clearControllerCache() {
    this.controllerStore.clear();
  }

  private String classFormatter(String route) {
    return route.replace(".",
                         "_");
  }

}
