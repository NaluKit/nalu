package com.github.nalukit.nalu.client;

import com.github.nalukit.nalu.client.internal.route.RouteParser;
import com.github.nalukit.nalu.client.internal.route.RouteResult;
import com.github.nalukit.nalu.client.internal.route.RouterException;
import com.github.nalukit.nalu.client.internal.route.RouterImpl;
import org.gwtproject.event.shared.SimpleEventBus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Router Tester.
 *
 * @author Frank Hossfeld
 * @version 1.0
 */
public class RouterImplTest {
  
  private RouterImpl router;
  
  @BeforeEach
  void before() {
    this.router = new RouterImpl(Utils.createPlugin(true,
                                                    true),
                                 Utils.createShellConfiguration(),
                                 Utils.createRouterConfiguration(),
                                 Utils.createCompositeConfiguration(),
                                 null,
                                 "startShell/startRoute",
                                 "",
                                 true,
                                 true,
                                 false,
                                 false);
    RouteParser.get()
               .setEventBus(new SimpleEventBus());
  }
  
  @AfterEach
  void after() {
    this.router = null;
  }
  
  /**
   * Method: parse(String route) without parameter ("/MockShell")
   */
  @Test
  void testParseRouteViewportOnly() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("/MockShell");
    } catch (RouterException e) {
      Assertions.fail();
    }
    Assertions.assertEquals("/MockShell",
                            routeResult.getShell());
    Assertions.assertEquals("/MockShell",
                            routeResult.getRoute());
  }
  
  /**
   * Method: parse(String route) without parameter ("/textViewport/testRoute01")
   */
  @Test
  void testParseRoute01() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("/MockShell/testRoute01");
    } catch (RouterException e) {
      Assertions.fail();
    }
    Assertions.assertEquals("/MockShell",
                            routeResult.getShell());
    Assertions.assertEquals("/MockShell/testRoute01",
                            routeResult.getRoute());
  }
  
  /**
   * Method: parse(String route) without parameter ("testRoute01")
   */
  @Test
  void testParseRoute02() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("MockShell/testRoute01");
    } catch (RouterException e) {
      Assertions.fail();
    }
    Assertions.assertEquals("/MockShell",
                            routeResult.getShell());
    Assertions.assertEquals("/MockShell/testRoute01",
                            routeResult.getRoute());
  }
  
  /**
   * Method: parse(String route) with one parameter ("/testRoute02/testParameter01")
   */
  @Test
  void testParseRoute03() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("/MockShell/testRoute02/testParameter01");
    } catch (RouterException e) {
      Assertions.fail();
    }
    Assertions.assertEquals("/MockShell",
                            routeResult.getShell());
    Assertions.assertEquals("/MockShell/testRoute02/*/*",
                            routeResult.getRoute());
    Assertions.assertEquals("testParameter01",
                            routeResult.getParameterValues()
                                       .get(0));
  }
  
  /**
   * Method: parse(String route) with one parameter ("/testRoute02/testParameter01/testParameter02")
   */
  @Test
  void testParseRoute04() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("/MockShell/testRoute02/testParameter01/testParameter02");
    } catch (RouterException e) {
      Assertions.fail();
    }
    Assertions.assertEquals("/MockShell",
                            routeResult.getShell());
    Assertions.assertEquals("/MockShell/testRoute02/*/*",
                            routeResult.getRoute());
    Assertions.assertEquals("testParameter01",
                            routeResult.getParameterValues()
                                       .get(0));
    Assertions.assertEquals("testParameter02",
                            routeResult.getParameterValues()
                                       .get(1));
  }
  
  /**
   * Method: parse(String route) with one parameter ("/testRoute02//testParameter02")
   */
  @Test
  void testParseRoute05() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("/MockShell/testRoute02//testParameter02");
    } catch (RouterException e) {
      Assertions.fail();
    }
    Assertions.assertEquals("/MockShell",
                            routeResult.getShell());
    Assertions.assertEquals("/MockShell/testRoute02/*/*",
                            routeResult.getRoute());
    Assertions.assertEquals("",
                            routeResult.getParameterValues()
                                       .get(0));
    Assertions.assertEquals("testParameter02",
                            routeResult.getParameterValues()
                                       .get(1));
  }
  
  /**
   * Method: parse(String route) with one parameter ("testRoute02/testParameter01")
   */
  @Test
  void testParseRoute06() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("MockShell/testRoute02/testParameter01");
    } catch (RouterException e) {
      Assertions.fail();
    }
    Assertions.assertEquals("/MockShell",
                            routeResult.getShell());
    Assertions.assertEquals("/MockShell/testRoute02/*/*",
                            routeResult.getRoute());
    Assertions.assertEquals("testParameter01",
                            routeResult.getParameterValues()
                                       .get(0));
  }
  
  /**
   * Method: parse(String route) with one parameter ("testRoute02/testParameter01/testParameter02")
   */
  @Test
  void testParseRoute07() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("MockShell/testRoute02/testParameter01/testParameter02");
    } catch (RouterException e) {
      Assertions.fail();
    }
    Assertions.assertEquals("/MockShell",
                            routeResult.getShell());
    Assertions.assertEquals("/MockShell/testRoute02/*/*",
                            routeResult.getRoute());
    Assertions.assertEquals("testParameter01",
                            routeResult.getParameterValues()
                                       .get(0));
    Assertions.assertEquals("testParameter02",
                            routeResult.getParameterValues()
                                       .get(1));
  }
  
  /**
   * Method: parse(String route) with one parameter ("testRoute02//testParameter02")
   */
  @Test
  void testParseRoute08() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("MockShell/testRoute02//testParameter02");
    } catch (RouterException e) {
      Assertions.fail();
    }
    Assertions.assertEquals("/MockShell",
                            routeResult.getShell());
    Assertions.assertEquals("/MockShell/testRoute02/*/*",
                            routeResult.getRoute());
    Assertions.assertEquals("",
                            routeResult.getParameterValues()
                                       .get(0));
    Assertions.assertEquals("testParameter02",
                            routeResult.getParameterValues()
                                       .get(1));
  }
  
  /**
   * Method: parse(String route) with one parameter ("/testRoute03/testRoute04/testRoute05")
   */
  @Test
  void testParseRoute09() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("/MockShell/testRoute03/testRoute04/testRoute05");
    } catch (RouterException e) {
      Assertions.fail();
    }
    Assertions.assertEquals("/MockShell",
                            routeResult.getShell());
    Assertions.assertEquals("/MockShell/testRoute03/testRoute04/testRoute05",
                            routeResult.getRoute());
  }
  
  /**
   * Method: parse(String route) with one parameter ("/testRoute03/testRoute04/testRoute05")
   */
  @Test
  void testParseRoute10() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("MockShell/testRoute03/testRoute04/testRoute05");
    } catch (RouterException e) {
      Assertions.fail();
    }
    Assertions.assertEquals("/MockShell",
                            routeResult.getShell());
    Assertions.assertEquals("/MockShell/testRoute03/testRoute04/testRoute05",
                            routeResult.getRoute());
  }
  
  /**
   * Method: parse(String route) with one parameter ("/testRoute03/testRoute04/testRoute05/testParameter01")
   */
  @Test
  void testParseRoute11() {
    try {
      this.router.parse("/MockShell/testRoute03/testRoute04/testRoute05/testParameter01");
      Assertions.fail("Expected exception to be thrown");
    } catch (RouterException e) {
      Assertions.assertEquals(e.getMessage(),
                              "no matching route found for route >>/MockShell/testRoute03/testRoute04/testRoute05/testParameter01<< --> Routing aborted!");
    }
  }
  
  /**
   * Method: parse(String route) with one parameter ("/testRoute06/testRoute07/testParameter01/testParameter02")
   */
  @Test
  void testParseRoute12() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("/MockShell/testRoute06/testRoute07/testParameter01/testParameter02");
    } catch (RouterException e) {
      Assertions.fail();
    }
    Assertions.assertEquals("/MockShell",
                            routeResult.getShell());
    Assertions.assertEquals("/MockShell/testRoute06/testRoute07/*/*",
                            routeResult.getRoute());
    Assertions.assertEquals("testParameter01",
                            routeResult.getParameterValues()
                                       .get(0));
    Assertions.assertEquals("testParameter02",
                            routeResult.getParameterValues()
                                       .get(1));
  }
  
  /**
   * Method: parse(String route) with one parameter ("/testRoute02/testParameter01/testParameter02")
   */
  @Test
  void testParseRoute13() {
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse("/MockShell/testRoute02/testParameter01/testParameter03");
    } catch (RouterException e) {
      Assertions.fail();
    }
    Assertions.assertEquals("/MockShell",
                            routeResult.getShell());
    Assertions.assertEquals("/MockShell/testRoute02/*/*",
                            routeResult.getRoute());
    Assertions.assertEquals("testParameter01",
                            routeResult.getParameterValues()
                                       .get(0));
  }
  
  /**
   * Method: generate(String route, String... params)
   */
  @Test
  void testGenerateRoute01() {
    String      route       = "MockShell/testRoute01";
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse(route);
    } catch (RouterException e) {
      Assertions.fail();
    }
    String generateRoute = this.router.generate(routeResult.getRoute());
    Assertions.assertEquals(route,
                            generateRoute);
  }
  
  /**
   * Method: generate(String route, String... params)
   */
  @Test
  void testGenerateRoute02() {
    String      route       = "MockShell/testRoute02/testParameter01";
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse(route);
    } catch (RouterException e) {
      Assertions.fail();
    }
    String generateRoute = this.router.generate(routeResult.getRoute(),
                                                routeResult.getParameterValues()
                                                           .toArray(new String[0]));
    Assertions.assertEquals(route + "/",
                            generateRoute);
  }
  
  /**
   * Method: generate(String route, String... params)
   */
  @Test
  void testGenerateRoute03() {
    String      route       = "MockShell/testRoute02/testParameter01/testParameter02";
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse(route);
    } catch (RouterException e) {
      Assertions.fail();
    }
    String generateRoute = this.router.generate(routeResult.getRoute(),
                                                routeResult.getParameterValues()
                                                           .toArray(new String[0]));
    Assertions.assertEquals(route,
                            generateRoute);
  }
  
  /**
   * Method: generate(String route, String... params)
   */
  @Test
  void testGenerateRoute04() {
    String      route       = "MockShell/testRoute02//testParameter02";
    RouteResult routeResult = null;
    try {
      routeResult = this.router.parse(route);
    } catch (RouterException e) {
      Assertions.fail();
    }
    String generateRoute = this.router.generate(routeResult.getRoute(),
                                                routeResult.getParameterValues()
                                                           .toArray(new String[0]));
    Assertions.assertEquals(route,
                            generateRoute);
  }
  
}
