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

import com.github.nalukit.nalu.client.component.AlwaysLoadComposite;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.application.ControllerCompositeConditionFactory;
import com.github.nalukit.nalu.client.internal.application.ControllerFactory;
import com.github.nalukit.nalu.client.internal.route.RouteConfig;
import com.github.nalukit.nalu.processor.ProcessorConstants;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.ControllerModel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class ControllerGenerator {

  private MetaModel metaModel;

  private TypeSpec.Builder typeSpec;

  @SuppressWarnings("unused")
  private ControllerGenerator() {
  }

  private ControllerGenerator(Builder builder) {
    this.metaModel = builder.metaModel;
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
    this.getAllComponents(this.metaModel.getControllers())
        .forEach(controllerModel -> {
          loadComponentsMethodBuilder.addComment("create ControllerCreator for: " +
                                                 controllerModel.getProvider()
                                                                .getPackage() +
                                                 "." +
                                                 controllerModel.getProvider()
                                                                .getSimpleName())
                                     .addStatement("$T.get().registerController($S, new $L(router, context, eventBus))",
                                                   ClassName.get(ControllerFactory.class),
                                                   controllerModel.getProvider()
                                                                  .getPackage() +
                                                   "." +
                                                   controllerModel.getProvider()
                                                                  .getSimpleName(),
                                                   ClassName.get(controllerModel.getController()
                                                                                .getPackage(),
                                                                 controllerModel.getController()
                                                                                .getSimpleName() + ProcessorConstants.CREATOR_IMPL));

          if (controllerModel.getComposites()
                             .size() > 0) {
            List<String> generatedConditionClassNames = new ArrayList<>();
            loadComponentsMethodBuilder.addComment("register conditions of composites for: " +
                                                   controllerModel.getProvider()
                                                                  .getPackage() +
                                                   "." +
                                                   controllerModel.getProvider()
                                                                  .getSimpleName());
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
                                                            .addStatement("$L.setContext(super.context)",
                                                                          this.setFirstCharacterToLowerCase(controllerCompositeModel.getCondition()
                                                                                                                                    .getSimpleName()));
                                 // remmeber generated condition to avoid creating the smae class again!
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

  private void generateLoadSelectors() {
    // method must always be created!
    MethodSpec.Builder loadSelectorsMethod = MethodSpec.methodBuilder("loadRoutes")
                                                       .addModifiers(Modifier.PUBLIC)
                                                       .addAnnotation(Override.class)
                                                       .addStatement("$T sb01 = new $T()",
                                                                     ClassName.get(StringBuilder.class),
                                                                     ClassName.get(StringBuilder.class))
                                                       .addStatement("sb01.append(\"load routes\")")
                                                       .addStatement("$T.get().logDetailed(sb01.toString(), 2)",
                                                                     ClassName.get(ClientLogger.class));
    this.metaModel.getControllers()
                  .forEach(route -> loadSelectorsMethod.addStatement("super.routerConfiguration.getRouters().add(new $T($S, $T.asList(new String[]{$L}), $S, $S))",
                                                                     ClassName.get(RouteConfig.class),
                                                                     createRoute(route.getRoute()),
                                                                     ClassName.get(Arrays.class),
                                                                     createParaemter(route.getParameters(),
                                                                                     true),
                                                                     route.getSelector(),
                                                                     route.getProvider()
                                                                          .getClassName())
                                                       .addStatement("sb01 = new $T()",
                                                                     ClassName.get(StringBuilder.class))
                                                       .addStatement("sb01.append(\"register route >>$L<< with parameter >>$L<< for selector >>$L<< for controller >>$L<<\")",
                                                                     createRoute(route.getRoute()),
                                                                     createParaemter(route.getParameters(),
                                                                                     false),
                                                                     route.getSelector(),
                                                                     route.getProvider()
                                                                          .getClassName())
                                                       .addStatement("$T.get().logDetailed(sb01.toString(), 3)",
                                                                     ClassName.get(ClientLogger.class)));
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

  private String createParaemter(List<String> parameters,
                                 boolean apostrophe) {
    StringBuilder sb = new StringBuilder();
    IntStream.range(0,
                    parameters.size())
             .forEach(i -> {
               if (apostrophe) {
                 sb.append("\"")
                   .append(parameters.get(i))
                   .append("\"");
               } else {
                 sb.append(parameters.get(i));
               }
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

  public static final class Builder {

    MetaModel metaModel;

    TypeSpec.Builder typeSpec;

    /**
     * Set the EventBusMetaModel of the currently generated eventBus
     *
     * @param metaModel meta data model of the eventbus
     * @return the Builder
     */
    public Builder metaModel(MetaModel metaModel) {
      this.metaModel = metaModel;
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
