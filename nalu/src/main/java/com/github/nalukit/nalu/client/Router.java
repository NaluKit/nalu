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
import com.github.nalukit.nalu.client.internal.route.RouterException;
import com.github.nalukit.nalu.client.model.NaluErrorMessage;

import java.util.Map;

public interface Router {

  /**
   * clears the chache
   */
  void clearCache();

  /**
   * Clears the Nalu error message.
   * <p>
   * Should be called after the error message is displayed!
   */
  void clearNaluErrorMessage();

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
   * Returns the last error message set by Nalu.
   * <p>
   * Once the error message is consumed, it should be reseted by the developer.
   * (after displayed on the error site!)
   *
   * @return the last set error message or null, if there is none
   */
  NaluErrorMessage getNaluErrorMessage();

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
   * Stores the instance of the controller in the cache, so that it can be reused the next time
   * the route is called.
   *
   * @param controller controller to store
   * @param <C>        controller type
   */
  <C extends AbstractComponentController<?, ?, ?>> void storeInCache(C controller);

  /**
   * Get a map of parameters contained in the url at application start
   *
   * @return list of parameters at application start
   */
  Map<String, String> getStartQueryParameters();

}
