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

package com.github.nalukit.nalu.simpleapplication02.client.event;

import org.gwtproject.event.shared.Event;

public class StatusChangeEvent
    extends Event<StatusChangeEvent.StatusChangeHandler> {
  
  public static Type<StatusChangeEvent.StatusChangeHandler> TYPE = new Type<>();
  
  private String status;
  
  public StatusChangeEvent(String status) {
    super();
    
    this.status = status;
  }
  
  public String getStatus() {
    return status;
  }
  
  @Override
  public Type<StatusChangeEvent.StatusChangeHandler> getAssociatedType() {
    return TYPE;
  }
  
  @Override
  protected void dispatch(StatusChangeEvent.StatusChangeHandler handler) {
    handler.onStatusChange(this);
  }
  
  public interface StatusChangeHandler {
    
    void onStatusChange(StatusChangeEvent event);
    
  }
  
}
