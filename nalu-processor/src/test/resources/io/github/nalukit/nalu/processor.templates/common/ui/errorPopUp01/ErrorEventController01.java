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

package io.github.nalukit.nalu.processor.common.ui.errorPopUp01;

import io.github.nalukit.nalu.client.component.AbstractErrorPopUpComponentController;
import io.github.nalukit.nalu.client.component.annotation.ErrorPopUpController;
import io.github.nalukit.nalu.processor.common.MockContext;

@ErrorPopUpController(componentInterface = IErrorEventComponent01.class,
                      component = ErrorEventComponent01.class)
public class ErrorEventController01
    extends AbstractErrorPopUpComponentController<MockContext, IErrorEventComponent01>
    implements IErrorEventComponent01.Controller {
  
  public ErrorEventController01() {
  }
  
  @Override
  public void show() {
  }
  
}
