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

import elemental2.dom.Document;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.HTMLBaseElement;
import elemental2.dom.HTMLHeadElement;
import elemental2.dom.Location;
import elemental2.dom.MouseEvent;
import elemental2.dom.Node;
import elemental2.dom.PopStateEvent;
import elemental2.dom.URL;
import io.github.nalukit.nalu.client.internal.NaluConfig;
import io.github.nalukit.nalu.client.internal.route.ShellConfig;
import io.github.nalukit.nalu.client.internal.route.ShellConfiguration;
import io.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin;
import io.github.nalukit.nalu.client.util.NaluUtils;
import io.github.nalukit.nalu.plugin.core.web.client.IsNaluCorePlugin;
import io.github.nalukit.nalu.plugin.core.web.client.NaluCoreConstants;
import io.github.nalukit.nalu.plugin.core.web.client.CleanUrlRouteUtils;
import io.github.nalukit.nalu.plugin.core.web.client.model.NaluStartModel;
import jsinterop.base.Js;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractCoreWebPlugin
    implements IsNaluCorePlugin {

  protected Map<String, String> queryParameters = new HashMap<>();

  private boolean linkInterceptorInstalled = false;

  protected static String getHashValue(String hash) {
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

  @Override
  public void addOnHashChangeHandler(IsNaluProcessorPlugin.RouteChangeHandler handler) {
    DomGlobal.window.onhashchange = ignore -> {
      String   newUrl;
      Location location = Js.uncheckedCast(DomGlobal.location);
      newUrl = location.hash;
      this.handleChange(handler,
                        newUrl);
      return null;
    };
  }

  @Override
  public void addPopStateHandler(IsNaluProcessorPlugin.RouteChangeHandler handler,
                                 String contextPath) {
    if (!NaluConfig.INSTANCE.isUsingHash()) {
      this.addLinkInterceptor(handler);
    }
    DomGlobal.window.onpopstate = e -> {
      String newUrl;
      if (NaluConfig.INSTANCE.isUsingHash()) {
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
      newUrl = removeContextPath(newUrl,
                                 NaluConfig.INSTANCE.getContextPath());
      this.handleChange(handler,
                        newUrl);
      return null;
    };
  }

  @Override
  @SuppressWarnings("StringSplitter")
  public void getContextPath(ShellConfiguration shellConfiguration) {
    if (NaluConfig.INSTANCE.isUsingHash()) {
      return;
    }
    // an explicitly configured base path always wins over the guessed context path
    String basePath = NaluConfig.INSTANCE.getBasePath();
    if (!Objects.isNull(basePath) && !basePath.isEmpty()) {
      if (NaluConfig.INSTANCE.isUsingBaseHref()) {
        this.ensureOrUpdateBase(basePath + "/");
      }
      NaluConfig.INSTANCE.setContextPath(CleanUrlRouteUtils.trimSlashes(basePath));
      return;
    }
    Location location = Js.uncheckedCast(DomGlobal.location);
    String   pathName = location.pathname;
    if (NaluConfig.INSTANCE.isUsingBaseHref()) {
      this.ensureOrUpdateBase(location.pathname);
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
        // ignore the landing page ...
        if (!partOfContext.endsWith(".html")) {
          if (context.length() > 0) {
            context.append("/");
          }
          context.append(partOfContext);
        }
      }
    }
    String calculatedContextPath = context.toString();
    NaluConfig.INSTANCE.setContextPath(calculatedContextPath);
  }

  @Override
  public NaluStartModel getNaluStartModel() {
    Location            location        = Js.uncheckedCast(DomGlobal.location);
    URL url = new URL(location.href);
    url.searchParams.forEach((value, key) -> queryParameters.put(key,
                                                                 value));
    String startRoute;
    String contextPath = NaluConfig.INSTANCE.getContextPath();
    if (NaluConfig.INSTANCE.isUsingHash()) {
      startRoute = getHashValue(location.hash);
    } else {
      startRoute = queryParameters.get(NaluCoreConstants.PARAMETER_URI);
      if (!Objects.isNull(startRoute)) {
        startRoute = NaluUtils.INSTANCE.removeLeading("/",
                                             startRoute);
        startRoute = removeContextPath(startRoute,
                                       contextPath);
        startRoute = NaluUtils.INSTANCE.removeLeading("/",
                                             startRoute);
        if (startRoute.endsWith("/")) {
          startRoute = startRoute.substring(0,
                                            startRoute.length() - 1);
        }
      } else {
        // no '?uri=' redirect: read the route directly from the path (server falls back to index.html)
        startRoute = CleanUrlRouteUtils.extractRoute(location.pathname,
                                                 contextPath);
      }
    }
    return new NaluStartModel(startRoute,
                              queryParameters);
  }

  protected void handleChange(IsNaluProcessorPlugin.RouteChangeHandler handler,
                              String newUrl) {
    newUrl = NaluUtils.INSTANCE.removeLeading("#",
                                     newUrl);
    if (newUrl.trim()
              .isEmpty()) {
      // In case we have an empty newUrl, we have moved back to the start page ==> use startRoute!
      this.route(NaluConfig.INSTANCE.getStartRoute(),
                 !NaluConfig.INSTANCE.isStayOnSide(),
                 false,
                 handler);
    } else {
      handler.onRouteChange(newUrl);
    }
  }

  /**
   * Ensures or updates the &lt;base&gt; element (href only).
   *
   * @param href the href to set
   */
  protected void ensureOrUpdateBase(String href) {
    this.ensureOrUpdateBase(href,
                            null);
  }

  /**
   * Ensures or updates the &lt;base&gt; element (href + optional target).
   *
   * @param href   the href to set
   * @param target the target to set (optional, can be null or empty)
   */
  protected void ensureOrUpdateBase(String href,
                                    String target) {

    Document doc = DomGlobal.document;
    HTMLBaseElement base = (HTMLBaseElement) doc.querySelector("base");
    if (base == null) {
      base = (HTMLBaseElement) doc.createElement("base");
      HTMLHeadElement head = (HTMLHeadElement) doc.querySelector("head");
      // Insert as first child of <head>, so all following <link>/<script> will benefit from it.
      Node first = head.firstChild;
      if (first != null) {
        head.insertBefore(base,
                          first);
      } else {
        head.appendChild(base);
      }
    }
    base.href = href;
    if (target != null && !target.isEmpty()) {
      base.target = target; // "_top", "_self", "contentFrame"
    }
  }

  /**
   * Removes the contextPath from a given path if it starts with it.
   * If the contextPath is null or empty, the path is returned unchanged.
   *
   * @param path        the path from which the contextPath should be removed
   * @param contextPath the contextPath to be removed
   * @return the path without the contextPath if it was present, otherwise the original path
   */
  private String removeContextPath(String path,
                                   String contextPath) {
    return CleanUrlRouteUtils.removePrefix(path,
                                       contextPath);
  }

  /**
   * Intercepts clicks on elements (or children of elements) marked with the attribute
   * <code>data-nalu-link</code> and routes inside the application instead of reloading the page.
   * <br>
   * <code>&lt;a data-nalu-link href="/app/shell/route"&gt;</code> uses the path of the href,
   * <code>&lt;a data-nalu-link="/shell/route" href="/app/shell/route"&gt;</code> uses the value of the attribute.
   * Clicks with modifier keys, other mouse buttons or a target other than _self are left to the browser.
   *
   * @param handler the route change handler
   */
  protected void addLinkInterceptor(IsNaluProcessorPlugin.RouteChangeHandler handler) {
    if (this.linkInterceptorInstalled) {
      return;
    }
    this.linkInterceptorInstalled = true;
    DomGlobal.document.addEventListener("click",
                                        evt -> {
                                          MouseEvent mouseEvent = Js.uncheckedCast(evt);
                                          if (mouseEvent.defaultPrevented ||
                                              mouseEvent.button != 0 ||
                                              mouseEvent.ctrlKey ||
                                              mouseEvent.metaKey ||
                                              mouseEvent.shiftKey ||
                                              mouseEvent.altKey) {
                                            return;
                                          }
                                          Element source = Js.uncheckedCast(evt.target);
                                          if (Objects.isNull(source)) {
                                            return;
                                          }
                                          Element link = source.closest("[data-nalu-link]");
                                          if (Objects.isNull(link)) {
                                            return;
                                          }
                                          String linkTarget = link.getAttribute("target");
                                          if (!Objects.isNull(linkTarget) &&
                                              !linkTarget.isEmpty() &&
                                              !"_self".equals(linkTarget)) {
                                            return;
                                          }
                                          String route = link.getAttribute("data-nalu-link");
                                          if (Objects.isNull(route) || route.trim()
                                                                            .isEmpty()) {
                                            String href = link.getAttribute("href");
                                            if (Objects.isNull(href)) {
                                              return;
                                            }
                                            Location location = Js.uncheckedCast(DomGlobal.location);
                                            URL      url      = new URL(href,
                                                                        location.href);
                                            if (!url.origin.equals(location.origin)) {
                                              return;
                                            }
                                            route = CleanUrlRouteUtils.extractRoute(url.pathname,
                                                                                NaluConfig.INSTANCE.getContextPath());
                                          } else {
                                            route = CleanUrlRouteUtils.trimSlashes(route);
                                          }
                                          evt.preventDefault();
                                          String newRoute = "/" + route;
                                          this.route(newRoute,
                                                     false,
                                                     false,
                                                     handler);
                                          handler.onRouteChange(newRoute);
                                        });
  }

}
