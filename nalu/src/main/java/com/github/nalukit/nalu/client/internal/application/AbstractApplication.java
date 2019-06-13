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

package com.github.nalukit.nalu.client.internal.application;

import com.github.nalukit.nalu.client.Nalu;
import com.github.nalukit.nalu.client.application.IsApplication;
import com.github.nalukit.nalu.client.application.IsApplicationLoader;
import com.github.nalukit.nalu.client.application.IsContext;
import com.github.nalukit.nalu.client.component.AlwaysLoadComposite;
import com.github.nalukit.nalu.client.component.IsShell;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.CompositeControllerReference;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import com.github.nalukit.nalu.client.internal.route.*;
import com.github.nalukit.nalu.client.internal.validation.RouteValidation;
import com.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin;
import com.github.nalukit.nalu.client.tracker.IsTracker;
import org.gwtproject.event.shared.SimpleEventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * generator of the eventBus
 */
@NaluInternalUse
public abstract class AbstractApplication<C extends IsContext>
    implements IsApplication {

  private final static String NO_ROUTE = "WhenShallWeThreeMeetAgainInThunderLightningOrInRain";

  /* start route */
  protected String                             startRoute;

  /* route in case of route error */
  protected String                             errorRoute;

  /* Shell */
  protected IsShell                            shell;

  /* Shell Configuration */
  protected ShellConfiguration                 shellConfiguration;

  /* Router Configuration */
  protected RouterConfiguration                routerConfiguration;

  /* Router */
  protected ConfiguratableRouter               router;

  /* application context */
  protected C                                  context;

  /* the event bus of the application */
  protected SimpleEventBus                     eventBus;

  /* plugin */
  protected IsNaluProcessorPlugin              plugin;

  /* Tracker instance */
  protected IsTracker                          tracker;

  /* instance of AlwaysLoadComposite-class */
  protected AlwaysLoadComposite                alwaysLoadComposite;

  /* List of CompositeControllerReferences */
  protected List<CompositeControllerReference> compositeControllerReferences;

  public AbstractApplication() {
    super();
    this.compositeControllerReferences = new ArrayList<>();
  }

  @Override
  public void run(IsNaluProcessorPlugin plugin) {
    // save the plugin
    this.plugin = plugin;
    // first load the debug configuration
    this.loadDebugConfiguration();
    // debug message
    ClientLogger.get()
                .logDetailed("=================================================================================",
                             0);
    ClientLogger.get()
                .logDetailed("Running Nalu version: >>" + Nalu.getVersion() + "<<",
                             0);
    ClientLogger.get()
                .logDetailed("=================================================================================",
                             0);
    // log processsor version
    this.logProcessorVersion();
    ClientLogger.get()
                .logDetailed("",
                             0);
    // debug message
    ClientLogger.get()
                .logSimple("AbstractApplication: application is started!",
                           0);
    // instantiate necessary classes
    this.eventBus = new SimpleEventBus();
    this.shellConfiguration = new ShellConfiguration();
    this.routerConfiguration = new RouterConfiguration();
    this.alwaysLoadComposite = new AlwaysLoadComposite();
    // initialize popup factory
    PopUpControllerFactory.get()
                          .register(this.eventBus);
    // initialize plugin
    this.plugin.initialize(this.shellConfiguration);
    // load optional tracker
    this.tracker = this.loadTrackerConfiguration();
    // load default routes!
    this.loadDefaultRoutes();
    // create router ...
    this.router = new RouterImpl(this.plugin,
                                 this.shellConfiguration,
                                 this.routerConfiguration,
                                 this.compositeControllerReferences,
                                 this.tracker,
                                 this.startRoute,
                                 this.hasHistory(),
                                 this.isUsingHash(),
                                 this.isUsingColonForParametersInUrl(),
                                 this.isStayOnSide());
    // load everything you need to start
    ClientLogger.get()
                .logDetailed("AbstractApplication: load configurations",
                             1);
    this.loadPlugins();
    this.loadShells();
    this.loadRoutes();
    this.loadFilters();
    this.loadCompositeReferences();
    this.loadPopUpControllerFactory();
    this.router.setRouteError(AbstractApplication.NO_ROUTE.equals(this.errorRoute) ? null : this.errorRoute);
    // load the shells of the application
    ClientLogger.get()
                .logDetailed("AbstractApplication: load shells",
                             1);
    this.loadShellFactory();
    // load the composite of the application
    ClientLogger.get()
                .logDetailed("AbstractApplication: load compositeControllers",
                             1);
    this.loadCompositeController();
    // load the controllers of the application
    ClientLogger.get()
                .logDetailed("AbstractApplication: load components",
                             1);
    this.loadComponents();
    // load the handlers fo the application
    ClientLogger.get()
                .logDetailed("AbstractApplication: load handlers",
                             1);
    this.loadHandlers();
    // execute the loader (if one is present)
    ClientLogger.get()
                .logDetailed("AbstractApplication: execute loader",
                             1);
    // validate
    if (!RouteValidation.validateStartRoute(this.shellConfiguration,
                                            this.routerConfiguration,
                                            this.startRoute)) {
      this.plugin.alert("startRoute not valid - application stopped!");
      return;
    }
    if (!RouteValidation.validateRouteError(this.shellConfiguration,
                                            this.routerConfiguration,
                                            this.errorRoute)) {
      this.plugin.alert("routeError not valid - application stopped!");
      return;
    }
    // handling application loading
    IsApplicationLoader<C> applicationLoader = getApplicationLoader();
    if (getApplicationLoader() == null) {
      this.onFinishLoading();
    } else {
      applicationLoader.setContext(this.context);
      applicationLoader.setEventBus(this.eventBus);
      applicationLoader.setRouter(this.router);
      applicationLoader.load(this::onFinishLoading);
    }
  }

  protected abstract void logProcessorVersion();

  protected abstract void loadPlugins();

  protected abstract void loadShellFactory();

  protected abstract void loadDebugConfiguration();

  protected abstract IsTracker loadTrackerConfiguration();

  protected abstract void loadShells();

  protected abstract void loadRoutes();

  protected abstract void loadFilters();

  protected abstract void loadDefaultRoutes();

  protected abstract void loadCompositeReferences();

  protected abstract void loadCompositeController();

  protected abstract void loadComponents();

  protected abstract void loadHandlers();

  protected abstract void loadPopUpControllerFactory();

  protected abstract IsApplicationLoader<C> getApplicationLoader();

  protected abstract boolean hasHistory();

  protected abstract boolean isUsingHash();

  protected abstract boolean isUsingColonForParametersInUrl();

  protected abstract boolean isStayOnSide();

  /**
   * Once the loader did his job, we will continue
   */
  private void onFinishLoading() {
    // save the current hash
    String hashOnStart = this.plugin.getStartRoute();
    // check if the url contains a hash.
    // in case it has a hash, use this to route otherwise
    // use the startRoute from the annotation
    if (hashOnStart != null &&
        hashOnStart.trim()
                   .length() > 0) {
      ClientLogger.get()
                  .logDetailed("AbstractApplication: handle history (hash at start: >>" + hashOnStart + "<<",
                               1);
      RouteResult routeResult;
      try {
        routeResult = this.router.parse(hashOnStart);
      } catch (RouterException e) {
        return;
      }
      this.router.route(routeResult.getRoute(),
                        routeResult.getParameterValues()
                                   .toArray(new String[0]));
    } else {
      ClientLogger.get()
                  .logDetailed("AbstractApplication: no history found -> use startRoute: >>" + this.startRoute + "<<",
                               1);
      this.router.route(this.startRoute);
    }
    ClientLogger.get()
                .logSimple("AbstractApplication: application started",
                           0);
  }

}
