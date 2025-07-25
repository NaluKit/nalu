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

package io.github.nalukit.nalu.client.internal.application;

import io.github.nalukit.nalu.client.component.AbstractComponentController;
import io.github.nalukit.nalu.client.internal.AbstractControllerCreator;
import io.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

@NaluInternalUse
public class ControllerInstance {

  /* controller's class name */
  private String                               controllerClassName;
  /* controller */
  private AbstractComponentController<?, ?, ?> controller;
  /* flag, that indicates weather the controller is reused or not */
  private boolean                              cached;
  /* controller creator instance - needed to call setParameters b handling mode REUSE */
  private AbstractControllerCreator<?>         controllerCreator;

  public ControllerInstance() {
  }

  public String getControllerClassName() {
    return controllerClassName;
  }

  public void setControllerClassName(String controllerClassName) {
    this.controllerClassName = controllerClassName;
  }

  public AbstractComponentController<?, ?, ?> getController() {
    return controller;
  }

  public void setController(AbstractComponentController<?, ?, ?> controller) {
    this.controller = controller;
  }

  public boolean isCached() {
    return cached;
  }

  public void setCached(boolean cached) {
    this.cached = cached;
  }

  public AbstractControllerCreator<?> getControllerCreator() {
    return controllerCreator;
  }

  public void setControllerCreator(AbstractControllerCreator<?> controllerCreator) {
    this.controllerCreator = controllerCreator;
  }

}
