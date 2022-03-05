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

import com.github.nalukit.nalu.client.application.IsApplication;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public class PopUpFiltersAnnotationValidator {

  private ProcessorUtils        processorUtils;
  private ProcessingEnvironment processingEnvironment;
  private Element               popUpFilterElement;

  @SuppressWarnings("unused")
  private PopUpFiltersAnnotationValidator() {
  }

  @SuppressWarnings("unused")
  private PopUpFiltersAnnotationValidator(Builder builder) {
    this.popUpFilterElement    = builder.popUpFilterElement;
    this.processingEnvironment = builder.processingEnvironment;
    this.processorUtils        = ProcessorUtils.builder()
                                               .processingEnvironment(processingEnvironment)
                                               .build();
    ;

    setUp();
  }

  public static Builder builder() {
    return new Builder();
  }

  private void setUp() {
  }

  public void validate(Element popFilterElement)
      throws ProcessorException {
    if (!popFilterElement.getKind()
                         .isInterface()) {
      throw new ProcessorException("Nalu-Processor: @PopUpFilters can only be used on a type (interface)");
    }
    TypeElement typeElement = (TypeElement) this.popUpFilterElement;
    // @PopUpController can only be used on a class
    if (!typeElement.getKind()
                    .isInterface()) {
      throw new ProcessorException("Nalu-Processor: @PopUpController can only be used with an interface");
    }
    // @PopUpController can only be used on a interface that extends IsApplication
    if (!this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
                                                     typeElement.asType(),
                                                     this.processingEnvironment.getElementUtils()
                                                                               .getTypeElement(IsApplication.class.getCanonicalName())
                                                                               .asType())) {
      throw new ProcessorException("Nalu-Processor: @PopUpController can only be used on a class that extends IsApplication");
    }
  }

  public static final class Builder {

    ProcessingEnvironment processingEnvironment;
    RoundEnvironment      roundEnvironment;
    Element               popUpFilterElement;

    public Builder popUpFilterElement(Element popUpFilterrElement) {
      this.popUpFilterElement = popUpFilterrElement;
      return this;
    }

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }

    public PopUpFiltersAnnotationValidator build() {
      return new PopUpFiltersAnnotationValidator(this);
    }

  }

}
