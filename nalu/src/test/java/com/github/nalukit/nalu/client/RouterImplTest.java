package com.github.nalukit.nalu.client;

import com.github.nalukit.nalu.client.internal.route.RouteResult;
import com.github.nalukit.nalu.client.internal.route.RouterException;
import com.github.nalukit.nalu.client.internal.route.RouterImpl;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Router Tester.
 *
 * @author Frank Hossfeld
 * @version 1.0
 */
public class RouterImplTest {

  private RouterImpl router;

  @Before
  public void before() {
    this.router = new RouterImpl(Utils.createPlugin(true,
                                                    true),
                                 Utils.createShellConfiguration(),
                                 Utils.createRouterConfiguration(),
                                 Utils.createCompositeConfiguration(),
                                 true,
                                 true,
                                 false);
  }

  @After
  public void after() {
    this.router = null;
  }

  /**
   * Method: parse(String route) without parameter ("/MockShell")
   */
  @Test
  public void testParseRouteViewportOnly() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("/MockShell");
    } catch (RouterException e) {
      Assert.fail();
    }
    Assert.assertEquals("route test with leading '/'",
                        "/MockShell",
                        routeResult.getShell());
    Assert.assertEquals("route test with leading '/' and no parameters",
                        "/MockShell",
                        routeResult.getRoute());
  }

  /**
   * Method: parse(String route) without parameter ("/textViewport/testRoute01")
   */
  @Test
  public void testParseRoute01() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("/MockShell/testRoute01");
    } catch (RouterException e) {
      Assert.fail();
    }
    Assert.assertEquals("route test with leading '/'",
                        "/MockShell",
                        routeResult.getShell());
    Assert.assertEquals("route test with leading '/' and no parameters",
                        "/MockShell/testRoute01",
                        routeResult.getRoute());
  }

  /**
   * Method: parse(String route) without parameter ("testRoute01")
   */
  @Test
  public void testParseRoute02() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("MockShell/testRoute01");
    } catch (RouterException e) {
      Assert.fail();
    }
    Assert.assertEquals("route test without leading '/'",
                        "/MockShell",
                        routeResult.getShell());
    Assert.assertEquals("route test with leading '/' and no parameters",
                        "/MockShell/testRoute01",
                        routeResult.getRoute());
  }

  /**
   * Method: parse(String route) with one parameter ("/testRoute02/testParameter01")
   */
  @Test
  public void testParseRoute03() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("/MockShell/testRoute02/testParameter01");
    } catch (RouterException e) {
      Assert.fail();
    }
    Assert.assertEquals("route test with leading '/'",
                        "/MockShell",
                        routeResult.getShell());
    Assert.assertEquals("route test with leading '/' and one parameter",
                        "/MockShell/testRoute02/*/*",
                        routeResult.getRoute());
    Assert.assertEquals("route test with leading '/' and one parameter",
                        "testParameter01",
                        routeResult.getParameterValues()
                                  .get(0));
  }

  /**
   * Method: parse(String route) with one parameter ("/testRoute02/testParameter01/testParameter02")
   */
  @Test
  public void testParseRoute04() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("/MockShell/testRoute02/testParameter01/testParameter02");
    } catch (RouterException e) {
      Assert.fail();
    }
    Assert.assertEquals("route test with leading '/'",
                        "/MockShell",
                        routeResult.getShell());
    Assert.assertEquals("route test with leading '/' and two parameter",
                        "/MockShell/testRoute02/*/*",
                        routeResult.getRoute());
    Assert.assertEquals("route test with leading '/' and two parameters",
                        "testParameter01",
                        routeResult.getParameterValues()
                                  .get(0));
    Assert.assertEquals("route test with leading '/' and two parameters",
                        "testParameter02",
                        routeResult.getParameterValues()
                                  .get(1));
  }

  /**
   * Method: parse(String route) with one parameter ("/testRoute02//testParameter02")
   */
  @Test
  public void testParseRoute05() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("/MockShell/testRoute02//testParameter02");
    } catch (RouterException e) {
      Assert.fail();
    }
    Assert.assertEquals("route test with leading '/'",
                        "/MockShell",
                        routeResult.getShell());
    Assert.assertEquals("route test with leading '/' and two parameters, first one empty",
                        "/MockShell/testRoute02/*/*",
                        routeResult.getRoute());
    Assert.assertEquals("route test with leading '/' and two parameters, first one empty",
                        "",
                        routeResult.getParameterValues()
                                  .get(0));
    Assert.assertEquals("route test with leading '/' and two parameters, first one empty",
                        "testParameter02",
                        routeResult.getParameterValues()
                                  .get(1));
  }

  /**
   * Method: parse(String route) with one parameter ("testRoute02/testParameter01")
   */
  @Test
  public void testParseRoute06() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("MockShell/testRoute02/testParameter01");
    } catch (RouterException e) {
      Assert.fail();
    }
    Assert.assertEquals("route test without leading '/'",
                        "/MockShell",
                        routeResult.getShell());
    Assert.assertEquals("route test with leading '/' and one parameter",
                        "/MockShell/testRoute02/*/*",
                        routeResult.getRoute());
    Assert.assertEquals("route test with leading '/' and one parameter",
                        "testParameter01",
                        routeResult.getParameterValues()
                                  .get(0));
  }

  /**
   * Method: parse(String route) with one parameter ("testRoute02/testParameter01/testParameter02")
   */
  @Test
  public void testParseRoute07() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("MockShell/testRoute02/testParameter01/testParameter02");
    } catch (RouterException e) {
      Assert.fail();
    }
    Assert.assertEquals("route test without leading '/'",
                        "/MockShell",
                        routeResult.getShell());
    Assert.assertEquals("route test with leading '/' and two parameters",
                        "/MockShell/testRoute02/*/*",
                        routeResult.getRoute());
    Assert.assertEquals("route test with leading '/' and two parameters",
                        "testParameter01",
                        routeResult.getParameterValues()
                                  .get(0));
    Assert.assertEquals("route test with leading '/' and two parameters",
                        "testParameter02",
                        routeResult.getParameterValues()
                                  .get(1));
  }

  /**
   * Method: parse(String route) with one parameter ("testRoute02//testParameter02")
   */
  @Test
  public void testParseRoute08() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("MockShell/testRoute02//testParameter02");
    } catch (RouterException e) {
      Assert.fail();
    }
    Assert.assertEquals("route test without leading '/'",
                        "/MockShell",
                        routeResult.getShell());
    Assert.assertEquals("route test without leading '/' and two parameters, first one empty",
                        "/MockShell/testRoute02/*/*",
                        routeResult.getRoute());
    Assert.assertEquals("route test without leading '/' and two parameters, first one empty",
                        "",
                        routeResult.getParameterValues()
                                  .get(0));
    Assert.assertEquals("route test without leading '/' and two parameters, first one empty",
                        "testParameter02",
                        routeResult.getParameterValues()
                                  .get(1));
  }

  /**
   * Method: parse(String route) with one parameter ("/testRoute03/testRoute04/testRoute05")
   */
  @Test
  public void testParseRoute09() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("/MockShell/testRoute03/testRoute04/testRoute05");
    } catch (RouterException e) {
      Assert.fail();
    }
    Assert.assertEquals("route test with leading '/'",
                        "/MockShell",
                        routeResult.getShell());
    Assert.assertEquals("route test with leading '/'",
                        "/MockShell/testRoute03/testRoute04/testRoute05",
                        routeResult.getRoute());
  }

  /**
   * Method: parse(String route) with one parameter ("/testRoute03/testRoute04/testRoute05")
   */
  @Test
  public void testParseRoute10() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("MockShell/testRoute03/testRoute04/testRoute05");
    } catch (RouterException e) {
      Assert.fail();
    }
    Assert.assertEquals("route test without leading '/'",
                        "/MockShell",
                        routeResult.getShell());
    Assert.assertEquals("route test without leading '/'",
                        "/MockShell/testRoute03/testRoute04/testRoute05",
                        routeResult.getRoute());
  }

  /**
   * Method: parse(String route) with one parameter ("/testRoute03/testRoute04/testRoute05/testParameter01")
   */
  @Test
  public void testParseRoute11() {
    try {
      this.router.parse("/MockShell/testRoute03/testRoute04/testRoute05/testParameter01");
      Assert.fail("Expected exception to be thrown");
    } catch (RouterException e) {
      Assert.assertThat(e,
                        Is.isA(RouterException.class));
      Assert.assertThat(e.getMessage(),
                        Is.is("no matching route found for route >>/MockShell/testRoute03/testRoute04/testRoute05/testParameter01<< --> Routing aborted!"));
    }
  }

  /**
   * Method: parse(String route) with one parameter ("/testRoute06/testRoute07/testParameter01/testParameter02")
   */
  @Test
  public void testParseRoute12() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("/MockShell/testRoute06/testRoute07/testParameter01/testParameter02");
    } catch (RouterException e) {
      Assert.fail();
    }
    Assert.assertEquals("route test without leading '/'",
                        "/MockShell",
                        routeResult.getShell());
    Assert.assertEquals("route test without leading '/' and two parameters, first one empty",
                        "/MockShell/testRoute06/testRoute07/*/*",
                        routeResult.getRoute());
    Assert.assertEquals("route test with leading '/', complex path and two parameters, both parameters exist",
                        "testParameter01",
                        routeResult.getParameterValues()
                                  .get(0));
    Assert.assertEquals("route test without leading '/' and two parameters, first one empty",
                        "testParameter02",
                        routeResult.getParameterValues()
                                  .get(1));
  }

  /**
   * Method: parse(String route) with one parameter ("/testRoute02/testParameter01/testParameter02")
   */
  @Test
  public void testParseRoute13() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("/MockShell/testRoute02/testParameter01/testParameter03");
    } catch (RouterException e) {
      Assert.fail();
    }
    Assert.assertEquals("route test with leading '/'",
                        "/MockShell",
                        routeResult.getShell());
    Assert.assertEquals("route test with leading '/' and one parameter",
                        "/MockShell/testRoute02/*/*",
                        routeResult.getRoute());
    Assert.assertEquals("route test with leading '/' and one parameter",
                        "testParameter01",
                        routeResult.getParameterValues()
                                   .get(0));
  }

  /**
   * Method: generate(String route, String... parms)
   */
  @Test
  public void testGenerateRoute01() {
    String route = "MockShell/testRoute01";
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse(route);
    } catch (RouterException e) {
      Assert.fail();
    }
    String generateRoute = this.router.generate(routeResult.getRoute());
    Assert.assertEquals("generate route with no parameters",
                        route,
                        generateRoute);
  }

  /**
   * Method: generate(String route, String... parms)
   */
  @Test
  public void testGenerateRoute02() {
    String route = "MockShell/testRoute02/testParameter01";
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse(route);
    } catch (RouterException e) {
      Assert.fail();
    }
    String generateRoute = this.router.generate(routeResult.getRoute(),
                                               routeResult.getParameterValues()
                                                             .toArray(new String[0]));
    Assert.assertEquals("generate route with one parameter",
                        route + "/",
                        generateRoute);
  }

  /**
   * Method: generate(String route, String... parms)
   */
  @Test
  public void testGenerateRoute03() {
    String route = "MockShell/testRoute02/testParameter01/testParameter02";
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse(route);
    } catch (RouterException e) {
      Assert.fail();
    }
    String generateRoute = this.router.generate(routeResult.getRoute(),
                                               routeResult.getParameterValues()
                                                             .toArray(new String[0]));
    Assert.assertEquals("generate route with two parameters",
                        route,
                        generateRoute);
  }

  /**
   * Method: generate(String route, String... parms)
   */
  @Test
  public void testGenerateRoute04() {
    String route = "MockShell/testRoute02//testParameter02";
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse(route);
    } catch (RouterException e) {
      Assert.fail();
    }
    String generateRoute = this.router.generate(routeResult.getRoute(),
                                               routeResult.getParameterValues()
                                                             .toArray(new String[0]));
    Assert.assertEquals("generate route with two parameters, first empty",
                        route,
                        generateRoute);
  }

}
