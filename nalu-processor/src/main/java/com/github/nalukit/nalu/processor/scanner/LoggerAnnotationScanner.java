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

import com.github.nalukit.nalu.client.application.annotation.Logger;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import java.util.Objects;

import static java.util.Objects.isNull;

public class LoggerAnnotationScanner {

  private ProcessingEnvironment processingEnvironment;

  private Element loggerElement;

  private MetaModel metaModel;

  @SuppressWarnings("unused")
  private LoggerAnnotationScanner(Builder builder) {
    super();
    this.processingEnvironment = builder.processingEnvironment;
    this.loggerElement         = builder.loggerElement;
    this.metaModel             = builder.metaModel;
    setUp();
  }

  public static Builder builder() {
    return new Builder();
  }

  private void setUp() {
  }

  public MetaModel scan(RoundEnvironment roundEnvironment) {
    // handle debug-annotation
    Logger loggerAnnotation = loggerElement.getAnnotation(Logger.class);
    if (!isNull(loggerAnnotation)) {
      if (!isNull(getLogger(loggerAnnotation))) {
        TypeElement loggerElement = getLogger(loggerAnnotation);
        if (!Objects.isNull(loggerElement)) {
          this.metaModel.setLogger(new ClassNameModel(loggerElement.getQualifiedName()
                                                                   .toString()));
        }
        TypeElement clientLoggerElement = getClientLogger(loggerAnnotation);
        if (!Objects.isNull(clientLoggerElement)) {
          this.metaModel.setClientLogger(new ClassNameModel(clientLoggerElement.getQualifiedName()
                                                                               .toString()));
        }
      }
    } else {
      this.metaModel.setLogger(null);
    }
    return this.metaModel;
  }

  private TypeElement getLogger(Logger loggerAnnotation) {
    try {
      loggerAnnotation.logger();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  private TypeElement getClientLogger(Logger loggerAnnotation) {
    try {
      loggerAnnotation.clientLogger();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  public static class Builder {

    ProcessingEnvironment processingEnvironment;

    Element loggerElement;

    MetaModel metaModel;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder loggerElement(Element loggerElement) {
      this.loggerElement = loggerElement;
      return this;
    }

    public Builder metaModel(MetaModel metaModel) {
      this.metaModel = metaModel;
      return this;
    }

    public LoggerAnnotationScanner build() {
      return new LoggerAnnotationScanner(this);
    }

  }

}
