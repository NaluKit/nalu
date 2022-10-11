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

import com.github.nalukit.nalu.processor.ProcessorUtils;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.EventModel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import java.util.Objects;

public class HandlerGenerator {

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private MetaModel metaModel;

  private TypeSpec.Builder typeSpec;

  @SuppressWarnings("unused")
  private HandlerGenerator() {
  }

  private HandlerGenerator(Builder builder) {
    this.metaModel             = builder.metaModel;
    this.processingEnvironment = builder.processingEnvironment;
    this.typeSpec              = builder.typeSpec;
    setUp();
  }

  public static Builder builder() {
    return new Builder();
  }

  private void setUp() {
    this.processorUtils = ProcessorUtils.builder()
                                        .processingEnvironment(this.processingEnvironment)
                                        .build();
  }

  void generate() {
    // method must always be created!
    MethodSpec.Builder loadHandlersMethod = MethodSpec.methodBuilder("loadHandlers")
                                                      .addAnnotation(Override.class)
                                                      .addModifiers(Modifier.PUBLIC);

    this.metaModel.getHandlers()
                  .forEach(handler -> {
                    String variableName = this.processorUtils.createFullClassName(handler.getHandler()
                                                                                         .getPackage(),
                                                                                  handler.getHandler()
                                                                                         .getSimpleName());
                    loadHandlersMethod.addStatement("$T $L = new $T()",
                                                    ClassName.get(handler.getHandler()
                                                                         .getPackage(),
                                                                  handler.getHandler()
                                                                         .getSimpleName()),
                                                    variableName,
                                                    ClassName.get(handler.getHandler()
                                                                         .getPackage(),
                                                                  handler.getHandler()
                                                                         .getSimpleName()))
                                      .addStatement("$L.setContext(super.context)",
                                                    variableName)
                                      .addStatement("$L.setEventBus(super.eventBus)",
                                                    variableName)
                                      .addStatement("$L.setRouter(super.router)",
                                                    variableName);

                    handler.getEventHandlers()
                           .forEach(m -> {
                             EventModel eventModel = handler.getEventModel(m.getEvent()
                                                                            .getClassName());
                             if (!Objects.isNull(eventModel)) {
                               loadHandlersMethod.addStatement("super.eventBus.addHandler($T.TYPE, e -> $L.$L(e))",
                                                               ClassName.get(eventModel.getEvent()
                                                                                       .getPackage(),
                                                                             eventModel.getEvent()
                                                                                       .getSimpleName()),
                                                               variableName,
                                                               m.getMethodName());
                             }
                           });

                    loadHandlersMethod.addStatement("$L.bind()",
                                                    variableName);
                  });

    typeSpec.addMethod(loadHandlersMethod.build());
  }

  public static final class Builder {

    ProcessingEnvironment processingEnvironment;

    MetaModel metaModel;

    TypeSpec.Builder typeSpec;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

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

    public HandlerGenerator build() {
      return new HandlerGenerator(this);
    }

  }

}
