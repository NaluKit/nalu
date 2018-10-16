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

package com.github.nalukit.nalu.client.internal;

import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import org.gwtproject.event.shared.HandlerRegistration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NaluInternalUse
public class HandlerRegistrations {

  private List<HandlerRegistration> handlerRegistrations;

  public HandlerRegistrations() {
    this.handlerRegistrations = new ArrayList<>();
  }

  /**
   * Create a {@link HandlerRegistration} that will call {@link
   * HandlerRegistration#removeHandler()} on all supplied handlers if {@link
   * HandlerRegistration#removeHandler()} is called on the returned object.
   *
   * <p>A simple example:
   *
   * <pre>
   * HandlerRegistration hr1 = ...
   * HandlerRegistration hr2 = ...
   * return HandlerRegistrations.compose(hr1, hr2);
   * </pre>
   *
   * @param handlers the {@link HandlerRegistration handler registrations} that should be composed
   *                 into a single {@link HandlerRegistration}
   */
  public void compose(HandlerRegistration... handlers) {
    handlerRegistrations.addAll(Arrays.asList(handlers));
  }

  public void add(HandlerRegistration handlerRegistration) {
    handlerRegistrations.add(handlerRegistration);
  }

  public void removeHandler() {
    if (handlerRegistrations == null) {
      return;
    }
    handlerRegistrations.forEach(h -> h.removeHandler());
    // make sure we remove the handlers to avoid potential leaks
    // if someone fails to null out their reference to us
    handlerRegistrations = null;
  }
}
