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

package com.github.nalukit.nalu.processor.scanner;

import com.github.nalukit.nalu.client.component.annotation.Composite;
import com.github.nalukit.nalu.client.component.annotation.Composite.Scope;
import com.github.nalukit.nalu.client.component.annotation.Composites;
import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;
import com.github.nalukit.nalu.processor.model.intern.ShellAndControllerCompositeModel;
import com.github.nalukit.nalu.processor.model.intern.ControllerModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import java.util.Objects;

public class ControllerCompositesAnnotationScanner {

  private ProcessingEnvironment processingEnvironment;

  private ControllerModel controllerModel;

  private Element controllerElement;

  @SuppressWarnings("unused")
  private ControllerCompositesAnnotationScanner(Builder builder) {
    super();
    this.processingEnvironment = builder.processingEnvironment;
    this.controllerModel       = builder.controllerModel;
    this.controllerElement     = builder.controllerElement;
    setUp();
  }

  public static Builder builder() {
    return new Builder();
  }

  private void setUp() {
  }

  @SuppressWarnings("unused")
  public ControllerModel scan(RoundEnvironment roundEnvironment) {
    Composites annotation = this.controllerElement.getAnnotation(Composites.class);
    if (annotation != null) {
      for (Composite composite : annotation.value()) {
        controllerModel.getComposites()
                       .add(new ShellAndControllerCompositeModel(composite.name(),
                                                                 new ClassNameModel(Objects.requireNonNull(getCompositeTypeElement(composite))
                                                                                   .toString()),
                                                                 composite.selector(),
                                                                 new ClassNameModel(Objects.requireNonNull(getCompositeConditionElement(composite))
                                                                                   .toString()),
                                                         Scope.GLOBAL == composite.scope()));
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

  private TypeElement getCompositeConditionElement(Composite annotation) {
    try {
      annotation.condition();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  public static class Builder {

    ProcessingEnvironment processingEnvironment;

    ControllerModel controllerModel;

    Element controllerElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder controllerModel(ControllerModel controllerModel) {
      this.controllerModel = controllerModel;
      return this;
    }

    public Builder controllerElement(Element controllerElement) {
      this.controllerElement = controllerElement;
      return this;
    }

    public ControllerCompositesAnnotationScanner build() {
      return new ControllerCompositesAnnotationScanner(this);
    }

  }

}
