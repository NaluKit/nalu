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

package com.github.nalukit.nalu.processor.scanner;

import com.github.nalukit.nalu.client.constraint.annotation.BlackListing;
import com.github.nalukit.nalu.client.constraint.annotation.MaxLength;
import com.github.nalukit.nalu.client.constraint.annotation.MinLength;
import com.github.nalukit.nalu.client.constraint.annotation.NotEmpty;
import com.github.nalukit.nalu.client.constraint.annotation.Pattern;
import com.github.nalukit.nalu.client.constraint.annotation.WhiteListing;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;
import com.github.nalukit.nalu.processor.model.intern.ParameterConstraintRuleModel;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

public class ParameterConstraintRuleAnnotationScanner {

  private final Element parameterConstraintRuleElement;

  @SuppressWarnings("unused")
  private ParameterConstraintRuleAnnotationScanner(Builder builder) {
    super();
    this.parameterConstraintRuleElement = builder.parameterConstraintRuleElement;
    setUp();
  }

  public static Builder builder() {
    return new Builder();
  }

  private void setUp() {
  }

  public ParameterConstraintRuleModel scan(RoundEnvironment roundEnvironment)
      throws ProcessorException {
    return this.handleParameterConstraintRule();
  }

  private ParameterConstraintRuleModel handleParameterConstraintRule() {
    NotEmpty notEmptyAnnotation = parameterConstraintRuleElement.getAnnotation(NotEmpty.class);
    boolean  notNullCheck       = notEmptyAnnotation != null;

    MinLength minLengthAnnotation = parameterConstraintRuleElement.getAnnotation(MinLength.class);
    boolean   minLengthCheck      = minLengthAnnotation != null;
    int       minLength           = 0;
    if (minLengthCheck) {
      minLength = minLengthAnnotation.value();
    }

    MaxLength maxLengthAnnotation = parameterConstraintRuleElement.getAnnotation(MaxLength.class);
    boolean   maxLengthCheck      = maxLengthAnnotation != null;
    int       maxLength           = 0;
    if (maxLengthCheck) {
      maxLength = maxLengthAnnotation.value();
    }

    Pattern patternAnnotation = parameterConstraintRuleElement.getAnnotation(Pattern.class);
    boolean patternCheck      = patternAnnotation != null;
    String  pattern           = "";
    if (patternCheck) {
      pattern = patternAnnotation.value();
    }

    BlackListing blackListing      = parameterConstraintRuleElement.getAnnotation(BlackListing.class);
    boolean      blackListingCheck = blackListing != null;
    String[]     blackList         = new String[] {};
    if (blackListingCheck) {
      blackList = blackListing.value();
    }

    WhiteListing whiteListing      = parameterConstraintRuleElement.getAnnotation(WhiteListing.class);
    boolean      whiteListingCheck = whiteListing != null;
    String[]     whiteList         = new String[] {};
    if (whiteListingCheck) {
      whiteList = whiteListing.value();
    }

    return new ParameterConstraintRuleModel(new ClassNameModel(parameterConstraintRuleElement.toString()),
                                            notNullCheck,
                                            minLengthCheck,
                                            minLength,
                                            maxLengthCheck,
                                            maxLength,
                                            patternCheck,
                                            pattern,
                                            blackListingCheck,
                                            blackList,
                                            whiteListingCheck,
                                            whiteList);
  }

  public static class Builder {

    Element parameterConstraintRuleElement;

    public Builder parameterConstraintRuleElement(Element parameterConstraintRuleElement) {
      this.parameterConstraintRuleElement = parameterConstraintRuleElement;
      return this;
    }

    public ParameterConstraintRuleAnnotationScanner build() {
      return new ParameterConstraintRuleAnnotationScanner(this);
    }

  }

}
