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
                             true);
    Assert.assertTrue(Nalu.match(route,
                                 with));
  }

}