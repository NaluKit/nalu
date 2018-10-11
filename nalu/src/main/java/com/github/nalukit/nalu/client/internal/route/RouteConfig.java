package com.github.nalukit.nalu.client.internal.route;

import com.github.nalukit.nalu.client.Nalu;

import java.util.List;
import java.util.stream.Stream;

public class RouteConfig {

  /* route */
  private String route;

  /* parameters */
  private List<String> parameters;

  /* selector (where to add the element */
  private String selector;

  /* class name of the class which uses this configuration */
  private String className;

  public RouteConfig() {
  }

  public RouteConfig(String route,
                     List<String> parameters,
                     String selector,
                     String className) {
    super();

    //    this.parameters = new ArrayList<>();
    //
    //    this.parse(route);
    this.route = route;
    this.parameters = parameters;
    this.selector = selector;
    this.className = className;
  }

  private void parse(String route) {
    String parseValue = route;
    // route has parameter?
    if (parseValue.contains(Nalu.NALU_PARAMETER)) {
      // seperate route:
      this.route = parseValue.substring(0,
                                        parseValue.indexOf(Nalu.NALU_PARAMETER));
      // shorten String
      parseValue = parseValue.substring(parseValue.indexOf(Nalu.NALU_PARAMETER) + 2);
      // split String
      Stream.of(parseValue.split(Nalu.NALU_PARAMETER))
            .forEach(p -> this.parameters.add(p));
    } else {
      this.route = parseValue;
    }
  }

  public String getRoute() {
    return route;
  }

  public void setRoute(String route) {
    this.route = route;
  }

  public String getSelector() {
    return selector;
  }

  public void setSelector(String selector) {
    this.selector = selector;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public List<String> getParameters() {
    return parameters;
  }
}
