package com.github.nalukit.nalu.client.internal.route;

import com.github.nalukit.nalu.client.Nalu;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class RouteParser {

  private static RouteParser instance = new RouteParser();

  private RouteParser() {
  }

  public static RouteParser get() {
    return instance;
  }

  /**
   * Parse the hash and divides it into shellCreator, route and parameters
   *
   * @param route ths hash to parse
   * @return parse result
   * @throws com.github.nalukit.nalu.client.internal.route.RouterException in case no controller is found for the routing
   */
  @SuppressWarnings("StringSplitter")
  RouteResult parse(String route,
                    ShellConfiguration shellConfiguration,
                    RouterConfiguration routerConfiguration)
      throws RouterException {
    RouteResult routeResult = new RouteResult();
    String routeValue = route;
    // only the part after the first # is of interest:
    if (routeValue.contains("#")) {
      routeValue = routeValue.substring(routeValue.indexOf("#") + 1);
    }
    // extract shellCreator first:
    if (routeValue.startsWith("/")) {
      routeValue = routeValue.substring(1);
    }
    // check, if there are more "/"
    if (routeValue.contains("/")) {
      routeResult.setShell("/" +
                           routeValue.substring(0,
                                                routeValue.indexOf("/")));
    } else {
      routeResult.setShell("/" + routeValue);
    }
    // check, if the shellCreator exists ....
    Optional<String> optional = shellConfiguration.getShells()
                                                  .stream()
                                                  .map(ShellConfig::getRoute)
                                                  .filter(f -> f.equals(routeResult.getShell()))
                                                  .findAny();
    if (optional.isPresent()) {
      routeResult.setShell(optional.get());
    } else {
      StringBuilder sb = new StringBuilder();
      sb.append("no matching shellCreator found for route >>")
        .append(route)
        .append("<< --> Routing aborted!");
      RouterLogger.logSimple(sb.toString(),
                             1);
      throw new RouterException(sb.toString());
    }
    // extract route first:
    routeValue = route;
    if (routeValue.startsWith("/")) {
      routeValue = routeValue.substring(1);
    }
    if (routeValue.contains("/")) {
      String searchRoute = routeValue;
      Optional<RouteConfig> optionalRouterConfig = routerConfiguration.getRouters()
                                                                      .stream()
                                                                      .filter(rc -> Nalu.match(searchRoute,
                                                                                               rc.getRoute()))
                                                                      .findFirst();
      if (optionalRouterConfig.isPresent()) {
        routeResult.setRoute(optionalRouterConfig.get()
                                                 .getRoute());
        if (routeResult.getRoute()
                       .contains("*")) {
          String[] partsOfRoute = routeValue.split("/");
          String compareRoute = optionalRouterConfig.get()
                                                    .getRoute();
          if (compareRoute.startsWith("/")) {
            compareRoute = compareRoute.substring(1);
          }
          String[] partsOfRouteFromConfiguration = compareRoute.split("/");
          for (int i = 0; i < partsOfRouteFromConfiguration.length; i++) {
            if (partsOfRouteFromConfiguration[i].equals("*")) {
              if (partsOfRoute.length - 1 >= i) {
                String parameterValue = partsOfRoute[i].replace(RouterConstants.NALU_SLASH_REPLACEMENT,
                                                                "/");
                if (Nalu.isUsingColonForParametersInUrl()) {
                  if (parameterValue.length() > 0) {
                    if (parameterValue.startsWith(":")) {
                      parameterValue = parameterValue.substring(1);
                    }
                  }
                }
                routeResult.getParameterValues()
                           .add(parameterValue);
              } else {
                routeResult.getParameterValues()
                           .add("");
              }
            }
          }
        }
      } else {
        StringBuilder sb = new StringBuilder();
        sb.append("no matching route found for route >>")
          .append(route)
          .append("<< --> Routing aborted!");
        RouterLogger.logSimple(sb.toString(),
                               1);
        throw new RouterException(sb.toString());
      }
    } else {
      String finalSearchPart = "/" + routeValue;
      if (routerConfiguration.getRouters()
                             .stream()
                             .anyMatch(f -> f.match(finalSearchPart))) {
        routeResult.setRoute("/" + routeValue);
      } else {
        throw new RouterException(RouterLogger.logNoMatchingRoute(route));
      }
    }
    return routeResult;
  }

  /**
   * Generates a new route!
   * <p>
   * If there is something to generate with parameters, the route
   * needs the same number of '*' in it.
   *
   * @param route route to navigate to
   * @param params parameters of the route
   * @return generate String of new route
   */
  String generate(String route,
                  String... params) {
    StringBuilder sb = new StringBuilder();
    String routeValue = route;
    if (routeValue.startsWith("/")) {
      routeValue = routeValue.substring(1);
    }
    String[] partsOfRoute = routeValue.split("/");

    int parameterIndex = 0;
    for (String s : partsOfRoute) {
      sb.append("/");
      if ("*".equals(s) || s.startsWith(":")) {
        if (Nalu.isUsingColonForParametersInUrl()) {
          sb.append(":");
        }
        if (params.length - 1 >= parameterIndex) {
          sb.append(params[parameterIndex].replace("/",
                                                  RouterConstants.NALU_SLASH_REPLACEMENT));
          parameterIndex++;
        }
      } else {
        sb.append(s);
      }
    }

    // in case there are more parameters then placesholders, we add them add the end!
    long numberOfPlaceHolders = Stream.of(partsOfRoute)
                                      .filter("*"::equals)
                                      .count();
    if (params.length > numberOfPlaceHolders) {
      String sbExeption = "Warning: route >>" + route + "<< has less parameter placeholder >>" + numberOfPlaceHolders + "<< than the number of parameters in the list of parameters >>" + params.length + "<< --> adding Prameters add the end of the url";
      RouterLogger.logSimple(sbExeption,
                             1);
      for (int i = parameterIndex; i < params.length; i++) {
        sb.append("/");
        if (Nalu.isUsingColonForParametersInUrl()) {
          sb.append(":");
        }
        if (!Objects.isNull(params[parameterIndex])) {
          sb.append(params[parameterIndex].replace("/",
                                                  RouterConstants.NALU_SLASH_REPLACEMENT));
        } else {
          sb.append("null");
        }
        parameterIndex++;
      }
    }

    // remove leading '/'
    String generatedRoute = sb.toString();
    if (generatedRoute.startsWith("/")) {
      generatedRoute = generatedRoute.substring(1);
    }
    StringBuilder parameters = new StringBuilder();
    for (int i = 0; i < params.length; i++) {
      parameters.append(params[i]);
      if (params.length - 1 < i) {
        parameters.append(",");
      }
    }
    String sblog = "generated route >>" + generatedRoute + "<< -> created from >>" + route + "<< with parameters >>" + parameters + "<<";
    RouterLogger.logSimple(sblog,
                           1);
    return generatedRoute;
  }

}
