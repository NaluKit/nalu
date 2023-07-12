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

import com.github.nalukit.nalu.client.IsRouter;
import com.github.nalukit.nalu.client.context.IsContext;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.HandlerRegistrations;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import org.gwtproject.event.shared.SimpleEventBus;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractShell<C extends IsContext>
    implements IsShell {

  protected IsRouter router;

  protected C context;

  protected     SimpleEventBus                                    eventBus;
  /* list fo composite controllers */
  private final Map<String, AbstractCompositeController<?, ?, ?>> compositeControllers;

  protected HandlerRegistrations handlerRegistrations = new HandlerRegistrations();

  public AbstractShell() {
    super();
    this.compositeControllers = new HashMap<>();
  }

  /**
   * The map of the depending composites of the shell
   *
   * @return Map of depending composites
   */
  @Override
  public Map<String, AbstractCompositeController<?, ?, ?>> getComposites() {
    return compositeControllers;
  }

  /**
   * Returns the composite stored under the composite name.
   *
   * @param name the name of the composite
   * @param <S>  type of the composite
   * @return instance of the composite
   */
  @Override
  @SuppressWarnings({ "unchecked",
                      "TypeParameterUnusedInFormals" })
  public <S extends AbstractCompositeController<?, ?, ?>> S getComposite(String name) {
    return (S) this.getComposites()
                   .get(name);
  }

  @NaluInternalUse
  public final void setRouter(IsRouter router) {
    this.router = router;
  }

  @NaluInternalUse
  public final void setContext(C context) {
    this.context = context;
  }

  @NaluInternalUse
  public final void setEventBus(SimpleEventBus eventBus) {
    this.eventBus = eventBus;
  }

  /**
   * Ovverride this method with the necessary code to remove the shell.
   */
  @Override
  public abstract void detachShell();

  @Override
  public void onAttachedComponent() {
    // override this method if you need to do something, after a component is attached!
  }

  /**
   * internal framework method! Will be called by the framework after the
   * stop-method f the controller is called
   * <p>
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   */
  @Override
  public void removeHandlers() {
    this.handlerRegistrations.removeHandler();
    this.handlerRegistrations = new HandlerRegistrations();
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

}
