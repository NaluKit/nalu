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

package com.github.nalukit.nalu.processor.common;

import com.github.nalukit.nalu.client.component.AbstractShell;
import elemental2.dom.CSSProperties;
import elemental2.dom.Element;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;

import static elemental2.dom.DomGlobal.document;

public class MockShellLogin
    extends AbstractShell<MockContext> {

  public MockShell() {
  }

  /**
   * The ShellPresenter has to implemented this method, because the framework
   * can not do this. (It does not know, what to use).
   * <p>
   * We append the ShellView to the browser body.
   */
  @Override
  public void attachShell() {
    document.body.appendChild(this.render());
  }

  private HTMLElement render() {
    document.body.style.margin = CSSProperties.MarginUnionType.of(0);

    HTMLDivElement shell = (HTMLDivElement) document.createElement("div");
    shell.style.height = CSSProperties.HeightUnionType.of("auto");
    shell.style.width = CSSProperties.WidthUnionType.of("100%");
    shell.style.margin = CSSProperties.MarginUnionType.of(0);

    HTMLDivElement content = (HTMLDivElement) document.createElement("div");
    content.id = "content";
    content.style.position = "absolute";
    content.style.overflow = "hidden";
    content.style.top = "128px";
    content.style.bottom = "42px";
    content.style.left = "212px";
    content.style.right = String.valueOf(0);

    shell.appendChild(content);

    Element footer = createSouth();
    shell.appendChild(footer);

    return shell;
  }

  private Element createNorth() {
    HTMLElement panel = (HTMLElement) document.createElement("header");
    panel.id = "header";
    panel.style.position = "absolute";
    panel.style.overflow = "hidden";
    panel.style.height = CSSProperties.HeightUnionType.of("128px");
    panel.style.top = String.valueOf(0);
    panel.style.right = String.valueOf(0);
    panel.style.left = String.valueOf(0);
    panel.style.width = CSSProperties.WidthUnionType.of("100%");
    panel.style.borderBottom = "black 1px solid";
    return panel;
  }

  private Element createSouth() {
    HTMLElement panel = (HTMLElement) document.createElement("header");
    panel.id = "footer";
    panel.style.position = "absolute";
    panel.style.overflow = "hidden";
    panel.style.height = CSSProperties.HeightUnionType.of("128px");
    panel.style.top = String.valueOf(0);
    panel.style.right = String.valueOf(0);
    panel.style.left = String.valueOf(0);
    panel.style.width = CSSProperties.WidthUnionType.of("100%");
    panel.style.borderBottom = "black 1px solid";
    return panel;
  }
}
