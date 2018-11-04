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

package com.github.nalukit.nalu.processor.controller.controllerWithIsComponentControllerNotOK.ui.content01;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.IsComponentCreator;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import com.github.nalukit.nalu.processor.common.MockContext;
import com.github.nalukit.nalu.processor.controller.controllerWithIsComponentControllerOK.ui.content01.Content01Component;
import com.github.nalukit.nalu.processor.controller.controllerWithIsComponentControllerOK.ui.content01.IContent01Component;

@Controller(route = "/route01",
            selector = "selector01",
            component = Content01Component.class,
            componentInterface = IContent01Component.class)
public class Content01Controller
    extends AbstractComponentController<MockContext, IContent01Component, String>
    implements IContent01Component.Controller,
               IsComponentCreator<IContent02Component> {

  public Content01Controller() {
  }

  @Override
  public IContent02Component createComponent() {
    return new Content02Component();
  }
}
