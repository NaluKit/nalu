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

import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.application.ShellCreator;
import com.github.nalukit.nalu.client.internal.application.ShellFactory;
import com.github.nalukit.nalu.client.internal.application.ShellInstance;
import com.github.nalukit.nalu.client.internal.route.ShellConfig;
import com.github.nalukit.nalu.processor.model.ApplicationMetaModel;
import com.github.nalukit.nalu.processor.model.intern.ShellModel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

public class ShellGenerator {

  private ApplicationMetaModel applicationMetaModel;

  private TypeSpec.Builder typeSpec;

  @SuppressWarnings("unused")
  private ShellGenerator() {
  }

  private ShellGenerator(Builder builder) {
    this.applicationMetaModel = builder.applicationMetaModel;
    this.typeSpec = builder.typeSpec;
  }

  public static Builder builder() {
    return new Builder();
  }

  void generate() {
    generateLoadShells();
    generateLoadShellFactory();
  }

  private void generateLoadShells() {
    // generate method 'generateLoadShells()'
    MethodSpec.Builder loadShellsMethodBuilder = MethodSpec.methodBuilder("loadShells")
                                                           .addModifiers(Modifier.PUBLIC)
                                                           .addAnnotation(Override.class);
    this.applicationMetaModel.getShells()
                             .forEach(shellModel -> loadShellsMethodBuilder.addStatement("super.shellConfiguration.getShells().add(new $T($S, $S))",
                                                                                         ClassName.get(ShellConfig.class),
                                                                                         "/" + shellModel.getName(),
                                                                                         shellModel.getShell()
                                                                                                   .getClassName()));
    typeSpec.addMethod(loadShellsMethodBuilder.build());
  }

  private void generateLoadShellFactory() {
    // generate method 'generateLoadShells()'
    MethodSpec.Builder loadShellFactoryMethodBuilder = MethodSpec.methodBuilder("loadShellFactory")
                                                                 .addModifiers(Modifier.PUBLIC)
                                                                 .addAnnotation(Override.class);
    this.applicationMetaModel.getShells()
                             .forEach(shellModel -> {
                               MethodSpec.Builder createMethod = this.createMethod(shellModel);
                               // add return statement
                               createMethod.addStatement("return shellInstance");
                               loadShellFactoryMethodBuilder.addComment("create ShellCreator for: " +
                                                                        shellModel.getShell()
                                                                                  .getPackage() +
                                                                        "." +
                                                                        shellModel.getShell()
                                                                                  .getSimpleName())
                                                            .addStatement("$T.get().registerShell($S, $L)",
                                                                          ClassName.get(ShellFactory.class),
                                                                          shellModel.getShell()
                                                                                    .getPackage() +
                                                                          "." +
                                                                          shellModel.getShell()
                                                                                    .getSimpleName(),
                                                                          TypeSpec.anonymousClassBuilder("")
                                                                                  .addSuperinterface(ShellCreator.class)
                                                                                  .addMethod(createMethod.build())
                                                                                  .build());

                             });

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
    //    for (ControllerModel controllerModel : this.getAllComponents(this.applicationMetaModel.getController())) {
    //      MethodSpec.Builder createMethod = this.createMethodWithoutCache(controllerModel);
    //      // add return statement
    //      createMethod.addStatement("return controllerInstance");
    //      // add create method to controller ...
    //      loadComponentsMethodBuilder.addComment("create ControllerCreator for: " +
    //                                                 controllerModel.getProvider()
    //                                                                .getPackage() +
    //                                                 "." +
    //                                                 controllerModel.getProvider()
    //                                                                .getSimpleName())
    //                                 .addStatement("$T.get().registerController($S, $L)",
    //                                               ClassName.get(ControllerFactory.class),
    //                                               controllerModel.getProvider()
    //                                                              .getPackage() +
    //                                                   "." +
    //                                                   controllerModel.getProvider()
    //                                                                  .getSimpleName(),
    //                                               TypeSpec.anonymousClassBuilder("")
    //                                                       .addSuperinterface(ControllerCreator.class)
    //                                                       .addMethod(createMethod.build())
    //                                                       .build());
    //    }

    typeSpec.addMethod(loadShellFactoryMethodBuilder.build());
  }

