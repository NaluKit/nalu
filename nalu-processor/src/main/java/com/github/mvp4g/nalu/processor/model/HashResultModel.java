package com.github.mvp4g.nalu.processor.model;

import java.util.ArrayList;
import java.util.List;

public class HashResultModel {

  private String route;

  private List<String> parameterValues;

  public HashResultModel() {
    this(null,
         new ArrayList<>());
  }

  public HashResultModel(String route,
                         List<String> parameterValues) {
    this.route = route;
    this.parameterValues = parameterValues;
  }

  public HashResultModel(String route) {
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
