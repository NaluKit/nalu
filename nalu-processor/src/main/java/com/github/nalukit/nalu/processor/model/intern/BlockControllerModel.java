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

import java.util.List;

public class BlockControllerModel {

  private       String                  name;
  private       ClassNameModel          context;
  private       ClassNameModel          controller;
  private       ClassNameModel          componentInterface;
  private       ClassNameModel          component;
  private final ClassNameModel          provider;
  private       boolean                 componentCreator;
  private       ClassNameModel          conndition;
  private       List<EventHandlerModel> eventHandlers;
  private       List<EventModel>        eventModels;

  public BlockControllerModel(String name,
                              ClassNameModel context,
                              ClassNameModel controller,
                              ClassNameModel componentInterface,
                              ClassNameModel component,
                              ClassNameModel provider,
                              boolean componentCreator,
                              ClassNameModel conndition,
                              List<EventHandlerModel> eventHandlers,
                              List<EventModel> eventModels) {
    this.name               = name;
    this.context            = context;
    this.controller         = controller;
    this.componentInterface = componentInterface;
    this.component          = component;
    this.provider           = provider;
    this.componentCreator   = componentCreator;
    this.conndition         = conndition;
    this.eventHandlers      = eventHandlers;
    this.eventModels        = eventModels;
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

  public boolean isComponentCreator() {
    return componentCreator;
  }

  public void setComponentCreator(boolean componentCreator) {
    this.componentCreator = componentCreator;
  }

  public ClassNameModel getConndition() {
    return conndition;
  }

  public void setConndition(ClassNameModel conndition) {
    this.conndition = conndition;
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
