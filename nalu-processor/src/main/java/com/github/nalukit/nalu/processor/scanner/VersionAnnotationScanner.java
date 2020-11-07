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

package com.github.nalukit.nalu.processor.scanner;

import com.github.nalukit.nalu.client.application.annotation.Version;
import com.github.nalukit.nalu.processor.model.MetaModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;

import static java.util.Objects.isNull;

public class VersionAnnotationScanner {
  
  private ProcessingEnvironment processingEnvironment;
  
  private Element versionElement;
  
  private MetaModel metaModel;
  
  @SuppressWarnings("unused")
  private VersionAnnotationScanner(Builder builder) {
    super();
    this.processingEnvironment = builder.processingEnvironment;
    this.versionElement        = builder.versionElement;
    this.metaModel             = builder.metaModel;
    setUp();
  }
  
  private void setUp() {
  }
  
  public static Builder builder() {
    return new Builder();
  }
  
  public MetaModel scan(RoundEnvironment roundEnvironment) {
    // handle debug-annotation
    Version versionAnnotation = versionElement.getAnnotation(Version.class);
    if (isNull(versionAnnotation)) {
      this.metaModel.setApplicationVersion("APPLICATION-VERSION-NOT-AVAILABLE");
    } else {
      this.metaModel.setApplicationVersion(versionAnnotation.value());
    }
    return this.metaModel;
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
  
  public static class Builder {
    
    ProcessingEnvironment processingEnvironment;
    
    Element versionElement;
    
    MetaModel metaModel;
    
    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }
    
    public Builder versionElement(Element debugElement) {
      this.versionElement = debugElement;
      return this;
    }
    
    public Builder metaModel(MetaModel metaModel) {
      this.metaModel = metaModel;
      return this;
    }
    
    public VersionAnnotationScanner build() {
      return new VersionAnnotationScanner(this);
    }
    
  }
  
}
