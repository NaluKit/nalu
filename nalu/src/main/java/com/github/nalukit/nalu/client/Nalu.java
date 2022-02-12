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

package com.github.nalukit.nalu.client;

import com.github.nalukit.nalu.client.internal.PropertyFactory;
import com.github.nalukit.nalu.client.internal.route.RouterUtils;

public class Nalu {
  
  public static String getVersion() {
    return "v2.8.0-gwt-2.8.2";
  }
  
  public static boolean hasHistory() {
    return PropertyFactory.get()
                          .hasHistory();
  }
  
  public static boolean isUsingHash() {
    return PropertyFactory.get()
                          .isUsingHash();
  }
  
  public static boolean isUsingColonForParametersInUrl() {
    return PropertyFactory.get()
                          .isUsingColonForParametersInUrl();
  }
  
  /**
   * This method compares the route with the value of withRoute respecting parameters.
   *
   * <ul>
   * <li>the route can contain parameter values</li>
   * <li>the withRoute should contain '*' instead of parameter values</li>
   * </ul>
   * <p>
   * Comparing route '/app/person/3/edit/ with '/app/person/*&#47;edit'
   * will return true
   * <p>
   * Comparing route '/app/person/3/edit/ with '/app/person/edit/*&#47;'
   * will return false.
   * <p>
   * Comparing route '/app/person/3/edit/ with '/app/person/*&#47;/*&#47;'
   * will return true.
   *
   * @param route     the route containing parameter values instead of '*'
   * @param withRoute the compare route which has no parameter values and uses '*' instead
   * @return true the routes matches or false in case not
   */
  public static boolean match(String route,
                              String withRoute) {
    return RouterUtils.get()
                      .match(route,
                             withRoute);
  }
  
  /**
   * This method compares the route with the value of withRoute respecting parameters.
   *
   * <ul>
   * <li>the route can contain parameter values</li>
   * <li>the withRoute should contain '*' instead of parameter values</li>
   * </ul>
   * <p>
   * Comparing route '/app/person/3/edit/ with '/app/person/*&#47;edit'
   * will return true
   * <p>
   * Comparing route '/app/person/3/edit/ with '/app/person/edit/*&#47;'
   * will return false.
   * <p>
   * Comparing route '/app/person/3/edit/ with '/app/person/*&#47;/*&#47;'
   * will return true.
   *
   * @param route     the route containing parameter values instead of '*'
   * @param withRoute the compare route which has no parameter values and uses '*' instead
   * @param exact     if true, routes must match exactly
   * @return true the routes matches or false in case not
   */
  public static boolean match(String route,
                              String withRoute,
                              boolean exact) {
    return RouterUtils.get()
                      .match(route,
                             withRoute,
                             exact);
  }
  
}
