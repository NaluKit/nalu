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

import com.github.nalukit.nalu.client.IsRouter;
import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.constraint.IsParameterConstraintRule;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.AbstractControllerCreator;
import com.github.nalukit.nalu.client.internal.application.ControllerFactory;
import com.github.nalukit.nalu.client.internal.application.ControllerInstance;
import com.github.nalukit.nalu.client.internal.application.IsControllerCreator;
import com.github.nalukit.nalu.client.internal.constrain.ParameterConstraintRuleFactory;
import com.github.nalukit.nalu.processor.ProcessorConstants;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.ControllerModel;
import com.github.nalukit.nalu.processor.model.intern.ParameterConstraintModel;
import com.github.nalukit.nalu.processor.util.BuildWithNaluCommentProvider;
import com.squareup.javapoet.*;
import org.gwtproject.event.shared.SimpleEventBus;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import java.io.IOException;

public class ControllerCreatorGenerator {

  private ProcessingEnvironment processingEnvironment;

  private ControllerModel controllerModel;

  @SuppressWarnings("unused")
  private ControllerCreatorGenerator() {
  }

  private ControllerCreatorGenerator(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;
    this.controllerModel       = builder.controllerModel;
  }

  public static Builder builder() {
    return new Builder();
  }

  public void generate()
      throws ProcessorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(controllerModel.getController()
                                                                     .getSimpleName() + ProcessorConstants.CREATOR_IMPL)
                                        .addJavadoc(BuildWithNaluCommentProvider.get()
                                                                                .getGeneratedComment())
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractControllerCreator.class),
                                                                              controllerModel.getContext()
                                                                                             .getTypeName()))
                                        .addModifiers(Modifier.PUBLIC,
                                                      Modifier.FINAL)
                                        .addSuperinterface(ClassName.get(IsControllerCreator.class));
    typeSpec.addMethod(createConstructor());
    typeSpec.addMethod(createCreateMethod());
    typeSpec.addMethod(createFinishCreateMethod());
    typeSpec.addMethod(createSetParameterMethod());

    JavaFile javaFile = JavaFile.builder(controllerModel.getController()
                                                        .getPackage(),
                                         typeSpec.build())
                                .build();
    try {
      //            System.out.println(javaFile.toString());
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

  private MethodSpec createConstructor() {
    return MethodSpec.constructorBuilder()
                     .addModifiers(Modifier.PUBLIC)
                     .addParameter(ParameterSpec.builder(ClassName.get(IsRouter.class),
                                                         "router")
                                                .build())
                     .addParameter(ParameterSpec.builder(controllerModel.getContext()
                                                                        .getTypeName(),
                                                         "context")
                                                .build())
                     .addParameter(ParameterSpec.builder(ClassName.get(SimpleEventBus.class),
                                                         "eventBus")
                                                .build())
                     .addStatement("super(router, context, eventBus)")
                     .build();
  }

  private MethodSpec createCreateMethod() {
    MethodSpec.Builder method = MethodSpec.methodBuilder("create")
                                          .addAnnotation(ClassName.get(Override.class))
                                          .addParameter(ParameterSpec.builder(ClassName.get(String.class),
                                                                              "route")
                                                                     .build())
                                          .addModifiers(Modifier.PUBLIC)
                                          .returns(ClassName.get(ControllerInstance.class))
                                          .addStatement("$T controllerInstance = new $T()",
                                                        ClassName.get(ControllerInstance.class),
                                                        ClassName.get(ControllerInstance.class))
                                          .addStatement("controllerInstance.setControllerCreator(this)")
                                          .addStatement("controllerInstance.setControllerClassName($S)",
                                                        controllerModel.getController()
                                                                       .getClassName())
                                          .addStatement("$T<?, ?, ?> storedController = $T.get().getControllerFormStore($S)",
                                                        ClassName.get(AbstractComponentController.class),
                                                        ClassName.get(ControllerFactory.class),
                                                        controllerModel.getController()
                                                                       .getClassName())
                                          .beginControlFlow("if (storedController == null)")
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
                                          .addStatement("controllerInstance.setCached(false)")
                                          .addStatement("controller.setContext(context)")
                                          .addStatement("controller.setEventBus(eventBus)")
                                          .addStatement("controller.setRouter(router)")
                                          .addStatement("controller.setCached(false)")
                                          .addStatement("controller.setRelatedRoute(route)")
                                          .addStatement("controller.setRelatedSelector($S)",
                                                        controllerModel.getSelector())
                                          .nextControlFlow("else")
                                          .addStatement("controllerInstance.setController(storedController)")
                                          .addStatement("controllerInstance.setCached(true)")
                                          .addStatement("controllerInstance.getController().setCached(true)")
                                          .endControlFlow();
    method.addStatement("return controllerInstance");
    return method.build();
  }

  private MethodSpec createFinishCreateMethod() {
    MethodSpec.Builder method = MethodSpec.methodBuilder("onFinishCreating")
                                          .addAnnotation(ClassName.get(Override.class))
                                          .addModifiers(Modifier.PUBLIC)
                                          .addParameter(ParameterSpec.builder(ClassName.get(Object.class),
                                                                              "object")
                                                                     .build())
                                          .addStatement("$T controller = ($T) object",
                                                        ClassName.get(controllerModel.getProvider()
                                                                                     .getPackage(),
                                                                      controllerModel.getProvider()
                                                                                     .getSimpleName()),
                                                        ClassName.get(controllerModel.getProvider()
                                                                                     .getPackage(),
                                                                      controllerModel.getProvider()
                                                                                     .getSimpleName()));
    if (controllerModel.isComponentCreator()) {
      method.addStatement("$T component = controller.createComponent()",
                          ClassName.get(controllerModel.getComponentInterface()
                                                       .getPackage(),
                                        controllerModel.getComponentInterface()
                                                       .getSimpleName()));
    } else {
      method.addStatement("$T component = new $T()",
                          ClassName.get(controllerModel.getComponentInterface()
                                                       .getPackage(),
                                        controllerModel.getComponentInterface()
                                                       .getSimpleName()),
                          ClassName.get(controllerModel.getComponent()
                                                       .getPackage(),
                                        controllerModel.getComponent()
                                                       .getSimpleName()));
    }
    method.addStatement("component.setController(controller)")
          .addStatement("controller.setComponent(component)")
          .addStatement("component.render()")
          .addStatement("component.bind()");
    return method.build();
  }

  private MethodSpec createSetParameterMethod() {
    MethodSpec.Builder method = MethodSpec.methodBuilder("setParameter")
                                          .addAnnotation(ClassName.get(Override.class))
                                          .addModifiers(Modifier.PUBLIC)
                                          .addParameter(ParameterSpec.builder(ClassName.get(Object.class),
                                                                              "object")
                                                                     .build())
                                          .addParameter(ParameterSpec.builder(String[].class,
                                                                              "params")
                                                                     .build())
                                          .varargs()
                                          .addException(ClassName.get(RoutingInterceptionException.class));
    if (controllerModel.getParameters()
                       .size() > 0) {
      // has the model AcceptParameter ?
      if (controllerModel.getParameterAcceptors()
                         .size() > 0) {
        method.addStatement("$T controller = ($T) object",
                            ClassName.get(controllerModel.getProvider()
                                                         .getPackage(),
                                          controllerModel.getProvider()
                                                         .getSimpleName()),
                            ClassName.get(controllerModel.getProvider()
                                                         .getPackage(),
                                          controllerModel.getProvider()
                                                         .getSimpleName()))
              .beginControlFlow("if (params != null)");
        for (int i = 0; i <
                        controllerModel.getParameters()
                                       .size(); i++) {
          String methodName = controllerModel.getParameterAcceptors(controllerModel.getParameters()
                                                                                   .get(i));
          if (methodName != null) {
            method.beginControlFlow("if (params.length >= " + (i + 1) + ")");
            ParameterConstraintModel parameterConstraintModel = controllerModel.getConstraintModelFor(controllerModel.getParameters()
                                                                                                                     .get(i));
            if (parameterConstraintModel != null) {
              method.addStatement("$T rule = $T.get().get($S)",
                                  ClassName.get(IsParameterConstraintRule.class),
                                  ClassName.get(ParameterConstraintRuleFactory.class),
                                  parameterConstraintModel.getKey());
              method.beginControlFlow("if (rule != null)")
                    .beginControlFlow("if (!rule.isValid(params[" + i + "]))")
                    .addStatement("throw new $T($S, $S)",
                                  ClassName.get(RoutingInterceptionException.class),
                                  controllerModel.getController()
                                                 .getSimpleName(),
                                  parameterConstraintModel.getIllegalParameterRoute())
                    .endControlFlow()
                    .endControlFlow();
            }
            method.addStatement("controller." + methodName + "(params[" + i + "])")
                  .endControlFlow();
          }
        }
        method.endControlFlow();
      }
    }
    return method.build();
  }

  public static final class Builder {

    MetaModel metaModel;

    ProcessingEnvironment processingEnvironment;

    ControllerModel controllerModel;

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

    public Builder controllerModel(ControllerModel controllerModel) {
      this.controllerModel = controllerModel;
      return this;
    }

    public ControllerCreatorGenerator build() {
      return new ControllerCreatorGenerator(this);
    }

  }

}
