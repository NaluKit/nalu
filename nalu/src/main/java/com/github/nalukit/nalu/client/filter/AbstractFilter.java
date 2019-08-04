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

package com.github.nalukit.nalu.client.filter;

import com.github.nalukit.nalu.client.Router;
import com.github.nalukit.nalu.client.context.IsContext;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import com.github.nalukit.nalu.client.model.NaluErrorMessage;

public abstract class AbstractFilter<C extends IsContext>
    implements IsFilter {

  protected C context;

  private Router router;

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
  public void setContext(C context) {
    this.context = context;
  }

  /**
   * Sets the router instance (used to set applicaiton error message)
   * <p>
   * <b>DO NOT USE!</b>
   *
   * @param router the application router
   */
  @NaluInternalUse
  public void setRouter(Router router) {
    this.router = router;
  }

  /**
   * Clears the application error message.
   * <p>
   * Should be called after the error message is displayed!
   */
  protected void clearApplicationErrorMessage() {
    this.router.clearApplicationErrorMessage();
  }

  /**
   * Sets the application error message.
   * <p>
   *
   * @param errorType    a String that indicates the type of the error
   *                     (value is to set by the developer)
   * @param errorMessage the error message that should be displayed
   */
  protected void setApplicationErrorMessage(String errorType,
                                            String errorMessage) {
    this.router.setApplicationErrorMessage(errorType,
                                           errorMessage);
  }

  /**
   * Sets the application error message.
   * <p>
   *
   * @param applicationErrorMessage the new applicaiton error message
   */
  protected void setApplicationErrorMessage(NaluErrorMessage applicationErrorMessage) {
    this.router.setApplicationErrorMessage(applicationErrorMessage);
  }

}
