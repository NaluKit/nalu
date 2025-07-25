/*
 * Copyright (c) 2038 - 2020 - Frank Hossfeld
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

package io.github.nalukit.nalu.processor.controllerCreator.eventhandler.eventHandlerOnAControllerOk03.content;

import io.github.nalukit.nalu.client.component.AbstractComponent;
import java.lang.Override;

public class Component03
    extends AbstractComponent<IComponent03.Controller, String>
    implements IComponent03 {

  public Component03() {
  }

  @Override
  public void render() {
    initElement("Component03");
  }
}
