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

package io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07;

import io.github.nalukit.nalu.client.component.AbstractComponentController;
import io.github.nalukit.nalu.client.component.annotation.AcceptParameter;
import io.github.nalukit.nalu.client.component.annotation.Composite;
import io.github.nalukit.nalu.client.component.annotation.Composites;
import io.github.nalukit.nalu.client.component.annotation.Controller;
import io.github.nalukit.nalu.processor.common.MockContext;
import io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController03;
import io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController04;
import io.github.nalukit.nalu.processor.common.ui.controllerWithComposite07.composite.CompositeController05;

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
                         condition = CompositeCondition01.class),
              @Composite(name = "testComposite03",
                         compositeController = CompositeController05.class,
                         selector = "selector",
                         condition = CompositeCondition02.class) })
public class ControllerWithComposite05
    extends AbstractComponentController<MockContext, IComponent05, String>
    implements IComponent05.Controller {

  public ControllerWithComposite05() {
  }

  @AcceptParameter("parameter05")
  public void setParameter05(String parameter) {
  }

}
