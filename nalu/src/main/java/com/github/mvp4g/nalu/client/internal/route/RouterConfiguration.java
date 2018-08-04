package com.github.mvp4g.nalu.client.internal.route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RouterConfiguration {

  private List<RouteConfig> routers;

  private Map<String, String> selectors;

  public RouterConfiguration() {
    super();

    this.routers = new ArrayList<>();
    this.selectors = new HashMap<>();
  }

  public List<RouteConfig> getRouters() {
    return routers;
  }

  public Map<String, String> getSelectors() {
    return selectors;
  }

  public List<RouteConfig> match(String hash) {
    return this.routers.stream()
                       .filter(routeConfig -> routeConfig.getRoute()
                                                         .equals("/" + hash))
                       .collect(Collectors.toList());
  }
}
