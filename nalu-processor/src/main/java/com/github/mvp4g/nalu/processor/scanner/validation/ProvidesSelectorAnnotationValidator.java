/*
 * Copyright (c) 2018 - Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *
 */
package com.github.mvp4g.nalu.processor.scanner.validation;

import com.github.mvp4g.nalu.client.ui.IsController;
import com.github.mvp4g.nalu.client.ui.IsShellController;
import com.github.mvp4g.nalu.processor.ProcessorException;
import com.github.mvp4g.nalu.processor.ProcessorUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public class ProvidesSelectorAnnotationValidator {

  private ProcessorUtils        processorUtils;
  private ProcessingEnvironment processingEnvironment;
  private RoundEnvironment      roundEnvironment;
  private Element               providesSecletorElement;

  @SuppressWarnings("unused")
  private ProvidesSelectorAnnotationValidator() {
  }

  private ProvidesSelectorAnnotationValidator(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;
    this.roundEnvironment = builder.roundEnvironment;
    this.providesSecletorElement = builder.providesSecletorElement;
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
    TypeElement typeElement = (TypeElement) this.providesSecletorElement;
    // @ProvidesSelector can only be used on a class
    if (!typeElement.getKind()
                    .isClass()) {
      throw new ProcessorException("@ProvidesSelector can only be used with an class");
    }
    // @ProvidesSelector can only be used on a interface that extends IsApplication
    if (!(this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
                                                     typeElement.asType(),
                                                     this.processingEnvironment.getElementUtils()
                                                                               .getTypeElement(IsController.class.getCanonicalName())
                                                                               .asType()) ||
          this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
                                                      typeElement.asType(),
                                                      this.processingEnvironment.getElementUtils()
                                                                                .getTypeElement(IsShellController.class.getCanonicalName())
                                                                                .asType()))) {
      throw new ProcessorException("@ProvidesSelector can only be used on a class that extends IsController or IsShellController");
    }
  }

  public static final class Builder {

    ProcessingEnvironment processingEnvironment;
    RoundEnvironment      roundEnvironment;
    Element               providesSecletorElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }

    public Builder providesSecletorElement(Element providesSecletorElement) {
      this.providesSecletorElement = providesSecletorElement;
      return this;
    }

    public ProvidesSelectorAnnotationValidator build() {
      return new ProvidesSelectorAnnotationValidator(this);
    }
  }
}
