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

package com.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithTwoParameter01;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.annotation.AcceptParameter;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import com.github.nalukit.nalu.processor.common.MockContext;

@Controller(route = "/mockShell/route01/:parameter01/:parameter02",
            selector = "selector01",
            component = Component04.class,
            componentInterface = IComponent04.class)
public class ControllerC04
    extends AbstractComponentController<MockContext, IComponent04, String>
    implements IComponent04.Controller {

  public ControllerC04() {
  }

  @AcceptParameter("parameter01")
  public void setParameter01(String parameter) {
  }

  @AcceptParameter("parameter02")
  public void setParameter02(String parameter) {
  }
}
