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

package com.github.nalukit.nalu.client.internal.validation;

import com.github.nalukit.nalu.client.Nalu;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.route.RouteConfig;
import com.github.nalukit.nalu.client.internal.route.RouterConfiguration;
import com.github.nalukit.nalu.client.internal.route.ShellConfig;
import com.github.nalukit.nalu.client.internal.route.ShellConfiguration;

import java.util.Optional;

public class RouteValidation {

  public static boolean validateRoute(ShellConfiguration shellConfiguration,
                                      RouterConfiguration routerConfiguration,
                                      String route) {
    return validateRoute(shellConfiguration,
                         routerConfiguration,
                         route,
                         false,
                         false);

  }

  public static boolean validateStartRoute(ShellConfiguration shellConfiguration,
                                           RouterConfiguration routerConfiguration,
                                           String route) {
    return validateRoute(shellConfiguration,
                         routerConfiguration,
                         route,
                         true,
                         false);

  }

  public static boolean validateRouteError(ShellConfiguration shellConfiguration,
                                           RouterConfiguration routerConfiguration,
                                           String route) {
    return validateRoute(shellConfiguration,
                         routerConfiguration,
                         route,
                         false,
                         true);

  }

  private static boolean validateRoute(ShellConfiguration shellConfiguration,
                                       RouterConfiguration routerConfiguration,
                                       String route,
                                       boolean startRoute,
                                       boolean routeError) {
    String shellOfRoute = getShellFromRoute(route);
    String routeWithoutShell = getRouteWithoutShellAndParameter(route);
    // check shell
    Optional<ShellConfig> optionalShell = shellConfiguration.getShells()
                                                           .stream()
                                                           .filter(s -> s.getRoute()
                                                                         .equals("/" + shellOfRoute))
                                                           .findFirst();
    if (!optionalShell.isPresent()) {
      logRouteNotFound(route,
                      startRoute,
                      routeError);
      return false;
    }
    // check route
    String searchRoute = "/" + shellOfRoute;
    if (routeWithoutShell.trim()
                         .length() > 0) {
      searchRoute = searchRoute + "/" + routeWithoutShell;
    }
    String finalSearchRoute = searchRoute;
    Optional<RouteConfig> optionalRoute = routerConfiguration.getRouters()
                                                             .stream()
                                                             .filter(r -> Nalu.match(finalSearchRoute,
                                                                                     r.getRoute()))
                                                             .findFirst();
    if (!optionalRoute.isPresent()) {
      logRouteNotFound(route,
                      startRoute,
                      routeError);
      return false;
    }
    return true;
  }

  private static void logRouteNotFound(String route,
                                       boolean startRoute,
                                       boolean routeError) {
    String sb = "value of ";
    if (startRoute) {
      sb += "start route ";
    } else if (routeError) {
      sb += "route error ";
    } else {
      sb += "route ";
    }
    sb += ">>" + route + "<< does not exist!";
    ClientLogger.get()
                .logSimple(sb,
                           0);
  }

  private static String getRouteWithoutShellAndParameter(String route) {
    String routeWithoutShell = route;
    if (routeWithoutShell.startsWith("/")) {
      routeWithoutShell = routeWithoutShell.substring(1);
    }
    if (routeWithoutShell.contains("/")) {
      routeWithoutShell = routeWithoutShell.substring(routeWithoutShell.indexOf("/"));
    } else {
      return "";
    }
    if (routeWithoutShell.contains("/")) {
      routeWithoutShell = routeWithoutShell.substring(1);
    }
    if (routeWithoutShell.contains("/:")) {
      routeWithoutShell = routeWithoutShell.substring(0,
                                                      routeWithoutShell.indexOf("/:"));
    }
    return routeWithoutShell;
  }

  private static String getShellFromRoute(String route) {
    String shell = route;
    if (shell.startsWith("/")) {
      shell = shell.substring(1);
    }
    if (shell.contains("/")) {
      return shell.substring(0,
                             shell.indexOf("/"));
    } else {
      return shell;
    }
  }

}
