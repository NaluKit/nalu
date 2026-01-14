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

package io.github.nalukit.nalu.processor.generator;

import io.github.nalukit.nalu.client.application.IsLoader;
import io.github.nalukit.nalu.client.application.event.LogEvent;
import io.github.nalukit.nalu.client.internal.NoCustomAlertPresenter;
import io.github.nalukit.nalu.client.internal.NoCustomConfirmPresenter;
import io.github.nalukit.nalu.client.internal.application.AbstractApplication;
import io.github.nalukit.nalu.client.internal.application.DefaultLoader;
import io.github.nalukit.nalu.client.plugin.IsCustomAlertPresenter;
import io.github.nalukit.nalu.client.plugin.IsCustomConfirmPresenter;
import io.github.nalukit.nalu.processor.ProcessorConstants;
import io.github.nalukit.nalu.processor.ProcessorException;
import io.github.nalukit.nalu.processor.ProcessorUtils;
import io.github.nalukit.nalu.processor.model.MetaModel;
import io.github.nalukit.nalu.processor.util.BuildWithNaluCommentProvider;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.Date;

public class ApplicationGenerator {

  private final static String IMPL_NAME = "Impl";

  private ProcessorUtils processorUtils;

  private final ProcessingEnvironment processingEnvironment;

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
                                        .addJavadoc(BuildWithNaluCommentProvider.INSTANCE.getGeneratedComment())
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractApplication.class),
                                                                              metaModel.getContext()
                                                                                       .getTypeName()))
                                        .addModifiers(Modifier.PUBLIC,
                                                      Modifier.FINAL)
                                        .addSuperinterface(metaModel.getApplication()
                                                                    .getTypeName());

    // constructor ...
    MethodSpec.Builder constructor = MethodSpec.constructorBuilder()
                                               .addModifiers(Modifier.PUBLIC)
                                               .addStatement("super()")
                                               .addStatement("super.context = new $N.$N()",
                                                             metaModel.getContext()
                                                                      .getPackage(),
                                                             metaModel.getContext()
                                                                      .getSimpleName());
    if (metaModel.isExtendingIsModuleContext()) {
      String applicationVersion = System.getProperty("nalu.application.version");
      if (applicationVersion == null) {
        applicationVersion = metaModel.getApplicationVersion();
      }
      constructor.addStatement("super.context.setApplicationVersion($S)",
                               applicationVersion)
                 .addStatement("super.context.setApplicationBuildTime(new $T($LL))",
                               ClassName.get(Date.class),
                               System.currentTimeMillis());
    }
    typeSpec.addMethod(constructor.build());

    LoggerGenerator.builder()
                   .metaModel(metaModel)
                   .typeSpec(typeSpec)
                   .build()
                   .generate();

    typeSpec.addMethod(MethodSpec.methodBuilder("logProcessorVersion")
                                 .addAnnotation(ClassName.get(Override.class))
                                 .addModifiers(Modifier.PUBLIC)
                                 .addStatement("this.eventBus.fireEvent($T.create()" +
                                               ".sdmOnly(true)" +
                                               ".addMessage(\"=================================================================================\")" +
                                               ".addMessage(\"Nalu processor version  >>$L<< used to generate this source\")" +
                                               ".addMessage(\"=================================================================================\")" +
                                               ".addMessage(\"\"))",
                                               ClassName.get(LogEvent.class),
                                               ProcessorConstants.PROCESSOR_VERSION)
                                 .build());

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

    BlockControllerGenerator.builder()
                            .metaModel(metaModel)
                            .typeSpec(typeSpec)
                            .build()
                            .generate();

    PopUpControllerGenerator.builder()
                            .metaModel(metaModel)
                            .typeSpec(typeSpec)
                            .build()
                            .generate();

    PopUpFiltersGenerator.builder()
                         .processingEnvironment(this.processingEnvironment)
                         .metaModel(metaModel)
                         .typeSpec(typeSpec)
                         .build()
                         .generate();

    ErrorPopUpControllerGenerator.builder()
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

    ParameterConstraintRuleRegisterGenerator.builder()
                                            .metaModel(metaModel)
                                            .typeSpec(typeSpec)
                                            .build()
                                            .generate();

    // need to be called!
    // even if the app has no modules,
    // a empty method has to be created!
    ModulesGenerator.builder()
                    .metaModel(metaModel)
                    .typeSpec(typeSpec)
                    .build()
                    .generate();

    // method "getLoader"
    MethodSpec.Builder getLoaderMethod = MethodSpec.methodBuilder("getLoader")
                                                   .addModifiers(Modifier.PUBLIC)
                                                   .addAnnotation(Override.class)
                                                   .returns(ParameterizedTypeName.get(ClassName.get(IsLoader.class),
                                                                                      metaModel.getContext()
                                                                                               .getTypeName()));
    if (DefaultLoader.class.getCanonicalName()
                           .equals(metaModel.getLoader()
                                            .getClassName())) {
      getLoaderMethod.addStatement("return null");
    } else {
      getLoaderMethod.addStatement("return new $T()",
                                   metaModel.getLoader()
                                            .getTypeName());
    }
    typeSpec.addMethod(getLoaderMethod.build());

    // method "getPostLoader"
    MethodSpec.Builder getPostLoaderMethod = MethodSpec.methodBuilder("getPostLoader")
                                                       .addModifiers(Modifier.PUBLIC)
                                                       .addAnnotation(Override.class)
                                                       .returns(ParameterizedTypeName.get(ClassName.get(IsLoader.class),
                                                                                          metaModel.getContext()
                                                                                                   .getTypeName()));
    if (DefaultLoader.class.getCanonicalName()
                           .equals(metaModel.getPostLoader()
                                            .getClassName())) {
      getPostLoaderMethod.addStatement("return null");
    } else {
      getPostLoaderMethod.addStatement("return new $T()",
                                       metaModel.getPostLoader()
                                                .getTypeName());
    }
    typeSpec.addMethod(getPostLoaderMethod.build());

    // method "getCustomAlertPresenter"
    MethodSpec.Builder getCustomAlertPresenterMethod = MethodSpec.methodBuilder("getCustomAlertPresenter")
                                                                 .addModifiers(Modifier.PUBLIC)
                                                                 .addAnnotation(Override.class)
                                                                 .returns(ClassName.get(IsCustomAlertPresenter.class));
    if (NoCustomAlertPresenter.class.getCanonicalName()
                                    .equals(metaModel.getCustomAlertPresenter()
                                                     .getClassName())) {
      getCustomAlertPresenterMethod.addStatement("return null");
    } else {
      getCustomAlertPresenterMethod.addStatement("return new $T()",
                                                 metaModel.getCustomAlertPresenter()
                                                          .getTypeName());
    }
    typeSpec.addMethod(getCustomAlertPresenterMethod.build());

    // method "getCustomConfirmPresenter"
    MethodSpec.Builder getCustomConfirmPresenterMethod = MethodSpec.methodBuilder("getCustomConfirmPresenter")
                                                                   .addModifiers(Modifier.PUBLIC)
                                                                   .addAnnotation(Override.class)
                                                                   .returns(ClassName.get(IsCustomConfirmPresenter.class));
    if (NoCustomConfirmPresenter.class.getCanonicalName()
                                      .equals(metaModel.getCustomConfirmPresenter()
                                                       .getClassName())) {
      getCustomConfirmPresenterMethod.addStatement("return null");
    } else {
      getCustomConfirmPresenterMethod.addStatement("return new $T()",
                                                   metaModel.getCustomConfirmPresenter()
                                                            .getTypeName());
    }
    typeSpec.addMethod(getCustomConfirmPresenterMethod.build());

    generateLoadDefaultsRoutes(typeSpec,
                               metaModel);
    generateLoadIllegalRouteTarget(typeSpec,
                                   metaModel);
    generateIsHandlingBaseHref(typeSpec,
                               metaModel);
    generateHasHistoryMethod(typeSpec,
                             metaModel);
    generateIsUsingHashMethod(typeSpec,
                              metaModel);
    generateIsUsingColonForParametersInUrl(typeSpec,
                                           metaModel);
    generateIsStayOnSide(typeSpec,
                         metaModel);
    generateIsTrailingSlash(typeSpec,
                            metaModel);

    JavaFile javaFile = JavaFile.builder(metaModel.getGenerateToPackage(),
                                         typeSpec.build())
                                .build();
    try {
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
                                          MetaModel metaModel) {
    typeSpec.addMethod(MethodSpec.methodBuilder("loadDefaultRoutes")
                                 .addModifiers(Modifier.PUBLIC)
                                 .addAnnotation(Override.class)
                                 .addStatement("this.startRoute = $S",
                                               metaModel.getStartRoute())
                                 .build());
  }

  private void generateLoadIllegalRouteTarget(TypeSpec.Builder typeSpec,
                                              MetaModel metaModel) {
    typeSpec.addMethod(MethodSpec.methodBuilder("loadIllegalRouteTarget")
                                 .addModifiers(Modifier.PUBLIC)
                                 .addAnnotation(Override.class)
                                 .addStatement("this.illegalRouteTarget = $S",
                                               metaModel.getIllegalRouteTarget())
                                 .build());
  }

  private void generateIsHandlingBaseHref(TypeSpec.Builder typeSpec,
                                          MetaModel metaModel) {
    typeSpec.addMethod(MethodSpec.methodBuilder("isHandlingBaseHref")
                                 .addModifiers(Modifier.PUBLIC)
                                 .addAnnotation(Override.class)
                                 .returns(boolean.class)
                                 .addStatement("return $L",
                                               metaModel.hasBaseHref() ?
                                               "true" :
                                               "false")
                                 .build());
  }

  private void generateHasHistoryMethod(TypeSpec.Builder typeSpec,
                                        MetaModel metaModel) {
    typeSpec.addMethod(MethodSpec.methodBuilder("isUsingHistory")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .returns(boolean.class)
                                 .addStatement("return $L",
                                               metaModel.isUsingHistory() ?
                                               "true" :
                                               "false")
                                 .build());
  }

  private void generateIsUsingHashMethod(TypeSpec.Builder typeSpec,
                                         MetaModel metaModel) {
    typeSpec.addMethod(MethodSpec.methodBuilder("isUsingHash")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .returns(boolean.class)
                                 .addStatement("return $L",
                                               metaModel.isUsingHash() ?
                                               "true" :
                                               "false")
                                 .build());
  }

  private void generateIsUsingColonForParametersInUrl(TypeSpec.Builder typeSpec,
                                                      MetaModel metaModel) {
    typeSpec.addMethod(MethodSpec.methodBuilder("isUsingColonForParametersInUrl")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .returns(boolean.class)
                                 .addStatement("return $L",
                                               metaModel.isUsingColonForParametersInUrl() ?
                                               "true" :
                                               "false")
                                 .build());
  }

  private void generateIsStayOnSide(TypeSpec.Builder typeSpec,
                                    MetaModel metaModel) {
    typeSpec.addMethod(MethodSpec.methodBuilder("isStayOnSide")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .returns(boolean.class)
                                 .addStatement("return $L",
                                               metaModel.isStayOnSide() ?
                                               "true" :
                                               "false")
                                 .build());
  }

  private void generateIsTrailingSlash(TypeSpec.Builder typeSpec,
                                    MetaModel metaModel) {
    typeSpec.addMethod(MethodSpec.methodBuilder("isUsingTrailingSlash")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .returns(boolean.class)
                                 .addStatement("return $L",
                                               metaModel.isUsingTrailingSlash() ?
                                               "true" :
                                               "false")
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
