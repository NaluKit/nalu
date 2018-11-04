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

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.application.ControllerCreator;
import com.github.nalukit.nalu.client.internal.application.ControllerFactory;
import com.github.nalukit.nalu.client.internal.application.ControllerInstance;
import com.github.nalukit.nalu.client.internal.route.RouteConfig;
import com.github.nalukit.nalu.processor.model.ApplicationMetaModel;
import com.github.nalukit.nalu.processor.model.intern.ControllerModel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class ControllerGenerator {

  private ApplicationMetaModel applicationMetaModel;

  private TypeSpec.Builder typeSpec;

  @SuppressWarnings("unused")
  private ControllerGenerator() {
  }

  private ControllerGenerator(Builder builder) {
    this.applicationMetaModel = builder.applicationMetaModel;
    this.typeSpec = builder.typeSpec;
  }

  public static Builder builder() {
    return new Builder();
  }

  void generate() {
    generateLoadContollers();
    generateLoadSelectors();
  }

  private void generateLoadContollers() {
    // generate method 'loadComponents()'
    MethodSpec.Builder loadComponentsMethodBuilder = MethodSpec.methodBuilder("loadComponents")
                                                               .addModifiers(Modifier.PUBLIC)
                                                               .addAnnotation(Override.class);
//    loadComponentsMethodBuilder.addComment("shell ...")
//                               .addStatement("$T shell = new $T()",
//                                             ClassName.get(this.applicationMetaModel.getShell()
//                                                                                    .getPackage(),
//                                                           this.applicationMetaModel.getShell()
//                                                                                    .getSimpleName()),
//                                             ClassName.get(this.applicationMetaModel.getShell()
//                                                                                    .getPackage(),
//                                                           this.applicationMetaModel.getShell()
//                                                                                    .getSimpleName()))
//                               .addStatement("shell.setRouter(this.router)")
//                               .addStatement("shell.setEventBus(this.eventBus)")
//                               .addStatement("shell.setContext(this.context)")
//                               .addStatement("super.shell = shell")
//                               .addStatement("super.router.setShell(this.shell)")
//                               .addStatement("shell.bind()")
//                               .addStatement("$T.get().logDetailed(\"AbstractApplicationImpl: shell created\", 3)",
//                                             ClassName.get(ClientLogger.class));
    // add return statement
    // add create method to controller ...
    this.getAllComponents(this.applicationMetaModel.getController())
        .forEach(controllerModel -> {
          MethodSpec.Builder createMethod = this.createMethodWithoutCache(controllerModel);
          createMethod.addStatement("return controllerInstance");
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
        });
    typeSpec.addMethod(loadComponentsMethodBuilder.build());
  }

  private MethodSpec.Builder createMethodWithoutCache(ControllerModel controllerModel) {
    MethodSpec.Builder createMethod = MethodSpec.methodBuilder("create")
                                                .addAnnotation(Override.class)
                                                .addModifiers(Modifier.PUBLIC)
                                                .addParameter(ParameterSpec.builder(String[].class,
                                                                                    "parms")
                                                                           .build())
                                                .varargs(true)
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
                .endControlFlow();
    return createMethod;
  }

  private void generateLoadSelectors() {
    // method must always be created!
    MethodSpec.Builder loadSelectorsMethod = MethodSpec.methodBuilder("loadRoutes")
                                                       .addAnnotation(Override.class)
                                                       .addModifiers(Modifier.PUBLIC);
    this.applicationMetaModel.getController()
                             .forEach(route -> loadSelectorsMethod.addStatement("super.routerConfiguration.getRouters().add(new $T($S, $T.asList(new String[]{$L}), $S, $S))",
                                                                                ClassName.get(RouteConfig.class),
                                                                                createRoute(route.getRoute()),
                                                                                ClassName.get(Arrays.class),
                                                                                createParaemter(route.getParameters()),
                                                                                route.getSelector(),
                                                                                route.getProvider()
                                                                                     .getClassName()));
    typeSpec.addMethod(loadSelectorsMethod.build());
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

    public ControllerGenerator build() {
      return new ControllerGenerator(this);
    }
  }
}
