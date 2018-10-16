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
  private Map<String, ControllerCreator> coontrollerFactory;

  private ControllerFactory() {
    this.coontrollerFactory = new HashMap<>();
  }

  public static ControllerFactory get() {
    if (instance == null) {
      instance = new ControllerFactory();
    }
    return instance;
  }

  public void registerController(String controller,
                                 ControllerCreator creator) {
    this.coontrollerFactory.put(controller,
                                creator);
  }

  public AbstractComponentController<?, ?, ?> controller(String controller,
                                                         String... parms)
      throws RoutingInterceptionException {
    if (this.coontrollerFactory.containsKey(controller)) {
      return this.coontrollerFactory.get(controller)
                                    .create(parms);
    }
    return null;
  }
}
