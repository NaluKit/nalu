package com.github.nalukit.nalu.client.util;

import org.junit.Assert;
import org.junit.Test;

public class NaluUtilsTest {

  @Test
  public void testConvertRoute01() {
    String routeToConvert = "/";
    String convertedRoute = NaluUtils.convertRoute(routeToConvert);
    Assert.assertEquals(routeToConvert,
                        convertedRoute);
  }

  @Test
  public void testConvertRoute02() {
    String routeToConvert = "/shell";
    String convertedRoute = NaluUtils.convertRoute(routeToConvert);
    Assert.assertEquals(routeToConvert,
                        convertedRoute);
  }

  @Test
  public void testConvertRoute03() {
    String routeToConvert = "/shell/part01";
    String convertedRoute = NaluUtils.convertRoute(routeToConvert);
    Assert.assertEquals(routeToConvert,
                        convertedRoute);
  }

  @Test
  public void testConvertRoute04() {
    String routeToConvert = "/shell/part01/part02";
    String convertedRoute = NaluUtils.convertRoute(routeToConvert);
    Assert.assertEquals(routeToConvert,
                        convertedRoute);
  }

  @Test
  public void testConvertRoute05() {
    String routeToConvert = "/*";
    String convertedRoute = NaluUtils.convertRoute(routeToConvert);
    Assert.assertEquals(routeToConvert,
                        convertedRoute);
  }

  @Test
  public void testConvertRoute06() {
    String routeToConvert = "/shell/*";
    String convertedRoute = NaluUtils.convertRoute(routeToConvert);
    Assert.assertEquals(routeToConvert,
                        convertedRoute);
  }

  @Test
  public void testConvertRoute07() {
    String routeToConvert = "/shell/part01/*";
    String convertedRoute = NaluUtils.convertRoute(routeToConvert);
    Assert.assertEquals(routeToConvert,
                        convertedRoute);
  }

  @Test
  public void testConvertRoute08() {
    String routeToConvert = "/shell/part01/part02/*";
    String convertedRoute = NaluUtils.convertRoute(routeToConvert);
    Assert.assertEquals(routeToConvert,
                        convertedRoute);
  }

  @Test
  public void testConvertRoute09() {
    String routeToConvert = "/*";
    String convertedRoute = NaluUtils.convertRoute(routeToConvert);
    Assert.assertEquals(routeToConvert,
                        convertedRoute);
  }

  @Test
  public void testConvertRoute10() {
    String routeToConvert = "/shell/*";
    String convertedRoute = NaluUtils.convertRoute(routeToConvert);
    Assert.assertEquals(routeToConvert,
                        convertedRoute);
  }

  @Test
  public void testConvertRoute11() {
    String routeToConvert = "/shell/*/part02";
    String convertedRoute = NaluUtils.convertRoute(routeToConvert);
    Assert.assertEquals(routeToConvert,
                        convertedRoute);
  }

  @Test
  public void testConvertRoute12() {
    String routeToConvert = "/shell/part01/*";
    String convertedRoute = NaluUtils.convertRoute(routeToConvert);
    Assert.assertEquals(routeToConvert,
                        convertedRoute);
  }

  @Test
  public void testConvertRoute13() {
    String routeToConvert = "/shell/*/*";
    String convertedRoute = NaluUtils.convertRoute(routeToConvert);
    Assert.assertEquals(routeToConvert,
                        convertedRoute);
  }

  @Test
  public void testConvertRoute14() {
    String routeToConvert = "/shell/*/part01/*";
    String convertedRoute = NaluUtils.convertRoute(routeToConvert);
    Assert.assertEquals(routeToConvert,
                        convertedRoute);
  }

  @Test
  public void testConvertRoute15() {
    String routeToConvert = "/shell/part01/*/*";
    String convertedRoute = NaluUtils.convertRoute(routeToConvert);
    Assert.assertEquals(routeToConvert,
                        convertedRoute);
  }

  @Test
  public void testConvertRoute16() {
    String routeToConvert = "/:parm01";
    String expectedRoute = "/*";
    String convertedRoute = NaluUtils.convertRoute(routeToConvert);
    Assert.assertEquals(expectedRoute,
                        convertedRoute);
  }

  @Test
  public void testConvertRoute17() {
    String routeToConvert = "/shell/:parm01";
    String expectedRoute = "/shell/*";
    String convertedRoute = NaluUtils.convertRoute(routeToConvert);
    Assert.assertEquals(expectedRoute,
                        convertedRoute);
  }

  @Test
  public void testConvertRoute18() {
    String routeToConvert = "/shell/part01/*";
    String expectedRoute = "/shell/part01/*";
    String convertedRoute = NaluUtils.convertRoute(routeToConvert);
    Assert.assertEquals(expectedRoute,
                        convertedRoute);
  }

  @Test
  public void testConvertRoute19() {
    String routeToConvert = "/shell/part01/part02/:part01";
    String expectedRoute = "/shell/part01/part02/*";
    String convertedRoute = NaluUtils.convertRoute(routeToConvert);
    Assert.assertEquals(expectedRoute,
                        convertedRoute);
  }

  @Test
  public void testConvertRoute20() {
    String routeToConvert = "/shell/:parm01/part02";
    String expectedRoute = "/shell/*/part02";
    String convertedRoute = NaluUtils.convertRoute(routeToConvert);
    Assert.assertEquals(expectedRoute,
                        convertedRoute);
  }

