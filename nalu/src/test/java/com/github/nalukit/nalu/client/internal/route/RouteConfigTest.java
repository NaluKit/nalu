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

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

// TODO more Tests!
public class RouteConfigTest {

  @Test
  public void testRouteConfigCreation01() {
    RouteConfig routeConfig = new RouteConfig("/*/testroute01",
                                              new ArrayList<>(),
                                              "selector",
                                              "className");
    Assert.assertEquals("RouteConfig test '/*/testroute01'",
                        "/*",
                        routeConfig.getShell()
                                   .get(0));
    Assert.assertEquals("RouteConfig test '/*/testroute01'",
                        "/testroute01",
                        routeConfig.getRouteWithoutShell());
  }

  @Test
  public void testRouteConfigCreation02() {
    RouteConfig routeConfig = new RouteConfig("/loginShell/testroute01",
                                              new ArrayList<>(),
                                              "selector",
                                              "className");
    Assert.assertEquals("RouteConfig test '/loginShell/testroute01'",
                        "/loginShell",
                        routeConfig.getShell()
                                   .get(0));
    Assert.assertEquals("RouteConfig test '/*/testroute01'",
                        "/testroute01",
                        routeConfig.getRouteWithoutShell());
  }

  @Test
  public void testRouteConfigCreation03() {
    RouteConfig routeConfig = new RouteConfig("/[loginShell|applicationShell]/testroute01",
                                              new ArrayList<>(),
                                              "selector",
                                              "className");
    Assert.assertEquals("RouteConfig test '/[loginShell|applicationShell]/testroute01'",
                        "/loginShell",
                        routeConfig.getShell()
                                   .get(0));
    Assert.assertEquals("RouteConfig test '/[loginShell|applicationShell]/testroute01'",
                        "/applicationShell",
                        routeConfig.getShell()
                                   .get(1));
    Assert.assertEquals("RouteConfig test '/[loginShell|applicationShell]/testroute01'",
                        "/testroute01",
                        routeConfig.getRouteWithoutShell());
  }

}