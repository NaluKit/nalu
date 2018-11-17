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

public class PluginModel {

  private String name;

  private ClassNameModel plugin;

  private ClassNameModel loader;

  private ClassNameModel context;

  public PluginModel(String name,
                     ClassNameModel plugin,
                     ClassNameModel loader,
                     ClassNameModel context) {
    this.name = name;
    this.plugin = plugin;
    this.loader = loader;
    this.context = context;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ClassNameModel getPlugin() {
    return plugin;
  }

  public void setPlugin(ClassNameModel plugin) {
    this.plugin = plugin;
  }

  public ClassNameModel getLoader() {
    return loader;
  }

  public void setLoader(ClassNameModel loader) {
    this.loader = loader;
  }

  public ClassNameModel getContext() {
    return context;
  }

  public void setContext(ClassNameModel context) {
    this.context = context;
  }
}
