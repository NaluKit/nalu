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

package com.github.nalukit.nalu.processor.model;

import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;
import com.github.nalukit.nalu.processor.model.intern.CompositeModel;
import com.github.nalukit.nalu.processor.model.intern.ControllerModel;
import com.github.nalukit.nalu.processor.model.intern.ShellModel;

import java.util.ArrayList;
import java.util.List;

public class ApplicationMetaModel {

  private String generateToPackage;

  private ClassNameModel application;

  private ClassNameModel loader;

  private List<ShellModel> shells;

  private ClassNameModel context;

  private String startRoute;

  private String routeErrorRoute;

  private boolean havingDebugAnnotation;

  private String debugLogLevel;

  private ClassNameModel debugLogger;

  private List<ControllerModel> routes;

  private String hasFiltersAnnotation;

  private List<ClassNameModel> filters;

  private List<ClassNameModel> handlers;

  private ClassNameModel componentType;

  private List<CompositeModel> compositeModels;

  public ApplicationMetaModel() {
  }

  public ApplicationMetaModel(String generateToPackage,
                              String application,
                              String loader,
                              String context,
                              String startRoute,
                              String routeErrorRoute) {
    this.generateToPackage = generateToPackage;
    this.application = new ClassNameModel(application);
    this.loader = new ClassNameModel(loader);
    this.context = new ClassNameModel(context);
    this.startRoute = startRoute;
    this.routeErrorRoute = routeErrorRoute;

    this.shells = new ArrayList<>();
    this.routes = new ArrayList<>();
    this.filters = new ArrayList<>();
    this.handlers = new ArrayList<>();
    this.compositeModels = new ArrayList<>();
  }

  public ClassNameModel getApplication() {
    return application;
  }

  public void setApplication(ClassNameModel application) {
    this.application = application;
  }

  public ClassNameModel getLoader() {
    return loader;
  }

  public void setLoader(ClassNameModel loader) {
    this.loader = loader;
  }

  public boolean isHavingDebugAnnotation() {
    return havingDebugAnnotation;
  }

  public void setHavingDebugAnnotation(boolean havingDebugAnnotation) {
    this.havingDebugAnnotation = havingDebugAnnotation;
  }

  public String getDebugLogLevel() {
    if (debugLogLevel == null || "".equals(debugLogLevel)) {
      return "SIMPLE";
    }
    return debugLogLevel;
  }

  public void setDebugLogLevel(String debugLogLevel) {
    this.debugLogLevel = debugLogLevel;
  }

  public String getGenerateToPackage() {
    return generateToPackage;
  }

  public void setGenerateToPackage(String generateToPackage) {
    this.generateToPackage = generateToPackage;
  }

  public ClassNameModel getContext() {
    return context;
  }

  public void setContext(ClassNameModel context) {
    this.context = context;
  }

  public String getStartRoute() {
    return startRoute;
  }

  public void setStartRoute(String startRoute) {
    this.startRoute = startRoute;
  }

  public ClassNameModel getDebugLogger() {
    return debugLogger;
  }

  public void setDebugLogger(ClassNameModel debugLogger) {
    this.debugLogger = debugLogger;
  }

  public List<ControllerModel> getController() {
    return routes;
  }

  public void setRoutes(List<ControllerModel> routes) {
    this.routes = routes;
  }

  public String getHasFiltersAnnotation() {
    return hasFiltersAnnotation;
  }

  public void setHasFiltersAnnotation(String hasFiltersAnnotation) {
    this.hasFiltersAnnotation = hasFiltersAnnotation;
  }

  public List<ClassNameModel> getFilters() {
    return filters;
  }

  public void setFilters(List<ClassNameModel> filters) {
    this.filters = filters;
  }

  public List<ClassNameModel> getHandlers() {
    return handlers;
  }

  public void setHandlers(List<ClassNameModel> handlers) {
    this.handlers = handlers;
  }

  public ClassNameModel getComponentType() {
    return componentType;
  }

  public void setComponentType(ClassNameModel componentType) {
    this.componentType = componentType;
  }

  public String getRouteErrorRoute() {
    return routeErrorRoute;
  }

  public void setRouteErrorRoute(String routeErrorRoute) {
    this.routeErrorRoute = routeErrorRoute;
  }

  public List<CompositeModel> getCompositeModels() {
    return compositeModels;
  }

  public void setCompositeModels(List<CompositeModel> compositeModels) {
    this.compositeModels = compositeModels;
  }

  public List<ShellModel> getShells() {
    return shells;
  }

  public String getShellOfStartRoute() {
    String shellOfStartRoute = this.startRoute;
    if (shellOfStartRoute.startsWith("/")) {
      shellOfStartRoute = shellOfStartRoute.substring(1);
    }
    if (shellOfStartRoute.contains("/")) {
      shellOfStartRoute = shellOfStartRoute.substring(0,
                                                      shellOfStartRoute.indexOf("/"));
    }
    return shellOfStartRoute;
  }
}
