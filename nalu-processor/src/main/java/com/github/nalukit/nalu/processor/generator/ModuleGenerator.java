/*
 * Copyright (c) 2018 - 2020 - Frank Hossfeld
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

import com.github.nalukit.nalu.client.component.AlwaysLoadComposite;
import com.github.nalukit.nalu.client.component.AlwaysShowPopUp;
import com.github.nalukit.nalu.client.context.ContextDataStore;
import com.github.nalukit.nalu.client.internal.CompositeControllerReference;
import com.github.nalukit.nalu.client.internal.application.BlockControllerFactory;
import com.github.nalukit.nalu.client.internal.application.CompositeFactory;
import com.github.nalukit.nalu.client.internal.application.ControllerCompositeConditionFactory;
import com.github.nalukit.nalu.client.internal.application.ControllerFactory;
import com.github.nalukit.nalu.client.internal.application.PopUpConditionFactory;
import com.github.nalukit.nalu.client.internal.application.PopUpControllerFactory;
import com.github.nalukit.nalu.client.internal.application.ShellFactory;
import com.github.nalukit.nalu.client.internal.constrain.ParameterConstraintRuleFactory;
import com.github.nalukit.nalu.client.internal.module.AbstractModule;
import com.github.nalukit.nalu.client.internal.module.NoModuleLoader;
import com.github.nalukit.nalu.client.internal.route.RouteConfig;
import com.github.nalukit.nalu.client.internal.route.RouterConfiguration;
import com.github.nalukit.nalu.client.internal.route.ShellConfig;
import com.github.nalukit.nalu.client.module.IsModuleLoader;
import com.github.nalukit.nalu.processor.ProcessorConstants;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;
import com.github.nalukit.nalu.processor.model.intern.CompositeModel;
import com.github.nalukit.nalu.processor.model.intern.ControllerModel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ModuleGenerator {

  private final MetaModel             metaModel;
  private final ProcessingEnvironment processingEnvironment;
  private       ProcessorUtils        processorUtils;
  private       Map<String, Integer>  variableCounterMap;

  @SuppressWarnings("unused")
  private ModuleGenerator(Builder builder) {
    super();
    this.processingEnvironment = builder.processingEnvironment;
    this.metaModel             = builder.metaModel;
    setUp();

    this.variableCounterMap = new HashMap<>();
  }

  public static Builder builder() {
    return new Builder();
  }

  private void setUp() {
    this.processorUtils = ProcessorUtils.builder()
                                        .processingEnvironment(this.processingEnvironment)
                                        .build();
  }

  public void generate()
      throws ProcessorException {
    // generate code
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(this.metaModel.getModuleModel()
                                                                    .getModule()
                                                                    .getSimpleName() + ProcessorConstants.MODULE_IMPL)
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractModule.class),
                                                                              this.metaModel.getModuleModel()
                                                                                            .getModuleContext()
                                                                                            .getTypeName()))
                                        .addModifiers(Modifier.PUBLIC,
                                                      Modifier.FINAL)
                                        .addSuperinterface(this.metaModel.getModuleModel()
                                                                         .getModule()
                                                                         .getTypeName());

    // constructor ...
    MethodSpec constructor = MethodSpec.constructorBuilder()
                                       .addModifiers(Modifier.PUBLIC)
                                       .addParameter(ParameterSpec.builder(ClassName.get(ContextDataStore.class),
                                                                           "contextDataStore")
                                                                  .build())
                                       .addStatement("super(contextDataStore)")
                                       .build();
    typeSpec.addMethod(constructor);

    this.generateCreateModuleContext(typeSpec);

    this.generateLoadShellFactory(typeSpec);
    this.generateLoadComposites(typeSpec);
    this.generateLoadContollers(typeSpec);
    this.generateLoadFilters(typeSpec);
    this.generateLoadPopUpFilters(typeSpec);
    this.generateLoadHandlers(typeSpec);
    this.generateLoadParameterCostrainRules(typeSpec);
    this.generateLoadPopUpControllers(typeSpec);
    this.generateLoadBlockControllers(typeSpec);

    this.generateGetShellConfigs(typeSpec);
    this.generateGetRouteConfigs(typeSpec);
    this.generateGetCompositeReferences(typeSpec);
    this.generateGetLoader(typeSpec);

    JavaFile javaFile = JavaFile.builder(this.metaModel.getModuleModel()
                                                       .getModule()
                                                       .getPackage(),
                                         typeSpec.build())
                                .build();
    try {
      //      System.out.println(javaFile.toString());
      javaFile.writeTo(this.processingEnvironment.getFiler());
      //      FileObject fileObject = this.processingEnvironment.getFiler()
      //                                                        .createResource(SOURCE_OUTPUT,
      //                                                                        this.metaModel.getModuleModel()
      //                                                                                      .getModule()
      //                                                                                      .getPackage(),
      //                                                                        metaModel.getApplication()
      //                                                                                 .getSimpleName() + ProcessorConstants.MODULE_IMPL + ".java");
      //      Writer writer = fileObject.openWriter();
      //      writer.write(javaFile.toString());
      //      writer.flush();
      //      writer.close();
    } catch (IOException e) {
      throw new ProcessorException("Unable to write generated file: >>" +
                                   this.metaModel.getModuleModel()
                                                 .getModule()
                                                 .getClassName() +
                                   ProcessorConstants.MODULE_IMPL +
                                   "<< -> exception: " +
                                   e.getMessage());
    }
  }

  private void generateCreateModuleContext(TypeSpec.Builder typeSpec) {
    MethodSpec.Builder createModuleContextMethod = MethodSpec.methodBuilder("createModuleContext")
                                                             .addAnnotation(Override.class)
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .returns(ClassName.get(this.metaModel.getModuleModel()
                                                                                                  .getModuleContext()
                                                                                                  .getPackage(),
                                                                                    this.metaModel.getModuleModel()
                                                                                                  .getModuleContext()
                                                                                                  .getSimpleName()))
                                                             .addStatement("return new $T()",
                                                                           ClassName.get(this.metaModel.getModuleModel()
                                                                                                       .getModuleContext()
                                                                                                       .getPackage(),
                                                                                         this.metaModel.getModuleModel()
                                                                                                       .getModuleContext()
                                                                                                       .getSimpleName()));
    typeSpec.addMethod(createModuleContextMethod.build());
  }

  private void generateLoadShellFactory(TypeSpec.Builder typeSpec) {
    // generate method 'generateLoadShells()'
    MethodSpec.Builder loadShellFactoryMethodBuilder = MethodSpec.methodBuilder("loadShellFactory")
                                                                 .addModifiers(Modifier.PUBLIC)
                                                                 .addAnnotation(Override.class);
    this.metaModel.getShells()
                  .forEach(shellModel -> {
                    // add return statement
                    loadShellFactoryMethodBuilder.addStatement("$T.get().registerShell($S, new $L(router, moduleContext, eventBus))",
                                                               ClassName.get(ShellFactory.class),
                                                               shellModel.getShell()
                                                                         .getPackage() +
                                                               "." +
                                                               shellModel.getShell()
                                                                         .getSimpleName(),
                                                               ClassName.get(shellModel.getShell()
                                                                                       .getPackage(),
                                                                             shellModel.getShell()
                                                                                       .getSimpleName() +
                                                                             ProcessorConstants.CREATOR_IMPL));

                  });
    typeSpec.addMethod(loadShellFactoryMethodBuilder.build());
  }

  private void generateLoadComposites(TypeSpec.Builder typeSpec) {
    // generate method 'loadCompositeController()'
    MethodSpec.Builder loadCompositesMethodBuilder = MethodSpec.methodBuilder("loadCompositeController")
                                                               .addModifiers(Modifier.PUBLIC)
                                                               .addAnnotation(Override.class);
    for (CompositeModel compositeModel : this.metaModel.getCompositeModels()) {
      loadCompositesMethodBuilder.addStatement("$T.get().registerComposite($S, new $L(router, moduleContext, eventBus))",
                                               ClassName.get(CompositeFactory.class),
                                               compositeModel.getProvider()
                                                             .getPackage() +
                                               "." +
                                               compositeModel.getProvider()
                                                             .getSimpleName(),
                                               ClassName.get(compositeModel.getProvider()
                                                                           .getPackage(),
                                                             compositeModel.getProvider()
                                                                           .getSimpleName() + ProcessorConstants.CREATOR_IMPL));
    }
    typeSpec.addMethod(loadCompositesMethodBuilder.build());
  }

  private void generateLoadContollers(TypeSpec.Builder typeSpec) {
    // generate method 'loadComponents()'
    MethodSpec.Builder loadComponentsMethodBuilder = MethodSpec.methodBuilder("loadComponents")
                                                               .addModifiers(Modifier.PUBLIC)
                                                               .addAnnotation(Override.class);
    this.getAllComponents(this.metaModel.getControllers())
        .forEach(controllerModel -> {
          loadComponentsMethodBuilder.addStatement("$T.get().registerController($S, new $L(router, moduleContext, eventBus))",
                                                   ClassName.get(ControllerFactory.class),
                                                   controllerModel.getProvider()
                                                                  .getPackage() +
                                                   "." +
                                                   controllerModel.getProvider()
                                                                  .getSimpleName(),
                                                   ClassName.get(controllerModel.getController()
                                                                                .getPackage(),
                                                                 controllerModel.getController()
                                                                                .getSimpleName() +
                                                                 ProcessorConstants.CREATOR_IMPL));

          if (controllerModel.getComposites()
                             .size() > 0) {
            List<String> generatedConditionClassNames = new ArrayList<>();
            controllerModel.getComposites()
                           .forEach(controllerCompositeModel -> {
                             if (AlwaysLoadComposite.class.getSimpleName()
                                                          .equals(controllerCompositeModel.getCondition()
                                                                                          .getSimpleName())) {
                               loadComponentsMethodBuilder.addStatement("$T.get().registerCondition($S, $S, super.alwaysLoadComposite)",
                                                                        ClassName.get(ControllerCompositeConditionFactory.class),
                                                                        controllerModel.getProvider()
                                                                                       .getPackage() +
                                                                        "." +
                                                                        controllerModel.getProvider()
                                                                                       .getSimpleName(),
                                                                        controllerCompositeModel.getComposite()
                                                                                                .getPackage() +
                                                                        "." +
                                                                        controllerCompositeModel.getComposite()
                                                                                                .getSimpleName());
                             } else {
                               if (!generatedConditionClassNames.contains(controllerCompositeModel.getCondition()
                                                                                                  .getClassName())) {
                                 loadComponentsMethodBuilder.addStatement("$T $L = new $T()",
                                                                          ClassName.get(controllerCompositeModel.getCondition()
                                                                                                                .getPackage(),
                                                                                        controllerCompositeModel.getCondition()
                                                                                                                .getSimpleName()),
                                                                          this.setFirstCharacterToLowerCase(controllerCompositeModel.getCondition()
                                                                                                                                    .getSimpleName()),
                                                                          ClassName.get(controllerCompositeModel.getCondition()
                                                                                                                .getPackage(),
                                                                                        controllerCompositeModel.getCondition()
                                                                                                                .getSimpleName()))
                                                            .addStatement("$L.setContext(super.moduleContext)",
                                                                          this.setFirstCharacterToLowerCase(controllerCompositeModel.getCondition()
                                                                                                                                    .getSimpleName()));
                                 // remember generated condition to avoid creating the same class again!
                                 generatedConditionClassNames.add(controllerCompositeModel.getCondition()
                                                                                          .getClassName());
                               }
                               loadComponentsMethodBuilder.addStatement("$T.get().registerCondition($S, $S, $L)",
                                                                        ClassName.get(ControllerCompositeConditionFactory.class),
                                                                        controllerModel.getProvider()
                                                                                       .getPackage() +
                                                                        "." +
                                                                        controllerModel.getProvider()
                                                                                       .getSimpleName(),
                                                                        controllerCompositeModel.getComposite()
                                                                                                .getPackage() +
                                                                        "." +
                                                                        controllerCompositeModel.getComposite()
                                                                                                .getSimpleName(),
                                                                        this.setFirstCharacterToLowerCase(controllerCompositeModel.getCondition()
                                                                                                                                  .getSimpleName()));
                             }
                           });
          }
        });
    typeSpec.addMethod(loadComponentsMethodBuilder.build());
  }

  private void generateLoadFilters(TypeSpec.Builder typeSpec) {
    // method must always be created!
    MethodSpec.Builder loadFiltersMethod = MethodSpec.methodBuilder("loadFilters")
                                                     .addAnnotation(Override.class)
                                                     .addModifiers(Modifier.PUBLIC)
                                                     .addParameter(ParameterSpec.builder(ClassName.get(RouterConfiguration.class),
                                                                                         "routerConfiguration")
                                                                                .build());

    this.metaModel.getFilters()
                  .forEach(filterModel -> loadFiltersMethod.addStatement("$T $L = new $T()",
                                                                         ClassName.get(filterModel.getFilter()
                                                                                                  .getPackage(),
                                                                                       filterModel.getFilter()
                                                                                                  .getSimpleName()),
                                                                         this.processorUtils.createFullClassName(filterModel.getFilter()
                                                                                                                            .getClassName()),
                                                                         ClassName.get(filterModel.getFilter()
                                                                                                  .getPackage(),
                                                                                       filterModel.getFilter()
                                                                                                  .getSimpleName()))
                                                           .addStatement("$L.setContext(super.moduleContext)",
                                                                         this.processorUtils.createFullClassName(filterModel.getFilter()
                                                                                                                            .getClassName()))
                                                           .addStatement("$L.setEventBus(super.eventBus)",
                                                                         this.processorUtils.createFullClassName(filterModel.getFilter()
                                                                                                                            .getClassName()))
                                                           .addStatement("routerConfiguration.getFilters().add($L)",
                                                                         this.processorUtils.createFullClassName(filterModel.getFilter()
                                                                                                                            .getClassName())));

    typeSpec.addMethod(loadFiltersMethod.build());
  }

  private void generateLoadPopUpFilters(TypeSpec.Builder typeSpec) {
    // method must always be created!
    MethodSpec.Builder loadPopUpFiltersMethod = MethodSpec.methodBuilder("loadPopUpFilters")
                                                          .addAnnotation(Override.class)
                                                          .addModifiers(Modifier.PUBLIC)
                                                          .addParameter(ParameterSpec.builder(ClassName.get(RouterConfiguration.class),
                                                                                              "routerConfiguration")
                                                                                     .build());

    this.metaModel.getPopUpFilters()
                  .forEach(classNameModel -> loadPopUpFiltersMethod.addStatement("$T $L = new $T()",
                                                                                 ClassName.get(classNameModel.getPackage(),
                                                                                               classNameModel.getSimpleName()),
                                                                                 this.processorUtils.createFullClassName(classNameModel.getClassName()),
                                                                                 ClassName.get(classNameModel.getPackage(),
                                                                                               classNameModel.getSimpleName()))
                                                                   .addStatement("$L.setContext(super.context)",
                                                                                 this.processorUtils.createFullClassName(classNameModel.getClassName()))
                                                                   .addStatement("$L.setEventBus(super.eventBus)",
                                                                                 this.processorUtils.createFullClassName(classNameModel.getClassName()))
                                                                   .addStatement("$T.get().registerPopUpFilter($S, $L)",
                                                                                 ClassName.get(PopUpControllerFactory.class),
                                                                                 this.processorUtils.createFullClassName(classNameModel.getClassName()),
                                                                                 this.processorUtils.createFullClassName(classNameModel.getClassName())));

    typeSpec.addMethod(loadPopUpFiltersMethod.build());
  }

  private void generateLoadHandlers(TypeSpec.Builder typeSpec) {
    // method must always be created!
    MethodSpec.Builder loadHandlersMethod = MethodSpec.methodBuilder("loadHandlers")
                                                      .addAnnotation(Override.class)
                                                      .addModifiers(Modifier.PUBLIC);

    this.metaModel.getHandlers()
                  .forEach(handler -> {
                    String variableName = this.processorUtils.createFullClassName(handler.getHandler()
                                                                                         .getPackage(),
                                                                                  handler.getHandler()
                                                                                         .getSimpleName());
                    loadHandlersMethod.addStatement("$T $L = new $T()",
                                                    ClassName.get(handler.getHandler()
                                                                         .getPackage(),
                                                                  handler.getHandler()
                                                                         .getSimpleName()),
                                                    variableName,
                                                    ClassName.get(handler.getHandler()
                                                                         .getPackage(),
                                                                  handler.getHandler()
                                                                         .getSimpleName()))
                                      .addStatement("$L.setContext(super.moduleContext)",
                                                    variableName)
                                      .addStatement("$L.setEventBus(super.eventBus)",
                                                    variableName)
                                      .addStatement("$L.setRouter(super.router)",
                                                    variableName)
                                      .addStatement("$L.bind()",
                                                    variableName);
                  });

    typeSpec.addMethod(loadHandlersMethod.build());
  }

  private void generateLoadParameterCostrainRules(TypeSpec.Builder typeSpec) {
    // generate method 'generateLoadParameterConstraintRules()'
    MethodSpec.Builder loadParameterConstraintRulesMethodBuilder = MethodSpec.methodBuilder("loadParameterConstraintRules")
                                                                             .addModifiers(Modifier.PUBLIC)
                                                                             .addAnnotation(Override.class);
    this.metaModel.getParameterConstraintRules()
                  .forEach(m -> loadParameterConstraintRulesMethodBuilder.addStatement("$T.get().registerParameterConstraintRule($S, new $L())",
                                                                                       ClassName.get(ParameterConstraintRuleFactory.class),
                                                                                       m.getParameterConstraintRule()
                                                                                        .getPackage() +
                                                                                       "." +
                                                                                       m.getParameterConstraintRule()
                                                                                        .getSimpleName(),
                                                                                       ClassName.get(m.getParameterConstraintRule()
                                                                                                      .getPackage(),
                                                                                                     m.getParameterConstraintRule()
                                                                                                      .getSimpleName() +
                                                                                                     ProcessorConstants.IMPL)));
    typeSpec.addMethod(loadParameterConstraintRulesMethodBuilder.build());
  }

  private void generateLoadPopUpControllers(TypeSpec.Builder typeSpec) {
    // method must always be created!
    MethodSpec.Builder loadPopUpControllersMethod = MethodSpec.methodBuilder("loadPopUpControllers")
                                                              .addAnnotation(Override.class)
                                                              .addModifiers(Modifier.PUBLIC);
    List<String> generatedConditionClassNames = new ArrayList<>();
    this.metaModel.getPopUpControllers()
                  .forEach(popUpControllerModel -> {
                    loadPopUpControllersMethod.addStatement("$T.get().registerPopUpController($S, new $L(super.router, super.moduleContext, super.eventBus))",
                                                            ClassName.get(PopUpControllerFactory.class),
                                                            popUpControllerModel.getName(),
                                                            ClassName.get(popUpControllerModel.getController()
                                                                                              .getPackage(),
                                                                          popUpControllerModel.getController()
                                                                                              .getSimpleName() +
                                                                          ProcessorConstants.CREATOR_IMPL));
                    if (AlwaysShowPopUp.class.getSimpleName()
                                             .equals(popUpControllerModel.getCondition()
                                                                         .getSimpleName())) {
                      loadPopUpControllersMethod.addStatement("$T.get().registerCondition($S,  super.alwaysShowPopUp)",
                                                              ClassName.get(PopUpConditionFactory.class),
                                                              popUpControllerModel.getName());
                    } else {
                      String conditionVariableName;
                      if (generatedConditionClassNames.contains(popUpControllerModel.getCondition()
                                                                                    .getClassName())) {
                        conditionVariableName = this.setFirstCharacterToLowerCase(popUpControllerModel.getCondition()
                                                                                                      .getSimpleName()) +
                                                this.getNameWithVariableCount(popUpControllerModel.getCondition(),
                                                                              false);
                      } else {
                        conditionVariableName = this.setFirstCharacterToLowerCase(popUpControllerModel.getCondition()
                                                                                                      .getSimpleName()) +
                                                this.getNameWithVariableCount(popUpControllerModel.getCondition(),
                                                                              true);

                        loadPopUpControllersMethod.addStatement("$T $L = new $T()",
                                                                ClassName.get(popUpControllerModel.getCondition()
                                                                                                  .getPackage(),
                                                                              popUpControllerModel.getCondition()
                                                                                                  .getSimpleName()),
                                                                conditionVariableName,
                                                                ClassName.get(popUpControllerModel.getCondition()
                                                                                                  .getPackage(),
                                                                              popUpControllerModel.getCondition()
                                                                                                  .getSimpleName()))
                                                  .addStatement("$L.setContext(super.context)",
                                                                conditionVariableName);
                        // remember generated condition to avoid creating the same class again!
                        generatedConditionClassNames.add(popUpControllerModel.getCondition()
                                                                             .getClassName());
                      }
                      loadPopUpControllersMethod.addStatement("$T.get().registerCondition($S,  $L)",
                                                              ClassName.get(PopUpConditionFactory.class),
                                                              conditionVariableName);
                    }
                  });
    typeSpec.addMethod(loadPopUpControllersMethod.build());
  }

  private void generateLoadBlockControllers(TypeSpec.Builder typeSpec) {
    // method must always be created!
    MethodSpec.Builder loadBlockControllersMethod = MethodSpec.methodBuilder("loadBlockControllers")
                                                              .addAnnotation(Override.class)
                                                              .addModifiers(Modifier.PUBLIC);
    this.metaModel.getBlockControllers()
                  .forEach(blockControllerModel -> loadBlockControllersMethod.addStatement("$T.get().registerBlockController($S, new $L(super.router, super.moduleContext, super.eventBus))",
                                                                                           ClassName.get(BlockControllerFactory.class),
                                                                                           blockControllerModel.getName(),
                                                                                           ClassName.get(blockControllerModel.getController()
                                                                                                                             .getPackage(),
                                                                                                         blockControllerModel.getController()
                                                                                                                             .getSimpleName() +
                                                                                                         ProcessorConstants.CREATOR_IMPL)));
    typeSpec.addMethod(loadBlockControllersMethod.build());
  }

  private void generateGetShellConfigs(TypeSpec.Builder typeSpec) {
    // generate method 'generateLoadShells()'
    MethodSpec.Builder loadShellConfigMethodBuilder = MethodSpec.methodBuilder("getShellConfigs")
                                                                .addModifiers(Modifier.PUBLIC)
                                                                .addAnnotation(Override.class)
                                                                .returns(ParameterizedTypeName.get(ClassName.get(List.class),
                                                                                                   ClassName.get(ShellConfig.class)))
                                                                .addStatement("$T<$T> list = new $T<>()",
                                                                              ClassName.get(List.class),
                                                                              ClassName.get(ShellConfig.class),
                                                                              ClassName.get(ArrayList.class));
    this.metaModel.getShells()
                  .forEach(shellModel -> loadShellConfigMethodBuilder.addStatement("list.add(new $T($S, $S))",
                                                                                   ClassName.get(ShellConfig.class),
                                                                                   "/" + shellModel.getName(),
                                                                                   shellModel.getShell()
                                                                                             .getClassName()));
    loadShellConfigMethodBuilder.addStatement("return list");
    typeSpec.addMethod(loadShellConfigMethodBuilder.build());
  }

  private void generateGetRouteConfigs(TypeSpec.Builder typeSpec) {
    // generate method 'generateLoadShells()'
    MethodSpec.Builder loadRouteConfigMethodBuilder = MethodSpec.methodBuilder("getRouteConfigs")
                                                                .addModifiers(Modifier.PUBLIC)
                                                                .addAnnotation(Override.class)
                                                                .returns(ParameterizedTypeName.get(ClassName.get(List.class),
                                                                                                   ClassName.get(RouteConfig.class)))
                                                                .addStatement("$T<$T> list = new $T<>()",
                                                                              ClassName.get(List.class),
                                                                              ClassName.get(RouteConfig.class),
                                                                              ClassName.get(ArrayList.class));
    this.metaModel.getControllers()
                  .forEach(controllerModel -> controllerModel.getRoute()
                                                             .forEach(route -> loadRouteConfigMethodBuilder.addStatement("list.add(new $T($S, $T.asList(new String[]{$L}), $S, $S))",
                                                                                                                         ClassName.get(RouteConfig.class),
                                                                                                                         createRoute(route),
                                                                                                                         ClassName.get(Arrays.class),
                                                                                                                         createParaemter(controllerModel.getParameters()),
                                                                                                                         controllerModel.getSelector(),
                                                                                                                         controllerModel.getProvider()
                                                                                                                                        .getClassName())));
    loadRouteConfigMethodBuilder.addStatement("return list");
    typeSpec.addMethod(loadRouteConfigMethodBuilder.build());
  }

  private void generateGetCompositeReferences(TypeSpec.Builder typeSpec) {
    MethodSpec.Builder getCompositeReferencesMethod = MethodSpec.methodBuilder("getCompositeReferences")
                                                                .addModifiers(Modifier.PUBLIC)
                                                                .addAnnotation(Override.class)
                                                                .returns(ParameterizedTypeName.get(ClassName.get(List.class),
                                                                                                   ClassName.get(CompositeControllerReference.class)))
                                                                .addStatement("$T<$T> list = new $T<>()",
                                                                              ClassName.get(List.class),
                                                                              ClassName.get(CompositeControllerReference.class),
                                                                              ClassName.get(ArrayList.class));
    this.metaModel.getControllers()
                  .forEach(controllerModel -> controllerModel.getComposites()
                                                             .forEach(controllerCompositeModel -> getCompositeReferencesMethod.addStatement("list.add(new $T($S, $S, $S, $S, $L))",
                                                                                                                                            ClassName.get(CompositeControllerReference.class),
                                                                                                                                            controllerModel.getProvider()
                                                                                                                                                           .getClassName(),
                                                                                                                                            controllerCompositeModel.getName(),
                                                                                                                                            controllerCompositeModel.getComposite()
                                                                                                                                                                    .getClassName(),
                                                                                                                                            controllerCompositeModel.getSelector(),
                                                                                                                                            controllerCompositeModel.isScopeGlobal())));
    getCompositeReferencesMethod.addStatement("return list");
    typeSpec.addMethod(getCompositeReferencesMethod.build());
  }

  private void generateGetLoader(TypeSpec.Builder typeSpec) {
    MethodSpec.Builder getLoaderMethod = MethodSpec.methodBuilder("createModuleLoader")
                                                   .addModifiers(Modifier.PUBLIC)
                                                   .addAnnotation(Override.class)
                                                   .returns(ParameterizedTypeName.get(ClassName.get(IsModuleLoader.class),
                                                                                      this.metaModel.getModuleModel()
                                                                                                    .getModuleContext()
                                                                                                    .getTypeName()));
    if (!this.metaModel.getModuleModel()
                       .getModuleLoader()
                       .getPackage()
                       .equals(NoModuleLoader.class.getPackage()
                                                   .getName()) &&
        !this.metaModel.getModuleModel()
                       .getModuleLoader()
                       .getPackage()
                       .equals(NoModuleLoader.class.getSimpleName())) {
      getLoaderMethod.addStatement("$T loader = new $T()",
                                   ClassName.get(this.metaModel.getModuleModel()
                                                               .getModuleLoader()
                                                               .getPackage(),
                                                 this.metaModel.getModuleModel()
                                                               .getModuleLoader()
                                                               .getSimpleName()),
                                   ClassName.get(this.metaModel.getModuleModel()
                                                               .getModuleLoader()
                                                               .getPackage(),
                                                 this.metaModel.getModuleModel()
                                                               .getModuleLoader()
                                                               .getSimpleName()))
                     .addStatement("loader.setContext(super.moduleContext)")
                     .addStatement("return loader");
    } else {
      getLoaderMethod.addStatement("return null");
    }
    typeSpec.addMethod(getLoaderMethod.build());
  }

  private List<ControllerModel> getAllComponents(List<ControllerModel> routes) {
    List<ControllerModel> models = new ArrayList<>();
    routes.forEach(route -> {
      if (!contains(models,
                    route)) {
        models.add(route);
      }
    });
    return models;
  }

  private String createRoute(String route) {
    if (route.startsWith("/")) {
      return route;
    } else {
      return "/" + route;
    }
  }

  private String createParaemter(List<String> parameters) {
    StringBuilder sb = new StringBuilder();
    IntStream.range(0,
                    parameters.size())
             .forEach(i -> {
               sb.append("\"")
                 .append(parameters.get(i))
                 .append("\"");
               if (i != parameters.size() - 1) {
                 sb.append(", ");
               }
             });
    return sb.toString();
  }

  private boolean contains(List<ControllerModel> models,
                           ControllerModel controllerModel) {
    return models.stream()
                 .anyMatch(model -> model.getProvider()
                                         .equals(controllerModel.getProvider()));
  }

  private String setFirstCharacterToLowerCase(String className) {
    return className.substring(0,
                               1)
                    .toLowerCase() + className.substring(1);
  }

  /**
   * Created a String with a number at the end to get unique condition
   * variable names
   *
   * @param classNameModel the condition class name
   * @return uniques string with number
   */
  private String getNameWithVariableCount(ClassNameModel classNameModel,
                                          boolean createNew) {
    if (createNew) {
      // new condition class!
      if (this.variableCounterMap.get(classNameModel.getClassName()) == null) {
        this.variableCounterMap.put(classNameModel.getClassName(),
                                    1);
        return "_1";
      }
      // already used condition class
      Integer counter    = this.variableCounterMap.get(classNameModel.getClassName());
      Integer newCounter = counter + 1;
      this.variableCounterMap.put(classNameModel.getClassName(),
                                  newCounter);
      return "_" + newCounter;
    } else {
      Integer count = this.variableCounterMap.get(classNameModel.getClassName());
      return "_" + count;
    }
  }

  public static final class Builder {

    MetaModel metaModel;

    ProcessingEnvironment processingEnvironment;

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

    public ModuleGenerator build() {
      return new ModuleGenerator(this);
    }

  }

}
