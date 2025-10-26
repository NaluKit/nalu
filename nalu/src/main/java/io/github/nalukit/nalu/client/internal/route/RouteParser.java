package io.github.nalukit.nalu.client.internal.route;

import io.github.nalukit.nalu.client.Nalu;
import io.github.nalukit.nalu.client.application.event.LogEvent;
import io.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import io.github.nalukit.nalu.client.util.NaluUtils;

import org.gwtproject.event.shared.SimpleEventBus;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@NaluInternalUse
public class RouteParser {

  public final static RouteParser INSTANCE = new RouteParser();

  private SimpleEventBus eventBus;

  private RouteParser() {
  }

  public void setEventBus(SimpleEventBus eventBus) {
    this.eventBus = eventBus;
  }

  /**
   * Parse the hash and divides it into shellCreator, route and parameters
   *
   * @param route ths hash to parse
   * @return parse result
   * @throws RouterException in case no controller is found for the routing
   */
  @SuppressWarnings("StringSplitter")
  RouteResult parse(String route,
                    ShellConfiguration shellConfiguration,
                    RouterConfiguration routerConfiguration)
      throws RouterException {
    RouteResult routeResult = new RouteResult();
    String      routeValue  = route;
    // only the part after the first # is of interest:
    if (routeValue.contains("#")) {
      routeValue = routeValue.substring(routeValue.indexOf("#") + 1);
    }
    // extract shellCreator first:
    routeValue = NaluUtils.removeLeading("/", routeValue);
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
      this.eventBus.fireEvent(LogEvent.create()
                                      .sdmOnly(true)
                                      .addMessage(sb.toString()));
      throw new RouterException(sb.toString());
    }
    // extract route first:
    routeValue = route;
    routeValue = NaluUtils.removeLeading("/", routeValue);
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
        routeResult.setParameterKeys(optionalRouterConfig.get()
                                                         .getParameters());
        if (routeResult.getRoute()
                       .contains("*")) {
          String[] partsOfRoute = routeValue.split("/");
          String compareRoute = optionalRouterConfig.get()
                                                    .getRoute();
          compareRoute = NaluUtils.removeLeading("/", compareRoute);
          String[] partsOfRouteFromConfiguration = compareRoute.split("/");
          for (int i = 0; i < partsOfRouteFromConfiguration.length; i++) {
            if (partsOfRouteFromConfiguration[i].equals("*")) {
              if (partsOfRoute.length - 1 >= i) {
                String parameterValue = partsOfRoute[i].replace(RouterConstants.NALU_SLASH_REPLACEMENT,
                                                                "/");
                if (Nalu.isUsingColonForParametersInUrl()) {
                  if (parameterValue.length() > 0) {
                    parameterValue = NaluUtils.removeLeading(":", parameterValue);
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
        this.eventBus.fireEvent(LogEvent.create()
                                        .sdmOnly(true)
                                        .addMessage(sb.toString()));
        throw new RouterException(sb.toString());
      }
    } else {
      String finalSearchPart = "/" + routeValue;
      if (routerConfiguration.getRouters()
                             .stream()
                             .anyMatch(f -> f.match(finalSearchPart))) {
        routeResult.setRoute("/" + routeValue);
      } else {
        StringBuilder sb = new StringBuilder();
        sb.append("no matching route for hash >>")
          .append(route)
          .append("<< --> Routing aborted!");
        this.eventBus.fireEvent(LogEvent.create()
                                        .sdmOnly(true)
                                        .addMessage(sb.toString()));
        throw new RouterException(sb.toString());
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
   * @param route  route to navigate to
   * @param params parameters of the route
   * @return generate String of new route
   */
  String generate(String route,
                  String... params) {
    StringBuilder newRoute         = new StringBuilder();
    String        routeValue = route;
    routeValue = NaluUtils.removeLeading("/", routeValue);

    String[] partsOfRoute = routeValue.split("/");

    int parameterIndex = 0;
    for (String routePart : partsOfRoute) {
      newRoute.append("/");
      if ("*".equals(routePart) || routePart.startsWith(":") || (routePart.startsWith("{") && routePart.endsWith("}"))) {
        if (Nalu.isUsingColonForParametersInUrl()) {
          if (routePart.startsWith(":")) {
            newRoute.append(routePart);
          } else {
             newRoute.append(":");
          }
        }
        if (params.length - 1 >= parameterIndex) {
          if (!Objects.isNull(params[parameterIndex])) {
            newRoute.append(params[parameterIndex].replace("/",
                                                     RouterConstants.NALU_SLASH_REPLACEMENT));
          }
          parameterIndex++;
        }
      } else {
        newRoute.append(routePart);
      }
    }

    // in case there are more parameters then placesholders, we add them add the end!
    long numberOfPlaceHolders = Stream.of(partsOfRoute)
                                      .filter(s -> "*".equals(s) || s.startsWith(":") || (s.startsWith("{") && s.endsWith("}")))
                                      .count();
    if (params.length > numberOfPlaceHolders) {
      String sbExeption = "Warning: route >>" +
                          route +
                          "<< has less parameter placeholder >>" +
                          numberOfPlaceHolders +
                          "<< than the number of parameters in the list of parameters >>" +
                          params.length +
                          "<< --> adding Parameters add the end of the url";
      this.eventBus.fireEvent(LogEvent.create()
                                      .sdmOnly(true)
                                      .addMessage(sbExeption));
      for (int i = parameterIndex; i < params.length; i++) {
        newRoute.append("/");
        if (Nalu.isUsingColonForParametersInUrl()) {
          newRoute.append(":");
        }
        if (!Objects.isNull(params[parameterIndex])) {
          newRoute.append(params[parameterIndex].replace("/",
                                                   RouterConstants.NALU_SLASH_REPLACEMENT));
        } else {
          newRoute.append("null");
        }
        parameterIndex++;
      }
    }

    // remove leading '/'
    return NaluUtils.removeLeading("/", newRoute.toString());
  }

}
