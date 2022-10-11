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

package com.github.nalukit.nalu.client.filter;

import com.github.nalukit.nalu.client.context.IsContext;
import com.github.nalukit.nalu.client.event.NaluErrorEvent;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import org.gwtproject.event.shared.SimpleEventBus;

public abstract class AbstractFilter<C extends IsContext>
    implements IsFilter {

  protected C              context;
  protected SimpleEventBus eventBus;

  public AbstractFilter() {
    super();
  }

  /**
   * Sets the context instance
   * <p>
   * <b>DO NOT USE!</b>
   *
   * @param context the application context
   */
  @NaluInternalUse
  public final void setContext(C context) {
    this.context = context;
  }

  /**
   * Sets the event bus instance (used to set application error message)
   * <p>
   * <b>DO NOT USE!</b>
   *
   * @param eventBus the application event bus
   */
  @NaluInternalUse
  public final void setEventBus(SimpleEventBus eventBus) {
    this.eventBus = eventBus;
  }

  /**
   * Fires a NaluError event.
   * <p>
   * Use this method to communicate an error inside a filter.
   *
   * @param event the error event
   */
  public void fireNaluErrorEvent(NaluErrorEvent event) {
    this.eventBus.fireEvent(event);
  }

}
