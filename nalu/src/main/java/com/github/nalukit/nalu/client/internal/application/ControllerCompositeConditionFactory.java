/*
 * Copyright (c) 2018 - 2019 - Frank Hossfeld
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
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

import java.util.*;

@NaluInternalUse
public class ControllerCompositeConditionFactory {

  /* instance of the controller factory */
  private static ControllerCompositeConditionFactory instance;

  /* map of conditions (key: controller name, value: ConditionContainer)  */
  private Map<String, List<ConditionContainer>> conditionContainerMap;

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
   * @return true -> load composite; false -> do not load composite
   */
  public boolean loadComposite(String controllerClassName,
                               String compositeName,
                               String route,
                               String... parms) {
    StringBuilder sb;
    List<ConditionContainer> conditionContainers = this.conditionContainerMap.get(controllerClassName);
    if (Objects.isNull(conditionContainers)) {
      sb = new StringBuilder();
      sb.append("ControllerCompositeConditionFactory: composite condition not found for controller class name >>")
        .append(controllerClassName)
        .append("<< and composite >>")
        .append(compositeName)
        .append("<<");
      ClientLogger.get()
                  .logSimple(sb.toString(),
                             5);
      return false;
    }
    for (ConditionContainer conditionContainer : conditionContainers) {
      if (conditionContainer.compositeName.equals(compositeName)) {
        boolean toLoad = conditionContainer.condition.loadComposite(route,
                                                                    parms);
        sb = new StringBuilder();
        if (toLoad) {
          sb.append("ControllerCompositeConditionFactory: composite condition for controller class name >>")
            .append(controllerClassName)
            .append("<< and composite >>")
            .append(compositeName)
            .append("<< will not interrupt loading the composite");
        } else {
          sb.append("ControllerCompositeConditionFactory: composite condition for controller class name >>")
            .append(controllerClassName)
            .append("<< and composite >>")
            .append(compositeName)
            .append("<< will abort loading the composite");
        }
        ClientLogger.get()
                    .logSimple(sb.toString(),
                               5);
        return toLoad;
      }
    }
    sb = new StringBuilder();
    sb.append("ControllerCompositeConditionFactory: list of composite condition for controller class name >>")
      .append(controllerClassName)
      .append("<< and composite >>")
      .append(compositeName)
      .append("<< does have a entry for this composite!");
    ClientLogger.get()
                .logSimple(sb.toString(),
                           5);
    return false;
  }

  class ConditionContainer {

    private String                   compositeName;
    private IsLoadCompositeCondition condition;

    ConditionContainer(String compositeName,
                       IsLoadCompositeCondition condition) {
      this.compositeName = compositeName;
      this.condition = condition;
    }

  }

}
