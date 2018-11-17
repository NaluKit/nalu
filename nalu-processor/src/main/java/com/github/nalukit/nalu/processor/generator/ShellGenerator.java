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
import com.github.nalukit.nalu.client.internal.application.ShellFactory;
import com.github.nalukit.nalu.client.internal.application.ShellInstance;
import com.github.nalukit.nalu.client.internal.route.ShellConfig;
import com.github.nalukit.nalu.processor.ProcessorConstants;
import com.github.nalukit.nalu.processor.model.ApplicationMetaModel;
import com.github.nalukit.nalu.processor.model.intern.ShellModel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

public class ShellGenerator {

  private ApplicationMetaModel applicationMetaModel;

  private TypeSpec.Builder typeSpec;

  @SuppressWarnings("unused")
  private ShellGenerator() {
  }

  private ShellGenerator(Builder builder) {
    this.applicationMetaModel = builder.applicationMetaModel;
    this.typeSpec = builder.typeSpec;
  }

  public static Builder builder() {
    return new Builder();
  }

  void generate() {
    generateLoadShells();
    generateLoadShellFactory();
  }

  private void generateLoadShells() {
    // generate method 'generateLoadShells()'
    MethodSpec.Builder loadShellsMethodBuilder = MethodSpec.methodBuilder("loadShells")
                                                           .addModifiers(Modifier.PUBLIC)
                                                           .addAnnotation(Override.class);
    this.applicationMetaModel.getShells()
                             .forEach(shellModel -> loadShellsMethodBuilder.addStatement("super.shellConfiguration.getShells().add(new $T($S, $S))",
                                                                                         ClassName.get(ShellConfig.class),
                                                                                         "/" + shellModel.getName(),
                                                                                         shellModel.getShell()
                                                                                                   .getClassName()));
    typeSpec.addMethod(loadShellsMethodBuilder.build());
  }

  private void generateLoadShellFactory() {
    // generate method 'generateLoadShells()'
    MethodSpec.Builder loadShellFactoryMethodBuilder = MethodSpec.methodBuilder("loadShellFactory")
                                                                 .addModifiers(Modifier.PUBLIC)
                                                                 .addAnnotation(Override.class);
    this.applicationMetaModel.getShells()
                             .forEach(shellModel -> {
                               // add return statement
                               loadShellFactoryMethodBuilder.addComment("create ShellCreator for: " +
                                                                        shellModel.getShell()
                                                                                  .getPackage() +
                                                                        "." +
                                                                        shellModel.getShell()
                                                                                  .getSimpleName())
                                                            .addStatement("$T.get().registerShell($S, new $L(router, context, eventBus))",
                                                                          ClassName.get(ShellFactory.class),
                                                                          shellModel.getShell()
                                                                                    .getPackage() +
                                                                          "." +
                                                                          shellModel.getShell()
                                                                                    .getSimpleName(),
                                                                          ClassName.get(shellModel.getShell()
                                                                                                  .getPackage(),
                                                                                        shellModel.getShell()
                                                                                                  .getSimpleName() + ProcessorConstants.CREATOR_IMPL));

                             });
    typeSpec.addMethod(loadShellFactoryMethodBuilder.build());
  }

  public static final class Builder {

    ApplicationMetaModel applicationMetaModel;

    TypeSpec.Builder typeSpec;

    /**
     * Set the EventBusMetaModel of the currently generated eventBus
     *
     * @param applicationMetaModel meta data model of the eventbus
     * @return the Builder
     */
    public Builder applicationMetaModel(ApplicationMetaModel applicationMetaModel) {
      this.applicationMetaModel = applicationMetaModel;
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

    public ShellGenerator build() {
      return new ShellGenerator(this);
    }
  }
}
