package com.github.nalukit.nalu.client;

import com.github.nalukit.nalu.client.internal.route.ShellConfiguration;
import com.github.nalukit.nalu.client.plugin.IsCustomAlertPresenter;
import com.github.nalukit.nalu.client.plugin.IsCustomConfirmPresenter;
import com.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin;
import com.github.nalukit.nalu.simpleapplication01.client.Application;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Router Tester.
 *
 * @author Frank Hossfeld
 * @version 1.0
 */
public class RoutingTest {

  private Application application;

  private IsPluginJUnit plugin;

  @BeforeEach
  void before() {
    // create plugin
    this.plugin = new IsPluginJUnit() {

      private boolean attached = true;

      private boolean confirm = true;

      private CompareHandler compareHandler;

      private RouteHandler routeHandler;

      @Override
      public void alert(String message) {
        System.out.println("alert-message: >>" + message + "<<");
      }

      @Override
      public boolean attach(String selector,
                            Object asElement) {
        Assertions.assertTrue(compareHandler.compare(selector,
                                                     (String) asElement),
                              "attach-method: Selector or object mismatch!");
        return this.attached;
      }

      @Override
      public void confirm(String message,
                          ConfirmHandler handler) {
      }

      @Override
      public String getStartRoute() {
        return "/search";
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
        // nothing to do
      }

      @Override
      public void route(String newRoute,
                        boolean replace) {
        Assertions.assertTrue(routeHandler.compare(newRoute,
                                                   replace),
                              "route mismatch!");
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

      @Override
      public void addCompareHandler(CompareHandler compareHandler) {
        this.compareHandler = compareHandler;
      }

      @Override
      public void addRouteHandler(RouteHandler routeHandler) {
        this.routeHandler = routeHandler;
      }

      @Override
      public void setAttached(boolean attached) {
        this.attached = attached;
      }

      @Override
      public void setConfirm(boolean confirm) {
        this.confirm = confirm;
      }

    };
  }

  @AfterEach
  void after() {
    this.application = null;
    this.plugin = null;
  }

  /**
   * Test application start with no book mark
   */
  @Test
  void testStartWithoutBookmark() {
    this.plugin.addCompareHandler(this::compare);
    this.plugin.addRouteHandler((newRoute, replace) -> {
      switch (newRoute) {
        case "":
          return !replace;
        case "footer":
          return !replace;
        case "search":
          return !replace;
        default:
          return false;
      }
    });
    this.application = new Application();
    this.application.run(this.plugin);
  }

  private boolean compare(String selector,
                          String object) {
    switch (selector) {
      case "content":
        return "DetailForm".equals(object) || "ListView".equals(object) || "SearchForm".equals(object);
      case "navigation":
        return "navigation".equals(object);
      case "footer":
        return "footer".equals(object);
      default:
        return false;
    }
  }

  interface IsPluginJUnit
      extends IsNaluProcessorPlugin {

    void addCompareHandler(CompareHandler compareHandler);

    void addRouteHandler(RouteHandler routeHandler);

    void setAttached(boolean attached);

    void setConfirm(boolean confirm);

  }



  interface CompareHandler {

    boolean compare(String selector,
                    String object);

  }



  interface RouteHandler {

    boolean compare(String newRoute,
                    boolean replace);

  }

}