  @Test
  public void testConvertRoute21() {
    String routeToConvert = "/shell/part01/:parm01";
    String expectedRoute = "/shell/part01/*";
    String convertedRoute = NaluUtils.convertRoute(routeToConvert);
    Assert.assertEquals(expectedRoute,
                        convertedRoute);
  }

  @Test
  public void testConvertRoute22() {
    String routeToConvert = "/shell/:parm01/:parm02";
    String expectedRoute = "/shell/*/*";
    String convertedRoute = NaluUtils.convertRoute(routeToConvert);
    Assert.assertEquals(expectedRoute,
                        convertedRoute);
  }

  @Test
  public void testConvertRoute23() {
    String routeToConvert = "/shell/:parm01/part01/:parm02";
    String expectedRoute = "/shell/*/part01/*";
    String convertedRoute = NaluUtils.convertRoute(routeToConvert);
    Assert.assertEquals(expectedRoute,
                        convertedRoute);
  }

  @Test
  public void testConvertRoute24() {
    String routeToConvert = "/shell/part01/:parm01/:parm02";
    String expectedRoute = "/shell/part01/*/*";
    String convertedRoute = NaluUtils.convertRoute(routeToConvert);
    Assert.assertEquals(expectedRoute,
                        convertedRoute);
  }

  @Test
  public void testConvertRoute25() {
    String routeToConvert = null;
    String expectedRoute = "";
    String convertedRoute = NaluUtils.convertRoute(routeToConvert);
    Assert.assertEquals(expectedRoute,
                        convertedRoute);
  }

  @Test
  public void testCompareRoutes01() {
    String routeToCompare01 = null;
    String routeToCompare02 = null;
    boolean result = NaluUtils.compareRoutes(routeToCompare01, routeToCompare02);
    Assert.assertTrue(result);
  }

  @Test
  public void testCompareRoutes02() {
    String routeToCompare01 = "/";
    String routeToCompare02 = "/";
    boolean result = NaluUtils.compareRoutes(routeToCompare01, routeToCompare02);
    Assert.assertTrue(result);
  }

  @Test
  public void testCompareRoutes03() {
    String routeToCompare01 = "/shell";
    String routeToCompare02 = "/shell";
    boolean result = NaluUtils.compareRoutes(routeToCompare01, routeToCompare02);
    Assert.assertTrue(result);
  }

  @Test
  public void testCompareRoutes04() {
    String routeToCompare01 = "/shell/part01";
    String routeToCompare02 = "/shell/part01";
    boolean result = NaluUtils.compareRoutes(routeToCompare01, routeToCompare02);
    Assert.assertTrue(result);
  }

  @Test
  public void testCompareRoutes05() {
    String routeToCompare01 = "/shell/*/part01";
    String routeToCompare02 = "/shell/*/part01";
    boolean result = NaluUtils.compareRoutes(routeToCompare01,
                                             routeToCompare02);
    Assert.assertTrue(result);
  }

  @Test
  public void testCompareRoutes06() {
    String routeToCompare01 = "/shell/part01/*";
    String routeToCompare02 = "/shell/part01/*";
    boolean result = NaluUtils.compareRoutes(routeToCompare01,
                                             routeToCompare02);
    Assert.assertTrue(result);
  }

  @Test
  public void testCompareRoutes07() {
    String routeToCompare01 = "/shell/:parm01/part01";
    String routeToCompare02 = "/shell/*/part01";
    boolean result = NaluUtils.compareRoutes(routeToCompare01,
                                             routeToCompare02);
    Assert.assertTrue(result);
  }

  @Test
  public void testCompareRoutes08() {
    String routeToCompare01 = "/shell/part01/:parm01";
    String routeToCompare02 = "/shell/part01/*";
    boolean result = NaluUtils.compareRoutes(routeToCompare01,
                                             routeToCompare02);
    Assert.assertTrue(result);
  }

  @Test
  public void testCompareRoutes09() {
    String routeToCompare01 = "/shell/*/part01";
    String routeToCompare02 = "/shell/:parm01/part01";
    boolean result = NaluUtils.compareRoutes(routeToCompare01,
                                             routeToCompare02);
    Assert.assertTrue(result);
  }

  @Test
  public void testCompareRoutes10() {
    String routeToCompare01 = "/shell/part01/*";
    String routeToCompare02 = "/shell/part01/:parm01";
    boolean result = NaluUtils.compareRoutes(routeToCompare01,
                                             routeToCompare02);
    Assert.assertTrue(result);
  }

  @Test
  public void testCompareRoutes11() {
    String routeToCompare01 = "/shell/part01/part02/*";
    String routeToCompare02 = "/shell/part01/:parm01";
    boolean result = NaluUtils.compareRoutes(routeToCompare01,
                                             routeToCompare02);
    Assert.assertFalse(result);
  }

  @Test
  public void testCompareRoutes12() {
    String routeToCompare01 = "/shell/part01/*/part02";
    String routeToCompare02 = "/shell/part01/:parm01";
    boolean result = NaluUtils.compareRoutes(routeToCompare01,
                                             routeToCompare02);
    Assert.assertFalse(result);
  }


}