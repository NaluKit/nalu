package com.github.nalukit.nalu.plugin.core.web.client;

import com.github.nalukit.nalu.client.internal.route.ShellConfig;
import com.github.nalukit.nalu.client.internal.route.ShellConfiguration;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class NaluPluginCoreWebTest {

  @Test
  public void isSuperDevMode() {
    System.setProperty("superdevmode",
                       "on");
    Assert.assertThat(NaluPluginCoreWeb.isSuperDevMode(),
                      is(true));
  }

  @Test
  public void isNotSuperDevMode() {
    System.setProperty("superdevmode",
                       "off");
    Assert.assertThat(NaluPluginCoreWeb.isSuperDevMode(),
                      is(false));
  }

}