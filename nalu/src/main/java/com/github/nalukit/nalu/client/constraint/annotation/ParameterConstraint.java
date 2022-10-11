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

package com.github.nalukit.nalu.client.constraint.annotation;

import com.github.nalukit.nalu.client.component.annotation.AcceptParameter;
import com.github.nalukit.nalu.client.constraint.IsParameterConstraintRule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Adds a parameter constraint to a parameter.
 * <br><br>
 * This annotation can only be used on methods
 * that also have a {@link AcceptParameter}-annotation!
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ParameterConstraint {

  /**
   * The constrain used with this parameter.
   * Must extend {@link IsParameterConstraintRule}.
   *
   * @return the constraint for the parameter
   */
  Class<? extends IsParameterConstraintRule> rule();

  /**
   * In case the parameter does not fit the constraint
   * Nalu will use this route.
   *
   * @return Route for illegal paraemter
   */
  String illegalParameterRoute();

}
