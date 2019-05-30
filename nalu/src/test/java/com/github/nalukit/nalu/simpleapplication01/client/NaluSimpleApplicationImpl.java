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

package com.github.nalukit.nalu.simpleapplication01.client;

import com.github.nalukit.nalu.client.application.IsApplicationLoader;
import com.github.nalukit.nalu.client.application.annotation.Debug;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.application.AbstractApplication;
import com.github.nalukit.nalu.client.internal.route.RouteConfig;
import com.github.nalukit.nalu.client.tracker.IsTracker;
import com.github.nalukit.nalu.simpleapplication01.client.filters.BartSimpsonFilter;
import com.github.nalukit.nalu.simpleapplication01.client.handler.SimpleApplicationHandler01;
import com.github.nalukit.nalu.simpleapplication01.client.logger.DefaultLogger;
import com.github.nalukit.nalu.simpleapplication01.client.ui.shell.Shell;

import java.util.Arrays;

public final class NaluSimpleApplicationImpl
    extends AbstractApplication<NaluSimpleApplicationContext>
    implements NaluSimpleApplication {

  public NaluSimpleApplicationImpl() {
    super();
    super.context = new NaluSimpleApplicationContext();
  }

  @Override
  protected void logProcessorVersion() {
  }

  @Override
  protected void loadPlugins() {

  }

  @Override
  protected void loadShellFactory() {

  }

  @Override
  public void loadDebugConfiguration() {
    ClientLogger.get()
                .register(true,
                          new DefaultLogger(),
                          Debug.LogLevel.DETAILED);
  }

  @Override
  protected IsTracker loadTrackerConfiguration() {
    return null;
  }

  @Override
  protected void loadShells() {

  }

  @Override
  public void loadCompositeController() {
  }

  @Override
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
    //    ClientLogger.get().logDetailed("AbstractApplicationImpl: shellCreator created", 1);
    //    // create ControllerCreator for: NavigationController
    //    ControllerFactory.get().registerController("NavigationController", new IsControllerCreator() {
    //      @Override
    //      public ControllerInstance create(String... parms) throws RoutingInterceptionException {
    //        StringBuilder sb01 = new StringBuilder();
    //        ControllerInstance controllerInstance = new ControllerInstance();
    //        controllerInstance.setControllerClassName("NavigationController");
    //        AbstractComponentController<?, ?, ?> storedController = ControllerFactory.get().getControllerFormStore("NavigationController");
    //        if (storedController == null) {
    //          sb01.append("controller >>NavigationController<< --> will be created");
    //          ClientLogger.get().logSimple(sb01.toString(), 1);
    //          NavigationController controller = new NavigationController();
    //          controllerInstance.setController(controller);
    //          controllerInstance.setChached(false);
    //          controller.setContext(context);
    //          controller.setEventBus(eventBus);
    //          controller.setRouter(router);
    //          controller.setCached(false);
    //          sb01 = new StringBuilder();
    //          sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> created and data injected");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          INavigationComponent component = new NavigationComponent();
    //          component.setController(controller);
    //          sb01 = new StringBuilder();
    //          sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> created and controller instance injected");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          controller.setComponent(component);
    //          sb01 = new StringBuilder();
    //          sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> instance of >>").append(component.getClass().getCanonicalName()).append("<< injected");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          component.render();
    //          sb01 = new StringBuilder();
    //          sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> rendered");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          component.bind();
    //          sb01 = new StringBuilder();
    //          sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> bound");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          ClientLogger.get().logSimple("controller >>NavigationComponent<< created for route >>/<<", 1);
    //        } else {
    //          sb01.append("controller >>").append(storedController.getClass().getCanonicalName()).append("<< --> found in cache -> REUSE!");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          controllerInstance.setController(storedController);
    //          controllerInstance.setChached(true);
    //        }
    //        return controllerInstance;
    //      }
    //    });
    //    // create ControllerCreator for: DetailController
    //    ControllerFactory.get().registerController("DetailController", new IsControllerCreator() {
    //      @Override
    //      public ControllerInstance create(String... parms) throws RoutingInterceptionException {
    //        StringBuilder sb01 = new StringBuilder();
    //        ControllerInstance controllerInstance = new ControllerInstance();
    //        controllerInstance.setControllerClassName("com.github.nalukit.nalu.simpleapplication.client.ui.detail.DetailController");
    //        AbstractComponentController<?, ?, ?> storedController = ControllerFactory.get().getControllerFormStore("DetailController");
    //        if (storedController == null) {
    //          sb01.append("controller >>DetailController<< --> will be created");
    //          ClientLogger.get().logSimple(sb01.toString(), 1);
    //          DetailController controller = new DetailController();
    //          controllerInstance.setController(controller);
    //          controllerInstance.setChached(false);
    //          controller.setContext(context);
    //          controller.setEventBus(eventBus);
    //          controller.setRouter(router);
    //          controller.setCached(false);
    //          sb01 = new StringBuilder();
    //          sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> created and data injected");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          IDetailComponent component = new DetailComponent();
    //          component.setController(controller);
    //          sb01 = new StringBuilder();
    //          sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> created and controller instance injected");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          controller.setComponent(component);
    //          sb01 = new StringBuilder();
    //          sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> instance of >>").append(component.getClass().getCanonicalName()).append("<< injected");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          component.render();
    //          sb01 = new StringBuilder();
    //          sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> rendered");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          component.bind();
    //          sb01 = new StringBuilder();
    //          sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> bound");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          ClientLogger.get().logSimple("controller >>DetailComponent<< created for route >>/detail<<", 1);
    //          if (parms != null) {
    //            if (parms.length >= 1) {
    //              sb01 = new StringBuilder();
    //              sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> using method >>setId<< to set value >>").append(parms[0]).append("<<");
    //              ClientLogger.get().logDetailed(sb01.toString(), 2);
    //              controller.setId(parms[0]);
    //            }
    //          }
    //        } else {
    //          sb01.append("controller >>").append(storedController.getClass().getCanonicalName()).append("<< --> found in cache -> REUSE!");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          controllerInstance.setController(storedController);
    //          controllerInstance.setChached(true);
    //        }
    //        return controllerInstance;
    //      }
    //    });
    //    // create ControllerCreator for: ListController
    //    ControllerFactory.get().registerController("ListController", new IsControllerCreator() {
    //      @Override
    //      public ControllerInstance create(String... parms) throws RoutingInterceptionException {
    //        StringBuilder sb01 = new StringBuilder();
    //        ControllerInstance controllerInstance = new ControllerInstance();
    //        controllerInstance.setControllerClassName("com.github.nalukit.nalu.simpleapplication.client.ui.list.ListController");
    //        AbstractComponentController<?, ?, ?> storedController = ControllerFactory.get().getControllerFormStore("ListController");
    //        if (storedController == null) {
    //          sb01.append("controller >>ListController<< --> will be created");
    //          ClientLogger.get().logSimple(sb01.toString(), 1);
    //          ListController controller = new ListController();
    //          controllerInstance.setController(controller);
    //          controllerInstance.setChached(false);
    //          controller.setContext(context);
    //          controller.setEventBus(eventBus);
    //          controller.setRouter(router);
    //          controller.setCached(false);
    //          sb01 = new StringBuilder();
    //          sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> created and data injected");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          IListComponent component = new ListComponent();
    //          component.setController(controller);
    //          sb01 = new StringBuilder();
    //          sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> created and controller instance injected");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          controller.setComponent(component);
    //          sb01 = new StringBuilder();
    //          sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> instance of >>").append(component.getClass().getCanonicalName()).append("<< injected");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          component.render();
    //          sb01 = new StringBuilder();
    //          sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> rendered");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          component.bind();
    //          sb01 = new StringBuilder();
    //          sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> bound");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          ClientLogger.get().logSimple("controller >>ListComponent<< created for route >>/list<<", 1);
    //          if (parms != null) {
    //            if (parms.length >= 1) {
    //              sb01 = new StringBuilder();
    //              sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> using method >>setName<< to set value >>").append(parms[0]).append("<<");
    //              ClientLogger.get().logDetailed(sb01.toString(), 2);
    //              controller.setName(parms[0]);
    //            }
    //            if (parms.length >= 2) {
    //              sb01 = new StringBuilder();
    //              sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> using method >>setCity<< to set value >>").append(parms[1]).append("<<");
    //              ClientLogger.get().logDetailed(sb01.toString(), 2);
    //              controller.setCity(parms[1]);
    //            }
    //          }
    //        } else {
    //          sb01.append("controller >>").append(storedController.getClass().getCanonicalName()).append("<< --> found in cache -> REUSE!");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          controllerInstance.setController(storedController);
    //          controllerInstance.setChached(true);
    //        }
    //        return controllerInstance;
    //      }
    //    });
    //    // create ControllerCreator for: FooterController
    //    ControllerFactory.get().registerController("FooterController", new IsControllerCreator() {
    //      @Override
    //      public ControllerInstance create(String... parms) throws RoutingInterceptionException {
    //        StringBuilder sb01 = new StringBuilder();
    //        ControllerInstance controllerInstance = new ControllerInstance();
    //        controllerInstance.setControllerClassName("FooterController");
    //        AbstractComponentController<?, ?, ?> storedController = ControllerFactory.get().getControllerFormStore("FooterController");
    //        if (storedController == null) {
    //          sb01.append("controller >>FooterController<< --> will be created");
    //          ClientLogger.get().logSimple(sb01.toString(), 1);
    //          FooterController controller = new FooterController();
    //          controllerInstance.setController(controller);
    //          controllerInstance.setChached(false);
    //          controller.setContext(context);
    //          controller.setEventBus(eventBus);
    //          controller.setRouter(router);
    //          controller.setCached(false);
    //          sb01 = new StringBuilder();
    //          sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> created and data injected");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          IFooterComponent component = new FooterComponent();
    //          component.setController(controller);
    //          sb01 = new StringBuilder();
    //          sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> created and controller instance injected");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          controller.setComponent(component);
    //          sb01 = new StringBuilder();
    //          sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> instance of >>").append(component.getClass().getCanonicalName()).append("<< injected");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          component.render();
    //          sb01 = new StringBuilder();
    //          sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> rendered");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          component.bind();
    //          sb01 = new StringBuilder();
    //          sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> bound");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          ClientLogger.get().logSimple("controller >>FooterComponent<< created for route >>/<<", 1);
    //        } else {
    //          sb01.append("controller >>").append(storedController.getClass().getCanonicalName()).append("<< --> found in cache -> REUSE!");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          controllerInstance.setController(storedController);
    //          controllerInstance.setChached(true);
    //        }
    //        return controllerInstance;
    //      }
    //    });
    //    // create ControllerCreator for: SearchController
    //    ControllerFactory.get().registerController("SearchController", new IsControllerCreator() {
    //      @Override
    //      public ControllerInstance create(String... parms) throws RoutingInterceptionException {
    //        StringBuilder sb01 = new StringBuilder();
    //        ControllerInstance controllerInstance = new ControllerInstance();
    //        controllerInstance.setControllerClassName("com.github.nalukit.nalu.simpleapplication.client.ui.search.SearchController");
    //        AbstractComponentController<?, ?, ?> storedController = ControllerFactory.get().getControllerFormStore("SearchController");
    //        if (storedController == null) {
    //          sb01.append("controller >>SearchController<< --> will be created");
    //          ClientLogger.get().logSimple(sb01.toString(), 1);
    //          SearchController controller = new SearchController();
    //          controllerInstance.setController(controller);
    //          controllerInstance.setChached(false);
    //          controller.setContext(context);
    //          controller.setEventBus(eventBus);
    //          controller.setRouter(router);
    //          controller.setCached(false);
    //          sb01 = new StringBuilder();
    //          sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> created and data injected");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          ISearchComponent component = new SearchComponent();
    //          component.setController(controller);
    //          sb01 = new StringBuilder();
    //          sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> created and controller instance injected");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          controller.setComponent(component);
    //          sb01 = new StringBuilder();
    //          sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> instance of >>").append(component.getClass().getCanonicalName()).append("<< injected");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          component.render();
    //          sb01 = new StringBuilder();
    //          sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> rendered");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          component.bind();
    //          sb01 = new StringBuilder();
    //          sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> bound");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          ClientLogger.get().logSimple("controller >>SearchComponent<< created for route >>/search<<", 1);
    //          if (parms != null) {
    //            if (parms.length >= 1) {
    //              sb01 = new StringBuilder();
    //              sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> using method >>setSearchName<< to set value >>").append(parms[0]).append("<<");
    //              ClientLogger.get().logDetailed(sb01.toString(), 2);
    //              controller.setSearchName(parms[0]);
    //            }
    //            if (parms.length >= 2) {
    //              sb01 = new StringBuilder();
    //              sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> using method >>setSearchCity<< to set value >>").append(parms[1]).append("<<");
    //              ClientLogger.get().logDetailed(sb01.toString(), 2);
    //              controller.setSearchCity(parms[1]);
    //            }
    //          }
    //        } else {
    //          sb01.append("controller >>").append(storedController.getClass().getCanonicalName()).append("<< --> found in cache -> REUSE!");
    //          ClientLogger.get().logDetailed(sb01.toString(), 2);
    //          controllerInstance.setController(storedController);
    //          controllerInstance.setChached(true);
    //        }
    //        return controllerInstance;
    //      }
    //    });
  }

  @Override
  public void loadRoutes() {
    super.routerConfiguration.getRouters()
                             .add(new RouteConfig("/",
                                                  Arrays.asList(new String[] {}),
                                                  "navigation",
                                                  "NavigationController"));
    super.routerConfiguration.getRouters()
                             .add(new RouteConfig("/detail",
                                                  Arrays.asList("id"),
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
                                                  Arrays.asList(new String[] {}),
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
    ClientLogger.get()
                .logDetailed("AbstractApplication: filter >> com_github_nalukit_example_nalu_simpleapplication_client_filters_BartSimpsonFilter << created",
                             0);
  }

  @Override
  public void loadHandlers() {
    // create handler for: SimpleApplicationHandler01
    SimpleApplicationHandler01 com_github_nalukit_example_nalu_simpleapplication_client_handler_SimpleApplicationHandler01 = new SimpleApplicationHandler01();
    com_github_nalukit_example_nalu_simpleapplication_client_handler_SimpleApplicationHandler01.setContext(super.context);
    com_github_nalukit_example_nalu_simpleapplication_client_handler_SimpleApplicationHandler01.setEventBus(super.eventBus);
    com_github_nalukit_example_nalu_simpleapplication_client_handler_SimpleApplicationHandler01.setRouter(super.router);
    com_github_nalukit_example_nalu_simpleapplication_client_handler_SimpleApplicationHandler01.bind();
    ClientLogger.get()
                .logDetailed("AbstractController: handler >>SimpleApplicationHandler01<< created",
                             0);
  }

  @Override
  protected void loadPopUpControllerFactory() {

  }

  @Override
  public void loadCompositeReferences() {
  }

  @Override
  public IsApplicationLoader<NaluSimpleApplicationContext> getApplicationLoader() {
    return new NaluSimpleApplicationLoader();
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
  public void loadDefaultRoutes() {
    this.startRoute = "/search";
    this.errorRoute = "WhenShallWeThreeMeetAgainInThunderLightningOrInRain";
  }

}
