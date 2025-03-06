package com.github.nalukit.nalu.client;

import com.github.nalukit.nalu.client.internal.PropertyFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@DisplayName("Nalu Tests")
public class NaluTest {

  @Test
  void getVersion()
      throws IOException {
    // TODO Change this if you want to test against another version
    Assertions.assertEquals("2.20.4",
                            Nalu.getVersion());
  }

  @Test
  void hasHistory01() {
    PropertyFactory.INSTANCE
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
    PropertyFactory.INSTANCE
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
    PropertyFactory.INSTANCE
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
    PropertyFactory.INSTANCE
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
    PropertyFactory.INSTANCE
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
    PropertyFactory.INSTANCE
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