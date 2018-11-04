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

package com.github.nalukit.nalu.client.internal.route;

import java.util.ArrayList;
import java.util.List;

public class ShellConfiguration {

  private List<ShellConfig> shells;

  public ShellConfiguration() {
    super();

    this.shells = new ArrayList<>();
  }

  public List<ShellConfig> getShells() {
    return shells;
  }

  public ShellConfig match(String hash) {
    return this.shells.stream()
                      .filter(shellConfig -> shellConfig.getRoute()
                                                        .equals(hash))
                      .findFirst()
                      .get();
  }
}
