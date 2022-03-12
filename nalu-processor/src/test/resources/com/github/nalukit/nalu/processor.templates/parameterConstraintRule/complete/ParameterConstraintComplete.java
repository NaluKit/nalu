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

package com.github.nalukit.nalu.processor.parameterConstraintRule.complete;

import com.github.nalukit.nalu.client.constraint.annotation.*;
import com.github.nalukit.nalu.client.constraint.IsParameterConstraintRule;

import javax.validation.constraints.NotNull;

@ParameterConstraintRule
@NotEmpty
@MinLength(2)
@MaxLength(12)
@Pattern("^[A-Z]?$")
@BlackListing({ "A", "B", "C", "D", "E", "F" })
@WhiteListing({ "X", "Y", "Z" })
public interface ParameterConstraintComplete
    extends IsParameterConstraintRule {
}
