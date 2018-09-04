package com.github.mvp4g.nalu.client;

import com.github.mvp4g.nalu.client.component.AbstractComponentController;
import com.github.mvp4g.nalu.client.exception.RoutingInterceptionException;
import com.github.mvp4g.nalu.client.filter.IsFilter;
import com.github.mvp4g.nalu.client.internal.ClientLogger;
import com.github.mvp4g.nalu.client.internal.application.ControllerFactory;
import com.github.mvp4g.nalu.client.internal.route.HashResult;
import com.github.mvp4g.nalu.client.internal.route.RouteConfig;
import com.github.mvp4g.nalu.client.internal.route.RouterConfiguration;
import com.github.mvp4g.nalu.client.internal.route.RouterException;
import com.github.mvp4g.nalu.client.plugin.IsPlugin;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Router {

  /* List of the routes of the application */
  private RouterConfiguration routerConfiguration;

  /* List of active components */
  private Map<String, AbstractComponentController<?, ?, ?>> activeComponents;

  //* hash of last successful routing */
  private String lastExecutedHash;

  /* the plugin */
  private IsPlugin plugin;

  public Router(IsPlugin plugin,
                RouterConfiguration routerConfiguration) {
    super();
    // save te plugin
    this.plugin = plugin;
    // save the router configuration reference
    this.routerConfiguration = routerConfiguration;
    // inistantiate lists, etc.
    this.activeComponents = new HashMap<>();
    // register event handler
    this.plugin.register(h -> this.handleRouting(h));
  }

  private String handleRouting(String hash) {
    StringBuilder sb = new StringBuilder();
    sb.append("Router: handleRouting for hash ->>")
      .append(hash)
      .append("<<");
    ClientLogger.get()
                .logDetailed(sb.toString(),
                             1);
    // ok, everything is fine, route!
    // parse hash ...
    HashResult hashResult = null;
    try {
      hashResult = this.parse(hash);
    } catch (RouterException e) {
      return null;
    }
    // First we have to check if there is a filter
    // if there are filters ==>  filter the rote
    for (IsFilter filter : this.routerConfiguration.getFilters()) {
      if (!filter.filter(addLeadgindSledge(hashResult.getRoute()),
                         hashResult.getParameterValues()
                                   .toArray(new String[0]))) {
        StringBuilder sb01 = new StringBuilder();
        sb01.append("Router: filter >>")
            .append(filter.getClass()
                          .getCanonicalName())
            .append("<< intercepts routing! New route: >>")
            .append(filter.redirectTo())
            .append("<<");
        if (Arrays.asList(filter.parameters())
                  .size() > 0) {
          sb01.append(" with parameters: ");
          Stream.of(filter.parameters())
                .forEach(p -> sb01.append(">>")
                                  .append(p)
                                  .append("<< "));
        }
        ClientLogger.get()
                    .logSimple(sb01.toString(),
                               1);
        this.route(filter.redirectTo(),
                   true,
                   filter.parameters());
        return null;
      }
    }
    // search for a matching routing
    List<RouteConfig> routeConfiguraions = this.routerConfiguration.match(hashResult.getRoute());
    // chech weather or not the routing is possible ...
    if (this.confirmRouting(routeConfiguraions)) {
      // call stop for all elements
      this.stopController(routeConfiguraions);
      // routing
      for (RouteConfig routeConfiguraion : routeConfiguraions) {
        AbstractComponentController<?, ?, ?> controller = null;
        try {
          controller = ControllerFactory.get()
                                        .controller(routeConfiguraion.getClassName(),
                                                    hashResult.getParameterValues()
                                                              .toArray(new String[0]));
        } catch (RoutingInterceptionException e) {
          StringBuilder sb03 = new StringBuilder();
          sb03.append("Router: create controller >>")
              .append(e.getControllerClassName())
              .append("<< intercepts routing! New route: >>")
              .append(e.getRoute())
              .append("<<");
          if (Arrays.asList(e.getParameter())
                    .size() > 0) {
            sb03.append(" with parameters: ");
            Stream.of(e.getParameter())
                  .forEach(p -> sb03.append(">>")
                                    .append(p)
                                    .append("<< "));
          }
          ClientLogger.get()
                      .logSimple(sb03.toString(),
                                 0);
          this.route(e.getRoute(),
                     true,
                     e.getParameter());
          return null;
        }
        if (!Objects.isNull(controller)) {
          controller.setRouter(this);
          this.append(routeConfiguraion.getSelector(),
                      controller);
          controller.onAttach();
          controller.start();
          this.lastExecutedHash = hash;
        } else {
          this.plugin.alert("Ups ... not found!");
        }
      }
    } else {
      this.plugin.route("#" + this.lastExecutedHash,
                        false);
    }
    return null;
  }

  /**
   * Parse the hash and divides it into route and parameters
   *
   * @param hash ths hash to parse
   * @return parse result
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
        StringBuilder sb01 = new StringBuilder();
        sb01.append("no matching route for hash >>")
            .append(hash)
            .append("<< --> Routing aborded!");
        ClientLogger.get()
                    .logSimple(sb01.toString(),
                               1);
        throw new RouterException(sb01.toString());
      } else {
        if (!hashResult.getRoute()
                       .equals("/" + hashValue)) {
          String parametersFromHash = hashValue.substring(hashResult.getRoute()
                                                                    .length());
          // lets get the parameters!
          List<String> paramsList = Stream.of(parametersFromHash.split("/"))
                                          .collect(Collectors.toList());
          // reset sledge in parms ....
          paramsList.forEach(parm -> hashResult.getParameterValues()
                                               .add(parm.replace(Nalu.NALU_SLEDGE_REPLACEMENT,
                                                                 "/")));
          // check the number of parameter
          // if the hash contains more paremters then the configuration
          // accepts, throw an exception!
          for (RouteConfig routeConfig : this.routerConfiguration.getRouters()) {
            if (routeConfig.getRoute()
                           .equals(hashResult.getRoute())) {
              if (routeConfig.getPraameters()
                             .size() <
                  hashResult.getParameterValues()
                            .size()) {
                StringBuilder sb01 = new StringBuilder();
                sb01.append("hash >>")
                    .append(hash)
                    .append("<< --> found routing >>")
                    .append(hashResult.getRoute())
                    .append("<< -> too much parameters! Expeted >>")
                    .append(routeConfig.getPraameters()
                                       .size())
                    .append("<< - found >>")
                    .append(hashResult.getParameterValues()
                                      .size())
                    .append("<<");
                ClientLogger.get()
                            .logSimple(sb01.toString(),
                                       1);
                throw new RouterException(sb01.toString());

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
        StringBuilder sb01 = new StringBuilder();
        sb01.append("no matching route for hash >>")
            .append(hash)
            .append("<< --> Routing aborded!");
        ClientLogger.get()
                    .logSimple(sb01.toString(),
                               1);
        throw new RouterException(sb01.toString());
      }
    }

    //    if (hashValue.contains("/")) {
    //      hashResult.setRoute(hashValue.substring(0,
    //                                              hashValue.indexOf("/")));
    //      String parametersFromHash = hashValue.substring(hashValue.indexOf("/") + 1);
    //      // lets get the parameters!
    //      List<String> paramsList = Stream.of(parametersFromHash.split("/"))
    //                                      .collect(Collectors.toList());
    //      // reset sledge in parms ....
    //      paramsList.forEach(parm -> hashResult.getParameterValues()
    //                                           .add(parm.replace(Nalu.NALU_SLEDGE_REPLACEMENT,
    //                                                             "/")));
    //    } else {
    //      hashResult.setRoute(hashValue);
    //    }
    //    // log result
    //    StringBuilder sb = new StringBuilder();
    //    sb.append("Router: parse hash >>")
    //      .append(hash)
    //      .append("<< --> New route: >>")
    //      .append(hashResult.getRoute())
    //      .append("<<");
    //    if (hashResult.getParameterValues()
    //                  .size() > 0) {
    //      sb.append(" with parameters: ");
    //      hashResult.getParameterValues()
    //                .forEach(p -> sb.append(">>")
    //                                .append(p)
    //                                .append("<< "));
    //    }
    //    ClientLogger.get()
    //                .logDetailed(sb.toString(),
    //                             2);
    return hashResult;
  }

  private String addLeadgindSledge(String value) {
    if (value.startsWith("/")) {
      return value;
    }
    return "/" + value;
  }

  private boolean confirmRouting(List<RouteConfig> routeConfiguraions) {
    return routeConfiguraions.stream()
                             .map(config -> this.activeComponents.get(config.getSelector()))
                             .filter(Objects::nonNull)
                             .map(AbstractComponentController::mayStop)
                             .filter(Objects::nonNull)
                             .allMatch(message -> this.plugin.confirm(message)
                                       //     DomGlobal.window.confirm(message);
                             );
  }

  private void stopController(List<RouteConfig> routeConfiguraions) {
    routeConfiguraions.stream()
                      .map(config -> this.activeComponents.get(config.getSelector()))
                      .filter(Objects::nonNull)
                      .forEach(abstractComponentController -> {
                        abstractComponentController.stop();
                        abstractComponentController.onDetach();
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
                                              Nalu.NALU_SLEDGE_REPLACEMENT)));
    }
    return sb.toString();
  }
}
