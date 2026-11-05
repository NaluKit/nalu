package io.github.nalukit.nalu.plugin.core.web.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PathRouteUtilsTest {

  @Test
  void trimSlashes() {
    Assertions.assertEquals("", PathRouteUtils.trimSlashes(null));
    Assertions.assertEquals("", PathRouteUtils.trimSlashes("/"));
    Assertions.assertEquals("a/b", PathRouteUtils.trimSlashes("/a/b/"));
  }

  @Test
  void removePrefixAtSegmentBoundary() {
    Assertions.assertEquals("/shell/route", PathRouteUtils.removePrefix("app/shell/route", "app"));
    Assertions.assertEquals("/shell/route", PathRouteUtils.removePrefix("/app/shell/route", "/app/"));
    Assertions.assertEquals("", PathRouteUtils.removePrefix("app", "app"));
    // must not cut inside a segment
    Assertions.assertEquals("application/x", PathRouteUtils.removePrefix("application/x", "app"));
    // empty prefix: unchanged
    Assertions.assertEquals("/a/b", PathRouteUtils.removePrefix("/a/b", ""));
    Assertions.assertEquals("/a/b", PathRouteUtils.removePrefix("/a/b", null));
  }

  @Test
  void extractRoute() {
    Assertions.assertEquals("shell/route", PathRouteUtils.extractRoute("/app/shell/route", "/app"));
    Assertions.assertEquals("shell/route", PathRouteUtils.extractRoute("/app/shell/route/", "/app"));
    Assertions.assertEquals("shell/route", PathRouteUtils.extractRoute("/shell/route", ""));
    Assertions.assertEquals("", PathRouteUtils.extractRoute("/", ""));
    Assertions.assertEquals("", PathRouteUtils.extractRoute("/app", "/app"));
    Assertions.assertEquals("", PathRouteUtils.extractRoute("/app/index.html", "/app"));
    Assertions.assertEquals("", PathRouteUtils.extractRoute("/index.html", ""));
    Assertions.assertEquals("shell/route/42", PathRouteUtils.extractRoute("/app/shell/route/42", "app"));
  }

  @Test
  void buildPath() {
    Assertions.assertEquals("/app/shell/route", PathRouteUtils.buildPath("/app", "/shell/route", false));
    Assertions.assertEquals("/app/shell/route/", PathRouteUtils.buildPath("app", "shell/route", true));
    Assertions.assertEquals("/shell/route", PathRouteUtils.buildPath("", "/shell/route", false));
    Assertions.assertEquals("/shell/route", PathRouteUtils.buildPath(null, "shell/route", false));
    Assertions.assertEquals("/", PathRouteUtils.buildPath("", "", false));
    Assertions.assertEquals("/app", PathRouteUtils.buildPath("/app", "", false));
    Assertions.assertEquals("/app/", PathRouteUtils.buildPath("/app", "", true));
  }

}
