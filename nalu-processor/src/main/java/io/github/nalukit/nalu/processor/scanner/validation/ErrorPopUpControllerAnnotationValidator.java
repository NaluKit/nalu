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
package io.github.nalukit.nalu.processor.scanner.validation;

import io.github.nalukit.nalu.client.component.AbstractErrorPopUpComponentController;
import io.github.nalukit.nalu.client.component.IsErrorPopUpController;
import io.github.nalukit.nalu.processor.ProcessorException;
import io.github.nalukit.nalu.processor.ProcessorUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public class ErrorPopUpControllerAnnotationValidator {

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private Element errorPopUpControllerElement;

  @SuppressWarnings("unused")
  private ErrorPopUpControllerAnnotationValidator() {
  }

  private ErrorPopUpControllerAnnotationValidator(Builder builder) {
    this.processingEnvironment       = builder.processingEnvironment;
    this.errorPopUpControllerElement = builder.errorPopUpControllerElement;
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
    TypeElement typeElement = (TypeElement) this.errorPopUpControllerElement;
    // @ErrorPopUpController can only be used on a class
    if (!typeElement.getKind()
                    .isClass()) {
      throw new ProcessorException("Nalu-Processor: @ErrorPopUpController can only be used with an class");
    }
    // @ErrorPopUpController can only be used on a interface that extends IsErrorPopUpController
    if (!this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
                                                     typeElement.asType(),
                                                     this.processingEnvironment.getElementUtils()
                                                                               .getTypeElement(IsErrorPopUpController.class.getCanonicalName())
                                                                               .asType())) {
      throw new ProcessorException("Nalu-Processor: @ErrorPopUpController can only be used on a class that implements IsErrorPopUpController");
    }
    // @ErrorPopUpController can only be used on a interface that extends AbstractErrorPopUpComponentController
    if (!this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
                                                     typeElement.asType(),
                                                     this.processingEnvironment.getElementUtils()
                                                                               .getTypeElement(AbstractErrorPopUpComponentController.class.getCanonicalName())
                                                                               .asType())) {
      throw new ProcessorException("Nalu-Processor: @ErrorPopUpController can only be used on a class that extends AbstractErrorPopUpComponentController");
    }
  }

  public static final class Builder {

    ProcessingEnvironment processingEnvironment;

    RoundEnvironment roundEnvironment;

    Element errorPopUpControllerElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }

    public Builder errorPopUpControllerElement(Element errorPopUpControllerElement) {
      this.errorPopUpControllerElement = errorPopUpControllerElement;
      return this;
    }

    public ErrorPopUpControllerAnnotationValidator build() {
      return new ErrorPopUpControllerAnnotationValidator(this);
    }

  }

}
