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

package com.github.nalukit.nalu.client;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.AbstractCompositeController;
import com.github.nalukit.nalu.client.component.IsShell;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.filter.IsFilter;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.CompositeControllerReference;
import com.github.nalukit.nalu.client.internal.application.*;
import com.github.nalukit.nalu.client.internal.route.*;
import com.github.nalukit.nalu.client.plugin.IsPlugin;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Router {

  /* route in case of route error */
  private String routeErrorRoute;

  /* composite configuration */
  private List<CompositeControllerReference> compositeControllerReferences;

  /* List of the viewports of the application */
  private ShellConfiguration shellConfiguration;

  /* List of the routes of the application */
  private RouterConfiguration routerConfiguration;

  /* List of active components */
  private Map<String, AbstractComponentController<?, ?, ?>> activeComponents;

  //* hash of last successful routing */
  private String lastExecutedHash = "";

  /* last added shell */
  private String lastAddedShell;

  /* instnce of the shell */
  private IsShell shell;

  /* the plugin */
  private IsPlugin plugin;

  public Router(IsPlugin plugin,
                ShellConfiguration shellConfiguration,
                RouterConfiguration routerConfiguration,
                List<CompositeControllerReference> compositeControllerReferences) {
    super();
    // save te plugin
    this.plugin = plugin;
    // save the viewport configuration reference
    this.shellConfiguration = shellConfiguration;
    // save the router configuration reference
    this.routerConfiguration = routerConfiguration;
    // save the composite configuration reference
    this.compositeControllerReferences = compositeControllerReferences;
    // inistantiate lists, etc.
    this.activeComponents = new HashMap<>();
    // register event handler
    this.plugin.register(this::handleRouting);
  }

  private void handleRouting(String hash) {
    RouterLogger.logHandleHash(hash);
    // ok, everything is fine, route!
    // parse hash ...
    HashResult hashResult;
    try {
      hashResult = this.parse(hash);
    } catch (RouterException e) {
      // TODO Router exception ->  routing auf Nalu Fehler-Seite implementieren!
      if (Objects.isNull(this.routeErrorRoute) || this.routeErrorRoute.isEmpty()) {
        return;
      } else {
        RouterLogger.logNoMatchingRoute(hash,
                                        this.routeErrorRoute);
        // TODO
        //        this.route(this.routeErrorRoute,
        //                   true);
      }
      return;
    }
    // First we have to check if there is a filter
    // if there are filters ==>  filter the route
    for (IsFilter filter : this.routerConfiguration.getFilters()) {
      if (!filter.filter(addLeadindgSlash(hashResult.getRoute()),
                         hashResult.getParameterValues()
                                   .toArray(new String[0]))) {
        RouterLogger.logFilterInterceptsRouting(filter.getClass()
                                                      .getCanonicalName(),
                                                filter.redirectTo(),
                                                filter.parameters());
        this.route(filter.redirectTo(),
                   true,
                   filter.parameters());
        return;
      }
    }
    // search for a matching routing
    List<RouteConfig> routeConfigurations = this.routerConfiguration.match(hashResult.getRoute());
    // check whether or not the routing is possible ...
    if (this.confirmRouting(routeConfigurations)) {
      // call stop for all elements
      this.stopController(routeConfigurations);
      // handle shell
      //
      // in case shell changed or is not set, use the actual shell!
      if (this.lastAddedShell == null ||
          !hashResult.getShell()
                     .equals(this.lastAddedShell)) {
        // add shell to the viewport
        ShellConfig shellConfig = this.shellConfiguration.match(hashResult.getShell());
        if (!Objects.isNull(shellConfig)) {
          ShellInstance shellInstance = ShellFactory.get()
                                                    .shell(shellConfig.getClassName());
          if (!Objects.isNull(shellInstance)) {
            // in case there is an instance of an shell existing, call the onDetach mehtod inside the shell
            if (!Objects.isNull(this.shell)) {
              ClientLogger.get()
                          .logDetailed("Router: detach shell >>" +
                                       this.shell.getClass()
                                                 .getCanonicalName() +
                                       "<<",
                                       1);
              this.shell.detachShell();
              ClientLogger.get()
                          .logDetailed("Router: shell >>" +
                                       this.shell.getClass()
                                                 .getCanonicalName() +
                                       "<< detached",
                                       1);
            }
            // set newe shell value
            this.shell = shellInstance.getShell();
            // save the last added shell ....
            this.lastAddedShell = hashResult.getShell();
            // initialize shell ...
            ClientLogger.get()
                        .logDetailed("Router: attach shell >>" + hashResult.getShell() + "<<",
                                     1);
            shellInstance.getShell()
                         .attachShell();
            ClientLogger.get()
                        .logDetailed("Router: shell >>" + hashResult.getShell() + "<< attached",
                                     1);
            // start the application by calling url + '#'
            ClientLogger.get()
                        .logDetailed("Router: initialize shell >>" + hashResult.getShell() + "<< (route to '/')",
                                     1);
            // get shell matching root configs ...
            List<RouteConfig> shellMatchingRouteConfigurations = this.routerConfiguration.match(hashResult.getShell());
            for (RouteConfig routeConfiguraion : shellMatchingRouteConfigurations) {
              this.handleRouteConfig(routeConfiguraion,
                                     hashResult,
                                     hash);
            }
          }
        }
      }
      // routing
      for (RouteConfig routeConfiguraion : routeConfigurations) {
        this.handleRouteConfig(routeConfiguraion,
                               hashResult,
                               hash);
      }
      this.shell.onAttachedComponent();
      RouterLogger.logShellOnAttachedComponentMethodCalled(this.shell.getClass()
                                                                     .getCanonicalName());

    } else {
      this.plugin.route("#" + this.lastExecutedHash,
                        false);
    }
  }

  private void handleRouteConfig(RouteConfig routeConfiguraion,
                                 HashResult hashResult,
                                 String hash) {
    ControllerInstance controller;
    try {
      controller = ControllerFactory.get()
                                    .controller(routeConfiguraion.getClassName(),
                                                hashResult.getParameterValues()
                                                          .toArray(new String[0]));
    } catch (RoutingInterceptionException e) {
      RouterLogger.logControllerInterceptsRouting(e.getControllerClassName(),
                                                  e.getRoute(),
                                                  e.getParameter());
      this.route(e.getRoute(),
                 true,
                 e.getParameter());
      return;
    }
    // do the routing ...
    doRouting(hash,
              hashResult,
              routeConfiguraion,
              controller);
  }

  private void doRouting(String hash,
                         HashResult hashResult,
                         RouteConfig routeConfiguraion,
                         ControllerInstance controllerInstance) {
    if (Objects.isNull(controllerInstance.getController())) {
      RouterLogger.logNoControllerFoundForHash(hash);
      if (!Objects.isNull(this.routeErrorRoute)) {
        RouterLogger.logUseErrorRoute(this.routeErrorRoute);
        this.route(this.routeErrorRoute,
                   true);
      } else {
        this.plugin.alert("Ups ... not found!");
      }
    } else {
      // handle controller
      controllerInstance.getController()
                        .setRouter(this);
      // handle composite of the controller
      RouterLogger.logControllerLookForCompositeCotroller(controllerInstance.getController()
                                                                            .getClass()
                                                                            .getCanonicalName());
      List<CompositeControllerReference> compositeForController = this.getCompositeForController(controllerInstance.getController()
                                                                                                                   .getClass()
                                                                                                                   .getCanonicalName());
      List<AbstractCompositeController<?, ?, ?>> compositeControllers = new ArrayList<>();
      if (compositeForController.size() > 0) {
        RouterLogger.logControllerCompositeControllerFound(controllerInstance.getController()
                                                                             .getClass()
                                                                             .getCanonicalName(),
                                                           compositeForController.size());
        compositeForController.forEach(s -> {
          try {
            AbstractCompositeController<?, ?, ?> composite = CompositeFactory.get()
                                                                             .getComposite(s.getComposite(),
                                                                                           hashResult.getParameterValues()
                                                                                                     .toArray(new String[0]));
            if (composite == null) {
              RouterLogger.logCompositeNotFound(controllerInstance.getController()
                                                                  .getClass()
                                                                  .getCanonicalName(),
                                                s.getCompositeName());

            } else {
              compositeControllers.add(composite);
              // inject router into composite
              composite.setRouter(this);
              // inject composite into controller
              controllerInstance.getController()
                                .getComposites()
                                .put(s.getCompositeName(),
                                     composite);
              RouterLogger.logCompositeControllerInjectedInController(composite.getClass()
                                                                               .getCanonicalName(),
                                                                      controllerInstance.getController()
                                                                                        .getClass()
                                                                                        .getCanonicalName());
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
      this.append(routeConfiguraion.getSelector(),
                  controllerInstance.getController());
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
          // TODO Was soll das? kann das raus????
          this.append(reference.getSelector(),
                      compositeController);
          RouterLogger.logControllerOnAttachedCompositeController(controllerInstance.getController()
                                                                                    .getClass()
                                                                                    .getCanonicalName(),
                                                                  compositeController.getClass()
                                                                                     .getCanonicalName());
        }
      }
      controllerInstance.getController()
                        .onAttach();
      RouterLogger.logControllerOnAttachedMethodCalled(controllerInstance.getController()
                                                                         .getClass()
                                                                         .getCanonicalName());
      // call start method only in case the controller was not stored!
      if (!controllerInstance.isChached()) {
        compositeControllers.forEach(s -> {
          s.start();
          RouterLogger.logCompositeComntrollerStartMethodCalled(s.getClass()
                                                                 .getCanonicalName());
        });
        controllerInstance.getController()
                          .start();
        RouterLogger.logControllerStartMethodCalled(controllerInstance.getController()
                                                                      .getClass()
                                                                      .getCanonicalName());
      }
      this.lastExecutedHash = hash;
    }

  }

  /**
   * Parse the hash and divides it into shell, route and parameters
   *
   * @param hash ths hash to parse
   * @return parse result
   * @throws RouterException in case no controller is found for the routing
   */
  public HashResult parse(String hash)
      throws RouterException {
    HashResult hashResult = new HashResult();
    String hashValue = hash;
    // only the part after the first # is intresting:
    if (hashValue.contains("#")) {
      hashValue = hashValue.substring(hashValue.indexOf("#") + 1);
    }
    // extract shell first:
    if (hashValue.startsWith("/")) {
      hashValue = hashValue.substring(1);
    }
    // check, if there are more "/"
    if (hashValue.contains("/")) {
      hashResult.setShell("/" +
                          hashValue.substring(0,
                                              hashValue.indexOf("/")));
    } else {
      hashResult.setShell("/" + hashValue);
    }
    // check, if the shell exists ....
    Optional<String> optional = this.shellConfiguration.getShells()
                                                       .stream()
                                                       .map(ShellConfig::getRoute)
                                                       .filter(f -> f.equals(hashResult.getShell()))
                                                       .findAny();
    if (!optional.isPresent()) {
      StringBuilder sb = new StringBuilder();
      sb.append("no matching shell found for hash >>")
        .append(hash)
        .append("<< --> Routing aborted!");
      RouterLogger.logSimple(sb.toString(),
                             1);
      throw new RouterException(sb.toString());
    }
    // extract route first:
    hashValue = hash;
    if (hashValue.startsWith("/")) {
      hashValue = hashValue.substring(1);
    }
    if (hashValue.contains("/")) {
      String searchPart = hashValue;
      do {
        String finalSearchPart = "/" + searchPart;
        if (this.routerConfiguration.getRouters()
                                    .stream()
                                    .anyMatch(f -> f.getRoute()
                                                    .equals(finalSearchPart))) {
          hashResult.setRoute("/" + searchPart);
          break;
        } else {
          if (searchPart.contains("/")) {
            searchPart = searchPart.substring(0,
                                              searchPart.lastIndexOf("/"));
          } else {
            break;
          }
        }
      } while (searchPart.length() > 1);
      if (hashResult.getRoute() == null) {
        StringBuilder sb = new StringBuilder();
        sb.append("no matching route found for hash >>")
          .append(hash)
          .append("<< --> Routing aborted!");
        RouterLogger.logSimple(sb.toString(),
                               1);
        throw new RouterException(sb.toString());
      } else {
        if (!hashResult.getRoute()
                       .equals("/" + hashValue)) {
          String parametersFromHash = hashValue.substring(hashResult.getRoute()
                                                                    .length());
          // lets get the parameters!
          List<String> paramsList = Stream.of(parametersFromHash.split("/"))
                                          .collect(Collectors.toList());
          // reset slash in params ....
          paramsList.forEach(parm -> hashResult.getParameterValues()
                                               .add(parm.replace(Nalu.NALU_SLASH_REPLACEMENT,
                                                                 "/")));
          // check the number of parameter
          // if the hash contains more paremters then the configuration
          // accepts, throw an exception!
          for (RouteConfig routeConfig : this.routerConfiguration.getRouters()) {
            if (routeConfig.getRoute()
                           .equals(hashResult.getRoute())) {
              if (routeConfig.getParameters()
                             .size() <
                  hashResult.getParameterValues()
                            .size()) {
                throw new RouterException(RouterLogger.logWrongNumbersOfPrameters(hash,
                                                                                  hashResult.getRoute(),
                                                                                  routeConfig.getParameters()
                                                                                             .size(),
                                                                                  hashResult.getParameterValues()
                                                                                            .size()));

              }
            }
          }
        }
      }
    } else {
      String finalSearchPart = "/" + hashValue;
      if (this.routerConfiguration.getRouters()
                                  .stream()
                                  .anyMatch(f -> f.match(finalSearchPart))) {
        hashResult.setRoute("/" + hashValue);
      } else {
        throw new RouterException(RouterLogger.logNoMatchingRoute(hash));
      }
    }
    return hashResult;
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

  private void stopController(List<RouteConfig> routeConfiguraions) {
    routeConfiguraions.stream()
                      .map(config -> this.activeComponents.get(config.getSelector()))
                      .filter(Objects::nonNull)
                      .forEach(controller -> {
                        // stop controller
                        RouterLogger.logControllerHandlingStop(controller.getClass()
                                                                         .getCanonicalName());
                        RouterLogger.logControllerHandlingStopComposites(controller.getClass()
                                                                                   .getCanonicalName());
                        // stop compositeComntrollers
                        controller.getComposites()
                                  .values()
                                  .forEach(s -> {
                                    RouterLogger.logCompositeControllerStopMethodWillBeCalled(s.getClass()
                                                                                               .getCanonicalName());
                                    s.getClass()
                                     .getCanonicalName();
                                    s.stop();
                                    RouterLogger.logCompositeControllerStopMethodCalled(s.getClass()
                                                                                         .getCanonicalName());
                                    s.onDetach();
                                    RouterLogger.logCompositeControllerDetached(s.getClass()
                                                                                 .getCanonicalName());
                                    s.removeHandlers();
                                    RouterLogger.logCompositeControllerRemoveHandlersMethodCalled(s.getClass()
                                                                                                   .getCanonicalName());
                                    s.getComponent()
                                     .onDetach();
                                    RouterLogger.logCompositeComponentDetached(s.getComponent()
                                                                                .getClass()
                                                                                .getCanonicalName());
                                    s.getComponent()
                                     .removeHandlers();
                                    RouterLogger.logCompositeComponentRemoveHandlersMethodCalled(s.getComponent()
                                                                                                  .getClass()
                                                                                                  .getCanonicalName());
                                    RouterLogger.logCompositeControllerStopped(controller.getClass()
                                                                                         .getCanonicalName());
                                  });

                        RouterLogger.logControllerCompositesStoppped(controller.getClass()
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
                      });
    routeConfiguraions.forEach(routeConfiguraion -> this.plugin.remove(routeConfiguraion.getSelector()));
    routeConfiguraions.stream()
                      .map(config -> this.activeComponents.get(config.getSelector()))
                      .filter(Objects::nonNull)
                      .forEach(c -> this.activeComponents.remove(c));
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
      // TODO ... write log, das der append fehl geschalgen st!
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
   * <li>update the url</li>
   * </ul>
   * Once the url gets updated, it triggers the onahshchange event and Nalu starts to work
   *
   * @param newRoute routing goal
   * @param parms    list of parameters [0 - n]
   */
  public void route(String newRoute,
                    String... parms) {
    this.route(newRoute,
               false,
               parms);
  }

  private void route(String newRoute,
                     boolean replaceState,
                     String... parms) {
    String newRouteWithParams = this.generateHash(newRoute,
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

  public String generateHash(String route,
                             String... parms) {
    StringBuilder sb = new StringBuilder();
    if (route.startsWith("/")) {
      route = route.substring(1);
    }
    sb.append(route);
    if (parms != null) {
      Stream.of(parms)
            .filter(Objects::nonNull)
            .forEach(s -> sb.append("/")
                            .append(s.replace("/",
                                              Nalu.NALU_SLASH_REPLACEMENT)));
    }
    return sb.toString();
  }

  // TODO
  @Deprecated
  public void setShell(IsShell shell) {
    //this.shell = shell;
  }

  public void setRouteErrorRoute(String routeErrorRoute) {
    this.routeErrorRoute = routeErrorRoute;
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
  }

  /**
   * clears the chache
   */
  public void clearCache() {
    ControllerFactory.get()
                     .clearControllerCache();
  }
}
