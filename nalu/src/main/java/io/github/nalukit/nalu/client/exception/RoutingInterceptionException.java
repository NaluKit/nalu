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

package io.github.nalukit.nalu.client.exception;

@SuppressWarnings("serial")
public class RoutingInterceptionException
    extends Exception {

  private final String controllerClassName;
  private final String route;
  private final String[] parameter;

  /**
   * create a RoutingInterceptionException
   *
   * @param controllerClassName name of the class of the controller that intercepts the routing
   * @param route               new route
   * @param parameter           new parameters
   */
  public RoutingInterceptionException(String controllerClassName,
                                      String route,
                                      String... parameter) {
    this.controllerClassName = controllerClassName;
    this.route               = route;
    this.parameter           = parameter;
  }

  /**
   * Returns the name of the controller class that intercepts the routing
   *
   * @return class name of the intercepting controller
   */
  public String getControllerClassName() {
    return controllerClassName;
  }

  public String getRoute() {
    return route;
  }

  public String[] getParameter() {
    return parameter;
  }

}
