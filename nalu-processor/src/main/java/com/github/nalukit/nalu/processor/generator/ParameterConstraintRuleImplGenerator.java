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

import com.github.nalukit.nalu.client.constraint.IsParameterConstraintRule;
import com.github.nalukit.nalu.client.internal.constrain.AbstractParameterConstraintRule;
import com.github.nalukit.nalu.processor.ProcessorConstants;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.ParameterConstraintRuleModel;
import com.github.nalukit.nalu.processor.util.BuildWithNaluCommentProvider;
import com.squareup.javapoet.*;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import java.io.IOException;

public class ParameterConstraintRuleImplGenerator {

  private ProcessingEnvironment        processingEnvironment;
  private ParameterConstraintRuleModel parameterConstraintRuleModel;

  @SuppressWarnings("unused")
  private ParameterConstraintRuleImplGenerator() {
  }

  private ParameterConstraintRuleImplGenerator(Builder builder) {
    this.processingEnvironment        = builder.processingEnvironment;
    this.parameterConstraintRuleModel = builder.parameterConstraintRuleModel;
  }

  public static Builder builder() {
    return new Builder();
  }

  public void generate()
      throws ProcessorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(parameterConstraintRuleModel.getParameterConstraintRule()
                                                                                  .getSimpleName() + ProcessorConstants.IMPL)
                                        .addJavadoc(BuildWithNaluCommentProvider.get()
                                                                                .getGeneratedComment())
                                        .superclass(ClassName.get(AbstractParameterConstraintRule.class))
                                        .addModifiers(Modifier.PUBLIC,
                                                      Modifier.FINAL)
                                        .addSuperinterface(ClassName.get(IsParameterConstraintRule.class));
    typeSpec.addMethod(createConstructor());
    typeSpec.addMethod(createKeyMethod());
    typeSpec.addMethod(createIsValidMethod());

    JavaFile javaFile = JavaFile.builder(parameterConstraintRuleModel.getParameterConstraintRule()
                                                                     .getPackage(),
                                         typeSpec.build())
                                .build();
    try {
//      System.out.println(javaFile.toString());
      javaFile.writeTo(this.processingEnvironment.getFiler());
    } catch (IOException e) {
      throw new ProcessorException("Unable to write generated file: >>" +
                                   parameterConstraintRuleModel.getParameterConstraintRule()
                                                               .getSimpleName() +
                                   ProcessorConstants.CREATOR_IMPL +
                                   "<< -> exception: " +
                                   e.getMessage());
    }
  }

  private MethodSpec createConstructor() {
    return MethodSpec.constructorBuilder()
                     .addModifiers(Modifier.PUBLIC)
                     //                     .addParameter(ParameterSpec.builder(ClassName.get(Router.class),
                     //                                                         "router")
                     //                                                .build())
                     //                     .addParameter(ParameterSpec.builder(controllerModel.getContext()
                     //                                                                        .getTypeName(),
                     //                                                         "context")
                     //                                                .build())
                     //                     .addParameter(ParameterSpec.builder(ClassName.get(SimpleEventBus.class),
                     //                                                         "eventBus")
                     //                                                .build())
                     //                     .addStatement("super(router, context, eventBus)")
                     .build();
  }

  private MethodSpec createKeyMethod() {
    MethodSpec.Builder method = MethodSpec.methodBuilder("key")
                                          .addAnnotation(ClassName.get(Override.class))
                                          .addModifiers(Modifier.PUBLIC)
                                          .returns(ClassName.get(String.class))
                                          .addStatement("return $S",
                                                        parameterConstraintRuleModel.getParameterConstraintRule()
                                                                                    .getPackage() +
                                                        "." +
                                                        parameterConstraintRuleModel.getParameterConstraintRule()
                                                                                    .getSimpleName());
    return method.build();
  }

  private MethodSpec createIsValidMethod() {
    MethodSpec.Builder method = MethodSpec.methodBuilder("isValid")
                                          .addAnnotation(ClassName.get(Override.class))
                                          .addParameter(ParameterSpec.builder(ClassName.get(String.class),
                                                                              "parameter")
                                                                     .build())
                                          .addModifiers(Modifier.PUBLIC)
                                          .returns(boolean.class);
    //                                          .addStatement("$T controllerInstance = new $T()",
    //                                                        ClassName.get(ControllerInstance.class),
    //                                                        ClassName.get(ControllerInstance.class))
    //                                          .addStatement("controllerInstance.setControllerCreator(this)")
    //                                          .addStatement("controllerInstance.setControllerClassName($S)",
    //                                                        controllerModel.getController()
    //                                                                       .getClassName())
    //                                          .addStatement("$T<?, ?, ?> storedController = $T.get().getControllerFormStore($S)",
    //                                                        ClassName.get(AbstractComponentController.class),
    //                                                        ClassName.get(ControllerFactory.class),
    //                                                        controllerModel.getController()
    //                                                                       .getClassName())
    //                                          .beginControlFlow("if (storedController == null)")
    //                                          .addStatement("$T controller = new $T()",
    //                                                        ClassName.get(controllerModel.getProvider()
    //                                                                                     .getPackage(),
    //                                                                      controllerModel.getProvider()
    //                                                                                     .getSimpleName()),
    //                                                        ClassName.get(controllerModel.getProvider()
    //                                                                                     .getPackage(),
    //                                                                      controllerModel.getProvider()
    //                                                                                     .getSimpleName()))
    //                                          .addStatement("controllerInstance.setController(controller)")
    //                                          .addStatement("controllerInstance.setCached(false)")
    //                                          .addStatement("controller.setContext(context)")
    //                                          .addStatement("controller.setEventBus(eventBus)")
    //                                          .addStatement("controller.setRouter(router)")
    //                                          .addStatement("controller.setCached(false)")
    //                                          .addStatement("controller.setRelatedRoute(route)")
    //                                          .addStatement("controller.setRelatedSelector($S)",
    //                                                        controllerModel.getSelector())
    //                                          .nextControlFlow("else")
    //                                          .addStatement("controllerInstance.setController(storedController)")
    //                                          .addStatement("controllerInstance.setCached(true)")
    //                                          .addStatement("controllerInstance.getController().setCached(true)")
    //                                          .endControlFlow();
    method.addStatement("return true");
    return method.build();
  }

  public static final class Builder {

    MetaModel                    metaModel;
    ProcessingEnvironment        processingEnvironment;
    ParameterConstraintRuleModel parameterConstraintRuleModel;

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

    public Builder parameterConstraintRuleModel(ParameterConstraintRuleModel parameterConstraintRuleModel) {
      this.parameterConstraintRuleModel = parameterConstraintRuleModel;
      return this;
    }

    public ParameterConstraintRuleImplGenerator build() {
      return new ParameterConstraintRuleImplGenerator(this);
    }

  }

}
