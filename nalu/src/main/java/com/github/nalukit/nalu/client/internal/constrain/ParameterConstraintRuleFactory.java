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

package com.github.nalukit.nalu.client.internal.constrain;

import com.github.nalukit.nalu.client.constraint.IsParameterConstraintRule;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

import java.util.HashMap;
import java.util.Map;

@NaluInternalUse
public class ParameterConstraintRuleFactory {

  /* instance of the controller factory */
  public static ParameterConstraintRuleFactory         INSTANCE = new ParameterConstraintRuleFactory();
  /* map of parameter constraint rules (key: key for parameter constraint rule, value: parameter constraint rule instance)  */
  private final Map<String, IsParameterConstraintRule> parameterConstraintRuleMap;

  private ParameterConstraintRuleFactory() {
    this.parameterConstraintRuleMap = new HashMap<>();
  }

  public static ParameterConstraintRuleFactory get() {
    if (INSTANCE == null) {
      INSTANCE = new ParameterConstraintRuleFactory();
    }
    return INSTANCE;
  }

  public void registerParameterConstraintRule(String key,
                                              IsParameterConstraintRule parameterConstraintRule) {
    if (!this.parameterConstraintRuleMap.containsKey(key)) {
      this.parameterConstraintRuleMap.put(key,
                                          parameterConstraintRule);
    }
  }

  public IsParameterConstraintRule get(String key) {
    return this.parameterConstraintRuleMap.get(key);
  }

}
