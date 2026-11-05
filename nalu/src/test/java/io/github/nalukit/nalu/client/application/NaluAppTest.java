package io.github.nalukit.nalu.client.application;

import io.github.nalukit.nalu.client.internal.NaluConfigOverrides;
import io.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NaluAppTest {

  @AfterEach
  void reset() {
    NaluConfigOverrides.INSTANCE.reset();
  }

  @Test
  void withoutConfigurationAnnotationValuesAreUsed() {
    NaluApp.configure()
           .apply();
    NaluConfigOverrides o = NaluConfigOverrides.INSTANCE;
    Assertions.assertTrue(o.resolveUsingHash(true));
    Assertions.assertFalse(o.resolveUsingHash(false));
    Assertions.assertEquals("/annotation", o.resolveBasePath("/annotation"));
    Assertions.assertEquals("/start", o.resolveStartRoute("/start"));
    Assertions.assertTrue(o.resolveUsingBaseHref(true));
    Assertions.assertFalse(o.resolveUsingTrailingSlash(false));
  }

  @Test
  void configurationOverridesAnnotation() {
    NaluApp.configure()
           .routing(RoutingMode.PATH)
           .basePath("/app")
           .startRoute("/shell/home")
           .usingBaseHref(true)
           .usingTrailingSlash(true)
           .apply();
    NaluConfigOverrides o = NaluConfigOverrides.INSTANCE;
    Assertions.assertFalse(o.resolveUsingHash(true));
    Assertions.assertEquals("/app", o.resolveBasePath(""));
    Assertions.assertEquals("/shell/home", o.resolveStartRoute("/other"));
    Assertions.assertTrue(o.resolveUsingBaseHref(false));
    Assertions.assertTrue(o.resolveUsingTrailingSlash(false));
  }

  @Test
  void hashRoutingOverridesUsingHashFalse() {
    NaluApp.configure()
           .routing(RoutingMode.HASH)
           .apply();
    Assertions.assertTrue(NaluConfigOverrides.INSTANCE.resolveUsingHash(false));
  }

  @Test
  void applyReplacesPreviousConfiguration() {
    NaluApp.configure()
           .basePath("/first")
           .apply();
    NaluApp.configure()
           .apply();
    Assertions.assertEquals("/annotation", NaluConfigOverrides.INSTANCE.resolveBasePath("/annotation"));
  }

  @Test
  void startAppliesAndRunsApplication() {
    boolean[] ran = {false};
    NaluApp.configure()
           .routing(RoutingMode.PATH)
           .start(plugin -> ran[0] = true,
                  (IsNaluProcessorPlugin) null);
    Assertions.assertTrue(ran[0]);
    Assertions.assertFalse(NaluConfigOverrides.INSTANCE.resolveUsingHash(true));
  }

  @Test
  void nullValuesAreRejected() {
    Assertions.assertThrows(NullPointerException.class,
                            () -> NaluApp.configure()
                                         .routing(null));
    Assertions.assertThrows(NullPointerException.class,
                            () -> NaluApp.configure()
                                         .basePath(null));
    Assertions.assertThrows(NullPointerException.class,
                            () -> NaluApp.configure()
                                         .startRoute(null));
  }

}
