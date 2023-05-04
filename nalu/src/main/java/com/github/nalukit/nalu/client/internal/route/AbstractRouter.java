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

package com.github.nalukit.nalu.client.internal.route;

import com.github.nalukit.nalu.client.NaluConstants;
import com.github.nalukit.nalu.client.application.event.LogEvent;
import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.AbstractCompositeController;
import com.github.nalukit.nalu.client.component.AlwaysLoadComposite;
import com.github.nalukit.nalu.client.component.AlwaysShowPopUp;
import com.github.nalukit.nalu.client.component.IsController;
import com.github.nalukit.nalu.client.component.IsShell;
import com.github.nalukit.nalu.client.event.NaluErrorEvent;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.filter.IsFilter;
import com.github.nalukit.nalu.client.internal.CompositeReference;
import com.github.nalukit.nalu.client.internal.PropertyFactory;
import com.github.nalukit.nalu.client.internal.Utils;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import com.github.nalukit.nalu.client.internal.application.CompositeConditionFactory;
import com.github.nalukit.nalu.client.internal.application.CompositeFactory;
import com.github.nalukit.nalu.client.internal.application.CompositeInstance;
import com.github.nalukit.nalu.client.internal.application.ControllerCallback;
import com.github.nalukit.nalu.client.internal.application.ControllerFactory;
import com.github.nalukit.nalu.client.internal.application.ControllerInstance;
import com.github.nalukit.nalu.client.internal.application.RouterStateEventFactory;
import com.github.nalukit.nalu.client.internal.application.ShellCallback;
import com.github.nalukit.nalu.client.internal.application.ShellFactory;
import com.github.nalukit.nalu.client.internal.application.ShellInstance;
import com.github.nalukit.nalu.client.module.IsModule;
import com.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin;
import com.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin.ConfirmHandler;
import com.github.nalukit.nalu.client.seo.SeoDataProvider;
import com.github.nalukit.nalu.client.tracker.IsTracker;
import org.gwtproject.event.shared.SimpleEventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract class AbstractRouter
    implements IsConfigurableRouter {

  // composite configuration
  private final List<CompositeReference>        compositeReferences;
  // List of the application shells
  private final ShellConfiguration              shellConfiguration;
  // List of the routes of the application
  private final RouterConfiguration             routerConfiguration;
  // List of active components
  private final Map<String, ControllerInstance> activeComponents;
  // list of routes used for handling the current route - used to detect loops
  private final List<String>                    loopDetectionList;
  // the tracker: if not null, track the users routing
  private final IsTracker                       tracker;
  /* instance of AlwaysLoadComposite-class */
  protected     AlwaysLoadComposite             alwaysLoadComposite;
  /* instance of AlwaysShowPopUp-class */
  protected     AlwaysShowPopUp                 alwaysShowPopUp;
  // the plugin
  IsNaluProcessorPlugin plugin;
  // hash of last successful routing
  private String         lastExecutedHash = "";
  // current route
  private String         currentRoute     = "";
  // current parameters
  private String[]       currentParameters;
  // last added shell - used, to check if the shell needs an shell replacement
  private String         lastAddedShell;
  // instance of the current shell
  private IsShell        shell;
  // the application event bus
  private SimpleEventBus eventBus;
  // internal counter for callback handling
  private int            callCounter;

  AbstractRouter(List<CompositeReference> compositeReferences,
                 ShellConfiguration shellConfiguration,
                 RouterConfiguration routerConfiguration,
                 IsNaluProcessorPlugin plugin,
                 IsTracker tracker,
                 String startRoute,
                 String illegalRouteTarget,
                 boolean hasHistory,
                 boolean usingHash,
                 boolean usingColonForParametersInUrl,
                 boolean stayOnSite,
                 boolean removeUrlParameterAtStart) {
    // save the composite configuration reference
    this.compositeReferences = compositeReferences;
    // save the shell configuration reference
    this.shellConfiguration = shellConfiguration;
    // save the router configuration reference
    this.routerConfiguration = routerConfiguration;
    // save te plugin
    this.plugin = plugin;
    // save the tracker
    this.tracker = tracker;
    // instantiate lists, etc.
    this.activeComponents  = new HashMap<>();
    this.loopDetectionList = new ArrayList<>();
    // set up PropertyFactory
    PropertyFactory.get()
                   .register(startRoute,
                             illegalRouteTarget,
                             hasHistory,
                             usingHash,
                             usingColonForParametersInUrl,
                             stayOnSite,
                             removeUrlParameterAtStart);
  }

  /**
   * clears the cache
   */
  @Override
  public void clearCache() {
    ControllerFactory.INSTANCE
                     .clearControllerCache();
    CompositeFactory.INSTANCE
                    .clearCompositeControllerCache();
  }

  /**
   * Generates a new route!
   * <p>
   * If there is something to generate with parameters, the route
   * needs the same number of '*' in it.
   *
   * @param route  route to navigate to
   * @param params parameters of the route
   * @return generate String of new route
   */
  @Override
  public String generate(String route,
                         String... params) {
    return RouteParser.INSTANCE.generate(route,
                                         params);
  }

  /**
   * The method routes to another screen. In case it is called,
   * it will:
   * <ul>
   * <li>create a new hash</li>
   * <li>update the url (in case history is desired)</li>
   * </ul>
   * Once the url gets updated, it triggers the onhashchange event and Nalu starts to work
   *
   * @param newRoute routing goal
   * @param params   list of parameters [0 - n]
   */
  @Override
  public void route(String newRoute,
                    String... params) {
    // first, we track the new route (if there is a tracker!)
    if (!Objects.isNull(this.tracker)) {
      this.tracker.track(newRoute,
                         params);
    }
    // let's do the routing!
    this.route(newRoute,
               false,
               false,
               false,
               params);
  }

  /**
   * The method routes to another screen. In case it is called,
   * it will:
   * <ul>
   * <li>create a new hash</li>
   * <li>update the url (in case history is desired)</li>
   * </ul>
   * Once the url gets updated, it triggers the onhashchange event and Nalu starts to work
   * <p>
   * in opposite to the route-method, the forceRoute-method does not confirm the new route!
   *
   * @param newRoute routing goal
   * @param params   list of parameters [0 - n]
   */
  @Override
  public void forceRoute(String newRoute,
                         String... params) {
    // first, we track the new route (if there is a tracker!)
    if (!Objects.isNull(this.tracker)) {
      this.tracker.track(newRoute,
                         params);
    }
    // let's do the routing!
    this.route(newRoute,
               true,
               false,
               false,
               params);
  }

  /**
   * The method routes to another screen. In case it is called,
   * it will:
   * <ul>
   * <li>not create a new hash</li>
   * <li>not update the url (in case history is desired)</li>
   * </ul>
   * Once the url gets updated, it triggers the onhashchange event
   * and Nalu starts to work
   * <p>
   * in opposite to the route-method, the forceStealthRoute-method
   * does not confirm the new route!
   *
   * @param newRoute routing goal
   * @param params   list of parameters [0 - n]
   */
  @Override
  public void forceStealthRoute(String newRoute,
                                String... params) {
    // first, we track the new route (if there is a tracker!)
    if (!Objects.isNull(this.tracker)) {
      this.tracker.track(newRoute,
                         params);
    }
    // let's do the routing!
    this.route(newRoute,
               true,
               false,
               true,
               params);
  }

  /**
   * The method routes to another screen. In case it is called,
   * it will:
   * <ul>
   * <li>not create a new hash</li>
   * <li>not update the url (in case history is desired)</li>
   * </ul>
   * Once the url gets updated, it triggers the onhashchange event and Nalu starts to work
   *
   * @param newRoute routing goal
   * @param params   list of parameters [0 - n]
   */
  @Override
  public void stealthRoute(String newRoute,
                           String... params) {
    // first, we track the new route (if there is a tracker!)
    if (!Objects.isNull(this.tracker)) {
      this.tracker.track(newRoute,
                         params);
    }
    // let's do the routing!
    this.route(newRoute,
               false,
               false,
               true,
               params);
  }

  /**
   * The method updates the hash without doing a routing.
   * <p>
   * In case it is called, it will:
   * <ul>
   * <li>create a new hash</li>
   * <li>update the url (in case history is desired)</li>
   * <li>does not route to the new hash</li>
   * </ul>
   *
   * @param newRoute routing goal
   * @param params   list of parameters [0 - n]
   */
  @Override
  public void fakeRoute(String newRoute,
                        String... params) {
    // first, we track the new route (if there is a tracker!)
    if (!Objects.isNull(this.tracker)) {
      this.tracker.track(newRoute,
                         params);
    }
    String newRouteWithParams = this.generate(newRoute,
                                              params);
    this.plugin.route(newRouteWithParams,
                      true,
                      false);
    RouterStateEventFactory.INSTANCE.fireDoneRoutingEvent(newRoute,
                                                          params);
  }

  /**
   * Removes a controller from the cache
   *
   * @param controller controller to be removed
   * @param <C>        controller type
   */
  @Override
  public <C extends AbstractComponentController<?, ?, ?>> void removeFromCache(C controller) {
    ControllerFactory.INSTANCE
                     .removeFromCache(controller);
    controller.setCached(false);
  }

  /**
   * Removes a controller from the cache
   *
   * @param compositeController controller to be removed
   * @param <C>                 controller type
   */
  @Override
  public <C extends AbstractCompositeController<?, ?, ?>> void removeFromCache(C compositeController) {
    CompositeFactory.INSTANCE
                    .removeFromCache(compositeController);
    compositeController.setCached(false);
  }

  /**
   * Stores the instance of the controller in the cache, so that it can be reused the next time
   * the route is called.
   *
   * @param controller controller to store
   * @param <C>        controller type
   */
  @Override
  public <C extends AbstractComponentController<?, ?, ?>> void storeInCache(C controller) {
    ControllerFactory.INSTANCE
                     .storeInCache(controller);
    controller.setCached(true);
  }

  /**
   * Stores the instance of the controller in the cache, so that it can be reused the next time
   * the route is called.
   *
   * @param compositeController compositeController to store
   * @param <C>                 controller type
   */
  @Override
  public <C extends AbstractCompositeController<?, ?, ?>> void storeInCache(C compositeController) {
    CompositeFactory.INSTANCE
                    .storeInCache(compositeController);
    compositeController.setCached(true);
  }

  /**
   * Returns a map of query parameters that was available at application start.
   *
   * @return map of query parameters at application start
   */
  @Override
  public Map<String, String> getStartQueryParameters() {
    return this.plugin.getQueryParameters();
  }

  /**
   * Returns the current route.
   * <br>
   * The method will return a route with a '*' as placeholder for parameters.
   * <br>
   * Keep in mind:
   * This is the current route. The route might be changed by other processes,
   * f.e.: a RoutingException or something else!
   *
   * @return the current route
   */
  @Override
  public String getCurrentRoute() {
    return this.currentRoute;
  }

  /**
   * Returns the current parameters from the last executed route..
   * <br>
   * The method will return a String[] for parameters.
   * <br>
   * Keep in mind:
   * This is the current route. The route might be changed by other processes,
   * f.e.: a RoutingException or something else!
   *
   * @return the current parameters
   */
  public String[] getCurrentParameters() {
    return this.currentParameters;
  }

  /**
   * Returns the last executed hash.
   * <br>
   * The method will return a route with all parameters set.
   * <br>
   * Keep in mind:
   * This is the last executed route. The route might be changed by other processes,
   * f.e.: a RoutingException or something else!
   *
   * @return the current route
   */
  @Override
  public String getLastExecutedHash() {
    return this.lastExecutedHash;
  }

  void handleRouting(String hash,
                     boolean forceRouting) {

    // in some cases the hash contains protocol, port and URI, we clean it
    if (hash.contains("#")) {
      hash = hash.substring(hash.indexOf("#") + 1);
    }
    // Keycloak add parameters at the end of the hash ... we need to ignore them!
    if (hash.contains("&")) {
      hash = hash.substring(0,
                            hash.indexOf("&") + 1);
    }
    // save hash to loop detector list ...
    if (this.loopDetectionList.contains(pimpUpHashForLoopDetection(hash))) {
      // fire Router StateEvent
      try {
        // parse it again to get more informations to add to the event
        RouteResult routeResult = this.parse(hash);
        RouterStateEventFactory.INSTANCE.fireAbortRoutingEvent(routeResult.getRoute(),
                                                               routeResult.getParameterValues()
                                                                          .toArray(new String[0]));
        RouterStateEventFactory.INSTANCE.fireDoneRoutingEvent(routeResult.getRoute(),
                                                              routeResult.getParameterValues()
                                                                         .toArray(new String[0]));
      } catch (RouterException e) {
        // Ups ... does not work ... lets use the hash
        RouterStateEventFactory.INSTANCE.fireAbortRoutingEvent(hash);
      }
      // loop discovered .... -> create message
      StringBuilder sb = new StringBuilder();
      sb.append("loop detected for hash >>")
        .append(hash)
        .append("<< --> Routing aborted!");
      this.eventBus.fireEvent(LogEvent.create()
                                      .sdmOnly(true)
                                      .addMessage(sb.toString()));
      // Fire error event ....
      this.eventBus.fireEvent(NaluErrorEvent.createNaluError()
                                            .errorId(NaluConstants.NALU_ERROR_LOOP_DETECTED)
                                            .message(sb.toString())
                                            .route(this.loopDetectionList.get(0)));
      // clear loop detection list ...
      this.loopDetectionList.clear();
      // abort handling!
      return;
    } else {
      this.loopDetectionList.add(pimpUpHashForLoopDetection(hash));
    }
    // parse hash ...

    RouteResult routeResult;
    try {
      routeResult = this.parse(hash);
      // once the hash is parsed, we save the route as currentRoute!
      this.currentRoute = routeResult.getRoute();
      // once the hash is parsed, we save the parameter as currentParameters!
      this.currentParameters = routeResult.getParameterValues()
                                          .toArray(new String[0]);
    } catch (RouterException e) {
      this.handleRouterException(hash,
                                 e);
      return;
    }
    // fire Router StateEvent
    RouterStateEventFactory.INSTANCE.fireStartRoutingEvent(routeResult.getRoute(),
                                                           routeResult.getParameterValues()
                                                                               .toArray(new String[0]));
    // First we have to check if there is a filter
    // if there are filters ==>  filter the route
    for (IsFilter filter : this.routerConfiguration.getFilters()) {
      if (!filter.filter(addLeadingSlash(routeResult.getRoute()),
                         routeResult.getParameterValues()
                                    .toArray(new String[0]))) {
        // fire Router StateEvent
        RouterStateEventFactory.INSTANCE.fireAbortRoutingEvent(routeResult.getRoute(),
                                                               routeResult.getParameterValues()
                                                                          .toArray(new String[0]));
        // redirect to new route!
        String        redirectTo = filter.redirectTo();
        String[]      parms      = filter.parameters();
        StringBuilder sb         = new StringBuilder();
        sb.append("Router: filter >>")
          .append(filter.getClass()
                        .getCanonicalName())
          .append("<< intercepts routing! New route: >>")
          .append(redirectTo)
          .append("<<");
        if (Arrays.asList(parms)
                  .size() > 0) {
          sb.append(" with parameters: ");
          Stream.of(parms)
                .forEach(p -> sb.append(">>")
                                .append(p)
                                .append("<< "));
        }
        this.eventBus.fireEvent(LogEvent.create()
                                        .sdmOnly(true)
                                        .addMessage(sb.toString()));
        this.route(redirectTo,
                   true,
                   true,
                   false,
                   parms);
        return;
      }
    }
    // search for a matching routing
    List<RouteConfig> routeConfigurations = this.routerConfiguration.match(routeResult.getRoute());
    // check whether the routing is possible ...
    if (forceRouting) {
      // in case of 'forceRouting' we route without confirmation!
      this.unbindController(hash,
                            routeResult,
                            routeConfigurations);
    } else {
      String finalHash = hash;
      this.confirmRouting(routeResult,
                          routeConfigurations,
                          new ConfirmHandler() {

                            @Override
                            public void onOk() {
                              unbindController(finalHash,
                                               routeResult,
                                               routeConfigurations);
                            }

                            @Override
                            public void onCancel() {
                              plugin.route(lastExecutedHash,
                                           false,
                                           false);
                              // clear loop detection list ...
                              loopDetectionList.clear();
                              RouterStateEventFactory.INSTANCE.fireCancelByUserRoutingEvent(routeResult.getRoute(),
                                                                                            routeResult.getParameterValues()
                                                                                                       .toArray(new String[0]));
                              RouterStateEventFactory.INSTANCE.fireDoneRoutingEvent(routeResult.getRoute(),
                                                                                    routeResult.getParameterValues()
                                                                                               .toArray(new String[0]));
                            }
                          });
    }
  }

  private void unbindController(String hash,
                                RouteResult routeResult,
                                List<RouteConfig> routeConfigurations) {
    List<ControllerInstance> instances = routeConfigurations.stream()
                                                            .map(config -> this.activeComponents.get(config.getSelector()))
                                                            .filter(Objects::nonNull)
                                                            .collect(Collectors.toList());
    if (instances.size() == 0) {
      this.doRouting(hash,
                     routeResult,
                     routeConfigurations);
    } else {
      this.callCounter = instances.size();
      instances.forEach(i -> i.getController()
                              .unbind(() -> this.handleUnbindCallBack(hash,
                                                                      routeResult,
                                                                      routeConfigurations),
                                      () -> {
                                        // clear loop detection list ...
                                        loopDetectionList.clear();
                                        RouterStateEventFactory.INSTANCE.fireCancelByUserRoutingEvent(routeResult.getRoute(),
                                                                                                      routeResult.getParameterValues()
                                                                                                                 .toArray(new String[0]));
                                        RouterStateEventFactory.INSTANCE.fireDoneRoutingEvent(routeResult.getRoute(),
                                                                                              routeResult.getParameterValues()
                                                                                                         .toArray(new String[0]));
                                      }));
    }
  }

  private void handleUnbindCallBack(String hash,
                                    RouteResult routeResult,
                                    List<RouteConfig> routeConfigurations) {
    this.callCounter--;
    if (this.callCounter == 0) {
      this.doRouting(hash,
                     routeResult,
                     routeConfigurations);
    }
  }

  private void doRouting(String hash,
                         RouteResult routeResult,
                         List<RouteConfig> routeConfigurations) {
    // call stop for all elements
    this.stopController(routeConfigurations,
                        !routeResult.getShell()
                                    .equals(this.lastAddedShell));
    // handle shellCreator
    // in case shellCreator changed or is not set, use the actual shellCreator!
    if (!routeResult.getShell()
                    .equals(this.lastAddedShell)) {
      // in case a shell already exists, composites of that shell
      // needs to be stopped. But before, we need to check if a
      // shell instance is already set.
      if (Objects.nonNull(this.shell)) {
        // possible existing composites stopping ..
        this.shell.getComposites()
                  .values()
                  .forEach(s -> {
                    Utils.get()
                         .deactivateCompositeController(s);
                    if (!s.isCached()) {
                      Utils.get()
                           .stopCompositeController(s);
                    }
                  });
      }
      updateShell(hash,
                  routeResult,
                  routeConfigurations);
    } else {
      postProcessHandleRouting(hash,
                               routeResult,
                               routeConfigurations);
    }
  }

  private void confirmRouting(RouteResult routeResult,
                              List<RouteConfig> routeConfigurations,
                              ConfirmHandler confirmHandler) {
    List<String> messageList = new ArrayList<>();

    // in case shell is null, there are no composites to ask ...
    if (Objects.nonNull(this.shell)) {
      Optional<String> optionalShellCompositeConfirmation = this.shell.getComposites()
                                                                      .values()
                                                                      .stream()
                                                                      .map(AbstractCompositeController::mayStop)
                                                                      .filter(Objects::nonNull)
                                                                      .findFirst();
      optionalShellCompositeConfirmation.ifPresent(messageList::add);
    }

    routeConfigurations.stream()
                       .map(config -> this.activeComponents.get(config.getSelector()))
                       .filter(Objects::nonNull)
                       .forEach(c -> {
                         Optional<String> optional = c.getController()
                                                      .getComposites()
                                                      .values()
                                                      .stream()
                                                      .map(AbstractCompositeController::mayStop)
                                                      .filter(Objects::nonNull)
                                                      .findFirst();
                         optional.ifPresent(messageList::add);
                       });

    Optional<String> optionalConfirm = routeConfigurations.stream()
                                                          .map(config -> this.activeComponents.get(config.getSelector()))
                                                          .filter(Objects::nonNull)
                                                          .map(c -> c.getController()
                                                                     .mayStop())
                                                          .filter(Objects::nonNull)
                                                          .findFirst();

    if (optionalConfirm.isPresent() || messageList.size() > 0) {
      this.plugin.confirm(optionalConfirm.orElseGet(() -> messageList.get(0)),
                          confirmHandler);
    } else {
      confirmHandler.onOk();
    }
  }

  /**
   * In case a routing exception occurs, Nalu will check weather there is
   * a <b>illegalRouteTarget</b> is set or not.
   * <br><br>
   * In case the <b>illegalRouteTarget</b> is not set, a NaluErrorEvent gets fired.
   * <br><br>
   * In case the <b>illegalRouteTarget</b> is set, Nalu will use this route.
   *
   * @param hash hash on start
   * @param e    the RouterException
   */
  @Override
  public void handleRouterException(String hash,
                                    RouterException e) {
    if (PropertyFactory.get()
                       .getIllegalRouteTarget() == null ||
        PropertyFactory.get()
                       .getIllegalRouteTarget()
                       .isEmpty()) {
      // fire Router StateEvent
      try {
        RouteResult routeResult = this.parse(hash);
        RouterStateEventFactory.INSTANCE.fireAbortRoutingEvent(routeResult.getRoute(),
                                                               routeResult.getParameterValues()
                                                                          .toArray(new String[0]));
        RouterStateEventFactory.INSTANCE.fireDoneRoutingEvent(routeResult.getRoute(),
                                                              routeResult.getParameterValues()
                                                                         .toArray(new String[0]));
      } catch (RouterException e1) {
        RouterStateEventFactory.INSTANCE.fireAbortRoutingEvent(hash);
      }
      // Fire error event ....
      StringBuilder sb = new StringBuilder();
      sb.append("no matching route for hash >>")
        .append(hash)
        .append("<< --> Routing aborted!");
      this.eventBus.fireEvent(LogEvent.create()
                                      .sdmOnly(true)
                                      .addMessage(sb.toString()));
      this.eventBus.fireEvent(NaluErrorEvent.createNaluError()
                                            .errorId(NaluConstants.NALU_ERROR_ROUTING_EXCEPTION)
                                            .message(sb.toString())
                                            .route(hash));
    } else {
      this.route(PropertyFactory.get()
                                .getIllegalRouteTarget());
    }
  }

  /**
   * Parse the hash and divides it into shellCreator, route and parameters
   *
   * @param route ths hash to parse
   * @return parse result
   * @throws com.github.nalukit.nalu.client.internal.route.RouterException in case no controller is found for the routing
   */
  @Override
  public RouteResult parse(String route)
      throws RouterException {
    String decodedUrl = this.plugin.decode(route);
    return RouteParser.INSTANCE.parse(decodedUrl,
                                      this.shellConfiguration,
                                      this.routerConfiguration);
  }

  /**
   * Sets the alwaysLoadComposite instance
   *
   * @param alwaysLoadComposite the alwaysLoadComposite instance
   */
  @NaluInternalUse
  @Override
  public final void setAlwaysLoadComposite(AlwaysLoadComposite alwaysLoadComposite) {
    this.alwaysLoadComposite = alwaysLoadComposite;
  }

  /**
   * Sets the alwaysShowPopUp instance
   *
   * @param alwaysShowPopUp the alwaysShowPopUp instance
   */
  @NaluInternalUse
  @Override
  public void setAlwaysShowPopUp(AlwaysShowPopUp alwaysShowPopUp) {
    this.alwaysShowPopUp = alwaysShowPopUp;
  }

  /**
   * sets the event bus inside the router
   *
   * @param eventBus Nalu application event bus
   */
  @Override
  @NaluInternalUse
  public void setEventBus(SimpleEventBus eventBus) {
    this.eventBus = eventBus;
  }

  /**
   * Add a module to the application.
   * <p>
   * The method wil inject the router, event bus, context
   * and the alwaysLoadComposite-flag into the module.
   * <p>
   * Besides that, the method adds the shell- and
   * route-configuration and also the controller-configurations.
   *
   * @param module the new module to add to the application
   * @param <M>    Type of the module.
   */
  @Override
  public <M extends IsModule<?>> void addModule(M module) {
    module.setRouter(this);
    module.setEventBus(this.eventBus);
    module.setAlwaysLoadComposite(this.alwaysLoadComposite);
    module.setAlwaysShowPopUp(this.alwaysShowPopUp);
    module.loadModule(this.routerConfiguration);
    this.shellConfiguration.getShells()
                           .addAll(module.getShellConfigs());
    this.routerConfiguration.getRouters()
                            .addAll(module.getRouteConfigs());
    this.compositeReferences.addAll(module.getCompositeReferences());
  }

  private void postProcessHandleRouting(String hash,
                                        RouteResult routeResult,
                                        List<RouteConfig> routeConfigurations) {
    // routing
    for (RouteConfig routeConfiguration : routeConfigurations) {
      // #237 - Undesired behavior in case two controllers are added to the same route and slot
      //
      // We check here if a component is already added to the slot.
      // In case it is added, another component, that which to be added to that
      // slot will be ignored.
      if (this.activeComponents.get(routeConfiguration.getSelector()) == null) {
        this.handleRouteConfig(routeConfiguration,
                               routeResult,
                               hash);
      }
    }
    this.shell.onAttachedComponent();
    // update seo-meta-data
    SeoDataProvider.INSTANCE
                   .update();
    // fire Router StateEvent
    RouterStateEventFactory.INSTANCE.fireDoneRoutingEvent(routeResult.getRoute(),
                                                          routeResult.getParameterValues()
                                                                     .toArray(new String[0]));
  }

  private void stopController(List<RouteConfig> routeConfigurations,
                              boolean replaceShell) {
    List<AbstractComponentController<?, ?, ?>> controllerList = new ArrayList<>();
    if (replaceShell) {
      controllerList.addAll(this.activeComponents.values()
                                                 .stream()
                                                 .map(ControllerInstance::getController)
                                                 .collect(Collectors.toList()));
    } else {
      controllerList.addAll(routeConfigurations.stream()
                                               .map(config -> this.activeComponents.get(config.getSelector()))
                                               .filter(Objects::nonNull)
                                               .map(ControllerInstance::getController)
                                               .collect(Collectors.toList()));
    }
    controllerList.forEach(controller -> {
      // check, if it is a redraw case
      boolean handlingModeReuse = this.isHandlingModeReuse(controller);
      // stop compositeControllers
      controller.getComposites()
                .values()
                .forEach(s -> {
                  if (controller.isCached() || handlingModeReuse) {
                    Utils.get()
                         .deactivateCompositeController(s);
                  } else {
                    Utils.get()
                         .deactivateCompositeController(s);
                    if (!s.isCached()) {
                      Utils.get()
                           .stopCompositeController(s);
                    }
                  }
                });

      Utils.get()
           .deactivateController(controller,
                                 handlingModeReuse);
      if (!controller.isCached() && !handlingModeReuse) {
        Utils.get()
             .stopController(controller);
      }
      // In case we have the same route and the redrawMode is set to 'REUSE'
      // we should only deactivate the controller and not remove them ...
      controllerList.stream()
                    .filter(c -> !isHandlingModeReuse(c))
                    .forEach(c -> {
                      this.plugin.remove(c.getRelatedSelector());
                      this.activeComponents.remove(c.getRelatedSelector());
                    });
    });
  }

  private void updateShell(String hash,
                           RouteResult routeResult,
                           List<RouteConfig> routeConfigurations) {
    // add shellCreator to the viewport
    ShellConfig shellConfig = this.shellConfiguration.match(routeResult.getShell());
    if (!Objects.isNull(shellConfig)) {
      ShellFactory.INSTANCE
                  .shell(shellConfig.getClassName(),
                         new ShellCallback() {

                           @Override
                           public void onFinish(ShellInstance shellInstance) {
                             // in case there is an instance of an shellCreator existing, call the onDetach method inside the shellCreator
                             if (!Objects.isNull(shell)) {
                               detachShell();
                             }
                             // set new shellCreator value
                             shell = shellInstance.getShell();
                             // save the last added shellCreator ....
                             lastAddedShell = routeResult.getShell();
                             // append shell
                             shellInstance.getShell()
                                          .attachShell();
                             // composites of the controller
                             List<AbstractCompositeController<?, ?, ?>> compositeControllers = new ArrayList<>();
                             // in case the controller is not cached, that means it is newly created, we have to deal with composites
                             // handle composite of the controller
                             // get a list of composites for this controller (might be empty ...
                             List<CompositeReference> compositeForShell = getCompositeForClassName(shell.getClass()
                                                                                                        .getCanonicalName());
                             if (compositeForShell.size() > 0) {
                               compositeForShell.forEach(s -> {
                                 try {
                                   // check for composite loader
                                   if (CompositeConditionFactory.INSTANCE.loadComposite(shell.getClass()
                                                                                             .getCanonicalName(),
                                                                                        s.getComposite(),
                                                                                        routeResult.getRoute(),
                                                                                        routeResult.getParameterValues()
                                                                                                   .toArray(new String[0]))) {
                                     CompositeInstance compositeInstance = CompositeFactory.INSTANCE.getComposite(shell.getClass()
                                                                                                                       .getCanonicalName(),
                                                                                                                  s.getComposite(),
                                                                                                                  s.getSelector(),
                                                                                                                  s.isScopeGlobal(),
                                                                                                                  routeResult.getParameterKeys(),
                                                                                                                  routeResult.getParameterValues());
                                     if (compositeInstance == null) {
                                       eventBus.fireEvent(LogEvent.create()
                                                                  .sdmOnly(true)
                                                                  .addMessage("controller >>" +
                                                                              shell.getClass()
                                                                                   .getCanonicalName() +
                                                                              "<< --> compositeController >>" +
                                                                              s.getCompositeName() +
                                                                              "<< not found"));
                                     } else {
                                       compositeControllers.add(compositeInstance.getComposite());
                                       // inject router into composite
                                       compositeInstance.getComposite()
                                                        .setRouter(AbstractRouter.this);
                                       // inject composite into controller
                                       shell.getComposites()
                                            .put(s.getCompositeName(),
                                                 compositeInstance.getComposite());
                                     }
                                   }
                                 } catch (RoutingInterceptionException e) {
                                   logControllerInterceptsRouting(e.getControllerClassName(),
                                                                  e.getRoute(),
                                                                  e.getParameter());
                                   route(e.getRoute(),
                                         true,
                                         true,
                                         false,
                                         e.getParameter());
                                 }
                               });
                               // try to find a reference with selector check
                               for (AbstractCompositeController<?, ?, ?> compositeController : compositeControllers) {
                                 CompositeReference reference = null;
                                 for (CompositeReference sfc : compositeForShell) {
                                   if (compositeController.getClass()
                                                          .getCanonicalName()
                                                          .equals(sfc.getComposite())) {
                                     if (compositeController.getSelector() != null) {
                                       if (compositeController.getSelector()
                                                              .equals(sfc.getSelector())) {
                                         reference = sfc;
                                         break;
                                       }
                                     }
                                   }
                                 }
                                 // uiiih nothiung found ... do it again without checking selector
                                 if (reference == null) {
                                   // try to find a reference without selector check
                                   for (CompositeReference sfc : compositeForShell) {
                                     if (compositeController.getClass()
                                                            .getCanonicalName()
                                                            .equals(sfc.getComposite())) {
                                       if (compositeController.getSelector() == null) {
                                         reference = sfc;
                                         break;
                                       }
                                     }
                                   }
                                 }
                                 if (reference != null) {
                                   append(reference.getSelector(),
                                          compositeController);
                                 }
                               }
                               // in case we have a cached controller, we need to look for global composites
                               // and append them!
                               List<CompositeReference> globalComposite = compositeForShell.stream()
                                                                                           .filter(CompositeReference::isScopeGlobal)
                                                                                           .collect(Collectors.toList());
                               for (CompositeReference compositeReference : globalComposite) {
                                 if (CompositeConditionFactory.INSTANCE.loadComposite(shell.getClass()
                                                                                           .getCanonicalName(),
                                                                                      compositeReference.getComposite(),
                                                                                      routeResult.getRoute(),
                                                                                      routeResult.getParameterValues()
                                                                                                 .toArray(new String[0]))) {
                                   try {
                                     CompositeInstance compositeInstance = CompositeFactory.INSTANCE.getComposite(shell.getClass()
                                                                                                                       .getCanonicalName(),
                                                                                                                  compositeReference.getComposite(),
                                                                                                                  compositeReference.getSelector(),
                                                                                                                  true,
                                                                                                                  routeResult.getParameterKeys(),
                                                                                                                  routeResult.getParameterValues());
                                     append(compositeReference.getSelector(),
                                            compositeInstance.getComposite());
                                   } catch (RoutingInterceptionException e) {
                                     logControllerInterceptsRouting(e.getControllerClassName(),
                                                                    e.getRoute(),
                                                                    e.getParameter());
                                     route(e.getRoute(),
                                           true,
                                           true,
                                           false,
                                           e.getParameter());
                                     return;
                                   }
                                 }
                               }
                               //                                 }
                             }
                             compositeControllers.forEach(AbstractCompositeController::onAttach);
                             compositeControllers.forEach(c -> {
                               if (!c.isCached()) {
                                 c.start();
                                 // in case we are cached globally we need to set cached
                                 // to true after the first time the
                                 // composite is created
                                 if (c.isCachedGlobal()) {
                                   c.setCached(true);
                                 }
                               }
                               if (!Objects.isNull(c.getActivateNaluCommand())) {
                                 c.getActivateNaluCommand()
                                  .execute();
                               }
                               c.activate();
                             });
                             // get shellCreator matching root configs ...
                             List<RouteConfig> shellMatchingRouteConfigurations = routerConfiguration.match(routeResult.getShell());
                             for (RouteConfig routeConfiguration : shellMatchingRouteConfigurations) {
                               handleRouteConfig(routeConfiguration,
                                                 routeResult,
                                                 hash);
                             }
                             postProcessHandleRouting(hash,
                                                      routeResult,
                                                      routeConfigurations);
                           }

                           private void detachShell() {
                             shell.detachShell();
                           }

                           @Override
                           public void onShellNotFound() {
                             eventBus.fireEvent(NaluErrorEvent.createNaluError()
                                                              .errorId(NaluConstants.NALU_ERROR_SHELL_NOT_FOUND)
                                                              .message("no shell found for route: >>" +
                                                                       shellConfig.getRoute() +
                                                                       "<<")
                                                              .route(shellConfig.getRoute()));
                           }

                           @Override
                           public void onRoutingInterceptionException(RoutingInterceptionException e) {
                             logControllerInterceptsRouting(e.getControllerClassName(),
                                                            e.getRoute(),
                                                            e.getParameter());
                           }
                         });
    }
  }

  private String addLeadingSlash(String value) {
    if (value.startsWith("/")) {
      return value;
    }
    return "/" + value;
  }

  private void handleRouteConfig(RouteConfig routeConfiguration,
                                 RouteResult routeResult,
                                 String hash) {
    ControllerFactory.INSTANCE
                     .controller(routeConfiguration.getRoute(),
                                 routeConfiguration.getClassName(),
                                 new ControllerCallback() {

                                   @Override
                                   public void onRoutingInterceptionException(RoutingInterceptionException e) {
                                     logControllerInterceptsRouting(e.getControllerClassName(),
                                                                    e.getRoute(),
                                                                    e.getParameter());
                                     route(e.getRoute(),
                                           true,
                                           true,
                                           false,
                                           e.getParameter());
                                   }

                                   @Override
                                   public void onFinish(ControllerInstance controller) {
                                     doRouting(hash,
                                               routeResult,
                                               routeConfiguration,
                                               controller);
                                   }
                                 },
                                 routeResult.getParameterKeys(),
                                 routeResult.getParameterValues());
  }

  private void doRouting(String hash,
                         RouteResult routeResult,
                         RouteConfig routeConfiguration,
                         ControllerInstance controllerInstance) {
    if (Objects.isNull(controllerInstance.getController())) {
      String sb = "no controller found for hash >>" + hash + "<<";
      this.eventBus.fireEvent(LogEvent.create()
                                      .sdmOnly(true)
                                      .addMessage(sb));
      this.eventBus.fireEvent(NaluErrorEvent.createNaluError()
                                            .errorId(NaluConstants.NALU_ERROR_NO_CONTROLLER_INSTANCE_FOUND)
                                            .message(sb)
                                            .route(routeResult.getRoute()));
    } else {
      // inject the router instance into the controller!
      // (we do it for cached and not cached controllers,
      // cause it does not matter!
      controllerInstance.getController()
                        .setRouter(this);
      // composites of the controller
      List<AbstractCompositeController<?, ?, ?>> compositeControllers = new ArrayList<>();
      // in case the controller is not cached, that means it is newly created, we have to deal with composites
      // handle composite of the controller
      // get a list of composites for this controller (might be empty ...
      List<CompositeReference> compositeForController = this.getCompositeForClassName(controllerInstance.getController()
                                                                                                        .getClass()
                                                                                                        .getCanonicalName());
      boolean handlingModeReuse = this.isHandlingModeReuse(controllerInstance.getController());
      // in case the controller is not cached, we have to deal with composites!
      if (!controllerInstance.isCached() && !handlingModeReuse) {
        if (compositeForController.size() > 0) {
          compositeForController.forEach(s -> {
            try {
              // check for composite loader
              if (CompositeConditionFactory.INSTANCE.loadComposite(controllerInstance.getControllerClassName(),
                                                                   s.getComposite(),
                                                                   routeResult.getRoute(),
                                                                   routeResult.getParameterValues()
                                                                              .toArray(new String[0]))) {
                CompositeInstance compositeInstance = CompositeFactory.INSTANCE.getComposite(controllerInstance.getControllerClassName(),
                                                                                             s.getComposite(),
                                                                                             s.getSelector(),
                                                                                             s.isScopeGlobal(),
                                                                                             routeResult.getParameterKeys(),
                                                                                             routeResult.getParameterValues());
                if (compositeInstance == null) {
                  this.eventBus.fireEvent(LogEvent.create()
                                                  .sdmOnly(true)
                                                  .addMessage("controller >>" +
                                                              controllerInstance.getController()
                                                                                .getClass()
                                                                                .getCanonicalName() +
                                                              "<< --> compositeController >>" +
                                                              s.getCompositeName() +
                                                              "<< not found"));
                } else {
                  compositeControllers.add(compositeInstance.getComposite());
                  // inject router into composite
                  compositeInstance.getComposite()
                                   .setRouter(this);
                  // inject composite into controller
                  controllerInstance.getController()
                                    .getComposites()
                                    .put(s.getCompositeName(),
                                         compositeInstance.getComposite());
                }
              }
            } catch (RoutingInterceptionException e) {
              this.logControllerInterceptsRouting(e.getControllerClassName(),
                                                  e.getRoute(),
                                                  e.getParameter());
              this.route(e.getRoute(),
                         true,
                         true,
                         false,
                         e.getParameter());
            }
          });
        }
      }
      // add element to DOM
      if (!handlingModeReuse) {
        this.append(routeConfiguration.getSelector(),
                    controllerInstance);
      }
      if (!controllerInstance.isCached() && !handlingModeReuse) {
        // try to find a reference with selector check
        for (AbstractCompositeController<?, ?, ?> compositeController : compositeControllers) {
          CompositeReference reference = null;
          for (CompositeReference sfc : compositeForController) {
            if (compositeController.getClass()
                                   .getCanonicalName()
                                   .equals(sfc.getComposite())) {
              if (compositeController.getSelector() != null) {
                if (compositeController.getSelector()
                                       .equals(sfc.getSelector())) {
                  reference = sfc;
                  break;
                }
              }
            }
          }
          // uiiih nothiung found ... do it again without checking selector
          if (reference == null) {
            // try to find a reference without selector check
            for (CompositeReference sfc : compositeForController) {
              if (compositeController.getClass()
                                     .getCanonicalName()
                                     .equals(sfc.getComposite())) {
                if (compositeController.getSelector() == null) {
                  reference = sfc;
                  break;
                }
              }
            }
          }
          if (reference != null) {
            this.append(reference.getSelector(),
                        compositeController);
          }
        }
      } else {
        if (!handlingModeReuse) {
          // in case we have a cached controller, we need to look for global composites
          // and append them!
          List<CompositeReference> globalComposite = compositeForController.stream()
                                                                           .filter(CompositeReference::isScopeGlobal)
                                                                           .collect(Collectors.toList());
          for (CompositeReference compositeReference : globalComposite) {
            if (CompositeConditionFactory.INSTANCE.loadComposite(controllerInstance.getControllerClassName(),
                                                                 compositeReference.getComposite(),
                                                                 routeResult.getRoute(),
                                                                 routeResult.getParameterValues()
                                                                            .toArray(new String[0]))) {
              try {
                CompositeInstance compositeInstance = CompositeFactory.INSTANCE.getComposite(controllerInstance.getControllerClassName(),
                                                                                             compositeReference.getComposite(),
                                                                                             compositeReference.getSelector(),
                                                                                             true,
                                                                                             routeResult.getParameterKeys(),
                                                                                             routeResult.getParameterValues());
                this.append(compositeReference.getSelector(),
                            compositeInstance.getComposite());
              } catch (RoutingInterceptionException e) {
                this.logControllerInterceptsRouting(e.getControllerClassName(),
                                                    e.getRoute(),
                                                    e.getParameter());
                this.route(e.getRoute(),
                           true,
                           true,
                           false,
                           e.getParameter());
                return;
              }
            }
          }
        }
      }
      if (!handlingModeReuse) {
        // call the onAttach method (for the component).
        // we will do it in both cases, cached and not cached!
        controllerInstance.getController()
                          .onAttach();
        compositeControllers.forEach(AbstractCompositeController::onAttach);
      }
      // in case the controller is cached, we call only activate  ...
      if (controllerInstance.isCached() || handlingModeReuse) {
        // in case we have a REDRAW handling mode, set the parameters
        if (handlingModeReuse) {
          try {
            controllerInstance.getControllerCreator()
                              .setParameter(controllerInstance.getController(),
                                            routeResult.getParameterKeys(),
                                            routeResult.getParameterValues());
          } catch (RoutingInterceptionException e) {
            this.logControllerInterceptsRouting(e.getControllerClassName(),
                                                e.getRoute(),
                                                e.getParameter());
            route(e.getRoute(),
                  true,
                  true,
                  false,
                  e.getParameter());
            return;
          }
        }
        // let's call active for all related composite
        compositeControllers.forEach(c -> {
          if (!Objects.isNull(c.getActivateNaluCommand())) {
            c.getActivateNaluCommand()
             .execute();
          }
          c.activate();
        });
        controllerInstance.getController()
                          .getComposites()
                          .values()
                          .forEach(c -> {
                            if (!Objects.isNull(c.getActivateNaluCommand())) {
                              c.getActivateNaluCommand()
                               .execute();
                            }
                            c.activate();
                          });
        if (!Objects.isNull(controllerInstance.getController()
                                              .getActivateNaluCommand())) {
          controllerInstance.getController()
                            .getActivateNaluCommand()
                            .execute();
        }
        controllerInstance.getController()
                          .activate();
      } else {
        compositeControllers.forEach(c -> {
          if (!c.isCached()) {
            c.start();
            // in case we are cached globally we need to set cached
            // to true after the first time the
            // composite is created
            if (c.isCachedGlobal()) {
              c.setCached(true);
            }
          }
          if (!Objects.isNull(c.getActivateNaluCommand())) {
            c.getActivateNaluCommand()
             .execute();
          }
          c.activate();
        });
        controllerInstance.getController()
                          .start();
        if (!Objects.isNull(controllerInstance.getController()
                                              .getActivateNaluCommand())) {
          controllerInstance.getController()
                            .getActivateNaluCommand()
                            .execute();
        }
        controllerInstance.getController()
                          .activate();
      }
      // save current hash
      this.lastExecutedHash = hash;
      // clear loop detection list ...
      this.loopDetectionList.clear();
    }
  }

  /**
   * Inspects whether the controller is in REDRAW or CREATE mode.
   *
   * @param controller the controller to test
   * @return true if handling mode is REUSE
   */
  private boolean isHandlingModeReuse(IsController<?, ?> controller) {
    return this.currentRoute.equals(controller.getRelatedRoute()) && IsController.Mode.REUSE == controller.getMode();
  }

  private void append(String selector,
                      ControllerInstance controllerInstance) {
    if (controllerInstance.getController()
                          .asElement() == null) {
      String sb = "controller element of controller >>" +
                  controllerInstance.getControllerClassName() +
                  "<< is null! --> Routing aborted!";
      this.eventBus.fireEvent(LogEvent.create()
                                      .sdmOnly(false)
                                      .addMessage(sb));
      eventBus.fireEvent(NaluErrorEvent.createNaluError()
                                       .errorId(NaluConstants.NALU_ERROR_ELEMENT_IS_NULL)
                                       .message(sb)
                                       .route("NoRouteAvailable"));
    }
    if (this.plugin.attach(selector,
                           controllerInstance.getController()
                                             .asElement())) {
      // save to active components
      this.activeComponents.put(selector,
                                controllerInstance);
    } else {
      String sb = "no element found, that matches selector >>" + selector + "<< --> Routing aborted!";
      this.eventBus.fireEvent(LogEvent.create()
                                      .sdmOnly(false)
                                      .addMessage(sb));
      eventBus.fireEvent(NaluErrorEvent.createNaluError()
                                       .errorId(NaluConstants.NALU_ERROR_SELECOR_NOT_FOUND)
                                       .message(sb)
                                       .route("NoRouteAvailable"));
    }
  }

  private void append(String selector,
                      AbstractCompositeController<?, ?, ?> compositeController) {
    if (compositeController.asElement() == null) {
      String sb = "composite controller element is null! --> Routing aborted!";
      this.eventBus.fireEvent(LogEvent.create()
                                      .sdmOnly(false)
                                      .addMessage(sb));
      eventBus.fireEvent(NaluErrorEvent.createNaluError()
                                       .errorId(NaluConstants.NALU_ERROR_ELEMENT_IS_NULL)
                                       .message(sb)
                                       .route("NoRouteAvailable"));
    }
    if (!this.plugin.attach(selector,
                            compositeController.asElement())) {
      String sb = "no element found, that matches selector >>" + selector + "<< --> Routing aborted!";
      this.eventBus.fireEvent(LogEvent.create()
                                      .sdmOnly(false)
                                      .addMessage(sb));
      eventBus.fireEvent(NaluErrorEvent.createNaluError()
                                       .errorId(NaluConstants.NALU_ERROR_SELECOR_NOT_FOUND)
                                       .message(sb)
                                       .route("NoRouteAvailable"));
    }
  }

  private List<CompositeReference> getCompositeForClassName(String className) {
    return this.compositeReferences.stream()
                                   .filter(s -> className.equals(s.getSource()))
                                   .collect(Collectors.toList());
  }

  private void route(String newRoute,
                     boolean forceRouting,
                     boolean replaceState,
                     boolean stealthMode,
                     String... params) {
    String newRouteWithParams = this.generate(newRoute,
                                              params);
    this.plugin.route(newRouteWithParams,
                      replaceState,
                      stealthMode);
    this.handleRouting(newRouteWithParams,
                       forceRouting);
  }

  private String pimpUpHashForLoopDetection(String hash) {
    String value = hash;
    if (value.startsWith("#")) {
      value = value.substring(1);
    }
    if (value.startsWith("/")) {
      value = value.substring(1);
    }
    return value;
  }

  private void logControllerInterceptsRouting(String controllerClassName,
                                              String route,
                                              String[] parameter) {
    StringBuilder sb = new StringBuilder();
    sb.append("Router: create controller >>")
      .append(controllerClassName)
      .append("<< intercepts routing! New route: >>")
      .append(route)
      .append("<<");
    if (Arrays.asList(parameter)
              .size() > 0) {
      sb.append(" with parameters: ");
      Stream.of(parameter)
            .forEach(p -> sb.append(">>")
                            .append(p)
                            .append("<< "));
    }
    this.eventBus.fireEvent(LogEvent.create()
                                    .sdmOnly(true)
                                    .addMessage(sb.toString()));
  }

}
