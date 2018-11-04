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

package com.github.nalukit.nalu.client.internal.route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// TODO tests
public class RouteConfig {

  /* shell */
  private List<String> shell;

  /* route */
  private String route;

  /* route without shell */
  private String routeWithoutShell;

  /* parameters */
  private List<String> parameters;

  /* selector (where to add the element */
  private String selector;

  /* class name of the class which uses this configuration */
  private String className;

  @SuppressWarnings("unused")
  private RouteConfig() {
  }

  public RouteConfig(String route,
                     List<String> parameters,
                     String selector,
                     String className) {
    super();

    this.shell = new ArrayList<>();

    this.route = route;
    this.parameters = parameters;
    this.selector = selector;
    this.className = className;
    // get shell from route
    String tmpValue = route;
    if (tmpValue.startsWith("/")) {
      tmpValue = tmpValue.substring(1);
    }
    String shellFromRoute = "";
    if (tmpValue.contains("/")) {
      shellFromRoute = tmpValue.substring(0,
                                          tmpValue.indexOf("/"));
      this.routeWithoutShell = tmpValue.substring(tmpValue.indexOf("/"));
    } else {
      shellFromRoute = tmpValue;
      this.routeWithoutShell = "/";
    }
    if (shellFromRoute.startsWith("[")) {
      shellFromRoute = shellFromRoute.substring(1);
    }
    if (shellFromRoute.endsWith("]")) {
      shellFromRoute = shellFromRoute.substring(0, shellFromRoute.length() - 1);
    }
    if (shellFromRoute.contains("|")) {
      this.shell = Arrays.asList(shellFromRoute.split("\\|"))
                         .stream()
                         .map(s -> "/" + s)
                         .collect(Collectors.toList());
    } else {
      this.shell.add("/" + shellFromRoute);
    }
  }

  public String getRoute() {
    return route;
  }

  public boolean match(String route) {
    if (this.matchShell(route)) {
      return this.matchRouteWithoutShell(route);
    }
    return false;
  }

  private boolean matchShell(String route) {
    if (this.shell.contains("*")) {
      return true;
    }
    // seperate shell from route
    String shellOfRoute = route;
    if (shellOfRoute.startsWith("/")) {
      shellOfRoute = shellOfRoute.substring(1);
    }
    if (shellOfRoute.contains("/")) {
      shellOfRoute = shellOfRoute.substring(0,
                                            shellOfRoute.indexOf("/"));
    }
    return this.shell.contains("/" + shellOfRoute);
  }

  private boolean matchRouteWithoutShell(String route) {
    // seperate shell from route
    String routeWithoutShell = route;
    if (routeWithoutShell.startsWith("/")) {
      routeWithoutShell = routeWithoutShell.substring(1);
    }
    if (routeWithoutShell.contains("/")) {
      routeWithoutShell = routeWithoutShell.substring(routeWithoutShell.indexOf("/") + 1);
    } else {
      routeWithoutShell = "";
    }
    return this.routeWithoutShell.equals("/" + routeWithoutShell);
  }

  public List<String> getShell() {
    return shell;
  }

  public String getRouteWithoutShell() {
    return routeWithoutShell;
  }

  public String getSelector() {
    return selector;
  }

  public String getClassName() {
    return className;
  }

  public List<String> getParameters() {
    return parameters;
  }
}
