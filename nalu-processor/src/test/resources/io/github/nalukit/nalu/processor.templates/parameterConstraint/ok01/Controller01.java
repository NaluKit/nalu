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

package io.github.nalukit.nalu.processor.parameterConstraint.ok01;

import io.github.nalukit.nalu.client.component.AbstractComponentController;
import io.github.nalukit.nalu.client.component.annotation.AcceptParameter;
import io.github.nalukit.nalu.client.component.annotation.Controller;
import io.github.nalukit.nalu.client.constraint.annotation.ParameterConstraint;
import io.github.nalukit.nalu.processor.common.MockContext;
import io.github.nalukit.nalu.processor.parameterConstraint.ParameterConstraintRule01;

@Controller(route = "/mockShell/route01/:parameter01",
            selector = "selector01",
            component = Component01.class,
            componentInterface = IComponent01.class)
public class Controller01
    extends AbstractComponentController<MockContext, IComponent01, String>
    implements IComponent01.Controller {
  
  public Controller01() {
  }
  
  @AcceptParameter("parameter01")
  @ParameterConstraint(rule = ParameterConstraintRule01.class, illegalParameterRoute = "asdfasd")
  public void setParameter01(String parameter) {
  }
  
}
