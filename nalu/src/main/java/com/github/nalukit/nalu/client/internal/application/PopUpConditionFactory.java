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

package com.github.nalukit.nalu.client.internal.application;

import com.github.nalukit.nalu.client.component.IsShowPopUpCondition;
import com.github.nalukit.nalu.client.component.event.ShowPopUpEvent;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@NaluInternalUse
public class PopUpConditionFactory {

  /* instance of the controller factory */
  public static PopUpConditionFactory             INSTANCE = new PopUpConditionFactory();
  /* map of popup condition */
  private final Map<String, IsShowPopUpCondition> conditionMap;

  private PopUpConditionFactory() {
    this.conditionMap = new HashMap<>();
  }

  public void registerCondition(String popUpName,
                                IsShowPopUpCondition condition) {
    if (!this.conditionMap.containsKey(popUpName)) {
      this.conditionMap.put(popUpName,
                            condition);
    }
  }

  /**
   * Will tell Nalu if the popup event can be fired or not!
   *
   * @param event the ShowPopUpEvent
   * @return true: show popup; false:  show popup
   */
  public boolean showPopUp(ShowPopUpEvent event) {
    IsShowPopUpCondition condition = this.conditionMap.get(event.getName());
    if (Objects.isNull(condition)) {
      return false;
    }
    return condition.showPopUp(event);
  }

}
