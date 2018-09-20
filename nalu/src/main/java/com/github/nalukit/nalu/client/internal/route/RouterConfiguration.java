package com.github.nalukit.nalu.client.internal.route;

import com.github.nalukit.nalu.client.filter.IsFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RouterConfiguration {

  private List<RouteConfig> routers;

  private List<IsFilter> filters;

  public RouterConfiguration() {
    super();

    this.routers = new ArrayList<>();
    this.filters = new ArrayList<>();
  }

  public List<RouteConfig> getRouters() {
    return routers;
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
