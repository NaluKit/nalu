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

package com.github.nalukit.nalu.client.internal;

public class CompositeControllerReference {

  private String controller;

  private String compositeName;

  private String composite;

  private String selector;

  public CompositeControllerReference(String controller,
                                      String compositeName,
                                      String composite,
                                      String selector) {
    this.controller = controller;
    this.compositeName = compositeName;
    this.composite = composite;
    this.selector = selector;
  }

  public String getController() {
    return controller;
  }

  public String getCompositeName() {
    return compositeName;
  }

  public String getComposite() {
    return composite;
  }

  public String getSelector() {
    return selector;
  }
}
