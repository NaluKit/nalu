package com.github.nalukit.nalu.plugin.core.web.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NaluPluginCoreWebTest {
  
  @Test
  void isSuperDevMode() {
    System.setProperty("superdevmode",
                       "on");
    Assertions.assertTrue(NaluPluginCoreWeb.isSuperDevMode());
  }
  
  @Test
  void isNotSuperDevMode() {
    System.setProperty("superdevmode",
                       "off");
    Assertions.assertFalse(NaluPluginCoreWeb.isSuperDevMode());
  }
  
}