/*
 * Copyright (c) 2018 - 2019 - Frank Hossfeld
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

package com.github.nalukit.nalu.client.internal.validation;

import com.github.nalukit.nalu.client.internal.route.RouteConfig;
import com.github.nalukit.nalu.client.internal.route.RouterConfiguration;
import com.github.nalukit.nalu.client.internal.route.ShellConfig;
import com.github.nalukit.nalu.client.internal.route.ShellConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class RouteValidationTest {

  private ShellConfiguration shellConfiguration;

  private RouterConfiguration routerConfiguration;

  @Before
  public void setUp() {
    this.routerConfiguration = new RouterConfiguration();
    this.shellConfiguration = new ShellConfiguration();

    this.routerConfiguration.getRouters()
                            .add(new RouteConfig("/application/person/list",
                                                 Arrays.asList("name",
                                                               "city"),
                                                 "content",
                                                 "com.github.nalukit.example.nalu.simpleapplication.client.ui.content.list.ListController"));
    this.routerConfiguration.getRouters()
                            .add(new RouteConfig("/application/person/search",
                                                 Arrays.asList("searchName",
                                                               "searchCity"),
                                                 "content",
                                                 "com.github.nalukit.example.nalu.simpleapplication.client.ui.content.search.SearchController"));
    this.routerConfiguration.getRouters()
                            .add(new RouteConfig("/application",
                                                 Collections.emptyList(),
                                                 "footer",
                                                 "com.github.nalukit.example.nalu.simpleapplication.client.ui.footer.FooterController"));
    this.routerConfiguration.getRouters()
                            .add(new RouteConfig("/error/show",
                                                 Collections.emptyList(),
                                                 "content",
                                                 "com.github.nalukit.example.nalu.simpleapplication.client.ui.content.error.ErrorController"));
    this.routerConfiguration.getRouters()
                            .add(new RouteConfig("/application",
                                                 Collections.emptyList(),
                                                 "navigation",
                                                 "com.github.nalukit.example.nalu.simpleapplication.client.ui.navigation.NavigationController"));
    this.routerConfiguration.getRouters()
                            .add(new RouteConfig("/application/person/detail",
                                                 Collections.singletonList("id"),
                                                 "content",
                                                 "com.github.nalukit.example.nalu.simpleapplication.client.ui.content.detail.DetailController"));

    this.shellConfiguration.getShells()
                           .add(new ShellConfig("/error",
                                                "com.github.nalukit.example.nalu.simpleapplication.client.ui.shell.error.ErrorShell"));
    this.shellConfiguration.getShells()
                           .add(new ShellConfig("/application",
                                                "com.github.nalukit.example.nalu.simpleapplication.client.ui.shell.application.ApplicationShell"));
    this.shellConfiguration.getShells()
                           .add(new ShellConfig("/login",
                                                "com.github.nalukit.example.nalu.simpleapplication.client.ui.shell.login.LoginShell"));
  }

  @Test
  public void validateRoute() {
    Assert.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                         this.routerConfiguration,
                                                         "/application"));
    Assert.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                         this.routerConfiguration,
                                                         "/error/show"));
    Assert.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                         this.routerConfiguration,
                                                         "/application/person/detail"));
    Assert.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                         this.routerConfiguration,
                                                         "/application/person/detail/:parameter01"));
    Assert.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                         this.routerConfiguration,
                                                         "/application/person/detail/:parameter01/:parameter02"));

    Assert.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                          this.routerConfiguration,
                                                          "/unknownShell"));
    Assert.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                          this.routerConfiguration,
                                                          "/unknownShell/show"));
    Assert.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                          this.routerConfiguration,
                                                          "/unknownShell/person/detail"));
    Assert.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                          this.routerConfiguration,
                                                          "/unknownShell/person/detail/:parameter01"));
    Assert.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                          this.routerConfiguration,
                                                          "/unknownShell/person/detail/:parameter01/:parameter02"));

    Assert.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                          this.routerConfiguration,
                                                          "/error/showa"));
    Assert.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                          this.routerConfiguration,
                                                          "/application/persona/detail"));
    Assert.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                          this.routerConfiguration,
                                                          "/application/persona/detail/:parameter01"));
    Assert.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                          this.routerConfiguration,
                                                          "/application/persona/detail/:parameter01/:parameter02"));

    Assert.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                         this.routerConfiguration,
                                                         "/application/"));
  }

}