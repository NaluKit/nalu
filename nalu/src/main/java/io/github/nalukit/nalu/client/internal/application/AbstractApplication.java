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

package io.github.nalukit.nalu.client.internal.application;

import io.github.nalukit.nalu.client.Nalu;
import io.github.nalukit.nalu.client.application.IsApplication;
import io.github.nalukit.nalu.client.application.IsLoader;
import io.github.nalukit.nalu.client.application.event.LogEvent;
import io.github.nalukit.nalu.client.component.AlwaysLoadComposite;
import io.github.nalukit.nalu.client.component.AlwaysShowPopUp;
import io.github.nalukit.nalu.client.component.IsShell;
import io.github.nalukit.nalu.client.context.IsContext;
import io.github.nalukit.nalu.client.internal.CompositeReference;
import io.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import io.github.nalukit.nalu.client.internal.route.IsConfigurableRouter;
import io.github.nalukit.nalu.client.internal.route.RouteParser;
import io.github.nalukit.nalu.client.internal.route.RouteResult;
import io.github.nalukit.nalu.client.internal.route.RouterConfiguration;
import io.github.nalukit.nalu.client.internal.route.RouterException;
import io.github.nalukit.nalu.client.internal.route.RouterImpl;
import io.github.nalukit.nalu.client.internal.route.ShellConfiguration;
import io.github.nalukit.nalu.client.internal.validation.RouteValidation;
import io.github.nalukit.nalu.client.plugin.IsCustomAlertPresenter;
import io.github.nalukit.nalu.client.plugin.IsCustomConfirmPresenter;
import io.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin;
import io.github.nalukit.nalu.client.seo.SeoDataProvider;
import io.github.nalukit.nalu.client.tracker.IsTracker;
import org.gwtproject.event.shared.SimpleEventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * generator of the eventBus
 */
