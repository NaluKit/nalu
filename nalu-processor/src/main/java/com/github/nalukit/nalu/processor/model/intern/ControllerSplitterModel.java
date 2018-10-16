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

package com.github.nalukit.nalu.processor.model.intern;

public class ControllerSplitterModel {

  private String name;

  private ClassNameModel splitter;

  private String selector;

  public ControllerSplitterModel() {
  }

  public ControllerSplitterModel(String name,
                                 ClassNameModel splitter,
                                 String selector) {
    this.name = name;
    this.splitter = splitter;
    this.selector = selector;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ClassNameModel getSplitter() {
    return splitter;
  }

  public void setSplitter(ClassNameModel splitter) {
    this.splitter = splitter;
  }

  public String getSelector() {
    return selector;
  }

  public void setSelector(String selector) {
    this.selector = selector;
  }
}
