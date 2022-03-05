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
import com.github.nalukit.nalu.client.internal.application.ControllerCompositeConditionFactory;
import com.github.nalukit.nalu.client.internal.application.ControllerFactory;
import com.github.nalukit.nalu.client.internal.route.RouteConfig;
import com.github.nalukit.nalu.processor.ProcessorConstants;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;
import com.github.nalukit.nalu.processor.model.intern.ControllerModel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ControllerGenerator {

  private MetaModel            metaModel;
  private TypeSpec.Builder     typeSpec;
  private Map<String, Integer> variableCounterMap;

  @SuppressWarnings("unused")
  private ControllerGenerator() {
  }

  private ControllerGenerator(Builder builder) {
    this.metaModel = builder.metaModel;
    this.typeSpec  = builder.typeSpec;

    this.variableCounterMap = new HashMap<>();
  }

  public static Builder builder() {
    return new Builder();
  }

  void generate() {
    generateLoadControllers();
    generateLoadSelectors();
  }

  private void generateLoadControllers() {
    // generate method 'loadComponents()'
    MethodSpec.Builder loadComponentsMethodBuilder = MethodSpec.methodBuilder("loadComponents")
                                                               .addModifiers(Modifier.PUBLIC)
                                                               .addAnnotation(Override.class);
    this.getAllComponents(this.metaModel.getControllers())
        .forEach(controllerModel -> {
          loadComponentsMethodBuilder.addStatement("$T.get().registerController($S, new $L(router, context, eventBus))",
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
                               String conditionVariableName;
                               if (generatedConditionClassNames.contains(controllerCompositeModel.getCondition()
                                                                                                 .getClassName())) {
                                 conditionVariableName = this.setFirstCharacterToLowerCase(controllerCompositeModel.getCondition()
                                                                                                                   .getSimpleName()) +
                                                         this.getNameWithVariableCount(controllerCompositeModel.getCondition(),
                                                                                       false);
                               } else {
                                 conditionVariableName = this.setFirstCharacterToLowerCase(controllerCompositeModel.getCondition()
                                                                                                                   .getSimpleName()) +
                                                         this.getNameWithVariableCount(controllerCompositeModel.getCondition(),
                                                                                       true);

                                 loadComponentsMethodBuilder.addStatement("$T $L = new $T()",
                                                                          ClassName.get(controllerCompositeModel.getCondition()
                                                                                                                .getPackage(),
                                                                                        controllerCompositeModel.getCondition()
                                                                                                                .getSimpleName()),
                                                                          conditionVariableName,
                                                                          ClassName.get(controllerCompositeModel.getCondition()
                                                                                                                .getPackage(),
                                                                                        controllerCompositeModel.getCondition()
                                                                                                                .getSimpleName()))
                                                            .addStatement("$L.setContext(super.context)",
                                                                          conditionVariableName);
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
                                                                        conditionVariableName);
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
                                                       .addAnnotation(Override.class);
    this.metaModel.getControllers()
                  .forEach(controllerModel -> controllerModel.getRoute()
                                                             .forEach(route -> loadSelectorsMethod.addStatement("super.routerConfiguration.getRouters().add(new $T($S, $T.asList(new String[]{$L}), $S, $S))",
                                                                                                                ClassName.get(RouteConfig.class),
                                                                                                                createRoute(route),
                                                                                                                ClassName.get(Arrays.class),
                                                                                                                createParameter(controllerModel.getParameters(),
                                                                                                                                true),
                                                                                                                controllerModel.getSelector(),
                                                                                                                controllerModel.getProvider()
                                                                                                                               .getClassName())));
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

  private String createRoute(String route) {
    if (route.startsWith("/")) {
      return route;
    } else {
      return "/" + route;
    }
  }

  private String createParameter(List<String> parameters,
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

  public static final class Builder {

    MetaModel metaModel;

    TypeSpec.Builder typeSpec;

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

    /**
     * Set the typeSpec of the currently generated eventBus
     *
     * @param typeSpec type spec of the current event bus
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
