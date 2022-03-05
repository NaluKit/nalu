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
import com.github.nalukit.nalu.client.internal.AbstractPopUpControllerCreator;
import com.github.nalukit.nalu.client.internal.application.IsPopUpControllerCreator;
import com.github.nalukit.nalu.client.internal.application.PopUpControllerInstance;
import com.github.nalukit.nalu.processor.ProcessorConstants;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.PopUpControllerModel;
import com.github.nalukit.nalu.processor.util.BuildWithNaluCommentProvider;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import org.gwtproject.event.shared.SimpleEventBus;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import java.io.IOException;

public class PopUpControllerCreatorGenerator {

  private ProcessingEnvironment processingEnvironment;

  private PopUpControllerModel popUpControllerModel;

  @SuppressWarnings("unused")
  private PopUpControllerCreatorGenerator() {
  }

  private PopUpControllerCreatorGenerator(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;
    this.popUpControllerModel  = builder.popUpControllerModel;
  }

  public static Builder builder() {
    return new Builder();
  }

  public void generate()
      throws ProcessorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(popUpControllerModel.getController()
                                                                          .getSimpleName() + ProcessorConstants.CREATOR_IMPL)
                                        .addJavadoc(BuildWithNaluCommentProvider.get()
                                                                                .getGeneratedComment())
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractPopUpControllerCreator.class),
                                                                              popUpControllerModel.getContext()
                                                                                                  .getTypeName()))
                                        .addModifiers(Modifier.PUBLIC,
                                                      Modifier.FINAL)
                                        .addSuperinterface(ClassName.get(IsPopUpControllerCreator.class));
    // constructor ...
    MethodSpec constructor = MethodSpec.constructorBuilder()
                                       .addModifiers(Modifier.PUBLIC)
                                       .addParameter(ParameterSpec.builder(ClassName.get(IsRouter.class),
                                                                           "router")
                                                                  .build())
                                       .addParameter(ParameterSpec.builder(popUpControllerModel.getContext()
                                                                                               .getTypeName(),
                                                                           "context")
                                                                  .build())
                                       .addParameter(ParameterSpec.builder(ClassName.get(SimpleEventBus.class),
                                                                           "eventBus")
                                                                  .build())
                                       .addStatement("super(router, context, eventBus)")
                                       .build();
    typeSpec.addMethod(constructor);

    MethodSpec.Builder createMethod = MethodSpec.methodBuilder("create")
                                                .addAnnotation(ClassName.get(Override.class))
                                                .addModifiers(Modifier.PUBLIC)
                                                .returns(ClassName.get(PopUpControllerInstance.class))
                                                .addStatement("$T popUpControllerInstance = new $T()",
                                                              ClassName.get(PopUpControllerInstance.class),
                                                              ClassName.get(PopUpControllerInstance.class))
                                                .addStatement("popUpControllerInstance.setPopUpControllerClassName($S)",
                                                              popUpControllerModel.getController()
                                                                                  .getClassName())
                                                .addStatement("popUpControllerInstance.setAlwaysRenderComponent($L)",
                                                              popUpControllerModel.isAlwaysRenderComponent() ? "true" : "false")
                                                .addStatement("$T controller = new $T()",
                                                              ClassName.get(popUpControllerModel.getProvider()
                                                                                                .getPackage(),
                                                                            popUpControllerModel.getProvider()
                                                                                                .getSimpleName()),
                                                              ClassName.get(popUpControllerModel.getProvider()
                                                                                                .getPackage(),
                                                                            popUpControllerModel.getProvider()
                                                                                                .getSimpleName()))
                                                .addStatement("popUpControllerInstance.setController(controller)")
                                                .addStatement("controller.setContext(context)")
                                                .addStatement("controller.setEventBus(eventBus)")
                                                .addStatement("controller.setRouter(router)")
                                                .addStatement("controller.setName($S)",
                                                              popUpControllerModel.getName());
    createMethod.addStatement("return popUpControllerInstance");
    typeSpec.addMethod(createMethod.build());

    MethodSpec.Builder onFinishCreatingMethod = MethodSpec.methodBuilder("onFinishCreating")
                                                          .addParameter(ParameterSpec.builder(ClassName.get(Object.class),
                                                                                              "object")
                                                                                     .build())
                                                          .addAnnotation(ClassName.get(Override.class))
                                                          .addModifiers(Modifier.PUBLIC)
                                                          .addStatement("$T controller = ($T) object",
                                                                        ClassName.get(popUpControllerModel.getProvider()
                                                                                                          .getPackage(),
                                                                                      popUpControllerModel.getProvider()
                                                                                                          .getSimpleName()),
                                                                        ClassName.get(popUpControllerModel.getProvider()
                                                                                                          .getPackage(),
                                                                                      popUpControllerModel.getProvider()
                                                                                                          .getSimpleName()));
    if (popUpControllerModel.isComponentCreator()) {
      onFinishCreatingMethod.addStatement("$T component = controller.createPopUpComponent()",
                                          ClassName.get(popUpControllerModel.getComponentInterface()
                                                                            .getPackage(),
                                                        popUpControllerModel.getComponentInterface()
                                                                            .getSimpleName()));
    } else {
      onFinishCreatingMethod.addStatement("$T component = new $T()",
                                          ClassName.get(popUpControllerModel.getComponentInterface()
                                                                            .getPackage(),
                                                        popUpControllerModel.getComponentInterface()
                                                                            .getSimpleName()),
                                          ClassName.get(popUpControllerModel.getComponent()
                                                                            .getPackage(),
                                                        popUpControllerModel.getComponent()
                                                                            .getSimpleName()));
    }
    onFinishCreatingMethod.addStatement("component.setController(controller)")
                          .addStatement("controller.setComponent(component)")
                          .addStatement("component.render()")
                          .addStatement("component.bind()");
    typeSpec.addMethod(onFinishCreatingMethod.build());

    JavaFile javaFile = JavaFile.builder(popUpControllerModel.getController()
                                                             .getPackage(),
                                         typeSpec.build())
                                .build();
    try {
      //      System.out.println(javaFile.toString());
      javaFile.writeTo(this.processingEnvironment.getFiler());
    } catch (IOException e) {
      throw new ProcessorException("Unable to write generated file: >>" +
                                   popUpControllerModel.getController()
                                                       .getSimpleName() +
                                   ProcessorConstants.CREATOR_IMPL +
                                   "<< -> exception: " +
                                   e.getMessage());
    }
  }

  public static final class Builder {

    MetaModel metaModel;

    ProcessingEnvironment processingEnvironment;

    PopUpControllerModel popUpControllerModel;

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

    public Builder popUpControllerModel(PopUpControllerModel popUpControllerModel) {
      this.popUpControllerModel = popUpControllerModel;
      return this;
    }

    public PopUpControllerCreatorGenerator build() {
      return new PopUpControllerCreatorGenerator(this);
    }

  }

}
