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

package io.github.nalukit.nalu.processor.common.ui.controllerWithComposite03;

import io.github.nalukit.nalu.client.component.AbstractComponentController;
import io.github.nalukit.nalu.client.component.annotation.AcceptParameter;
import io.github.nalukit.nalu.client.component.annotation.Composite;
import io.github.nalukit.nalu.client.component.annotation.Composites;
import io.github.nalukit.nalu.client.component.annotation.Controller;
import io.github.nalukit.nalu.processor.common.MockContext;
import io.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.composite.CompositeController01;
import io.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.composite.CompositeController03;

@Controller(route = "/mockShell/route03/:parameter03",
            selector = "selector03",
            component = Component03.class,
            componentInterface = IComponent03.class)
@Composites({ @Composite(name = "testComposite01",
                         compositeController = CompositeController01.class,
                         selector = "selector"),
              @Composite(name = "testComposite03",
                         compositeController = CompositeController03.class,
                         selector = "selector",
                         condition = CompositeCondition03.class) })
public class ControllerWithComposite03
    extends AbstractComponentController<MockContext, IComponent03, String>
    implements IComponent03.Controller {

  public ControllerWithComposite03() {
  }

  @AcceptParameter("parameter03")
  public void setParameter03(String parameter) {
  }

}
