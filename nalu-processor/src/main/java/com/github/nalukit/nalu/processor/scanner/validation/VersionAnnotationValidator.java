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
import com.github.nalukit.nalu.client.application.annotation.Version;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import java.util.Set;

public class VersionAnnotationValidator {
  
  private Element versionElement;
  
  private ProcessorUtils processorUtils;
  
  private ProcessingEnvironment processingEnvironment;
  
  private RoundEnvironment roundEnvironment;
  
  @SuppressWarnings("unused")
  private VersionAnnotationValidator() {
  }
  
  private VersionAnnotationValidator(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;
    this.roundEnvironment      = builder.roundEnvironment;
    this.versionElement        = builder.versionElement;
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
  
  public void validate()
      throws ProcessorException {
    // get elements annotated with Debug annotation
    Set<? extends Element> elementsWithVersionAnnotation = this.roundEnvironment.getElementsAnnotatedWith(Version.class);
    // at least there should only one Application annotation!
    if (elementsWithVersionAnnotation.size() > 1) {
      throw new ProcessorException("Nalu-Processor: There should be at least only one interface, that is annotated with @Version");
    }
    for (Element element : elementsWithVersionAnnotation) {
      if (element instanceof TypeElement) {
        TypeElement typeElement = (TypeElement) element;
        // @Version can only be used on a interface
        if (!versionElement.getKind()
                           .isInterface()) {
          throw new ProcessorException("Nalu-Processor: @Version can only be used on a type (interface)");
        }
        // @Version can only be used on a interface that extends IsApplication
        if (!this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
                                                         versionElement.asType(),
                                                         this.processingEnvironment.getElementUtils()
                                                                                   .getTypeElement(IsApplication.class.getCanonicalName())
                                                                                   .asType())) {
          throw new ProcessorException("Nalu-Processor: @Version can only be used on interfaces that extends IsApplication");
        }
        // @Version can only be used on a interface that has a @Application annotation
        if (versionElement.getAnnotation(Application.class) == null) {
          throw new ProcessorException("Nalu-Processor: @Version can only be used with an interfaces annotated with @Annotation");
        }
      } else {
        throw new ProcessorException("Nalu-Processor: @Version can only be used on a type (interface)");
      }
    }
  }
  
  private TypeElement getVersion(Version versionAnnotation) {
    try {
      versionAnnotation.value();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }
  
  public static final class Builder {
    
    ProcessingEnvironment processingEnvironment;
    
    RoundEnvironment roundEnvironment;
    
    Element versionElement;
    
    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }
    
    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }
    
    public Builder versionElement(Element debugElement) {
      this.versionElement = debugElement;
      return this;
    }
    
    public VersionAnnotationValidator build() {
      return new VersionAnnotationValidator(this);
    }
    
  }
  
}
