/*
 * Copyright (c) 2019 - Frank Hossfeld
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

package com.github.nalukit.nalu.plugin.core.web.client;

import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.route.ShellConfig;
import com.github.nalukit.nalu.client.internal.route.ShellConfiguration;
import com.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin.RouteChangeHandler;
import com.github.nalukit.nalu.plugin.core.web.client.model.NaluStartModel;
import elemental2.dom.DomGlobal;
import elemental2.dom.HashChangeEvent;
import elemental2.dom.Location;
import elemental2.dom.PopStateEvent;
import jsinterop.base.Js;

import java.util.*;

public class NaluPluginCoreWeb {

  public static boolean isSuperDevMode() {
    return "on".equals(System.getProperty("superdevmode",
                                          "off"));
  }

  /**
   * Log's the new URL on the browser's console
   *
   * @param newUrl new url to log
   */
  public static void logNewUrl(String newUrl) {
    StringBuilder sb = new StringBuilder();
    sb.append("Router: onhashchange: new url ->>")
      .append(newUrl)
      .append("<<");
    ClientLogger.get()
                .logSimple(sb.toString(),
                           0);
  }

  public static String getContextPath(boolean usingHash,
                                      ShellConfiguration shellConfiguration) {
    if (usingHash) {
      return "";
    }
    Location location = Js.uncheckedCast(DomGlobal.location);
    String pathName = location.getPathname();
    if (pathName.startsWith("/") && pathName.length() > 1) {
      pathName = pathName.substring(1);
    }
    if (pathName.contains(".")) {
      if (pathName.contains("/")) {
        pathName = pathName.substring(0,
                                      pathName.lastIndexOf("/"));
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
        return context.toString();
      } else {
        return "";
      }
    }
    return "";
  }

  public static NaluStartModel getNaluStartModel(String contextPath,
                                                 boolean usingHash) {
    Js.debugger();
    Location location = Js.uncheckedCast(DomGlobal.location);
    Map<String, String> queryParameters = new HashMap<>();
    String search = location.getSearch();
    if (!Objects.isNull(search)) {
      if (search.startsWith("?")) {
        search = search.substring(1);
      }
      Arrays.stream(search.split("&"))
            .forEach(s -> {
              String[] split = s.split("=");
              if (split.length == 1) {
                queryParameters.put(split[0],
                                    "");
              } else if (split.length == 2) {
                queryParameters.put(split[0],
                                    split[1]);

              }
            });
    }
    String startRoute;
    if (usingHash) {
      startRoute = getHashValue(location.getHash());
    } else {
      startRoute = queryParameters.get("uri");
      if (!Objects.isNull(startRoute)) {
        if (startRoute.startsWith("/")) {
          if (startRoute.length() > 1) {
            startRoute = startRoute.substring(1);
          }
        }
        if (startRoute.startsWith(contextPath)) {
          startRoute = startRoute.substring(contextPath.length());
        }
        if (startRoute.startsWith("/")) {
          startRoute = startRoute.substring(1);
        }
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

  public static void route(String contextPath,
                           String newRoute,
                           boolean replace,
                           boolean usingHash) {
    String value;
    if (usingHash) {
      value = "#" + newRoute;
    } else {
      value = "/";
      if (contextPath.length() > 0) {
        value = value + contextPath + "/";
      }
      value = value + newRoute;
    }
    if (replace) {
      DomGlobal.window.history.replaceState(value,
                                            null,
                                            value);
    } else {
      DomGlobal.window.history.pushState(value,
                                         null,
                                         value);
    }
  }

  /**
   * checks weather the current browser is IE or not.
   * <p>
   * IE 10
   * ua = 'Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)';
   * <p>
   * IE 11
   * ua = 'Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko';
   * <p>
   * Edge 12 (Spartan)
   * ua = 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36 Edge/12.0';
   * <p>
   * Edge 13
   * ua = 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586';
   *
   * @return true: is IE
   */
  public static boolean detectIE11() {
    String ua = DomGlobal.window.navigator.userAgent;
    if (ua.indexOf("MSIE ") > 0) {
      return true;
    }
    return ua.indexOf("Trident/") > 0;
  }

  public static void addPopStateHandler(RouteChangeHandler handler) {
    DomGlobal.window.onpopstate = e -> {
      String newUrl;
      if (NaluPluginCoreWeb.detectIE11()) {
        Location location = Js.uncheckedCast(DomGlobal.location);
        newUrl = location.getHash();
      } else {
        // cast event ...
        PopStateEvent event = (PopStateEvent) e;
        newUrl = (String) event.state;
      }
      NaluPluginCoreWeb.handleChange(handler,
                                     newUrl);
      return null;
    };
  }

  public static void addOnHashChangeHandler(RouteChangeHandler handler) {
    DomGlobal.window.onhashchange = e -> {
      String newUrl;
      if (NaluPluginCoreWeb.detectIE11()) {
        Location location = Js.uncheckedCast(DomGlobal.location);
        newUrl = location.getHash();
      } else {
        // cast event ...
        HashChangeEvent event = (HashChangeEvent) e;
        newUrl = event.newURL;
      }
      NaluPluginCoreWeb.handleChange(handler,
                                     newUrl);
      return null;
    };
  }

  private static void handleChange(RouteChangeHandler handler,
                                   String newUrl) {
    if (newUrl.startsWith("#")) {
      newUrl = newUrl.substring(1);
    }
    NaluPluginCoreWeb.logNewUrl(newUrl);
    handler.onRouteChange(newUrl);
  }

}
