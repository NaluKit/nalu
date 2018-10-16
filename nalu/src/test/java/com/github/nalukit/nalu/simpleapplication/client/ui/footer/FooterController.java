/*
 * Copyright (c) 2018 - Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *
 */

package com.github.nalukit.nalu.simpleapplication.client.ui.footer;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.simpleapplication.client.NaluSimpleApplicationContext;
import com.github.nalukit.nalu.simpleapplication.client.event.StatusChangeEvent;
import org.gwtproject.event.shared.HandlerRegistration;

/**
 * this is the presenter of the shell. The shell divides the browser in
 * severeal areas.
 */
public class FooterController
    extends AbstractComponentController<NaluSimpleApplicationContext, IFooterComponent, String>
    implements IFooterComponent.Controller {

  private HandlerRegistration registration;

  public FooterController() {
  }

  @Override
  public void start() {
    this.registration = this.eventBus.addHandler(StatusChangeEvent.TYPE,
                                                 e -> component.setStatus(e.getStatus()));
  }

  @Override
  public void stop() {
    this.registration.removeHandler();
  }
}
