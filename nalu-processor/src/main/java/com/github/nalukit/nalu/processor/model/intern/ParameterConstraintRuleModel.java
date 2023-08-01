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

public class ParameterConstraintRuleModel {

  private final boolean        notNullCheck;
  private final boolean        minLengthCheck;
  private final int            minLength;
  private final boolean        maxLengthCheck;
  private final int            maxLength;
  private final boolean        patternCheck;
  private final String         pattern;
  private final boolean        blackListingCheck;
  private final String[]       blackList;
  private final boolean        whiteListingCheck;
  private final String[]       whiteList;
  private       ClassNameModel parameterConstraintRule;

  public ParameterConstraintRuleModel(ClassNameModel parameterConstraintRule,
                                      boolean notNullCheck,
                                      boolean minLengthCheck,
                                      int minLength,
                                      boolean maxLengthCheck,
                                      int maxLength,
                                      boolean patternCheck,
                                      String pattern,
                                      boolean blackListingCheck,
                                      String[] blackList,
                                      boolean whiteListingCheck,
                                      String[] whiteList) {
    this.parameterConstraintRule = parameterConstraintRule;
    this.notNullCheck            = notNullCheck;
    this.minLengthCheck          = minLengthCheck;
    this.minLength               = minLength;
    this.maxLengthCheck          = maxLengthCheck;
    this.maxLength               = maxLength;
    this.patternCheck            = patternCheck;
    this.pattern                 = pattern;
    this.blackListingCheck       = blackListingCheck;
    this.blackList               = blackList;
    this.whiteListingCheck       = whiteListingCheck;
    this.whiteList               = whiteList;
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

  public boolean isMinLengthCheck() {
    return minLengthCheck;
  }

  public int getMinLength() {
    return minLength;
  }

  public boolean isMaxLengthCheck() {
    return maxLengthCheck;
  }

  public int getMaxLength() {
    return maxLength;
  }

  public boolean isPatternCheck() {
    return patternCheck;
  }

  public String getPattern() {
    return pattern;
  }

  public boolean isBlackListingCheck() {
    return blackListingCheck;
  }

  public String[] getBlackList() {
    return blackList;
  }

  public boolean isWhiteListingCheck() {
    return whiteListingCheck;
  }

  public String[] getWhiteList() {
    return whiteList;
  }

}
