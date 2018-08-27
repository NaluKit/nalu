package de.gishmo.mvp4g.nalu.simpleapplication.client;

import com.github.mvp4g.nalu.client.application.IsApplicationLoader;
import com.github.mvp4g.nalu.client.application.annotation.Debug;
import com.github.mvp4g.nalu.client.component.AbstractComponentController;
import com.github.mvp4g.nalu.client.exception.RoutingInterceptionException;
import com.github.mvp4g.nalu.client.internal.ClientLogger;
import com.github.mvp4g.nalu.client.internal.application.AbstractApplication;
import com.github.mvp4g.nalu.client.internal.application.ControllerCreator;
import com.github.mvp4g.nalu.client.internal.application.ControllerFactory;
import com.github.mvp4g.nalu.client.internal.route.RouteConfig;
import de.gishmo.mvp4g.nalu.simpleapplication.client.filters.BartSimpsonFilter;
import de.gishmo.mvp4g.nalu.simpleapplication.client.handler.SimpleApplicationHandler01;
import de.gishmo.mvp4g.nalu.simpleapplication.client.logger.DefaultLogger;
import de.gishmo.mvp4g.nalu.simpleapplication.client.ui.content.detail.DetailComponent;
import de.gishmo.mvp4g.nalu.simpleapplication.client.ui.content.detail.DetailController;
import de.gishmo.mvp4g.nalu.simpleapplication.client.ui.content.detail.IDetailComponent;
import de.gishmo.mvp4g.nalu.simpleapplication.client.ui.content.list.IListComponent;
import de.gishmo.mvp4g.nalu.simpleapplication.client.ui.content.list.ListComponent;
import de.gishmo.mvp4g.nalu.simpleapplication.client.ui.content.list.ListController;
import de.gishmo.mvp4g.nalu.simpleapplication.client.ui.content.search.ISearchComponent;
import de.gishmo.mvp4g.nalu.simpleapplication.client.ui.content.search.SearchComponent;
import de.gishmo.mvp4g.nalu.simpleapplication.client.ui.content.search.SearchController;
import de.gishmo.mvp4g.nalu.simpleapplication.client.ui.footer.FooterComponent;
import de.gishmo.mvp4g.nalu.simpleapplication.client.ui.footer.FooterController;
import de.gishmo.mvp4g.nalu.simpleapplication.client.ui.footer.IFooterComponent;
import de.gishmo.mvp4g.nalu.simpleapplication.client.ui.navigation.INavigationComponent;
import de.gishmo.mvp4g.nalu.simpleapplication.client.ui.navigation.NavigationComponent;
import de.gishmo.mvp4g.nalu.simpleapplication.client.ui.navigation.NavigationController;
import de.gishmo.mvp4g.nalu.simpleapplication.client.ui.shell.Shell;


