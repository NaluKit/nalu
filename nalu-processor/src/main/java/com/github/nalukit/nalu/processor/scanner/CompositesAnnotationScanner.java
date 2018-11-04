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

package com.github.nalukit.nalu.processor.scanner;

import com.github.nalukit.nalu.client.component.annotation.Composite;
import com.github.nalukit.nalu.client.component.annotation.Composites;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;
import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;
import com.github.nalukit.nalu.processor.model.intern.ControllerModel;
import com.github.nalukit.nalu.processor.model.intern.ControllerCompositeModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompositesAnnotationScanner {

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private ControllerModel controllerModel;

  private TypeElement controllerTypeElement;

  @SuppressWarnings("unused")
  private CompositesAnnotationScanner(Builder builder) {
    super();
    this.processingEnvironment = builder.processingEnvironment;
    this.controllerModel = builder.controllerModel;
    this.controllerTypeElement = builder.controllerTypeElement;
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

  ControllerModel scan(RoundEnvironment roundEnvironment)
      throws ProcessorException {
    Composites annotation = this.controllerTypeElement.getAnnotation(Composites.class);
    if (annotation != null) {
      for (Composite composite : annotation.value()) {
        controllerModel.getComposites()
                       .add(new ControllerCompositeModel(composite.name(),
                                                         new ClassNameModel(getCompositeTypeElement(composite).toString()),
                                                         composite.selector()));
      }
    }
    return this.controllerModel;
  }

  private TypeElement getCompositeTypeElement(Composite annotation) {
    try {
      annotation.compositeController();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

//  private String[] getParameters(String parameters) {
//    return Stream.of(parameters.split("/:"))
//                 .filter(p -> !"".equals(p))
//                 .toArray(String[]::new);
//  }

  public static class Builder {

    ProcessingEnvironment processingEnvironment;

    ControllerModel controllerModel;

    TypeElement controllerTypeElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder controllerModel(ControllerModel controllerModel) {
      this.controllerModel = controllerModel;
      return this;
    }

    public Builder controllerTypeElement(TypeElement controllerTypeElement) {
      this.controllerTypeElement = controllerTypeElement;
      return this;
    }

    public CompositesAnnotationScanner build() {
      return new CompositesAnnotationScanner(this);
    }
  }
}
