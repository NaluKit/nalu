/*
 * Copyright (c) 2018 - 2020 - Frank Hossfeld
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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class RouteConfigTest {
  
  @Test
  void testRouteConfigCreation01() {
    RouteConfig routeConfig = new RouteConfig("/*/testroute01",
                                              new ArrayList<>(),
                                              "selector",
                                              "className");
    Assertions.assertEquals("/*",
                            routeConfig.getShell()
                                       .get(0));
    Assertions.assertEquals("/testroute01",
                            routeConfig.getRouteWithoutShell());
  }
  
  @Test
  void testRouteConfigCreation02() {
    RouteConfig routeConfig = new RouteConfig("/loginShell/testroute01",
                                              new ArrayList<>(),
                                              "selector",
                                              "className");
    Assertions.assertEquals("/loginShell",
                            routeConfig.getShell()
                                       .get(0));
    Assertions.assertEquals("/testroute01",
                            routeConfig.getRouteWithoutShell());
  }
  
  @Test
  void testRouteConfigCreation03() {
    RouteConfig routeConfig = new RouteConfig("/[loginShell|applicationShell]/testroute01",
                                              new ArrayList<>(),
                                              "selector",
                                              "className");
    Assertions.assertEquals("/loginShell",
                            routeConfig.getShell()
                                       .get(0));
    Assertions.assertEquals("/applicationShell",
                            routeConfig.getShell()
                                       .get(1));
    Assertions.assertEquals("/testroute01",
                            routeConfig.getRouteWithoutShell());
  }
  
}