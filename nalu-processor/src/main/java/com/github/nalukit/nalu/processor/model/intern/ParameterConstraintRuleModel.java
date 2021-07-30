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

package com.github.nalukit.nalu.processor.model.intern;

public class ParameterConstraintRuleModel {

  private ClassNameModel parameterConstraintRule;
  private boolean        notNullCheck;
  private boolean        maxLengthCheck;
  private int            maxLength;
  private boolean        patternCheck;
  private String         pattern;

  public ParameterConstraintRuleModel(ClassNameModel parameterConstraintRule,
                                      boolean notNullCheck,
                                      boolean maxLengthCheck,
                                      int maxLength,
                                      boolean patternCheck,
                                      String pattern) {
    this.parameterConstraintRule = parameterConstraintRule;
    this.notNullCheck            = notNullCheck;
    this.maxLengthCheck          = maxLengthCheck;
    this.maxLength               = maxLength;
    this.patternCheck            = patternCheck;
    this.pattern                 = pattern;
  }

  public ClassNameModel getParameterConstraintRule() {
    return parameterConstraintRule;
  }

  public void setParameterConstraintRule(ClassNameModel parameterConstraintRule) {
    this.parameterConstraintRule = parameterConstraintRule;
  }

  public boolean isNotNullCheck() {
    return notNullCheck;
  }

  public void setNotNullCheck(boolean notNullCheck) {
    this.notNullCheck = notNullCheck;
  }

  public boolean isMaxLengthCheck() {
    return maxLengthCheck;
  }

  public void setMaxLengthCheck(boolean maxLengthCheck) {
    this.maxLengthCheck = maxLengthCheck;
  }

  public int getMaxLength() {
    return maxLength;
  }

  public void setMaxLength(int maxLength) {
    this.maxLength = maxLength;
  }

  public boolean isPatternCheck() {
    return patternCheck;
  }

  public void setPatternCheck(boolean patternCheck) {
    this.patternCheck = patternCheck;
  }

  public String getPattern() {
    return pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }
}
