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
package com.github.nalukit.nalu.processor.scanner.validation;

import com.github.nalukit.nalu.client.application.IsApplication;
import com.github.nalukit.nalu.client.application.annotation.Application;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

public class ApplicationAnnotationValidator {

  private ProcessorUtils        processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private RoundEnvironment      roundEnvironment;

  @SuppressWarnings("unused")
  private ApplicationAnnotationValidator() {
  }

  private ApplicationAnnotationValidator(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;
    this.roundEnvironment = builder.roundEnvironment;
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
    // get elements annotated with Application annotation
    Set<? extends Element> elementsWithApplicationAnnotation = this.roundEnvironment.getElementsAnnotatedWith(Application.class);
    // at least there should exatly one Application annotation!
    if (elementsWithApplicationAnnotation.size() == 0) {
      throw new ProcessorException("Nalu-Processor: Missing Nalu Application interface");
    }
    // at least there should only one Application annotation!
    if (elementsWithApplicationAnnotation.size() > 1) {
      throw new ProcessorException("Nalu-Processor: There should be at least only one interface, that is annotated with @Application");
    }
    // validate annotation
    for (Element element : elementsWithApplicationAnnotation) {
      Application annotation = element.getAnnotation(Application.class);
      if (annotation.startRoute() == null ||
          annotation.startRoute()
                    .equals("") ||
          annotation.startRoute()
                    .equals("/")) {
        throw new ProcessorException("Nalu-Processor: @Application -> startroute can not be empty and can not be '/'");
      }
    }
  }

  public void validate(Element element)
      throws ProcessorException {
    if (element instanceof TypeElement) {
      TypeElement typeElement = (TypeElement) element;
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
    } else {
      throw new ProcessorException("Nalu-Processor:" + "@Application can only be used on a type (interface)");
    }
  }

  public static final class Builder {

    ProcessingEnvironment processingEnvironment;

    RoundEnvironment      roundEnvironment;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }

    public ApplicationAnnotationValidator build() {
      return new ApplicationAnnotationValidator(this);
    }
  }
}
