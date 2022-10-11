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

import com.github.nalukit.nalu.client.component.IsBlockComponentController;
import com.github.nalukit.nalu.client.component.annotation.BlockController;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public class BlockControllerAnnotationValidator {

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private Element blockControllerElement;

  @SuppressWarnings("unused")
  private BlockControllerAnnotationValidator() {
  }

  private BlockControllerAnnotationValidator(Builder builder) {
    this.processingEnvironment  = builder.processingEnvironment;
    this.blockControllerElement = builder.blockControllerElement;
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
    TypeElement typeElement = (TypeElement) this.blockControllerElement;
    // @BlockController can only be used on a class
    if (!typeElement.getKind()
                    .isClass()) {
      throw new ProcessorException("Nalu-Processor: @BlockController can only be used with an class");
    }
    // @BlockController can only be used on a interface that extends IsBlockController
    if (!this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
                                                     typeElement.asType(),
                                                     this.processingEnvironment.getElementUtils()
                                                                               .getTypeElement(IsBlockComponentController.class.getCanonicalName())
                                                                               .asType())) {
      throw new ProcessorException("Nalu-Processor: @BlockController can only be used on a class that extends IsBlockComponentController");
    }
    // check if name is not empty
    BlockController blockControllerAnnotation = blockControllerElement.getAnnotation(BlockController.class);
    if (blockControllerAnnotation.name()
                                 .trim()
                                 .isEmpty()) {
      throw new ProcessorException("Nalu-Processor: @BlockController - name attribute should not be empty");
    }
  }

  public static final class Builder {

    ProcessingEnvironment processingEnvironment;

    RoundEnvironment roundEnvironment;

    Element blockControllerElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }

    public Builder blockControllerElement(Element blockControllerElement) {
      this.blockControllerElement = blockControllerElement;
      return this;
    }

    public BlockControllerAnnotationValidator build() {
      return new BlockControllerAnnotationValidator(this);
    }

  }

}
