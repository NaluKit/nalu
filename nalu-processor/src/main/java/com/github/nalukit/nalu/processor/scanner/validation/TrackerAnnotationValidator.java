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

import com.github.nalukit.nalu.client.application.IsApplication;
import com.github.nalukit.nalu.client.application.annotation.Application;
import com.github.nalukit.nalu.client.tracker.AbstractTracker;
import com.github.nalukit.nalu.client.tracker.annotation.Tracker;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import java.util.Objects;
import java.util.Set;

public class TrackerAnnotationValidator {

  private Element trackerElement;

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private RoundEnvironment roundEnvironment;

  @SuppressWarnings("unused")
  private TrackerAnnotationValidator() {
  }

  private TrackerAnnotationValidator(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;
    this.roundEnvironment = builder.roundEnvironment;
    this.trackerElement = builder.trackerElement;
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
    // get elements annotated with Debug annotation
    Set<? extends Element> elementsWithTrackerAnnotation = this.roundEnvironment.getElementsAnnotatedWith(Tracker.class);
    // at least there should only one Application annotation!
    if (elementsWithTrackerAnnotation.size() > 1) {
      throw new ProcessorException("Nalu-Processor: There should be at least only one interface, that is annotated with @Tracker");
    }
    for (Element element : elementsWithTrackerAnnotation) {
      if (element instanceof TypeElement) {
        TypeElement typeElement = (TypeElement) element;
        // @Tracker can only be used on a interface
        if (!trackerElement.getKind()
                           .isInterface()) {
          throw new ProcessorException("Nalu-Processor: @Tracker can only be used on a type (interface)");
        }
        // @Tracker can only be used on a interface that extends IsApplication
        if (!this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
                                                         trackerElement.asType(),
                                                         this.processingEnvironment.getElementUtils()
                                                                                   .getTypeElement(IsApplication.class.getCanonicalName())
                                                                                   .asType())) {
          throw new ProcessorException("Nalu-Processor: @Tracker can only be used on interfaces that extends IsApplication");
        }
        // @Tracker can only be used on a interface that has a @Application annotation
        if (trackerElement.getAnnotation(Application.class) == null) {
          throw new ProcessorException("Nalu-Processor: @Tracker can only be used with an interfaces annotated with @Annotation");
        }
        // get the value of the tracker annotation
        TypeElement trackerClassTypeElement = this.getTracker(typeElement.getAnnotation(Tracker.class));
        if (!Objects.isNull(trackerClassTypeElement)) {
          if (!this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
                                                           trackerClassTypeElement.asType(),
                                                           this.processingEnvironment.getElementUtils()
                                                                                     .getTypeElement(AbstractTracker.class.getCanonicalName())
                                                                                     .asType())) {
            throw new ProcessorException("Nalu-Processor: value of @Tracker annotation needs to extends AbstractTracker<C>");
          }
        } else {
          throw new ProcessorException("Nalu-Processor: @Tracker needs a value of type .class which extends AbstractTracker");
        }
      } else {
        throw new ProcessorException("Nalu-Processor: @Tracker can only be used on a type (interface)");
      }
    }
  }

  private TypeElement getTracker(Tracker trackerAnnotation) {
    try {
      trackerAnnotation.value();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  public static final class Builder {

    ProcessingEnvironment processingEnvironment;

    RoundEnvironment roundEnvironment;

    Element trackerElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }

    public Builder trackerElement(Element debugElement) {
      this.trackerElement = debugElement;
      return this;
    }

    public TrackerAnnotationValidator build() {
      return new TrackerAnnotationValidator(this);
    }

  }

}
