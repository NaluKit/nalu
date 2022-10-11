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

package com.github.nalukit.nalu.client.internal.application;

import com.github.nalukit.nalu.client.component.AbstractCompositeController;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

@NaluInternalUse
public class CompositeInstance {

  /* controller's class name */
  private String                               compositeClassName;
  /* controller */
  private AbstractCompositeController<?, ?, ?> composite;
  /* flag, that indicates weather the controller is reused or not */
  private boolean                              cached;

  public CompositeInstance() {
  }

  public String getCompositeClassName() {
    return compositeClassName;
  }

  public void setCompositeClassName(String compositeClassName) {
    this.compositeClassName = compositeClassName;
  }

  public AbstractCompositeController<?, ?, ?> getComposite() {
    return composite;
  }

  public void setComposite(AbstractCompositeController<?, ?, ?> composite) {
    this.composite = composite;
  }

  public boolean isCached() {
    return cached;
  }

  public void setCached(boolean cached) {
    this.cached = cached;
  }

}
