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

package com.github.nalukit.nalu.processor.common.ui.controllerWithComposite01;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.annotation.AcceptParameter;
import com.github.nalukit.nalu.client.component.annotation.Composite;
import com.github.nalukit.nalu.client.component.annotation.Composites;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import com.github.nalukit.nalu.processor.common.MockContext;
import com.github.nalukit.nalu.processor.common.ui.controllerWithComposite01.composite.CompositeController01;

@Controller(route = "/mockShell/route01/:parameter01",
            selector = "selector01",
            component = Component01.class,
            componentInterface = IComponent01.class)
@Composites(@Composite(name = "testComposite",
                       compositeController = CompositeController01.class,
                       selector = "selector"))
public class ControllerWithComposite01
    extends AbstractComponentController<MockContext, IComponent01, String>
    implements IComponent01.Controller {

  public ControllerWithComposite01() {
  }

  @AcceptParameter("parameter01")
  public void setParameter01(String parameter) {
  }

}
