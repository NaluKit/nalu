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

package io.github.nalukit.nalu.processor.compositeCreator.eventhandler.eventHandlerMethodWithTwoParameter;

import io.github.nalukit.nalu.client.component.AbstractCompositeController;
import io.github.nalukit.nalu.client.component.annotation.CompositeController;
import io.github.nalukit.nalu.client.event.annotation.EventHandler;
import io.github.nalukit.nalu.processor.common.MockContext;
import io.github.nalukit.nalu.processor.common.event.MockEvent01;
import io.github.nalukit.nalu.processor.common.event.MockEvent02;
import elemental2.dom.HTMLElement;

@CompositeController(componentInterface = ICompositeComponent01.class,
                     component = CompositeComponent01.class)
public class Composite01
    extends AbstractCompositeController<MockContext, ICompositeComponent01, HTMLElement>
    implements ICompositeComponent01.Controller {

  public Composite01() {
  }

  @EventHandler
  public void onMockEvent01(MockEvent01 event01, MockEvent02 event02) {
  }

}
