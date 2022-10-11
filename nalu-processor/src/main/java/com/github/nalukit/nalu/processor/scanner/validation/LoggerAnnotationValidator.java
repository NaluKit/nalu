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
import com.github.nalukit.nalu.client.application.IsLogger;
import com.github.nalukit.nalu.client.application.annotation.Application;
import com.github.nalukit.nalu.client.application.annotation.Logger;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import java.util.Objects;
import java.util.Set;

public class LoggerAnnotationValidator {

  private Element loggerElement;

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private RoundEnvironment roundEnvironment;

  @SuppressWarnings("unused")
  private LoggerAnnotationValidator() {
  }

  private LoggerAnnotationValidator(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;
    this.roundEnvironment      = builder.roundEnvironment;
    this.loggerElement         = builder.loggerElement;
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
    // get elements annotated with Logger annotation
    Set<? extends Element> elementsWithLoggerAnnotation = this.roundEnvironment.getElementsAnnotatedWith(Logger.class);
    // at least there should only one Application annotation!
    if (elementsWithLoggerAnnotation.size() > 1) {
      throw new ProcessorException("Nalu-Processor: There should be at least only one interface, that is annotated with @Logger");
    }
    for (Element element : elementsWithLoggerAnnotation) {
      if (element instanceof TypeElement) {
        // @Logger can only be used on a interface
        if (!loggerElement.getKind()
                          .isInterface()) {
          throw new ProcessorException("Nalu-Processor: @Logger can only be used on a type (interface)");
        }
        // @Logger can only be used on a interface that extends IsApplication
        if (!this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
                                                         loggerElement.asType(),
                                                         this.processingEnvironment.getElementUtils()
                                                                                   .getTypeElement(IsApplication.class.getCanonicalName())
                                                                                   .asType())) {
          throw new ProcessorException("Nalu-Processor: @Logger can only be used on interfaces that extends IsApplication");
        }
        // @Loggewr can only be used on a interface that has a @Application annotation
        if (loggerElement.getAnnotation(Application.class) == null) {
          throw new ProcessorException("Nalu-Processor: @Logger can only be used with an interfaces annotated with @Application");
        }
        // the logger inside the annotation must extends IsNaluLogger!
        TypeElement loggerElement = this.getLogger(this.loggerElement.getAnnotation(Logger.class));
        if (!this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
                                                         Objects.requireNonNull(loggerElement)
                                                                .asType(),
                                                         this.processingEnvironment.getElementUtils()
                                                                                   .getTypeElement(IsLogger.class.getCanonicalName())
                                                                                   .asType())) {
          throw new ProcessorException("Nalu-Processor: @Logger - the logger attribute needs class that extends IsLogger");
        }
      } else {
        throw new ProcessorException("Nalu-Processor: @Logger can only be used on a type (interface)");
      }
    }
  }

  private TypeElement getLogger(Logger loggerAnnotation) {
    try {
      loggerAnnotation.logger();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  public static final class Builder {

    ProcessingEnvironment processingEnvironment;

    RoundEnvironment roundEnvironment;

    Element loggerElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }

    public Builder loggerElement(Element loggerElement) {
      this.loggerElement = loggerElement;
      return this;
    }

    public LoggerAnnotationValidator build() {
      return new LoggerAnnotationValidator(this);
    }

  }

}
