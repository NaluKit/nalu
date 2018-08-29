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

import com.github.mvp4g.nalu.client.component.IsController;
import com.github.mvp4g.nalu.client.component.IsShellController;
import com.github.mvp4g.nalu.client.component.annotation.Controller;
import com.github.mvp4g.nalu.processor.ProcessorException;
import com.github.mvp4g.nalu.processor.ProcessorUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RouteAnnotationValidator {

  private ProcessorUtils        processorUtils;
  private ProcessingEnvironment processingEnvironment;
  private RoundEnvironment      roundEnvironment;
  private Element               controllerElement;

  @SuppressWarnings("unused")
  private RouteAnnotationValidator() {
  }

  private RouteAnnotationValidator(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;
    this.roundEnvironment = builder.roundEnvironment;
    this.controllerElement = builder.controllerElement;
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
    TypeElement typeElement = (TypeElement) this.controllerElement;
    // @ProvidesSelector can only be used on a class
    if (!typeElement.getKind()
                    .isClass()) {
      throw new ProcessorException("Nalu-Processor: @Controller can only be used with an class");
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
                                                                                .asType())
    )) {
      throw new ProcessorException("Nalu-Processor: @Controller can only be used on a class that extends IsController or IsShellController");
    }
    // check if all parameter have a setter method inside of the controller
    Controller annotation = this.controllerElement.getAnnotation(Controller.class);
    List<String> parameters = getParameterFromRoute(this.controllerElement.getAnnotation(Controller.class)
                                                                          .route());
    if (parameters.size() > 0) {
      for (String parameter : parameters) {
        String methodName = this.processorUtils.createSetMethodName(parameter);
        Optional<? extends Element> optional = this.processingEnvironment.getElementUtils()
                                                                         .getAllMembers((TypeElement) this.controllerElement)
                                                                         .stream()
                                                                         .filter(element -> ElementKind.METHOD.equals(((Element) element).getKind()))
                                                                         .map(element -> (ExecutableElement) element)
                                                                         .filter(f -> f.toString()
                                                                                       .contains(methodName))
                                                                         .findFirst();
        if (optional.isPresent()) {
          ExecutableElement executableElement = (ExecutableElement) optional.get();
          if (executableElement.getParameters()
                               .size() != 1) {
            throw new ProcessorException("Nalu-Processor: @Controller >>" + this.controllerElement.toString() + "<< and method: >>" + methodName + "<< number of arguments is wrong");
          } else {
            if (!String.class.getCanonicalName()
                             .equals(executableElement.getParameters()
                                                      .get(0)
                                                      .asType()
                                                      .toString())) {
              throw new ProcessorException("Nalu-Processor: @Controller >>" + this.controllerElement.toString() + "<< and method: >>" + methodName + "<< needs a String parameter");
            }
          }
        } else {
          throw new ProcessorException("Nalu-Processor: @Controller >>" + this.controllerElement.toString() + "<< does not implement the method: >>" + methodName + "<< which is required for accepting a variable from the route");
        }
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
    Element               controllerElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }

    public Builder controllerElement(Element controllerElement) {
      this.controllerElement = controllerElement;
      return this;
    }

    public RouteAnnotationValidator build() {
      return new RouteAnnotationValidator(this);
    }
  }
}
