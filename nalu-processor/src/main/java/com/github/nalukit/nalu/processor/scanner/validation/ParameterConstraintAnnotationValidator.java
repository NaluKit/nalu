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
import com.github.nalukit.nalu.processor.model.intern.ControllerModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import java.util.List;

public class ParameterConstraintAnnotationValidator {

  List<Element> annotatedElements;

  @SuppressWarnings("unused")
  private ParameterConstraintAnnotationValidator() {
  }

  private ParameterConstraintAnnotationValidator(Builder builder) {
    this.annotatedElements = builder.annotatedElements;
    setUp();
  }

  public static Builder builder() {
    return new Builder();
  }

  private void setUp() {
  }

  public void validate()
      throws ProcessorException {
    // Tests are currently done during ControllerValidation
  }

  public static final class Builder {

    ProcessingEnvironment processingEnvironment;

    RoundEnvironment roundEnvironment;

    List<Element> annotatedElements;

    ControllerModel controllerModel;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }

    public Builder listOfAnnotatedElements(List<Element> annotatedElements) {
      this.annotatedElements = annotatedElements;
      return this;
    }

    public Builder controllerModel(ControllerModel controllerModel) {
      this.controllerModel = controllerModel;
      return this;
    }

    public ParameterConstraintAnnotationValidator build() {
      return new ParameterConstraintAnnotationValidator(this);
    }

  }

}