public final class NaluSimpleApplicationImpl
  extends AbstractApplication<NaluSimpleApplicationContext> implements NaluSimpleApplication {

  public NaluSimpleApplicationImpl() {
    super();
    super.context = new NaluSimpleApplicationContext();
  }

  @Override
  public void loadDebugConfiguration() {
    ClientLogger.get().register(true, new DefaultLogger(), Debug.LogLevel.DETAILED);
  }

  @Override
  public void loadRoutes() {
    super.routerConfiguration.getRouters().add(new RouteConfig("/list/:name/:city", "content", "de.gishmo.gwt.example.nalu.simpleapplication.client.ui.content.list.ListController"));
    super.routerConfiguration.getRouters().add(new RouteConfig("/", "footer", "de.gishmo.gwt.example.nalu.simpleapplication.client.ui.footer.FooterController"));
    super.routerConfiguration.getRouters().add(new RouteConfig("/search/:searchName/:searchCity", "content", "de.gishmo.gwt.example.nalu.simpleapplication.client.ui.content.search.SearchController"));
    super.routerConfiguration.getRouters().add(new RouteConfig("/", "navigation", "de.gishmo.gwt.example.nalu.simpleapplication.client.ui.navigation.NavigationController"));
    super.routerConfiguration.getRouters().add(new RouteConfig("/detail/:id", "content", "de.gishmo.gwt.example.nalu.simpleapplication.client.ui.content.detail.DetailController"));
  }

  @Override
  public void loadFilters() {
    BartSimpsonFilter de_gishmo_gwt_example_nalu_simpleapplication_client_filters_BartSimpsonFilter = new BartSimpsonFilter();
    de_gishmo_gwt_example_nalu_simpleapplication_client_filters_BartSimpsonFilter.setContext(super.context);
    super.routerConfiguration.getFilters().add(de_gishmo_gwt_example_nalu_simpleapplication_client_filters_BartSimpsonFilter);
    ClientLogger.get().logDetailed("AbstractApplication: filter >> de_gishmo_gwt_example_nalu_simpleapplication_client_filters_BartSimpsonFilter << created", 0);
  }

  @Override
  public void loadHandlers() {
    // create handler for: de.gishmo.gwt.example.nalu.simpleapplication.client.handler.SimpleApplicationHandler01
    SimpleApplicationHandler01 de_gishmo_gwt_example_nalu_simpleapplication_client_handler_SimpleApplicationHandler01 = new SimpleApplicationHandler01();
    de_gishmo_gwt_example_nalu_simpleapplication_client_handler_SimpleApplicationHandler01.setContext(super.context);
    de_gishmo_gwt_example_nalu_simpleapplication_client_handler_SimpleApplicationHandler01.setEventBus(super.eventBus);
    de_gishmo_gwt_example_nalu_simpleapplication_client_handler_SimpleApplicationHandler01.bind();
    ClientLogger.get().logDetailed("AbstractController: handler >>de.gishmo.gwt.example.nalu.simpleapplication.client.handler.SimpleApplicationHandler01<< created", 0);
  }

  @Override
  public IsApplicationLoader getApplicationLoader() {
    return new NaluSimpleApplicationLoader();
  }

  @Override
  public void loadComponents() {
    // shell ...
    Shell shell = new Shell();
    shell.setRouter(this.router);
    shell.setEventBus(this.eventBus);
    shell.setContext(this.context);
    super.shell = shell;
    shell.bind();
    ClientLogger.get().logDetailed("AbstractApplicationImpl: shell created", 1);
    // create ControllerCreator for: de.gishmo.gwt.example.nalu.simpleapplication.client.ui.content.list.ListController
    ControllerFactory.get().registerController("de.gishmo.gwt.example.nalu.simpleapplication.client.ui.content.list.ListController", new ControllerCreator() {
      @Override
      public AbstractComponentController<NaluSimpleApplicationContext, IListComponent, String> create(
          String... parms) throws RoutingInterceptionException {
        ListController controller = new ListController();
        controller.setContext(context);
        controller.setEventBus(eventBus);
        controller.setRouter(router);
        IListComponent component = new ListComponent();
        component.setController(controller);
        controller.setComponent(component);
        ClientLogger.get().logDetailed("ControllerFactory: controller >>de.gishmo.gwt.example.nalu.simpleapplication.client.ui.content.list.ListComponent<< created for route >>/list/:name/:city<<", 0);
        if (parms != null) {
          if (parms.length >= 1) {
            controller.setName(parms[0]);
          }
          if (parms.length >= 2) {
            controller.setCity(parms[1]);
          }
        }
        return controller;
      }
    });
    // create ControllerCreator for: de.gishmo.gwt.example.nalu.simpleapplication.client.ui.footer.FooterController
    ControllerFactory.get().registerController("de.gishmo.gwt.example.nalu.simpleapplication.client.ui.footer.FooterController", new ControllerCreator() {
      @Override
      public AbstractComponentController<NaluSimpleApplicationContext, IFooterComponent, String> create(
          String... parms) throws RoutingInterceptionException {
        FooterController controller = new FooterController();
        controller.setContext(context);
        controller.setEventBus(eventBus);
        controller.setRouter(router);
        IFooterComponent component = new FooterComponent();
        component.setController(controller);
        controller.setComponent(component);
        ClientLogger.get().logDetailed("ControllerFactory: controller >>de.gishmo.gwt.example.nalu.simpleapplication.client.ui.footer.FooterComponent<< created for route >>/<<", 0);
        if (parms != null) {
        }
        return controller;
      }
    });
    // create ControllerCreator for: de.gishmo.gwt.example.nalu.simpleapplication.client.ui.content.search.SearchController
    ControllerFactory.get().registerController("de.gishmo.gwt.example.nalu.simpleapplication.client.ui.content.search.SearchController", new ControllerCreator() {
      @Override
      public AbstractComponentController<NaluSimpleApplicationContext, ISearchComponent, String> create(
          String... parms) throws RoutingInterceptionException {
        SearchController controller = new SearchController();
        controller.setContext(context);
        controller.setEventBus(eventBus);
        controller.setRouter(router);
        ISearchComponent component = new SearchComponent();
        component.setController(controller);
        controller.setComponent(component);
        ClientLogger.get().logDetailed("ControllerFactory: controller >>de.gishmo.gwt.example.nalu.simpleapplication.client.ui.content.search.SearchComponent<< created for route >>/search/:searchName/:searchCity<<", 0);
        if (parms != null) {
          if (parms.length >= 1) {
            controller.setSearchName(parms[0]);
          }
          if (parms.length >= 2) {
            controller.setSearchCity(parms[1]);
          }
        }
        return controller;
      }
    });
    // create ControllerCreator for: de.gishmo.gwt.example.nalu.simpleapplication.client.ui.navigation.NavigationController
    ControllerFactory.get().registerController("de.gishmo.gwt.example.nalu.simpleapplication.client.ui.navigation.NavigationController", new ControllerCreator() {
      @Override
      public AbstractComponentController<NaluSimpleApplicationContext, INavigationComponent, String> create(
          String... parms) throws RoutingInterceptionException {
        NavigationController controller = new NavigationController();
        controller.setContext(context);
        controller.setEventBus(eventBus);
        controller.setRouter(router);
        INavigationComponent component = new NavigationComponent();
        component.setController(controller);
        controller.setComponent(component);
        ClientLogger.get().logDetailed("ControllerFactory: controller >>de.gishmo.gwt.example.nalu.simpleapplication.client.ui.navigation.NavigationComponent<< created for route >>/<<", 0);
        if (parms != null) {
        }
        return controller;
      }
    });
    // create ControllerCreator for: de.gishmo.gwt.example.nalu.simpleapplication.client.ui.content.detail.DetailController
    ControllerFactory.get().registerController("de.gishmo.gwt.example.nalu.simpleapplication.client.ui.content.detail.DetailController", new ControllerCreator() {
      @Override
      public AbstractComponentController<NaluSimpleApplicationContext, IDetailComponent, String> create(
          String... parms) throws RoutingInterceptionException {
        DetailController controller = new DetailController();
        controller.setContext(context);
        controller.setEventBus(eventBus);
        controller.setRouter(router);
        IDetailComponent component = new DetailComponent();
        component.setController(controller);
        controller.setComponent(component);
        ClientLogger.get().logDetailed("ControllerFactory: controller >>de.gishmo.gwt.example.nalu.simpleapplication.client.ui.content.detail.DetailComponent<< created for route >>/detail/:id<<", 0);
        if (parms != null) {
          if (parms.length >= 1) {
            controller.setId(parms[0]);
          }
        }
        return controller;
      }
    });
  }

  @Override
  public void loadStartRoute() {
    this.startRoute = "/search";
  }

  @Override
  public void attachShell() {
    super.shell.attachShell();
  }
}
