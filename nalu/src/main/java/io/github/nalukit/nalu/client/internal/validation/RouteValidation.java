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

package io.github.nalukit.nalu.client.internal.validation;

import io.github.nalukit.nalu.client.Nalu;
import io.github.nalukit.nalu.client.internal.route.RouteConfig;
import io.github.nalukit.nalu.client.internal.route.RouterConfiguration;
import io.github.nalukit.nalu.client.internal.route.ShellConfig;
import io.github.nalukit.nalu.client.internal.route.ShellConfiguration;
import io.github.nalukit.nalu.client.util.NaluUtils;

import java.util.Optional;

public class RouteValidation {

  public static boolean validateStartRoute(ShellConfiguration shellConfiguration,
                                           RouterConfiguration routerConfiguration,
                                           String route) {
    return validateRoute(shellConfiguration,
                         routerConfiguration,
                         route);

  }

  private static boolean validateRoute(ShellConfiguration shellConfiguration,
                                       RouterConfiguration routerConfiguration,
                                       String route) {
    String shellOfRoute      = getShellFromRoute(route);
    String routeWithoutShell = getRouteWithoutShellAndParameter(route);
    // check shell
    Optional<ShellConfig> optionalShell = shellConfiguration.getShells()
                                                            .stream()
                                                            .filter(s -> s.getRoute()
                                                                          .equals("/" + shellOfRoute))
                                                            .findFirst();
    if (!optionalShell.isPresent()) {
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
    return optionalRoute.isPresent();
  }

  private static String getShellFromRoute(String route) {
    String shell = route;
    shell = NaluUtils.removeLeading("/", shell);

    if (shell.contains("/")) {
      return shell.substring(0,
                             shell.indexOf("/"));
    } else {
      return shell;
    }
  }

  private static String getRouteWithoutShellAndParameter(String route) {
    String routeWithoutShell = route;
    routeWithoutShell = NaluUtils.removeLeading("/", routeWithoutShell);

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

}
