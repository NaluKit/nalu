/*
 * Copyright (c) 2018 Frank Hossfeld
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
import com.github.nalukit.nalu.client.component.AbstractCompositeController;
import com.github.nalukit.nalu.client.constraint.IsParameterConstraintRule;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.AbstractCompositeCreator;
import com.github.nalukit.nalu.client.internal.application.CompositeFactory;
import com.github.nalukit.nalu.client.internal.application.CompositeInstance;
import com.github.nalukit.nalu.client.internal.application.IsCompositeCreator;
import com.github.nalukit.nalu.client.internal.constrain.ParameterConstraintRuleFactory;
import com.github.nalukit.nalu.processor.ProcessorConstants;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.CompositeModel;
import com.github.nalukit.nalu.processor.model.intern.ParameterAcceptorModel;
import com.github.nalukit.nalu.processor.model.intern.ParameterConstraintModel;
import com.github.nalukit.nalu.processor.util.BuildWithNaluCommentProvider;
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

public class CompositeCreatorGenerator {

  private ProcessingEnvironment processingEnvironment;
  private CompositeModel        compositeModel;

  @SuppressWarnings("unused")
  private CompositeCreatorGenerator() {
  }

  private CompositeCreatorGenerator(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;
    this.compositeModel        = builder.compositeModel;
  }

  public static Builder builder() {
    return new Builder();
  }

  public void generate()
      throws ProcessorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(compositeModel.getProvider()
                                                                    .getSimpleName() + ProcessorConstants.CREATOR_IMPL)
                                        .addJavadoc(BuildWithNaluCommentProvider.INSTANCE.getGeneratedComment())
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractCompositeCreator.class),
                                                                              compositeModel.getContext()
                                                                                            .getTypeName()))
                                        .addModifiers(Modifier.PUBLIC,
                                                      Modifier.FINAL)
                                        .addSuperinterface(ClassName.get(IsCompositeCreator.class));
    typeSpec.addMethod(createConstructor());
    typeSpec.addMethod(createCreateMethod());
    typeSpec.addMethod(createSetParameterMethod());

    JavaFile javaFile = JavaFile.builder(this.compositeModel.getProvider()
                                                            .getPackage(),
                                         typeSpec.build())
                                .build();
    try {
      System.out.println(javaFile.toString());
      javaFile.writeTo(this.processingEnvironment.getFiler());
    } catch (IOException e) {
      throw new ProcessorException("Unable to write generated file: >>" +
                                   this.compositeModel.getProvider()
                                                      .getClassName() +
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
                     .addParameter(ParameterSpec.builder(compositeModel.getContext()
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
    MethodSpec.Builder createMethod = MethodSpec.methodBuilder("create")
                                                .addModifiers(Modifier.PUBLIC)
                                                .addParameter(ParameterSpec.builder(String.class,
                                                                                    "parentControllerClassName")
                                                                           .build())
                                                .addParameter(ParameterSpec.builder(String.class,
                                                                                    "selector")
                                                                           .build())
                                                .addParameter(ParameterSpec.builder(boolean.class,
                                                                                    "scopeGlobal")
                                                                           .build())
                                                .returns(ClassName.get(CompositeInstance.class))
                                                .addException(ClassName.get(RoutingInterceptionException.class))
                                                .addStatement("$T compositeInstance = new $T()",
                                                              ClassName.get(CompositeInstance.class),
                                                              ClassName.get(CompositeInstance.class))
                                                .addStatement("compositeInstance.setCompositeClassName($S)",
                                                              compositeModel.getProvider()
                                                                            .getClassName())
                                                .addStatement("$T<?, ?, ?> storedComposite = $T.INSTANCE.getCompositeFormStore(parentControllerClassName, $S, selector)",
                                                              ClassName.get(AbstractCompositeController.class),
                                                              ClassName.get(CompositeFactory.class),
                                                              compositeModel.getProvider()
                                                                            .getClassName());
    createMethod.beginControlFlow("if (storedComposite == null)")
                .addStatement("$T composite = new $T()",
                              ClassName.get(compositeModel.getProvider()
                                                          .getPackage(),
                                            compositeModel.getProvider()
                                                          .getSimpleName()),
                              ClassName.get(compositeModel.getProvider()
                                                          .getPackage(),
                                            compositeModel.getProvider()
                                                          .getSimpleName()))
                .addStatement("compositeInstance.setComposite(composite)")
                .addStatement("composite.setParentClassName(parentControllerClassName)")
                .addStatement("composite.setCached(false)")
                .addStatement("composite.setContext(context)")
                .addStatement("composite.setEventBus(eventBus)")
                .addStatement("composite.setRouter(router)")
                .addStatement("composite.setCached(false)")
                .beginControlFlow("if (!scopeGlobal)")
                .addStatement("composite.setSelector(selector)")
                .endControlFlow();

    if (this.compositeModel.getEventHandlers().size() > 0) {
      CodeBlock.Builder lambdaBuilder = CodeBlock.builder()
                                                 .add("() -> {\n")
                                                 .indent();
      this.compositeModel.getEventModels()
                         .forEach(m -> lambdaBuilder.addStatement("composite.getHandlerRegistrations().add(this.eventBus.addHandler($T.TYPE, e -> composite.$L(e)))",
                                                                  ClassName.get(m.getEvent()
                                                                                 .getPackage(),
                                                                                m.getEvent()
                                                                                 .getSimpleName()),
                                                                  m.getMethodNameOfHandler()));
      lambdaBuilder.unindent()
                   .add("}");
      createMethod.addStatement("composite.setActivateNaluCommand($L)",
                                lambdaBuilder.build().toString());
    } else {
      createMethod.addStatement("composite.setActivateNaluCommand(() -> {})");
    }

    if (compositeModel.isComponentCreator()) {
      createMethod.addStatement("$T component = composite.createComponent()",
                                ClassName.get(compositeModel.getComponentInterface()
                                                            .getPackage(),
                                              compositeModel.getComponentInterface()
                                                            .getSimpleName()));
    } else {
      createMethod.addStatement("$T component = new $T()",
                                ClassName.get(compositeModel.getComponentInterface()
                                                            .getPackage(),
                                              compositeModel.getComponentInterface()
                                                            .getSimpleName()),
                                ClassName.get(compositeModel.getComponent()
                                                            .getPackage(),
                                              compositeModel.getComponent()
                                                            .getSimpleName()));
    }
    createMethod.addStatement("component.setController(composite)")
                .addStatement("composite.setComponent(component)")
                .addStatement("component.render()")
                .addStatement("component.bind()");
    createMethod.nextControlFlow("else")
                .addStatement("compositeInstance.setComposite(storedComposite)")
                .addStatement("compositeInstance.setCached(true)")
                .addStatement("compositeInstance.getComposite().setCached(true)")
                .endControlFlow();
    createMethod.addStatement("return compositeInstance");
    return createMethod.build();
  }

  private MethodSpec createSetParameterMethod() {
    MethodSpec.Builder method = MethodSpec.methodBuilder("setParameter")
                                          .addAnnotation(ClassName.get(Override.class))
                                          .addModifiers(Modifier.PUBLIC)
                                          .addParameter(ParameterSpec.builder(Object.class,
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
    // compositeModel has parameters?
    if (compositeModel.getParameterAcceptors()
                      .size() > 0) {
      // has the model AcceptParameter ?
      if (compositeModel.getParameterAcceptors()
                        .size() > 0) {
        method.addStatement("$T composite = ($T) object",
                            ClassName.get(compositeModel.getProvider()
                                                        .getPackage(),
                                          compositeModel.getProvider()
                                                        .getSimpleName()),
                            ClassName.get(compositeModel.getProvider()
                                                        .getPackage(),
                                          compositeModel.getProvider()
                                                        .getSimpleName()));
        method.beginControlFlow("if (parameterKeys != null && parameterValues != null)");
        method.beginControlFlow("for (int i = 0; i < parameterKeys.size(); i++)");
        for (int i = 0; i <
                        compositeModel.getParameterAcceptors()
                                      .size(); i++) {
          ParameterAcceptorModel parameterAcceptor = compositeModel.getParameterAcceptors()
                                                                   .get(i);
          method.beginControlFlow("if ($S.equals(parameterKeys.get(i)))",
                                  parameterAcceptor.getParameterName());
          ParameterConstraintModel parameterConstraintModel = compositeModel.getConstraintModelFor(parameterAcceptor.getParameterName());
          if (parameterConstraintModel != null) {
            method.addStatement("$T rule = $T.INSTANCE.get($S)",
                                ClassName.get(IsParameterConstraintRule.class),
                                ClassName.get(ParameterConstraintRuleFactory.class),
                                parameterConstraintModel.getKey());
            method.beginControlFlow("if (rule != null)")
                  .beginControlFlow("if (!rule.isValid(parameterValues.get(i)))")
                  .addStatement("throw new $T($S, $S)",
                                ClassName.get(RoutingInterceptionException.class),
                                compositeModel.getProvider()
                                              .getSimpleName(),
                                parameterConstraintModel.getIllegalParameterRoute())
                  .endControlFlow()
                  .endControlFlow();
          }
          method.addStatement("composite." + parameterAcceptor.getMethodName() + "(parameterValues.get(i))");
          method.endControlFlow();
        }
        method.endControlFlow();
        method.endControlFlow();
      }
    }
    return method.build();
  }

  public static final class Builder {

    MetaModel             metaModel;
    ProcessingEnvironment processingEnvironment;
    CompositeModel        compositeModel;

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

    public Builder compositeModel(CompositeModel compositeModel) {
      this.compositeModel = compositeModel;
      return this;
    }

    public CompositeCreatorGenerator build() {
      return new CompositeCreatorGenerator(this);
    }

  }

}
