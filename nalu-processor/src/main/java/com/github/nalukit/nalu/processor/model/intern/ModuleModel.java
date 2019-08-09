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

/**
 * Model of the Module annotation.
 */
public class ModuleModel {

  private String name;

  private ClassNameModel module;

  private ClassNameModel moduleContext;

  public ModuleModel(String name,
                     ClassNameModel module,
                     ClassNameModel moduleContext) {
    this.name = name;
    this.module = module;
    this.moduleContext = moduleContext;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ClassNameModel getModule() {
    return module;
  }

  public void setModule(ClassNameModel module) {
    this.module = module;
  }

  public ClassNameModel getModuleContext() {
    return moduleContext;
  }

  public void setModuleContext(ClassNameModel moduleContext) {
    this.moduleContext = moduleContext;
  }

}
