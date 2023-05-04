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

package com.github.nalukit.nalu.processor.model.intern;

public class ParameterAcceptorModel {

  private String                   parameterName;
  private String                   methodName;
  private ParameterConstraintModel parameterConstrait;

  public ParameterAcceptorModel(String parameterName,
                                String methodName,
                                ParameterConstraintModel parameterConstrait) {
    this.parameterName      = parameterName;
    this.methodName         = methodName;
    this.parameterConstrait = parameterConstrait;
  }

  public String getParameterName() {
    return parameterName;
  }

  public String getMethodName() {
    return methodName;
  }

  public ParameterConstraintModel getParameterConstrait() {
    return parameterConstrait;
  }
}
