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

package com.github.nalukit.nalu.client;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.AbstractCompositeController;
import com.github.nalukit.nalu.client.model.NaluErrorMessage;

import java.util.Map;

public interface Router {

  /**
   * clears the chache
   */
  void clearCache();

  /**
   * Returns the last error message set by the application.
   * <p>
   * Once the error message is consumed, it should be reseted by the developer.
   * (after displayed on the error site!)
   *
   * @return the last set error message or null, if there is none
   */
  NaluErrorMessage getApplicationErrorMessage();

  /**
   * Sets the application error message.
   * <p>
   *
   * @param applicationErrorMessage the new application error message
   */
  void setApplicationErrorMessage(NaluErrorMessage applicationErrorMessage);

  /**
   * Clears the application error message.
   * <p>
   * Should be called after the error message is displayed!
   */
  void clearApplicationErrorMessage();

  /**
   * Sets the application error message.
   * <p>
   *
   * @param errorType    a String that indicates the type of the error
   *                     (value is to set by the developer)
   * @param errorMessage the error message that should be displayed
   */
  void setApplicationErrorMessage(String errorType,
                                  String errorMessage);

  /**
   * Clears the Nalu error message.
   * <p>
   * Should be called after the error message is displayed!
   */
  void clearNaluErrorMessage();

  /**
   * Returns the last error message set by Nalu.
   * <p>
   * Once the error message is consumed, it should be reseted by the developer.
   * (after displayed on the error site!)
   *
   * @return the last set error message or null, if there is none
   */
  NaluErrorMessage getNaluErrorMessage();

  /**
   * Returns the last error message set by Nalu or application.
   * <p>
   * In case a error message is set by Nalu and by the application,
   * this method will return the error message set by Nalu.
   * <p>
   * Once the error message is consumed, it should be reseted by the developer.
   * (after displayed on the error site!)
   *
   * @return the last set error message set by thel application
   * or null, if there is none
   */
  NaluErrorMessage getErrorMessageByPriority();

  /**
   * Generates the url using the given input ..
   *
   * @param route route to navigate to
   * @param parms parameters of the route
   * @return generated hash
   */
  String generate(String route,
                  String... parms);

  /**
   * Route to a new page.
   *
   * @param route     new route
   * @param parameter parameters of the route
   */
  void route(String route,
             String... parameter);

  /**
   * Removes a controller from the chache
   *
   * @param controller controller to be removed
   * @param <C>        controller type
   */
  <C extends AbstractComponentController<?, ?, ?>> void removeFromCache(C controller);

  /**
   * Removes a controller from the chache
   *
   * @param controller controller to be removed
   * @param <C>        controller type
   */
  <C extends AbstractCompositeController<?, ?, ?>> void removeFromCache(C controller);

  /**
   * Stores the instance of the controller in the cache, so that it can be reused the next time
   * the route is called.
   *
   * @param controller controller to store
   * @param <C>        controller type
   */
  <C extends AbstractComponentController<?, ?, ?>> void storeInCache(C controller);

  /**
   * Stores the instance of the composite controller in the cache, so that it can be reused the next time
   * the route is called.
   *
   * @param controller composite controller to store
   * @param <C>        composite controller type
   */
  <C extends AbstractCompositeController<?, ?, ?>> void storeInCache(C controller);

  /**
   * Get a map of parameters contained in the url at application start
   *
   * @return list of parameters at application start
   */
  Map<String, String> getStartQueryParameters();

}
