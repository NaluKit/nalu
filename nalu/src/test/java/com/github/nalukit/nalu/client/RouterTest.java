package com.github.nalukit.nalu.client;

import com.github.nalukit.nalu.client.internal.route.HashResult;
import com.github.nalukit.nalu.client.internal.route.RouterException;
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
public class RouterTest {

  private Router router;

  @Before
  public void before() {
    this.router = new Router(Utils.createPlugin(true,
                                                true),
                             Utils.createShellConfiguration(),
                             Utils.createRouterConfiguration(),
                             Utils.createCompositeConfiguration());
  }

  @After
  public void after() {
    this.router = null;
  }

  /**
   * Method: parse(String hash) without paramter ("/MockShell")
   */
  @Test
  public void testParseHashViewportOnly() {
    HashResult hashResult = null;
    try {
      hashResult = this.router.parse("/MockShell");
    } catch (RouterException e) {
      Assert.fail();
    }
    Assert.assertEquals("route test with leading '/'",
                        "/MockShell",
                        hashResult.getShell());
    Assert.assertEquals("route test with leading '/' and no parameters",
                        "/MockShell",
                        hashResult.getRoute());
  }

  /**
   * Method: parse(String hash) without paramter ("/textViewport/testRoute01")
   */
  @Test
  public void testParseHash01() {
    HashResult hashResult = null;
    try {
      hashResult = this.router.parse("/MockShell/testRoute01");
    } catch (RouterException e) {
      Assert.fail();
    }
    Assert.assertEquals("route test with leading '/'",
                        "/MockShell",
                        hashResult.getShell());
    Assert.assertEquals("route test with leading '/' and no parameters",
                        "/MockShell/testRoute01",
                        hashResult.getRoute());
  }

  /**
   * Method: parse(String hash) without paramter ("testRoute01")
   */
  @Test
  public void testParseHash02() {
    HashResult hashResult = null;
    try {
      hashResult = this.router.parse("MockShell/testRoute01");
    } catch (RouterException e) {
      Assert.fail();
    }
    Assert.assertEquals("route test without leading '/'",
                        "/MockShell",
                        hashResult.getShell());
    Assert.assertEquals("route test with leading '/' and no parameters",
                        "/MockShell/testRoute01",
                        hashResult.getRoute());
  }

  /**
   * Method: parse(String hash) with one paramter ("/testRoute02/testParameter01")
   */
  @Test
  public void testParseHash03() {
    HashResult hashResult = null;
    try {
      hashResult = this.router.parse("/MockShell/testRoute02/testParameter01");
    } catch (RouterException e) {
      Assert.fail();
    }
    Assert.assertEquals("route test with leading '/'",
                        "/MockShell",
                        hashResult.getShell());
   Assert.assertEquals("route test with leading '/' and one parameters",
                        "/MockShell/testRoute02",
                        hashResult.getRoute());
    Assert.assertEquals("route test with leading '/' and one parameters",
                        "testParameter01",
                        hashResult.getParameterValues()
                                  .get(0));
  }

  /**
   * Method: parse(String hash) with one paramter ("/testRoute02/testParameter01/testParameter02")
   */
  @Test
  public void testParseHash04() {
    HashResult hashResult = null;
    try {
      hashResult = this.router.parse("/MockShell/testRoute02/testParameter01/testParameter02");
    } catch (RouterException e) {
      Assert.fail();
    }
    Assert.assertEquals("route test with leading '/'",
                        "/MockShell",
                        hashResult.getShell());
    Assert.assertEquals("route test with leading '/' and two parameters",
                        "testParameter01",
                        hashResult.getParameterValues()
                                  .get(0));
    Assert.assertEquals("route test with leading '/' and two parameters",
                        "testParameter02",
                        hashResult.getParameterValues()
                                  .get(1));
  }

