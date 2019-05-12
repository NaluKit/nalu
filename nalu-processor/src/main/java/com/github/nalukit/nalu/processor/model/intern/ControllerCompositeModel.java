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

package com.github.nalukit.nalu.processor.model.intern;

import com.github.nalukit.nalu.client.component.IsLoadCompositeCondition;

public class ControllerCompositeModel {

  private String name;

  private ClassNameModel composite;

  private String selector;

  public ClassNameModel condition;

  public ControllerCompositeModel() {
  }

  public ControllerCompositeModel(String name,
                                  ClassNameModel composite,
                                  String selector,
                                  ClassNameModel condition) {
    this.name = name;
    this.composite = composite;
    this.selector = selector;
    this.condition = condition;
  }

  public String getName() {
    return name;
  }

//  public void setName(String name) {
//    this.name = name;
//  }

  public ClassNameModel getComposite() {
    return composite;
  }

//  public void setComposite(ClassNameModel composite) {
//    this.composite = composite;
//  }

  public String getSelector() {
    return selector;
  }

//  public void setSelector(String selector) {
//    this.selector = selector;
//  }

  public ClassNameModel getCondition() {
    return condition;
  }

}
