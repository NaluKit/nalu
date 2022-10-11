/*
 * Copyright (c) 2018 Frank Hossfeld
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
import com.github.nalukit.nalu.client.application.annotation.Application;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public class ApplicationAnnotationValidator {

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private Element applicationElement;

  @SuppressWarnings("unused")
  private ApplicationAnnotationValidator() {
  }

  private ApplicationAnnotationValidator(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;
    this.applicationElement    = builder.applicationElement;
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
    if (this.applicationElement instanceof TypeElement) {
      TypeElement typeElement = (TypeElement) this.applicationElement;
      // annotated element has to be a interface
      if (!typeElement.getKind()
                      .isInterface()) {
        throw new ProcessorException("Nalu-Processor: @Application annotated must be used with an interface");
      }
      // check, that the typeElement implements IsApplication
      if (!this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
                                                       typeElement.asType(),
                                                       this.processingEnvironment.getElementUtils()
                                                                                 .getTypeElement(IsApplication.class.getCanonicalName())
                                                                                 .asType())) {
        throw new ProcessorException("Nalu-Processor: " +
                                     typeElement.getSimpleName()
                                                .toString() +
                                     ": @Application must implement IsApplication interface");
      }
      // check if startRoute start with "/"
      Application applicationAnnotation = applicationElement.getAnnotation(Application.class);
      if (!applicationAnnotation.startRoute()
                                .startsWith("/")) {
        throw new ProcessorException("Nalu-Processor:" + "@Application - startRoute attribute muss begin with a '/'");
      }
    } else {
      throw new ProcessorException("Nalu-Processor:" + "@Application can only be used on a type (interface)");
    }
  }

  public static final class Builder {

    ProcessingEnvironment processingEnvironment;

    Element applicationElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder applicationElement(Element applicationElement) {
      this.applicationElement = applicationElement;
      return this;
    }

    public ApplicationAnnotationValidator build() {
      return new ApplicationAnnotationValidator(this);
    }

  }

}
