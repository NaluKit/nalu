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

package com.github.nalukit.nalu.processor.popUpControllerCreator.eventhandler.eventHandlerOnAPopUpControllerOk03.popUp;

import com.github.nalukit.nalu.client.component.AbstractPopUpComponentController;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import com.github.nalukit.nalu.client.component.annotation.PopUpController;
import com.github.nalukit.nalu.client.event.annotation.EventHandler;
import com.github.nalukit.nalu.processor.common.MockContext;
import com.github.nalukit.nalu.processor.common.event.MockEvent01;

import java.util.Objects;

@PopUpController(name = "PopUpController03",
                 componentInterface = IPopUpComponent03.class,
                 component = PopUpComponent03.class)
public class PopUpController03
    extends AbstractPopUpComponentController<MockContext, IPopUpComponent03>
    implements IPopUpComponent03.Controller {

  public PopUpController03() {
  }

  @Override
  public void show() {
  }

  @EventHandler
  public void onMockEvent01(MockEvent01 event01) {
  }

}
