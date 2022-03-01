package com.github.nalukit.nalu.client;

import com.github.nalukit.nalu.client.internal.PropertyFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

public class NaluTest {

  @Test
  void getVersion()
      throws IOException {
    final Properties properties = new Properties();
    properties.load(this.getClass().getClassLoader().getResourceAsStream("version.properties"));
    Assertions.assertEquals(properties.getProperty("artifact.version"),
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