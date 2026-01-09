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

import elemental2.dom.DomGlobal;
import io.github.nalukit.nalu.client.internal.NaluConfig;
import io.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin;
import io.github.nalukit.nalu.plugin.core.web.client.IsNaluCorePlugin;

import java.util.Objects;

public class DefaultCoreWebPlugin
    extends AbstractCoreWebPlugin
    implements IsNaluCorePlugin {

  @Override
  public void route(String newRoute,
                    boolean replace,
                    boolean stealthMode,
                    IsNaluProcessorPlugin.RouteChangeHandler handler) {
    String newRouteToken;
    String contextPath = NaluConfig.INSTANCE.getContextPath();
    if (NaluConfig.INSTANCE.isUsingHash()) {
      newRouteToken = newRoute.startsWith("#") ?
                      newRoute :
                      "#" + newRoute;
    } else {
      newRouteToken = "/";
      if (!Objects.isNull(contextPath) && !contextPath.isEmpty()) {
        newRouteToken = newRouteToken + contextPath + "/";
      }
      newRouteToken = newRouteToken + newRoute;

      if (!newRouteToken.endsWith("/")) {
        newRouteToken += "/";
      }
    }
    if (NaluConfig.INSTANCE.hasHistory()) {
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

}
