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

package io.github.nalukit.nalu.client.module;

import io.github.nalukit.nalu.client.IsRouter;
import io.github.nalukit.nalu.client.context.AbstractModuleContext;
import org.gwtproject.event.shared.SimpleEventBus;

public abstract class AbstractModuleLoader<C extends AbstractModuleContext>
    implements IsModuleLoader<C> {

  protected C context;

  protected SimpleEventBus eventBus;

  protected IsRouter router;

  @Override
  public void setContext(C context) {
    this.context = context;
  }

  @Override
  public void setEventBus(SimpleEventBus eventBus) {
    this.eventBus = eventBus;
  }

  @Override
  public void setRouter(IsRouter router) {
    this.router = router;
  }

}
