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

package com.github.nalukit.nalu.client.component;

import com.github.nalukit.nalu.client.component.event.HideBlockComponentEvent;
import com.github.nalukit.nalu.client.component.event.ShowBlockComponentEvent;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

public interface IsBlockComponentController<V> {

  void setComponent(V component);

  /**
   * gets the name of the block controller
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   *
   * @return the name of the block controller
   */
  @NaluInternalUse
  String getName();

  /**
   * The method is called before the show-method.
   * A good place to do some preapration, if needed.
   * <p>
   * If you want to do something before you get the
   * control, just override the method.
   *
   * @param event the show event - can be used to get parameters
   */
  void onBeforeShow(ShowBlockComponentEvent event);

  /**
   * The method is called before the hide-method.
   * A good place to do some clean up.
   * <p>
   * If you want to do some cblean up before you get the
   * control, just override the method.
   *
   * @param event the hide event - can be used to get parameters
   */
  void onBeforeHide(HideBlockComponentEvent event);

  /**
   * Add the element to the root element
   */
  void append();

}
