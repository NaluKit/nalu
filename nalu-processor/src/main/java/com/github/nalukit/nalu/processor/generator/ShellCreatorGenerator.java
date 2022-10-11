/*
 * Copyright (c) 2018 Frank Hossfeld
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

import com.github.nalukit.nalu.client.IsRouter;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.AbstractShellCreator;
import com.github.nalukit.nalu.client.internal.application.IsShellCreator;
import com.github.nalukit.nalu.client.internal.application.ShellInstance;
import com.github.nalukit.nalu.processor.ProcessorConstants;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.EventModel;
import com.github.nalukit.nalu.processor.model.intern.ShellModel;
import com.github.nalukit.nalu.processor.util.BuildWithNaluCommentProvider;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import org.gwtproject.event.shared.SimpleEventBus;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.Objects;

public class ShellCreatorGenerator {

  private ProcessingEnvironment processingEnvironment;

  private ShellModel shellModel;

  @SuppressWarnings("unused")
  private ShellCreatorGenerator() {
  }

  private ShellCreatorGenerator(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;
    this.shellModel            = builder.shellModel;
  }

  public static Builder builder() {
    return new Builder();
  }

  public void generate()
      throws ProcessorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(shellModel.getShell()
                                                                .getSimpleName() + ProcessorConstants.CREATOR_IMPL)
                                        .addJavadoc(BuildWithNaluCommentProvider.get()
                                                                                .getGeneratedComment())
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractShellCreator.class),
                                                                              shellModel.getContext()
                                                                                        .getTypeName()))
                                        .addModifiers(Modifier.PUBLIC,
                                                      Modifier.FINAL)
                                        .addSuperinterface(ClassName.get(IsShellCreator.class));
    // constructor ...
    MethodSpec constructor = MethodSpec.constructorBuilder()
                                       .addModifiers(Modifier.PUBLIC)
                                       .addParameter(ParameterSpec.builder(ClassName.get(IsRouter.class),
                                                                           "router")
                                                                  .build())
                                       .addParameter(ParameterSpec.builder(shellModel.getContext()
                                                                                     .getTypeName(),
                                                                           "context")
                                                                  .build())
                                       .addParameter(ParameterSpec.builder(ClassName.get(SimpleEventBus.class),
                                                                           "eventBus")
                                                                  .build())
                                       .addStatement("super(router, context, eventBus)")
                                       .build();
    typeSpec.addMethod(constructor);
    // create Method
    MethodSpec.Builder createMethod = MethodSpec.methodBuilder("create")
                                                .addAnnotation(ClassName.get(Override.class))
                                                .addModifiers(Modifier.PUBLIC)
                                                .returns(ClassName.get(ShellInstance.class))
                                                .addStatement("$T shellInstance = new $T()",
                                                              ClassName.get(ShellInstance.class),
                                                              ClassName.get(ShellInstance.class))
                                                .addStatement("shellInstance.setShellClassName($S)",
                                                              this.shellModel.getShell()
                                                                             .getClassName())
                                                .addStatement("$T shell = new $T()",
                                                              ClassName.get(this.shellModel.getShell()
                                                                                           .getPackage(),
                                                                            this.shellModel.getShell()
                                                                                           .getSimpleName()),
                                                              ClassName.get(this.shellModel.getShell()
                                                                                           .getPackage(),
                                                                            this.shellModel.getShell()
                                                                                           .getSimpleName()))
                                                .addStatement("shellInstance.setShell(shell)")
                                                .addStatement("shell.setContext(context)")
                                                .addStatement("shell.setEventBus(eventBus)")
                                                .addStatement("shell.setRouter(router)");

    this.shellModel.getEventHandlers()
                   .forEach(m -> {
                     EventModel eventModel = this.shellModel.getEventModel(m.getEvent()
                                                                            .getClassName());
                     if (!Objects.isNull(eventModel)) {
                       createMethod.addStatement("super.eventBus.addHandler($T.TYPE, e -> shell.$L(e))",
                                                 ClassName.get(eventModel.getEvent()
                                                                         .getPackage(),
                                                               eventModel.getEvent()
                                                                         .getSimpleName()),
                                                 m.getMethodName());
                     }
                   });

    createMethod.addStatement("return shellInstance");
    typeSpec.addMethod(createMethod.build());

    // create Method
    MethodSpec.Builder finishCreateMethod = MethodSpec.methodBuilder("onFinishCreating")
                                                      .addAnnotation(ClassName.get(Override.class))
                                                      .addModifiers(Modifier.PUBLIC)
                                                      .addException(ClassName.get(RoutingInterceptionException.class));

    typeSpec.addMethod(finishCreateMethod.build());

    JavaFile javaFile = JavaFile.builder(this.shellModel.getShell()
                                                        .getPackage(),
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(this.processingEnvironment.getFiler());
    } catch (IOException e) {
      throw new ProcessorException("Unable to write generated file: >>" +
                                   this.shellModel.getShell()
                                                  .getClassName() +
                                   ProcessorConstants.CREATOR_IMPL +
                                   "<< -> exception: " +
                                   e.getMessage());
    }
  }

  public static final class Builder {

    MetaModel metaModel;

    ProcessingEnvironment processingEnvironment;

    ShellModel shellModel;

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

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder shellModel(ShellModel shellModel) {
      this.shellModel = shellModel;
      return this;
    }

    public ShellCreatorGenerator build() {
      return new ShellCreatorGenerator(this);
    }

  }

}
