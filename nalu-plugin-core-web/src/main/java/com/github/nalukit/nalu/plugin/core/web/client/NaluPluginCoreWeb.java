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

package com.github.nalukit.nalu.plugin.core.web.client;

import com.github.nalukit.nalu.client.internal.PropertyFactory;
import com.github.nalukit.nalu.client.internal.route.ShellConfig;
import com.github.nalukit.nalu.client.internal.route.ShellConfiguration;
import com.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin.RouteChangeHandler;
import com.github.nalukit.nalu.plugin.core.web.client.model.NaluStartModel;
import elemental2.dom.DomGlobal;
import elemental2.dom.Location;
import elemental2.dom.PopStateEvent;
import jsinterop.base.Js;

import javax.naming.spi.DirObjectFactory;
import java.util.*;

public class NaluPluginCoreWeb {

  public static boolean isSuperDevMode() {
    return "on".equals(System.getProperty("superdevmode",
                                          "off"));
  }

  @SuppressWarnings("StringSplitter")
  public static void getContextPath(ShellConfiguration shellConfiguration) {
    if (PropertyFactory.get()
                       .isUsingHash()) {
      return;
    }
    Location location = Js.uncheckedCast(DomGlobal.location);
    String   pathName = location.getPathname();
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
        PropertyFactory.get()
                       .setContextPath(context.toString());
      } else {
        PropertyFactory.get()
                       .setContextPath("");
      }
    }
    PropertyFactory.get()
                   .setContextPath("");
  }

  @SuppressWarnings("StringSplitter")
  public static NaluStartModel getNaluStartModel() {
    Js.debugger();
    Location            location        = Js.uncheckedCast(DomGlobal.location);
    Map<String, String> queryParameters = new HashMap<>();
    String              search          = location.getSearch();
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
    if (PropertyFactory.get()
                       .isUsingHash()) {
      startRoute = getHashValue(location.getHash());
    } else {
      startRoute = queryParameters.get("uri");
      if (!Objects.isNull(startRoute)) {
        if (startRoute.startsWith("/")) {
          if (startRoute.length() > 1) {
            startRoute = startRoute.substring(1);
          }
        }
        if (startRoute.startsWith(PropertyFactory.get()
                                                 .getContextPath())) {
          startRoute = startRoute.substring(PropertyFactory.get()
                                                           .getContextPath()
                                                           .length());
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

    // in case we need to remove the parameter, update histry ...
    if (PropertyFactory.get().isRemoveUrlParameterAtStart()) {
      String href = location.href;
      String newUrl = "";
      if (href.contains("?")) {
        newUrl = href.substring(0, href.indexOf("?"));
        if (startRoute.length() > 0) {
          newUrl = newUrl + "#" + startRoute;
          DomGlobal.window.history.replaceState(newUrl, DomGlobal.document.title, newUrl);
        }
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

  public static void addPopStateHandler(RouteChangeHandler handler,
                                        String contextPath) {
    DomGlobal.window.onpopstate = e -> {
      String newUrl;
      if (PropertyFactory.get()
                         .isUsingHash()) {
        Location location = Js.uncheckedCast(DomGlobal.location);
        newUrl = location.getHash();
      } else {
        PopStateEvent event = (PopStateEvent) e;
        newUrl = (String) event.state;
        if (Objects.isNull(newUrl) ||
            newUrl.trim()
                  .length() == 0) {
          newUrl = PropertyFactory.get()
                                  .getStartRoute();
        }
      }
      // remove leading '/'
      if (newUrl.length() > 1) {
        if (newUrl.startsWith("/")) {
          newUrl = newUrl.substring(1);
        }
      }
      // remove contextPath
      if (!Objects.isNull(contextPath)) {
        if (newUrl.length() > contextPath.length()) {
          newUrl = newUrl.substring(contextPath.length());
        }
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
    if (newUrl.trim()
              .length() == 0) {
      // In case we have an empty newUrl, we have moved back to the start page ==> use startRoute!
      NaluPluginCoreWeb.route(PropertyFactory.get()
                                             .getStartRoute(),
                              !PropertyFactory.get()
                                              .isStayOnSide(),
                              handler);
    } else {
      handler.onRouteChange(newUrl);
    }
  }

  public static void route(String newRoute,
                           boolean replace,
                           RouteChangeHandler handler) {
    String newRouteToken;
    if (PropertyFactory.get()
                       .isUsingHash()) {
      newRouteToken = newRoute.startsWith("#") ? newRoute : "#" + newRoute;
    } else {
      newRouteToken = "/";
      if (PropertyFactory.get()
                         .getContextPath()
                         .length() > 0) {
        newRouteToken = newRouteToken +
                        PropertyFactory.get()
                                       .getContextPath() +
                        "/";
      }
      newRouteToken = newRouteToken + newRoute;
    }
    if (PropertyFactory.get()
                       .hasHistory()) {
      if (replace) {
        DomGlobal.window.history.replaceState(newRouteToken,
                                              null,
                                              newRouteToken);
      } else {
        DomGlobal.window.history.pushState(newRouteToken,
                                           null,
                                           newRouteToken);
      }
    }
  }

  public static void addOnHashChangeHandler(RouteChangeHandler handler) {
    DomGlobal.window.onhashchange = e -> {
      String   newUrl;
      Location location = Js.uncheckedCast(DomGlobal.location);
      newUrl = location.getHash();
      NaluPluginCoreWeb.handleChange(handler,
                                     newUrl);
      return null;
    };
  }

}
