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
import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.AbstractControllerCreator;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.application.ControllerFactory;
import com.github.nalukit.nalu.client.internal.application.ControllerInstance;
import com.github.nalukit.nalu.client.internal.application.IsControllerCreator;
import com.github.nalukit.nalu.processor.ProcessorConstants;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.model.ApplicationMetaModel;
import com.github.nalukit.nalu.processor.model.intern.ControllerModel;
import com.squareup.javapoet.*;
import org.gwtproject.event.shared.SimpleEventBus;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ControllerCreatorGenerator {

  private ApplicationMetaModel applicationMetaModel;

  private TypeSpec.Builder typeSpec;

  private ProcessingEnvironment processingEnvironment;

  private ControllerModel controllerModel;

  @SuppressWarnings("unused")
  private ControllerCreatorGenerator() {
  }

  private ControllerCreatorGenerator(Builder builder) {
    this.applicationMetaModel = builder.applicationMetaModel;
    this.typeSpec = builder.typeSpec;
    this.processingEnvironment = builder.processingEnvironment;
    this.controllerModel = builder.controllerModel;
  }

  public static Builder builder() {
    return new Builder();
  }

  public void generate()
      throws ProcessorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(controllerModel.getController()
                                                                     .getSimpleName() + ProcessorConstants.CREATOR_IMPL)
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractControllerCreator.class),
                                                                              applicationMetaModel.getContext()
                                                                                                  .getTypeName()))
                                        .addModifiers(Modifier.PUBLIC,
                                                      Modifier.FINAL)
                                        .addSuperinterface(ClassName.get(IsControllerCreator.class));
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
                                                .addParameter(ParameterSpec.builder(String[].class,
                                                                                    "parms")
                                                                           .build())
                                                .varargs()
                                                .returns(ClassName.get(ControllerInstance.class))
                                                .addException(ClassName.get(RoutingInterceptionException.class))
                                                .addStatement("$T sb01 = new $T()",
                                                              ClassName.get(StringBuilder.class),
                                                              ClassName.get(StringBuilder.class))
                                                .addStatement("$T controllerInstance = new $T()",
                                                              ClassName.get(ControllerInstance.class),
                                                              ClassName.get(ControllerInstance.class))
                                                .addStatement("controllerInstance.setControllerClassName($S)",
                                                              controllerModel.getController()
                                                                             .getClassName())
                                                .addStatement("$T<?, ?, ?> storedController = $T.get().getControllerFormStore($S)",
                                                              ClassName.get(AbstractComponentController.class),
                                                              ClassName.get(ControllerFactory.class),
                                                              controllerModel.getController()
                                                                             .getClassName())
                                                .beginControlFlow("if (storedController == null)")
                                                .addStatement("sb01.append(\"controller >>$L<< --> will be created\")",
                                                              controllerModel.getProvider()
                                                                             .getPackage() +
                                                              "." +
                                                              controllerModel.getProvider()
                                                                             .getSimpleName())
                                                .addStatement("$T.get().logSimple(sb01.toString(), 3)",
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
                                                .addStatement("controllerInstance.setController(controller)")
                                                .addStatement("controllerInstance.setChached(false)")
                                                .addStatement("controller.setContext(context)")
                                                .addStatement("controller.setEventBus(eventBus)")
                                                .addStatement("controller.setRouter(router)")
                                                .addStatement("controller.setRestored(false)")
                                                .addStatement("sb01 = new $T()",
                                                              ClassName.get(StringBuilder.class))
                                                .addStatement("sb01.append(\"controller >>\").append(controller.getClass().getCanonicalName()).append(\"<< --> created and data injected\")")
                                                .addStatement("$T.get().logDetailed(sb01.toString(), 4)",
                                                              ClassName.get(ClientLogger.class));
    if (controllerModel.isComponentCreator()) {
      createMethod.addStatement("$T component = controller.createComponent()",
                                ClassName.get(controllerModel.getComponentInterface()
                                                             .getPackage(),
                                              controllerModel.getComponentInterface()
                                                             .getSimpleName()))
                  .addStatement("sb01 = new $T()",
                                ClassName.get(StringBuilder.class))
                  .addStatement("sb01.append(\"component >>$L<< --> created using createComponent-Method of controller\")",
                                controllerModel.getComponent()
                                               .getClassName())
                  .addStatement("$T.get().logDetailed(sb01.toString(), 4)",
                                ClassName.get(ClientLogger.class));
    } else {
      createMethod.addStatement("$T component = new $T()",
                                ClassName.get(controllerModel.getComponentInterface()
                                                             .getPackage(),
                                              controllerModel.getComponentInterface()
                                                             .getSimpleName()),
                                ClassName.get(controllerModel.getComponent()
                                                             .getPackage(),
                                              controllerModel.getComponent()
                                                             .getSimpleName()))
                  .addStatement("sb01 = new $T()",
                                ClassName.get(StringBuilder.class))
                  .addStatement("sb01.append(\"component >>$L<< --> created using new\")",
                                controllerModel.getComponent()
                                               .getClassName())
                  .addStatement("$T.get().logDetailed(sb01.toString(), 4)",
                                ClassName.get(ClientLogger.class));
    }
    createMethod.addStatement("component.setController(controller)")
                .addStatement("sb01 = new $T()",
                              ClassName.get(StringBuilder.class))
                .addStatement("sb01.append(\"component >>\").append(component.getClass().getCanonicalName()).append(\"<< --> created and controller instance injected\")")
                .addStatement("$T.get().logDetailed(sb01.toString(), 4)",
                              ClassName.get(ClientLogger.class))
                .addStatement("controller.setComponent(component)")
                .addStatement("sb01 = new $T()",
                              ClassName.get(StringBuilder.class))
                .addStatement("sb01.append(\"controller >>\").append(controller.getClass().getCanonicalName()).append(\"<< --> instance of >>\").append(component.getClass().getCanonicalName()).append(\"<< injected\")")
                .addStatement("$T.get().logDetailed(sb01.toString(), 4)",
                              ClassName.get(ClientLogger.class))
                .addStatement("component.render()")
                .addStatement("sb01 = new $T()",
                              ClassName.get(StringBuilder.class))
                .addStatement("sb01.append(\"component >>\").append(component.getClass().getCanonicalName()).append(\"<< --> rendered\")")
                .addStatement("$T.get().logDetailed(sb01.toString(), 4)",
                              ClassName.get(ClientLogger.class))
                .addStatement("component.bind()")
                .addStatement("sb01 = new $T()",
                              ClassName.get(StringBuilder.class))
                .addStatement("sb01.append(\"component >>\").append(component.getClass().getCanonicalName()).append(\"<< --> bound\")")
                .addStatement("$T.get().logDetailed(sb01.toString(), 4)",
                              ClassName.get(ClientLogger.class))
                .addStatement("$T.get().logSimple(\"controller >>$L<< created for route >>$L<<\", 3)",
                              ClassName.get(ClientLogger.class),
                              controllerModel.getComponent()
                                             .getClassName(),
                              controllerModel.getRoute());
    if (controllerModel.getParameters()
                       .size() > 0) {
      // has the model AccpetParameter ?
      if (controllerModel.getParameterAcceptors()
                         .size() > 0) {
        createMethod.beginControlFlow("if (parms != null)");
        for (int i = 0; i <
                        controllerModel.getParameters()
                                       .size(); i++) {
          String methodName = controllerModel.getParameterAcceptors(controllerModel.getParameters()
                                                                                   .get(i));
          if (methodName != null) {
            createMethod.beginControlFlow("if (parms.length >= " + Integer.toString(i + 1) + ")")
                        .addStatement("sb01 = new $T()",
                                      ClassName.get(StringBuilder.class))
                        .addStatement("sb01.append(\"controller >>\").append(controller.getClass().getCanonicalName()).append(\"<< --> using method >>" +
                                      methodName +
                                      "<< to set value >>\").append(parms[" +
                                      Integer.toString(i) +
                                      "]).append(\"<<\")")
                        .addStatement("$T.get().logDetailed(sb01.toString(), 4)",
                                      ClassName.get(ClientLogger.class))
                        .addStatement("controller." + methodName + "(parms[" + Integer.toString(i) + "])")
                        .endControlFlow();
          }
        }
        createMethod.endControlFlow();
      }
    }
    createMethod.nextControlFlow("else")
                .addStatement("sb01.append(\"controller >>\").append(storedController.getClass().getCanonicalName()).append(\"<< --> found in cache -> REUSE!\")")
                .addStatement("$T.get().logDetailed(sb01.toString(), 4)",
                              ClassName.get(ClientLogger.class))
                .addStatement("controllerInstance.setController(storedController)")
                .addStatement("controllerInstance.setChached(true)")
                .addStatement("controllerInstance.getController().setRestored(true)")
                .endControlFlow();
    createMethod.addStatement("return controllerInstance");

    typeSpec.addMethod(createMethod.build());

    JavaFile javaFile = JavaFile.builder(controllerModel.getController()
                                                        .getPackage(),
                                         typeSpec.build())
                                .build();
    try {
      //      System.out.println(javaFile.toString());
      javaFile.writeTo(this.processingEnvironment.getFiler());
    } catch (IOException e) {
      throw new ProcessorException("Unable to write generated file: >>" +
                                   controllerModel.getController()
                                                  .getSimpleName() +
                                   ProcessorConstants.CREATOR_IMPL +
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

  public static final class Builder {

    ApplicationMetaModel applicationMetaModel;

    TypeSpec.Builder typeSpec;

    ProcessingEnvironment processingEnvironment;

    ControllerModel controllerModel;

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

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder controllerModel(ControllerModel controllerModel) {
      this.controllerModel = controllerModel;
      return this;
    }

    public ControllerCreatorGenerator build() {
      return new ControllerCreatorGenerator(this);
    }
  }
}
