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

import com.github.nalukit.nalu.client.component.AbstractBlockComponentController;
import com.github.nalukit.nalu.client.component.AbstractPopUpComponentController;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

@NaluInternalUse
public class BlockControllerInstance {

  /* controller's class name */
  private String blockControllerClassName;

  /* controller */
  private AbstractBlockComponentController<?, ?, ?> controller;

  public BlockControllerInstance() {
  }

  public String getBlockControllerClassName() {
    return blockControllerClassName;
  }

  public void setBlockControllerClassName(String blockControllerClassName) {
    this.blockControllerClassName = blockControllerClassName;
  }

  public AbstractBlockComponentController<?, ?, ?> getController() {
    return controller;
  }

  public void setController(AbstractBlockComponentController<?, ?, ?> controller) {
    this.controller = controller;
  }

}
