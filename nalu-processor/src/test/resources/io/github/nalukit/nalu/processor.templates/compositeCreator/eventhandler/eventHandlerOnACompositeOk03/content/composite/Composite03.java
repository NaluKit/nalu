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

package io.github.nalukit.nalu.processor.compositeCreator.eventhandler.eventHandlerOnACompositeOk03.content.composite;

import io.github.nalukit.nalu.client.component.AbstractCompositeController;
import io.github.nalukit.nalu.client.component.annotation.CompositeController;
import io.github.nalukit.nalu.client.event.annotation.EventHandler;
import io.github.nalukit.nalu.processor.common.MockContext;
import elemental2.dom.HTMLElement;
import io.github.nalukit.nalu.processor.common.event.MockEvent01;
import io.github.nalukit.nalu.processor.common.event.MockEvent02;

@CompositeController(componentInterface = ICompositeComponent03.class,
                     component = CompositeComponent03.class)
public class Composite03
    extends AbstractCompositeController<MockContext, ICompositeComponent03, HTMLElement>
    implements ICompositeComponent03.Controller {

  public Composite03() {
  }

  @EventHandler
  public void onMockEvent01(MockEvent01 event) {
  }

  @EventHandler
  public void onMockEvent02(MockEvent02 event) {
  }

}
