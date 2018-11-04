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

import com.github.nalukit.nalu.client.application.IsApplicationLoader;
import com.github.nalukit.nalu.client.internal.application.AbstractApplication;
import com.github.nalukit.nalu.client.internal.application.NoApplicationLoader;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;
import com.github.nalukit.nalu.processor.model.ApplicationMetaModel;
import com.squareup.javapoet.*;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import java.io.IOException;

public class ApplicationGenerator {

  private final static String IMPL_NAME = "Impl";

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;

  @SuppressWarnings("unused")
  private ApplicationGenerator(Builder builder) {
    super();

    this.processingEnvironment = builder.processingEnvironment;

    setUp();
  }

  public static Builder builder() {
    return new Builder();
  }

  private void setUp() {
    this.processorUtils = ProcessorUtils.builder()
                                        .processingEnvironment(this.processingEnvironment)
                                        .build();
  }

  public void generate(ApplicationMetaModel metaModel)
      throws ProcessorException {
    // check if element is existing (to avoid generating code for deleted items)
    if (!this.processorUtils.doesExist(metaModel.getApplication())) {
      return;
    }
    // generate code
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(metaModel.getApplication()
                                                               .getSimpleName() + ApplicationGenerator.IMPL_NAME)
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractApplication.class),
                                                                              metaModel.getContext()
                                                                                       .getTypeName()))
                                        .addModifiers(Modifier.PUBLIC,
                                                      Modifier.FINAL)
                                        .addSuperinterface(metaModel.getApplication()
                                                                    .getTypeName());

    // constructor ...
    MethodSpec constructor = MethodSpec.constructorBuilder()
                                       .addModifiers(Modifier.PUBLIC)
                                       .addStatement("super()")
                                       .addStatement("super.context = new $N.$N()",
                                                     metaModel.getContext()
                                                              .getPackage(),
                                                     metaModel.getContext()
                                                              .getSimpleName())
                                       .build();
    DebugGenerator.builder()
                  .applicationMetaModel(metaModel)
                  .typeSpec(typeSpec)
                  .build()
                  .generate();
    typeSpec.addMethod(constructor);

    ShellGenerator.builder()
                  .applicationMetaModel(metaModel)
                  .typeSpec(typeSpec)
                  .build()
                  .generate();

    CompositeControllerGenerator.builder()
                                .applicationMetaModel(metaModel)
                                .typeSpec(typeSpec)
                                .build()
                                .generate();

    ControllerGenerator.builder()
                       .applicationMetaModel(metaModel)
                       .typeSpec(typeSpec)
                       .build()
                       .generate();

    FiltersGenerator.builder()
                    .processingEnvironment(this.processingEnvironment)
                    .applicationMetaModel(metaModel)
                    .typeSpec(typeSpec)
                    .build()
                    .generate();

    HandlerGenerator.builder()
                    .processingEnvironment(this.processingEnvironment)
                    .applicationMetaModel(metaModel)
                    .typeSpec(typeSpec)
                    .build()
                    .generate();

    CompositesGenerator.builder()
                       .applicationMetaModel(metaModel)
                       .typeSpec(typeSpec)
                       .build()
                       .generate();

    // method "getApplicationLoader"
    MethodSpec.Builder getApplicationLoaderMethod = MethodSpec.methodBuilder("getApplicationLoader")
                                                              .addModifiers(Modifier.PUBLIC)
                                                              .addAnnotation(Override.class)
                                                              .returns(ParameterizedTypeName.get(ClassName.get(IsApplicationLoader.class),
                                                                                                 metaModel.getContext()
                                                                                                          .getTypeName()));
    if (NoApplicationLoader.class.getCanonicalName()
                                 .equals(metaModel.getLoader()
                                                  .getClassName())) {
      getApplicationLoaderMethod.addStatement("return null");
    } else {
      getApplicationLoaderMethod.addStatement("return new $T()",
                                              metaModel.getLoader()
                                                       .getTypeName());
    }
    typeSpec.addMethod(getApplicationLoaderMethod.build());

    generateLoadDefaultsRoutes(typeSpec,
                               metaModel);

    JavaFile javaFile = JavaFile.builder(metaModel.getGenerateToPackage(),
                                         typeSpec.build())
                                .build();
    try {
      //      System.out.println(javaFile.toString());
      javaFile.writeTo(this.processingEnvironment.getFiler());
    } catch (IOException e) {
      throw new ProcessorException("Unable to write generated file: >>" +
                                   metaModel.getApplication()
                                            .getSimpleName() +
                                   ApplicationGenerator.IMPL_NAME +
                                   "<< -> exception: " +
                                   e.getMessage());
    }
  }

  private void generateLoadDefaultsRoutes(TypeSpec.Builder typeSpec,
                                          ApplicationMetaModel metaModel) {
    typeSpec.addMethod(MethodSpec.methodBuilder("loadDefaultRoutes")
                                 .addModifiers(Modifier.PUBLIC)
                                 .addAnnotation(Override.class)
                                 .addStatement("this.startRoute = $S",
                                               metaModel.getStartRoute())
                                 .addStatement("this.errorRoute = $S",
                                               metaModel.getRouteErrorRoute())
                                 .build());
  }

  public static class Builder {

    ProcessingEnvironment processingEnvironment;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public ApplicationGenerator build() {
      return new ApplicationGenerator(this);
    }
  }
}
