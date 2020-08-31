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

import com.github.nalukit.nalu.client.Router;
import com.github.nalukit.nalu.client.internal.AbstractBlockControllerCreator;
import com.github.nalukit.nalu.client.internal.application.BlockControllerInstance;
import com.github.nalukit.nalu.client.internal.application.IsBlockControllerCreator;
import com.github.nalukit.nalu.processor.ProcessorConstants;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.BlockControllerModel;
import com.github.nalukit.nalu.processor.util.BuildWithNaluCommentProvider;
import com.squareup.javapoet.*;
import org.gwtproject.event.shared.SimpleEventBus;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import java.io.IOException;

public class BlockControllerCreatorGenerator {
  
  private ProcessingEnvironment processingEnvironment;
  
  private BlockControllerModel blockControllerModel;
  
  @SuppressWarnings("unused")
  private BlockControllerCreatorGenerator() {
  }
  
  private BlockControllerCreatorGenerator(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;
    this.blockControllerModel  = builder.blockControllerModel;
  }
  
  public static Builder builder() {
    return new Builder();
  }
  
  public void generate()
      throws ProcessorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(blockControllerModel.getController()
                                                                          .getSimpleName() + ProcessorConstants.CREATOR_IMPL)
                                        .addJavadoc(BuildWithNaluCommentProvider.get()
                                                                                .getGeneratedComment())
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractBlockControllerCreator.class),
                                                                              blockControllerModel.getContext()
                                                                                                  .getTypeName()))
                                        .addModifiers(Modifier.PUBLIC,
                                                      Modifier.FINAL)
                                        .addSuperinterface(ClassName.get(IsBlockControllerCreator.class));
    // constructor ...
    MethodSpec constructor = MethodSpec.constructorBuilder()
                                       .addModifiers(Modifier.PUBLIC)
                                       .addParameter(ParameterSpec.builder(ClassName.get(Router.class),
                                                                           "router")
                                                                  .build())
                                       .addParameter(ParameterSpec.builder(blockControllerModel.getContext()
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
                                                .returns(ClassName.get(BlockControllerInstance.class))
                                                .addStatement("$T blockControllerInstance = new $T()",
                                                              ClassName.get(BlockControllerInstance.class),
                                                              ClassName.get(BlockControllerInstance.class))
                                                .addStatement("blockControllerInstance.setBlockControllerClassName($S)",
                                                              blockControllerModel.getController()
                                                                                  .getClassName())
                                                .addStatement("blockControllerInstance.setCondition(new $T())",
                                                              ClassName.get(blockControllerModel.getConndition()
                                                                                                .getPackage(),
                                                                            blockControllerModel.getConndition()
                                                                                                .getSimpleName()))
                                                .addStatement("$T controller = new $T()",
                                                              ClassName.get(blockControllerModel.getProvider()
                                                                                                .getPackage(),
                                                                            blockControllerModel.getProvider()
                                                                                                .getSimpleName()),
                                                              ClassName.get(blockControllerModel.getProvider()
                                                                                                .getPackage(),
                                                                            blockControllerModel.getProvider()
                                                                                                .getSimpleName()))
                                                .addStatement("blockControllerInstance.setController(controller)")
                                                .addStatement("controller.setContext(context)")
                                                .addStatement("controller.setEventBus(eventBus)")
                                                .addStatement("controller.setRouter(router)")
                                                .addStatement("controller.setName($S)",
                                                              blockControllerModel.getName());
    if (blockControllerModel.isComponentCreator()) {
      createMethod.addStatement("$T component = controller.createBlockComponent()",
                                ClassName.get(blockControllerModel.getComponentInterface()
                                                                  .getPackage(),
                                              blockControllerModel.getComponentInterface()
                                                                  .getSimpleName()));
    } else {
      createMethod.addStatement("$T component = new $T()",
                                ClassName.get(blockControllerModel.getComponentInterface()
                                                                  .getPackage(),
                                              blockControllerModel.getComponentInterface()
                                                                  .getSimpleName()),
                                ClassName.get(blockControllerModel.getComponent()
                                                                  .getPackage(),
                                              blockControllerModel.getComponent()
                                                                  .getSimpleName()));
    }
    createMethod.addStatement("component.setController(controller)")
                .addStatement("controller.setComponent(component)")
                .addStatement("component.render()")
                .addStatement("component.bind()")
                .addStatement("controller.bind()");
    
    createMethod.addStatement("return blockControllerInstance");
    typeSpec.addMethod(createMethod.build());
    
    JavaFile javaFile = JavaFile.builder(blockControllerModel.getController()
                                                             .getPackage(),
                                         typeSpec.build())
                                .build();
    try {
      //      System.out.println(javaFile.toString());
      javaFile.writeTo(this.processingEnvironment.getFiler());
    } catch (IOException e) {
      throw new ProcessorException("Unable to write generated file: >>" +
                                   blockControllerModel.getController()
                                                       .getSimpleName() +
                                   ProcessorConstants.CREATOR_IMPL +
                                   "<< -> exception: " +
                                   e.getMessage());
    }
  }
  
  public static final class Builder {
    
    MetaModel metaModel;
    
    ProcessingEnvironment processingEnvironment;
    
    BlockControllerModel blockControllerModel;
    
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
    
    public Builder blockControllerModel(BlockControllerModel blockControllerModel) {
      this.blockControllerModel = blockControllerModel;
      return this;
    }
    
    public BlockControllerCreatorGenerator build() {
      return new BlockControllerCreatorGenerator(this);
    }
    
  }
  
}
