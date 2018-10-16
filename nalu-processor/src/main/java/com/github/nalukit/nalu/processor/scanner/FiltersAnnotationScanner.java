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
import com.github.nalukit.nalu.processor.ProcessorUtils;
import com.github.nalukit.nalu.processor.model.ApplicationMetaModel;
import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;
import com.github.nalukit.nalu.processor.scanner.validation.FiltersAnnotationValidator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class FiltersAnnotationScanner {

  private ProcessorUtils        processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private RoundEnvironment      roundEnvironment;

  private ApplicationMetaModel  applicationMetaModel;

  private TypeElement           applicationTypeElement;

  @SuppressWarnings("unused")
  private FiltersAnnotationScanner(Builder builder) {
    super();
    this.processingEnvironment = builder.processingEnvironment;
    this.roundEnvironment = builder.roundEnvironment;
    this.applicationMetaModel = builder.applicationMetaModel;
    this.applicationTypeElement = builder.applicationTypeElement;
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

  ApplicationMetaModel scan(RoundEnvironment roundEnvironment)
      throws ProcessorException {
    // do validation
    FiltersAnnotationValidator.builder()
                              .roundEnvironment(roundEnvironment)
                              .processingEnvironment(processingEnvironment)
                              .applicationTypeElement(this.applicationTypeElement)
                              .build()
                              .validate();
    // get the Filters annotation
    Filters filtersAnotation = this.applicationTypeElement.getAnnotation(Filters.class);
    if (isNull(filtersAnotation)) {
      this.applicationMetaModel.setHasFiltersAnnotation("false");
    } else {
      this.applicationMetaModel.setHasFiltersAnnotation("true");

      this.applicationMetaModel.setFilters(this.getFiltersAsList()
                                               .stream()
                                               .map(ClassNameModel::new)
                                               .collect(Collectors.toList()));
    }
    return this.applicationMetaModel;
  }

  private List<String> getFiltersAsList() {
    Element filterAnnotation = this.processingEnvironment.getElementUtils()
                                                         .getTypeElement(Filters.class.getName());
    TypeMirror filterAnnotationAsTypeMirror = filterAnnotation.asType();
    return this.applicationTypeElement.getAnnotationMirrors()
                                      .stream()
                                      .filter(annotationMirror -> annotationMirror.getAnnotationType()
                                                                                  .equals(filterAnnotationAsTypeMirror))
                                      .flatMap(annotationMirror -> annotationMirror.getElementValues()
                                                                                   .entrySet()
                                                                                   .stream())
                                      .findFirst().<List<String>>map(entry -> Arrays.stream(entry.getValue()
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
                                                                                    .collect(Collectors.toList())).orElse(null);
  }

  public static class Builder {

    ApplicationMetaModel  applicationMetaModel;

    ProcessingEnvironment processingEnvironment;

    RoundEnvironment      roundEnvironment;

    TypeElement           applicationTypeElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }

    public Builder applicationMetaModel(ApplicationMetaModel applicationMetaModel) {
      this.applicationMetaModel = applicationMetaModel;
      return this;
    }

    public Builder applicationTypeElement(TypeElement applicationTypeElement) {
      this.applicationTypeElement = applicationTypeElement;
      return this;
    }

    public FiltersAnnotationScanner build() {
      return new FiltersAnnotationScanner(this);
    }
  }
}
