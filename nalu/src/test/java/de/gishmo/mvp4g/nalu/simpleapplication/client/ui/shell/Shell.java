/*
 * Copyright (c) 2018 - Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *
 */

package de.gishmo.mvp4g.nalu.simpleapplication.client.ui.shell;

import com.github.mvp4g.nalu.client.component.AbstractShell;
import de.gishmo.mvp4g.nalu.simpleapplication.client.NaluSimpleApplicationContext;

/**
 * this is the presenter of the shell. The shell divides the browser in
 * severeal areas.
 */
public class Shell
  extends AbstractShell<NaluSimpleApplicationContext> {

  public Shell() {
  }

  /**
   * The ShellPresenter has to implemented this method, because the framework
   * can not do this. (It does not know, what to use).
   * <p>
   * We append the ShellView to the browser body.
   */
  @Override
  public void attachShell() {
//    Layout layout = Layout.create("Nalu - Simple Application using Domino-UI")
//                          .show(ColorScheme.INDIGO);
//
//    layout.showFooter()
//          .fixFooter()
//          .getFooter()
//          .asElement().style.minHeight = CSSProperties.MinHeightUnionType.of("42px");
//
//    layout.getFooter()
//          .asElement().id = "footer";
//    layout.getLeftPanel().id = "navigation";
//    layout.getContentPanel().id = "content";
  }
}
