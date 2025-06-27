package io.github.nalukit.nalu.client;

import io.github.nalukit.nalu.client.internal.CompositeReference;
import io.github.nalukit.nalu.client.internal.route.RouteConfig;
import io.github.nalukit.nalu.client.internal.route.RouterConfiguration;
import io.github.nalukit.nalu.client.internal.route.ShellConfig;
import io.github.nalukit.nalu.client.internal.route.ShellConfiguration;
import io.github.nalukit.nalu.client.plugin.IsCustomAlertPresenter;
import io.github.nalukit.nalu.client.plugin.IsCustomConfirmPresenter;
import io.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Utils {

  static IsNaluProcessorPlugin createPlugin(boolean attached,
                                            boolean confirm) {
    return new IsNaluProcessorPlugin() {

      @Override
      public void alert(String message) {
        // nothing to do
      }

      @Override
      public boolean attach(String selector,
                            Object asElement) {
        // rerun the attached default value
        return attached;
      }

      @Override
      public void confirm(String message,
                          ConfirmHandler handler) {
      }

      @Override
      public String getStartRoute() {
        return "/";
      }

      @Override
      public Map<String, String> getQueryParameters() {
        return new HashMap<>();
      }

      @Override
      public void register(RouteChangeHandler handler) {
      }

      @Override
      public void remove(String selector) {
      }

      @Override
      public void route(String newRoute,
                        boolean replace,
                        boolean stealthMode) {
      }

      @Override
      public void initialize(ShellConfiguration shellConfiguration) {
      }

      @Override
      public void updateTitle(String title) {
      }

      @Override
      public void updateMetaNameContent(String name,
                                        String content) {
      }

      @Override
      public void updateMetaPropertyContent(String property,
                                            String content) {
      }

      @Override
      public String decode(String route) {
        return route;
      }

      @Override
      public void setCustomAlertPresenter(IsCustomAlertPresenter presenter) {

      }

      @Override
      public void setCustomConfirmPresenter(IsCustomConfirmPresenter presenter) {

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
                       .add(new RouteConfig("/MockShell/testRoute02/*/*",
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
                       .add(new RouteConfig("/MockShell/testRoute06/testRoute07/*/*",
                                            Arrays.asList("testParameter01",
                                                          "testParameter02",
                                                          "testParameter03"),
                                            "content",
                                            "com.github.nalukit.example.nalu.simpleapplication.client.ui.content.detail.DetailController"));
    return routerConfiguration;
  }

  static List<CompositeReference> createCompositeConfiguration() {
    List<CompositeReference> compositeReferences = new ArrayList<>();
    return compositeReferences;
  }

}
