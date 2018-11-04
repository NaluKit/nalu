package com.github.nalukit.nalu.client;

import com.github.nalukit.nalu.client.internal.CompositeControllerReference;
import com.github.nalukit.nalu.client.internal.route.RouteConfig;
import com.github.nalukit.nalu.client.internal.route.RouterConfiguration;
import com.github.nalukit.nalu.client.internal.route.ShellConfig;
import com.github.nalukit.nalu.client.internal.route.ShellConfiguration;
import com.github.nalukit.nalu.client.plugin.IsPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

  static ShellConfiguration createShellConfiguration() {
    ShellConfiguration shellConfiguration = new ShellConfiguration();
    shellConfiguration.getShells()
                       .add(new ShellConfig("/MockShell",
                                            "com.github.nalukit.example.nalu.simpleapplication.client.ui.shell.Shell"));
    return shellConfiguration;
  }

  static RouterConfiguration createRouterConfiguration() {
    RouterConfiguration routerConfiguration = new RouterConfiguration();
    routerConfiguration.getRouters()
                       .add(new RouteConfig("/MockShell/",
                                            Arrays.asList(new String[] {}),
                                            "content",
                                            "com.github.nalukit.example.nalu.simpleapplication.client.ui.footer.FooterController"));
    routerConfiguration.getRouters()
                       .add(new RouteConfig("/MockShell/testRoute01",
                                            Arrays.asList(new String[] {}),
                                            "content",
                                            "com.github.nalukit.example.nalu.simpleapplication.client.ui.content.detail.DetailController"));
    routerConfiguration.getRouters()
                       .add(new RouteConfig("/MockShell/testRoute02",
                                            Arrays.asList("testParaemter01",
                                                          "testParameter02"),
                                            "content",
                                            "com.github.nalukit.example.nalu.simpleapplication.client.ui.content.detail.DetailController"));
    routerConfiguration.getRouters()
                       .add(new RouteConfig("/MockShell/testRoute03/testRoute04/testRoute05",
                                            Arrays.asList(new String[] {}),
                                            "content",
                                            "com.github.nalukit.example.nalu.simpleapplication.client.ui.content.detail.DetailController"));
    routerConfiguration.getRouters()
                       .add(new RouteConfig("/MockShell/testRoute06/testRoute07",
                                            Arrays.asList("testParameter01",
                                                          "testParameter02",
                                                          "testParameter03"),
                                            "content",
                                            "com.github.nalukit.example.nalu.simpleapplication.client.ui.content.detail.DetailController"));
    return routerConfiguration;
  }

  static List<CompositeControllerReference> createCompositeConfiguration() {
    List<CompositeControllerReference> compositeControllerReferences = new ArrayList<>();
    return compositeControllerReferences;
  }
}
