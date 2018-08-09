package com.github.mvp4g.nalu.client.internal.route;

import com.github.mvp4g.nalu.client.filter.IsFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RouterConfiguration {

  private List<RouteConfig> routers;

  private Map<String, String> selectors;

  private List<IsFilter> filters;

  public RouterConfiguration() {
    super();

    this.routers = new ArrayList<>();
    this.selectors = new HashMap<>();
    this.filters = new ArrayList<>();
  }

  public List<RouteConfig> getRouters() {
    return routers;
  }

  public Map<String, String> getSelectors() {
    return selectors;
  }

  public List<IsFilter> getFilters() {
    return filters;
  }

  public List<RouteConfig> match(String hash) {
    return this.routers.stream()
                       .filter(routeConfig -> routeConfig.getRoute()
                                                         .equals(hash))
                       .collect(Collectors.toList());
  }
}
