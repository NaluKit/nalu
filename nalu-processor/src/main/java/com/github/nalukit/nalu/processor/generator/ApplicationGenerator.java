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

import com.github.nalukit.nalu.client.application.IsApplicationLoader;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.application.AbstractApplication;
import com.github.nalukit.nalu.client.internal.application.NoApplicationLoader;
import com.github.nalukit.nalu.processor.ProcessorConstants;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.util.BuildWithNaluCommentProvider;
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

  public void generate(MetaModel metaModel)
      throws ProcessorException {
    // check if element is existing (to avoid generating code for deleted items)
    if (!this.processorUtils.doesExist(metaModel.getApplication())) {
      return;
    }
    // generate code
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(metaModel.getApplication()
                                                               .getSimpleName() + ApplicationGenerator.IMPL_NAME)
                                        .addJavadoc(BuildWithNaluCommentProvider.get()
                                                                                .getGeneratedComment())
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
    typeSpec.addMethod(constructor);

    typeSpec.addMethod(MethodSpec.methodBuilder("logProcessorVersion")
                                 .addAnnotation(ClassName.get(Override.class))
                                 .addModifiers(Modifier.PUBLIC)
                                 .addStatement("$T.get().logDetailed(\"\", 0)",
                                               ClassName.get(ClientLogger.class))
                                 .addStatement("$T.get().logDetailed(\"=================================================================================\", 0)",
                                               ClassName.get(ClientLogger.class))
                                 .addStatement("$T sb01 = new $T()",
                                               ClassName.get(StringBuilder.class),
                                               ClassName.get(StringBuilder.class))
                                 .addStatement("sb01.append(\"Nalu processor version  >>$L<< used to generate this source\")",
                                               ProcessorConstants.PROCESSOR_VERSION)
                                 .addStatement("$T.get().logDetailed(sb01.toString(), 0)",
                                               ClassName.get(ClientLogger.class))
                                 .addStatement("$T.get().logDetailed(\"=================================================================================\", 0)",
                                               ClassName.get(ClientLogger.class))
                                 .addStatement("$T.get().logDetailed(\"\", 0)",
                                               ClassName.get(ClientLogger.class))
                                 .build());

    DebugGenerator.builder()
                  .metaModel(metaModel)
                  .typeSpec(typeSpec)
                  .build()
                  .generate();

    TrackerGenerator.builder()
                    .metaModel(metaModel)
                    .typeSpec(typeSpec)
                    .build()
                    .generate();

    ShellGenerator.builder()
                  .metaModel(metaModel)
                  .typeSpec(typeSpec)
                  .build()
                  .generate();

    CompositeControllerGenerator.builder()
                                .metaModel(metaModel)
                                .typeSpec(typeSpec)
                                .build()
                                .generate();

    ControllerGenerator.builder()
                       .metaModel(metaModel)
                       .typeSpec(typeSpec)
                       .build()
                       .generate();

    PopUpControllerGenerator.builder()
                            .metaModel(metaModel)
                            .typeSpec(typeSpec)
                            .build()
                            .generate();

    FiltersGenerator.builder()
                    .processingEnvironment(this.processingEnvironment)
                    .metaModel(metaModel)
                    .typeSpec(typeSpec)
                    .build()
                    .generate();

    HandlerGenerator.builder()
                    .processingEnvironment(this.processingEnvironment)
                    .metaModel(metaModel)
                    .typeSpec(typeSpec)
                    .build()
                    .generate();

    CompositesGenerator.builder()
                       .metaModel(metaModel)
                       .typeSpec(typeSpec)
                       .build()
                       .generate();
    // need to be called!
    // even if the app has no plugins,
    // a empty method has to be created!
    PluginsGenerator.builder()
                    .metaModel(metaModel)
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

    generatHasHistoryMethod(typeSpec,
                            metaModel);

    generateIsUsingHashMethod(typeSpec,
                              metaModel);

    generateIsUsingColonForParametersInUrl(typeSpec,
                                           metaModel);

    generateIsStayOnSide(typeSpec,
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

  private void generatHasHistoryMethod(TypeSpec.Builder typeSpec,
                                       MetaModel metaModel) {
    typeSpec.addMethod(MethodSpec.methodBuilder("hasHistory")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .returns(boolean.class)
                                 .addStatement("return $L",
                                               metaModel.hasHistory() ? "true" : "false")
                                 .build());
  }

  private void generateIsUsingHashMethod(TypeSpec.Builder typeSpec,
                                         MetaModel metaModel) {
    typeSpec.addMethod(MethodSpec.methodBuilder("isUsingHash")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .returns(boolean.class)
                                 .addStatement("return $L",
                                               metaModel.isUsingHash() ? "true" : "false")
                                 .build());
  }

  private void generateIsUsingColonForParametersInUrl(TypeSpec.Builder typeSpec,
                                                      MetaModel metaModel) {
    typeSpec.addMethod(MethodSpec.methodBuilder("isUsingColonForParametersInUrl")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .returns(boolean.class)
                                 .addStatement("return $L",
                                               metaModel.isUsingColonForParametersInUrl() ? "true" : "false")
                                 .build());
  }

  private void generateIsStayOnSide(TypeSpec.Builder typeSpec,
                                    MetaModel metaModel) {
    typeSpec.addMethod(MethodSpec.methodBuilder("isStayOnSide")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .returns(boolean.class)
                                 .addStatement("return $L",
                                               metaModel.isStayOnSide() ? "true" : "false")
                                 .build());
  }

  private void generateLoadDefaultsRoutes(TypeSpec.Builder typeSpec,
                                          MetaModel metaModel) {
    typeSpec.addMethod(MethodSpec.methodBuilder("loadDefaultRoutes")
                                 .addModifiers(Modifier.PUBLIC)
                                 .addAnnotation(Override.class)
                                 .addStatement("$T sb01 = new $T()",
                                               ClassName.get(StringBuilder.class),
                                               ClassName.get(StringBuilder.class))
                                 .addStatement("this.startRoute = $S",
                                               metaModel.getStartRoute())
                                 .addStatement("sb01.append(\"found startRoute >>$L<<\")",
                                               metaModel.getStartRoute())
                                 .addStatement("$T.get().logDetailed(sb01.toString(), 2)",
                                               ClassName.get(ClientLogger.class))
                                 .addStatement("sb01 = new $T()",
                                               ClassName.get(StringBuilder.class))
                                 .addStatement("this.errorRoute = $S",
                                               metaModel.getRouteError())
                                 .addStatement("sb01.append(\"found errorRoute >>$L<<\")",
                                               metaModel.getRouteError())
                                 .addStatement("$T.get().logDetailed(sb01.toString(), 2)",
                                               ClassName.get(ClientLogger.class))
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
