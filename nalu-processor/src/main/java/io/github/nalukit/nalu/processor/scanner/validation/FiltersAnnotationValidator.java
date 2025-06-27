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
package io.github.nalukit.nalu.processor.scanner.validation;

import io.github.nalukit.nalu.processor.ProcessorException;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

public class FiltersAnnotationValidator {

  @SuppressWarnings("unused")
  private FiltersAnnotationValidator() {
  }

  @SuppressWarnings("unused")
  private FiltersAnnotationValidator(Builder builder) {

    setUp();
  }

  public static Builder builder() {
    return new Builder();
  }

  private void setUp() {
  }

  public void validate(Element filterElement)
      throws ProcessorException {
    // get elements annotated with EventBus annotation
    //    Set<? extends Element> elementsWithFiltersAnnotation = this.roundEnvironment.getElementsAnnotatedWith(Filters.class);
    //    // at least there should only one Application annotation!
    //    if (elementsWithFiltersAnnotation.size() > 1) {
    //      throw new ProcessorException("Nalu-Processor: There should be at least only one interface, that is annotated with @Filters");
    //    }
    //    // annotated element has to be a interface
    //    for (Element element : elementsWithFiltersAnnotation) {
    //      if (element instanceof TypeElement) {
    //        TypeElement typeElement = (TypeElement) element;
    if (!filterElement.getKind()
                      .isInterface()) {
      throw new ProcessorException("Nalu-Processor: @Filters can only be used on a type (interface)");
    }
    //        // @Filter can only be used on a interface that extends IsEventBus
    //        if (!this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
    //                                                         typeElement.asType(),
    //                                                         this.processingEnvironment.getElementUtils()
    //                                                                                   .getTypeElement(IsApplication.class.getCanonicalName())
    //                                                                                   .asType())) {
    //          throw new ProcessorException("Nalu-Processor: @Filters can only be used on interfaces that extends IsApplication");
    //        }
    //        // test, that all filterClasses implement IsEventFilter!
    //        List<String> filtersAsStringList = this.getFilterClassesAsList(this.applicationTypeElement);
    //        for (String eventFilterClassname : filtersAsStringList) {
    //          TypeElement filterElement = this.processingEnvironment.getElementUtils()
    //                                                                .getTypeElement(eventFilterClassname);
    //          if (!this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
    //                                                           filterElement.asType(),
    //                                                           this.processingEnvironment.getElementUtils()
    //                                                                                     .getTypeElement(IsFilter.class.getCanonicalName())
    //                                                                                     .asType())) {
    //            throw new ProcessorException("Nalu-Processor: @Filters - the filterClasses attribute needs classes that implements IsFilter");
    //          }
    //        }
    //      } else {
    //        throw new ProcessorException("Nalu-Processor: @Filters can only be used on a type (interface)");
    //      }
    //    }
  }

  //  private List<String> getFilterClassesAsList(TypeElement typeElement) {
  //    Element filtersAnnotation = this.processingEnvironment.getElementUtils()
  //                                                          .getTypeElement(Filters.class.getName());
  //    TypeMirror filtersAnnotationAsTypeMirror = filtersAnnotation.asType();
  //    return typeElement.getAnnotationMirrors()
  //                      .stream()
  //                      .filter(annotationMirror -> annotationMirror.getAnnotationType()
  //                                                                  .equals(filtersAnnotationAsTypeMirror))
  //                      .flatMap(annotationMirror -> annotationMirror.getElementValues()
  //                                                                   .entrySet()
  //                                                                   .stream())
  //                      .findFirst().<List<String>>map(entry -> Arrays.stream(entry.getValue()
  //                                                                                 .toString()
  //                                                                                 .replace("{",
  //                                                                                          "")
  //                                                                                 .replace("}",
  //                                                                                          "")
  //                                                                                 .replace(" ",
  //                                                                                          "")
  //                                                                                 .split(","))
  //                                                                    .map((v) -> v.substring(0,
  //                                                                                            v.indexOf(".class")))
  //                                                                    .collect(Collectors.toList())).orElse(new ArrayList<>());
  //  }



  public static final class Builder {

    ProcessingEnvironment processingEnvironment;

    RoundEnvironment roundEnvironment;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }

    public FiltersAnnotationValidator build() {
      return new FiltersAnnotationValidator(this);
    }

  }

}
