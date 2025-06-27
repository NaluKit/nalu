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
package io.github.nalukit.nalu.processor.generator;

import io.github.nalukit.nalu.client.context.AbstractModuleContext;
import io.github.nalukit.nalu.client.module.IsModuleLoader;
import io.github.nalukit.nalu.processor.ProcessorConstants;
import io.github.nalukit.nalu.processor.model.MetaModel;
import io.github.nalukit.nalu.processor.model.intern.ClassNameModel;
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
    if (this.metaModel.getModules()
                      .size() == 0) {
      loadModuleMethodBuilder.addStatement("super.onFinishModuleLoading()");
    } else {
      loadModuleMethodBuilder.addStatement("this.callCounter = $L",
                                           String.valueOf(this.metaModel.getModules()
                                                                        .size()));
      this.metaModel.getModules()
                    .forEach(moduleModel -> this.loadModule(loadModuleMethodBuilder,
                                                            this.metaModel.getContext(),
                                                            moduleModel));
    }
    typeSpec.addMethod(loadModuleMethodBuilder.build());
  }

  private void loadModule(MethodSpec.Builder loadModuleMethodBuilder,
                          ClassNameModel contextModel,
                          ClassNameModel moduleModel) {
    String moduleImplVariableName = this.createPackageName(moduleModel.getPackage()) +
                                    "_" +
                                    moduleModel.getSimpleName() +
                                    ProcessorConstants.MODULE_IMPL;
    loadModuleMethodBuilder.addStatement("$T $L = new $T(super.context.getApplicationContext())",
                                         ClassName.get(moduleModel.getPackage(),
                                                       moduleModel.getSimpleName() + ProcessorConstants.MODULE_IMPL),
                                         moduleImplVariableName,
                                         ClassName.get(moduleModel.getPackage(),
                                                       moduleModel.getSimpleName() + ProcessorConstants.MODULE_IMPL));
    loadModuleMethodBuilder.addStatement("this.router.addModule($L)",
                                         moduleImplVariableName);
    String moduleLoaderVariableName = this.createPackageName(moduleModel.getPackage()) +
                                      "_" +
                                      moduleModel.getSimpleName() +
                                      ProcessorConstants.LOADER_IMPL;
    loadModuleMethodBuilder.addStatement("$T<? extends $T> $L = $L.createModuleLoader()",
                                         ClassName.get(IsModuleLoader.class),
                                         ClassName.get(AbstractModuleContext.class),
                                         moduleLoaderVariableName,
                                         moduleImplVariableName);
    //    loadModuleMethodBuilder.addStatement("$T<$T> $L = $L.createModuleLoader()",
    //                                         ClassName.get(IsModuleLoader.class),
    //                                         ClassName.get(contextModel.getPackage(),
    //                                                       contextModel.getSimpleName()),
    //                                         moduleLoaderVariableName,
    //                                         moduleImplVariableName);
    loadModuleMethodBuilder.beginControlFlow("if ($L == null)",
                                             moduleLoaderVariableName)
                           .addStatement("this.handleSuccess()")
                           .nextControlFlow("else")
                           .addStatement("$L.setRouter(super.router)",
                                         moduleLoaderVariableName)
                           .addStatement("$L.setEventBus(super.eventBus)",
                                         moduleLoaderVariableName)
                           //                           .addStatement("$L.setContext(super.context)",
                           //                                         moduleLoaderVariableName)
                           .addStatement("$L.load(() -> this.handleSuccess())",
                                         moduleLoaderVariableName)
                           .endControlFlow();
  }

  private String createPackageName(String pkg) {
    String value = pkg.replace(".",
                               "_");
    value = value.substring(0,
                            1)
                 .toLowerCase() + value.substring(1);
    return value;
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
