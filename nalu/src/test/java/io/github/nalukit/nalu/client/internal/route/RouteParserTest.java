package io.github.nalukit.nalu.client.internal.route;

import io.github.nalukit.nalu.client.internal.NaluConfig;
import org.gwtproject.event.shared.SimpleEventBus;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;

public class RouteParserTest {

  private ShellConfiguration shellConfiguration;

  private RouterConfiguration routerConfiguration;

  @BeforeEach
  void setUp() {
    this.routerConfiguration = new RouterConfiguration();
    this.shellConfiguration  = new ShellConfiguration();

    RouteParser.INSTANCE
               .setEventBus(new SimpleEventBus());

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
                           .add(new ShellConfig("/application",
                                                "io.github.nalukit.nalu.example.nalu.simpleapplication.client.ui.shell.application.ApplicationShell"));
    this.shellConfiguration.getShells()
                           .add(new ShellConfig("/login",
                                                "io.github.nalukit.nalu.example.nalu.simpleapplication.client.ui.shell.login.LoginShell"));
    NaluConfig.INSTANCE
                   .register("/login",
                             "",
                             false,
                             true,
                             true,
                             false,
                             true,
                             false);

  }

  @Test
  void parse01() {
    String route = "/application/person/search";
    try {
      RouteResult routeResult = RouteParser.INSTANCE.parse(route,
                                                           this.shellConfiguration,
                                                           this.routerConfiguration);
      MatcherAssert.assertThat(routeResult.getShell(),
                               is("/application"));
      MatcherAssert.assertThat(routeResult.getRoute(),
                               is("/application/person/search/*/*"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(0),
                               is(""));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(1),
                               is(""));
    } catch (RouterException e) {
      throw new AssertionError("no exception expected here!",
                               e);
    }
  }

  @Test
  void parse02() {
    String route = "/application/person/search/";
    try {
      RouteResult routeResult = RouteParser.INSTANCE.parse(route,
                                                           this.shellConfiguration,
                                                           this.routerConfiguration);
      MatcherAssert.assertThat(routeResult.getShell(),
                               is("/application"));
      MatcherAssert.assertThat(routeResult.getRoute(),
                               is("/application/person/search/*/*"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(0),
                               is(""));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(1),
                               is(""));
    } catch (RouterException e) {
      throw new AssertionError("no exception expected here!",
                               e);
    }
  }

