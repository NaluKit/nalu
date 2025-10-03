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

package io.github.nalukit.nalu.simpleapplication01.client;

import io.github.nalukit.nalu.client.application.IsLoader;
import io.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import io.github.nalukit.nalu.client.internal.application.AbstractApplication;
import io.github.nalukit.nalu.client.internal.route.RouteConfig;
import io.github.nalukit.nalu.client.plugin.IsCustomAlertPresenter;
import io.github.nalukit.nalu.client.plugin.IsCustomConfirmPresenter;
import io.github.nalukit.nalu.client.tracker.IsTracker;
import io.github.nalukit.nalu.simpleapplication01.client.filters.BartSimpsonFilter;
import io.github.nalukit.nalu.simpleapplication01.client.handler.SimpleApplicationHandler01;
import io.github.nalukit.nalu.simpleapplication01.client.ui.shell.Shell;

import java.util.Arrays;
import java.util.Collections;

public final class NaluSimpleApplicationImpl
    extends AbstractApplication<NaluSimpleApplicationContext>
    implements NaluSimpleApplication {

  public NaluSimpleApplicationImpl() {
    super();
    super.context = new NaluSimpleApplicationContext();
  }

  @Override
  protected IsCustomAlertPresenter getCustomAlertPresenter() {
    return null;
  }

  @Override
  protected IsCustomConfirmPresenter getCustomConfirmPresenter() {
    return null;
  }

  @Override
  public void loadLoggerConfiguration() {
  }

  @Override
  protected void logProcessorVersion() {
  }

  @Override
  public void loadDefaultRoutes() {
    this.startRoute = "/search";
  }

  @Override
  public void loadIllegalRouteTarget() {
    this.illegalRouteTarget = "";
  }

  @Override
  public boolean isHandlingBaseHref() {
    return false;
  }

  @Override
  protected void loadModules() {

  }

  @Override
  protected void loadShells() {

  }

  @Override
  public void loadRoutes() {
    super.routerConfiguration.getRouters()
                             .add(new RouteConfig("/",
                                                  Collections.emptyList(),
                                                  "navigation",
                                                  "NavigationController"));
    super.routerConfiguration.getRouters()
                             .add(new RouteConfig("/detail",
                                                  Collections.singletonList("id"),
                                                  "content",
                                                  "DetailController"));
    super.routerConfiguration.getRouters()
                             .add(new RouteConfig("/list",
                                                  Arrays.asList("name",
                                                                "city"),
                                                  "content",
                                                  "ListController"));
    super.routerConfiguration.getRouters()
                             .add(new RouteConfig("/",
                                                  Collections.emptyList(),
                                                  "footer",
                                                  "FooterController"));
    super.routerConfiguration.getRouters()
                             .add(new RouteConfig("/search",
                                                  Arrays.asList("searchName",
                                                                "searchCity"),
                                                  "content",
                                                  "SearchController"));
  }

  @Override
  public void loadFilters() {
    BartSimpsonFilter com_github_nalukit_example_nalu_simpleapplication_client_filters_BartSimpsonFilter = new BartSimpsonFilter();
    com_github_nalukit_example_nalu_simpleapplication_client_filters_BartSimpsonFilter.setContext(super.context);
    super.routerConfiguration.getFilters()
                             .add(com_github_nalukit_example_nalu_simpleapplication_client_filters_BartSimpsonFilter);
  }

  @Override
  public void loadCompositeReferences() {
  }

  @Override
  protected IsTracker loadTrackerConfiguration() {
    return null;
  }

  @Override
  protected boolean hasHistory() {
    return true;
  }

  @Override
  protected boolean isUsingHash() {
    return true;
  }

  @Override
  protected boolean isUsingColonForParametersInUrl() {
    return false;
  }

  @Override
  protected boolean isStayOnSide() {
    return false;
  }

  @Override
  protected void loadShellFactory() {

  }

  @Override
  protected void loadBlockControllerFactory() {
  }

  @Override
  protected void loadPopUpControllerFactory() {

  }

  @Override
  protected void loadPopUpFilters() {

  }

  @Override
  protected void loadErrorPopUpController() {

  }

  @Override
  public void loadCompositeController() {
  }

  @Override
  @SuppressWarnings("CatchAndPrintStackTrace")
  public void loadComponents() {
    // shellCreator ...
    Shell shell = new Shell();
    shell.setRouter(this.router);
    shell.setEventBus(this.eventBus);
    shell.setContext(this.context);
    super.shell = shell;
    try {
      shell.bind(() -> {
      });
    } catch (RoutingInterceptionException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void loadHandlers() {
    // create handler for: SimpleApplicationHandler01
    SimpleApplicationHandler01 com_github_nalukit_example_nalu_simpleapplication_client_handler_SimpleApplicationHandler01 = new SimpleApplicationHandler01();
    com_github_nalukit_example_nalu_simpleapplication_client_handler_SimpleApplicationHandler01.setContext(super.context);
    com_github_nalukit_example_nalu_simpleapplication_client_handler_SimpleApplicationHandler01.setEventBus(super.eventBus);
    com_github_nalukit_example_nalu_simpleapplication_client_handler_SimpleApplicationHandler01.setRouter(super.router);
    com_github_nalukit_example_nalu_simpleapplication_client_handler_SimpleApplicationHandler01.bind();
  }

  @Override
  protected void loadParameterConstraintRules() {
  }

  @Override
  public IsLoader<NaluSimpleApplicationContext> getLoader() {
    return new NaluSimpleApplicationLoader();
  }

  @Override
  public IsLoader<NaluSimpleApplicationContext> getPostLoader() {
    return null;
  }

}
