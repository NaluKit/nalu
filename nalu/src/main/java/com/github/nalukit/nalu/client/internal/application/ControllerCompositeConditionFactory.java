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

import com.github.nalukit.nalu.client.component.IsLoadCompositeCondition;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@NaluInternalUse
public class ControllerCompositeConditionFactory {

  /* instance of the controller factory */
  private static ControllerCompositeConditionFactory   instance;
  /* map of conditions (key: controller name, value: ConditionContainer)  */
  private final  Map<String, List<ConditionContainer>> conditionContainerMap;

  private ControllerCompositeConditionFactory() {
    this.conditionContainerMap = new HashMap<>();
  }

  public static ControllerCompositeConditionFactory get() {
    if (instance == null) {
      instance = new ControllerCompositeConditionFactory();
    }
    return instance;
  }

  public void registerCondition(String controllerClassName,
                                String compositeName,
                                IsLoadCompositeCondition condition) {
    if (this.conditionContainerMap.containsKey(controllerClassName)) {
      this.conditionContainerMap.get(controllerClassName)
                                .add(new ConditionContainer(compositeName,
                                                            condition));
    } else {
      this.conditionContainerMap.computeIfAbsent(controllerClassName,
                                                 v -> new ArrayList<>())
                                .add(new ConditionContainer(compositeName,
                                                            condition));
    }
  }

  /**
   * Will tell Nalu if the composite can be loaded or not!
   *
   * @param controllerClassName name of the controller containing the composites
   * @param compositeName       name of the composite which condition is requested
   * @param route               the route
   * @param params              parameter (0 .. n)
   * @return true: load composite; false:  do not load composite
   */
  public boolean loadComposite(String controllerClassName,
                               String compositeName,
                               String route,
                               String... params) {
    List<ConditionContainer> conditionContainers = this.conditionContainerMap.get(controllerClassName);
    if (Objects.isNull(conditionContainers)) {
      return false;
    }
    for (ConditionContainer conditionContainer : conditionContainers) {
      if (conditionContainer.compositeName.equals(compositeName)) {
        return conditionContainer.condition.loadComposite(route,
                                                          params);
      }
    }
    return false;
  }

  static class ConditionContainer {

    private final String                   compositeName;
    private final IsLoadCompositeCondition condition;

    ConditionContainer(String compositeName,
                       IsLoadCompositeCondition condition) {
      this.compositeName = compositeName;
      this.condition     = condition;
    }

  }

}
