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
package com.github.nalukit.nalu.processor.generator;

import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.processor.ProcessorConstants;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

public class PluginsGenerator {

  private MetaModel metaModel;

  private TypeSpec.Builder typeSpec;

  @SuppressWarnings("unused")
  private PluginsGenerator() {
  }

  private PluginsGenerator(Builder builder) {
    this.metaModel = builder.metaModel;
    this.typeSpec = builder.typeSpec;
  }

  public static Builder builder() {
    return new Builder();
  }

  public void generate() {
    // generate method 'generateLoadPlugins()'
    MethodSpec.Builder loadPluginMethodBuilder = MethodSpec.methodBuilder("loadPlugins")
                                                           .addModifiers(Modifier.PUBLIC)
                                                           .addAnnotation(Override.class)
                                                           .addStatement("$T sb01 = new $T()",
                                                                         ClassName.get(StringBuilder.class),
                                                                         ClassName.get(StringBuilder.class))
        ;
    // are there any plugins?
    this.metaModel.getPlugins()
                  .forEach(pluginModel -> {
                    String pluginInstanceName = pluginModel.getSimpleName()
                                                           .substring(0,
                                                                      1)
                                                           .toLowerCase() +
                                                pluginModel.getSimpleName()
                                                           .substring(1);

                    loadPluginMethodBuilder.addComment("")
                                           .addComment("")
                                           .addComment(" Start handling Plugin: $L",
                                                       pluginModel.getClassName())
                                           .addComment("")
                                           .addStatement("sb01 = new $T()",
                                                         ClassName.get(StringBuilder.class))
                                           .addStatement("sb01.append(\"load plugin >>$L<<\")",
                                                         pluginModel.getClassName())
                                           .addStatement("$T.get().logSimple(sb01.toString(), 1)",
                                                         ClassName.get(ClientLogger.class))

                                           .addStatement("sb01 = new $T()",
                                                         ClassName.get(StringBuilder.class))
                                           .addStatement("sb01.append(\"create plugin >>$L<<\")",
                                                         pluginModel.getClassName())
                                           .addStatement("$T.get().logDetailed(sb01.toString(), 2)",
                                                         ClassName.get(ClientLogger.class))
                                           .addStatement("$T $L = new $T(super.router, super.context, super.eventBus)",
                                                         ClassName.get(pluginModel.getPackage(),
                                                                       pluginModel.getSimpleName()),
                                                         pluginInstanceName,
                                                         ClassName.get(pluginModel.getPackage(),
                                                                       pluginModel.getSimpleName() + ProcessorConstants.PLUGIN_IMPL))
                                           .addStatement("sb01 = new $T()",
                                                         ClassName.get(StringBuilder.class))
                                           .addStatement("sb01.append(\"plugin >>$L<< created\")",
                                                         pluginModel.getClassName())
                                           .addStatement("$T.get().logDetailed(sb01.toString(), 2)",
                                                         ClassName.get(ClientLogger.class))

                                           .addStatement("sb01 = new $T()",
                                                         ClassName.get(StringBuilder.class))
                                           .addStatement("sb01.append(\"call >>loadPlugin<<\")")
                                           .addStatement("$T.get().logDetailed(sb01.toString(), 3)",
                                                         ClassName.get(ClientLogger.class))
                                           .addStatement("$L.loadPlugin()",
                                                         pluginInstanceName)
                                           .addStatement("sb01 = new $T()",
                                                         ClassName.get(StringBuilder.class))
                                           .addStatement("sb01.append(\"plugin >>$L<< loaded\")",
                                                         pluginInstanceName)
                                           .addStatement("$T.get().logDetailed(sb01.toString(), 3)",
                                                         ClassName.get(ClientLogger.class))

                                           .addStatement("sb01 = new $T()",
                                                         ClassName.get(StringBuilder.class))
                                           .addStatement("sb01.append(\"call >>getShellConfigs<< and add to shell config list\")")
                                           .addStatement("$T.get().logDetailed(sb01.toString(), 3)",
                                                         ClassName.get(ClientLogger.class))
                                           .addStatement("super.shellConfiguration.getShells().addAll($L.getShellConfigs())",
                                                         pluginInstanceName)
                                           .addStatement("sb01 = new $T()",
                                                         ClassName.get(StringBuilder.class))
                                           .addStatement("sb01.append(\"called >>getShellConfigs<<\")",
                                                         pluginInstanceName)
                                           .addStatement("$T.get().logDetailed(sb01.toString(), 3)",
                                                         ClassName.get(ClientLogger.class))

                                           .addStatement("sb01 = new $T()",
                                                         ClassName.get(StringBuilder.class))
                                           .addStatement("sb01.append(\"call >>getRouteConfigs<< and add to route config list\")")
                                           .addStatement("$T.get().logDetailed(sb01.toString(), 3)",
                                                         ClassName.get(ClientLogger.class))
                                           .addStatement("super.routerConfiguration.getRouters().addAll($L.getRouteConfigs())",
                                                         pluginInstanceName)
                                           .addStatement("sb01 = new $T()",
                                                         ClassName.get(StringBuilder.class))
                                           .addStatement("sb01.append(\"called >>getRouteConfigs<<\")",
                                                         pluginInstanceName)
                                           .addStatement("$T.get().logDetailed(sb01.toString(), 3)",
                                                         ClassName.get(ClientLogger.class))

                                           .addStatement("sb01 = new $T()",
                                                         ClassName.get(StringBuilder.class))
                                           .addStatement("sb01.append(\"call >>getCompositeReferences<< and add to composite controller references\")")
                                           .addStatement("$T.get().logDetailed(sb01.toString(), 3)",
                                                         ClassName.get(ClientLogger.class))
                                           .addStatement("super.compositeControllerReferences.addAll($L.getCompositeReferences())",
                                                         pluginInstanceName)
                                           .addStatement("sb01 = new $T()",
                                                         ClassName.get(StringBuilder.class))
                                           .addStatement("sb01.append(\"called >>getCompositeReferences<<\")",
                                                         pluginInstanceName)
                                           .addStatement("$T.get().logDetailed(sb01.toString(), 3)",
                                                         ClassName.get(ClientLogger.class))

                                           .addStatement("sb01 = new $T()",
                                                         ClassName.get(StringBuilder.class))
                                           .addStatement("sb01.append(\"plugin >>$L<< loaded\")",
                                                         pluginModel.getClassName())
                                           .addStatement("$T.get().logSimple(sb01.toString(), 2)",
                                                         ClassName.get(ClientLogger.class));

                  });
    typeSpec.addMethod(loadPluginMethodBuilder.build());
  }

  public static final class Builder {

    MetaModel metaModel;

    TypeSpec.Builder typeSpec;

    /**
     * Set the EventBusMetaModel of the currently generated eventBus
     *
     * @param metaModel meta data model of the eventbus
     * @return the Builder
     */
    public Builder metaModel(MetaModel metaModel) {
      this.metaModel = metaModel;
      return this;
    }

    /**
     * Set the typeSpec of the currently generated eventBus
     *
     * @param typeSpec ttype spec of the crruent eventbus
     * @return the Builder
     */
    Builder typeSpec(TypeSpec.Builder typeSpec) {
      this.typeSpec = typeSpec;
      return this;
    }

    public PluginsGenerator build() {
      return new PluginsGenerator(this);
    }
  }
}
