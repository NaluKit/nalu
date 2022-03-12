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

package com.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithTwoParameter03;

import com.github.nalukit.nalu.client.component.AbstractComponent;

public class Component06
    extends AbstractComponent<IComponent06.Controller, String>
    implements IComponent06 {

  public Component06() {
  }

  @Override
  public void render() {
    initElement("Component06");
  }
}
