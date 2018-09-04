package com.github.mvp4g.nalu.client;

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
                       .add(new RouteConfig("/testRoute01",
                                            "content",
                                            "de.gishmo.gwt.example.nalu.simpleapplication.client.ui.content.detail.DetailController"));
    routerConfiguration.getRouters()
                       .add(new RouteConfig("/testRoute02/:testParaemter01/:testParameter02",
                                            "content",
                                            "de.gishmo.gwt.example.nalu.simpleapplication.client.ui.content.detail.DetailController"));
    routerConfiguration.getRouters()
                       .add(new RouteConfig("/testRoute03/testRoute04/testRoute05",
                                            "content",
                                            "de.gishmo.gwt.example.nalu.simpleapplication.client.ui.content.detail.DetailController"));
    routerConfiguration.getRouters()
                       .add(new RouteConfig("/testRoute06/testRoute07/:testParameter01/:testParameter02/:testParameter03",
                                            "content",
                                            "de.gishmo.gwt.example.nalu.simpleapplication.client.ui.content.detail.DetailController"));
    return routerConfiguration;
  }
}
