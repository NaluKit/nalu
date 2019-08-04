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

package com.github.nalukit.nalu.client.internal.application;

import com.github.nalukit.nalu.client.Router;
import com.github.nalukit.nalu.client.context.IsContext;
import com.github.nalukit.nalu.client.component.AlwaysLoadComposite;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import com.github.nalukit.nalu.client.internal.route.RouterConfiguration;
import com.github.nalukit.nalu.client.plugin.IsPlugin;
import org.gwtproject.event.shared.SimpleEventBus;

/**
 * generator of the eventBus
 */
@NaluInternalUse
public abstract class AbstractPlugin<C extends IsContext>
    implements IsPlugin<C> {

  protected Router router;

  protected C context;

  protected SimpleEventBus      eventBus;
  /* instance of AlwaysLoadComposite-class */
  protected AlwaysLoadComposite alwaysLoadComposite;

  public AbstractPlugin(Router router,
                        C context,
                        SimpleEventBus eventBus,
                        AlwaysLoadComposite alwaysLoadComposite) {
    super();
    this.router = router;
    this.context = context;
    this.eventBus = eventBus;
    this.alwaysLoadComposite = alwaysLoadComposite;
  }

  @Override
  public void loadPlugin(RouterConfiguration routeConfiguration) {
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
