package com.github.mvp4g.nalu.client.internal.route;

public class RouteConfig {

  private String route;
  private String selector;
  private String className;

  public RouteConfig() {
  }

  public RouteConfig(String route,
                     String selector,
                     String className) {
    this.route = route;
    this.selector = selector;
    this.className = className;
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
}
