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

package com.github.nalukit.nalu.plugin.gwt.client;

import com.github.nalukit.nalu.client.internal.PropertyFactory;
import com.github.nalukit.nalu.client.internal.route.ShellConfiguration;
import com.github.nalukit.nalu.client.plugin.IsCustomAlertPresenter;
import com.github.nalukit.nalu.client.plugin.IsCustomConfirmPresenter;
import com.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin;
import com.github.nalukit.nalu.plugin.core.web.client.NaluPluginCoreWeb;
import com.github.nalukit.nalu.plugin.core.web.client.model.NaluStartModel;
import com.github.nalukit.nalu.plugin.gwt.client.selector.SelectorCommand;
import com.github.nalukit.nalu.plugin.gwt.client.selector.SelectorProvider;
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

  private IsCustomAlertPresenter   customAlertPresenter;
  private IsCustomConfirmPresenter customConfirmPresenter;

  /* RouteChangeHandler - to be used directly   */
  /* in case Nalu does not have history support */
  private RouteChangeHandler routeChangeHandler;

  public NaluPluginGWT() {
    super();
  }

  @Override
  public void alert(String message) {
    if (customAlertPresenter == null) {
      Window.alert(message);
    } else {
      this.customAlertPresenter.alert(message);
    }
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
  public void confirm(String message,
                      ConfirmHandler handler) {
    if (customConfirmPresenter == null) {
      if (Window.confirm(message)) {
        handler.onOk();
      } else {
        handler.onCancel();
      }
    } else {
      customConfirmPresenter.addConfirmHandler(handler);
      customConfirmPresenter.confirm(message);
    }
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
    if (PropertyFactory.INSTANCE
                       .hasHistory()) {
      if (PropertyFactory.INSTANCE
                         .isUsingHash()) {
        NaluPluginCoreWeb.addOnHashChangeHandler(handler);
      } else {
        NaluPluginCoreWeb.addPopStateHandler(handler,
                                             PropertyFactory.INSTANCE
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
                    boolean replace,
                    boolean stealthMode) {
    NaluPluginCoreWeb.route(newRoute,
                            replace,
                            stealthMode,
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
    NodeList<Element> metaTagList = Document.get()
                                            .getElementsByTagName("meta");
    for (int i = 0; i < metaTagList.getLength(); i++) {
      if (metaTagList.getItem(i) instanceof MetaElement) {
        MetaElement nodeListElement = (MetaElement) metaTagList.getItem(i);
        if (!Objects.isNull(nodeListElement.getName())) {
          if (nodeListElement.getName()
                             .equals(name)) {
            nodeListElement.removeFromParent();
            break;
          }
        }
      }
    }
    MetaElement metaElement = Document.get()
                                      .createMetaElement();
    metaElement.setName("name");
    metaElement.setContent("content");
    Element headerElement = getHeaderNode();
    if (!Objects.isNull(headerElement)) {
      headerElement.appendChild(metaElement);
    }
  }

  @Override
  public void updateMetaPropertyContent(String property,
                                        String content) {
    NodeList<Element> metaTagList = Document.get()
                                            .getElementsByTagName("meta");
    for (int i = 0; i < metaTagList.getLength(); i++) {
      if (metaTagList.getItem(i) instanceof MetaElement) {
        MetaElement nodeListElement = (MetaElement) metaTagList.getItem(i);
        if (!Objects.isNull(nodeListElement.getAttribute("property"))) {
          if (nodeListElement.getAttribute("property")
                             .equals(property)) {
            nodeListElement.removeFromParent();
            break;
          }
        }
      }
    }
    MetaElement metaElement = Document.get()
                                      .createMetaElement();
    metaElement.setAttribute("property",
                             property);
    metaElement.setContent("content");
    Element headerElement = getHeaderNode();
    if (!Objects.isNull(headerElement)) {
      headerElement.appendChild(metaElement);
    }
  }

  @Override
  public String decode(String route) {
    return URL.decode(route);
  }

  @Override
  public void setCustomAlertPresenter(IsCustomAlertPresenter customAlertPresenter) {
    this.customAlertPresenter = customAlertPresenter;
  }

  @Override
  public void setCustomConfirmPresenter(IsCustomConfirmPresenter customConfirmPresenter) {
    this.customConfirmPresenter = customConfirmPresenter;
  }

  private Element getHeaderNode() {
    NodeList<Element> node = Document.get()
                                     .getElementsByTagName("head");
    return node.getItem(0);
  }

}
