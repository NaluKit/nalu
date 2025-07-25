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

package io.github.nalukit.nalu.simpleapplication02.client.event;

import org.gwtproject.event.shared.Event;

public class SelectEvent
    extends Event<SelectEvent.StatusChangeHandler> {

  public static Type<SelectEvent.StatusChangeHandler> TYPE = new Type<>();

  private Select select;

  public SelectEvent(SelectEvent.Select select) {
    super();

    this.select = select;
  }

  public Select getSelect() {
    return select;
  }

  @Override
  public Type<SelectEvent.StatusChangeHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(SelectEvent.StatusChangeHandler handler) {
    handler.onSelect(this);
  }

  public enum Select {
    SEARCH,
    LIST,
    DETAIL
  }



  public interface StatusChangeHandler {

    void onSelect(SelectEvent event);

  }

}
