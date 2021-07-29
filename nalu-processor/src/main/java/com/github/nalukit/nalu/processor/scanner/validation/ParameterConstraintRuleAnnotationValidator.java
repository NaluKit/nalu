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
package com.github.nalukit.nalu.processor.scanner.validation;

import com.github.nalukit.nalu.client.constrain.IsParameterConstraintRule;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public class ParameterConstraintRuleAnnotationValidator {

  private ProcessorUtils        processorUtils;
  private ProcessingEnvironment processingEnvironment;
  private Element               parameterConstraintRuleElement;

  @SuppressWarnings("unused")
  private ParameterConstraintRuleAnnotationValidator() {
  }

  private ParameterConstraintRuleAnnotationValidator(Builder builder) {
    this.processingEnvironment          = builder.processingEnvironment;
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

  public void validate()
      throws ProcessorException {
    TypeElement typeElement = (TypeElement) this.parameterConstraintRuleElement;
    // @ParameterConstraintRule can only be used with an interface
    if (!typeElement.getKind()
                    .isInterface()) {
      throw new ProcessorException("Nalu-Processor: @ParameterConstraintRule can only be used with an interface");
    }
    // @ParameterConstraintRule can only be used on an interface that extends IsParameterConstraintRule
    if (!this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
                                                     typeElement.asType(),
                                                     this.processingEnvironment.getElementUtils()
                                                                               .getTypeElement(IsParameterConstraintRule.class.getCanonicalName())
                                                                               .asType())) {
      throw new ProcessorException("Nalu-Processor: @ParameterConstraintRule can only be used on an interface that extends IsParameterConstraintRule");
    }
  }

  public static final class Builder {

    ProcessingEnvironment processingEnvironment;
    RoundEnvironment      roundEnvironment;
    Element               parameterConstraintRuleElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }

    public Builder parameterConstraintRuleElement(Element parameterConstraintRuleElement) {
      this.parameterConstraintRuleElement = parameterConstraintRuleElement;
      return this;
    }

    public ParameterConstraintRuleAnnotationValidator build() {
      return new ParameterConstraintRuleAnnotationValidator(this);
    }

  }

}
