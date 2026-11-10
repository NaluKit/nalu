package io.github.nalukit.nalu.plugin.core.web.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CleanUrlRouteUtilsTest {

  @Test
  void trimSlashes() {
    Assertions.assertEquals("", CleanUrlRouteUtils.trimSlashes(null));
    Assertions.assertEquals("", CleanUrlRouteUtils.trimSlashes("/"));
    Assertions.assertEquals("a/b", CleanUrlRouteUtils.trimSlashes("/a/b/"));
  }

  @Test
  void removePrefixAtSegmentBoundary() {
    Assertions.assertEquals("/shell/route", CleanUrlRouteUtils.removePrefix("app/shell/route", "app"));
    Assertions.assertEquals("/shell/route", CleanUrlRouteUtils.removePrefix("/app/shell/route", "/app/"));
    Assertions.assertEquals("", CleanUrlRouteUtils.removePrefix("app", "app"));
    // must not cut inside a segment
    Assertions.assertEquals("application/x", CleanUrlRouteUtils.removePrefix("application/x", "app"));
    // empty prefix: unchanged
    Assertions.assertEquals("/a/b", CleanUrlRouteUtils.removePrefix("/a/b", ""));
    Assertions.assertEquals("/a/b", CleanUrlRouteUtils.removePrefix("/a/b", null));
  }

  @Test
  void extractRoute() {
    Assertions.assertEquals("shell/route", CleanUrlRouteUtils.extractRoute("/app/shell/route", "/app"));
    Assertions.assertEquals("shell/route", CleanUrlRouteUtils.extractRoute("/app/shell/route/", "/app"));
    Assertions.assertEquals("shell/route", CleanUrlRouteUtils.extractRoute("/shell/route", ""));
    Assertions.assertEquals("", CleanUrlRouteUtils.extractRoute("/", ""));
    Assertions.assertEquals("", CleanUrlRouteUtils.extractRoute("/app", "/app"));
    Assertions.assertEquals("", CleanUrlRouteUtils.extractRoute("/app/index.html", "/app"));
    Assertions.assertEquals("", CleanUrlRouteUtils.extractRoute("/index.html", ""));
    Assertions.assertEquals("shell/route/42", CleanUrlRouteUtils.extractRoute("/app/shell/route/42", "app"));
  }

  @Test
  void buildPath() {
    Assertions.assertEquals("/app/shell/route", CleanUrlRouteUtils.buildPath("/app", "/shell/route", false));
    Assertions.assertEquals("/app/shell/route/", CleanUrlRouteUtils.buildPath("app", "shell/route", true));
    Assertions.assertEquals("/shell/route", CleanUrlRouteUtils.buildPath("", "/shell/route", false));
    Assertions.assertEquals("/shell/route", CleanUrlRouteUtils.buildPath(null, "shell/route", false));
    Assertions.assertEquals("/", CleanUrlRouteUtils.buildPath("", "", false));
    Assertions.assertEquals("/app", CleanUrlRouteUtils.buildPath("/app", "", false));
    Assertions.assertEquals("/app/", CleanUrlRouteUtils.buildPath("/app", "", true));
  }

}
