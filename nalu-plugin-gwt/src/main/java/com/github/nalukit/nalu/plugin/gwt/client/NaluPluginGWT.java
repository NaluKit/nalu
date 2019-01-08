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

package com.github.nalukit.nalu.plugin.gwt.client;

import com.github.nalukit.nalu.client.internal.route.ShellConfiguration;
import com.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin;
import com.github.nalukit.nalu.plugin.core.web.client.NaluPluginCoreWeb;
import com.github.nalukit.nalu.plugin.core.web.client.model.NaluStartModel;
import com.github.nalukit.nalu.plugin.gwt.client.selector.SelectorCommand;
import com.github.nalukit.nalu.plugin.gwt.client.selector.SelectorProvider;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;

import java.util.Map;

public class NaluPluginGWT
    implements IsNaluProcessorPlugin {

  private String contextPath;

  private NaluStartModel naluStartModel;

  public NaluPluginGWT() {
    super();
  }

  @Override
  public void alert(String message) {
    Window.alert(message);
  }

  @Override
  public boolean attach(String selector,
                        Object asElement) {
    SelectorCommand selectorCommand = SelectorProvider.get()
                                                      .getSelectorCommands()
                                                      .get(selector);
    if (selectorCommand == null) {
      return false;
    } else {
      selectorCommand.append(((IsWidget) asElement).asWidget());
      return true;
    }
  }

  @Override
  public boolean confirm(String message) {
    return Window.confirm(message);
  }

  @Override
  public String getStartRoute(boolean usingHash) {
    return this.naluStartModel.getStartRoute();
  }

  @Override
  public Map<String, String> getQueryParameters() {
    return this.naluStartModel.getQueryParameters();
  }

  @Override
  public void register(RouteChangeHandler handler,
                       boolean usingHash) {
    if (usingHash) {
      NaluPluginCoreWeb.addOnHashChangeHandler(handler);
    } else {
      NaluPluginCoreWeb.addPopStateHandler(handler,
                                           this.contextPath);
    }
  }

  @Override
  public void remove(String selector) {
    Element selectorElement = DOM.getElementById(selector);
    if (selectorElement != null) {
      selectorElement.removeAllChildren();
    }
  }

  @Override
  public void route(String newRoute,
                    boolean replace,
                    boolean usingHash) {
    NaluPluginCoreWeb.route(this.contextPath,
                            newRoute,
                            replace,
                            usingHash);
  }

  @Override
  public void initialize(boolean usingHash,
                         ShellConfiguration shellConfiguration) {
    this.contextPath = NaluPluginCoreWeb.getContextPath(usingHash,
                                                        shellConfiguration);

    this.naluStartModel = NaluPluginCoreWeb.getNaluStartModel(this.contextPath,
                                                              usingHash);
  }

}
