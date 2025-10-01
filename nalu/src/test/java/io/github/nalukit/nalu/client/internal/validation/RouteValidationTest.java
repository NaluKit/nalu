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

package io.github.nalukit.nalu.client.internal.validation;

import io.github.nalukit.nalu.client.internal.PropertyFactory;
import io.github.nalukit.nalu.client.internal.route.RouteConfig;
import io.github.nalukit.nalu.client.internal.route.RouterConfiguration;
import io.github.nalukit.nalu.client.internal.route.ShellConfig;
import io.github.nalukit.nalu.client.internal.route.ShellConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

public class RouteValidationTest {

  private ShellConfiguration shellConfiguration;

  private RouterConfiguration routerConfiguration;

  @BeforeEach
  void setUp() {
    this.routerConfiguration = new RouterConfiguration();
    this.shellConfiguration  = new ShellConfiguration();

    this.routerConfiguration.getRouters()
                            .add(new RouteConfig("/application/person/list/*/*",
                                                 Arrays.asList("name",
                                                               "city"),
                                                 "content",
                                                 "io.github.nalukit.nalu.example.nalu.simpleapplication.client.ui.content.list.ListController"));
    this.routerConfiguration.getRouters()
                            .add(new RouteConfig("/application/person/search/*/*",
                                                 Arrays.asList("searchName",
                                                               "searchCity"),
                                                 "content",
                                                 "io.github.nalukit.nalu.example.nalu.simpleapplication.client.ui.content.search.SearchController"));
    this.routerConfiguration.getRouters()
                            .add(new RouteConfig("/application",
                                                 Collections.emptyList(),
                                                 "footer",
                                                 "io.github.nalukit.nalu.example.nalu.simpleapplication.client.ui.footer.FooterController"));
    this.routerConfiguration.getRouters()
                            .add(new RouteConfig("/error/show",
                                                 Collections.emptyList(),
                                                 "content",
                                                 "io.github.nalukit.nalu.example.nalu.simpleapplication.client.ui.content.error.ErrorController"));
    this.routerConfiguration.getRouters()
                            .add(new RouteConfig("/application",
                                                 Collections.emptyList(),
                                                 "navigation",
                                                 "io.github.nalukit.nalu.example.nalu.simpleapplication.client.ui.navigation.NavigationController"));
    this.routerConfiguration.getRouters()
                            .add(new RouteConfig("/application/person/detail/*",
                                                 Collections.singletonList("id"),
                                                 "content",
                                                 "io.github.nalukit.nalu.example.nalu.simpleapplication.client.ui.content.detail.DetailController"));
    this.routerConfiguration.getRouters()
                            .add(new RouteConfig("/application/person/*/detail",
                                                 Collections.singletonList("id"),
                                                 "content",
                                                 "io.github.nalukit.nalu.example.nalu.simpleapplication.client.ui.content.detail.DetailController"));

    this.shellConfiguration.getShells()
                           .add(new ShellConfig("/error",
                                                "io.github.nalukit.nalu.example.nalu.simpleapplication.client.ui.shell.error.ErrorShell"));
    this.shellConfiguration.getShells()
                           .add(new ShellConfig("/application",
                                                "io.github.nalukit.nalu.example.nalu.simpleapplication.client.ui.shell.application.ApplicationShell"));
    this.shellConfiguration.getShells()
                           .add(new ShellConfig("/login",
                                                "io.github.nalukit.nalu.example.nalu.simpleapplication.client.ui.shell.login.LoginShell"));
  }

