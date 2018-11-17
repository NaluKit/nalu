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

import com.github.nalukit.nalu.client.Router;
import com.github.nalukit.nalu.client.internal.AbstractShellCreator;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.application.IsShellCreator;
import com.github.nalukit.nalu.client.internal.application.ShellInstance;
import com.github.nalukit.nalu.processor.ProcessorConstants;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.model.ApplicationMetaModel;
import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;
import com.squareup.javapoet.*;
import org.gwtproject.event.shared.SimpleEventBus;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import java.io.IOException;

public class ShellCreatorGenerator {

  private ApplicationMetaModel applicationMetaModel;

  private ProcessingEnvironment processingEnvironment;

  private ClassNameModel shell;

  @SuppressWarnings("unused")
  private ShellCreatorGenerator() {
  }

  private ShellCreatorGenerator(Builder builder) {
    this.applicationMetaModel = builder.applicationMetaModel;
    this.processingEnvironment = builder.processingEnvironment;
    this.shell = builder.shell;
  }

  public static Builder builder() {
    return new Builder();
  }

  public void generate()
      throws ProcessorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(shell.getSimpleName() + ProcessorConstants.CREATOR_IMPL)
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractShellCreator.class),
                                                                              applicationMetaModel.getContext()
                                                                                                  .getTypeName()))
                                        .addModifiers(Modifier.PUBLIC,
                                                      Modifier.FINAL)
                                        .addSuperinterface(ClassName.get(IsShellCreator.class));
    // constructor ...
    MethodSpec constructor = MethodSpec.constructorBuilder()
                                       .addModifiers(Modifier.PUBLIC)
                                       .addParameter(ParameterSpec.builder(ClassName.get(Router.class),
                                                                           "router")
                                                                  .build())
                                       .addParameter(ParameterSpec.builder(applicationMetaModel.getContext()
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
                                                .addModifiers(Modifier.PUBLIC)
                                                .returns(ClassName.get(ShellInstance.class))
                                                .addStatement("$T sb01 = new $T()",
                                                              ClassName.get(StringBuilder.class),
                                                              ClassName.get(StringBuilder.class))
                                                .addStatement("$T shellInstance = new $T()",
                                                              ClassName.get(ShellInstance.class),
                                                              ClassName.get(ShellInstance.class))
                                                .addStatement("shellInstance.setShellClassName($S)",
                                                              this.shell.getClassName())
                                                .addStatement("sb01.append(\"compositeModel >>$L<< --> will be created\")",
                                                              shell.getClassName())
                                                .addStatement("$T.get().logSimple(sb01.toString(), 1)",
                                                              ClassName.get(ClientLogger.class))
                                                .addStatement("$T compositeModel = new $T()",
                                                              ClassName.get(this.shell.getPackage(),
                                                                            this.shell.getSimpleName()),
                                                              ClassName.get(this.shell.getPackage(),
                                                                            this.shell.getSimpleName()))
                                                .addStatement("compositeModel.setContext(context)")
                                                .addStatement("compositeModel.setEventBus(eventBus)")
                                                .addStatement("compositeModel.setRouter(router)")
                                                .addStatement("sb01 = new $T()",
                                                              ClassName.get(StringBuilder.class))
                                                .addStatement("sb01.append(\"compositeModel >>$L<< --> created and data injected\")",
                                                              this.shell.getClassName())
                                                .addStatement("$T.get().logDetailed(sb01.toString(), 2)",
                                                              ClassName.get(ClientLogger.class))
                                                .addStatement("sb01 = new $T()",
                                                              ClassName.get(StringBuilder.class))
                                                .addStatement("sb01.append(\"compositeModel >>$L<< --> call bind()-method\")",
                                                              this.shell.getClassName())
                                                .addStatement("$T.get().logDetailed(sb01.toString(), 2)",
                                                              ClassName.get(ClientLogger.class))
                                                .addStatement("compositeModel.bind()")
                                                .addStatement("sb01 = new $T()",
                                                              ClassName.get(StringBuilder.class))
                                                .addStatement("sb01.append(\"compositeModel >>$L<< --> called bind()-method\")",
                                                              this.shell.getClassName())
                                                .addStatement("$T.get().logDetailed(sb01.toString(), 2)",
                                                              ClassName.get(ClientLogger.class))
                                                .addStatement("shellInstance.setShell(compositeModel)")
                                                .addStatement("return shellInstance");

    typeSpec.addMethod(createMethod.build());

    JavaFile javaFile = JavaFile.builder(this.shell.getPackage(),
                                         typeSpec.build())
                                .build();
    try {
      //      System.out.println(javaFile.toString());
      javaFile.writeTo(this.processingEnvironment.getFiler());
    } catch (IOException e) {
      throw new ProcessorException("Unable to write generated file: >>" + this.shell.getClassName() + ProcessorConstants.CREATOR_IMPL + "<< -> exception: " + e.getMessage());
    }
  }

  public static final class Builder {

    ApplicationMetaModel applicationMetaModel;

    ProcessingEnvironment processingEnvironment;

    ClassNameModel shell;

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

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder shell(ClassNameModel shell) {
      this.shell = shell;
      return this;
    }

    public ShellCreatorGenerator build() {
      return new ShellCreatorGenerator(this);
    }
  }
}