  @Test
  void parse03() {
    String route = "/application/person/search/S";
    try {
      RouteResult routeResult = RouteParser.INSTANCE.parse(route,
                                                           this.shellConfiguration,
                                                           this.routerConfiguration);
      MatcherAssert.assertThat(routeResult.getShell(),
                               is("/application"));
      MatcherAssert.assertThat(routeResult.getRoute(),
                               is("/application/person/search/*/*"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(0),
                               is("S"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(1),
                               is(""));
    } catch (RouterException e) {
      throw new AssertionError("no exception expected here!",
                               e);
    }
  }

  @Test
  void parse04() {
    String route = "/application/person/search/S/";
    try {
      RouteResult routeResult = RouteParser.INSTANCE.parse(route,
                                                           this.shellConfiguration,
                                                           this.routerConfiguration);
      MatcherAssert.assertThat(routeResult.getShell(),
                               is("/application"));
      MatcherAssert.assertThat(routeResult.getRoute(),
                               is("/application/person/search/*/*"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(0),
                               is("S"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(1),
                               is(""));
    } catch (RouterException e) {
      throw new AssertionError("no exception expected here!",
                               e);
    }
  }

  @Test
  void parse05() {
    String route = "/application/person/search/S/T";
    try {
      RouteResult routeResult = RouteParser.INSTANCE.parse(route,
                                                           this.shellConfiguration,
                                                           this.routerConfiguration);
      MatcherAssert.assertThat(routeResult.getShell(),
                               is("/application"));
      MatcherAssert.assertThat(routeResult.getRoute(),
                               is("/application/person/search/*/*"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(0),
                               is("S"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(1),
                               is("T"));
    } catch (RouterException e) {
      throw new AssertionError("no exception expected here!",
                               e);
    }
  }

  @Test
  void parse06() {
    String route = "/application/person/search/S/T/";
    try {
      RouteResult routeResult = RouteParser.INSTANCE.parse(route,
                                                           this.shellConfiguration,
                                                           this.routerConfiguration);
      MatcherAssert.assertThat(routeResult.getShell(),
                               is("/application"));
      MatcherAssert.assertThat(routeResult.getRoute(),
                               is("/application/person/search/*/*"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(0),
                               is("S"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(1),
                               is("T"));
    } catch (RouterException e) {
      throw new AssertionError("no exception expected here!",
                               e);
    }
  }

  @Test
  void parse11() {
    String route = "/application/person/list";
    try {
      RouteResult routeResult = RouteParser.INSTANCE.parse(route,
                                                           this.shellConfiguration,
                                                           this.routerConfiguration);
      MatcherAssert.assertThat(routeResult.getShell(),
                               is("/application"));
      MatcherAssert.assertThat(routeResult.getRoute(),
                               is("/application/person/list/*/*"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(0),
                               is(""));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(1),
                               is(""));
    } catch (RouterException e) {
      throw new AssertionError("no exception expected here!",
                               e);
    }
  }

  @Test
  void parse12() {
    String route = "/application/person/list/";
    try {
      RouteResult routeResult = RouteParser.INSTANCE.parse(route,
                                                           this.shellConfiguration,
                                                           this.routerConfiguration);
      MatcherAssert.assertThat(routeResult.getShell(),
                               is("/application"));
      MatcherAssert.assertThat(routeResult.getRoute(),
                               is("/application/person/list/*/*"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(0),
                               is(""));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(1),
                               is(""));
    } catch (RouterException e) {
      throw new AssertionError("no exception expected here!",
                               e);
    }
  }

  @Test
  void parse13() {
    String route = "/application/person/list/S";
    try {
      RouteResult routeResult = RouteParser.INSTANCE.parse(route,
                                                           this.shellConfiguration,
                                                           this.routerConfiguration);
      MatcherAssert.assertThat(routeResult.getShell(),
                               is("/application"));
      MatcherAssert.assertThat(routeResult.getRoute(),
                               is("/application/person/list/*/*"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(0),
                               is("S"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(1),
                               is(""));
    } catch (RouterException e) {
      throw new AssertionError("no exception expected here!",
                               e);
    }
  }

  @Test
  void parse14() {
    String route = "/application/person/list/S/";
    try {
      RouteResult routeResult = RouteParser.INSTANCE.parse(route,
                                                           this.shellConfiguration,
                                                           this.routerConfiguration);
      MatcherAssert.assertThat(routeResult.getShell(),
                               is("/application"));
      MatcherAssert.assertThat(routeResult.getRoute(),
                               is("/application/person/list/*/*"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(0),
                               is("S"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(1),
                               is(""));
    } catch (RouterException e) {
      throw new AssertionError("no exception expected here!",
                               e);
    }
  }

  @Test
  void parse15() {
    String route = "/application/person/list/S/T";
    try {
      RouteResult routeResult = RouteParser.INSTANCE.parse(route,
                                                           this.shellConfiguration,
                                                           this.routerConfiguration);
      MatcherAssert.assertThat(routeResult.getShell(),
                               is("/application"));
      MatcherAssert.assertThat(routeResult.getRoute(),
                               is("/application/person/list/*/*"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(0),
                               is("S"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(1),
                               is("T"));
    } catch (RouterException e) {
      throw new AssertionError("no exception expected here!",
                               e);
    }
  }

  @Test
  void parse16() {
    String route = "/application/person/list/S/T/";
    try {
      RouteResult routeResult = RouteParser.INSTANCE.parse(route,
                                                           this.shellConfiguration,
                                                           this.routerConfiguration);
      MatcherAssert.assertThat(routeResult.getShell(),
                               is("/application"));
      MatcherAssert.assertThat(routeResult.getRoute(),
                               is("/application/person/list/*/*"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(0),
                               is("S"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(1),
                               is("T"));
    } catch (RouterException e) {
      throw new AssertionError("no exception expected here!",
                               e);
    }
  }

  @Test
  void parse21() {
    String route = "/application/person/detail";
    try {
      RouteResult routeResult = RouteParser.INSTANCE.parse(route,
                                                           this.shellConfiguration,
                                                           this.routerConfiguration);
      MatcherAssert.assertThat(routeResult.getShell(),
                               is("/application"));
      MatcherAssert.assertThat(routeResult.getRoute(),
                               is("/application/person/detail/*"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(0),
                               is(""));
    } catch (RouterException e) {
      throw new AssertionError("no exception expected here!",
                               e);
    }
  }

  @Test
  void parse22() {
    String route = "/application/person/detail/";
    try {
      RouteResult routeResult = RouteParser.INSTANCE.parse(route,
                                                           this.shellConfiguration,
                                                           this.routerConfiguration);
      MatcherAssert.assertThat(routeResult.getShell(),
                               is("/application"));
      MatcherAssert.assertThat(routeResult.getRoute(),
                               is("/application/person/detail/*"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(0),
                               is(""));
    } catch (RouterException e) {
      throw new AssertionError("no exception expected here!",
                               e);
    }
  }

  @Test
  void parse23() {
    String route = "/application/person/detail/1";
    try {
      RouteResult routeResult = RouteParser.INSTANCE.parse(route,
                                                           this.shellConfiguration,
                                                           this.routerConfiguration);
      MatcherAssert.assertThat(routeResult.getShell(),
                               is("/application"));
      MatcherAssert.assertThat(routeResult.getRoute(),
                               is("/application/person/detail/*"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(0),
                               is("1"));
    } catch (RouterException e) {
      throw new AssertionError("no exception expected here!",
                               e);
    }
  }

  @Test
  void parse24() {
    String route = "/application/person/detail/1/";
    try {
      RouteResult routeResult = RouteParser.INSTANCE.parse(route,
                                                           this.shellConfiguration,
                                                           this.routerConfiguration);
      MatcherAssert.assertThat(routeResult.getShell(),
                               is("/application"));
      MatcherAssert.assertThat(routeResult.getRoute(),
                               is("/application/person/detail/*"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(0),
                               is("1"));
    } catch (RouterException e) {
      throw new AssertionError("no exception expected here!",
                               e);
    }
  }

  @Test
  void parse31() {
    String route = "/application/person//detail";
    try {
      RouteResult routeResult = RouteParser.INSTANCE.parse(route,
                                                           this.shellConfiguration,
                                                           this.routerConfiguration);
      MatcherAssert.assertThat(routeResult.getShell(),
                               is("/application"));
      MatcherAssert.assertThat(routeResult.getRoute(),
                               is("/application/person/*/detail"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(0),
                               is(""));
    } catch (RouterException e) {
      throw new AssertionError("no exception expected here!",
                               e);
    }
  }

  @Test
  void parse32() {
    String route = "/application/person//detail/";
    try {
      RouteResult routeResult = RouteParser.INSTANCE.parse(route,
                                                           this.shellConfiguration,
                                                           this.routerConfiguration);
      MatcherAssert.assertThat(routeResult.getShell(),
                               is("/application"));
      MatcherAssert.assertThat(routeResult.getRoute(),
                               is("/application/person/*/detail"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(0),
                               is(""));
    } catch (RouterException e) {
      throw new AssertionError("no exception expected here!",
                               e);
    }
  }

  @Test
  void parse33() {
    String route = "/application/person/1/detail";
    try {
      RouteResult routeResult = RouteParser.INSTANCE.parse(route,
                                                           this.shellConfiguration,
                                                           this.routerConfiguration);
      MatcherAssert.assertThat(routeResult.getShell(),
                               is("/application"));
      MatcherAssert.assertThat(routeResult.getRoute(),
                               is("/application/person/*/detail"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(0),
                               is("1"));
    } catch (RouterException e) {
      throw new AssertionError("no exception expected here!",
                               e);
    }
  }

  @Test
  void parse34() {
    String route = "/application/person/1/detail/";
    try {
      RouteResult routeResult = RouteParser.INSTANCE.parse(route,
                                                           this.shellConfiguration,
                                                           this.routerConfiguration);
      MatcherAssert.assertThat(routeResult.getShell(),
                               is("/application"));
      MatcherAssert.assertThat(routeResult.getRoute(),
                               is("/application/person/*/detail"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(0),
                               is("1"));
    } catch (RouterException e) {
      throw new AssertionError("no exception expected here!",
                               e);
    }
  }

  @Test
  void parse35() {
    String route = "/application/person/:ptNr/detail/";
    try {
      RouteResult routeResult = RouteParser.INSTANCE.parse(route,
                                                           this.shellConfiguration,
                                                           this.routerConfiguration);
      MatcherAssert.assertThat(routeResult.getShell(),
                               is("/application"));
      MatcherAssert.assertThat(routeResult.getRoute(),
                               is("/application/person/*/detail"));
      MatcherAssert.assertThat(routeResult.getParameterValues()
                                          .get(0),
                               is(":ptNr"));
    } catch (RouterException e) {
      throw new AssertionError("no exception expected here!",
                               e);
    }
  }

  @Test
  void generate01() {
    String hash = RouteParser.INSTANCE.generate("/application/person/detail",
                                                "1");
    MatcherAssert.assertThat(hash,
                             is("application/person/detail/1"));
  }

  @Test
  void generate02() {
    String hash = RouteParser.INSTANCE.generate("/application/person/*/detail",
                                                "1");
    MatcherAssert.assertThat(hash,
                             is("application/person/1/detail"));
  }

  @Test
  void generate03() {
    String hash = RouteParser.INSTANCE.generate("/application/person/list",
                                                "A",
                                                "B");
    MatcherAssert.assertThat(hash,
                             is("application/person/list/A/B"));
  }

  @Test
  void generate04() {
    String hash = RouteParser.INSTANCE.generate("/application/person/list",
                                                "A");
    MatcherAssert.assertThat(hash,
                             is("application/person/list/A"));
  }

  @Test
  void generate05() {
    String hash = RouteParser.INSTANCE.generate("/application/person/list/:name/:citty",
                                                null,
                                                null);
    MatcherAssert.assertThat(hash,
                             is("application/person/list//"));
  }

}