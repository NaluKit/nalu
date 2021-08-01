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

import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

public interface IsPopUpComponent<C extends IsPopUpComponent.Controller>
    extends IsCommonComponent<C> {

  /**
   * Call this method to show the component
   */
  void show();

  /**
   * Call this method to hide the component
   */
  void hide();

  /**
   * Is called to remove binding.
   *
   * Note:
   * The method is called by teh framework. Don't call it!
   */
  @NaluInternalUse
  void removeHandlers();

  interface Controller
      extends IsCommonComponent.Controller {

  }

}
