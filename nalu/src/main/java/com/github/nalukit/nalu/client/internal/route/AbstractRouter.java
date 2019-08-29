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

package com.github.nalukit.nalu.client.internal.route;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.AbstractCompositeController;
import com.github.nalukit.nalu.client.component.IsShell;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.filter.IsFilter;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.CompositeControllerReference;
import com.github.nalukit.nalu.client.internal.PropertyFactory;
import com.github.nalukit.nalu.client.internal.application.*;
import com.github.nalukit.nalu.client.model.NaluErrorMessage;
import com.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin;
import com.github.nalukit.nalu.client.event.RouterStateEvent;
import com.github.nalukit.nalu.client.event.RouterStateEvent.RouterState;
import com.github.nalukit.nalu.client.tracker.IsTracker;
import org.gwtproject.event.shared.SimpleEventBus;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

abstract class AbstractRouter
    implements ConfiguratableRouter {

  private static final String NALU_ERROR_TYPE_NO_CONTROLLER_INSTANCE = "NoControllerInstance";
  private static final String NALU_ERROR_TYPE_NO_SELECTOR_FOUND      = "NoSelectorFound";
  private static final String NALU_ERROR_TYPE_LOOP_DETECTED          = "RoutingLoopDetected";

  // the plugin
  IsNaluProcessorPlugin plugin;
  // route in case of route error
  private String                                            routeError;
  // the latest error object (set by the application
  private NaluErrorMessage                                  applicationErrorMessage;
  // the latest error object
  private NaluErrorMessage                                  naluErrorMessage;
  // composite configuration
  private List<CompositeControllerReference>                compositeControllerReferences;
  // List of the application shells
  private ShellConfiguration                                shellConfiguration;
  // List of the routes of the application
  private RouterConfiguration                               routerConfiguration;
  // List of active components
  private Map<String, AbstractComponentController<?, ?, ?>> activeComponents;
  // hash of last successful routing
  private String                                            lastExecutedHash = "";
  // last added shell - used, to check if the shell needs an shell replacement
  private String                                            lastAddedShell;
  // instance of the current shell
  private IsShell                                           shell;
  // list of routes used for handling the current route - used to detect loops
  private List<String>                                      loopDetectionList;
  // the tracker: if not null, track the users routing
  private IsTracker                                         tracker;
  // the application eventbus
  private SimpleEventBus                                    eventBus;

  AbstractRouter(List<CompositeControllerReference> compositeControllerReferences,
                 ShellConfiguration shellConfiguration,
                 RouterConfiguration routerConfiguration,
                 IsNaluProcessorPlugin plugin,
                 IsTracker tracker,
                 String startRoute,
                 boolean hasHistory,
                 boolean usingHash,
                 boolean usingColonForParametersInUrl,
                 boolean stayOnSite) {
    // save the composite configuration reference
    this.compositeControllerReferences = compositeControllerReferences;
    // save the shell configuration reference
    this.shellConfiguration = shellConfiguration;
    // save the router configuration reference
    this.routerConfiguration = routerConfiguration;
    // save te plugin
    this.plugin = plugin;
    // save the tracker
    this.tracker = tracker;
    // inistantiate lists, etc.
    this.activeComponents = new HashMap<>();
    this.loopDetectionList = new ArrayList<>();
    // set up PropertyFactory
    PropertyFactory.get()
                   .register(startRoute,
                             hasHistory,
                             usingHash,
                             usingColonForParametersInUrl,
                             stayOnSite);
  }

  /**
   * Returns the last error message set by the application.
   * <p>
   * Once the error message is consumed, it should be reseted by the developer.
   * (after displayed on the error site!)
   *
   * @return the last set error message or null, if there is none
   */
  public NaluErrorMessage getApplicationErrorMessage() {
    return applicationErrorMessage;
  }

  /**
   * Sets the application error message.
   * <p>
   *
   * @param applicationErrorMessage the new application error message
   */
  public void setApplicationErrorMessage(NaluErrorMessage applicationErrorMessage) {
    this.applicationErrorMessage = applicationErrorMessage;
  }

  /**
   * Clears the application error message.
   * <p>
   * Should be called after the error message is displayed!
   */
  public void clearApplicationErrorMessage() {
    this.applicationErrorMessage = null;
  }

  /**
   * Sets the application error message.
   * <p>
   *
   * @param errorType    a String that indicates the type of the error (value is to set by the developer)
   * @param errorMessage the error message that should be displayed
   */
  public void setApplicationErrorMessage(String errorType,
                                         String errorMessage) {
    this.applicationErrorMessage = new NaluErrorMessage(errorType,
                                                        errorMessage);
  }

  /**
   * Returns the last error message set by Nalu.
   * <p>
   * Once the error message is consumed, it should be reseted by the developer.
   * (after displayed on the error site!)
   *
   * @return the last set error message or null, if there is none
   */
  public NaluErrorMessage getNaluErrorMessage() {
    return naluErrorMessage;
  }

  /**
   * Clears the Nalu error message.
   * <p>
   * Should be called after the error message is displayed!
   */
  public void clearNaluErrorMessage() {
    this.naluErrorMessage = null;
  }

  /**
   * Returns the last error message set by Nalu or application.
   * <p>
   * In case a error message is set by Nalu and by the application,
   * this method will return the error message set by Nalu.
   * <p>
   * Once the error message is consumed, it should be reseted by the developer.
   * (after displayed on the error site!)
   *
   * @return the last set error message set by thel application
   * or null, if there is none
   */
  public NaluErrorMessage getErrorMessageByPriority() {
    if (!Objects.isNull(this.naluErrorMessage)) {
      return this.naluErrorMessage;
    } else if (!Objects.isNull(this.applicationErrorMessage)) {
      return this.applicationErrorMessage;
    } else {
      return null;
    }
  }

  /**
   * Sets the error route. (Mostly done by the framework)
   *
   * @param routeError route used by Nalu in case of a routing error
   */
  public void setRouteError(String routeError) {
    this.routeError = routeError;
  }

  /**
   * Stores the instance of the controller in the cache, so that it can be reused the next time
   * the route is called.
   *
   * @param controller controller to store
   * @param <C>        controller type
   */
  public <C extends AbstractComponentController<?, ?, ?>> void storeInCache(C controller) {
    ControllerFactory.get()
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
  public <C extends AbstractCompositeController<?, ?, ?>> void storeInCache(C compositeController) {
    CompositeFactory.get()
                    .storeInCache(compositeController);
    compositeController.setCached(true);
  }

  /**
   * Removes a controller from the chache
   *
   * @param controller controller to be removed
   * @param <C>        controller type
   */
  public <C extends AbstractComponentController<?, ?, ?>> void removeFromCache(C controller) {
    ControllerFactory.get()
                     .removeFromCache(controller);
    controller.setCached(false);
  }

  /**
   * Removes a controller from the chache
   *
   * @param compositeController controller to be removed
   * @param <C>                 controller type
   */
  public <C extends AbstractCompositeController<?, ?, ?>> void removeFromCache(C compositeController) {
    CompositeFactory.get()
                    .removeFromCache(compositeController);
    compositeController.setCached(false);
  }

  /**
   * clears the chache
   */
  public void clearCache() {
    ControllerFactory.get()
                     .clearControllerCache();
  }

  void handleRouting(String hash) {
    // in some cases the hash contains protocoll, port and URI, we clean it
    if (hash.contains("#")) {
      hash = hash.substring(hash.indexOf("#") + 1);
    }
    // logg hash
    RouterLogger.logHandleHash(hash);
    // save hash to loop detector list ...
    if (this.loopDetectionList.contains(pimpUpHashForLoopDetection(hash))) {
      // fire Router StateEvent
      this.fireRouterStateEvent(RouterState.ROUTING_ABORTED,
                                hash);
      // loop discovered .... -> show message
      String message = RouterLogger.logLoopDetected(this.loopDetectionList.get(0));
      // check, if there is a loop containing the error route
      if (this.loopDetectionList.contains(pimpUpHashForLoopDetection(this.routeError))) {
        // YES!! -> just use the alert feature of the plugin
        this.plugin.alert(message);
      } else {
        // NO!! -> route to error site ....
        this.naluErrorMessage = new NaluErrorMessage(AbstractRouter.NALU_ERROR_TYPE_LOOP_DETECTED,
                                                     message);
        this.loopDetectionList.add(pimpUpHashForLoopDetection(this.routeError));
        this.route(this.routeError);
      }
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
    } catch (RouterException e) {
      this.naluErrorMessage = new NaluErrorMessage(AbstractRouter.NALU_ERROR_TYPE_NO_CONTROLLER_INSTANCE,
                                                   RouterLogger.logNoMatchingRoute(hash,
                                                                                   this.routeError));
      if (!Objects.isNull(this.routeError)) {
        // fire Router StateEvent
        this.fireRouterStateEvent(RouterState.ROUTING_ABORTED,
                                  hash);
        // loop discovered .... -> show message
        String message = RouterLogger.logLoopDetected(this.loopDetectionList.get(0));
        // check, if there is a loop containing the error route
        if (this.loopDetectionList.contains(pimpUpHashForLoopDetection(this.routeError))) {
          // YES!! -> just use the alert feature of the plugin
          this.plugin.alert(message);
          return;
        }
        RouterLogger.logUseErrorRoute(this.routeError);
        this.loopDetectionList.add(pimpUpHashForLoopDetection(hash));
        this.route(this.routeError,
                   true);
      } else {
        // should never be seen!
        this.plugin.alert("No error Route defeined");
      }
      return;
    }
    // First we have to check if there is a filter
    // if there are filters ==>  filter the route
    for (IsFilter filter : this.routerConfiguration.getFilters()) {
      if (!filter.filter(addLeadindgSlash(routeResult.getRoute()),
                         routeResult.getParameterValues()
                                    .toArray(new String[0]))) {
        RouterLogger.logFilterInterceptsRouting(filter.getClass()
                                                      .getCanonicalName(),
                                                filter.redirectTo(),
                                                filter.parameters());
        this.route(filter.redirectTo(),
                   true,
                   filter.parameters());
        // fire Router StateEvent
        this.fireRouterStateEvent(RouterState.ROUTING_ABORTED,
                                  hash);
        return;
      }
    }
    // search for a matching routing
    List<RouteConfig> routeConfigurations = this.routerConfiguration.match(routeResult.getRoute());
    // check whether or not the routing is possible ...
    if (this.confirmRouting(routeConfigurations)) {
      // call stop for all elements
      this.stopController(routeConfigurations,
                          !routeResult.getShell()
                                      .equals(this.lastAddedShell));
      // handle shellCreator
      //
      // in case shellCreator changed or is not set, use the actual shellCreator!
      if (!routeResult.getShell()
                      .equals(this.lastAddedShell)) {
        // add shellCreator to the viewport
        ShellConfig shellConfig = this.shellConfiguration.match(routeResult.getShell());
        if (!Objects.isNull(shellConfig)) {
          String finalHash = hash;
          ShellFactory.get()
                      .shell(shellConfig.getClassName(),
                             new ShellCallback() {
                               @Override
                               public void onFinish(ShellInstance shellInstance) {
                                 // in case there is an instance of an shellCreator existing, call the onDetach mehtod inside the shellCreator
                                 if (!Objects.isNull(shell)) {
                                   detachShell();
                                 }
                                 // set newe shellCreator value
                                 shell = shellInstance.getShell();
                                 // save the last added shellCreator ....
                                 lastAddedShell = routeResult.getShell();
                                 // initialize shellCreator ...
                                 ClientLogger.get()
                                             .logDetailed("Router: attach shellCreator >>" + routeResult.getShell() + "<<",
                                                          1);
                                 shellInstance.getShell()
                                              .attachShell();
                                 ClientLogger.get()
                                             .logDetailed("Router: shellCreator >>" + routeResult.getShell() + "<< attached",
                                                          1);
                                 // start the application by calling url + '#'
                                 ClientLogger.get()
                                             .logDetailed("Router: initialize shellCreator >>" + routeResult.getShell() + "<< (route to '/')",
                                                          1);
                                 // get shellCreator matching root configs ...
                                 List<RouteConfig> shellMatchingRouteConfigurations = routerConfiguration.match(routeResult.getShell());
                                 for (RouteConfig routeConfiguraion : shellMatchingRouteConfigurations) {
                                   handleRouteConfig(routeConfiguraion,
                                                     routeResult,
                                                     finalHash);
                                 }
                                 postProcessHandleRouting(finalHash,
                                                          routeResult,
                                                          routeConfigurations);
                               }

                               private void detachShell() {
                                 ClientLogger.get()
                                             .logDetailed("Router: detach shellCreator >>" +
                                                          shell.getClass()
                                                               .getCanonicalName() +
                                                          "<<",
                                                          1);
                                 shell.detachShell();
                                 ClientLogger.get()
                                             .logDetailed("Router: shellCreator >>" +
                                                          shell.getClass()
                                                               .getCanonicalName() +
                                                          "<< detached",
                                                          1);
                               }

                               @Override
                               public void onShellNotFound() {
                                 RouterLogger.logUseErrorRoute(routeError);
                                 route(routeError,
                                       true);
                               }

                               @Override
                               public void onRoutingInterceptionException(RoutingInterceptionException e) {
                                 RouterLogger.logControllerInterceptsRouting(e.getControllerClassName(),
                                                                             e.getRoute(),
                                                                             e.getParameter());
                               }
                             });
        }
      } else {
        postProcessHandleRouting(hash,
                                 routeResult,
                                 routeConfigurations);
      }
    } else {
      this.plugin.route("#" + this.lastExecutedHash,
                        false);
    }
  }

  private void postProcessHandleRouting(String hash,
                                        RouteResult routeResult,
                                        List<RouteConfig> routeConfigurations) {
    // routing
    for (RouteConfig routeConfiguraion : routeConfigurations) {
      this.handleRouteConfig(routeConfiguraion,
                             routeResult,
                             hash);
    }
    this.shell.onAttachedComponent();
    RouterLogger.logShellOnAttachedComponentMethodCalled(this.shell.getClass()
                                                                   .getCanonicalName());
    // fire Router StateEvent
    this.fireRouterStateEvent(RouterState.ROUTING_DONE,
                              routeResult.getRoute());
  }

  private void handleRouteConfig(RouteConfig routeConfiguraion,
                                 RouteResult routeResult,
                                 String hash) {
    ControllerFactory.get()
                     .controller(routeConfiguraion.getClassName(),
                                 new ControllerCallback() {
                                   @Override
                                   public void onRoutingInterceptionException(RoutingInterceptionException e) {
                                     RouterLogger.logControllerInterceptsRouting(e.getControllerClassName(),
                                                                                 e.getRoute(),
                                                                                 e.getParameter());
                                     route(e.getRoute(),
                                           true,
                                           e.getParameter());
                                   }

                                   @Override
                                   public void onFinish(ControllerInstance controller) {
                                     doRouting(hash,
                                               routeResult,
                                               routeConfiguraion,
                                               controller);
                                   }
                                 },
                                 routeResult.getParameterValues()
                                            .toArray(new String[0]));
  }

  private void doRouting(String hash,
                         RouteResult hashResult,
                         RouteConfig routeConfiguration,
                         ControllerInstance controllerInstance) {
    if (Objects.isNull(controllerInstance.getController())) {
      this.naluErrorMessage = new NaluErrorMessage(AbstractRouter.NALU_ERROR_TYPE_NO_CONTROLLER_INSTANCE,
                                                   RouterLogger.logNoControllerFoundForHash(hash));
      if (!Objects.isNull(this.routeError)) {
        RouterLogger.logUseErrorRoute(this.routeError);
        this.route(this.routeError,
                   true);
      } else {
        // should never be seen!
        this.plugin.alert("Nalu: ups ... not found!");
      }
    } else {
      // inject the router instance into the controller!
      // (we do it for cahced and non cached controllers,
      // cause it does not matter!
      controllerInstance.getController()
                        .setRouter(this);
      // composites of the controller
      List<AbstractCompositeController<?, ?, ?>> compositeControllers = new ArrayList<>();
      // in case the controller is not cached, that means it is newly created, we have to deal with compüosites
      // handle composite of the controller
      RouterLogger.logControllerLookForCompositeController(controllerInstance.getController()
                                                                             .getClass()
                                                                             .getCanonicalName());
      // get a list of compistes for this controller (might be empty ...
      List<CompositeControllerReference> compositeForController = this.getCompositeForController(controllerInstance.getController()
                                                                                                                   .getClass()
                                                                                                                   .getCanonicalName());
      // in case the controller is not cached, we have to deal with composites!
      if (!controllerInstance.isChached()) {
        if (compositeForController.size() > 0) {
          RouterLogger.logControllerCompositeControllerFound(controllerInstance.getController()
                                                                               .getClass()
                                                                               .getCanonicalName(),
                                                             compositeForController.size());
          compositeForController.forEach(s -> {
            try {
              // check for composite loader
              if (ControllerCompositeConditionFactory.get()
                                                     .loadComposite(controllerInstance.getControllerClassName(),
                                                                    s.getComposite(),
                                                                    hashResult.getRoute(),
                                                                    hashResult.getParameterValues()
                                                                              .toArray(new String[hashResult.getParameterValues()
                                                                                                            .size()]))) {
                CompositeInstance compositeInstance = CompositeFactory.get()
                                                                      .getComposite(s.getComposite(),
                                                                                    hashResult.getParameterValues()
                                                                                              .toArray(new String[0]));
                if (compositeInstance == null) {
                  RouterLogger.logCompositeNotFound(controllerInstance.getController()
                                                                      .getClass()
                                                                      .getCanonicalName(),
                                                    s.getCompositeName());
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
                  RouterLogger.logCompositeControllerInjectedInController(compositeInstance.getComposite()
                                                                                           .getClass()
                                                                                           .getCanonicalName(),
                                                                          controllerInstance.getController()
                                                                                            .getClass()
                                                                                            .getCanonicalName());
                }
              }
            } catch (RoutingInterceptionException e) {
              RouterLogger.logControllerInterceptsRouting(e.getControllerClassName(),
                                                          e.getRoute(),
                                                          e.getParameter());
              this.route(e.getRoute(),
                         true,
                         e.getParameter());
            }
          });
        } else {
          RouterLogger.logControllerNoCompositeControllerFound(controllerInstance.getController()
                                                                                 .getClass()
                                                                                 .getCanonicalName());
        }
      }
      // add element to DOM
      this.append(routeConfiguration.getSelector(),
                  controllerInstance.getController());
      if (!controllerInstance.isChached()) {
        // append composite
        for (AbstractCompositeController<?, ?, ?> compositeController : compositeControllers) {
          CompositeControllerReference reference = null;
          for (CompositeControllerReference sfc : compositeForController) {
            if (compositeController.getClass()
                                   .getCanonicalName()
                                   .equals(sfc.getComposite())) {
              reference = sfc;
              break;
            }
          }
          if (reference != null) {
            this.append(reference.getSelector(),
                        compositeController);
            RouterLogger.logControllerOnAttachedCompositeController(controllerInstance.getController()
                                                                                      .getClass()
                                                                                      .getCanonicalName(),
                                                                    compositeController.getClass()
                                                                                       .getCanonicalName());
          }
        }
      }
      // call the onAttach method (for the component).
      // we will do it in both cases, cached and not cached!
      controllerInstance.getController()
                        .onAttach();
      RouterLogger.logControllerOnAttachedMethodCalled(controllerInstance.getController()
                                                                         .getClass()
                                                                         .getCanonicalName());
      compositeControllers.forEach(s -> {
        s.onAttach();
        RouterLogger.logControllerOnAttachedMethodCalled(s.getClass()
                                                          .getCanonicalName());
      });
      // in case the controller is cached, we call only activate  ...
      if (controllerInstance.isChached()) {
        compositeControllers.forEach(s -> {
          s.activate();
          RouterLogger.logCompositeComntrollerActivateMethodCalled(s.getClass()
                                                                    .getCanonicalName());
        });
        controllerInstance.getController()
                          .activate();
        RouterLogger.logControllerActivateMethodCalled(controllerInstance.getController()
                                                                         .getClass()
                                                                         .getCanonicalName());
      } else {
        compositeControllers.forEach(s -> {
          if (!s.isCached()) {
            s.start();
            RouterLogger.logCompositeComntrollerStartMethodCalled(s.getClass()
                                                                   .getCanonicalName());
          }
          s.activate();
          RouterLogger.logCompositeComntrollerActivateMethodCalled(s.getClass()
                                                                    .getCanonicalName());
        });
        controllerInstance.getController()
                          .start();
        RouterLogger.logControllerStartMethodCalled(controllerInstance.getController()
                                                                      .getClass()
                                                                      .getCanonicalName());
        controllerInstance.getController()
                          .activate();
        RouterLogger.logControllerActivateMethodCalled(controllerInstance.getController()
                                                                         .getClass()
                                                                         .getCanonicalName());
      }
      // save current hash
      this.lastExecutedHash = hash;
      // clear loo detection list ...
      this.loopDetectionList.clear();
    }
  }

  /**
   * Parse the hash and divides it into shellCreator, route and parameters
   *
   * @param route ths hash to parse
   * @return parse result
   * @throws com.github.nalukit.nalu.client.internal.route.RouterException in case no controller is found for the routing
   */
  public RouteResult parse(String route)
      throws RouterException {
    return RouteParser.get()
                      .parse(route,
                             this.shellConfiguration,
                             this.routerConfiguration);
  }

  private String addLeadindgSlash(String value) {
    if (value.startsWith("/")) {
      return value;
    }
    return "/" + value;
  }

  private boolean confirmRouting(List<RouteConfig> routeConfigurations) {
    AtomicBoolean isDirtyComposite = new AtomicBoolean(false);
    routeConfigurations.stream()
                       .map(config -> this.activeComponents.get(config.getSelector()))
                       .filter(Objects::nonNull)
                       .forEach(c -> {
                         Optional<String> optional = c.getComposites()
                                                      .values()
                                                      .stream()
                                                      .map(AbstractCompositeController::mayStop)
                                                      .filter(Objects::nonNull)
                                                      .findFirst();
                         if (optional.isPresent()) {
                           this.plugin.confirm(optional.get());
                           isDirtyComposite.set(true);
                         }
                       });

    return !isDirtyComposite.get() &&
           routeConfigurations.stream()
                              .map(config -> this.activeComponents.get(config.getSelector()))
                              .filter(Objects::nonNull)
                              .map(AbstractComponentController::mayStop)
                              .filter(Objects::nonNull)
                              .allMatch(message -> this.plugin.confirm(message));
  }

  private void stopController(List<RouteConfig> routeConfiguraions,
                              boolean replaceShell) {
    List<AbstractComponentController<?, ?, ?>> controllerList = new ArrayList<>();
    if (replaceShell) {
      controllerList.addAll(this.activeComponents.values());
    } else {
      controllerList.addAll(routeConfiguraions.stream()
                                              .map(config -> this.activeComponents.get(config.getSelector()))
                                              .filter(Objects::nonNull)
                                              .collect(Collectors.toList()));

    }
    controllerList.forEach(controller -> {
      // stop controller
      RouterLogger.logControllerHandlingStop(controller.getClass()
                                                       .getCanonicalName());
      RouterLogger.logControllerHandlingStopComposites(controller.getClass()
                                                                 .getCanonicalName());
      // stop compositeComntrollers
      controller.getComposites()
                .values()
                .forEach(s -> {
                  if (controller.isCached()) {
                    deactivateCompositeController(controller,
                                                  s);
                  } else {
                    if (s.isCached()) {
                      deactivateCompositeController(controller,
                                                    s);
                    } else {
                      stopCompositeController(controller,
                                              s);
                    }
                  }
                });

      RouterLogger.logControllerCompositesStopped(controller.getClass()
                                                            .getCanonicalName());
      if (controller.isCached()) {
        deactivateController(controller);
      } else {
        stopController(controller);
      }
    });
    routeConfiguraions.forEach(routeConfiguraion -> this.plugin.remove(routeConfiguraion.getSelector()));
    controllerList.forEach(c -> this.activeComponents.remove(c));
  }

  private void deactivateController(AbstractComponentController<?, ?, ?> controller) {
    // deactivate controller
    RouterLogger.logControllerdeactivateMethodWillBeCalled(controller.getClass()
                                                                     .getCanonicalName());
    controller.deactivate();
    RouterLogger.logControllerDeactivateMethodCalled(controller.getClass()
                                                               .getCanonicalName());
    controller.onDetach();
    RouterLogger.logControllerDetached(controller.getClass()
                                                 .getCanonicalName());
    controller.getComponent()
              .onDetach();
    RouterLogger.logComponentDetached(controller.getComponent()
                                                .getClass()
                                                .getCanonicalName());
    RouterLogger.logControllerDeactivated(controller.getClass()
                                                    .getCanonicalName());
  }

  private void stopController(AbstractComponentController<?, ?, ?> controller) {
    RouterLogger.logControllerdeactivateMethodWillBeCalled(controller.getClass()
                                                                     .getCanonicalName());
    controller.deactivate();
    RouterLogger.logControllerDeactivateMethodCalled(controller.getClass()
                                                               .getCanonicalName());
    controller.onDetach();
    RouterLogger.logControllerDetached(controller.getClass()
                                                 .getCanonicalName());
    // stop controller
    RouterLogger.logControllerStopMethodWillBeCalled(controller.getClass()
                                                               .getCanonicalName());
    controller.stop();
    RouterLogger.logControllerStopMethodCalled(controller.getClass()
                                                         .getCanonicalName());
    controller.onDetach();
    RouterLogger.logControllerDetached(controller.getClass()
                                                 .getCanonicalName());
    controller.removeHandlers();
    RouterLogger.logControllerRemoveHandlersMethodCalled(controller.getClass()
                                                                   .getCanonicalName());
    controller.getComponent()
              .onDetach();
    RouterLogger.logComponentDetached(controller.getComponent()
                                                .getClass()
                                                .getCanonicalName());
    controller.getComponent()
              .removeHandlers();
    RouterLogger.logComponentRemoveHandlersMethodCalled(controller.getComponent()
                                                                  .getClass()
                                                                  .getCanonicalName());
    RouterLogger.logControllerStopped(controller.getClass()
                                                .getCanonicalName());
  }

  private void deactivateCompositeController(AbstractComponentController<?, ?, ?> controller,
                                             AbstractCompositeController<?, ?, ?> compositeController) {
    RouterLogger.logCompositeControllerDeactivateMethodWillBeCalled(compositeController.getClass()
                                                                                       .getCanonicalName());
    compositeController.deactivate();
    RouterLogger.logCompositeControllerDeactivateMethodCalled(compositeController.getClass()
                                                                                 .getCanonicalName());
    compositeController.onDetach();
    RouterLogger.logCompositeControllerDetached(compositeController.getClass()
                                                                   .getCanonicalName());
    compositeController.getComponent()
                       .onDetach();
    RouterLogger.logCompositeComponentDetached(compositeController.getComponent()
                                                                  .getClass()
                                                                  .getCanonicalName());
    RouterLogger.logCompositeControllerDeactivated(controller.getClass()
                                                             .getCanonicalName());
  }

  private void stopCompositeController(AbstractComponentController<?, ?, ?> controller,
                                       AbstractCompositeController<?, ?, ?> compositeController) {
    RouterLogger.logCompositeControllerDeactivateMethodWillBeCalled(compositeController.getClass()
                                                                                       .getCanonicalName());
    compositeController.deactivate();
    RouterLogger.logCompositeControllerDeactivateMethodCalled(compositeController.getClass()
                                                                                 .getCanonicalName());
    RouterLogger.logCompositeControllerStopMethodWillBeCalled(compositeController.getClass()
                                                                                 .getCanonicalName());
    if (!compositeController.isCached()) {
      compositeController.stop();
      RouterLogger.logCompositeControllerRemoveMethodCalled(compositeController.getClass()
                                                                               .getCanonicalName());
    }
    compositeController.remove();
    RouterLogger.logCompositeControllerStopMethodCalled(compositeController.getClass()
                                                                           .getCanonicalName());
    compositeController.onDetach();
    RouterLogger.logCompositeControllerDetached(compositeController.getClass()
                                                                   .getCanonicalName());
    compositeController.removeHandlers();
    RouterLogger.logCompositeControllerRemoveHandlersMethodCalled(compositeController.getClass()
                                                                                     .getCanonicalName());
    compositeController.getComponent()
                       .onDetach();
    RouterLogger.logCompositeComponentDetached(compositeController.getComponent()
                                                                  .getClass()
                                                                  .getCanonicalName());
    compositeController.getComponent()
                       .removeHandlers();
    RouterLogger.logCompositeComponentRemoveHandlersMethodCalled(compositeController.getComponent()
                                                                                    .getClass()
                                                                                    .getCanonicalName());
    RouterLogger.logCompositeControllerStopped(controller.getClass()
                                                         .getCanonicalName());
  }

  private void append(String selector,
                      AbstractComponentController<?, ?, ?> controller) {
    if (this.plugin.attach(selector,
                           controller.asElement())) {
      // save to active components
      this.activeComponents.put(selector,
                                controller);
    }
  }

  private void append(String selector,
                      AbstractCompositeController<?, ?, ?> compositeController) {
    if (!this.plugin.attach(selector,
                            compositeController.asElement())) {
      String sb = "no element found, that matches selector >>" + selector + "<< --> Routing aborted!";
      RouterLogger.logSimple(sb,
                             1);
      this.naluErrorMessage = new NaluErrorMessage(AbstractRouter.NALU_ERROR_TYPE_NO_SELECTOR_FOUND,
                                                   sb);
      RouterLogger.logUseErrorRoute(this.routeError);
      this.route(this.routeError,
                 true);
    }
  }

  private List<CompositeControllerReference> getCompositeForController(String controllerClassName) {
    return this.compositeControllerReferences.stream()
                                             .filter(s -> controllerClassName.equals(s.getController()))
                                             .collect(Collectors.toList());
  }

  /**
   * The method routes to another screen. In case it is called,
   * it will:
   * <ul>
   * <li>create a new hash</li>
   * <li>update the url (in case history is desired)</li>
   * </ul>
   * Once the url gets updated, it triggers the onahshchange event and Nalu starts to work
   *
   * @param newRoute routing goal
   * @param parms    list of parameters [0 - n]
   */
  public void route(String newRoute,
                    String... parms) {
    // fire souring event ...
    this.fireRouterStateEvent(RouterState.START_ROUTING,
                              newRoute);
    // first, we track the new route (if there is a tracker!)
    if (!Objects.isNull(this.tracker)) {
      this.tracker.track(newRoute,
                         parms);
    }
    // let's do the routing!
    this.route(newRoute,
               false,
               parms);
  }

  private void route(String newRoute,
                     boolean replaceState,
                     String... parms) {
    String newRouteWithParams = this.generate(newRoute,
                                              parms);
    if (replaceState) {
      this.plugin.route(newRouteWithParams,
                        true);
    } else {
      this.plugin.route(newRouteWithParams,
                        false);
    }
    this.handleRouting(newRouteWithParams);
  }

  /**
   * Generates a new route!
   * <p>
   * If there is something to generate with parameters, the route
   * needs the same number of '*' in it.
   *
   * @param route route to navigate to
   * @param parms parameters of the route
   * @return generate String of new route
   */
  public String generate(String route,
                         String... parms) {
    return RouteParser.get()
                      .generate(route,
                                parms);
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

  /**
   * sets the eventbus inside the router
   *
   * @param eventBus Nalu application eventbus
   */
  public void setEventBus(SimpleEventBus eventBus) {
    this.eventBus = eventBus;
  }

  /**
   * Fires a router state event to inform the application about the state
   * of routing.
   *
   * @param state routing state
   */
  private void fireRouterStateEvent(RouterState state,
                                    String route) {
    String sb = "fire RouterEvent for route >>" + route + "<< with state >>" + state.name() + "<<";
    RouterLogger.logSimple(sb,
                           1);
    this.eventBus.fireEvent(new RouterStateEvent(state,
                                                 route));
  }

  /**
   * Returns a map of query parameters that was available at application start.
   *
   * @return list of query parameters at application start
   */
  public Map<String, String> getStartQueryParameters() {
    return this.plugin.getQueryParameters();
  }

}
