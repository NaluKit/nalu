/*
 * Copyright (c) 2018 - 2019 - 2019 - Frank Hossfeld
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

import com.github.nalukit.nalu.client.internal.ClientLogger;
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
    this.typeSpec = builder.typeSpec;
  }

  public static Builder builder() {
    return new Builder();
  }

  public void generate() {
    // generate method 'generateLoadModules()'
    MethodSpec.Builder loadModuleMethodBuilder = MethodSpec.methodBuilder("loadModules")
                                                           .addModifiers(Modifier.PUBLIC)
                                                           .addAnnotation(Override.class)
                                                           .addStatement("$T sb01 = new $T()",
                                                                         ClassName.get(StringBuilder.class),
                                                                         ClassName.get(StringBuilder.class));
    // are there any modules?
    this.metaModel.getModules()
                  .forEach(moduleModel -> {
                    String moduleInstanceName = moduleModel.getSimpleName()
                                                           .substring(0,
                                                                      1)
                                                           .toLowerCase() +
                                                moduleModel.getSimpleName()
                                                           .substring(1);

                    loadModuleMethodBuilder.addComment("")
                                           .addComment("")
                                           .addComment(" Start handling Module: $L",
                                                       moduleModel.getClassName())
                                           .addComment("")
                                           .addStatement("sb01.setLength(0)")
                                           .addStatement("sb01.append(\"load module >>$L<<\")",
                                                         moduleModel.getClassName())
                                           .addStatement("$T.get().logSimple(sb01.toString(), 1)",
                                                         ClassName.get(ClientLogger.class))

                                           .addStatement("sb01.setLength(0)")
                                           .addStatement("sb01.append(\"create module >>$L<<\")",
                                                         moduleModel.getClassName())
                                           .addStatement("$T.get().logDetailed(sb01.toString(), 2)",
                                                         ClassName.get(ClientLogger.class))
                                           .addStatement("$T $L = new $T(super.router, super.context, super.eventBus, super.alwaysLoadComposite)",
                                                         ClassName.get(moduleModel.getPackage(),
                                                                       moduleModel.getSimpleName()),
                                                         moduleInstanceName,
                                                         ClassName.get(moduleModel.getPackage(),
                                                                       moduleModel.getSimpleName() + ProcessorConstants.MODULE_IMPL))
                                           .addStatement("sb01.setLength(0)")
                                           .addStatement("sb01.append(\"module >>$L<< created\")",
                                                         moduleModel.getClassName())
                                           .addStatement("$T.get().logDetailed(sb01.toString(), 2)",
                                                         ClassName.get(ClientLogger.class))

                                           .addStatement("sb01.setLength(0)")
                                           .addStatement("sb01.append(\"call >>loadModule<<\")")
                                           .addStatement("$T.get().logDetailed(sb01.toString(), 3)",
                                                         ClassName.get(ClientLogger.class))
                                           .addStatement("$L.loadModule(super.routerConfiguration)",
                                                         moduleInstanceName)
                                           .addStatement("sb01.setLength(0)")
                                           .addStatement("sb01.append(\"module >>$L<< loaded\")",
                                                         moduleInstanceName)
                                           .addStatement("$T.get().logDetailed(sb01.toString(), 3)",
                                                         ClassName.get(ClientLogger.class))

                                           .addStatement("sb01.setLength(0)")
                                           .addStatement("sb01.append(\"call >>getShellConfigs<< and add to shellCreator config list\")")
                                           .addStatement("$T.get().logDetailed(sb01.toString(), 3)",
                                                         ClassName.get(ClientLogger.class))
                                           .addStatement("super.shellConfiguration.getShells().addAll($L.getShellConfigs())",
                                                         moduleInstanceName)
                                           .addStatement("sb01.setLength(0)")
                                           .addStatement("sb01.append(\"called >>getShellConfigs<<\")",
                                                         moduleInstanceName)
                                           .addStatement("$T.get().logDetailed(sb01.toString(), 3)",
                                                         ClassName.get(ClientLogger.class))

                                           .addStatement("sb01.setLength(0)")
                                           .addStatement("sb01.append(\"call >>getRouteConfigs<< and add to route config list\")")
                                           .addStatement("$T.get().logDetailed(sb01.toString(), 3)",
                                                         ClassName.get(ClientLogger.class))
                                           .addStatement("super.routerConfiguration.getRouters().addAll($L.getRouteConfigs())",
                                                         moduleInstanceName)
                                           .addStatement("sb01.setLength(0)")
                                           .addStatement("sb01.append(\"called >>getRouteConfigs<<\")",
                                                         moduleInstanceName)
                                           .addStatement("$T.get().logDetailed(sb01.toString(), 3)",
                                                         ClassName.get(ClientLogger.class))

                                           .addStatement("sb01.setLength(0)")
                                           .addStatement("sb01.append(\"call >>getCompositeReferences<< and add to composite controller references\")")
                                           .addStatement("$T.get().logDetailed(sb01.toString(), 3)",
                                                         ClassName.get(ClientLogger.class))
                                           .addStatement("super.compositeControllerReferences.addAll($L.getCompositeReferences())",
                                                         moduleInstanceName)
                                           .addStatement("sb01.setLength(0)")
                                           .addStatement("sb01.append(\"called >>getCompositeReferences<<\")",
                                                         moduleInstanceName)
                                           .addStatement("$T.get().logDetailed(sb01.toString(), 3)",
                                                         ClassName.get(ClientLogger.class))

                                           .addStatement("sb01.setLength(0)")
                                           .addStatement("sb01.append(\"module >>$L<< loaded\")",
                                                         moduleModel.getClassName())
                                           .addStatement("$T.get().logSimple(sb01.toString(), 2)",
                                                         ClassName.get(ClientLogger.class));

                  });
    typeSpec.addMethod(loadModuleMethodBuilder.build());
  }

  public static final class Builder {

    MetaModel metaModel;

    TypeSpec.Builder typeSpec;

    /**
     * Set the EventBusMetaModel of the currently generated eventBus
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
