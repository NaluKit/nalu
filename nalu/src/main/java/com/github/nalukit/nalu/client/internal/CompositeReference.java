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

package com.github.nalukit.nalu.client.internal;

public class CompositeReference {

  private String source;

  private String compositeName;

  private String composite;

  private String selector;

  private boolean scopeGlobal;

  public CompositeReference(String source,
                            String compositeName,
                            String composite,
                            String selector,
                            boolean scopeGlobal) {
    this.source        = source;
    this.compositeName = compositeName;
    this.composite     = composite;
    this.selector      = selector;
    this.scopeGlobal   = scopeGlobal;
  }

  public String getSource() {
    return source;
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

  public boolean isScopeGlobal() {
    return scopeGlobal;
  }

}
