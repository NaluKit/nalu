package com.github.nalukit.nalu.client;

import com.github.nalukit.nalu.client.internal.PropertyFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NaluTest {

  @Test
  void getVersion() {
    Assertions.assertEquals("2.7.0-gwt-2.8.2",
                            Nalu.getVersion());
  }

  @Test
  void hasHistory01() {
    PropertyFactory.get()
                   .register("/startShell/startRoute01/startRoute02",
                             "",
                             true,
                             true,
                             true,
                             false,
                             false);
    Assertions.assertTrue(Nalu.hasHistory());
  }

  @Test
  void hasHistory02() {
    PropertyFactory.get()
                   .register("/startShell/startRoute01/startRoute02",
                             "",
                             false,
                             true,
                             true,
                             false,
                             false);
    Assertions.assertFalse(Nalu.hasHistory());
  }

  @Test
  void isUsingHash01() {
    PropertyFactory.get()
                   .register("/startShell/startRoute01/startRoute02",
                             "",
                             true,
                             true,
                             true,
                             false,
                             false);
    Assertions.assertTrue(Nalu.isUsingHash());
  }

  @Test
  void isUsingHash02() {
    PropertyFactory.get()
                   .register("/startShell/startRoute01/startRoute02",
                             "",
                             true,
                             false,
                             true,
                             false,
                             false);
    Assertions.assertFalse(Nalu.isUsingHash());
  }

  @Test
  void isUsingColonForParametersInUrl01() {
    PropertyFactory.get()
                   .register("/startShell/startRoute01/startRoute02",
                             "",
                             true,
                             true,
                             true,
                             false,
                             false);
    Assertions.assertTrue(Nalu.isUsingColonForParametersInUrl());
  }

  @Test
  void isUsingColonForParametersInUrl02() {
    PropertyFactory.get()
                   .register("/startShell/startRoute01/startRoute02",
                             "",
                             true,
                             true,
                             false,
                             false,
                             false);
    Assertions.assertFalse(Nalu.isUsingColonForParametersInUrl());
  }

}