/*
 * Copyright (c) 2018 - Frank Hossfeld
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

package com.github.nalukit.nalu.plugin.elemental2.client;

import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.plugin.IsPlugin;
import elemental2.dom.*;
import jsinterop.base.Js;

public class NaluPluginElemental2
    implements IsPlugin {

  public NaluPluginElemental2() {
    super();
  }

  @Override
  public void alert(String message) {
    DomGlobal.window.alert(message);
  }

  @Override
  public boolean attach(String selector,
                        Object asElement) {
    Element selectorElement = DomGlobal.document.querySelector("#" + selector);
    if (selectorElement == null) {
      // TODO better message
      DomGlobal.window.alert("Ups ... selector >>" + selector + "<< not found!");
      return false;
    } else {
      selectorElement.appendChild((HTMLElement) asElement);
      return true;
    }
  }

  @Override
  public boolean confirm(String message) {
    return DomGlobal.window.confirm(message);
  }

  @Override
  public String getStartRoute() {
    Location location = Js.uncheckedCast(DomGlobal.location);
    String starthash = location.getHash();
    if (starthash.startsWith("#")) {
      starthash = starthash.substring(1);
    }
    return starthash;
  }

  @Override
  public void register(HashHandler handler) {
    DomGlobal.window.onhashchange = e -> {
      String newUrl = "";
      if (detectIE11()) {
        Location location = Js.uncheckedCast(DomGlobal.location);
        newUrl = location.getHash();
      } else {
        // cast event ...
        HashChangeEvent hashChangeEvent = (HashChangeEvent) e;
        newUrl = hashChangeEvent.newURL;
      }
      if (newUrl.startsWith("#")) {
        newUrl = newUrl.substring(1);
      }
      StringBuilder sb = new StringBuilder();
      sb.append("Router: onhashchange: new url ->>")
        .append(newUrl)
        .append("<<");
      ClientLogger.get()
                  .logSimple(sb.toString(),
                             0);
      // look for a routing ...
      handler.onHashChange(newUrl);
      return null;
    };
  }

  @Override
  public void remove(String selector) {
    Element selectorElement = DomGlobal.document.querySelector("#" + selector);
    if (selectorElement != null) {
      if (selectorElement.childNodes.length > 0) {
        for (int i = selectorElement.childNodes.asList()
                                               .size() - 1; i > -1; i--) {
          selectorElement.removeChild(selectorElement.childNodes.asList()
                                                                .get(i));
        }
      }
    }
  }

  @Override
  public void route(String newRoute,
                    boolean replace) {
    if (replace) {
      DomGlobal.window.history.replaceState(null,
                                            null,
                                            "#" + newRoute);
    } else {
      DomGlobal.window.history.pushState(null,
                                         null,
                                         "#" + newRoute);
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
   * @param
   * @return true -> is IE
   */
  private boolean detectIE11() {
    String ua = DomGlobal.window.navigator.userAgent;
    if (ua.indexOf("MSIE ") > 0) {
      return true;
    }
    return ua.indexOf("Trident/") > 0;
  }
}
