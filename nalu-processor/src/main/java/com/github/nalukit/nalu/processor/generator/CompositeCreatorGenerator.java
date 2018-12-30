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

import com.github.nalukit.nalu.client.Router;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.AbstractCompositeCreator;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.application.CompositeInstance;
import com.github.nalukit.nalu.client.internal.application.IsCompositeCreator;
import com.github.nalukit.nalu.processor.ProcessorConstants;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.CompositeModel;
import com.squareup.javapoet.*;
import org.gwtproject.event.shared.SimpleEventBus;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import java.io.IOException;

public class CompositeCreatorGenerator {

  private MetaModel metaModel;

  private ProcessingEnvironment processingEnvironment;

  private CompositeModel compositeModel;

  @SuppressWarnings("unused")
  private CompositeCreatorGenerator() {
  }

  private CompositeCreatorGenerator(Builder builder) {
    this.metaModel = builder.metaModel;
    this.processingEnvironment = builder.processingEnvironment;
    this.compositeModel = builder.compositeModel;
  }

  public static Builder builder() {
    return new Builder();
  }

  public void generate()
      throws ProcessorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(compositeModel.getProvider()
                                                                    .getSimpleName() + ProcessorConstants.CREATOR_IMPL)
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractCompositeCreator.class),
                                                                              compositeModel.getContext()
                                                                                            .getTypeName()))
                                        .addModifiers(Modifier.PUBLIC,
                                                      Modifier.FINAL)
                                        .addSuperinterface(ClassName.get(IsCompositeCreator.class));
    // constructor ...
    MethodSpec constructor = MethodSpec.constructorBuilder()
                                       .addModifiers(Modifier.PUBLIC)
                                       .addParameter(ParameterSpec.builder(ClassName.get(Router.class),
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
    typeSpec.addMethod(constructor);
    // create Method
    MethodSpec.Builder createMethod = MethodSpec.methodBuilder("create")
                                                .addModifiers(Modifier.PUBLIC)
                                                .addParameter(ParameterSpec.builder(String[].class,
                                                                                    "parms")
                                                                           .build())
                                                .varargs()
                                                .returns(ClassName.get(CompositeInstance.class))
                                                .addException(ClassName.get(RoutingInterceptionException.class))
                                                .addStatement("$T sb01 = new $T()",
                                                              ClassName.get(StringBuilder.class),
                                                              ClassName.get(StringBuilder.class))
                                                .addStatement("sb01.append(\"compositeModel >>$L<< --> will be created\")",
                                                              compositeModel.getProvider()
                                                                            .getClassName())
                                                .addStatement("$T.get().logDetailed(sb01.toString(), 4)",
                                                              ClassName.get(ClientLogger.class))
                                                .addStatement("$T compositeModel = new $T()",
                                                              ClassName.get(compositeModel.getProvider()
                                                                                          .getPackage(),
                                                                            compositeModel.getProvider()
                                                                                          .getSimpleName()),
                                                              ClassName.get(compositeModel.getProvider()
                                                                                          .getPackage(),
                                                                            compositeModel.getProvider()
                                                                                          .getSimpleName()))
                                                .addStatement("compositeModel.setContext(context)")
                                                .addStatement("compositeModel.setEventBus(eventBus)")
                                                .addStatement("compositeModel.setRouter(router)")
                                                .addStatement("sb01 = new $T()",
                                                              ClassName.get(StringBuilder.class))
                                                .addStatement("sb01.append(\"compositeModel >>$L<< --> created and data injected\")",
                                                              compositeModel.getProvider()
                                                                            .getClassName())
                                                .addStatement("$T.get().logDetailed(sb01.toString(), 5)",
                                                              ClassName.get(ClientLogger.class));
    if (compositeModel.isComponentCreator()) {
      createMethod.addStatement("$T component = compositeModel.createComponent()",
                                ClassName.get(compositeModel.getComponentInterface()
                                                            .getPackage(),
                                              compositeModel.getComponentInterface()
                                                            .getSimpleName()))
                  .addStatement("sb01 = new $T()",
                                ClassName.get(StringBuilder.class))
                  .addStatement("sb01.append(\"component >>$L<< --> created using createComponent-Method of compositeModel controller\")",
                                compositeModel.getComponent()
                                              .getClassName())
                  .addStatement("$T.get().logDetailed(sb01.toString(), 4)",
                                ClassName.get(ClientLogger.class));
    } else {
      createMethod.addStatement("$T component = new $T()",
                                ClassName.get(compositeModel.getComponentInterface()
                                                            .getPackage(),
                                              compositeModel.getComponentInterface()
                                                            .getSimpleName()),
                                ClassName.get(compositeModel.getComponent()
                                                            .getPackage(),
                                              compositeModel.getComponent()
                                                            .getSimpleName()))
                  .addStatement("sb01 = new $T()",
                                ClassName.get(StringBuilder.class))
                  .addStatement("sb01.append(\"component >>$L<< --> created using new\")",
                                compositeModel.getComponent()
                                              .getClassName())
                  .addStatement("$T.get().logDetailed(sb01.toString(), 4)",
                                ClassName.get(ClientLogger.class));
    }
    createMethod.addStatement("component.setController(compositeModel)")
                .addStatement("sb01 = new $T()",
                              ClassName.get(StringBuilder.class))
                .addStatement("sb01.append(\"component >>\").append(component.getClass().getCanonicalName()).append(\"<< --> created and controller instance injected\")")
                .addStatement("$T.get().logDetailed(sb01.toString(), 5)",
                              ClassName.get(ClientLogger.class))
                .addStatement("compositeModel.setComponent(component)")
                .addStatement("sb01 = new $T()",
                              ClassName.get(StringBuilder.class))
                .addStatement("sb01.append(\"compositeModel >>\").append(compositeModel.getClass().getCanonicalName()).append(\"<< --> instance of >>\").append(component.getClass().getCanonicalName()).append(\"<< injected\")")
                .addStatement("$T.get().logDetailed(sb01.toString(), 5)",
                              ClassName.get(ClientLogger.class))
                .addStatement("component.render()")
                .addStatement("sb01 = new $T()",
                              ClassName.get(StringBuilder.class))
                .addStatement("sb01.append(\"component >>\").append(component.getClass().getCanonicalName()).append(\"<< --> rendered\")")
                .addStatement("$T.get().logDetailed(sb01.toString(), 5)",
                              ClassName.get(ClientLogger.class))
                .addStatement("component.bind()")
                .addStatement("sb01 = new $T()",
                              ClassName.get(StringBuilder.class))
                .addStatement("sb01.append(\"component >>\").append(component.getClass().getCanonicalName()).append(\"<< --> bound\")")
                .addStatement("$T.get().logDetailed(sb01.toString(), 5)",
                              ClassName.get(ClientLogger.class))
                .addStatement("$T.get().logSimple(\"compositeModel >>$L<< created\", 4)",
                              ClassName.get(ClientLogger.class),
                              compositeModel.getComponent()
                                            .getClassName());
    // compositeModel has parameters?
    if (compositeModel.getParameterAcceptors()
                      .size() > 0) {
      // has the model AccpetParameter ?
      if (compositeModel.getParameterAcceptors()
                        .size() > 0) {
        createMethod.beginControlFlow("if (parms != null)");
        for (int i = 0; i <
                        compositeModel.getParameterAcceptors()
                                      .size(); i++) {
          createMethod.beginControlFlow("if (parms.length >= " + Integer.toString(i + 1) + ")")
                      .addStatement("sb01 = new $T()",
                                    ClassName.get(StringBuilder.class))
                      .addStatement("sb01.append(\"compositeModel >>\").append(compositeModel.getClass().getCanonicalName()).append(\"<< --> using method >>" +
                                    compositeModel.getParameterAcceptors()
                                                  .get(i)
                                                  .getMethodName() +
                                    "<< to set value >>\").append(parms[" +
                                    Integer.toString(i) +
                                    "]).append(\"<<\")")
                      .addStatement("$T.get().logDetailed(sb01.toString(), 4)",
                                    ClassName.get(ClientLogger.class))
                      .addStatement("compositeModel." +
                                    compositeModel.getParameterAcceptors()
                                                  .get(i)
                                                  .getMethodName() +
                                    "(parms[" +
                                    Integer.toString(i) +
                                    "])")
                      .endControlFlow();
        }
        createMethod.endControlFlow();
      }
    }
    createMethod.addStatement("$T compositeInstance = new $T()",
                              ClassName.get(CompositeInstance.class),
                              ClassName.get(CompositeInstance.class))
                .addStatement("compositeInstance.setCompositeClassName(compositeModel.getClass().getCanonicalName())")
                .addStatement("compositeInstance.setComposite(compositeModel)")
                .addStatement("return compositeInstance");

    typeSpec.addMethod(createMethod.build());

    JavaFile javaFile = JavaFile.builder(this.compositeModel.getProvider()
                                                            .getPackage(),
                                         typeSpec.build())
                                .build();
    try {
      //      System.out.println(javaFile.toString());
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

  public static final class Builder {

    MetaModel metaModel;

    ProcessingEnvironment processingEnvironment;

    CompositeModel compositeModel;

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
