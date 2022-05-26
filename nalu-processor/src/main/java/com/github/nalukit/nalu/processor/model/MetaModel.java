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

package com.github.nalukit.nalu.processor.model;

import com.github.nalukit.nalu.processor.model.intern.BlockControllerModel;
import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;
import com.github.nalukit.nalu.processor.model.intern.CompositeModel;
import com.github.nalukit.nalu.processor.model.intern.ControllerModel;
import com.github.nalukit.nalu.processor.model.intern.ErrorPopUpControllerModel;
import com.github.nalukit.nalu.processor.model.intern.EventHandlerModel;
import com.github.nalukit.nalu.processor.model.intern.EventModel;
import com.github.nalukit.nalu.processor.model.intern.FilterModel;
import com.github.nalukit.nalu.processor.model.intern.HandlerModel;
import com.github.nalukit.nalu.processor.model.intern.ModuleModel;
import com.github.nalukit.nalu.processor.model.intern.ParameterConstraintRuleModel;
import com.github.nalukit.nalu.processor.model.intern.PopUpControllerModel;
import com.github.nalukit.nalu.processor.model.intern.ShellModel;
import com.github.nalukit.nalu.processor.model.intern.TrackerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MetaModel {

  private String                             generateToPackage;
  private ClassNameModel                     application;
  private ClassNameModel                     loader;
  private ClassNameModel                     postLoader;
  private ClassNameModel                     customAlertPresenter;
  private ClassNameModel                     customConfirmPresenter;
  private List<ClassNameModel>               modules;
  private boolean                            usingHash;
  private boolean                            usingColonForParametersInUrl;
  private boolean                            stayOnSide;
  /* this model represents the plugin interface */
  /* is the model not null, we have to deal     */
  /* with a a plugin and not with application  */
  private ModuleModel                        moduleModel;
  private List<ShellModel>                   shells;
  private ClassNameModel                     context;
  private String                             startRoute;
  private String                             illegalRouteTarget;
  private ErrorPopUpControllerModel          errorPopUpController;
  private boolean                            hasPluginsAnnotation;
  private boolean                            hasLoggerAnnotation;
  private List<ControllerModel>              controllers;
  private List<BlockControllerModel>         blockControllers;
  private List<ParameterConstraintRuleModel> parameterConstraintRules;
  private List<PopUpControllerModel>         popUpControllers;
  private TrackerModel                       tracker;
  private List<FilterModel>                  filters;
  private List<ClassNameModel>               popUpFilters;
  private List<HandlerModel>                 handlers;
  private ClassNameModel                     componentType;
  private List<CompositeModel>               compositeModels;
  private ClassNameModel                     logger;
  private ClassNameModel                     clientLogger;
  /* flag, that indicates, if a Nalu application */
  /* uses a history token or not.                */
  private boolean                            history;
  /* Version of the applicaiton set by the       */
  /* Version annotation                          */
  private String                             applicationVersion;
  /* does the context extends                    */
  /* AbstractModuleContext?                      */
  private boolean                            extendingIsModuleContext;
  /* do we have to remove the URL parameter      */
  /* from the URL                                */
  private boolean                            removeUrlParameterAtStart;

  public MetaModel() {
    this.modules                  = new ArrayList<>();
    this.shells                   = new ArrayList<>();
    this.controllers              = new ArrayList<>();
    this.blockControllers         = new ArrayList<>();
    this.popUpControllers         = new ArrayList<>();
    this.filters                  = new ArrayList<>();
    this.popUpFilters             = new ArrayList<>();
    this.handlers                 = new ArrayList<>();
    this.compositeModels          = new ArrayList<>();
    this.parameterConstraintRules = new ArrayList<>();

    this.applicationVersion        = "APPLCIATION-VERSION-NOT-AVAILABLE";
    this.extendingIsModuleContext  = false;
    this.removeUrlParameterAtStart = false;
  }

  public ModuleModel getModuleModel() {
    return moduleModel;
  }

  public void setModuleModel(ModuleModel moduleModel) {
    this.moduleModel = moduleModel;
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

  public ClassNameModel getPostLoader() {
    return postLoader;
  }

  public void setPostLoader(ClassNameModel postLoader) {
    this.postLoader = postLoader;
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

  public List<ControllerModel> getControllers() {
    return controllers;
  }

  public void setControllers(List<ControllerModel> controllers) {
    this.controllers = controllers;
  }

  public List<BlockControllerModel> getBlockControllers() {
    return blockControllers;
  }

  public void setBlockControllers(List<BlockControllerModel> blockControllers) {
    this.blockControllers = blockControllers;
  }

  public List<PopUpControllerModel> getPopUpControllers() {
    return popUpControllers;
  }

  public void setPopUpControllers(List<PopUpControllerModel> popUpControllers) {
    this.popUpControllers = popUpControllers;
  }

  public List<FilterModel> getFilters() {
    return filters;
  }

  public void setFilters(List<FilterModel> filters) {
    this.filters = filters;
  }

  public List<HandlerModel> getHandlers() {
    return handlers;
  }

  public void setHandlers(List<HandlerModel> handlers) {
    this.handlers = handlers;
  }

  public ClassNameModel getComponentType() {
    return componentType;
  }

  public void setComponentType(ClassNameModel componentType) {
    this.componentType = componentType;
  }

  public ErrorPopUpControllerModel getErrorPopUpController() {
    return errorPopUpController;
  }

  public void setErrorPopUpController(ErrorPopUpControllerModel errorPopUpController) {
    this.errorPopUpController = errorPopUpController;
  }

  public List<CompositeModel> getCompositeModels() {
    return compositeModels;
  }

  public List<ShellModel> getShells() {
    return shells;
  }

  public List<ClassNameModel> getModules() {
    return modules;
  }

  public boolean isUsingHash() {
    return usingHash;
  }

  public void setUsingHash(boolean usingHash) {
    this.usingHash = usingHash;
  }

  public boolean isUsingColonForParametersInUrl() {
    return usingColonForParametersInUrl;
  }

  public void setUsingColonForParametersInUrl(boolean usingColonForParametersInUrl) {
    this.usingColonForParametersInUrl = usingColonForParametersInUrl;
  }

  public boolean isStayOnSide() {
    return stayOnSide;
  }

  public void setStayOnSide(boolean stayOnSide) {
    this.stayOnSide = stayOnSide;
  }

  public ClassNameModel getCustomAlertPresenter() {
    return customAlertPresenter;
  }

  public void setCustomAlertPresenter(ClassNameModel customAlertPresenter) {
    this.customAlertPresenter = customAlertPresenter;
  }

  public ClassNameModel getCustomConfirmPresenter() {
    return customConfirmPresenter;
  }

  public void setCustomConfirmPresenter(ClassNameModel customConfirmPresenter) {
    this.customConfirmPresenter = customConfirmPresenter;
  }

  public List<ClassNameModel> getPopUpFilters() {
    return popUpFilters;
  }

  public void setPopUpFilters(List<ClassNameModel> popUpFilters) {
    this.popUpFilters = popUpFilters;
  }

  public String getShellOfStartRoute() {
    if (Objects.isNull(this.startRoute)) {
      return "";
    }
    return getShellFromRoute(this.startRoute);
  }

  private String getShellFromRoute(String route) {
    String shellFromRoute = route;
    if (shellFromRoute.startsWith("/")) {
      shellFromRoute = shellFromRoute.substring(1);
    }
    if (shellFromRoute.contains("/")) {
      shellFromRoute = shellFromRoute.substring(0,
                                                shellFromRoute.indexOf("/"));
    }
    return shellFromRoute;
  }

  public boolean hasHistory() {
    return history;
  }

  public void setHistory(boolean history) {
    this.history = history;
  }

  public ClassNameModel getLogger() {
    return logger;
  }

  public void setLogger(ClassNameModel logger) {
    this.logger = logger;
  }

  public ClassNameModel getClientLogger() {
    return clientLogger;
  }

  public void setClientLogger(ClassNameModel clientLogger) {
    this.clientLogger = clientLogger;
  }

  public String getApplicationVersion() {
    return applicationVersion;
  }

  public void setApplicationVersion(String applicationVersion) {
    this.applicationVersion = applicationVersion;
  }

  public boolean isExtendingIsModuleContext() {
    return extendingIsModuleContext;
  }

  public void setExtendingIsModuleContext(boolean extendingIsModuleContext) {
    this.extendingIsModuleContext = extendingIsModuleContext;
  }

  public String getIllegalRouteTarget() {
    return illegalRouteTarget;
  }

  public void setIllegalRouteTarget(String illegalRouteTarget) {
    this.illegalRouteTarget = illegalRouteTarget;
  }

  public boolean isRemoveUrlParameterAtStart() {
    return removeUrlParameterAtStart;
  }

  public void setRemoveUrlParameterAtStart(boolean removeUrlParameterAtStart) {
    this.removeUrlParameterAtStart = removeUrlParameterAtStart;
  }

  public List<ParameterConstraintRuleModel> getParameterConstraintRules() {
    return parameterConstraintRules;
  }

  public void setParameterConstraintRules(List<ParameterConstraintRuleModel> parameterConstraintRules) {
    this.parameterConstraintRules = parameterConstraintRules;
  }

  public TrackerModel getTracker() {
    return tracker;
  }

  public void setTracker(TrackerModel tracker) {
    this.tracker = tracker;
  }
}
