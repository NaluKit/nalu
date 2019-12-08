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

import java.util.Map;

public interface Router {

  /**
   * clears the cache
   */
  void clearCache();

  /**
   * Generates the url using the given input ..
   *
   * @param route  route to navigate to
   * @param params parameters of the route
   * @return generated hash
   */
  String generate(String route,
                  String... params);

  /**
   * Route to a new page.
   *
   * @param route     new route
   * @param parameter parameters of the route
   */
  void route(String route,
             String... parameter);

  /**
   * Route to a new page without confirmation.
   *
   * @param route     new route
   * @param parameter parameters of the route
   */
  void forceRoute(String route,
                  String... parameter);

  /**
   * Removes a controller from the cache
   *
   * @param controller controller to be removed
   * @param <C>        controller type
   */
  <C extends AbstractComponentController<?, ?, ?>> void removeFromCache(C controller);

  /**
   * Removes a controller from the cache
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

  /**
   * Returns the current route.
   * <br>
   * The method will return a route with a '*' as placeholder for parameters.
   * <br>
   * Keep in mind:
   * This is the current route. The route might be changed by other processes,
   * f.e.: a RoutingException or something else!
   *
   * @return the current route
   */
  String getCurrentRoute();

  /**
   * Returns the current parameters from the last executed route..
   * <br>
   * The method will return a String[] for parameters.
   * <br>
   * Keep in mind:
   * This is the current route. The route might be changed by other processes,
   * f.e.: a RoutingException or something else!
   *
   * @return the current parameters
   */
  String[] getCurrentParameters();

  /**
   * Returns the last executed hash.
   * <br>
   * The method will return a route with all parameters set.
   * <br>
   * Keep in mind:
   * This is the last executed route. The route might be changed by other processes,
   * f.e.: a RoutingException or something else!
   *
   * @return the current route
   */
  String getLastExecutetdHash();

}
