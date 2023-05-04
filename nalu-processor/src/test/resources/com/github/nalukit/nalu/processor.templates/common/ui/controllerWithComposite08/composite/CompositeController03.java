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

package com.github.nalukit.nalu.processor.common.ui.controllerWithComposite08.composite;

import com.github.nalukit.nalu.client.component.AbstractCompositeController;
import com.github.nalukit.nalu.client.component.annotation.AcceptParameter;
import com.github.nalukit.nalu.client.component.annotation.CompositeController;
import com.github.nalukit.nalu.processor.common.MockContext;

@CompositeController(component = CompositeComponent03.class,
                     componentInterface = ICompositeComponent03.class)
public class CompositeController03
    extends AbstractCompositeController<MockContext, ICompositeComponent03, String>
    implements ICompositeComponent03.Controller {

  public CompositeController03() {
  }

  @AcceptParameter("parameter03")
  public void setParameter03(String parameter) {
  }

}
