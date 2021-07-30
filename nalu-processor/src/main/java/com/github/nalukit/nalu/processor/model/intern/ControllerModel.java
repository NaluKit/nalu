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

package com.github.nalukit.nalu.processor.model.intern;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControllerModel {

  private final String[]                       originalRoute;
  private final List<String>                   parameters;
  private final ClassNameModel                 provider;
  private final List<ParameterAcceptorModel>   parameterAcceptors;
  private final boolean                        componentCreator;
  private       List<String>                   route;
  private       String                         selector;
  private       ClassNameModel                 context;
  private       ClassNameModel                 controller;
  private       ClassNameModel                 componentInterface;
  private       ClassNameModel                 component;
  private       List<ControllerCompositeModel> composites;

  public ControllerModel(String[] originalRoute,
                         List<String> route,
                         String selector,
                         List<String> parameters,
                         ClassNameModel context,
                         ClassNameModel controller,
                         ClassNameModel componentInterface,
                         ClassNameModel component,
                         ClassNameModel provider,
                         boolean componentCreator) {
    this.originalRoute      = originalRoute;
    this.route              = route;
    this.selector           = selector;
    this.parameters         = parameters;
    this.context            = context;
    this.controller         = controller;
    this.componentInterface = componentInterface;
    this.component          = component;
    this.provider           = provider;
    this.componentCreator   = componentCreator;

    this.parameterAcceptors = new ArrayList<>();
    this.composites         = new ArrayList<>();
  }

  public List<String> getRoute() {
    return route;
  }

  public void setRoute(List<String> route) {
    this.route = route;
  }

  public String getSelector() {
    return selector;
  }

  public void setSelector(String selector) {
    this.selector = selector;
  }

  public List<String> getParameters() {
    return parameters;
  }

  public ClassNameModel getController() {
    return controller;
  }

  public void setController(ClassNameModel controller) {
    this.controller = controller;
  }

  public ClassNameModel getComponentInterface() {
    return componentInterface;
  }

  public void setComponentInterface(ClassNameModel componentInterface) {
    this.componentInterface = componentInterface;
  }

  public ClassNameModel getComponent() {
    return component;
  }

  public void setComponent(ClassNameModel component) {
    this.component = component;
  }

  public ClassNameModel getProvider() {
    return provider;
  }

  public List<ParameterAcceptorModel> getParameterAcceptors() {
    return parameterAcceptors;
  }

  public String getParameterAcceptors(String parameterName) {
    Optional<ParameterAcceptorModel> optional = this.parameterAcceptors.stream()
                                                                       .filter(a -> parameterName.equals(a.getParameterName()))
                                                                       .findFirst();
    return optional.map(ParameterAcceptorModel::getMethodName)
                   .orElse(null);
  }

  public ParameterConstraintModel getConstraintModelFor(String parameterName) {
    Optional<ParameterAcceptorModel> optional = this.parameterAcceptors.stream()
                                                                       .filter(a -> parameterName.equals(a.getParameterName()))
                                                                       .findFirst();
    return optional.map(ParameterAcceptorModel::getParameterConstrait)
                   .orElse(null);
  }

  public ClassNameModel getContext() {
    return context;
  }

  public void setContext(ClassNameModel context) {
    this.context = context;
  }

  public List<ControllerCompositeModel> getComposites() {
    return composites;
  }

  public void setComposites(List<ControllerCompositeModel> composites) {
    this.composites = composites;
  }

  public boolean isComponentCreator() {
    return componentCreator;
  }

  public boolean match(String route) {
    for (String originalRoute : this.originalRoute) {
      if (this.matchShell(originalRoute,
                          route)) {
        String routeWithoutShell      = this.getRouteWithoutShell(originalRoute);
        String startRouteWithoutShell = this.getRouteWithoutShell(route);
        if (routeWithoutShell.equals(startRouteWithoutShell)) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean matchShell(String originalRoute,
                             String route) {
    if (originalRoute.startsWith("/*")) {
      return true;
    }
    String shellOfRoute         = this.getShellFromRoute(route);
    String shellOfOriginalRoute = this.getShellFromRoute(originalRoute);
    return shellOfOriginalRoute.contains(shellOfRoute);
  }

  private String getRouteWithoutShell(String route) {
    String routeWithoutShell = route;
    if (routeWithoutShell.startsWith("/")) {
      routeWithoutShell = routeWithoutShell.substring(1);
    }
    if (routeWithoutShell.contains("/")) {
      routeWithoutShell = routeWithoutShell.substring(routeWithoutShell.indexOf("/"));
      if (routeWithoutShell.contains("/:")) {
        routeWithoutShell = routeWithoutShell.substring(0,
                                                        routeWithoutShell.indexOf("/:"));
      }
      return routeWithoutShell;
    } else {
      return "/";
    }
  }

  private String getShellFromRoute(String route) {
    String shellOfRoute = route;
    if (shellOfRoute.startsWith("/")) {
      shellOfRoute = shellOfRoute.substring(1);
    }
    if (shellOfRoute.contains("/")) {
      shellOfRoute = shellOfRoute.substring(0,
                                            shellOfRoute.indexOf("/"));
    }
    return shellOfRoute;
  }

  private boolean matchRouteWithoutShell(String route) {
    for (String originalRoute : this.originalRoute) {
      String routeWithoutShell      = this.getRouteWithoutShell(originalRoute);
      String startRouteWithoutShell = this.getRouteWithoutShell(route);
      if (routeWithoutShell.equals(startRouteWithoutShell)) {
        return true;
      }
    }
    return false;
  }

}
