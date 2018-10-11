package com.github.nalukit.nalu.client;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.AbstractCompositeController;
import com.github.nalukit.nalu.client.component.IsShell;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.filter.IsFilter;
import com.github.nalukit.nalu.client.internal.CompositeControllerReference;
import com.github.nalukit.nalu.client.internal.application.CompositeFactory;
import com.github.nalukit.nalu.client.internal.application.ControllerFactory;
import com.github.nalukit.nalu.client.internal.route.HashResult;
import com.github.nalukit.nalu.client.internal.route.RouteConfig;
import com.github.nalukit.nalu.client.internal.route.RouterConfiguration;
import com.github.nalukit.nalu.client.internal.route.RouterException;
import com.github.nalukit.nalu.client.plugin.IsPlugin;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Router {

  /* Shell */
  private IsShell shell;

  /* route in case of route error */
  private String routeErrorRoute;

  /* composite configuration */
  private List<CompositeControllerReference> compositeControllerReferences;

  /* List of the routes of the application */
  private RouterConfiguration routerConfiguration;

  /* List of active components */
  private Map<String, AbstractComponentController<?, ?, ?>> activeComponents;

  //* hash of last successful routing */
  private String lastExecutedHash = "";

  /* the plugin */
  private IsPlugin plugin;

  public Router(IsPlugin plugin,
                RouterConfiguration routerConfiguration,
                List<CompositeControllerReference> compositeControllerReferences) {
    super();
    // save the shell
    this.shell = shell;
    // save te plugin
    this.plugin = plugin;
    // save the router configuration reference
    this.routerConfiguration = routerConfiguration;
    // save the composite configuration reference
    this.compositeControllerReferences = compositeControllerReferences;
    // inistantiate lists, etc.
    this.activeComponents = new HashMap<>();
    // register event handler
    this.plugin.register(h -> this.handleRouting(h));
  }

  private String handleRouting(String hash) {
    RouterLogger.logHandleHash(hash);
    // ok, everything is fine, route!
    // parse hash ...
    HashResult hashResult = null;
    try {
      hashResult = this.parse(hash);
    } catch (RouterException e) {
      if (Objects.isNull(this.routeErrorRoute) || this.routeErrorRoute.isEmpty()) {
        return null;
      } else {
        RouterLogger.logNoMatchingRoute(hash,
                                        this.routeErrorRoute);
        this.route(this.routeErrorRoute,
                   true);
      }
      return null;
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
        return null;
      }
    }
    // search for a matching routing
    List<RouteConfig> routeConfigurations = this.routerConfiguration.match(hashResult.getRoute());
    // check whether or not the routing is possible ...
    if (this.confirmRouting(routeConfigurations)) {
      // call stop for all elements
      this.stopController(routeConfigurations);
      // routing
      for (RouteConfig routeConfiguraion : routeConfigurations) {
        AbstractComponentController<?, ?, ?> controller;
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
          return null;
        }
        // do the routing ...
        doRouting(hash,
                  hashResult,
                  routeConfiguraion,
                  controller);
      }
    } else {
      this.plugin.route("#" + this.lastExecutedHash,
                        false);
    }
    return null;
  }

  private void doRouting(String hash,
                         HashResult hashResult,
                         RouteConfig routeConfiguraion,
                         AbstractComponentController<?, ?, ?> controller) {
    if (Objects.isNull(controller)) {
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
      controller.setRouter(this);
      // handle composite of the controller
      RouterLogger.logControllerLookForCompositeCotroller(controller.getClass()
                                                                    .getCanonicalName());
      List<CompositeControllerReference> compositeForController = this.getCompositeForController(controller.getClass()
                                                                                                           .getCanonicalName());
      List<AbstractCompositeController<?, ?, ?>> compositeControllers = new ArrayList<>();
      if (compositeForController.size() > 0) {
        RouterLogger.logControllerCompositeControllerFound(controller.getClass()
                                                                     .getCanonicalName(),
                                                           compositeForController.size());
        compositeForController.forEach(s -> {
          try {
            AbstractCompositeController<?, ?, ?> composite = CompositeFactory.get()
                                                                             .getComposite(s.getComposite(),
                                                                                           hashResult.getParameterValues()
                                                                                                     .toArray(new String[0]));
            if (composite == null) {
              RouterLogger.logCompositeNotFound(controller.getClass()
                                                          .getCanonicalName(),
                                                s.getCompositeName());

            } else {
              compositeControllers.add(composite);
              // inject router into composite
              composite.setRouter(this);
              // inject composite into controller
              controller.getComposites()
                        .put(s.getCompositeName(),
                             composite);
              RouterLogger.logCompositeControllerInjectedInController(composite.getClass()
                                                                               .getCanonicalName(),
                                                                      controller.getClass()
                                                                                .getCanonicalName());
            }
          } catch (RoutingInterceptionException e) {
            RouterLogger.logControllerInterceptsRouting(e.getControllerClassName(),
                                                        e.getRoute(),
                                                        e.getParameter());
            this.route(e.getRoute(),
                       true,
                       e.getParameter());
            return;
          }
        });
      } else {
        RouterLogger.logControllerNoCompositeControllerFound(controller.getClass()
                                                                       .getCanonicalName());
      }
      this.append(routeConfiguraion.getSelector(),
                  controller);
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
          RouterLogger.logControllerOnAttachedCompositeController(controller.getClass()
                                                                            .getCanonicalName(),
                                                                  compositeController.getClass()
                                                                                     .getCanonicalName());
        }
      }
      controller.onAttach();
      RouterLogger.logControllerOnAttachedMethodCalled(controller.getClass()
                                                                 .getCanonicalName());
      compositeControllers.forEach(s -> {
        s.start();
        RouterLogger.logCompositeComntrollerStartMethodCalled(s.getClass()
                                                               .getCanonicalName());
      });
      controller.start();
      RouterLogger.logControllerStartMethodCalled(controller.getClass()
                                                            .getCanonicalName());
      this.shell.onAttachedComponent();
      RouterLogger.logShellOnAttachedComponentMethodCalled(controller.getClass()
                                                                     .getCanonicalName());
      this.lastExecutedHash = hash;
    }

  }

  /**
   * Parse the hash and divides it into route and parameters
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
    // extract route first:
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
        sb.append("no matching route for hash >>")
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
                                  .anyMatch(f -> f.getRoute()
                                                  .equals(finalSearchPart))) {
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
                        // stop compositeComntrollers
                        controller.getComposites()
                                  .values()
                                  .stream()
                                  .forEach(s -> {
                                    RouterLogger.logFilterStopMethodWillBeCalled(controller.getClass()
                                                                                           .getCanonicalName(),
                                                                                 s.getClass()
                                                                                  .getCanonicalName());
                                    s.stop();
                                    RouterLogger.logFilterStopMethodCalled(controller.getClass()
                                                                                     .getCanonicalName(),
                                                                           s.getClass()
                                                                            .getCanonicalName());
                                  });

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
    if (this.plugin.attach(selector,
                           compositeController.asElement())) {
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
      sb.append(route.substring(1));
    } else {
      sb.append(route);
    }
    if (parms != null) {
      Stream.of(parms)
            .filter(Objects::nonNull)
            .forEach(s -> sb.append("/")
                            .append(s.replace("/",
                                              Nalu.NALU_SLASH_REPLACEMENT)));
    }
    return sb.toString();
  }

  public void setShell(IsShell shell) {
    this.shell = shell;
  }

  public void setRouteErrorRoute(String routeErrorRoute) {
    this.routeErrorRoute = routeErrorRoute;
  }
}
