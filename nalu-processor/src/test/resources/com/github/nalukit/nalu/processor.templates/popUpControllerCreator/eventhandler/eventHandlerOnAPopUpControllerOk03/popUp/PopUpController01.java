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

package com.github.nalukit.nalu.processor.popUpControllerCreator.eventhandler.eventHandlerOnAPopUpControllerOk03.popUp;

import com.github.nalukit.nalu.client.component.AbstractPopUpComponentController;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import com.github.nalukit.nalu.client.component.annotation.PopUpController;
import com.github.nalukit.nalu.client.event.annotation.EventHandler;
import com.github.nalukit.nalu.processor.common.MockContext;
import com.github.nalukit.nalu.processor.common.event.MockEvent01;
import com.github.nalukit.nalu.processor.common.event.MockEvent02;

import java.util.Objects;

@PopUpController(name = "PopUpController01",
                 componentInterface = IPopUpComponent01.class,
                 component = PopUpComponent01.class)
public class PopUpController01
    extends AbstractPopUpComponentController<MockContext, IPopUpComponent01>
    implements IPopUpComponent01.Controller {

  public PopUpController01() {
  }

  @Override
  public void show() {
  }

  @EventHandler
  public void onMockEvent01(MockEvent01 event01) {
  }

  @EventHandler
  public void onMockEvent02(MockEvent02 event) {
  }

}
