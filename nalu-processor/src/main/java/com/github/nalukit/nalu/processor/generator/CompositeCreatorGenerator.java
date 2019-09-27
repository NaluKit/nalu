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

import com.github.nalukit.nalu.client.Router;
import com.github.nalukit.nalu.client.component.AbstractCompositeController;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.AbstractCompositeCreator;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.application.CompositeFactory;
import com.github.nalukit.nalu.client.internal.application.CompositeInstance;
import com.github.nalukit.nalu.client.internal.application.IsCompositeCreator;
import com.github.nalukit.nalu.processor.ProcessorConstants;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.CompositeModel;
import com.github.nalukit.nalu.processor.util.BuildWithNaluCommentProvider;
import com.squareup.javapoet.*;
import org.gwtproject.event.shared.SimpleEventBus;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import java.io.IOException;

public class CompositeCreatorGenerator {

  private ProcessingEnvironment processingEnvironment;

  private CompositeModel compositeModel;

  @SuppressWarnings("unused")
  private CompositeCreatorGenerator() {
  }

  private CompositeCreatorGenerator(Builder builder) {
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
                                        .addJavadoc(BuildWithNaluCommentProvider.get()
                                                                                .getGeneratedComment())
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

  private MethodSpec createCreateMethod() {
    MethodSpec.Builder createMethod = MethodSpec.methodBuilder("create")
                                                .addModifiers(Modifier.PUBLIC)
                                                .addParameter(ParameterSpec.builder(String.class,
                                                                                    "parentControllerClassName")
                                                                           .build())
                                                .returns(ClassName.get(CompositeInstance.class))
                                                .addException(ClassName.get(RoutingInterceptionException.class))
                                                .addStatement("$T sb01 = new $T()",
                                                              ClassName.get(StringBuilder.class),
                                                              ClassName.get(StringBuilder.class))
                                                .addStatement("$T compositeInstance = new $T()",
                                                              ClassName.get(CompositeInstance.class),
                                                              ClassName.get(CompositeInstance.class))
                                                .addStatement("compositeInstance.setCompositeClassName($S)",
                                                              compositeModel.getProvider()
                                                                            .getClassName())
                                                .addStatement("$T<?, ?, ?> storedComposite = $T.get().getCompositeFormStore(parentControllerClassName, $S)",
                                                              ClassName.get(AbstractCompositeController.class),
                                                              ClassName.get(CompositeFactory.class),
                                                              compositeModel.getProvider()
                                                                            .getClassName());
    createMethod.beginControlFlow("if (storedComposite == null)")
                .addStatement("sb01.append(\"composite >>$L<< --> will be created\")",
                              compositeModel.getProvider()
                                            .getClassName())
                .addStatement("$T.get().logDetailed(sb01.toString(), 4)",
                              ClassName.get(ClientLogger.class))
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
                .addStatement("sb01.setLength(0)")
                .addStatement("sb01.append(\"composite >>$L<< --> created and data injected\")",
                              compositeModel.getProvider()
                                            .getClassName())
                .addStatement("$T.get().logDetailed(sb01.toString(), 5)",
                              ClassName.get(ClientLogger.class));
    if (compositeModel.isComponentCreator()) {
      createMethod.addStatement("$T component = composite.createComponent()",
                                ClassName.get(compositeModel.getComponentInterface()
                                                            .getPackage(),
                                              compositeModel.getComponentInterface()
                                                            .getSimpleName()))
                  .addStatement("sb01.setLength(0)")
                  .addStatement("sb01.append(\"component >>$L<< --> created using createComponent-Method of composite controller\")",
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
                  .addStatement("sb01.setLength(0)")
                  .addStatement("sb01.append(\"component >>$L<< --> created using new\")",
                                compositeModel.getComponent()
                                              .getClassName())
                  .addStatement("$T.get().logDetailed(sb01.toString(), 4)",
                                ClassName.get(ClientLogger.class));
    }
    createMethod.addStatement("component.setController(composite)")
                .addStatement("sb01.setLength(0)")
                .addStatement("sb01.append(\"component >>\").append(component.getClass().getCanonicalName()).append(\"<< --> created and controller instance injected\")")
                .addStatement("$T.get().logDetailed(sb01.toString(), 5)",
                              ClassName.get(ClientLogger.class))
                .addStatement("composite.setComponent(component)")
                .addStatement("sb01.setLength(0)")
                .addStatement("sb01.append(\"composite >>\").append(composite.getClass().getCanonicalName()).append(\"<< --> instance of >>\").append(component.getClass().getCanonicalName()).append(\"<< injected\")")
                .addStatement("$T.get().logDetailed(sb01.toString(), 5)",
                              ClassName.get(ClientLogger.class))
                .addStatement("component.render()")
                .addStatement("sb01.setLength(0)")
                .addStatement("sb01.append(\"component >>\").append(component.getClass().getCanonicalName()).append(\"<< --> rendered\")")
                .addStatement("$T.get().logDetailed(sb01.toString(), 5)",
                              ClassName.get(ClientLogger.class))
                .addStatement("component.bind()")
                .addStatement("sb01.setLength(0)")
                .addStatement("sb01.append(\"component >>\").append(component.getClass().getCanonicalName()).append(\"<< --> bound\")")
                .addStatement("$T.get().logDetailed(sb01.toString(), 5)",
                              ClassName.get(ClientLogger.class))
                .addStatement("$T.get().logSimple(\"compositeModel >>$L<< created\", 4)",
                              ClassName.get(ClientLogger.class),
                              compositeModel.getComponent()
                                            .getClassName());
    createMethod.nextControlFlow("else")
                .addStatement("sb01.append(\"composite >>\").append(storedComposite.getClass().getCanonicalName()).append(\"<< --> found in cache -> REUSE!\")")
                .addStatement("$T.get().logDetailed(sb01.toString(), 4)",
                              ClassName.get(ClientLogger.class))
                .addStatement("compositeInstance.setComposite(storedComposite)")
                .addStatement("compositeInstance.setCached(true)")
                .addStatement("compositeInstance.getComposite().setCached(true)")
                .endControlFlow();
    createMethod.addStatement("return compositeInstance");
    return createMethod.build();
  }

  private MethodSpec createSetParameterMethod() {
    MethodSpec.Builder method = MethodSpec.methodBuilder("setParameter")
                                          .addModifiers(Modifier.PUBLIC)
                                          .addParameter(ParameterSpec.builder(Object.class,
                                                                              "object")
                                                                     .build())
                                          .addParameter(ParameterSpec.builder(String[].class,
                                                                              "params")
                                                                     .build())
                                          .varargs()
                                          //                                                .returns(ClassName.get(CompositeInstance.class))
                                          .addException(ClassName.get(RoutingInterceptionException.class))
                                          .addStatement("$T composite = ($T) object",
                                                        ClassName.get(compositeModel.getProvider()
                                                                                    .getPackage(),
                                                                      compositeModel.getProvider()
                                                                                    .getSimpleName()),
                                                        ClassName.get(compositeModel.getProvider()
                                                                                    .getPackage(),
                                                                      compositeModel.getProvider()
                                                                                    .getSimpleName()))
                                          .addStatement("$T sb01 = new $T()",
                                                        ClassName.get(StringBuilder.class),
                                                        ClassName.get(StringBuilder.class));
    // compositeModel has parameters?
    if (compositeModel.getParameterAcceptors()
                      .size() > 0) {
      // has the model AcceptParameter ?
      if (compositeModel.getParameterAcceptors()
                        .size() > 0) {
        method.beginControlFlow("if (params != null)");
        for (int i = 0; i <
            compositeModel.getParameterAcceptors()
                          .size(); i++) {
          method.beginControlFlow("if (params.length >= " + (i + 1) + ")")
                .addStatement("sb01.setLength(0)")
                .addStatement("sb01.append(\"composite >>\").append(composite.getClass().getCanonicalName()).append(\"<< --> using method >>" +
                              compositeModel.getParameterAcceptors()
                                                .get(i)
                                                .getMethodName() + "<< to set value >>\").append(params[" +
                              i +
                              "]).append(\"<<\")")
                .addStatement("$T.get().logDetailed(sb01.toString(), 4)",
                              ClassName.get(ClientLogger.class))
                .addStatement("composite." +
                              compositeModel.getParameterAcceptors()
                                                .get(i)
                                                .getMethodName() + "(params[" +
                              i +
                              "])")
                .endControlFlow();
        }
        method.endControlFlow();
      }
    }
    return method.build();
  }

  private MethodSpec createConstructor() {
    return MethodSpec.constructorBuilder()
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
  }

  public static final class Builder {

    MetaModel metaModel;

    ProcessingEnvironment processingEnvironment;

    CompositeModel compositeModel;

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
