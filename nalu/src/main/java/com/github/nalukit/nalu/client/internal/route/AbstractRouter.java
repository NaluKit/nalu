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

import com.github.nalukit.nalu.client.NaluConstants;
import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.AbstractCompositeController;
import com.github.nalukit.nalu.client.component.IsShell;
import com.github.nalukit.nalu.client.event.NaluErrorEvent;
import com.github.nalukit.nalu.client.event.RouterStateEvent;
import com.github.nalukit.nalu.client.event.RouterStateEvent.RouterState;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.filter.IsFilter;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.CompositeControllerReference;
import com.github.nalukit.nalu.client.internal.PropertyFactory;
import com.github.nalukit.nalu.client.internal.PropertyFactory.ErrorHandlingMethod;
import com.github.nalukit.nalu.client.internal.application.*;
import com.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin;
import com.github.nalukit.nalu.client.seo.SeoDataProvider;
import com.github.nalukit.nalu.client.tracker.IsTracker;
import org.gwtproject.event.shared.SimpleEventBus;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

abstract class AbstractRouter
    implements ConfiguratableRouter {

  // the plugin
  IsNaluProcessorPlugin plugin;
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
                 boolean stayOnSite,
                 ErrorHandlingMethod errorHandlingMethod) {
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
                             stayOnSite,
                             errorHandlingMethod);
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
      // loop discovered .... -> create message
      String message = RouterLogger.logLoopDetected(this.loopDetectionList.get(0));
      // Fire error event ....
      this.eventBus.fireEvent(NaluErrorEvent.createNaluError()
                                            .errorId(NaluConstants.NALU_ERROR_LOOP_DETECTED)
                                            .message(message)
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
    } catch (RouterException e) {
      this.handleRouterException(hash,
                                 e);
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
                                 eventBus.fireEvent(NaluErrorEvent.createNaluError()
                                                                  .errorId(NaluConstants.NALU_ERROR_SHELL_NOT_FOUND)
                                                                  .message("no shell found for route: >>" + shellConfig.getRoute() + "<<")
                                                                  .route(shellConfig.getRoute()));
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

  public void handleRouterException(String hash,
                                    RouterException e) {
    // fire Router StateEvent
    this.fireRouterStateEvent(RouterState.ROUTING_ABORTED,
                              hash);
    // Fire error event ....
    this.eventBus.fireEvent(NaluErrorEvent.createNaluError()
                                          .errorId(NaluConstants.NALU_ERROR_ROUTING_EXCEPTION)
                                          .message(RouterLogger.logNoMatchingRoute(hash))
                                          .route(hash));
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
    // update seo-meta-data
    SeoDataProvider.get()
                   .update();
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
      eventBus.fireEvent(NaluErrorEvent.createNaluError()
                                       .errorId(NaluConstants.NALU_ERROR_NO_CONTROLLER_INSTANCE_FOUND)
                                       .message(RouterLogger.logNoControllerFoundForHash(hash))
                                       .route(hashResult.getRoute()));
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
                                                                      .getComposite(controllerInstance.getControllerClassName(),
                                                                                    s.getComposite(),
                                                                                    s.isScopeGlobal(),
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
      } else {
        // in case we have a cached controller, we need to look for global composites
        // and append them!
        List<CompositeControllerReference> globalComposite = compositeForController.stream()
                                                                                   .filter(CompositeControllerReference::isScopeGlobal)
                                                                                   .collect(Collectors.toList());
        for (CompositeControllerReference compositeControllerReference : globalComposite) {
          if (ControllerCompositeConditionFactory.get()
                                                 .loadComposite(controllerInstance.getControllerClassName(),
                                                                compositeControllerReference.getComposite(),
                                                                hashResult.getRoute(),
                                                                hashResult.getParameterValues()
                                                                          .toArray(new String[hashResult.getParameterValues()
                                                                                                        .size()]))) {
            try {
              CompositeInstance compositeInstance = CompositeFactory.get()
                                                                    .getComposite(controllerInstance.getControllerClassName(),
                                                                                  compositeControllerReference.getComposite(),
                                                                                  true,
                                                                                  hashResult.getParameterValues()
                                                                                            .toArray(new String[0]));
              this.append(compositeControllerReference.getSelector(),
                          compositeInstance.getComposite());
              RouterLogger.logCachedControllerOnAttachedGlobalCompositeController(controllerInstance.getController()
                                                                                                    .getClass()
                                                                                                    .getCanonicalName(),
                                                                                  compositeControllerReference.getComposite());
            } catch (RoutingInterceptionException e) {
              RouterLogger.logControllerInterceptsRouting(e.getControllerClassName(),
                                                          e.getRoute(),
                                                          e.getParameter());
              this.route(e.getRoute(),
                         true,
                         e.getParameter());
              return;
            }
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
        // let's call active for all related composite
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
            // in case we are cached globally we need to set cached
            // to true after the first time the
            // composite is created
            if (s.isCachedGlobal()) {
              s.setCached(true);
            }
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
    String decodedUrl = this.plugin.decode(route);
    return RouteParser.get()
                      .parse(decodedUrl,
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
      eventBus.fireEvent(NaluErrorEvent.createNaluError()
                                       .errorId(NaluConstants.NALU_ERROR_SELECOR_NOT_FOUND)
                                       .message(sb)
                                       .route("NoRouteAvailable"));
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
