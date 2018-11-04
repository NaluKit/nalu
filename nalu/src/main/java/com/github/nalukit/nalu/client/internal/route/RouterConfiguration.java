/*
 * Copyright (c) 2018 - Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 */

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
                       .filter(routeConfig -> routeConfig.match(hash))
                       .collect(Collectors.toList());
  }
}
