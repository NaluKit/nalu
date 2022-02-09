/*
 * Copyright (c) 2018 - 2020 - Frank Hossfeld
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

package com.github.nalukit.nalu.client.internal.module;

import com.github.nalukit.nalu.client.Router;
import com.github.nalukit.nalu.client.component.AlwaysLoadComposite;
import com.github.nalukit.nalu.client.component.AlwaysShowPopUp;
import com.github.nalukit.nalu.client.context.AbstractModuleContext;
import com.github.nalukit.nalu.client.context.ContextDataStore;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import com.github.nalukit.nalu.client.internal.route.RouterConfiguration;
import com.github.nalukit.nalu.client.module.IsModule;
import com.github.nalukit.nalu.client.module.IsModuleLoader;
import org.gwtproject.event.shared.SimpleEventBus;

/**
 * generator of the eventBus
 */
@NaluInternalUse
public abstract class AbstractModule<C extends AbstractModuleContext>
    implements IsModule<C> {

  protected Router         router;
  protected C              moduleContext;
  protected SimpleEventBus eventBus;

  protected AlwaysLoadComposite alwaysLoadComposite;
  protected AlwaysShowPopUp     alwaysShowPopUp;

  public AbstractModule(ContextDataStore applicationContext) {
    super();
    this.moduleContext = createModuleContext();
    this.moduleContext.setApplicationContext(applicationContext);
  }

  /**
   * Sets the alwaysLoadComposite instance
   *
   * @param alwaysLoadComposite the alwaysLoadComposite instance
   */
  @Override
  @NaluInternalUse
  public final void setAlwaysLoadComposite(AlwaysLoadComposite alwaysLoadComposite) {
    this.alwaysLoadComposite = alwaysLoadComposite;
  }

  /**
   * Sets the alwaysShowPopUp instnace
   *
   * @param alwaysShowPopUp the alwaysShowPopUp instance
   */
  @Override
  @NaluInternalUse
  public final void setAlwaysShowPopUp(AlwaysShowPopUp alwaysShowPopUp) {
    this.alwaysShowPopUp = alwaysShowPopUp;
  }

  /**
   * Sets the event bus inside the router
   *
   * @param eventBus Nalu application event bus
   */
  @Override
  @NaluInternalUse
  public final void setEventBus(SimpleEventBus eventBus) {
    this.eventBus = eventBus;
  }

  /**
   * Sets the router inside the router
   *
   * @param router Nalu application router
   */
  @Override
  @NaluInternalUse
  public final void setRouter(Router router) {
    this.router = router;
  }

  @Override
  @NaluInternalUse
  public final void loadModule(RouterConfiguration routeConfiguration) {
    this.setUpContext();
    this.loadShellFactory();
    this.loadCompositeController();
    this.loadComponents();
    this.loadFilters(routeConfiguration);
    this.loadPopUpFilters(routeConfiguration);
    this.loadHandlers();
    this.loadParameterConstraintRules();
    this.loadPopUpControllers();
    this.loadBlockControllers();
  }

  private void setUpContext() {
  }

  protected abstract void loadShellFactory();

  protected abstract void loadCompositeController();

  protected abstract void loadComponents();

  protected abstract void loadFilters(RouterConfiguration routeConfiguration);

  protected abstract void loadHandlers();

  protected abstract void loadParameterConstraintRules();

  protected abstract void loadPopUpControllers();

  protected abstract void loadBlockControllers();

  protected abstract void loadPopUpFilters(RouterConfiguration routeConfiguration);

  protected abstract C createModuleContext();

  protected abstract IsModuleLoader<C> createModuleLoader();

}
