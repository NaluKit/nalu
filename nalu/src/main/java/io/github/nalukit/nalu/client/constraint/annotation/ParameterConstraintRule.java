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

package io.github.nalukit.nalu.client.constraint.annotation;

import io.github.nalukit.nalu.client.constraint.IsParameterConstraintRule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to annotate a parameter constrain rule.
 * <br><br>
 * The rules that Nalu will use to validate the parameter are added by
 * using special annoations like:
 * <ul>
 *   <li>TODO add rules for parameter description!</li>
 * </ul>
 * The parameter constrain rule interface needs to extend
 * {@link IsParameterConstraintRule}. Otherwise the processor will
 * create an error
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ParameterConstraintRule {
}
