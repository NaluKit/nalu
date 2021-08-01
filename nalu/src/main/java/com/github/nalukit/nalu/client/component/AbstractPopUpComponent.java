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

package com.github.nalukit.nalu.client.component;

import com.github.nalukit.nalu.client.internal.HandlerRegistrations;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

public abstract class AbstractPopUpComponent<C extends IsPopUpComponent.Controller>
    extends AbstractCommonComponent<C>
    implements IsPopUpComponent<C>,
               IsAbstractPopUpComponent {

  protected HandlerRegistrations handlerRegistrations = new HandlerRegistrations();

  public AbstractPopUpComponent() {
  }

  /**
   * call to show the popup
   */
  @Override
  public abstract void show();

  /**
   * call to hide the popup
   */
  @Override
  public abstract void hide();

  /**
   * internal framework method! Will be called by the framework after the
   * stop-method f the controller is called
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   */
  @Override
  @NaluInternalUse
  public final void removeHandlers() {
    this.handlerRegistrations.removeHandler();
    this.handlerRegistrations = new HandlerRegistrations();
  }

}
