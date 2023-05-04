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

import com.github.nalukit.nalu.client.application.annotation.Filters;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;
import com.github.nalukit.nalu.processor.model.intern.FilterModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FiltersAnnotationScanner {

  private ProcessingEnvironment processingEnvironment;

  private TypeElement filtersElement;

  @SuppressWarnings("unused")
  private FiltersAnnotationScanner(Builder builder) {
    super();
    this.processingEnvironment = builder.processingEnvironment;
    this.filtersElement        = (TypeElement) builder.filtersElement;
    setUp();
  }

  public static Builder builder() {
    return new Builder();
  }

  private void setUp() {
  }

  public List<FilterModel> scan(RoundEnvironment roundEnvironment)
      throws ProcessorException {
    List<FilterModel> filters = this.getFiltersAsList()
                                    .stream()
                                    .map(ClassNameModel::new)
                                    .map(FilterModel::new)
                                    .collect(Collectors.toList());

    for (FilterModel filter : filters) {
      TypeElement typeElement = this.processingEnvironment.getElementUtils()
                                                          .getTypeElement(filter.getFilter()
                                                                                .getClassName());
      EventHandlerAnnotationScanner.EventMetaData eventMetaData = EventHandlerAnnotationScanner.builder()
                                                                                               .processingEnvironment(this.processingEnvironment)
                                                                                               .parentElement(typeElement)
                                                                                               .build()
                                                                                               .scan();

      filter.setEventHandlers(eventMetaData.getEventHandlerModels());
      filter.setEventModels(eventMetaData.getEventModels());
    }

    return filters;
  }

  private List<String> getFiltersAsList() {
    Element filterAnnotation = this.processingEnvironment.getElementUtils()
                                                         .getTypeElement(Filters.class.getName());
    TypeMirror filterAnnotationAsTypeMirror = filterAnnotation.asType();
    return this.filtersElement.getAnnotationMirrors()
                              .stream()
                              .filter(annotationMirror -> this.processingEnvironment.getTypeUtils()
                                                                                    .isSameType(annotationMirror.getAnnotationType(),
                                                                                                filterAnnotationAsTypeMirror))
                              .flatMap(annotationMirror -> annotationMirror.getElementValues()
                                                                           .entrySet()
                                                                           .stream())
                              .findFirst()
                              .<List<String>>map(entry -> Arrays.stream(entry.getValue()
                                                                             .toString()
                                                                             .replace("{",
                                                                                      "")
                                                                             .replace("}",
                                                                                      "")
                                                                             .replace(" ",
                                                                                      "")
                                                                             .split(","))
                                                                .map((v) -> v.substring(0,
                                                                                        v.indexOf(".class")))
                                                                .collect(Collectors.toList()))
                              .orElse(null);
  }

  public static class Builder {

    MetaModel metaModel;

    ProcessingEnvironment processingEnvironment;

    RoundEnvironment roundEnvironment;

    Element filtersElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }

    public Builder metaModel(MetaModel metaModel) {
      this.metaModel = metaModel;
      return this;
    }

    public Builder filtersElement(Element filtersElement) {
      this.filtersElement = filtersElement;
      return this;
    }

    public FiltersAnnotationScanner build() {
      return new FiltersAnnotationScanner(this);
    }

  }

}
