/*
 * Copyright (c) 2018 - Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *
 */

package com.github.nalukit.nalu.processor.model.intern;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControllerModel {

  private String originalRoute;

  private String route;

  private String selector;

  private List<String> parameters;

  private ClassNameModel controller;

  private ClassNameModel componentInterface;

  private ClassNameModel component;

  private ClassNameModel provider;

  private ClassNameModel componentType;

  private List<ParameterAcceptor> parameterAcceptors;

  private List<ControllerSplitterModel> splitters;

  public ControllerModel(String originalRoute,
                         String route,
                         String selector,
                         List<String> parameters,
                         ClassNameModel controller,
                         ClassNameModel componentInterface,
                         ClassNameModel component,
                         ClassNameModel componentType,
                         ClassNameModel provider) {
    this.originalRoute = originalRoute;
    this.route = route;
    this.selector = selector;
    this.parameters = parameters;
    this.controller = controller;
    this.componentInterface = componentInterface;
    this.component = component;
    this.provider = provider;
    this.componentType = componentType;

    this.parameterAcceptors = new ArrayList<>();
    this.splitters = new ArrayList<>();
  }

  public String getOriginalRoute() {
    return originalRoute;
  }

  public void setOriginalRoute(String originalRoute) {
    this.originalRoute = originalRoute;
  }

  public String getRoute() {
    return route;
  }

  public void setRoute(String route) {
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

  public void setParameters(List<String> parameters) {
    this.parameters = parameters;
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

  public void setProvider(ClassNameModel provider) {
    this.provider = provider;
  }

  public ClassNameModel getComponentType() {
    return componentType;
  }

  public void setComponentType(ClassNameModel componentType) {
    this.componentType = componentType;
  }

  public List<ParameterAcceptor> getParameterAcceptors() {
    return parameterAcceptors;
  }

  public String getParameterAcceptors(String parameterName) {
    Optional<ParameterAcceptor> optional = this.parameterAcceptors.stream()
                                                                  .filter(a -> parameterName.equals(a.getParameterName()))
                                                                  .findFirst();
    return optional.map(ParameterAcceptor::getMethodName)
                   .orElse(null);
  }

  public List<ControllerSplitterModel> getSplitters() {
    return splitters;
  }

  public void setSplitters(List<ControllerSplitterModel> splitters) {
    this.splitters = splitters;
  }
}
