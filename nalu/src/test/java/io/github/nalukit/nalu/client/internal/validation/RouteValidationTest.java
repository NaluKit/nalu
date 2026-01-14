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

import io.github.nalukit.nalu.client.internal.NaluConfig;
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
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application"));
  }

  @Test
  void validateStartRoute02() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/error/show"));
  }

  @Test
  void validateStartRoute03() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail"));
  }

  @Test
  void validateStartRoute04() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/parameter01"));
  }

  @Test
  void validateStartRoute05() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell"));
  }

  @Test
  void validateStartRoute06() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/application/person/detail/parameter01/parameter02"));
  }

  @Test
  void validateStartRoute07() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/show"));
  }

  @Test
  void validateStartRoute08() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/person/detail"));
  }

  @Test
  void validateStartRoute09() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/person/detail/parameter01"));
  }

  @Test
  void validateStartRoute00() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/person/detail/parameter01/parameter02"));
  }

  @Test
  void validateStartRoute10() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/error/showa"));
  }

  @Test
  void validateStartRoute11() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail"));
  }

  @Test
  void validateStartRoute12() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/parameter01"));
  }

  @Test
  void validateStartRoute13() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/application/person/detail/parameter01/parameter02"));
  }

  @Test
  void validateStartRoute14() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/"));
  }

  @Test
  void validateStartRoute15() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/parameter01"));
  }

  @Test
  void validateStartRoute16() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/parameter01"));
  }

  // - useColon = true

  @Test
  void validateStartRoute51() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application"));
  }

  @Test
  void validateStartRoute52() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/error/show"));
  }

  @Test
  void validateStartRoute53() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail"));
  }

  @Test
  void validateStartRoute54() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/:parameter01"));
  }

  @Test
  void validateStartRoute55() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell"));
  }

  @Test
  void validateStartRoute56() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/:parameter01/:parameter02"));
  }

  @Test
  void validateStartRoute57() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/show"));
  }

  @Test
  void validateStartRoute58() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/person/detail"));
  }

  @Test
  void validateStartRoute59() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/person/detail/:parameter01"));
  }

  @Test
  void validateStartRoute50() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/person/detail/:parameter01/:parameter02"));
  }

  @Test
  void validateStartRoute60() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/error/showa"));
  }

  @Test
  void validateStartRoute61() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail"));
  }

  @Test
  void validateStartRoute62() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/:parameter01"));
  }

  @Test
  void validateStartRoute63() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/:parameter01/:parameter02"));
  }

  @Test
  void validateStartRoute64() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/"));
  }

  @Test
  void validateStartRoute65() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/:parameter01"));
  }

  @Test
  void validateStartRoute66() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/:parameter01"));
  }

  @Test
  void validateStartRoute101() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application"));
  }

  @Test
  void validateStartRoute102() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/error/show"));
  }

  @Test
  void validateStartRoute103() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail"));
  }

  @Test
  void validateStartRoute104() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/parameter01"));
  }

  @Test
  void validateStartRoute105() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell"));
  }

  @Test
  void validateStartRoute106() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/application/person/detail/parameter01/parameter02"));
  }

  @Test
  void validateStartRoute107() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/show"));
  }

  @Test
  void validateStartRoute108() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/person/detail"));
  }

  @Test
  void validateStartRoute109() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/person/detail/parameter01"));
  }

  @Test
  void validateStartRoute100() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/person/detail/parameter01/parameter02"));
  }

  @Test
  void validateStartRoute110() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/error/showa"));
  }

  @Test
  void validateStartRoute111() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail"));
  }

  @Test
  void validateStartRoute112() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/parameter01"));
  }

  @Test
  void validateStartRoute113() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/application/person/detail/parameter01/parameter02"));
  }

  @Test
  void validateStartRoute114() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/"));
  }

  @Test
  void validateStartRoute115() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/parameter01"));
  }

  @Test
  void validateStartRoute116() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 false,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/parameter01"));
  }

  // - useColon = true

  @Test
  void validateStartRoute151() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application"));
  }

  @Test
  void validateStartRoute152() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/error/show"));
  }

  @Test
  void validateStartRoute153() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail"));
  }

  @Test
  void validateStartRoute154() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/:parameter01"));
  }

  @Test
  void validateStartRoute155() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell"));
  }

  @Test
  void validateStartRoute156() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/:parameter01/:parameter02"));
  }

  @Test
  void validateStartRoute157() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/show"));
  }

  @Test
  void validateStartRoute158() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/person/detail"));
  }

  @Test
  void validateStartRoute159() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/person/detail/:parameter01"));
  }

  @Test
  void validateStartRoute150() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/unknownShell/person/detail/:parameter01/:parameter02"));
  }

  @Test
  void validateStartRoute160() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertFalse(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                              this.routerConfiguration,
                                                              "/error/showa"));
  }

  @Test
  void validateStartRoute161() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail"));
  }

  @Test
  void validateStartRoute162() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/:parameter01"));
  }

  @Test
  void validateStartRoute163() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/:parameter01/:parameter02"));
  }

  @Test
  void validateStartRoute164() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/"));
  }

  @Test
  void validateStartRoute165() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/:parameter01"));
  }

  @Test
  void validateStartRoute166() {
    NaluConfig.INSTANCE.register("startShell/startRoute",
                                 "",
                                 false,
                                 true,
                                 true,
                                 true,
                                 false,
                                 false);
    Assertions.assertTrue(RouteValidation.validateStartRoute(this.shellConfiguration,
                                                             this.routerConfiguration,
                                                             "/application/person/detail/:parameter01"));
  }

}