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

package com.github.nalukit.nalu.client.internal.module;

import com.github.nalukit.nalu.client.context.IsModuleContext;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import com.github.nalukit.nalu.client.internal.route.RouterConfiguration;
import com.github.nalukit.nalu.client.module.IsModule;

/**
 * generator of the eventBus
 */
@NaluInternalUse
public abstract class AbstractModule<C extends IsModuleContext>
    implements IsModule<C> {

  public AbstractModule() {
  }

  @Override
  public void loadModule(RouterConfiguration routeConfiguration) {
    this.loadShellFactory();
    this.loadCompositeController();
    this.loadComponents();
    this.loadFilters(routeConfiguration);
    this.loadHandlers();
  }

  protected abstract void loadHandlers();

  protected abstract void loadFilters(RouterConfiguration routeConfiguration);

  protected abstract void loadComponents();

  protected abstract void loadCompositeController();

  protected abstract void loadShellFactory();

}
