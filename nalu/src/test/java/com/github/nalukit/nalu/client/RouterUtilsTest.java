package com.github.nalukit.nalu.client;

import com.github.nalukit.nalu.client.internal.PropertyFactory;
import org.junit.Assert;
import org.junit.Test;

public class RouterUtilsTest {

  @Test
  public void testMatch01Ok() {
    String route = "/app/person/3/edit";
    String with = "/app/person/*/edit";
    PropertyFactory.get()
                   .register(true,
                             true,
                             false);
    Assert.assertTrue(Nalu.match(route,
                                 with));
  }

  @Test
  public void testMatch02Fail() {
    String route = "/app/person/3/edit";
    String with = "/app/person/*";
    PropertyFactory.get()
                   .register(true,
                             true,
                             false);
    Assert.assertFalse(Nalu.match(route,
                                  with));
  }

  @Test
  public void testMatch03Fail() {
    String route = "/app/person/3/edit";
    String with = "/app/*/person/edit";
    PropertyFactory.get()
                   .register(true,
                             true,
                             false);
    Assert.assertFalse(Nalu.match(route,
                                  with));
  }

  @Test
  public void testMatch04Fail() {
    String route = "/app/person/3/edit";
    String with = "/app/*/person/*";
    PropertyFactory.get()
                   .register(true,
                             true,
                             false);
    Assert.assertFalse(Nalu.match(route,
                                  with));
  }

  @Test
  public void testMatch05Ok() {
    String route = "/app/person/3/edit";
    String with = "/app/person/*/*";
    PropertyFactory.get()
                   .register(true,
                             true,
                             false);
    Assert.assertTrue(Nalu.match(route,
                                 with));
  }

  @Test
  public void testMatch06Fail() {
    String route = "/";
    String with = "/app/person/*/*";
    PropertyFactory.get()
                   .register(true,
                             true,
                             false);
    Assert.assertFalse(Nalu.match(route,
                                  with));
  }

  @Test
  public void testMatch07Ok() {
    String route = "/";
    String with = "/";
    PropertyFactory.get()
                   .register(true,
                             true,
                             false);
    Assert.assertTrue(Nalu.match(route,
                                 with));
  }

  @Test
  public void testMatch08Ok() {
    String route = "/";
    String with = "/*";
    PropertyFactory.get()
                   .register(true,
                             true,
                             false);
    Assert.assertTrue(Nalu.match(route,
                                 with));
  }

  @Test
  public void testMatch09Fail() {
    String route = "/app/person/search";
    String with = "/app/person/*/edit";
    PropertyFactory.get()
                   .register(true,
                             true,
                             false);
    Assert.assertFalse(Nalu.match(route,
                                  with));
  }

  @Test
  public void testMatch51Ok() {
    String route = "/app/person/:3/edit";
    String with = "/app/person/*/edit";
    PropertyFactory.get()
                   .register(true,
                             true,
                             true);
    Assert.assertTrue(Nalu.match(route,
                                 with));
  }

  @Test
  public void testMatch52Ok() {
    String route = "/app/person/:3/edit";
    String with = "/app/person/*";
    PropertyFactory.get()
                   .register(true,
                             true,
                             true);
    Assert.assertFalse(Nalu.match(route,
                                  with));
  }

  @Test
  public void testMatch59FailOk() {
    String route = "/app/person/:3/edit";
    String with = "/app/person/*";
    PropertyFactory.get()
                   .register(true,
                             true,
                             true);
    Assert.assertFalse(Nalu.match(route,
                                  with,
                                  true));
  }

  @Test
  public void testMatch53Fail() {
    String route = "/app/person/:3/edit";
    String with = "/app/*/person/edit";
    PropertyFactory.get()
                   .register(true,
                             true,
                             true);
    Assert.assertFalse(Nalu.match(route,
                                  with));
  }

  @Test
  public void testMatch54Fail() {
    String route = "/app/person/:3/edit";
    String with = "/app/*/person/*";
    PropertyFactory.get()
                   .register(true,
                             true,
                             true);
    Assert.assertFalse(Nalu.match(route,
                                  with));
  }

  @Test
  public void testMatch55Fail() {
    String route = "/app/person/:3/edit";
    String with = "/app/person/*/*";
    PropertyFactory.get()
                   .register(true,
                             true,
                             true);
    Assert.assertFalse(Nalu.match(route,
                                  with));
  }

  @Test
  public void testMatch56Fail() {
    String route = "/";
    String with = "/app/person/*/*";
    PropertyFactory.get()
                   .register(true,
                             true,
                             true);
    Assert.assertFalse(Nalu.match(route,
                                  with));
  }

  @Test
  public void testMatch57Ok() {
    String route = "/";
    String with = "/";
    PropertyFactory.get()
                   .register(true,
                             true,
                             true);
    Assert.assertTrue(Nalu.match(route,
                                 with));
  }

