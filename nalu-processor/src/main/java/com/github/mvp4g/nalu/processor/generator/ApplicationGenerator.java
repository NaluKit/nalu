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
import com.github.mvp4g.nalu.client.component.AbstractComponentController;
import com.github.mvp4g.nalu.client.exception.RoutingInterceptionException;
import com.github.mvp4g.nalu.client.internal.ClientLogger;
import com.github.mvp4g.nalu.client.internal.application.AbstractApplication;
import com.github.mvp4g.nalu.client.internal.application.ControllerCreator;
import com.github.mvp4g.nalu.client.internal.application.ControllerFactory;
import com.github.mvp4g.nalu.client.internal.application.NoApplicationLoader;
import com.github.mvp4g.nalu.processor.ProcessorException;
import com.github.mvp4g.nalu.processor.ProcessorUtils;
import com.github.mvp4g.nalu.processor.model.ApplicationMetaModel;
import com.github.mvp4g.nalu.processor.model.HashResultModel;
import com.github.mvp4g.nalu.processor.model.intern.ControllerModel;
import com.squareup.javapoet.*;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    RouteGenerator.builder()
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

    // method "getApplicaitonLoader"
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
                               .addStatement("shell.setEventBus(this.eventBus)")
                               .addStatement("shell.setContext(this.context)")
                               .addStatement("super.shell = shell")
                               .addStatement("super.router.setShell(this.shell)")
                               .addStatement("shell.bind()")
                               .addStatement("$T.get().logDetailed(\"AbstractApplicationImpl: shell created\", 1)",
                                             ClassName.get(ClientLogger.class));
    for (ControllerModel controllerModel : this.getAllComponents(metaModel.getRoutes())) {
      MethodSpec.Builder createMethod = MethodSpec.methodBuilder("create")
                                                  .addAnnotation(Override.class)
                                                  .addModifiers(Modifier.PUBLIC)
                                                  .addParameter(ParameterSpec.builder(String[].class,
                                                                                      "parms")
                                                                             .build())
                                                  .varargs(true)
                                                  .returns(ParameterizedTypeName.get(ClassName.get(AbstractComponentController.class),
                                                                                     metaModel.getContext()
                                                                                              .getTypeName(),
                                                                                     controllerModel.getComponentInterface()
                                                                                                    .getTypeName(),
                                                                                     metaModel.getComponentType()
                                                                                              .getTypeName()))
                                                  .addException(ClassName.get(RoutingInterceptionException.class))
                                                  .addStatement("$T sb01 = new $T();",
                                                                ClassName.get(StringBuilder.class),
                                                                ClassName.get(StringBuilder.class))
                                                  .addStatement("sb01.append(\"controller >>$L<< --> will be created\")",
                                                                controllerModel.getProvider()
                                                                               .getPackage() +
                                                                    "." +
                                                                    controllerModel.getProvider()
                                                                                   .getSimpleName())
                                                  .addStatement("$T.get().logSimple(sb01.toString(), 1)",
                                                                ClassName.get(ClientLogger.class))
                                                  .addStatement("$T controller = new $T()",
                                                                ClassName.get(controllerModel.getProvider()
                                                                                             .getPackage(),
                                                                              controllerModel.getProvider()
                                                                                             .getSimpleName()),
                                                                ClassName.get(controllerModel.getProvider()
                                                                                             .getPackage(),
                                                                              controllerModel.getProvider()
                                                                                             .getSimpleName()))
                                                  .addStatement("controller.setContext(context)")
                                                  .addStatement("controller.setEventBus(eventBus)")
                                                  .addStatement("controller.setRouter(router)")
                                                  .addStatement("sb01 = new $T();",
                                                                ClassName.get(StringBuilder.class))
                                                  .addStatement("sb01.append(\"controller >>\").append(controller.getClass().getCanonicalName()).append(\"<< --> created and data injected\")")
                                                  .addStatement("$T.get().logDetailed(sb01.toString(), 2)",
                                                                ClassName.get(ClientLogger.class))
                                                  .addStatement("$T component = new $T()",
                                                                ClassName.get(controllerModel.getComponentInterface()
                                                                                             .getPackage(),
                                                                              controllerModel.getComponentInterface()
                                                                                             .getSimpleName()),
                                                                ClassName.get(controllerModel.getComponent()
                                                                                             .getPackage(),
                                                                              controllerModel.getComponent()
                                                                                             .getSimpleName()))
                                                  .addStatement("component.setController(controller)")
                                                  .addStatement("sb01 = new $T();",
                                                                ClassName.get(StringBuilder.class))
                                                  .addStatement("sb01.append(\"component >>\").append(component.getClass().getCanonicalName()).append(\"<< --> created and controller instance injected\")")
                                                  .addStatement("$T.get().logDetailed(sb01.toString(), 2)",
                                                                ClassName.get(ClientLogger.class))
                                                  .addStatement("controller.setComponent(component)")
                                                  .addStatement("sb01 = new $T();",
                                                                ClassName.get(StringBuilder.class))
                                                  .addStatement("sb01.append(\"controller >>\").append(controller.getClass().getCanonicalName()).append(\"<< --> instance of >>\").append(component.getClass().getCanonicalName()).append(\"<< injected\")")
                                                  .addStatement("$T.get().logDetailed(sb01.toString(), 2)",
                                                                ClassName.get(ClientLogger.class))
                                                  .addStatement("component.render()")
                                                  .addStatement("sb01 = new $T();",
                                                                ClassName.get(StringBuilder.class))
                                                  .addStatement("sb01.append(\"component >>\").append(component.getClass().getCanonicalName()).append(\"<< --> rendered\")")
                                                  .addStatement("$T.get().logDetailed(sb01.toString(), 2)",
                                                                ClassName.get(ClientLogger.class))
                                                  .addStatement("component.bind()")
                                                  .addStatement("sb01 = new $T();",
                                                                ClassName.get(StringBuilder.class))
                                                  .addStatement("sb01.append(\"component >>\").append(component.getClass().getCanonicalName()).append(\"<< --> binded\")")
                                                  .addStatement("$T.get().logDetailed(sb01.toString(), 2)",
                                                                ClassName.get(ClientLogger.class))
                                                  .addStatement("$T.get().logSimple(\"controller >>$L<< created for route >>$L<<\", 1)",
                                                                ClassName.get(ClientLogger.class),
                                                                controllerModel.getComponent()
                                                                               .getClassName(),
                                                                controllerModel.getRoute());

      HashResultModel hashResultModel = parseRoute(controllerModel);
      if (hashResultModel.getParameterValues() != null &&
          hashResultModel.getParameterValues()
                         .size() > 0) {
        createMethod.beginControlFlow("if (parms != null)");
        for (int i = 0; i <
            hashResultModel.getParameterValues()
                           .size(); i++) {
          String parameter = hashResultModel.getParameterValues()
                                            .get(i);
          createMethod.beginControlFlow("if (parms.length >= " + Integer.toString(i + 1) + ")")
                      .addStatement("controller.set" + processorUtils.setFirstCharacterToUpperCase(parameter) + "(parms[" + Integer.toString(i) + "])")
                      .endControlFlow();
        }
        createMethod.endControlFlow();
      }

      createMethod.addStatement("return controller");

      loadComponentsMethodBuilder.addComment("create ControllerCreator for: " +
                                                 controllerModel.getProvider()
                                                                .getPackage() +
                                                 "." +
                                                 controllerModel.getProvider()
                                                                .getSimpleName())
                                 .addStatement("$T.get().registerController($S, $L)",
                                               ClassName.get(ControllerFactory.class),
                                               controllerModel.getProvider()
                                                              .getPackage() +
                                                   "." +
                                                   controllerModel.getProvider()
                                                                  .getSimpleName(),
                                               TypeSpec.anonymousClassBuilder("")
                                                       .addSuperinterface(ControllerCreator.class)
                                                       .addMethod(createMethod.build())
                                                       .build());
    }
    typeSpec.addMethod(loadComponentsMethodBuilder.build());

    typeSpec.addMethod(MethodSpec.methodBuilder("loadDefaultRoutes")
                                 .addModifiers(Modifier.PUBLIC)
                                 .addAnnotation(Override.class)
                                 .addStatement("this.startRoute = $S",
                                               metaModel.getStartRoute())
                                 .addStatement("this.routeErrorRoute = $S",
                                               metaModel.getRouteErrorRoute())
                                 .build());

    // generate method 'attachShell()'
    typeSpec.addMethod(MethodSpec.methodBuilder("attachShell")
                                 .addModifiers(Modifier.PUBLIC)
                                 .addAnnotation(Override.class)
                                 .addStatement("super.shell.attachShell()")
                                 .build());

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

  private HashResultModel parseRoute(ControllerModel model)
      throws ProcessorException {
    // handle initial route "/" seperately
    if ("/".equals(model.getRoute())) {
      return new HashResultModel(model.getRoute());
    }
    // handle route
    String routeValue = model.getRoute();
    StringBuilder route = new StringBuilder();
    List<String> parameters = new ArrayList<>();
    // extract route first:
    if (routeValue.startsWith("/")) {
      routeValue = routeValue.substring(1);
    }
    String[] splits = routeValue.split("/");
    for (String s : splits) {
      // TODO TESTEN !!!!!!
      // handle "//" -> not allowed
      if (s.length() == 0) {
        throw new ProcessorException("Nalu-Processor: controler >>" +
                                         model.getController()
                                              .getClassName() +
                                         "<<  - illegal route >>" +
                                         route +
                                         "<< -> '//' not allowed!");
      }
      // check if it is a parameter definition (starting with ':' at first position)
      if (s.startsWith(":")) {
        // starts with a parameter ==> error
        if (route.length() == 0) {
          throw new ProcessorException("Nalu-Processor: controler >>" +
                                           model.getController()
                                                .getClassName() +
                                           "<<  - illegal route >>" +
                                           route +
                                           "<< -> route cannot start with parameter");
        }
        if (s.length() == 1) {
          throw new ProcessorException("Nalu-Processor: controler >>" +
                                           model.getController()
                                                .getClassName() +
                                           "<<  - illegal route >>" +
                                           route +
                                           "<< -> illegal parameter name!");
        }
        parameters.add(s.substring(1));
      } else {
        route.append("/")
             .append(s);
      }
    }
    // check for empty route!
    if (route.equals("")) {
      throw new ProcessorException("Nalu-Processor: controler >>" +
                                       model.getController()
                                            .getClassName() +
                                       "<<  - no route defined  route >>" +
                                       route +
                                       "<< -> illegal parameter name!");
    }
    HashResultModel hashResultModel = new HashResultModel();
    hashResultModel.setRoute(route.toString());
    hashResultModel.setParameterValues(parameters);
    //
    //    // extract route first:
    //    if (route.startsWith("/")) {
    //      routeValue = routeValue.substring(1);
    //    }
    //    if (routeValue.contains("/")) {
    //      hashResultModel.setRoute(routeValue.substring(0,
    //                                                    routeValue.indexOf("/")));
    //      String parametersFromHash = routeValue.substring(routeValue.indexOf("/") + 2);
    //      // lets get the parameters!
    //      hashResultModel.setParameterValues(Stream.of(parametersFromHash.split("/:"))
    //                                               .collect(Collectors.toList()));
    //    } else {
    //      hashResultModel.setRoute(routeValue);
    //    }

    return hashResultModel;
  }

  private boolean contains(List<ControllerModel> models,
                           ControllerModel controllerModel) {
    return models.stream()
                 .anyMatch(model -> model.getProvider()
                                         .equals(controllerModel.getProvider()));
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
