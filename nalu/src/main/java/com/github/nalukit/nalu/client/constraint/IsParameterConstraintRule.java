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

package com.github.nalukit.nalu.client.constraint;

import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import com.github.nalukit.nalu.client.internal.constrain.ParameterConstraintRuleFactory;

/**
 * Marks an interface as parameter constrain.
 */
public interface IsParameterConstraintRule {

  /**
   * Returns the key of the parameter constraint rule
   * for accessing the instance of the rule from
   * the {@link ParameterConstraintRuleFactory}
   *
   * @return key of parameter constraint rule
   */
  @NaluInternalUse
  String key();

  /**
   * Gets called from Nalu when a constraint for a parameter ist requested.
   *
   * @param parameter parameter to check
   * @return result of the validation
   */
  @NaluInternalUse
  boolean isValid(String parameter);

}