@NaluInternalUse
public abstract class AbstractApplication<C extends IsContext>
    implements IsApplication {

  /* start route */
  protected String                   startRoute;
  /* illegat route target */
  protected String                   illegalRouteTarget;
  /* Shell */
  protected IsShell                  shell;
  /* Shell Configuration */
  protected ShellConfiguration       shellConfiguration;
  /* Router Configuration */
  protected RouterConfiguration      routerConfiguration;
  /* Router */
  protected IsConfigurableRouter     router;
  /* application context */
  protected C                        context;
  /* the event bus of the application */
  protected SimpleEventBus           eventBus;
  /* plugin */
  protected IsNaluProcessorPlugin    plugin;
  /* Tracker instance */
  protected IsTracker                tracker;
  /* instance of AlwaysLoadComposite-class */
  protected AlwaysLoadComposite      alwaysLoadComposite;
  /* instance of AlwaysShowPopUp-class */
  protected AlwaysShowPopUp          alwaysShowPopUp;
  /* List of CompositeControllerReferences */
  protected List<CompositeReference> compositeReferences;
  /* Nalu Logger instance */
  protected NaluLogger<C>            naluLogger;
  /* The call counter */
  protected int                      callCounter;

  public AbstractApplication() {
    super();
    this.compositeReferences = new ArrayList<>();
  }

  @Override
  public void run(IsNaluProcessorPlugin plugin) {
    // save the plugin
    this.plugin = plugin;
    // instantiate necessary classes
    this.eventBus            = new SimpleEventBus();
    this.shellConfiguration  = new ShellConfiguration();
    this.routerConfiguration = new RouterConfiguration();
    this.alwaysLoadComposite = new AlwaysLoadComposite();
    this.alwaysShowPopUp     = new AlwaysShowPopUp();
    // set custom presenter - if available
    this.plugin.setCustomAlertPresenter(getCustomAlertPresenter());
    this.plugin.setCustomConfirmPresenter(getCustomConfirmPresenter());
    // create NaluLogger
    this.naluLogger = new NaluLogger<>();
    this.naluLogger.setEventBus(this.eventBus);
    this.naluLogger.bind();
    this.loadLoggerConfiguration();
    // set event bus in RouteParser
    RouteParser.INSTANCE.setEventBus(this.eventBus);
    // set up RoterStateEventFactory
    RouterStateEventFactory.INSTANCE.register(this.eventBus);
    // log development messages
    this.eventBus.fireEvent(LogEvent.create()
                                    .sdmOnly(true)
                                    .addMessage("=================================================================================")
                                    .addMessage("Running Nalu version: >>" + Nalu.getVersion() + "<<")
                                    .addMessage("================================================================================="));
    // log processor version
    this.logProcessorVersion();
    // load default routes!
    this.loadDefaultRoutes();
    // load illegal route target
    this.loadIllegalRouteTarget();
    // Register plugin
    SeoDataProvider.INSTANCE.register(this.plugin);
    // load everything you need to start
    this.loadShells();
    this.loadRoutes();
    this.loadFilters();
    this.loadCompositeReferences();
    // load optional tracker
    this.tracker = this.loadTrackerConfiguration();
    // initialize block factory
    BlockControllerFactory.INSTANCE.register(this.eventBus);
    // initialize popup factory
    PopUpControllerFactory.INSTANCE.register(this.eventBus);
    // create router ...
    this.router = new RouterImpl(this.plugin,
                                 this.shellConfiguration,
                                 this.routerConfiguration,
                                 this.compositeReferences,
                                 this.tracker,
                                 this.startRoute,
                                 this.illegalRouteTarget,
                                 this.hasHistory(),
                                 this.isUsingHash(),
                                 this.isUsingColonForParametersInUrl(),
                                 this.isStayOnSide());
    this.router.setAlwaysLoadComposite(this.alwaysLoadComposite);
    this.router.setAlwaysShowPopUp(this.alwaysShowPopUp);
    this.router.setEventBus(this.eventBus);
    // initialize plugin
    this.plugin.initialize(this.shellConfiguration);
    // load the shells of the application
    this.loadShellFactory();
    // load block factory
    this.loadBlockControllerFactory();
    // load popup factory
    this.loadPopUpControllerFactory();
    this.loadPopUpFilters();
    // load popup factory
    this.loadErrorPopUpController();
    // load the composite of the application
    this.loadCompositeController();
    // load the controllers of the application
    this.loadComponents();
    // load the handlers of the application
    this.loadHandlers();
    // load parameter constraints
    this.loadParameterConstraintRules();
    // handling application loading
    IsLoader<C> loader = getLoader();
    if (loader == null) {
      this.onFinishLoading();
    } else {
      loader.setContext(this.context);
      loader.setEventBus(this.eventBus);
      loader.setRouter(this.router);
      loader.load(this::onFinishLoading);
    }
  }

  protected abstract IsCustomAlertPresenter getCustomAlertPresenter();

  protected abstract IsCustomConfirmPresenter getCustomConfirmPresenter();

  protected abstract void loadLoggerConfiguration();

  protected abstract void logProcessorVersion();

  protected abstract void loadDefaultRoutes();

  protected abstract void loadIllegalRouteTarget();

  protected abstract void loadShells();

  protected abstract void loadRoutes();

  protected abstract void loadFilters();

  protected abstract void loadCompositeReferences();

  protected abstract IsTracker loadTrackerConfiguration();

  protected abstract boolean hasHistory();

  protected abstract boolean isUsingHash();

  protected abstract boolean isUsingColonForParametersInUrl();

  protected abstract boolean isStayOnSide();

  protected abstract void loadShellFactory();

  protected abstract void loadBlockControllerFactory();

  protected abstract void loadPopUpControllerFactory();

  protected abstract void loadPopUpFilters();

  protected abstract void loadErrorPopUpController();

  protected abstract void loadCompositeController();

  protected abstract void loadComponents();

  protected abstract void loadHandlers();

  protected abstract void loadParameterConstraintRules();

  protected abstract IsLoader<C> getLoader();

  protected abstract IsLoader<C> getPostLoader();

  /**
   * Once the loader did his job, we will continue
   */
  private void onFinishLoading() {
    // load modules, now we have started everything, it's time to deal with modules ...
    this.loadModules();
  }

  protected abstract void loadModules();

  protected void handleSuccess() {
    callCounter--;
    if (callCounter == 0) {
      this.onFinishModuleLoading();
    }
  }

  /**
   * Once the loader did his job, we will execute the post loader
   */
  protected void onFinishModuleLoading() {
    // validate start route
    if (!RouteValidation.validateStartRoute(this.shellConfiguration,
                                            this.routerConfiguration,
                                            this.startRoute)) {
      String sb = "value of start route >>" + this.startRoute + "<< does not exist!";
      eventBus.fireEvent(LogEvent.create()
                                 .sdmOnly(false)
                                 .addMessage(sb));
      this.plugin.alert("startRoute not valid - application stopped!");
      return;
    }
    // now, let's execute the 'postloader'
    // handling application loading
    IsLoader<C> postLoader = getPostLoader();
    if (postLoader == null) {
      this.onFinishPostLoading();
    } else {
      postLoader.setContext(this.context);
      postLoader.setEventBus(this.eventBus);
      postLoader.setRouter(this.router);
      postLoader.load(this::onFinishPostLoading);
    }
  }

  /**
   * Once the post loader did his job, we will start the application
   */
  protected void onFinishPostLoading() {
    // save the current hash
    String hashOnStart = this.plugin.getStartRoute();
    // check if the url contains a hash.
    // in case it has a hash, use this to route otherwise
    // use the startRoute from the annotation
    if (hashOnStart != null &&
        hashOnStart.trim()
                   .length() > 0) {
      RouteResult routeResult;
      try {
        routeResult = this.router.parse(hashOnStart);
      } catch (RouterException e) {
        this.router.handleRouterException(this.startRoute,
                                          e);
        return;
      }
      this.router.route(routeResult.getRoute(),
                        routeResult.getParameterValues()
                                   .toArray(new String[0]));
    } else {
      this.router.route(this.startRoute);
    }
  }

}
