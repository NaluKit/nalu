package com.github.mvp4g.nalu.client.internal.exception;

@SuppressWarnings("serial")
public class RoutingInterceptionByControllerException
  extends Exception {

  private String controllerClassName;

  private String route;

  private String[] parameter;

  public RoutingInterceptionByControllerException(String controllerClassName,
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
