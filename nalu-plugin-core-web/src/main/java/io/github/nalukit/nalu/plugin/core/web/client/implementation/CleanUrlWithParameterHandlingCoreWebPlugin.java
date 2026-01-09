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
import io.github.nalukit.nalu.plugin.core.web.client.NaluCoreConstants;

import java.util.Map;
import java.util.Objects;

/**
 * This plugin anables Nalu to deal with parameters inside the url.
 * <br>
 * Forsing Nalu to use this plugin instead of the defailt one, call:
 * <br>
 * NaluCorePluginFactory.INSTANCE.registerPlugin(new CleanUrlWithParameterHandlingCoreWebPlugin());
 * <br>
 * bfore the <b>run</b>-method of the Applicaiton class.
 *
 */
public class CleanUrlWithParameterHandlingCoreWebPlugin
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
    // remove last "/" (in case it exists)
    if (newRouteToken.endsWith("/")) {
      newRouteToken = newRouteToken.substring(0, newRouteToken.length() - 1);
    }
    // in case we had parameters inside the url at start up, we add
    // them to the newRoute
    StringBuilder parameterToken = new StringBuilder();
    if (this.hasParameters()) {
      this.createParameterToken(parameterToken);
    }
    if (NaluConfig.INSTANCE.hasHistory()) {
      if (!stealthMode) {
        if (parameterToken.length() > 0) {
          newRouteToken = newRouteToken + parameterToken;
        }
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
      String newPath = DomGlobal.window.location.pathname;
      if (parameterToken.length() > 0) {
        newPath = newPath + parameterToken;
      }
      DomGlobal.window.history.pushState("",
                                         DomGlobal.document.title,
                                         newPath);
    }
  }

  private void createParameterToken(StringBuilder parameterToken) {
    parameterToken.append("?");
    boolean firstOneAdded = false;
    for (Map.Entry<String, String> entry : super.queryParameters.entrySet()) {
      if (!NaluCoreConstants.PARAMETER_URI.equals(entry.getKey())) {
        String key   = entry.getKey();
        String value = entry.getValue();
        if (!firstOneAdded) {
          parameterToken.append("&");
        }
        parameterToken.append(key)
                      .append("=")
                      .append(value);
      }
    }
  }

  private boolean hasParameters() {
    if (Objects.isNull(super.queryParameters)) {
      return false;
    }
    if (super.queryParameters.isEmpty()) {
      return false;
    }
    return super.queryParameters.size() != 1 || !super.queryParameters.containsKey(NaluCoreConstants.PARAMETER_URI);
  }

}
