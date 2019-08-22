package com.github.nalukit.nalu.client;

import com.github.nalukit.nalu.client.internal.PropertyFactory;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class NaluTest {

  @Test
  public void getVersion() {
    assertThat(Nalu.getVersion(),
                      is("1.3.2-SNAPSHOT"));
  }

  @Test
  public void hasHistory01() {
    PropertyFactory.get()
                   .register("/startShell/startRoute01/startRoute02",
                             true,
                             true,
                             true,
                             false);
    assertThat(Nalu.hasHistory(), is(true));
  }

  @Test
  public void hasHistory02() {
    PropertyFactory.get()
                   .register("/startShell/startRoute01/startRoute02",
                             false,
                             true,
                             true,
                             false);
    assertThat(Nalu.hasHistory(), is(false));
  }

  @Test
  public void isUsingHash01() {
    PropertyFactory.get()
                   .register("/startShell/startRoute01/startRoute02",
                             true,
                             true,
                             true,
                             false);
    assertThat(Nalu.isUsingHash(), is(true));
  }

  @Test
  public void isUsingHash02() {
    PropertyFactory.get()
                   .register("/startShell/startRoute01/startRoute02",
                             true,
                             false,
                             true,
                             false);
    assertThat(Nalu.isUsingHash(), is(false));
  }

  @Test
  public void isUsingColonForParametersInUrl01() {
    PropertyFactory.get()
                   .register("/startShell/startRoute01/startRoute02",
                             true,
                             true,
                             true,
                             false);
    assertThat(Nalu.isUsingColonForParametersInUrl(), is(true));
  }

  @Test
  public void isUsingColonForParametersInUrl02() {
    PropertyFactory.get()
                   .register("/startShell/startRoute01/startRoute02",
                             true,
                             true,
                             false,
                             false);
    assertThat(Nalu.isUsingColonForParametersInUrl(), is(false));
  }

}