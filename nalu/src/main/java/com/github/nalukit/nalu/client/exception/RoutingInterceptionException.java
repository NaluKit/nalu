package com.github.nalukit.nalu.client.exception;

@SuppressWarnings("serial")
public class RoutingInterceptionException
    extends Exception {

  private String controllerClassName;

  private String route;

  private String[] parameter;

  public RoutingInterceptionException(String controllerClassName,
                                      String route,
                                      String... parameter) {
    this.controllerClassName = controllerClassName;
    this.route = route;
    this.parameter = parameter;
  }

  public String getControllerClassName() {
    return controllerClassName;
  }

  public String getRoute() {
    return route;
  }

  public String[] getParameter() {
    return parameter;
  }
}
