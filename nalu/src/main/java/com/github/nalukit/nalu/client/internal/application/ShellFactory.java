/*
 * Copyright (c) 2018 Frank Hossfeld
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

import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

import java.util.HashMap;
import java.util.Map;

@NaluInternalUse
public class ShellFactory {

  /* instance of the controller factory */
  private static ShellFactory instance;

  /* map of components (key: name of class, Value: ShellCreator */
  private Map<String, IsShellCreator> shellFactory;

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
                            IsShellCreator creator) {
    this.shellFactory.put(shellName,
                          creator);
  }

  public void shell(String shellName,
                    ShellCallback callback) {
    if (this.shellFactory.containsKey(shellName)) {
      IsShellCreator shellCreator  = this.shellFactory.get(shellName);
      ShellInstance  shellInstance = shellCreator.create();
      try {
        shellInstance.getShell()
                     .bind(() -> {
                       try {
                         shellCreator.onFinishCreating();
                         callback.onFinish(shellInstance);
                       } catch (RoutingInterceptionException e) {
                         callback.onRoutingInterceptionException(e);
                       }
                     });
      } catch (RoutingInterceptionException e) {
        callback.onRoutingInterceptionException(e);
      }
    } else {
      callback.onShellNotFound();
    }
  }

}
