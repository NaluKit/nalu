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

package com.github.mvp4g.nalu.processor.model;

import com.github.mvp4g.nalu.processor.model.intern.ClassNameModel;
import com.github.mvp4g.nalu.processor.model.intern.ProvidesSelectorModel;
import com.github.mvp4g.nalu.processor.model.intern.ControllerModel;

import java.util.ArrayList;
import java.util.List;

public class ApplicationMetaModel {

  private static final String KEY_SELECTORS = "selectors";

  private ClassNameModel application;
  private ClassNameModel loader;
  private ClassNameModel shell;
  private ClassNameModel context;
  private String         startRoute;


  private boolean        havingDebugAnnotation;
  private String         debugLogLevel;
  private ClassNameModel debugLogger;


  private List<ControllerModel>       routes;
  private List<ProvidesSelectorModel> selectors;


  public ApplicationMetaModel() {
  }

  public ApplicationMetaModel(String application,
                              String loader,
                              String shell,
                              String context,
                              String startRoute) {
    this.application = new ClassNameModel(application);
    this.loader = new ClassNameModel(loader);
    this.shell = new ClassNameModel(shell);
    this.context = new ClassNameModel(context);
    this.startRoute = startRoute;

    this.routes = new ArrayList<>();
    this.selectors = new ArrayList<>();
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

  public ClassNameModel getShell() {
    return shell;
  }

  public void setShell(ClassNameModel shell) {
    this.shell = shell;
  }

  public boolean isHavingDebugAnnotation() {
    return havingDebugAnnotation;
  }

  public void setHavingDebugAnnotation(boolean havingDebugAnnotation) {
    this.havingDebugAnnotation = havingDebugAnnotation;
  }

  public String getDebugLogLevel() {
    return debugLogLevel;
  }

  public void setDebugLogLevel(String debugLogLevel) {
    this.debugLogLevel = debugLogLevel;
  }

  public ClassNameModel getDebugLogger() {
    return debugLogger;
  }

  public void setDebugLogger(ClassNameModel debugLogger) {
    this.debugLogger = debugLogger;
  }

  public List<ProvidesSelectorModel> getSelectors() {
    return selectors;
  }

  public void setSelectors(List<ProvidesSelectorModel> selectors) {
    this.selectors = selectors;
  }

  public List<ControllerModel> getRoutes() {
    return routes;
  }

  public void setRoutes(List<ControllerModel> routes) {
    this.routes = routes;
  }

  public String getStartRoute() {
    return startRoute;
  }

  public void setStartRoute(String startRoute) {
    this.startRoute = startRoute;
  }

  public ClassNameModel getContext() {
    return context;
  }

  public void setContext(ClassNameModel context) {
    this.context = context;
  }
}
