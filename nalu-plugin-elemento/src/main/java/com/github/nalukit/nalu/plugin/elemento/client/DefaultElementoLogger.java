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
package com.github.nalukit.nalu.plugin.elemento.client;

import com.github.nalukit.nalu.plugin.core.web.client.AbstractLogger;
import com.github.nalukit.nalu.plugin.core.web.client.NaluPluginCoreWeb;
import elemental2.dom.DomGlobal;

public class DefaultElementoLogger
    extends AbstractLogger {

  static final String INDENT = "..";

  public void log(String message,
                  int depth) {
    if (NaluPluginCoreWeb.isSuperDevMode()) {
      DomGlobal.window.console.log(createLog(message,
                                             depth));
    }
  }

}
