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

import com.github.nalukit.nalu.client.tracker.annotation.Tracker;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;
import com.github.nalukit.nalu.processor.model.intern.TrackerModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import java.util.Objects;

import static java.util.Objects.isNull;

public class TrackerAnnotationScanner {

  private final ProcessingEnvironment processingEnvironment;

  private final Element trackerElement;

  @SuppressWarnings("unused")
  private TrackerAnnotationScanner(Builder builder) {
    super();
    this.processingEnvironment = builder.processingEnvironment;
    this.trackerElement        = builder.trackerElement;
    setUp();
  }

  public static Builder builder() {
    return new Builder();
  }

  private void setUp() {
  }

  public TrackerModel scan(RoundEnvironment roundEnvironment)
      throws ProcessorException {
    // handle debug-annotation
    TrackerModel trackerModel = new TrackerModel(new ClassNameModel(this.trackerElement.toString()));
    addTrackerInformation(trackerModel);
    addEventHandlerInformation(trackerModel);

    return trackerModel;
  }

  private void addEventHandlerInformation(TrackerModel trackerModel)
      throws ProcessorException {
    TypeElement trackerClass = getTrackerClass(this.trackerElement.getAnnotation(Tracker.class));
    EventHandlerAnnotationScanner.EventMetaData eventMetaData = EventHandlerAnnotationScanner.builder()
                                                                                             .processingEnvironment(this.processingEnvironment)
                                                                                             .parentElement(trackerClass)
                                                                                             .build()
                                                                                             .scan();

    trackerModel.setEventModels(eventMetaData.getEventModels());
    trackerModel.setEventHandlers(eventMetaData.getEventHandlerModels());
  }

  private TypeElement getTrackerClass(Tracker trackerAnnotation) {
    try {
      trackerAnnotation.value();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  private void addTrackerInformation(TrackerModel trackerModel) {
    Tracker trackerAnnotation = this.trackerElement.getAnnotation(Tracker.class);
    if (!isNull(trackerAnnotation)) {
      trackerModel.setHasTrackerAnnotation(true);
      if (!isNull(getTracker(trackerAnnotation))) {
        TypeElement typeElement = getTracker(trackerAnnotation);
        if (!Objects.isNull(typeElement)) {
          trackerModel.setTracker(new ClassNameModel(typeElement.getQualifiedName()
                                                                .toString()));
        }
      }
    } else {
      trackerModel.setHasTrackerAnnotation(false);
      trackerModel.setTracker(null);
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

  public static class Builder {

    ProcessingEnvironment processingEnvironment;

    Element trackerElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder trackerElement(Element debugElement) {
      this.trackerElement = debugElement;
      return this;
    }

    public TrackerAnnotationScanner build() {
      return new TrackerAnnotationScanner(this);
    }

  }

}
