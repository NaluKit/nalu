package com.github.mvp4g.nalu.client;

import com.github.mvp4g.nalu.client.internal.route.HashResult;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Router Tester.
 *
 * @author Frank Hossfeld
 * @version 1.0
 * @since <pre>Aug 26, 2018</pre>
 */
public class RouterTest {

  private Router router;

  @Before
  public void before() {
    this.router = new Router(Utils.createPlugin(true,
                                                true),
                             Utils.createRouterConfiguration());
  }

  @After
  public void after() {
    this.router = null;
  }

  /**
   * Method: parse(String hash) without paramter ("/testRoute")
   */
  @Test
  public void testParseHashWithoutParameter01() {
    HashResult hashResult = this.router.parse("/testRoute");
    Assert.assertEquals("Route test with leading '/' and not parameters",
                        "testRoute",
                        hashResult.getRoute());
  }

  /**
   * Method: parse(String hash) without paramter ("testRoute")
   */
  @Test
  public void testParseHashWithoutParameter02() {
    HashResult hashResult = this.router.parse("testRoute");
    Assert.assertEquals("Route test without leading '/' and not parameters",
                        "testRoute",
                        hashResult.getRoute());
  }

  /**
   * Method: parse(String hash) with one paramter ("/testRoute/testParameter01")
   */
  @Test
  public void testParseHashWithoutParameter03() {
    HashResult hashResult = this.router.parse("/testRoute/testParameter01");
    Assert.assertEquals("Route test with leading '/' and one parameters",
                        "testRoute",
                        hashResult.getRoute());
    Assert.assertEquals("Route test with leading '/' and one parameters",
                        "testParameter01",
                        hashResult.getParameterValues()
                                  .get(0));
  }

  /**
   * Method: parse(String hash) with one paramter ("/testRoute/testParameter01/testParameter02")
   */
  @Test
  public void testParseHashWithoutParameter04() {
    HashResult hashResult = this.router.parse("/testRoute/testParameter01/testParameter02");
    Assert.assertEquals("Route test with leading '/' and two parameters",
                        "testRoute",
                        hashResult.getRoute());
    Assert.assertEquals("Route test with leading '/' and two parameters",
                        "testParameter01",
                        hashResult.getParameterValues()
                                  .get(0));
    Assert.assertEquals("Route test with leading '/' and two parameters",
                        "testParameter02",
                        hashResult.getParameterValues()
                                  .get(1));
  }

  /**
   * Method: parse(String hash) with one paramter ("/testRoute//testParameter02")
   */
  @Test
  public void testParseHashWithoutParameter05() {
    HashResult hashResult = this.router.parse("/testRoute//testParameter02");
    Assert.assertEquals("Route test with leading '/' and two parameters, first one empty",
                        "testRoute",
                        hashResult.getRoute());
    Assert.assertEquals("Route test with leading '/' and two parameters, first one empty",
                        "",
                        hashResult.getParameterValues()
                                  .get(0));
    Assert.assertEquals("Route test with leading '/' and two parameters, first one empty",
                        "testParameter02",
                        hashResult.getParameterValues()
                                  .get(1));
  }

  /**
   * Method: parse(String hash) with one paramter ("testRoute/testParameter01")
   */
  @Test
  public void testParseHashWithoutParameter06() {
    HashResult hashResult = this.router.parse("testRoute/testParameter01");
    Assert.assertEquals("Route test without leading '/' and one parameters",
                        "testRoute",
                        hashResult.getRoute());
    Assert.assertEquals("Route test without leading '/' and one parameters",
                        "testParameter01",
                        hashResult.getParameterValues()
                                  .get(0));
  }

  /**
   * Method: parse(String hash) with one paramter ("testRoute/testParameter01/testParameter02")
   */
  @Test
  public void testParseHashWithoutParameter07() {
    HashResult hashResult = this.router.parse("testRoute/testParameter01/testParameter02");
    Assert.assertEquals("Route test without leading '/' and two parameters",
                        "testRoute",
                        hashResult.getRoute());
    Assert.assertEquals("Route test without leading '/' and two parameters",
                        "testParameter01",
                        hashResult.getParameterValues()
                                  .get(0));
    Assert.assertEquals("Route test without leading '/' and two parameters",
                        "testParameter02",
                        hashResult.getParameterValues()
                                  .get(1));
  }

  /**
   * Method: parse(String hash) with one paramter ("testRoute//testParameter02")
   */
  @Test
  public void testParseHashWithoutParameter08() {
    HashResult hashResult = this.router.parse("testRoute//testParameter02");
    Assert.assertEquals("Route test without leading '/' and two parameters, first one empty",
                        "testRoute",
                        hashResult.getRoute());
    Assert.assertEquals("Route test without leading '/' and two parameters, first one empty",
                        "",
                        hashResult.getParameterValues()
                                  .get(0));
    Assert.assertEquals("Route test without leading '/' and two parameters, first one empty",
                        "testParameter02",
                        hashResult.getParameterValues()
                                  .get(1));
  }

  /**
   * Method: generateHash(String route, String... parms)
   */
  @Test
  public void testGenerateHash01() {
    String hash = "route";
    HashResult hashResult = this.router.parse(hash);
    String generateHash = this.router.generateHash(hashResult.getRoute(),
                                                   hashResult.getParameterValues()
                                                             .toArray(new String[0]));
    Assert.assertEquals("genereate hash with no parameters",
                        hash,
                        generateHash);
  }

  /**
   * Method: generateHash(String route, String... parms)
   */
  @Test
  public void testGenerateHash02() {
    String hash = "route/testParameter01";
    HashResult hashResult = this.router.parse(hash);
    String generateHash = this.router.generateHash(hashResult.getRoute(),
                                                   hashResult.getParameterValues()
                                                             .toArray(new String[0]));
    Assert.assertEquals("genereate hash with one parameters",
                        hash,
                        generateHash);
  }

  /**
   * Method: generateHash(String route, String... parms)
   */
  @Test
  public void testGenerateHash03() {
    String hash = "route/testParameter01/testParameter02";
    HashResult hashResult = this.router.parse(hash);
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
    String hash = "route//testParameter02";
    HashResult hashResult = this.router.parse(hash);
    String generateHash = this.router.generateHash(hashResult.getRoute(),
                                                   hashResult.getParameterValues()
                                                             .toArray(new String[0]));
    Assert.assertEquals("genereate hash with two parameters, first empty",
                        hash,
                        generateHash);
  }
}
