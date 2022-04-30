/*
 * Copyright (c) 2018 - 2020 - Frank Hossfeld
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

package com.github.nalukit.nalu.processor.model.intern;

import java.util.ArrayList;
import java.util.List;

public class ControllerCompositeModel {

  public  ClassNameModel          condition;
  private String                  name;
  private ClassNameModel          composite;
  private String                  selector;
  private boolean                 scopeGlobal;


  public ControllerCompositeModel() {
  }

  public ControllerCompositeModel(String name,
                                  ClassNameModel composite,
                                  String selector,
                                  ClassNameModel condition,
                                  boolean scopeGlobal) {
    this();

    this.condition     = condition;
    this.name          = name;
    this.composite     = composite;
    this.selector      = selector;
    this.scopeGlobal   = scopeGlobal;
   }

  public String getName() {
    return name;
  }

  public ClassNameModel getComposite() {
    return composite;
  }

  public String getSelector() {
    return selector;
  }

  public ClassNameModel getCondition() {
    return condition;
  }

  public boolean isScopeGlobal() {
    return scopeGlobal;
  }

}
