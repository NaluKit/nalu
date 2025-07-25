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

public class EventHandlerModel {

  private ClassNameModel parentClass;

  private ClassNameModel event;

  private String methodName;

  public EventHandlerModel(ClassNameModel parentClass,
                           ClassNameModel event,
                           String methodName) {
    this.parentClass = parentClass;
    this.event       = event;
    this.methodName  = methodName;
  }

  public ClassNameModel getParentClass() {
    return parentClass;
  }

  public void setParentClass(ClassNameModel parentClass) {
    this.parentClass = parentClass;
  }

  public ClassNameModel getEvent() {
    return event;
  }

  public void setEvent(ClassNameModel event) {
    this.event = event;
  }

  public String getMethodName() {
    return methodName;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }
}
