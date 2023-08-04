package com.github.nalukit.nalu.client.util;

import com.github.nalukit.nalu.client.internal.PropertyFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NaluUtilsTest {

  @Test
  void testConvertRoute01() {
    String routeToConvert = "/";
    String convertedRoute = NaluUtils.INSTANCE.convertRoute(routeToConvert);
    Assertions.assertEquals(routeToConvert,
                            convertedRoute);
  }

  @Test
  void testConvertRoute02() {
    String routeToConvert = "/shell";
    String convertedRoute = NaluUtils.INSTANCE.convertRoute(routeToConvert);
    Assertions.assertEquals(routeToConvert,
                            convertedRoute);
  }

  @Test
  void testConvertRoute03() {
    String routeToConvert = "/shell/part01";
    String convertedRoute = NaluUtils.INSTANCE.convertRoute(routeToConvert);
    Assertions.assertEquals(routeToConvert,
                            convertedRoute);
  }

  @Test
  void testConvertRoute04() {
    String routeToConvert = "/shell/part01/part02";
    String convertedRoute = NaluUtils.INSTANCE.convertRoute(routeToConvert);
    Assertions.assertEquals(routeToConvert,
                            convertedRoute);
  }

  @Test
  void testConvertRoute05() {
    String routeToConvert = "/*";
    String convertedRoute = NaluUtils.INSTANCE.convertRoute(routeToConvert);
    Assertions.assertEquals(routeToConvert,
                            convertedRoute);
  }

  @Test
  void testConvertRoute06() {
    String routeToConvert = "/shell/*";
    String convertedRoute = NaluUtils.INSTANCE.convertRoute(routeToConvert);
    Assertions.assertEquals(routeToConvert,
                            convertedRoute);
  }

  @Test
  void testConvertRoute07() {
    String routeToConvert = "/shell/part01/*";
    String convertedRoute = NaluUtils.INSTANCE.convertRoute(routeToConvert);
    Assertions.assertEquals(routeToConvert,
                            convertedRoute);
  }

  @Test
  void testConvertRoute08() {
    String routeToConvert = "/shell/part01/part02/*";
    String convertedRoute = NaluUtils.INSTANCE.convertRoute(routeToConvert);
    Assertions.assertEquals(routeToConvert,
                            convertedRoute);
  }

  @Test
  void testConvertRoute09() {
    String routeToConvert = "/*";
    String convertedRoute = NaluUtils.INSTANCE.convertRoute(routeToConvert);
    Assertions.assertEquals(routeToConvert,
                            convertedRoute);
  }

  @Test
  void testConvertRoute10() {
    String routeToConvert = "/shell/*";
    String convertedRoute = NaluUtils.INSTANCE.convertRoute(routeToConvert);
    Assertions.assertEquals(routeToConvert,
                            convertedRoute);
  }

  @Test
  void testConvertRoute11() {
    String routeToConvert = "/shell/*/part02";
    String convertedRoute = NaluUtils.INSTANCE.convertRoute(routeToConvert);
    Assertions.assertEquals(routeToConvert,
                            convertedRoute);
  }

  @Test
  void testConvertRoute12() {
    String routeToConvert = "/shell/part01/*";
    String convertedRoute = NaluUtils.INSTANCE.convertRoute(routeToConvert);
    Assertions.assertEquals(routeToConvert,
                            convertedRoute);
  }

  @Test
  void testConvertRoute13() {
    String routeToConvert = "/shell/*/*";
    String convertedRoute = NaluUtils.INSTANCE.convertRoute(routeToConvert);
    Assertions.assertEquals(routeToConvert,
                            convertedRoute);
  }

  @Test
  void testConvertRoute14() {
    String routeToConvert = "/shell/*/part01/*";
    String convertedRoute = NaluUtils.INSTANCE.convertRoute(routeToConvert);
    Assertions.assertEquals(routeToConvert,
                            convertedRoute);
  }

  @Test
  void testConvertRoute15() {
    String routeToConvert = "/shell/part01/*/*";
    String convertedRoute = NaluUtils.INSTANCE.convertRoute(routeToConvert);
    Assertions.assertEquals(routeToConvert,
                            convertedRoute);
  }

  @Test
  void testConvertRoute16() {
    String routeToConvert = "/:parm01";
    String expectedRoute  = "/*";
    String convertedRoute = NaluUtils.INSTANCE.convertRoute(routeToConvert);
    Assertions.assertEquals(expectedRoute,
                            convertedRoute);
  }

  @Test
  void testConvertRoute17() {
    String routeToConvert = "/shell/:parm01";
    String expectedRoute  = "/shell/*";
    String convertedRoute = NaluUtils.INSTANCE.convertRoute(routeToConvert);
    Assertions.assertEquals(expectedRoute,
                            convertedRoute);
  }

  @Test
  void testConvertRoute18() {
    String routeToConvert = "/shell/part01/*";
    String expectedRoute  = "/shell/part01/*";
    String convertedRoute = NaluUtils.INSTANCE.convertRoute(routeToConvert);
    Assertions.assertEquals(expectedRoute,
                            convertedRoute);
  }

  @Test
  void testConvertRoute19() {
    String routeToConvert = "/shell/part01/part02/:part01";
    String expectedRoute  = "/shell/part01/part02/*";
    String convertedRoute = NaluUtils.INSTANCE.convertRoute(routeToConvert);
    Assertions.assertEquals(expectedRoute,
                            convertedRoute);
  }

  @Test
  void testConvertRoute20() {
    String routeToConvert = "/shell/:parm01/part02";
    String expectedRoute  = "/shell/*/part02";
    String convertedRoute = NaluUtils.INSTANCE.convertRoute(routeToConvert);
    Assertions.assertEquals(expectedRoute,
                            convertedRoute);
  }

  @Test
  void testConvertRoute21() {
    String routeToConvert = "/shell/part01/:parm01";
    String expectedRoute  = "/shell/part01/*";
    String convertedRoute = NaluUtils.INSTANCE.convertRoute(routeToConvert);
    Assertions.assertEquals(expectedRoute,
                            convertedRoute);
  }

  @Test
  void testConvertRoute22() {
    String routeToConvert = "/shell/:parm01/:parm02";
    String expectedRoute  = "/shell/*/*";
    String convertedRoute = NaluUtils.INSTANCE.convertRoute(routeToConvert);
    Assertions.assertEquals(expectedRoute,
                            convertedRoute);
  }

  @Test
  void testConvertRoute23() {
    String routeToConvert = "/shell/:parm01/part01/:parm02";
    String expectedRoute  = "/shell/*/part01/*";
    String convertedRoute = NaluUtils.INSTANCE.convertRoute(routeToConvert);
    Assertions.assertEquals(expectedRoute,
                            convertedRoute);
  }

  @Test
  void testConvertRoute24() {
    String routeToConvert = "/shell/part01/:parm01/:parm02";
    String expectedRoute  = "/shell/part01/*/*";
    String convertedRoute = NaluUtils.INSTANCE.convertRoute(routeToConvert);
    Assertions.assertEquals(expectedRoute,
                            convertedRoute);
  }

  @Test
  void testConvertRoute25() {
    String routeToConvert = null;
    String expectedRoute  = "";
    String convertedRoute = NaluUtils.INSTANCE.convertRoute(routeToConvert);
    Assertions.assertEquals(expectedRoute,
                            convertedRoute);
  }

  @Test
  void testCompareRoutes01() {
    String routeToCompare01 = "";
    String routeToCompare02 = null;
    boolean result = NaluUtils.INSTANCE.compareRoutes(routeToCompare01,
                                                      routeToCompare02);
    Assertions.assertTrue(result);
  }

  @Test
  void testCompareRoutes02() {
    String routeToCompare01 = "/";
    String routeToCompare02 = "/";
    boolean result = NaluUtils.INSTANCE.compareRoutes(routeToCompare01,
                                                      routeToCompare02);
    Assertions.assertTrue(result);
  }

  @Test
  void testCompareRoutes03() {
    String routeToCompare01 = "/shell";
    String routeToCompare02 = "/shell";
    boolean result = NaluUtils.INSTANCE.compareRoutes(routeToCompare01,
                                                      routeToCompare02);
    Assertions.assertTrue(result);
  }

  @Test
  void testCompareRoutes04() {
    String routeToCompare01 = "/shell/part01";
    String routeToCompare02 = "/shell/part01";
    boolean result = NaluUtils.INSTANCE.compareRoutes(routeToCompare01,
                                                      routeToCompare02);
    Assertions.assertTrue(result);
  }

  @Test
  void testCompareRoutes05() {
    String routeToCompare01 = "/shell/*/part01";
    String routeToCompare02 = "/shell/*/part01";
    boolean result = NaluUtils.INSTANCE.compareRoutes(routeToCompare01,
                                                      routeToCompare02);
    Assertions.assertTrue(result);
  }

  @Test
  void testCompareRoutes06() {
    String routeToCompare01 = "/shell/part01/*";
    String routeToCompare02 = "/shell/part01/*";
    boolean result = NaluUtils.INSTANCE.compareRoutes(routeToCompare01,
                                                      routeToCompare02);
    Assertions.assertTrue(result);
  }

  @Test
  void testCompareRoutes07() {
    String routeToCompare01 = "/shell/:parm01/part01";
    String routeToCompare02 = "/shell/*/part01";
    boolean result = NaluUtils.INSTANCE.compareRoutes(routeToCompare01,
                                                      routeToCompare02);
    Assertions.assertTrue(result);
  }

  @Test
  void testCompareRoutes08() {
    String routeToCompare01 = "/shell/part01/:parm01";
    String routeToCompare02 = "/shell/part01/*";
    boolean result = NaluUtils.INSTANCE.compareRoutes(routeToCompare01,
                                                      routeToCompare02);
    Assertions.assertTrue(result);
  }

  @Test
  void testCompareRoutes09() {
    String routeToCompare01 = "/shell/*/part01";
    String routeToCompare02 = "/shell/:parm01/part01";
    boolean result = NaluUtils.INSTANCE.compareRoutes(routeToCompare01,
                                                      routeToCompare02);
    Assertions.assertTrue(result);
  }

  @Test
  void testCompareRoutes10() {
    String routeToCompare01 = "/shell/part01/*";
    String routeToCompare02 = "/shell/part01/:parm01";
    boolean result = NaluUtils.INSTANCE.compareRoutes(routeToCompare01,
                                                      routeToCompare02);
    Assertions.assertTrue(result);
  }

  @Test
  void testCompareRoutes11() {
    String routeToCompare01 = "/shell/part01/part02/*";
    String routeToCompare02 = "/shell/part01/:parm01";
    boolean result = NaluUtils.INSTANCE.compareRoutes(routeToCompare01,
                                                      routeToCompare02);
    Assertions.assertFalse(result);
  }

  @Test
  void testCompareRoutes12() {
    String routeToCompare01 = "/shell/part01/*/part02";
    String routeToCompare02 = "/shell/part01/:parm01";
    boolean result = NaluUtils.INSTANCE.compareRoutes(routeToCompare01,
                                                      routeToCompare02);
    Assertions.assertFalse(result);
  }

  @Test
  void testCleanRoute01() {
    PropertyFactory.INSTANCE.register("/shell/login/",
                                      "/shell/login/",
                                      true,
                                      false,
                                      false,
                                      false,
                                      true);
    Assertions.assertEquals("",
                            NaluUtils.INSTANCE.cleanRoute("#"));
  }

  @Test
  void testCleanRoute02() {
    PropertyFactory.INSTANCE.register("/shell/login/",
                                      "/shell/login/",
                                      true,
                                      false,
                                      false,
                                      false,
                                      true);
    Assertions.assertEquals("/login/user",
                            NaluUtils.INSTANCE.cleanRoute("/login/user#"));
  }

  @Test
  void testCleanRoute03() {
    PropertyFactory.INSTANCE.register("/shell/login/",
                                      "/shell/login/",
                                      true,
                                      false,
                                      false,
                                      false,
                                      true);
    Assertions.assertEquals("/login/user/",
                            NaluUtils.INSTANCE.cleanRoute("/login/user/#"));
  }

}