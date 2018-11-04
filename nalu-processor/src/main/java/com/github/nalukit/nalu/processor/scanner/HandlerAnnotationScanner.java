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

import com.github.nalukit.nalu.client.handler.annotation.Handler;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.model.ApplicationMetaModel;
import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;
import com.github.nalukit.nalu.processor.scanner.validation.HandlerAnnotationValidator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

public class HandlerAnnotationScanner {

  private ProcessingEnvironment processingEnvironment;

  private RoundEnvironment roundEnvironment;

  private ApplicationMetaModel applicationMetaModel;

  @SuppressWarnings("unused")
  private HandlerAnnotationScanner(Builder builder) {
    super();
    this.processingEnvironment = builder.processingEnvironment;
    this.roundEnvironment = builder.roundEnvironment;
    this.applicationMetaModel = builder.applicationMetaModel;
    setUp();
  }

  public static Builder builder() {
    return new Builder();
  }

  private void setUp() {
  }

  public ApplicationMetaModel scan()
      throws ProcessorException {
    // handle ProvidesSelector-annotation
    for (Element element : roundEnvironment.getElementsAnnotatedWith(Handler.class)) {
      // do validation
      HandlerAnnotationValidator.builder()
                                .roundEnvironment(roundEnvironment)
                                .processingEnvironment(processingEnvironment)
                                .handlerElement(element)
                                .build()
                                .validate();
      // save handler class in meta model
      this.applicationMetaModel.getHandlers()
                               .add(new ClassNameModel(element.toString()));
    }
    return this.applicationMetaModel;
  }

  public static class Builder {

    ProcessingEnvironment processingEnvironment;

    RoundEnvironment roundEnvironment;

    ApplicationMetaModel applicationMetaModel;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder applicationMetaModel(ApplicationMetaModel applicationMetaModel) {
      this.applicationMetaModel = applicationMetaModel;
      return this;
    }

    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }

    public HandlerAnnotationScanner build() {
      return new HandlerAnnotationScanner(this);
    }
  }
}