  @Test
  public void testMatch58Ok() {
    String route = "/:";
    String with = "/*";
    PropertyFactory.get()
                   .register(true,
                             true,
                             true);
    Assert.assertTrue(Nalu.match(route,
                                 with));
  }

  @Test
  public void testMatch101Ok() {
    String route = "/app/person/3/edit";
    String with = "/app/person/*/edit";
    PropertyFactory.get()
                   .register(false,
                             true,
                             false);
    Assert.assertTrue(Nalu.match(route,
                                 with));
  }

  @Test
  public void testMatch102Fail() {
    String route = "/app/person/3/edit";
    String with = "/app/person/*";
    PropertyFactory.get()
                   .register(false,
                             true,
                             false);
    Assert.assertFalse(Nalu.match(route,
                                  with));
  }

  @Test
  public void testMatch103Fail() {
    String route = "/app/person/3/edit";
    String with = "/app/*/person/edit";
    PropertyFactory.get()
                   .register(false,
                             true,
                             false);
    Assert.assertFalse(Nalu.match(route,
                                  with));
  }

  @Test
  public void testMatch104Fail() {
    String route = "/app/person/3/edit";
    String with = "/app/*/person/*";
    PropertyFactory.get()
                   .register(false,
                             true,
                             false);
    Assert.assertFalse(Nalu.match(route,
                                  with));
  }

  @Test
  public void testMatch105Ok() {
    String route = "/app/person/3/edit";
    String with = "/app/person/*/*";
    PropertyFactory.get()
                   .register(false,
                             true,
                             false);
    Assert.assertTrue(Nalu.match(route,
                                 with));
  }

  @Test
  public void testMatch106Fail() {
    String route = "/";
    String with = "/app/person/*/*";
    PropertyFactory.get()
                   .register(false,
                             true,
                             false);
    Assert.assertFalse(Nalu.match(route,
                                  with));
  }

  @Test
  public void testMatch107Ok() {
    String route = "/";
    String with = "/";
    PropertyFactory.get()
                   .register(false,
                             true,
                             false);
    Assert.assertTrue(Nalu.match(route,
                                 with));
  }

  @Test
  public void testMatch108Ok() {
    String route = "/";
    String with = "/*";
    PropertyFactory.get()
                   .register(false,
                             true,
                             false);
    Assert.assertTrue(Nalu.match(route,
                                 with));
  }

  @Test
  public void testMatch109Fail() {
    String route = "/app/person/search";
    String with = "/app/person/*/edit";
    PropertyFactory.get()
                   .register(false,
                             true,
                             false);
    Assert.assertFalse(Nalu.match(route,
                                  with));
  }

  @Test
  public void testMatch151Ok() {
    String route = "/app/person/:3/edit";
    String with = "/app/person/*/edit";
    PropertyFactory.get()
                   .register(false,
                             true,
                             true);
    Assert.assertTrue(Nalu.match(route,
                                 with));
  }

  @Test
  public void testMatch152Ok() {
    String route = "/app/person/:3/edit";
    String with = "/app/person/*";
    PropertyFactory.get()
                   .register(false,
                             true,
                             true);
    Assert.assertFalse(Nalu.match(route,
                                  with));
  }

  @Test
  public void testMatch159FailOk() {
    String route = "/app/person/:3/edit";
    String with = "/app/person/*";
    PropertyFactory.get()
                   .register(false,
                             true,
                             true);
    Assert.assertFalse(Nalu.match(route,
                                  with,
                                  true));
  }

  @Test
  public void testMatch153Fail() {
    String route = "/app/person/:3/edit";
    String with = "/app/*/person/edit";
    PropertyFactory.get()
                   .register(false,
                             true,
                             true);
    Assert.assertFalse(Nalu.match(route,
                                  with));
  }

  @Test
  public void testMatch154Fail() {
    String route = "/app/person/:3/edit";
    String with = "/app/*/person/*";
    PropertyFactory.get()
                   .register(false,
                             true,
                             true);
    Assert.assertFalse(Nalu.match(route,
                                  with));
  }

  @Test
  public void testMatch155Fail() {
    String route = "/app/person/:3/edit";
    String with = "/app/person/*/*";
    PropertyFactory.get()
                   .register(false,
                             true,
                             true);
    Assert.assertFalse(Nalu.match(route,
                                  with));
  }

  @Test
  public void testMatch156Fail() {
    String route = "/";
    String with = "/app/person/*/*";
    PropertyFactory.get()
                   .register(false,
                             true,
                             true);
    Assert.assertFalse(Nalu.match(route,
                                  with));
  }

  @Test
  public void testMatch157Ok() {
    String route = "/";
    String with = "/";
    PropertyFactory.get()
                   .register(false,
                             true,
                             true);
    Assert.assertTrue(Nalu.match(route,
                                 with));
  }

  @Test
  public void testMatch158Ok() {
    String route = "/:";
    String with = "/*";
    PropertyFactory.get()
                   .register(false,
                             true,
                             true);
    Assert.assertTrue(Nalu.match(route,
                                 with));
  }

}