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

package com.github.nalukit.nalu.client.internal.application;

import com.github.nalukit.nalu.client.component.IsShell;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

import java.util.HashMap;
import java.util.Map;

@NaluInternalUse
public class ShellFactory {

  /* instance of the controller factory */
  private static ShellFactory instance;

  /* map of components (key: name of class, Value: ShellCreator */
  private Map<String, ShellCreator> shellFactory;

  private ShellFactory() {
    this.shellFactory = new HashMap<>();
  }

  public static ShellFactory get() {
    if (instance == null) {
      instance = new ShellFactory();
    }
    return instance;
  }

  public void registerShell(String shellName,
                            ShellCreator creator) {
    this.shellFactory.put(shellName,
                          creator);
  }

  public ShellInstance shell(String shellName) {
    if (this.shellFactory.containsKey(shellName)) {
      return this.shellFactory.get(shellName)
                              .create();
    }
    return null;
  }
}
