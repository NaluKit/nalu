/*
 * Copyright (c) 2018 - 2019 - Frank Hossfeld
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
import com.github.nalukit.nalu.client.internal.CompositeControllerReference;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.ControllerCompositeModel;
import com.github.nalukit.nalu.processor.model.intern.ControllerModel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

public class CompositesGenerator {

  private MetaModel metaModel;

  private TypeSpec.Builder typeSpec;

  @SuppressWarnings("unused")
  private CompositesGenerator() {
  }

  private CompositesGenerator(Builder builder) {
    this.metaModel = builder.metaModel;
    this.typeSpec = builder.typeSpec;
  }

  public static Builder builder() {
    return new Builder();
  }

  void generate() {
    generateLoadCompositeReferences();
  }

  private void generateLoadCompositeReferences() {
    // generate method 'generateLoadCompositeReferences()'
    MethodSpec.Builder loadCompositesMethodBuilder = MethodSpec.methodBuilder("loadCompositeReferences")
                                                               .addModifiers(Modifier.PUBLIC)
                                                               .addAnnotation(Override.class)
                                                               .addStatement("$T sb01 = new $T()",
                                                                             ClassName.get(StringBuilder.class),
                                                                             ClassName.get(StringBuilder.class))
                                                               .addStatement("sb01.append(\"load composite references\")")
                                                               .addStatement("$T.get().logDetailed(sb01.toString(), 2)",
                                                                             ClassName.get(ClientLogger.class));
    for (ControllerModel controllerModel : this.metaModel.getControllers()) {
      for (ControllerCompositeModel controllerCompositeModel : controllerModel.getComposites()) {
        loadCompositesMethodBuilder.addStatement("this.compositeControllerReferences.add(new $T($S, $S, $S, $S, $L))",
                                                 ClassName.get(CompositeControllerReference.class),
                                                 controllerModel.getProvider()
                                                                .getClassName(),
                                                 controllerCompositeModel.getName(),
                                                 controllerCompositeModel.getComposite()
                                                                         .getClassName(),
                                                 controllerCompositeModel.getSelector(),
                                                 controllerCompositeModel.isScopeGlobal())
                                   .addStatement("sb01.setLength(0)")
                                   .addStatement("sb01.append(\"register composite >>$L<< for controller >>$L<< in selector >>$L<<\")",
                                                 controllerCompositeModel.getName(),
                                                 controllerModel.getProvider()
                                                                .getClassName(),
                                                 controllerCompositeModel.getSelector())
                                   .addStatement("$T.get().logDetailed(sb01.toString(), 3)",
                                                 ClassName.get(ClientLogger.class));

      }
    }
    typeSpec.addMethod(loadCompositesMethodBuilder.build());
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

    public CompositesGenerator build() {
      return new CompositesGenerator(this);
    }

  }

}
