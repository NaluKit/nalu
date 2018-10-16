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

import com.github.nalukit.nalu.client.component.annotation.AcceptParameter;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;
import com.github.nalukit.nalu.processor.model.intern.ControllerModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AcceptParameterAnnotationValidator {

  List<Element> annotatedElements;

  private ProcessorUtils        processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private RoundEnvironment      roundEnvironment;

  private ControllerModel       controllerModel;

  @SuppressWarnings("unused")
  private AcceptParameterAnnotationValidator() {
  }

  private AcceptParameterAnnotationValidator(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;
    this.roundEnvironment = builder.roundEnvironment;
    this.annotatedElements = builder.annotatedElements;
    this.controllerModel = builder.controllerModel;
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
    for (Element annotatedElement : this.annotatedElements) {
      // @AcceptParameter can only be used on a method
      if (!ElementKind.METHOD.equals(annotatedElement.getKind())) {
        throw new ProcessorException("Nalu-Processor: @AcceptParameter can only be used with a method");
      }
      ExecutableElement executableElement = (ExecutableElement) annotatedElement;
      if (executableElement.getParameters()
                           .size() != 1) {
        throw new ProcessorException("Nalu-Processor: @AcceptParameter can only be used with a method that has one parameter");
      }
      if (!executableElement.getParameters()
                            .get(0)
                            .asType()
                            .toString()
                            .equals("java.lang.String")) {
        throw new ProcessorException("Nalu-Processor: @AcceptParameter can only be used with a method that has one parameter and the parameter type is String");
      }
      AcceptParameter annotation = annotatedElement.getAnnotation(AcceptParameter.class);
      if (!this.controllerModel.getParameters()
                               .contains(annotation.value())) {
        throw new ProcessorException("Nalu-Processor: @AcceptParameter refering a variable which is not in the controller's route");
      }
    }
  }

  private List<String> getParameterFromRoute(String route) {
    if (!route.contains("/:")) {
      return new ArrayList<>();
    }
    return Arrays.asList(route.substring(route.indexOf("/:") + 2)
                              .split("/:"));
  }

  public static final class Builder {

    ProcessingEnvironment processingEnvironment;

    RoundEnvironment      roundEnvironment;

    List<Element>         annotatedElements;

    ControllerModel       controllerModel;

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

    public AcceptParameterAnnotationValidator build() {
      return new AcceptParameterAnnotationValidator(this);
    }
  }
}
