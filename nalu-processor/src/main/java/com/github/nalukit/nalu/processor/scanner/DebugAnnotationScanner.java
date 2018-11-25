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

import com.github.nalukit.nalu.client.application.annotation.Debug;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;

import static java.util.Objects.isNull;

public class DebugAnnotationScanner {

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private Element debugElement;

  private MetaModel metaModel;

  @SuppressWarnings("unused")
  private DebugAnnotationScanner(Builder builder) {
    super();
    this.processingEnvironment = builder.processingEnvironment;
    this.debugElement = builder.debugElement;
    this.metaModel = builder.metaModel;
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

  public MetaModel scan(RoundEnvironment roundEnvironment)
      throws ProcessorException {
    // handle debug-annotation
    Debug debugAnnotation = debugElement.getAnnotation(Debug.class);
    if (!isNull(debugAnnotation)) {
      this.metaModel.setHavingDebugAnnotation(true);
      this.metaModel.setDebugLogLevel(debugAnnotation.logLevel()
                                                     .toString());
      if (!isNull(getLogger(debugAnnotation))) {
        this.metaModel.setDebugLogger(new ClassNameModel(getLogger(debugAnnotation).getQualifiedName()
                                                                                   .toString()));
      }
    } else {
      this.metaModel.setHavingDebugAnnotation(false);
      this.metaModel.setDebugLogLevel("");
      this.metaModel.setDebugLogger(null);
    }
    return this.metaModel;
  }

  private TypeElement getLogger(Debug debugAnnotation) {
    try {
      debugAnnotation.logger();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  public static class Builder {

    ProcessingEnvironment processingEnvironment;

    Element debugElement;

    MetaModel metaModel;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder debugElement(Element debugElement) {
      this.debugElement = debugElement;
      return this;
    }

    public Builder metaModel(MetaModel metaModel) {
      this.metaModel = metaModel;
      return this;
    }

    public DebugAnnotationScanner build() {
      return new DebugAnnotationScanner(this);
    }
  }
}
