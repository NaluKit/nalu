/*
 * Copyright (c) 2018 - 2019 - Frank Hossfeld
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
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
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

  /**
   * The bind-method will be called before the shell is added to the viewport.
   * <p>
   * This method runs before the component and composites are
   * created. This is f.e.: a got place to do some
   * authentication checks.
   * <p>
   * Keep in mind, that the method is asynchronous. Once you have
   * done your work, you have to call <b>loader.continueLoading()</b>.
   * Otherwise Nalu will stop working!
   * <p>
   * Attention:
   * Do not call super.bind(loader)! Cause this will tell Nalu to
   * continue loading!
   *
   * @param loader loader to tell Nalu to continue loading the shell
   * @throws RoutingInterceptionException in case the bind shell
   *                                      process should be interrupted
   */
  @Override
  public void bind(ShellLoader loader)
      throws RoutingInterceptionException {
    loader.continueLoading();
  }

  /**
   * internal framework method! Will be called by the framework after the
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
   * Will be called in case a shellCreator ist detached.
   * <p>
   * In case you have something to do if a shellCreator is detached, override this method.
   */
  @Override
  public void detachShell() {
  }

}
