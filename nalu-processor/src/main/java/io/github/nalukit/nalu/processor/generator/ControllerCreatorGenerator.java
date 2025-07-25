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

import io.github.nalukit.nalu.client.IsRouter;
import io.github.nalukit.nalu.client.component.AbstractComponentController;
import io.github.nalukit.nalu.client.constraint.IsParameterConstraintRule;
import io.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import io.github.nalukit.nalu.client.internal.AbstractControllerCreator;
import io.github.nalukit.nalu.client.internal.application.ControllerFactory;
import io.github.nalukit.nalu.client.internal.application.ControllerInstance;
import io.github.nalukit.nalu.client.internal.application.IsControllerCreator;
import io.github.nalukit.nalu.client.internal.constrain.ParameterConstraintRuleFactory;
import io.github.nalukit.nalu.processor.ProcessorConstants;
import io.github.nalukit.nalu.processor.ProcessorException;
import io.github.nalukit.nalu.processor.model.MetaModel;
import io.github.nalukit.nalu.processor.model.intern.ControllerModel;
import io.github.nalukit.nalu.processor.model.intern.ParameterAcceptorModel;
import io.github.nalukit.nalu.processor.model.intern.ParameterConstraintModel;
import io.github.nalukit.nalu.processor.util.BuildWithNaluCommentProvider;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import org.gwtproject.event.shared.SimpleEventBus;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.List;

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
                                        .addJavadoc(BuildWithNaluCommentProvider.INSTANCE.getGeneratedComment())
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
                                          .addStatement("$T<?, ?, ?> storedController = $T.INSTANCE.getControllerFormStore($S)",
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
                                                        controllerModel.getSelector());
    if (this.controllerModel.getEventHandlers()
                            .size() > 0) {
      CodeBlock.Builder lambdaBuilder = CodeBlock.builder()
                                                 .add("() -> {\n")
                                                 .indent();
      this.controllerModel.getEventModels()
                          .forEach(m -> lambdaBuilder.addStatement("controller.getHandlerRegistrations().add(this.eventBus.addHandler($T.TYPE, e -> controller.$L(e)))",
                                                                   ClassName.get(m.getEvent()
                                                                                  .getPackage(),
                                                                                 m.getEvent()
                                                                                  .getSimpleName()),
                                                                   m.getMethodNameOfHandler()));
      lambdaBuilder.unindent()
                   .add("}");
      method.addStatement("controller.setActivateNaluCommand($L)",
                          lambdaBuilder.build()
                                       .toString());
    } else {
      method.addStatement("controller.setActivateNaluCommand(() -> {})");
    }

    method.nextControlFlow("else")
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
                                          .addParameter(ParameterSpec.builder(ParameterizedTypeName.get(ClassName.get(List.class),
                                                                                                        ClassName.get(String.class)),
                                                                              "parameterKeys")
                                                                     .build())
                                          .addParameter(ParameterSpec.builder(ParameterizedTypeName.get(ClassName.get(List.class),
                                                                                                        ClassName.get(String.class)),
                                                                              "parameterValues")
                                                                     .build())
                                          .addException(ClassName.get(RoutingInterceptionException.class));
    // controllerModel has parameters?
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
                                                         .getSimpleName()));
        method.beginControlFlow("if (parameterKeys != null && parameterValues != null)");
        method.beginControlFlow("for (int i = 0; i < parameterKeys.size(); i++)");
        for (int i = 0; i <
                        controllerModel.getParameterAcceptors()
                                       .size(); i++) {
          ParameterAcceptorModel parameterAcceptor = controllerModel.getParameterAcceptors()
                                                                    .get(i);
          method.beginControlFlow("if ($S.equals(parameterKeys.get(i)))",
                                  parameterAcceptor.getParameterName());
          ParameterConstraintModel parameterConstraintModel = controllerModel.getConstraintModelFor(parameterAcceptor.getParameterName());
          if (parameterConstraintModel != null) {
            method.addStatement("$T rule = $T.INSTANCE.get($S)",
                                ClassName.get(IsParameterConstraintRule.class),
                                ClassName.get(ParameterConstraintRuleFactory.class),
                                parameterConstraintModel.getKey());
            method.beginControlFlow("if (rule != null)")
                  .beginControlFlow("if (!rule.isValid(parameterValues.get(i)))")
                  .addStatement("throw new $T($S, $S)",
                                ClassName.get(RoutingInterceptionException.class),
                                controllerModel.getProvider()
                                               .getSimpleName(),
                                parameterConstraintModel.getIllegalParameterRoute())
                  .endControlFlow()
                  .endControlFlow();
          }
          method.addStatement("controller." + parameterAcceptor.getMethodName() + "(parameterValues.get(i))");
          method.endControlFlow();
        }
        method.endControlFlow();
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
