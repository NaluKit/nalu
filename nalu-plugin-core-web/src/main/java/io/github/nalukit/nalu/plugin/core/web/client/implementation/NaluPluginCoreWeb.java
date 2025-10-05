/*
 * Copyright (c) 2020 - Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 */

package io.github.nalukit.nalu.plugin.core.web.client.implementation;

import io.github.nalukit.nalu.client.internal.PropertyFactory;
import io.github.nalukit.nalu.client.internal.route.ShellConfig;
import io.github.nalukit.nalu.client.internal.route.ShellConfiguration;
import io.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin.RouteChangeHandler;
import io.github.nalukit.nalu.client.util.NaluUtils;
import io.github.nalukit.nalu.plugin.core.web.client.IsNaluCorePlugin;
import io.github.nalukit.nalu.plugin.core.web.client.model.NaluStartModel;
import elemental2.dom.Document;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLBaseElement;
import elemental2.dom.HTMLHeadElement;
import elemental2.dom.Location;
import elemental2.dom.Node;
import elemental2.dom.PopStateEvent;
import elemental2.dom.URL;
import jsinterop.base.Js;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class NaluPluginCoreWeb
    implements IsNaluCorePlugin {

  public static boolean isSuperDevMode() {
    return "on".equals(System.getProperty("superdevmode",
                                          "off"));
  }

  private static String getHashValue(String hash) {
    if (!Objects.isNull(hash)) {
      if (hash.startsWith("#")) {
        if (hash.length() > 1) {
          return hash.substring(1);
        } else {
          return "";
        }
      }
    }
    return null;
  }

  /**
   * Removes the contextPath from a given path if it starts with it.
   * If the contextPath is null or empty, the path is returned unchanged.
   *
   * @param path the path from which the contextPath should be removed
   * @param contextPath the contextPath to be removed
   * @return the path without the contextPath if it was present, otherwise the original path
   */
  private String removeContextPath(String path, String contextPath) {
    if (Objects.isNull(contextPath) || contextPath.isEmpty()) {
      return path;
    }
    if (path.startsWith(contextPath)) {
      return path.substring(contextPath.length());
    }
    return path;
  }

  /**
   * Ensures or updates the <base> element (href only).
   * @param href the href to set
   */
  private static final void ensureOrUpdateBase(String href) {
    ensureOrUpdateBase(href, null);
  }

  /**
   * Ensures or updates the <base> element (href + optional target).
   * @param href the href to set
   * @param target the target to set (optional, can be null or empty)
   */
  private static final void ensureOrUpdateBase(String href, String target) {

    Document doc = DomGlobal.document;
    HTMLBaseElement base =
        (HTMLBaseElement) doc.querySelector("base");

    if (base == null) {
      base = (HTMLBaseElement) doc.createElement("base");
      HTMLHeadElement head = (HTMLHeadElement) doc.querySelector("head");
      // Insert as first child of <head>, so all following <link>/<script> will benefit from it.
      Node first = head.firstChild;
      if (first != null) {
          head.insertBefore(base, first);
      } else {
          head.appendChild(base);
      }
    }

    base.href = href;

    if (target != null && !target.isEmpty()) {
      base.target = target; // "_top", "_self", "contentFrame"
    }
  }

  @Override
  @SuppressWarnings("StringSplitter")
  public void getContextPath(ShellConfiguration shellConfiguration) {
    if (PropertyFactory.INSTANCE.isUsingHash()) {
      return;
    }
    Location location = Js.uncheckedCast(DomGlobal.location);
    String pathName = location.pathname;

    if (PropertyFactory.INSTANCE.isUsingBaseHref()) {
        ensureOrUpdateBase(location.pathname);
    }

    if (pathName.startsWith("/")) {
      pathName = pathName.substring(1);
    }

    StringBuilder context = new StringBuilder();

    for (String partOfContext : pathName.split("/")) {
      Optional<String> optional = shellConfiguration.getShells()
                                                    .stream()
                                                    .map(ShellConfig::getRoute)
                                                    .filter(f -> f.equals("/" + partOfContext))
                                                    .findAny();
      if (optional.isPresent()) {
        break;
      } else {
        if (context.length() > 0) {
          context.append("/");
        }
        context.append(partOfContext);
      }
    }

    String calculatedContextPath = context.toString();
    PropertyFactory.INSTANCE.setContextPath(calculatedContextPath);
  }

  @Override
  public NaluStartModel getNaluStartModel() {
    Location            location        = Js.uncheckedCast(DomGlobal.location);
    Map<String, String> queryParameters = new HashMap<>();

    URL url = new URL(location.href);
    url.searchParams.forEach((value, key) -> queryParameters.put(key, value));

    String startRoute;
    String contextPath = PropertyFactory.INSTANCE.getContextPath();
    if (PropertyFactory.INSTANCE.isUsingHash()) {
      startRoute = getHashValue(location.hash);
    } else {
      startRoute = queryParameters.get("uri");
      if (!Objects.isNull(startRoute)) {
        startRoute = NaluUtils.removeLeading("/", startRoute);
        startRoute = removeContextPath(startRoute, contextPath);
        startRoute = NaluUtils.removeLeading("/", startRoute);
        if (startRoute.endsWith("/")) {
          startRoute = startRoute.substring(0,
                                            startRoute.length() - 1);
        }
      } else {
        startRoute = "";
      }
    }
    return new NaluStartModel(startRoute,
                              queryParameters);
  }

  @Override
  public void addPopStateHandler(RouteChangeHandler handler,
                                 String contextPath) {
    DomGlobal.window.onpopstate = e -> {
      String newUrl;
      if (PropertyFactory.INSTANCE.isUsingHash()) {
        Location location = Js.uncheckedCast(DomGlobal.location);
        newUrl = location.hash;
      } else {
        PopStateEvent event = (PopStateEvent) e;
        newUrl = (String) event.state;
        if (Objects.isNull(newUrl) ||
            newUrl.trim()
                  .isEmpty()) {
          return null;
        }
      }
      if (newUrl.length() > 1 && newUrl.startsWith("/")) {
        newUrl = newUrl.substring(1);
      }
      newUrl = removeContextPath(newUrl, contextPath);
      this.handleChange(handler,
                        newUrl);
      return null;
    };
  }

  private void handleChange(RouteChangeHandler handler,
                            String newUrl) {
    newUrl = NaluUtils.removeLeading("#", newUrl);
    if (newUrl.trim()
              .isEmpty()) {
      // In case we have an empty newUrl, we have moved back to the start page ==> use startRoute!
      this.route(PropertyFactory.INSTANCE.getStartRoute(),
                 !PropertyFactory.INSTANCE.isStayOnSide(),
                 false,
                 handler);
    } else {
      handler.onRouteChange(newUrl);
    }
  }

  @Override
  public void route(String newRoute,
                    boolean replace,
                    boolean stealthMode,
                    RouteChangeHandler handler) {
    String newRouteToken;
    String contextPath = PropertyFactory.INSTANCE.getContextPath();
    if (PropertyFactory.INSTANCE.isUsingHash()) {
      newRouteToken = newRoute.startsWith("#") ?
                      newRoute :
                      "#" + newRoute;
    } else {
      newRouteToken = "/";
      if (!Objects.isNull(contextPath)
              && !contextPath.isEmpty()) {
        newRouteToken = newRouteToken + contextPath + "/";
      }
      newRouteToken = newRouteToken + newRoute;
    }
    if (PropertyFactory.INSTANCE.hasHistory()) {
      if (!stealthMode) {
        if (replace) {
          DomGlobal.window.history.replaceState(newRouteToken,
                                                DomGlobal.document.title,
                                                newRouteToken);
        } else {
          DomGlobal.window.history.pushState(newRouteToken,
                                             DomGlobal.document.title,
                                             newRouteToken);
        }
      }
    } else {
      DomGlobal.window.history.pushState("",
                                         DomGlobal.document.title,
                                         DomGlobal.window.location.pathname);
    }
  }

  @Override
  public void addOnHashChangeHandler(RouteChangeHandler handler) {
    DomGlobal.window.onhashchange = e -> {
      String   newUrl;
      Location location = Js.uncheckedCast(DomGlobal.location);
      newUrl = location.hash;
      this.handleChange(handler,
                        newUrl);
      return null;
    };
  }

}
