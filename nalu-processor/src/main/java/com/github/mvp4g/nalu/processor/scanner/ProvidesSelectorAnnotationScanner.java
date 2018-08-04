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

package com.github.mvp4g.nalu.processor.scanner;

import com.github.mvp4g.nalu.processor.ProcessorException;
import com.github.mvp4g.nalu.processor.ProcessorUtils;
import com.github.mvp4g.nalu.processor.model.ApplicationMetaModel;
import com.github.mvp4g.nalu.processor.model.intern.ClassNameModel;
import com.github.mvp4g.nalu.processor.model.intern.ProvidesSelectorModel;
import com.github.mvp4g.nalu.processor.scanner.validation.ProvidesSelectorAnnotationValidator;
import com.github.mvp4g.nalu.client.ui.annotations.ProvidesSelector;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;

public class ProvidesSelectorAnnotationScanner {

  private ProcessorUtils        processorUtils;
  private ProcessingEnvironment processingEnvironment;
  private TypeElement           applicationTypeElement;
  private ApplicationMetaModel  applicationMetaModel;

  @SuppressWarnings("unused")
  private ProvidesSelectorAnnotationScanner(Builder builder) {
    super();
    this.processingEnvironment = builder.processingEnvironment;
    this.applicationTypeElement = builder.eventBusTypeElement;
    this.applicationMetaModel = builder.applicationMetaModel;
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

  public ApplicationMetaModel scan(RoundEnvironment roundEnvironment)
    throws ProcessorException {
    // handle ProvidesSelector-annotation
    for (Element element : roundEnvironment.getElementsAnnotatedWith(ProvidesSelector.class)) {
      // do validation
      ProvidesSelectorAnnotationValidator.builder()
                                         .roundEnvironment(roundEnvironment)
                                         .processingEnvironment(processingEnvironment)
                                         .providesSecletorElement(element)
                                         .build()
                                         .validate();
      // get Annotation ...
      ProvidesSelector annotation = element.getAnnotation(ProvidesSelector.class);
      // handle ...
      Arrays.stream(annotation.selector())
            .forEach(s -> this.applicationMetaModel.getSelectors()
                                                   .add(new ProvidesSelectorModel(s,
                                                                                  new ClassNameModel(element.toString()))));
    }
    return this.applicationMetaModel;
  }

  public static class Builder {

    ProcessingEnvironment processingEnvironment;
    TypeElement           eventBusTypeElement;
    ApplicationMetaModel  applicationMetaModel;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder eventBusTypeElement(TypeElement eventBusTypeElement) {
      this.eventBusTypeElement = eventBusTypeElement;
      return this;
    }

    public Builder applicationMetaModel(ApplicationMetaModel applicationMetaModel) {
      this.applicationMetaModel = applicationMetaModel;
      return this;
    }

    public ProvidesSelectorAnnotationScanner build() {
      return new ProvidesSelectorAnnotationScanner(this);
    }
  }
}
