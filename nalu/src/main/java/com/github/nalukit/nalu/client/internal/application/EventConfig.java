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

package com.github.nalukit.nalu.client.internal.application;

public class EventConfig {

  /* class name of event */
  private String eventClassName;
  /* class name of event handler */
  private String eventHandlerOfEventClassName;
  /* name of teh method to call from the handler */
  private String methodNameOfHandler;

  public EventConfig() {
  }

  public EventConfig(String eventClassName,
                     String eventHandlerOfEventClassName,
                     String methodNameOfHandler) {
    this.eventClassName               = eventClassName;
    this.eventHandlerOfEventClassName = eventHandlerOfEventClassName;
    this.methodNameOfHandler          = methodNameOfHandler;
  }

  public String getEventClassName() {
    return eventClassName;
  }

  public void setEventClassName(String eventClassName) {
    this.eventClassName = eventClassName;
  }

  public String getEventHandlerOfEventClassName() {
    return eventHandlerOfEventClassName;
  }

  public void setEventHandlerOfEventClassName(String eventHandlerOfEventClassName) {
    this.eventHandlerOfEventClassName = eventHandlerOfEventClassName;
  }

  public String getMethodNameOfHandler() {
    return methodNameOfHandler;
  }

  public void setMethodNameOfHandler(String methodNameOfHandler) {
    this.methodNameOfHandler = methodNameOfHandler;
  }
}
