package test.com.github.mvp4g.nalu.client;

import com.github.mvp4g.nalu.client.internal.route.RouteConfig;
import com.github.mvp4g.nalu.client.internal.route.RouterConfiguration;
import com.github.mvp4g.nalu.client.plugin.IsPlugin;

class Utils {

  static IsPlugin createPlugin(boolean attached,
                               boolean confirm) {
    return new IsPlugin() {
      @Override
      public void alert(String message) {
        // nothing to do
      }

      @Override
      public boolean attach(String selector,
                            Object asElement) {
        // retrun the attached default value
        return attached;
      }

      @Override
      public boolean confirm(String message) {
        return confirm;
      }

      @Override
      public String getStartRoute() {
        return "/";
      }

      @Override
      public void register(HashHandler handler) {
      }

      @Override
      public void remove(String selector) {
      }

      @Override
      public void route(String newRoute,
                        boolean replace) {
      }
    };
  }

  static RouterConfiguration createRouterConfiguration() {
    RouterConfiguration routerConfiguration = new RouterConfiguration();
    routerConfiguration.getRouters()
                       .add(new RouteConfig("/list/:name/:city",
                                            "content",
                                            "de.gishmo.gwt.example.nalu.simpleapplication.client.ui.content.list.ListController"));
    routerConfiguration.getRouters()
                       .add(new RouteConfig("/",
                                            "footer",
                                            "de.gishmo.gwt.example.nalu.simpleapplication.client.ui.footer.FooterController"));
    routerConfiguration.getRouters()
                       .add(new RouteConfig("/search/:searchName/:searchCity",
                                            "content",
                                            "de.gishmo.gwt.example.nalu.simpleapplication.client.ui.content.search.SearchController"));
    routerConfiguration.getRouters()
                       .add(new RouteConfig("/",
                                            "navigation",
                                            "de.gishmo.gwt.example.nalu.simpleapplication.client.ui.navigation.NavigationController"));
    routerConfiguration.getRouters()
                       .add(new RouteConfig("/detail/:id",
                                            "content",
                                            "de.gishmo.gwt.example.nalu.simpleapplication.client.ui.content.detail.DetailController"));
    return routerConfiguration;
  }
}
