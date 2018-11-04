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

import com.github.nalukit.nalu.client.component.IsController;
import com.github.nalukit.nalu.client.component.IsShell;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControllerAnnotationValidator {

  private ProcessorUtils        processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private RoundEnvironment      roundEnvironment;

  private Element               controllerElement;

  @SuppressWarnings("unused")
  private ControllerAnnotationValidator() {
  }

  private ControllerAnnotationValidator(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;
    this.roundEnvironment = builder.roundEnvironment;
    this.controllerElement = builder.controllerElement;
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
                                                                                    .getTypeElement(IsShell.class.getCanonicalName())
                                                                                    .asType()))) {
      throw new ProcessorException("Nalu-Processor: @Controller can only be used on a class that extends IsController or IsShell");
    }
    // validate route
    validateRoute();
  }

  private void validateRoute()
      throws ProcessorException {
    Controller controllerAnnotation = this.controllerElement.getAnnotation(Controller.class);
    String route = controllerAnnotation.route();
    // extract route first:
    if (route.startsWith("/")) {
      route = route.substring(1);
    }
    // initial routes do not need a validation!
    if (route.length() == 0) {
      return;
    }
    boolean handlingParameter = false;
    String[] splits = route.split("/");
    for (String s : splits) {
      // handle "//" -> not allowed
      if (s.length() == 0) {
        throw new ProcessorException("Nalu-Processor: controller >>" +
                                         this.controllerElement.getEnclosingElement()
                                                               .toString() +
                                         "<<  - illegal route >>" +
                                         route +
                                         "<< -> '//' not allowed!");
      }
      // check if it is a parameter definition (starting with ':' at first position)
      if (s.startsWith(":")) {
        handlingParameter = true;
        // starts with a parameter ==> error
        if (route.length() == 0) {
          throw new ProcessorException("Nalu-Processor: controller >>" +
                                           this.controllerElement.getEnclosingElement()
                                                                 .toString() +
                                           "<<  - illegal route >>" +
                                           route +
                                           "<< -> route cannot start with parameter");
        }
        if (s.length() == 1) {
          throw new ProcessorException("Nalu-Processor: controller >>" +
                                           this.controllerElement.getEnclosingElement()
                                                                 .toString() +
                                           "<<  - illegal route >>" +
                                           route +
                                           "<< -> illegal parameter name!");
        }
      } else {
        if (handlingParameter) {
          throw new ProcessorException("Nalu-Processor: controller >>" +
                                           this.controllerElement.getEnclosingElement()
                                                                 .toString() +
                                           "<<  - illegal route >>" +
                                           route +
                                           "<< -> illegal route!");
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

    public ControllerAnnotationValidator build() {
      return new ControllerAnnotationValidator(this);
    }
  }
}
