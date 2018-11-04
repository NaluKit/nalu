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

import com.github.nalukit.nalu.client.plugin.IsPlugin;

public class MockPlugin
    implements IsPlugin {

  @Override
  public void alert(String message) {

  }

  @Override
  public boolean attach(String selector,
                        Object asElement) {
    return true;
  }

  @Override
  public boolean confirm(String message) {
    return true;
  }

  @Override
  public String getStartRoute() {
    return "/";
  }

  @Override
  public void register(HashHandler handler) {

  }

  @Override
  public void remove(String selector) {

  }

  @Override
  public void route(String newRoute,
                    boolean replace) {

  }
}
