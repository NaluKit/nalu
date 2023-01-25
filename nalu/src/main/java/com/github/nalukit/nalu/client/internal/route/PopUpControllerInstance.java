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

package com.github.nalukit.nalu.client.internal.route;

import com.github.nalukit.nalu.client.component.AbstractPopUpComponentController;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

@NaluInternalUse
public class PopUpControllerInstance {

  /* controller's class name */
  private String popUpControllerClassName;

  private boolean alwaysRenderComponent;

  /* controller */
  private AbstractPopUpComponentController<?, ?> controller;

  public PopUpControllerInstance() {
  }

  public String getPopUpControllerClassName() {
    return popUpControllerClassName;
  }

  public void setPopUpControllerClassName(String popUpControllerClassName) {
    this.popUpControllerClassName = popUpControllerClassName;
  }

  public AbstractPopUpComponentController<?, ?> getController() {
    return controller;
  }

  public void setController(AbstractPopUpComponentController<?, ?> controller) {
    this.controller = controller;
  }

  public boolean isAlwaysRenderComponent() {
    return alwaysRenderComponent;
  }

  public void setAlwaysRenderComponent(boolean alwaysRenderComponent) {
    this.alwaysRenderComponent = alwaysRenderComponent;
  }
}
