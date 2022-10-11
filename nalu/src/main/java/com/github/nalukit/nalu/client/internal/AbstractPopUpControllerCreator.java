/*
 * Copyright (c) 2018 Frank Hossfeld
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

package com.github.nalukit.nalu.client.internal;

import com.github.nalukit.nalu.client.IsRouter;
import com.github.nalukit.nalu.client.context.IsContext;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import com.github.nalukit.nalu.client.internal.application.IsPopUpControllerCreator;
import org.gwtproject.event.shared.SimpleEventBus;

@NaluInternalUse
public abstract class AbstractPopUpControllerCreator<C extends IsContext>
    implements IsPopUpControllerCreator {

  protected StringBuilder sb;
  protected IsRouter      router;
  protected C             context;

  protected SimpleEventBus eventBus;

  private boolean initialShow;

  public AbstractPopUpControllerCreator(IsRouter router,
                                        C context,
                                        SimpleEventBus eventBus) {
    super();
    this.router   = router;
    this.context  = context;
    this.eventBus = eventBus;

    this.initialShow = true;
  }

  @Override
  public boolean isInitialShow() {
    return initialShow;
  }

  public void initialShowDone() {
    this.initialShow = false;
  }
}
