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

import com.github.nalukit.nalu.client.component.IsPopUpController;
import com.github.nalukit.nalu.client.component.annotation.PopUpController;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public class PopUpControllerAnnotationValidator {

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private Element popUpControllerElement;

  @SuppressWarnings("unused")
  private PopUpControllerAnnotationValidator() {
  }

  private PopUpControllerAnnotationValidator(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;
    this.popUpControllerElement = builder.popUpControllerElement;
    setUp();
  }

  public static Builder builder() {
    return new Builder();
  }

  private void setUp() {
    this.processorUtils = ProcessorUtils.builder()
                                        .processingEnvironment(this.processingEnvironment)
                                        .build();
  }

  public void validate()
      throws ProcessorException {
    TypeElement typeElement = (TypeElement) this.popUpControllerElement;
    // @PopUpController can only be used on a class
    if (!typeElement.getKind()
                    .isClass()) {
      throw new ProcessorException("Nalu-Processor: @PopUpController can only be used with an class");
    }
    // @PopUpController can only be used on a interface that extends IsApplication
    if (!this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
                                                     typeElement.asType(),
                                                     this.processingEnvironment.getElementUtils()
                                                                               .getTypeElement(IsPopUpController.class.getCanonicalName())
                                                                               .asType())) {
      throw new ProcessorException("Nalu-Processor: @PopUpController can only be used on a class that extends IsPopUpController");
    }
    // check if name is not empty
    PopUpController popUpControllerAnnotation = popUpControllerElement.getAnnotation(PopUpController.class);
    if (popUpControllerAnnotation.name()
                                 .trim()
                                 .isEmpty()) {
      throw new ProcessorException("Nalu-Processor: @PopUpController - name attribute should not be empty");
    }
  }

  public static final class Builder {

    ProcessingEnvironment processingEnvironment;

    RoundEnvironment roundEnvironment;

    Element popUpControllerElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }

    public Builder popUpControllerElement(Element popUpControllerElement) {
      this.popUpControllerElement = popUpControllerElement;
      return this;
    }

    public PopUpControllerAnnotationValidator build() {
      return new PopUpControllerAnnotationValidator(this);
    }

  }

}
