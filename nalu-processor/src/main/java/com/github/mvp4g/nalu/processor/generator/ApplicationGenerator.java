/*
 * Copyright (c) 2018 - Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *
 */

package com.github.mvp4g.nalu.processor.generator;

import com.github.mvp4g.nalu.client.application.IsApplicationLoader;
import com.github.mvp4g.nalu.client.internal.application.AbstractApplication;
import com.github.mvp4g.nalu.client.internal.application.ComponentCreator;
import com.github.mvp4g.nalu.client.internal.application.ComponentFactory;
import com.github.mvp4g.nalu.client.ui.AbstractComponent;
import com.github.mvp4g.nalu.processor.ProcessorException;
import com.github.mvp4g.nalu.processor.ProcessorUtils;
import com.github.mvp4g.nalu.processor.model.ApplicationMetaModel;
import com.github.mvp4g.nalu.processor.model.intern.ClassNameModel;
import com.github.mvp4g.nalu.processor.model.intern.RouteModel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationGenerator {

  private final static String IMPL_NAME = "Impl";

  private ProcessorUtils        processorUtils;
  private ProcessingEnvironment processingEnvironment;

  @SuppressWarnings("unused")
  private ApplicationGenerator(Builder builder) {
    super();

    this.processingEnvironment = builder.processingEnvironment;

    setUp();
  }

  private void setUp() {
    this.processorUtils = ProcessorUtils.builder()
                                        .processingEnvironment(this.processingEnvironment)
                                        .build();
  }

  public static Builder builder() {
    return new Builder();
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
//                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractApplication.class),
//                                                                              metaModel.getEventBus()
//                                                                                       .getTypeName()))
                                        .superclass(ClassName.get(AbstractApplication.class))
                                        .addModifiers(Modifier.PUBLIC,
                                                      Modifier.FINAL)
                                        .addSuperinterface(metaModel.getApplication()
                                                                    .getTypeName());

    // constructor ...
    MethodSpec constructor = MethodSpec.constructorBuilder()
                                       .addModifiers(Modifier.PUBLIC)
                                       .addStatement("super()")
//                                       .addStatement("super.eventBus = new $N.$N()",
//                                                     metaModel.getEventBus()
//                                                              .getPackage(),
//                                                     metaModel.getEventBus()
//                                                              .getSimpleName() + ApplicationGenerator.IMPL_NAME)
//                                       .addStatement("super.historyOnStart = $L",
//                                                     metaModel.getHistoryOnStart())
//                                       .addStatement("super.encodeToken = $L",
//                                                     metaModel.getEncodeToken())
                                       .build();
    DebugGenerator.builder()
                  .applicationMetaModel(metaModel)
                  .typeSpec(typeSpec)
                  .build()
                  .generate();
    typeSpec.addMethod(constructor);

    ProvidesSelecctorGenerator.builder()
                              .applicationMetaModel(metaModel)
                              .typeSpec(typeSpec)
                              .build()
                              .generate();

    RouteGenerator.builder()
                  .applicationMetaModel(metaModel)
                  .typeSpec(typeSpec)
                  .build()
                  .generate();


    // method "getApplicaitonLoader"
    MethodSpec getApplicaitonLaoderMethod = MethodSpec.methodBuilder("getApplicationLoader")
                                                      .addModifiers(Modifier.PUBLIC)
                                                      .addAnnotation(Override.class)
                                                      .returns(IsApplicationLoader.class)
                                                      .addStatement("return new $T()",
                                                                    metaModel.getLoader()
                                                                             .getTypeName())
                                                      .build();
    typeSpec.addMethod(getApplicaitonLaoderMethod);

    // generate method 'loadComponents()'
    MethodSpec.Builder loadComponentsMethodBuilder = MethodSpec.methodBuilder("loadComponents")
                                                               .addModifiers(Modifier.PUBLIC)
                                                               .addAnnotation(Override.class);
    loadComponentsMethodBuilder.addComment("shell ...")
                               .addStatement("$T shell = new $T()",
                                             ClassName.get(metaModel.getShell()
                                                                    .getPackage(),
                                                           metaModel.getShell()
                                                                    .getSimpleName()),
                                             ClassName.get(metaModel.getShell()
                                                                    .getPackage(),
                                                           metaModel.getShell()
                                                                    .getSimpleName()))
                               .addStatement("shell.setRouter(this.router)")
                               .addStatement("super.shell = shell");
    this.getAllComponents(metaModel.getRoutes())
        .forEach(provider -> {
          loadComponentsMethodBuilder.addComment("create ComponentCreator for: " + provider.getPackage() + "." + provider.getSimpleName())
                                     .addStatement("$T.get().registerComponent($S, $L)",
                                                   ClassName.get(ComponentFactory.class),
                                                   provider.getPackage() + "." + provider.getSimpleName(),
                                                   TypeSpec.anonymousClassBuilder("")
                                                           .addSuperinterface(ComponentCreator.class)
                                                           .addMethod(MethodSpec.methodBuilder("create")
                                                                                .addAnnotation(Override.class)
                                                                                .addModifiers(Modifier.PUBLIC)
                                                                                .returns(AbstractComponent.class)
                                                                                .addStatement("return new $T()",
                                                                                              ClassName.get(provider.getPackage(),
                                                                                                            provider.getSimpleName()))
                                                                                .build())
                                                           .build());
        });
    typeSpec.addMethod(loadComponentsMethodBuilder.build());

    typeSpec.addMethod(MethodSpec.methodBuilder("loadStartRoute")
                                 .addModifiers(Modifier.PUBLIC)
                                 .addAnnotation(Override.class)
                                 .addStatement("this.startRoute = $S",
                                               metaModel.getStartRoute())
                                 .build());

    // generate method 'setShell()'
    typeSpec.addMethod(MethodSpec.methodBuilder("setShell")
                                 .addModifiers(Modifier.PUBLIC)
                                 .addAnnotation(Override.class)
                                 .addStatement("super.shell.setShell()")
                                 .build());


    JavaFile javaFile = JavaFile.builder(metaModel.getApplication()
                                                  .getPackage(),
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(this.processingEnvironment.getFiler());
    } catch (IOException e) {
      throw new ProcessorException("Unable to write generated file: >>" + metaModel.getApplication()
                                                                                   .getSimpleName() + ApplicationGenerator.IMPL_NAME + "<< -> exception: " + e.getMessage());
    }
  }

  private List<ClassNameModel> getAllComponents(List<RouteModel> routes) {
    List<ClassNameModel> models = new ArrayList<>();
    routes.forEach(route -> {
      if (!models.contains(route.getProvider())) {
        models.add(route.getProvider());
      }
    });
    return models;
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
