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

import com.github.nalukit.nalu.client.internal.PropertyFactory;
import com.github.nalukit.nalu.client.internal.route.ShellConfiguration;
import com.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin;
import com.github.nalukit.nalu.plugin.core.web.client.NaluPluginCoreWeb;
import com.github.nalukit.nalu.plugin.core.web.client.model.NaluStartModel;
import com.github.nalukit.nalu.plugin.gwt.client.selector.SelectorCommand;
import com.github.nalukit.nalu.plugin.gwt.client.selector.SelectorProvider;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.MetaElement;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;

import java.util.Map;
import java.util.Objects;

public class NaluPluginGWT
    implements IsNaluProcessorPlugin {

  private NaluStartModel naluStartModel;

  /* RouteChangeHandler - to be used directly   */
  /* in case Nalu does not have history support */
  private RouteChangeHandler routeChangeHandler;

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
  public String getStartRoute() {
    return this.naluStartModel.getStartRoute();
  }

  @Override
  public Map<String, String> getQueryParameters() {
    return this.naluStartModel.getQueryParameters();
  }

  @Override
  public void register(RouteChangeHandler handler) {
    if (PropertyFactory.get()
                       .hasHistory()) {
      if (PropertyFactory.get()
                         .isUsingHash()) {
        NaluPluginCoreWeb.addOnHashChangeHandler(handler);
      } else {
        NaluPluginCoreWeb.addPopStateHandler(handler,
                                             PropertyFactory.get()
                                                            .getContextPath());
      }
    } else {
      this.routeChangeHandler = handler;
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
                    boolean replace) {
    NaluPluginCoreWeb.route(newRoute,
                            replace,
                            this.routeChangeHandler);
  }

  @Override
  public void initialize(ShellConfiguration shellConfiguration) {
    // Sets the context path inside the PropertyFactory
    NaluPluginCoreWeb.getContextPath(shellConfiguration);
    this.naluStartModel = NaluPluginCoreWeb.getNaluStartModel();
  }

  @Override
  public void updateTitle(String title) {
    Window.setTitle(title);
  }

  @Override
  public void updateMetaNameContent(String name,
                                    String content) {
    MetaElement metaElement = Document.get()
                                      .createMetaElement();
    metaElement.setName("name");
    metaElement.setContent("content");
    Element headerElement = getHeaderNode();
    if (!Objects.isNull(headerElement)) {
      headerElement.appendChild(metaElement);
    }
    // TODO
  }

  @Override
  public void updateMetaPropertyContent(String property,
                                        String content) {
    NodeList<Element> nodeList = Document.get()
                                         .getElementsByTagName("meta");
    for (int i = 0; i < nodeList.getLength(); i++) {
      Element node = nodeList.getItem(i);
      GWT.debugger();

    }




    MetaElement metaElement = Document.get()
                                      .createMetaElement();
    metaElement.setAttribute("property",
                             property);
    metaElement.setContent("content");
    // TODO
  }

  @Override
  public String decode(String route) {
    return URL.decode(route);
  }

  private Element addOrUpdate(MetaElement metaElement) {
    NodeList<Element> node = Document.get()
                                     .getElementsByTagName("head");
    return node.getItem(0);
  }

  private Element getHeaderNode() {
    NodeList<Element> node = Document.get()
                                     .getElementsByTagName("head");
    return node.getItem(0);
  }

  private void addOUpdateNameMetaTag(MetaElement metaElement,
                                     String firstParameter,
                                     String secondParameter) {
    NodeList<Element> nodeList = Document.get()
                                         .getElementsByTagName("meta");
    for (int i = 0; i < nodeList.getLength(); i++) {
      Element node = nodeList.getItem(i);
      GWT.debugger();

    }

    //    Element element2 = (Element) node.getItem(0);
    //    String name = element2.getAttribute(name);

  }

}