  private MethodSpec.Builder createMethod(ShellModel shellModel) {
    MethodSpec.Builder createMethod = MethodSpec.methodBuilder("create")
                                                .addAnnotation(Override.class)
                                                .addModifiers(Modifier.PUBLIC)
                                                .returns(ClassName.get(ShellInstance.class))
                                                .addStatement("$T sb01 = new $T()",
                                                              ClassName.get(StringBuilder.class),
                                                              ClassName.get(StringBuilder.class))
                                                .addStatement("$T shellInstance = new $T()",
                                                              ClassName.get(ShellInstance.class),
                                                              ClassName.get(ShellInstance.class))
                                                .addStatement("shellInstance.setShellClassName($S)",
                                                              shellModel.getShell()
                                                                        .getClassName())
                                                .addStatement("sb01.append(\"shell >>$L<< --> will be created\")",
                                                              shellModel.getShell()
                                                                        .getPackage() +
                                                              "." +
                                                              shellModel.getShell()
                                                                        .getSimpleName())
                                                .addStatement("$T.get().logSimple(sb01.toString(), 1)",
                                                              ClassName.get(ClientLogger.class))
                                                .addStatement("$T shell = new $T()",
                                                              ClassName.get(shellModel.getShell()
                                                                                      .getPackage(),
                                                                            shellModel.getShell()
                                                                                      .getSimpleName()),
                                                              ClassName.get(shellModel.getShell()
                                                                                      .getPackage(),
                                                                            shellModel.getShell()
                                                                                      .getSimpleName()))
                                                .addStatement("shell.setContext(context)")
                                                .addStatement("shell.setEventBus(eventBus)")
                                                .addStatement("shell.setRouter(router)")
                                                .addStatement("sb01 = new $T()",
                                                              ClassName.get(StringBuilder.class))
                                                .addStatement("sb01.append(\"shell >>\").append(shell.getClass().getCanonicalName()).append(\"<< --> created and data injected\")")
                                                .addStatement("$T.get().logDetailed(sb01.toString(), 2)",
                                                              ClassName.get(ClientLogger.class))
                                                .addStatement("sb01 = new $T()",
                                                              ClassName.get(StringBuilder.class))
                                                .addStatement("sb01.append(\"shell >>\").append(shell.getClass().getCanonicalName()).append(\"<< --> call bind()-method\")")
                                                .addStatement("$T.get().logDetailed(sb01.toString(), 2)",
                                                              ClassName.get(ClientLogger.class))
                                                .addStatement("shell.bind()")
                                                .addStatement("sb01.append(\"shell >>\").append(shell.getClass().getCanonicalName()).append(\"<< --> called bind()-method\")")
                                                .addStatement("$T.get().logDetailed(sb01.toString(), 2)",
                                                              ClassName.get(ClientLogger.class))
                                                .addStatement("shellInstance.setShell(shell)");
    return createMethod;
  }

  //  private void generateLoadSelectors() {
  //    // method must always be created!
  //    MethodSpec.Builder loadSelectorsMethod = MethodSpec.methodBuilder("loadRoutes")
  //                                                       .addAnnotation(Override.class)
  //                                                       .addModifiers(Modifier.PUBLIC);
  //    this.applicationMetaModel.getController()
  //                             .forEach(route -> loadSelectorsMethod.addStatement("super.routerConfiguration.getRouters().add(new $T($S, $T.asList(new String[]{$L}), $S, $S))",
  //                                                                                ClassName.get(RouteConfig.class),
  //                                                                                createRoute(route.getRoute()),
  //                                                                                ClassName.get(Arrays.class),
  //                                                                                createParaemter(route.getParameters()),
  //                                                                                route.getSelector(),
  //                                                                                route.getProvider()
  //                                                                                     .getClassName()));
  //    typeSpec.addMethod(loadSelectorsMethod.build());
  //  }
  //
  //  private List<ControllerModel> getAllComponents(List<ControllerModel> routes) {
  //    List<ControllerModel> models = new ArrayList<>();
  //    routes.forEach(route -> {
  //      if (!contains(models,
  //                    route)) {
  //        models.add(route);
  //      }
  //    });
  //    return models;
  //  }
  //
  //  private String createRoute(String route) {
  //    if (route.startsWith("/")) {
  //      return route;
  //    } else {
  //      return "/" + route;
  //    }
  //  }
  //
  //  private String createParaemter(List<String> parameters) {
  //    StringBuilder sb = new StringBuilder();
  //    IntStream.range(0,
  //                    parameters.size())
  //             .forEach(i -> {
  //               sb.append("\"")
  //                 .append(parameters.get(i))
  //                 .append("\"");
  //               if (i != parameters.size() - 1) {
  //                 sb.append(", ");
  //               }
  //             });
  //    return sb.toString();
  //  }
  //
  //  private boolean contains(List<ControllerModel> models,
  //                           ControllerModel controllerModel) {
  //    return models.stream()
  //                 .anyMatch(model -> model.getProvider()
  //                                         .equals(controllerModel.getProvider()));
  //  }



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

    public ShellGenerator build() {
      return new ShellGenerator(this);
    }
  }
}