  @Test
  void validateStartRoute01() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application"));
  }

  @Test
  void validateStartRoute02() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/error/show"));
  }

  @Test
  void validateStartRoute03() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail"));
  }

  @Test
  void validateStartRoute04() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/parameter01"));
  }

  @Test
  void validateStartRoute05() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell"));
  }

  @Test
  void validateStartRoute06() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/application/person/detail/parameter01/parameter02"));
  }

  @Test
  void validateStartRoute07() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/show"));
  }

  @Test
  void validateStartRoute08() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/person/detail"));
  }

  @Test
  void validateStartRoute09() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/person/detail/parameter01"));
  }

  @Test
  void validateStartRoute00() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/person/detail/parameter01/parameter02"));
  }

  @Test
  void validateStartRoute10() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/error/showa"));
  }

  @Test
  void validateStartRoute11() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail"));
  }

  @Test
  void validateStartRoute12() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/parameter01"));
  }

  @Test
  void validateStartRoute13() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/application/person/detail/parameter01/parameter02"));
  }

  @Test
  void validateStartRoute14() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/"));
  }

  @Test
  void validateStartRoute15() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/parameter01"));
  }

  @Test
  void validateStartRoute16() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/parameter01"));
  }

  // - useColon = true

  @Test
  void validateStartRoute51() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application"));
  }

  @Test
  void validateStartRoute52() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/error/show"));
  }

  @Test
  void validateStartRoute53() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail"));
  }

  @Test
  void validateStartRoute54() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/:parameter01"));
  }

  @Test
  void validateStartRoute55() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell"));
  }

  @Test
  void validateStartRoute56() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/:parameter01/:parameter02"));
  }

  @Test
  void validateStartRoute57() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/show"));
  }

  @Test
  void validateStartRoute58() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/person/detail"));
  }

  @Test
  void validateStartRoute59() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/person/detail/:parameter01"));
  }

  @Test
  void validateStartRoute50() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/person/detail/:parameter01/:parameter02"));
  }

  @Test
  void validateStartRoute60() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/error/showa"));
  }

  @Test
  void validateStartRoute61() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail"));
  }

  @Test
  void validateStartRoute62() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/:parameter01"));
  }

  @Test
  void validateStartRoute63() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/:parameter01/:parameter02"));
  }

  @Test
  void validateStartRoute64() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/"));
  }

  @Test
  void validateStartRoute65() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/:parameter01"));
  }

  @Test
  void validateStartRoute66() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/:parameter01"));
  }

  @Test
  void validateStartRoute101() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application"));
  }

  @Test
  void validateStartRoute102() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/error/show"));
  }

  @Test
  void validateStartRoute103() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail"));
  }

  @Test
  void validateStartRoute104() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/parameter01"));
  }

  @Test
  void validateStartRoute105() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell"));
  }

  @Test
  void validateStartRoute106() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/application/person/detail/parameter01/parameter02"));
  }

  @Test
  void validateStartRoute107() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/show"));
  }

  @Test
  void validateStartRoute108() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/person/detail"));
  }

  @Test
  void validateStartRoute109() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/person/detail/parameter01"));
  }

  @Test
  void validateStartRoute100() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/person/detail/parameter01/parameter02"));
  }

  @Test
  void validateStartRoute110() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/error/showa"));
  }

  @Test
  void validateStartRoute111() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail"));
  }

  @Test
  void validateStartRoute112() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/parameter01"));
  }

  @Test
  void validateStartRoute113() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/application/person/detail/parameter01/parameter02"));
  }

  @Test
  void validateStartRoute114() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/"));
  }

  @Test
  void validateStartRoute115() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/parameter01"));
  }

  @Test
  void validateStartRoute116() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             false,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/parameter01"));
  }

  // - useColon = true

  @Test
  void validateStartRoute151() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application"));
  }

  @Test
  void validateStartRoute152() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/error/show"));
  }

  @Test
  void validateStartRoute153() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail"));
  }

  @Test
  void validateStartRoute154() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/:parameter01"));
  }

  @Test
  void validateStartRoute155() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell"));
  }

  @Test
  void validateStartRoute156() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/:parameter01/:parameter02"));
  }

  @Test
  void validateStartRoute157() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/show"));
  }

  @Test
  void validateStartRoute158() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/person/detail"));
  }

  @Test
  void validateStartRoute159() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/person/detail/:parameter01"));
  }

  @Test
  void validateStartRoute150() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/person/detail/:parameter01/:parameter02"));
  }

  @Test
  void validateStartRoute160() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/error/showa"));
  }

  @Test
  void validateStartRoute161() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail"));
  }

  @Test
  void validateStartRoute162() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/:parameter01"));
  }

  @Test
  void validateStartRoute163() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/:parameter01/:parameter02"));
  }

  @Test
  void validateStartRoute164() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/"));
  }

  @Test
  void validateStartRoute165() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/:parameter01"));
  }

  @Test
  void validateStartRoute166() {
    PropertyFactory.INSTANCE
                   .register("startShell/startRoute",
                             "",
                             "",
                             true,
                             true,
                             true,
                             false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/:parameter01"));
  }

}