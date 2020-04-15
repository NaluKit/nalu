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
package com.github.nalukit.nalu.processor.generator;

import com.github.nalukit.nalu.client.internal.application.CompositeFactory;
import com.github.nalukit.nalu.processor.ProcessorConstants;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.CompositeModel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

public class CompositeControllerGenerator {
  
  private MetaModel metaModel;
  
  private TypeSpec.Builder typeSpec;
  
  @SuppressWarnings("unused")
  private CompositeControllerGenerator() {
  }
  
  private CompositeControllerGenerator(Builder builder) {
    this.metaModel = builder.metaModel;
    this.typeSpec  = builder.typeSpec;
  }
  
  public static Builder builder() {
    return new Builder();
  }
  
  void generate() {
    generateLoadComposites();
  }
  
  private void generateLoadComposites() {
    // generate method 'loadCompositeController()'
    MethodSpec.Builder loadCompositesMethodBuilder = MethodSpec.methodBuilder("loadCompositeController")
                                                               .addModifiers(Modifier.PUBLIC)
                                                               .addAnnotation(Override.class);
    for (CompositeModel compositeModel : this.metaModel.getCompositeModels()) {
      loadCompositesMethodBuilder.addComment("create Composite for: " +
                                             compositeModel.getProvider()
                                                           .getPackage() +
                                             "." +
                                             compositeModel.getProvider()
                                                           .getSimpleName())
                                 .addStatement("$T.get().registerComposite($S, new $L(router, context, eventBus))",
                                               ClassName.get(CompositeFactory.class),
                                               compositeModel.getProvider()
                                                             .getPackage() +
                                               "." +
                                               compositeModel.getProvider()
                                                             .getSimpleName(),
                                               ClassName.get(compositeModel.getProvider()
                                                                           .getPackage(),
                                                             compositeModel.getProvider()
                                                                           .getSimpleName() + ProcessorConstants.CREATOR_IMPL));
    }
    typeSpec.addMethod(loadCompositesMethodBuilder.build());
  }
  
  public static final class Builder {
    
    MetaModel metaModel;
    
    TypeSpec.Builder typeSpec;
    
    /**
     * Set the MetaModel of the currently generated eventBus
     *
     * @param metaModel meta data model of the event bus
     * @return the Builder
     */
    public Builder metaModel(MetaModel metaModel) {
      this.metaModel = metaModel;
      return this;
    }
    
    /**
     * Set the typeSpec of the currently generated eventBus
     *
     * @param typeSpec type spec of the current event bus
     * @return the Builder
     */
    Builder typeSpec(TypeSpec.Builder typeSpec) {
      this.typeSpec = typeSpec;
      return this;
    }
    
    public CompositeControllerGenerator build() {
      return new CompositeControllerGenerator(this);
    }
    
  }
  
}
