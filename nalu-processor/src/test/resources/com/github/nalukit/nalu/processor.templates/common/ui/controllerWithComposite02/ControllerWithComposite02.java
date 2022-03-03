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

package com.github.nalukit.nalu.processor.common.ui.controllerWithComposite02;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.annotation.AcceptParameter;
import com.github.nalukit.nalu.client.component.annotation.Composite;
import com.github.nalukit.nalu.client.component.annotation.Composites;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import com.github.nalukit.nalu.processor.common.MockContext;
import com.github.nalukit.nalu.processor.common.ui.controllerWithComposite02.composite.CompositeController02;

@Controller(route = "/mockShell/route02/:parameter02",
            selector = "selector02",
            component = Component02.class,
            componentInterface = IComponent02.class)
@Composites(@Composite(name = "testComposite",
                       compositeController = CompositeController02.class,
                       selector = "selector",
                       condition = CompositeCondition02.class))
public class ControllerWithComposite02
    extends AbstractComponentController<MockContext, IComponent02, String>
    implements IComponent02.Controller {
  
  public ControllerWithComposite02() {
  }
  
  @AcceptParameter("parameter02")
  public void setParameter02(String parameter) {
  }
  
}
