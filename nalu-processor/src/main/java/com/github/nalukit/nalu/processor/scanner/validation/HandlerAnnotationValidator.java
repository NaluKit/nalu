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

import com.github.nalukit.nalu.client.handler.AbstractHandler;
import com.github.nalukit.nalu.client.handler.IsHandler;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public class HandlerAnnotationValidator {

  private ProcessorUtils        processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private RoundEnvironment      roundEnvironment;

  private Element               handlerElement;

  @SuppressWarnings("unused")
  private HandlerAnnotationValidator() {
  }

  private HandlerAnnotationValidator(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;
    this.roundEnvironment = builder.roundEnvironment;
    this.handlerElement = builder.handlerElement;
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
    TypeElement typeElement = (TypeElement) this.handlerElement;
    // @ProvidesSelector can only be used on a class
    if (!typeElement.getKind()
                    .isClass()) {
      throw new ProcessorException("Nalu-Processor: @Handler can only be used with an class");
    }
    // @Handler can only be used on a interface that extends IsHandler
    if (!(this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
                                                      typeElement.asType(),
                                                      this.processingEnvironment.getElementUtils()
                                                                                .getTypeElement(IsHandler.class.getCanonicalName())
                                                                                .asType()))) {
      throw new ProcessorException("Nalu-Processor: @Handler can only be used on a class that implements IsHandler");
    }
    // @Handler can only be used on a interface that extends IsApplication
    if (!(this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
                                                      typeElement.asType(),
                                                      this.processingEnvironment.getElementUtils()
                                                                                .getTypeElement(AbstractHandler.class.getCanonicalName())
                                                                                .asType()))) {
      throw new ProcessorException("Nalu-Processor: @Handler can only be used on a class that extends AbstractHandler");
    }
  }

  public static final class Builder {

    ProcessingEnvironment processingEnvironment;

    RoundEnvironment      roundEnvironment;

    Element               handlerElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }

    public Builder handlerElement(Element handlerElement) {
      this.handlerElement = handlerElement;
      return this;
    }

    public HandlerAnnotationValidator build() {
      return new HandlerAnnotationValidator(this);
    }
  }
}
