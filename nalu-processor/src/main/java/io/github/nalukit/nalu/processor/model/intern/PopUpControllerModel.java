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

package io.github.nalukit.nalu.processor.model.intern;

import java.util.ArrayList;
import java.util.List;

public class PopUpControllerModel {

  public  ClassNameModel          condition;
  private String                  name;
  private ClassNameModel          context;
  private ClassNameModel          controller;
  private ClassNameModel          componentInterface;
  private ClassNameModel          component;
  private ClassNameModel          provider;
  private boolean                 componentCreator;
  private boolean                 alwaysRenderComponent;
  private List<EventHandlerModel> eventHandlers;
  private List<EventModel>        eventModels;

  public PopUpControllerModel() {
    this.eventHandlers = new ArrayList<>();
    this.eventModels   = new ArrayList<>();
  }

  public PopUpControllerModel(String name,
                              ClassNameModel context,
                              ClassNameModel controller,
                              ClassNameModel componentInterface,
                              ClassNameModel component,
                              ClassNameModel provider,
                              ClassNameModel condition,
                              boolean componentCreator,
                              boolean alwaysRenderComponent,
                              List<EventHandlerModel> eventHandlers,
                              List<EventModel> eventModels) {
    this();

    this.name                  = name;
    this.context               = context;
    this.controller            = controller;
    this.componentInterface    = componentInterface;
    this.component             = component;
    this.provider              = provider;
    this.componentCreator      = componentCreator;
    this.condition             = condition;
    this.alwaysRenderComponent = alwaysRenderComponent;
    this.eventHandlers         = eventHandlers;
    this.eventModels           = eventModels;
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

  public ClassNameModel getCondition() {
    return condition;
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

  public boolean isAlwaysRenderComponent() {
    return alwaysRenderComponent;
  }

  public void setAlwaysRenderComponent(boolean alwaysRenderComponent) {
    this.alwaysRenderComponent = alwaysRenderComponent;
  }

  public List<EventHandlerModel> getEventHandlers() {
    return eventHandlers;
  }

  public void setEventHandlers(List<EventHandlerModel> eventHandlers) {
    this.eventHandlers = eventHandlers;
  }

  public List<EventModel> getEventModels() {
    return eventModels;
  }

  public void setEventModels(List<EventModel> eventModels) {
    this.eventModels = eventModels;
  }

  public EventModel getEventModel(String className) {
    return this.getEventModels()
               .stream()
               .filter(c -> c.getEvent()
                             .getClassName()
                             .equals(className))
               .findFirst()
               .orElse(null);
  }

}
