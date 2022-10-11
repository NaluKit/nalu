/*
 * Copyright (c) 2018 Frank Hossfeld
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

public interface IsPopUpController<V> {

  @NaluInternalUse
  void setComponent(V component);

  /**
   * will be called one time, when a popup gets created.
   *
   * @param finishLoadCommand needs to be executed to give the control back to Nalu
   */
  void bind(FinishLoadCommand finishLoadCommand);

  /**
   * Will be called before a popup gets visible
   *
   * @param finishLoadCommand needs to be executed to give the control back to Nalu
   */
  void onBeforeShow(FinishLoadCommand finishLoadCommand);

  /**
   * sow hte popup
   */
  void show();

  @FunctionalInterface
  interface FinishLoadCommand {

    void finishLoading();

  }

}