  /**
   * Method: parse(String hash) with one paramter ("/testRoute02//testParameter02")
   */
  @Test
  public void testParseHash05() {
    HashResult hashResult = null;
    try {
      hashResult = this.router.parse("/MockShell/testRoute02//testParameter02");
    } catch (RouterException e) {
      Assert.fail();
    }
    Assert.assertEquals("route test with leading '/'",
                        "/MockShell",
                        hashResult.getShell());
    Assert.assertEquals("route test with leading '/' and two parameters, first one empty",
                        "/MockShell/testRoute02",
                        hashResult.getRoute());
    Assert.assertEquals("route test with leading '/' and two parameters, first one empty",
                        "",
                        hashResult.getParameterValues()
                                  .get(0));
    Assert.assertEquals("route test with leading '/' and two parameters, first one empty",
                        "testParameter02",
                        hashResult.getParameterValues()
                                  .get(1));
  }

  /**
   * Method: parse(String hash) with one paramter ("testRoute02/testParameter01")
   */
  @Test
  public void testParseHash06() {
    HashResult hashResult = null;
    try {
      hashResult = this.router.parse("MockShell/testRoute02/testParameter01");
    } catch (RouterException e) {
      Assert.fail();
    }
    Assert.assertEquals("route test without leading '/'",
                        "/MockShell",
                        hashResult.getShell());
    Assert.assertEquals("route test with leading '/' and one parameters",
                        "/MockShell/testRoute02",
                        hashResult.getRoute());
    Assert.assertEquals("route test with leading '/' and one parameters",
                        "testParameter01",
                        hashResult.getParameterValues()
                                  .get(0));
  }

  /**
   * Method: parse(String hash) with one paramter ("testRoute02/testParameter01/testParameter02")
   */
  @Test
  public void testParseHash07() {
    HashResult hashResult = null;
    try {
      hashResult = this.router.parse("MockShell/testRoute02/testParameter01/testParameter02");
    } catch (RouterException e) {
      Assert.fail();
    }
    Assert.assertEquals("route test without leading '/'",
                        "/MockShell",
                        hashResult.getShell());
    Assert.assertEquals("route test with leading '/' and two parameters",
                        "/MockShell/testRoute02",
                        hashResult.getRoute());
    Assert.assertEquals("route test with leading '/' and two parameters",
                        "testParameter01",
                        hashResult.getParameterValues()
                                  .get(0));
    Assert.assertEquals("route test with leading '/' and two parameters",
                        "testParameter02",
                        hashResult.getParameterValues()
                                  .get(1));
  }

  /**
   * Method: parse(String hash) with one paramter ("testRoute02//testParameter02")
   */
  @Test
  public void testParseHash08() {
    HashResult hashResult = null;
    try {
      hashResult = this.router.parse("MockShell/testRoute02//testParameter02");
    } catch (RouterException e) {
      Assert.fail();
    }
    Assert.assertEquals("route test without leading '/'",
                        "/MockShell",
                        hashResult.getShell());
    Assert.assertEquals("route test without leading '/' and two parameters, first one empty",
                        "/MockShell/testRoute02",
                        hashResult.getRoute());
    Assert.assertEquals("route test without leading '/' and two parameters, first one empty",
                        "",
                        hashResult.getParameterValues()
                                  .get(0));
    Assert.assertEquals("route test without leading '/' and two parameters, first one empty",
                        "testParameter02",
                        hashResult.getParameterValues()
                                  .get(1));
  }

  /**
   * Method: parse(String hash) with one paramter ("/testRoute03/testRoute04/testRoute05")
   */
  @Test
  public void testParseHash09() {
    HashResult hashResult = null;
    try {
      hashResult = this.router.parse("/MockShell/testRoute03/testRoute04/testRoute05");
    } catch (RouterException e) {
      Assert.fail();
    }
    Assert.assertEquals("route test with leading '/'",
                        "/MockShell",
                        hashResult.getShell());
    Assert.assertEquals("route test with leading '/'",
                        "/MockShell/testRoute03/testRoute04/testRoute05",
                        hashResult.getRoute());
  }

  /**
   * Method: parse(String hash) with one paramter ("/testRoute03/testRoute04/testRoute05")
   */
  @Test
  public void testParseHash10() {
    HashResult hashResult = null;
    try {
      hashResult = this.router.parse("MockShell/testRoute03/testRoute04/testRoute05");
    } catch (RouterException e) {
      Assert.fail();
    }
    Assert.assertEquals("route test without leading '/'",
                        "/MockShell",
                        hashResult.getShell());
    Assert.assertEquals("route test without leading '/'",
                        "/MockShell/testRoute03/testRoute04/testRoute05",
                        hashResult.getRoute());
  }

