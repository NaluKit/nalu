package io.github.nalukit.nalu.client;

import io.github.nalukit.nalu.client.internal.NaluConfig;
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
    Assertions.assertEquals("4.0.1",
                            Nalu.getVersion());
  }

  @Test
  void isUsingHistory01() {
    NaluConfig.INSTANCE
                   .register("/startShell/startRoute01/startRoute02",
                             "",
                             false,
                             true,
                             true,
                             true,
                             false,
                             false);
    Assertions.assertTrue(Nalu.isUsingHistory());
  }

  @Test
  void isUsingHistory02() {
    NaluConfig.INSTANCE
                   .register("/startShell/startRoute01/startRoute02",
                             "",
                             false,
                             false,
                             true,
                             true,
                             false,
                             false);
    Assertions.assertFalse(Nalu.isUsingHistory());
  }

  @Test
  void isUsingHash01() {
    NaluConfig.INSTANCE
                   .register("/startShell/startRoute01/startRoute02",
                             "",
                             false,
                             true,
                             true,
                             true,
                             false,
                             false);
    Assertions.assertTrue(Nalu.isUsingHash());
  }

  @Test
  void isUsingHash02() {
    NaluConfig.INSTANCE
                   .register("/startShell/startRoute01/startRoute02",
                             "",
                             false,
                             true,
                             false,
                             true,
                             false,
                             false);
    Assertions.assertFalse(Nalu.isUsingHash());
  }

  @Test
  void isUsingUnderscoreForParametersInUrl01() {
    NaluConfig.INSTANCE
                   .register("/startShell/startRoute01/startRoute02",
                             "",
                             false,
                             true,
                             true,
                             true,
                             false,
                             false);
    Assertions.assertTrue(Nalu.isUsingUnderscoreForParametersInUrl());
  }

  @Test
  void isUsingUnderscoreForParametersInUrl02() {
    NaluConfig.INSTANCE
                   .register("/startShell/startRoute01/startRoute02",
                             "",
                             false,
                             true,
                             true,
                             false,
                             false,
                             false);
    Assertions.assertFalse(Nalu.isUsingUnderscoreForParametersInUrl());
  }

}