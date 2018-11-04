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

package com.github.nalukit.nalu.client.component;

import com.github.nalukit.nalu.client.Router;
import com.github.nalukit.nalu.client.application.IsContext;
import com.github.nalukit.nalu.client.internal.HandlerRegistrations;
import org.gwtproject.event.shared.SimpleEventBus;

public abstract class AbstractShell<C extends IsContext>
    implements IsShell {

  protected Router router;

  protected C context;

  protected SimpleEventBus eventBus;

  protected HandlerRegistrations handlerRegistrations = new HandlerRegistrations();

  public AbstractShell() {
  }

  public void setRouter(Router router) {
    this.router = router;
  }

  public void setContext(C context) {
    this.context = context;
  }

  public void setEventBus(SimpleEventBus eventBus) {
    this.eventBus = eventBus;
  }

  public void bind() {
    // override this method if you need to bind something inside the Shell
  }

  /**
   * internal framework method! Will be called by the framdework after the
   * stop-method f the controller is called
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   */
  @Override
  public void removeHandlers() {
    this.handlerRegistrations.removeHandler();
    this.handlerRegistrations = new HandlerRegistrations();
  }

  @Override
  public void onAttachedComponent() {
    // override this method if you need to do something, after a component is attached!
  }

  /**
   * Will be called in case a shell ist detachred.
   *
   * In case you have something to do if a shell is detached, override this mehtod.
   */
  @Override
  public void detachShell() {
  }
}
