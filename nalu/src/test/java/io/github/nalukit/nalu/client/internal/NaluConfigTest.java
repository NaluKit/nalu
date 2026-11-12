package io.github.nalukit.nalu.client.internal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NaluConfigTest {

  @AfterEach
  void reset() {
    NaluConfig.INSTANCE.setBasePath("");
  }

  @Test
  void normalizeNullAndEmpty() {
    Assertions.assertEquals("", NaluConfig.normalizeBasePath(null));
    Assertions.assertEquals("", NaluConfig.normalizeBasePath(""));
    Assertions.assertEquals("", NaluConfig.normalizeBasePath("   "));
    Assertions.assertEquals("", NaluConfig.normalizeBasePath("/"));
    Assertions.assertEquals("", NaluConfig.normalizeBasePath("//"));
  }

  @Test
  void normalizeAddsLeadingSlash() {
    Assertions.assertEquals("/app", NaluConfig.normalizeBasePath("app"));
  }

  @Test
  void normalizeRemovesTrailingSlash() {
    Assertions.assertEquals("/app", NaluConfig.normalizeBasePath("/app/"));
    Assertions.assertEquals("/app", NaluConfig.normalizeBasePath("app/"));
  }

  @Test
  void normalizeNestedAndDoubleSlashes() {
    Assertions.assertEquals("/a/b", NaluConfig.normalizeBasePath("/a/b/"));
    Assertions.assertEquals("/a/b", NaluConfig.normalizeBasePath("//a//b//"));
  }

  @Test
  void setBasePathNormalizes() {
    NaluConfig.INSTANCE.setBasePath("app/");
    Assertions.assertEquals("/app", NaluConfig.INSTANCE.getBasePath());
  }

  @Test
  void defaultBasePathIsEmpty() {
    Assertions.assertEquals("", NaluConfig.INSTANCE.getBasePath());
  }

}
