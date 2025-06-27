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

package io.github.nalukit.nalu.processor.common.ui.controllerWithComposite04;

import io.github.nalukit.nalu.client.component.AbstractComponentController;
import io.github.nalukit.nalu.client.component.annotation.AcceptParameter;
import io.github.nalukit.nalu.client.component.annotation.Composite;
import io.github.nalukit.nalu.client.component.annotation.Composites;
import io.github.nalukit.nalu.client.component.annotation.Controller;
import io.github.nalukit.nalu.processor.common.MockContext;
import io.github.nalukit.nalu.processor.common.ui.controllerWithComposite04.composite.CompositeController03;
import io.github.nalukit.nalu.processor.common.ui.controllerWithComposite04.composite.CompositeController04;

@Controller(route = "/mockShell/route04/:parameter04",
            selector = "selector04",
            component = Component04.class,
            componentInterface = IComponent04.class)
@Composites({ @Composite(name = "testComposite03",
                         compositeController = CompositeController03.class,
                         selector = "selector",
                         condition = CompositeCondition01.class),
              @Composite(name = "testComposite04",
                         compositeController = CompositeController04.class,
                         selector = "selector",
                         condition = CompositeCondition02.class) })
public class ControllerWithComposite04
    extends AbstractComponentController<MockContext, IComponent04, String>
    implements IComponent04.Controller {

  public ControllerWithComposite04() {
  }

  @AcceptParameter("parameter04")
  public void setParameter04(String parameter) {
  }

}
