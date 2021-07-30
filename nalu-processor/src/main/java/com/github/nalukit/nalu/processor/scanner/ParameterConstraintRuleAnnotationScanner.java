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

package com.github.nalukit.nalu.processor.scanner;

import com.github.nalukit.nalu.client.constraint.annotation.MaxLength;
import com.github.nalukit.nalu.client.constraint.annotation.NotEmpty;
import com.github.nalukit.nalu.client.constraint.annotation.ParameterConstraintRule;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;
import com.github.nalukit.nalu.processor.model.intern.ParameterConstraintRuleModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

public class ParameterConstraintRuleAnnotationScanner {

  private final ProcessingEnvironment processingEnvironment;
  private final MetaModel             metaModel;
  private final Element               parameterConstraintRuleElement;
  private       ProcessorUtils        processorUtils;

  @SuppressWarnings("unused")
  private ParameterConstraintRuleAnnotationScanner(Builder builder) {
    super();
    this.processingEnvironment          = builder.processingEnvironment;
    this.metaModel                      = builder.metaModel;
    this.parameterConstraintRuleElement = builder.parameterConstraintRuleElement;
    setUp();
  }

  private void setUp() {
    this.processorUtils = ProcessorUtils.builder()
                                        .processingEnvironment(this.processingEnvironment)
                                        .build();
  }

  public static Builder builder() {
    return new Builder();
  }

  public ParameterConstraintRuleModel scan(RoundEnvironment roundEnvironment)
      throws ProcessorException {
    return this.handleParameterConstraintRule();
  }

  private ParameterConstraintRuleModel handleParameterConstraintRule()
  //      throws ProcessorException
  {
    // get Annotation ...
    ParameterConstraintRule annotation = parameterConstraintRuleElement.getAnnotation(ParameterConstraintRule.class);

    NotEmpty                notEmptyAnnotation = parameterConstraintRuleElement.getAnnotation(NotEmpty.class);
    boolean notNullCheck = notEmptyAnnotation != null;

    MaxLength maxLengthAnnotation = parameterConstraintRuleElement.getAnnotation(MaxLength.class);
    boolean   maxLengthCheck            = maxLengthAnnotation != null;
    int maxLength = 0;
    if (maxLengthCheck) {
      maxLength = maxLengthAnnotation.value();
    }
    // save model ...
    return new ParameterConstraintRuleModel(new ClassNameModel(parameterConstraintRuleElement.toString()),
                                            notNullCheck,
                                            maxLengthCheck,
                                            maxLength);
  }



  public static class Builder {

    ProcessingEnvironment processingEnvironment;
    MetaModel             metaModel;
    Element               parameterConstraintRuleElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder metaModel(MetaModel metaModel) {
      this.metaModel = metaModel;
      return this;
    }

    public Builder parameterConstraintRuleElement(Element parameterConstraintRuleElement) {
      this.parameterConstraintRuleElement = parameterConstraintRuleElement;
      return this;
    }

    public ParameterConstraintRuleAnnotationScanner build() {
      return new ParameterConstraintRuleAnnotationScanner(this);
    }

  }

}
