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

package io.github.nalukit.nalu.simpleapplication02.client.ui.footer;

import io.github.nalukit.nalu.client.component.AbstractComponent;

/**
 * this is the presenter of the shellCreator. The shellCreator divides the browser in
 * several areas.
 */
public class FooterComponent
    extends AbstractComponent<IFooterComponent.Controller, String>
    implements IFooterComponent {

  @SuppressWarnings("unused")
  private String messageInfo;

  public FooterComponent() {
  }

  @Override
  public void render() {
    initElement("footer");
  }

  @Override
  public void setStatus(String status) {
    //this.messageInfo.textContent = status;
  }

}
