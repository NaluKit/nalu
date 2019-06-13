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

public class PopUpControllerModel {

  private String name;

  private ClassNameModel context;

  private ClassNameModel controller;

  private ClassNameModel componentInterface;

  private ClassNameModel component;

  private ClassNameModel provider;

  private boolean componentCreator;

  public PopUpControllerModel(String name,
                              ClassNameModel context,
                              ClassNameModel controller,
                              ClassNameModel componentInterface,
                              ClassNameModel component,
                              ClassNameModel provider,
                              boolean componentCreator) {
    this.name = name;
    this.context = context;
    this.controller = controller;
    this.componentInterface = componentInterface;
    this.component = component;
    this.provider = provider;
    this.componentCreator = componentCreator;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ClassNameModel getContext() {
    return context;
  }

  public void setContext(ClassNameModel context) {
    this.context = context;
  }

  public ClassNameModel getController() {
    return controller;
  }

  public void setController(ClassNameModel controller) {
    this.controller = controller;
  }

  public ClassNameModel getComponentInterface() {
    return componentInterface;
  }

  public void setComponentInterface(ClassNameModel componentInterface) {
    this.componentInterface = componentInterface;
  }

  public ClassNameModel getComponent() {
    return component;
  }

  public void setComponent(ClassNameModel component) {
    this.component = component;
  }

  public ClassNameModel getProvider() {
    return provider;
  }

  public void setProvider(ClassNameModel provider) {
    this.provider = provider;
  }

  public boolean isComponentCreator() {
    return componentCreator;
  }

  public void setComponentCreator(boolean componentCreator) {
    this.componentCreator = componentCreator;
  }

}
