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

public class ShellModel {

  private String name;

  private ClassNameModel shell;

  private ClassNameModel                         context;
  private List<EventHandlerModel>                eventHandlers;
  private List<ShellAndControllerCompositeModel> composites;
  private List<EventModel>                       eventModels;

  public ShellModel() {
    this.composites    = new ArrayList<>();
    this.eventHandlers = new ArrayList<>();
    this.eventModels   = new ArrayList<>();
  }

  public ShellModel(String name,
                    ClassNameModel shell,
                    ClassNameModel context,
                    List<EventHandlerModel> eventHandlers,
                    List<EventModel> eventModels) {
    this()

    ;
    this.name          = name;
    this.shell         = shell;
    this.context       = context;
    this.eventHandlers = eventHandlers;
    this.eventModels   = eventModels;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ClassNameModel getShell() {
    return shell;
  }

  public void setShell(ClassNameModel shell) {
    this.shell = shell;
  }

  public ClassNameModel getContext() {
    return context;
  }

  public void setContext(ClassNameModel context) {
    this.context = context;
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

  public List<ShellAndControllerCompositeModel> getComposites() {
    return composites;
  }

  public void setComposites(List<ShellAndControllerCompositeModel> composites) {
    this.composites = composites;
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
