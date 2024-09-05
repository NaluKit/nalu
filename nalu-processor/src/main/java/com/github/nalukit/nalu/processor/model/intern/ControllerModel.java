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

package com.github.nalukit.nalu.processor.model.intern;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControllerModel {

  private       String[]                               originalRoute;
  private       List<String>                           parameters;
  private       ClassNameModel                         provider;
  private final List<ParameterAcceptorModel>           parameterAcceptors;
  private       boolean                                componentCreator;
  private       List<String>                           route;
  private       String                                 selector;
  private       ClassNameModel                         context;
  private       ClassNameModel                         controller;
  private       ClassNameModel                         componentInterface;
  private       ClassNameModel                         component;
  private       List<ShellAndControllerCompositeModel> composites;
  private       List<EventHandlerModel>                eventHandlers;
  private       List<EventModel>                       eventModels;

  public ControllerModel() {
    this.parameterAcceptors = new ArrayList<>();
    this.composites         = new ArrayList<>();
    this.eventHandlers      = new ArrayList<>();
    this.eventModels        = new ArrayList<>();
  }

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
    this();

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
  }

  public ControllerModel(String[] originalRoute,
                         List<String> route,
                         String selector,
                         List<String> parameters,
                         ClassNameModel context,
                         ClassNameModel controller,
                         ClassNameModel componentInterface,
                         ClassNameModel component,
                         ClassNameModel provider,
                         boolean componentCreator,
                         List<EventHandlerModel> eventHandlers,
                         List<EventModel> eventModels) {
    this();

    this.originalRoute      = originalRoute;
    this.parameters         = parameters;
    this.provider           = provider;
    this.componentCreator   = componentCreator;
    this.route              = route;
    this.selector           = selector;
    this.context            = context;
    this.controller         = controller;
    this.componentInterface = componentInterface;
    this.component          = component;
    this.eventHandlers      = eventHandlers;
    this.eventModels        = eventModels;
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

  public List<ShellAndControllerCompositeModel> getComposites() {
    return composites;
  }

  public void setComposites(List<ShellAndControllerCompositeModel> composites) {
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

  public List<EventHandlerModel> getEventHandlers() {
    return eventHandlers;
  }

  public void setEventHandlers(List<EventHandlerModel> eventHandlers) {
    this.eventHandlers = eventHandlers;
  }

  public List<EventModel> getEventModels() {
    return eventModels;
  }

  public void setEventModels(List<EventModel> eventModels) {
    this.eventModels = eventModels;
  }

  public EventModel getEventModel(String className) {
    return this.getEventModels()
               .stream()
               .filter(c -> c.getEvent()
                             .getClassName()
                             .equals(className))
               .findFirst()
               .orElse(null);
  }

}
