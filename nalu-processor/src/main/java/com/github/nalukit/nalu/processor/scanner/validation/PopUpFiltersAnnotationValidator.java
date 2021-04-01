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

import com.github.nalukit.nalu.processor.ProcessorException;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

public class PopUpFiltersAnnotationValidator {

  @SuppressWarnings("unused")
  private PopUpFiltersAnnotationValidator() {
  }

  @SuppressWarnings("unused")
  private PopUpFiltersAnnotationValidator(Builder builder) {

    setUp();
  }

  private void setUp() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public void validate(Element popFilterElement)
      throws ProcessorException {
    if (!popFilterElement.getKind()
                         .isInterface()) {
      throw new ProcessorException("Nalu-Processor: @PopUpFilters can only be used on a type (interface)");
    }
  }

  public static final class Builder {

    ProcessingEnvironment processingEnvironment;

    RoundEnvironment roundEnvironment;

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
