/*
 * Copyright (c) 2018 - 2019 - Frank Hossfeld
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
import elemental2.dom.Element;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;

import static elemental2.dom.DomGlobal.document;
import static org.jboss.gwt.elemento.core.Elements.*;

public class MockShell
    extends AbstractShell<MockContext> {

  private static final String NALU_ID_ATTRIBUTE = "id";

  private HTMLDivElement shell;

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

  /**
   * The ShellPresenter has to implemented this method, because the framework
   * can not do this. (It does not know, what to use).
   * <p>
   * We remmove the ShellView from the browser body.
   */
  @Override
  public void detachShell() {
    document.body.removeChild(this.shell);
  }

  private HTMLElement render() {
    this.shell = div().css("shellCreator")
                      .add(createNorth())
                      .add(createSouth())
                      .add(div().css("shellNavigation")
                                .attr(MockShell.NALU_ID_ATTRIBUTE,
                                      "navigation")
                                .get())
                      .add(div().css("shellContent")
                                .attr(MockShell.NALU_ID_ATTRIBUTE,
                                      "content")
                                .get())
                      .get();
    return this.shell;
  }

  private Element createNorth() {
    return header().css("shellHeader")
                   .attr(MockShell.NALU_ID_ATTRIBUTE,
                         "header")
                   .get();
  }

  private Element createSouth() {
    return footer().css("shellFooter")
                   .attr(MockShell.NALU_ID_ATTRIBUTE,
                         "footer")
                   .get();
  }

}
