package com.github.nalukit.nalu.client;

import com.github.nalukit.nalu.client.internal.PropertyFactory;
import com.github.nalukit.nalu.client.internal.PropertyFactory.ErrorHandlingMethod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RouterUtilsTest {

  @Test
  void testMatch01Ok() {
    String route = "/app/person/3/edit";
    String with = "/app/person/*/edit";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             true,
                             true,
                             false,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertTrue(Nalu.match(route,
                                     with));
  }

  @Test
  void testMatch02Fail() {
    String route = "/app/person/3/edit";
    String with = "/app/person/*";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             true,
                             true,
                             false,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertFalse(Nalu.match(route,
                                      with));
  }

  @Test
  void testMatch03Fail() {
    String route = "/app/person/3/edit";
    String with = "/app/*/person/edit";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             true,
                             true,
                             false,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertFalse(Nalu.match(route,
                                      with));
  }

  @Test
  void testMatch04Fail() {
    String route = "/app/person/3/edit";
    String with = "/app/*/person/*";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             true,
                             true,
                             false,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertFalse(Nalu.match(route,
                                      with));
  }

  @Test
  void testMatch05Ok() {
    String route = "/app/person/3/edit";
    String with = "/app/person/*/*";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             true,
                             true,
                             false,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertTrue(Nalu.match(route,
                                     with));
  }

  @Test
  void testMatch06Fail() {
    String route = "/";
    String with = "/app/person/*/*";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             true,
                             true,
                             false,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertFalse(Nalu.match(route,
                                      with));
  }

  @Test
  void testMatch07Ok() {
    String route = "/";
    String with = "/";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             true,
                             true,
                             false,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertTrue(Nalu.match(route,
                                     with));
  }

  @Test
  void testMatch08Ok() {
    String route = "/";
    String with = "/*";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             true,
                             true,
                             false,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertTrue(Nalu.match(route,
                                     with));
  }

  @Test
  void testMatch09Fail() {
    String route = "/app/person/search";
    String with = "/app/person/*/edit";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             true,
                             true,
                             false,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertFalse(Nalu.match(route,
                                      with));
  }

  @Test
  void testMatch51Ok() {
    String route = "/app/person/:3/edit";
    String with = "/app/person/*/edit";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             true,
                             true,
                             true,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertTrue(Nalu.match(route,
                                     with));
  }

  @Test
  void testMatch52Ok() {
    String route = "/app/person/:3/edit";
    String with = "/app/person/*";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             true,
                             true,
                             true,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertFalse(Nalu.match(route,
                                      with));
  }

  @Test
  void testMatch59FailOk() {
    String route = "/app/person/:3/edit";
    String with = "/app/person/*";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             true,
                             true,
                             true,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertFalse(Nalu.match(route,
                                      with,
                                      true));
  }

  @Test
  void testMatch53Fail() {
    String route = "/app/person/:3/edit";
    String with = "/app/*/person/edit";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             true,
                             true,
                             true,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertFalse(Nalu.match(route,
                                      with));
  }

  @Test
  void testMatch54Fail() {
    String route = "/app/person/:3/edit";
    String with = "/app/*/person/*";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             true,
                             true,
                             true,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertFalse(Nalu.match(route,
                                      with));
  }

  @Test
  void testMatch55Fail() {
    String route = "/app/person/:3/edit";
    String with = "/app/person/*/*";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             true,
                             true,
                             true,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertFalse(Nalu.match(route,
                                      with));
  }

  @Test
  void testMatch56Fail() {
    String route = "/";
    String with = "/app/person/*/*";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             true,
                             true,
                             true,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertFalse(Nalu.match(route,
                                      with));
  }

  @Test
  void testMatch57Ok() {
    String route = "/";
    String with = "/";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             true,
                             true,
                             true,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertTrue(Nalu.match(route,
                                     with));
  }

  @Test
  void testMatch58Ok() {
    String route = "/:";
    String with = "/*";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             true,
                             true,
                             true,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertTrue(Nalu.match(route,
                                     with));
  }

  @Test
  void testMatch101Ok() {
    String route = "/app/person/3/edit";
    String with = "/app/person/*/edit";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             false,
                             true,
                             false,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertTrue(Nalu.match(route,
                                     with));
  }

  @Test
  void testMatch102Fail() {
    String route = "/app/person/3/edit";
    String with = "/app/person/*";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             false,
                             true,
                             false,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertFalse(Nalu.match(route,
                                      with));
  }

  @Test
  void testMatch103Fail() {
    String route = "/app/person/3/edit";
    String with = "/app/*/person/edit";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             false,
                             true,
                             false,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertFalse(Nalu.match(route,
                                      with));
  }

  @Test
  void testMatch104Fail() {
    String route = "/app/person/3/edit";
    String with = "/app/*/person/*";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             false,
                             true,
                             false,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertFalse(Nalu.match(route,
                                      with));
  }

  @Test
  void testMatch105Ok() {
    String route = "/app/person/3/edit";
    String with = "/app/person/*/*";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             false,
                             true,
                             false,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertTrue(Nalu.match(route,
                                     with));
  }

  @Test
  void testMatch106Fail() {
    String route = "/";
    String with = "/app/person/*/*";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             false,
                             true,
                             false,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertFalse(Nalu.match(route,
                                      with));
  }

  @Test
  void testMatch107Ok() {
    String route = "/";
    String with = "/";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             false,
                             true,
                             false,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertTrue(Nalu.match(route,
                                     with));
  }

  @Test
  void testMatch108Ok() {
    String route = "/";
    String with = "/*";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             false,
                             true,
                             false,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertTrue(Nalu.match(route,
                                     with));
  }

  @Test
  void testMatch109Fail() {
    String route = "/app/person/search";
    String with = "/app/person/*/edit";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             false,
                             true,
                             false,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertFalse(Nalu.match(route,
                                      with));
  }

  @Test
  void testMatch151Ok() {
    String route = "/app/person/:3/edit";
    String with = "/app/person/*/edit";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             false,
                             true,
                             true,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertTrue(Nalu.match(route,
                                     with));
  }

  @Test
  void testMatch152Ok() {
    String route = "/app/person/:3/edit";
    String with = "/app/person/*";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             false,
                             true,
                             true,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertFalse(Nalu.match(route,
                                      with));
  }

  @Test
  void testMatch159FailOk() {
    String route = "/app/person/:3/edit";
    String with = "/app/person/*";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             false,
                             true,
                             true,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertFalse(Nalu.match(route,
                                      with,
                                      true));
  }

  @Test
  void testMatch153Fail() {
    String route = "/app/person/:3/edit";
    String with = "/app/*/person/edit";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             false,
                             true,
                             true,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertFalse(Nalu.match(route,
                                      with));
  }

  @Test
  void testMatch154Fail() {
    String route = "/app/person/:3/edit";
    String with = "/app/*/person/*";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             false,
                             true,
                             true,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertFalse(Nalu.match(route,
                                      with));
  }

  @Test
  void testMatch155Fail() {
    String route = "/app/person/:3/edit";
    String with = "/app/person/*/*";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             false,
                             true,
                             true,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertFalse(Nalu.match(route,
                                      with));
  }

  @Test
  void testMatch156Fail() {
    String route = "/";
    String with = "/app/person/*/*";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             false,
                             true,
                             true,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertFalse(Nalu.match(route,
                                      with));
  }

  @Test
  void testMatch157Ok() {
    String route = "/";
    String with = "/";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             false,
                             true,
                             true,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertTrue(Nalu.match(route,
                                     with));
  }

  @Test
  void testMatch158Ok() {
    String route = "/:";
    String with = "/*";
    PropertyFactory.get()
                   .register("startShell/startRoute",
                             false,
                             true,
                             true,
                             false,
                             ErrorHandlingMethod.ROUTING);
    Assertions.assertTrue(Nalu.match(route,
                                     with));
  }

}