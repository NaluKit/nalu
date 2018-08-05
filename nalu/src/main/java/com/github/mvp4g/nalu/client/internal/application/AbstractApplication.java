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

package com.github.mvp4g.nalu.client.internal.application;

import com.github.mvp4g.nalu.client.Router;
import com.github.mvp4g.nalu.client.application.IsApplication;
import com.github.mvp4g.nalu.client.application.IsApplicationLoader;
import com.github.mvp4g.nalu.client.application.IsContext;
import com.github.mvp4g.nalu.client.application.IsLogger;
import com.github.mvp4g.nalu.client.application.annotation.Debug;
import com.github.mvp4g.nalu.client.internal.annotation.NaluInternalUse;
import com.github.mvp4g.nalu.client.internal.route.HashResult;
import com.github.mvp4g.nalu.client.internal.route.RouterConfiguration;
import com.github.mvp4g.nalu.client.ui.IsShellController;
import elemental2.dom.DomGlobal;

/**
 * generator of the eventBus
 */
@NaluInternalUse
public abstract class AbstractApplication<C extends IsContext>
  implements IsApplication {

  /* start route */
  protected String              startRoute;
  /* Shell */
  protected IsShellController   shell;
  /* Router */
  protected RouterConfiguration routerConfiguration;
  /* Router */
  protected Router              router;
  /* application context */
  protected C                   context;
  //  protected E                                  eventBus;
//  /* flag if we have to check history token at the start of the application */
//  protected boolean                            historyOnStart;
//  /* flag if we have to enode the history token */
//  protected boolean                            encodeToken;
//  /* the PlaceService */
//  private   PlaceService<? extends IsEventBus> placeService;
  /* debug enabled? */
  private   boolean             debugEnabled = false;
  /* logger */
  private   IsLogger            logger;
  /* log level */
  private   Debug.LogLevel      logLevel;

  public AbstractApplication() {
    super();
  }

  @Override
  public void run() {
    // instantiate necessary classes
    this.routerConfiguration = new RouterConfiguration();
    this.router = new Router(this.routerConfiguration);
    //load everything you need to start
    this.loadDebugConfiguration();
    this.loadSelectors();
    this.loadRoutes();
    this.loadStartRoute();
    // load the components
    this.loadComponents();
    // set debug
    this.router.setDebug(this.debugEnabled,
                         this.logger,
                         this.logLevel);
    // execute the loader (if one is present)
    getApplicationLoader().load(this::onFinishLaoding);
  }

  protected abstract void loadDebugConfiguration();

  protected abstract void loadSelectors();

  protected abstract void loadRoutes();

  protected abstract void loadStartRoute();

  protected abstract void loadComponents();

  protected abstract IsApplicationLoader getApplicationLoader();

//  public RouterConfiguration getRouter() {
//    return routerConfiguration;
//  }

  /**
   * Once the loader did his job, we will continue
   */
  private void onFinishLaoding() {
    // save the current hash
    String hashOnStart = DomGlobal.window.location.getHash();
    if (hashOnStart.startsWith("#")) {
      if (hashOnStart.length() > 1) {
        hashOnStart = hashOnStart.substring(1);
      }
    }
    // initialize shell ...
    this.setShell();
    // start the application by calling url + '#'
    this.router.route("");
    // check if the url contains a hash.
    // in case it has a hash, use this to route otherwise
    // use the startRoute form the annoatation
    if (hashOnStart != null && hashOnStart.trim()
                                          .length() > 0) {
      HashResult hashResult = this.router.parse(hashOnStart);
      this.router.route(hashResult.getRoute(),
                        hashResult.getParameterValues()
                                  .toArray(new String[0]));
    } else {
      this.router.route(this.startRoute);
    }

//    // create place service and bind
//    this.placeService = new PlaceService<E>(this.eventBus,
//                                            new DefaultHistoryProxyImpl(),
//                                            historyOnStart,
//                                            encodeToken);
//    this.eventBus.setPlaceService(this.placeService);
//    // start the application
//    placeService.startApplication();
  }

  protected abstract void setShell();

  /**
   * Get the logger for the applicaiton
   *
   * @return logger
   */
  protected IsLogger getLogger() {
    return logger;
  }

  /**
   * Sets the logger
   *
   * @param logger logger
   */
  protected void setLogger(IsLogger logger) {
    this.logger = logger;
  }

  /**
   * gets the log level
   *
   * @return the selected log level
   */
  public Debug.LogLevel getLogLevel() {
    return logLevel;
  }

  /**
   * Set the log level
   *
   * @param logLevel the new log level
   */
  protected void setLogLevel(Debug.LogLevel logLevel) {
    this.logLevel = logLevel;
  }

  /**
   * set the debug state
   *
   * @param debugEnabled true - is enable
   */
  protected void setDebugEnabled(boolean debugEnabled) {
    this.debugEnabled = debugEnabled;
  }

}
