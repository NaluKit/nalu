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

package io.github.nalukit.nalu.client.handler;

import io.github.nalukit.nalu.client.IsRouter;
import io.github.nalukit.nalu.client.context.IsContext;
import org.gwtproject.event.shared.SimpleEventBus;

public abstract class AbstractHandler<C extends IsContext>
    implements IsHandler {

  protected C context;

  protected SimpleEventBus eventBus;

  protected IsRouter router;

  public AbstractHandler() {
    super();
  }

  public void setContext(C context) {
    this.context = context;
  }

  public void setEventBus(SimpleEventBus eventBus) {
    this.eventBus = eventBus;
  }

  public void setRouter(IsRouter router) {
    this.router = router;
  }

  /**
   * In case you want to do something when the application is started
   * and the handler created. F.e.: adding event handler
   * <p>
   * Of course, it is possible to use the @EventHandler-annotation
   * for adding event hadnler. In this case you don't need to override
   * the bind-method.
   */
  @Override
  public void bind() {
  }
}
