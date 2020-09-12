/*
 * Copyright (c) 2018 - 2020 - 2020 - Frank Hossfeld
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

import com.github.nalukit.nalu.processor.ProcessorConstants;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

public class ModulesGenerator {
  
  private MetaModel metaModel;
  
  private TypeSpec.Builder typeSpec;
  
  @SuppressWarnings("unused")
  private ModulesGenerator() {
  }
  
  private ModulesGenerator(Builder builder) {
    this.metaModel = builder.metaModel;
    this.typeSpec  = builder.typeSpec;
  }
  
  public static Builder builder() {
    return new Builder();
  }
  
  public void generate() {
    // generate method 'generateLoadModules()'
    MethodSpec.Builder loadModuleMethodBuilder = MethodSpec.methodBuilder("loadModules")
                                                           .addModifiers(Modifier.PUBLIC)
                                                           .addAnnotation(Override.class);
    // are there any modules?
    this.metaModel.getModules()
                  .forEach(moduleModel -> {
                    String moduleInstanceName = moduleModel.getSimpleName()
                                                           .substring(0,
                                                                      1)
                                                           .toLowerCase() +
                                                moduleModel.getSimpleName()
                                                           .substring(1);
      
                    loadModuleMethodBuilder.addStatement("$T $L = new $T(super.router, super.context, super.eventBus, super.alwaysLoadComposite)",
                                                         ClassName.get(moduleModel.getPackage(),
                                                                       moduleModel.getSimpleName()),
                                                         moduleInstanceName,
                                                         ClassName.get(moduleModel.getPackage(),
                                                                       moduleModel.getSimpleName() + ProcessorConstants.MODULE_IMPL))
                                           .addStatement("$L.loadModule(super.routerConfiguration)",
                                                         moduleInstanceName)
                                           .addStatement("super.shellConfiguration.getShells().addAll($L.getShellConfigs())",
                                                         moduleInstanceName)
                                           .addStatement("super.routerConfiguration.getRouters().addAll($L.getRouteConfigs())",
                                                         moduleInstanceName)
                                           .addStatement("super.compositeControllerReferences.addAll($L.getCompositeReferences())",
                                                         moduleInstanceName);
      
                  });
    typeSpec.addMethod(loadModuleMethodBuilder.build());
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
    
    public ModulesGenerator build() {
      return new ModulesGenerator(this);
    }
    
  }
  
}