  /**
   * Method: parse(String hash) with one paramter ("/testRoute03/testRoute04/testRoute05/testParameter01")
   */
  @Test
  public void testParseHash11() {
    try {
      this.router.parse("/MockShell/testRoute03/testRoute04/testRoute05/testParameter01");
      Assert.fail("Expected exception to be thrown");
    } catch (RouterException e) {
      Assert.assertThat(e,
                        Is.isA(RouterException.class));
      Assert.assertThat(e.getMessage(),
                        Is.is("hash >>/MockShell/testRoute03/testRoute04/testRoute05/testParameter01<< --> found routing >>/MockShell/testRoute03/testRoute04/testRoute05<< -> too much parameters! Expeted >>0<< - found >>1<<"));
    }
  }

  /**
   * Method: parse(String hash) with one paramter ("/testRoute06/testRoute07/testParameter01/testParameter02")
   */
  @Test
  public void testParseHash12() {
    HashResult hashResult = null;
    try {
      hashResult = this.router.parse("/MockShell/testRoute06/testRoute07/testParameter01/testParameter02");
    } catch (RouterException e) {
      Assert.fail();
    }
    Assert.assertEquals("route test without leading '/'",
                        "/MockShell",
                        hashResult.getShell());
    Assert.assertEquals("route test without leading '/' and two parameters, first one empty",
                        "/MockShell/testRoute06/testRoute07",
                        hashResult.getRoute());
    Assert.assertEquals("route test with leading '/', complex path and two parameters, both parametes exist",
                        "testParameter01",
                        hashResult.getParameterValues()
                                  .get(0));
    Assert.assertEquals("route test without leading '/' and two parameters, first one empty",
                        "testParameter02",
                        hashResult.getParameterValues()
                                  .get(1));
  }

  /**
   * Method: generateHash(String route, String... parms)
   */
  @Test
  public void testGenerateHash01() {
    String hash = "MockShell/testRoute01";
    HashResult hashResult = null;
    try {
      hashResult = this.router.parse(hash);
    } catch (RouterException e) {
      Assert.fail();
    }
    String generateHash = this.router.generateHash(hashResult.getRoute());
    Assert.assertEquals("genereate hash with no parameters",
                        hash,
                        generateHash);
  }

  /**
   * Method: generateHash(String route, String... parms)
   */
  @Test
  public void testGenerateHash02() {
    String hash = "MockShell/testRoute02/testParameter01";
    HashResult hashResult = null;
    try {
      hashResult = this.router.parse(hash);
    } catch (RouterException e) {
      Assert.fail();
    }
    String generateHash = this.router.generateHash(hashResult.getRoute(),
                                                   hashResult.getParameterValues()
                                                             .toArray(new String[0]));
    Assert.assertEquals("generate hash with one parameters",
                        hash,
                        generateHash);
  }

  /**
   * Method: generateHash(String route, String... parms)
   */
  @Test
  public void testGenerateHash03() {
    String hash = "MockShell/testRoute02/testParameter01/testParameter02";
    HashResult hashResult = null;
    try {
      hashResult = this.router.parse(hash);
    } catch (RouterException e) {
      Assert.fail();
    }
    String generateHash = this.router.generateHash(hashResult.getRoute(),
                                                   hashResult.getParameterValues()
                                                             .toArray(new String[0]));
    Assert.assertEquals("genereate hash with two parameters",
                        hash,
                        generateHash);
  }

  /**
   * Method: generateHash(String route, String... parms)
   */
  @Test
  public void testGenerateHash04() {
    String hash = "MockShell/testRoute02//testParameter02";
    HashResult hashResult = null;
    try {
      hashResult = this.router.parse(hash);
    } catch (RouterException e) {
      Assert.fail();
    }
    String generateHash = this.router.generateHash(hashResult.getRoute(),
                                                   hashResult.getParameterValues()
                                                             .toArray(new String[0]));
    Assert.assertEquals("genereate hash with two parameters, first empty",
                        hash,
                        generateHash);
  }
}
