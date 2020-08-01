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

import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

public class HandlerAnnotationScanner {
  
  private Element handlerElement;
  
  @SuppressWarnings("unused")
  private HandlerAnnotationScanner(Builder builder) {
    super();
    this.handlerElement = builder.handlerElement;
    setUp();
  }
  
  private void setUp() {
  }
  
  public static Builder builder() {
    return new Builder();
  }
  
  public ClassNameModel scan()
      throws ProcessorException {
    return new ClassNameModel(handlerElement.toString());
  }
  
  public static class Builder {
    
    ProcessingEnvironment processingEnvironment;
    
    RoundEnvironment roundEnvironment;
    
    Element handlerElement;
    
    MetaModel metaModel;
    
    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }
    
    public Builder metaModel(MetaModel metaModel) {
      this.metaModel = metaModel;
      return this;
    }
    
    public Builder handlerElement(Element handlerElement) {
      this.handlerElement = handlerElement;
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
