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

package com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.annotation.AcceptParameter;
import com.github.nalukit.nalu.client.component.annotation.Composite;
import com.github.nalukit.nalu.client.component.annotation.Composites;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import com.github.nalukit.nalu.processor.common.MockContext;
import com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.composite.CompositeController03;
import com.github.nalukit.nalu.processor.common.ui.controllerWithComposite06.composite.CompositeController04;

@Controller(route = "/mockShell/route05/:parameter05",
            selector = "selector05",
            component = Component05.class,
            componentInterface = IComponent05.class)
@Composites({ @Composite(name = "testComposite01",
                         compositeController = CompositeController03.class,
                         selector = "selector",
                         condition = CompositeCondition01.class),
              @Composite(name = "testComposite02",
                         compositeController = CompositeController04.class,
                         selector = "selector",
                         condition = CompositeCondition01.class) })
public class ControllerWithComposite05
    extends AbstractComponentController<MockContext, IComponent05, String>
    implements IComponent05.Controller {

  public ControllerWithComposite05() {
  }

  @AcceptParameter("parameter05")
  public void setParameter05(String parameter) {
  }

}
