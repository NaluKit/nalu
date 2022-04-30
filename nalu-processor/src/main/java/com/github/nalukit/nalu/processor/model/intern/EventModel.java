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

import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

import java.util.List;

@NaluInternalUse
public class EventModel {

  private ClassNameModel event;
  private ClassNameModel eventHandlerOfEvent;
  private String         methodNameOfHandler;

  public EventModel(ClassNameModel event,
                    ClassNameModel eventHandlerOfEvent,
                    String methodNameOfHandler) {
    this.event               = event;
    this.eventHandlerOfEvent = eventHandlerOfEvent;
    this.methodNameOfHandler = methodNameOfHandler;
  }

  public ClassNameModel getEvent() {
    return event;
  }

  public ClassNameModel getEventHandlerOfEvent() {
    return eventHandlerOfEvent;
  }

  public String getMethodNameOfHandler() {
    return methodNameOfHandler;
  }
}
