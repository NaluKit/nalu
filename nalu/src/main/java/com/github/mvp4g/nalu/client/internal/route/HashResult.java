package com.github.mvp4g.nalu.client.internal.route;

import java.util.ArrayList;
import java.util.List;

public class HashResult {

  private String route;

  private List<String> parameterValues;

  public HashResult() {
    this(null,
         new ArrayList<>());
  }

  public HashResult(String route,
                    List<String> parameterValues) {
    this.route = route;
    this.parameterValues = parameterValues;
  }

  public HashResult(String route) {
    this(route,
         new ArrayList<>());
  }

  public String getRoute() {
    return route;
  }

  public void setRoute(String route) {
    this.route = route;
  }

  public List<String> getParameterValues() {
    return parameterValues;
  }

  public void setParameterValues(List<String> parameterValues) {
    this.parameterValues = parameterValues;
  }
}
