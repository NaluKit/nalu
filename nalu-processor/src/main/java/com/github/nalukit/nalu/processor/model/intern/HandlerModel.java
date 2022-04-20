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

public class HandlerModel {

  private ClassNameModel          handler;
  private List<EventHandlerModel> eventHandlers;
  private List<EventModel>        eventModels;

  private HandlerModel() {
    this.eventHandlers = new ArrayList<>();
    this.eventModels   = new ArrayList<>();
  }

  public HandlerModel(ClassNameModel handler) {
    this();
    this.handler = handler;
  }

  public ClassNameModel getHandler() {
    return handler;
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
