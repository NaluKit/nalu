/*
 * Copyright (c) 2018 - Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *
 */
package com.github.nalukit.nalu.processor.generator;

import com.github.nalukit.nalu.client.component.AbstractSplitterController;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.application.SplitterCreator;
import com.github.nalukit.nalu.client.internal.application.SplitterFactory;
import com.github.nalukit.nalu.processor.model.ApplicationMetaModel;
import com.github.nalukit.nalu.processor.model.intern.ControllerModel;
import com.github.nalukit.nalu.processor.model.intern.SplitterModel;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.stream.IntStream;

public class SplitterControllerGenerator {

  private ApplicationMetaModel applicationMetaModel;
  private TypeSpec.Builder     typeSpec;

  @SuppressWarnings("unused")
  private SplitterControllerGenerator() {
  }

  private SplitterControllerGenerator(Builder builder) {
    this.applicationMetaModel = builder.applicationMetaModel;
    this.typeSpec = builder.typeSpec;
  }

  public static Builder builder() {
    return new Builder();
  }

  void generate() {
    generateLoadSplitters();
  }

  private void generateLoadSplitters() {
    // generate method 'loadSplitterts()'
    MethodSpec.Builder loadSplittersMethodBuilder = MethodSpec.methodBuilder("loadSplitterController")
                                                              .addModifiers(Modifier.PUBLIC)
                                                              .addAnnotation(Override.class);
    for (SplitterModel splitterModel : this.applicationMetaModel.getSplitters()) {
      MethodSpec.Builder createMethod = MethodSpec.methodBuilder("create")
                                                  .addAnnotation(Override.class)
                                                  .addModifiers(Modifier.PUBLIC)
                                                  .addParameter(ParameterSpec.builder(String[].class,
                                                                                      "parms")
                                                                             .build())
                                                  .varargs(true)
                                                  .returns(ParameterizedTypeName.get(ClassName.get(AbstractSplitterController.class),
                                                                                     this.applicationMetaModel.getContext()
                                                                                                              .getTypeName(),
                                                                                     splitterModel.getComponentInterface()
                                                                                                  .getTypeName(),
                                                                                     this.applicationMetaModel.getComponentType()
                                                                                                              .getTypeName()))
                                                  .addException(ClassName.get(RoutingInterceptionException.class))
                                                  .addStatement("$T sb01 = new $T()",
                                                                ClassName.get(StringBuilder.class),
                                                                ClassName.get(StringBuilder.class))
                                                  .addStatement("sb01.append(\"splitter >>$L<< --> will be created\")",
                                                                splitterModel.getProvider()
                                                                             .getPackage() +
                                                                "." +
                                                                splitterModel.getProvider()
                                                                             .getSimpleName())
                                                  .addStatement("$T.get().logSimple(sb01.toString(), 2)",
                                                                ClassName.get(ClientLogger.class))
                                                  .addStatement("$T splitter = new $T()",
                                                                ClassName.get(splitterModel.getProvider()
                                                                                           .getPackage(),
                                                                              splitterModel.getProvider()
                                                                                           .getSimpleName()),
                                                                ClassName.get(splitterModel.getProvider()
                                                                                           .getPackage(),
                                                                              splitterModel.getProvider()
                                                                                           .getSimpleName()))
                                                  .addStatement("splitter.setContext(context)")
                                                  .addStatement("splitter.setEventBus(eventBus)")
                                                  .addStatement("splitter.setRouter(router)")
                                                  .addStatement("sb01 = new $T()",
                                                                ClassName.get(StringBuilder.class))
                                                  .addStatement("sb01.append(\"splitter >>\").append(splitter.getClass().getCanonicalName()).append(\"<< --> created and data injected\")")
                                                  .addStatement("$T.get().logDetailed(sb01.toString(), 3)",
                                                                ClassName.get(ClientLogger.class))
                                                  .addStatement("$T component = new $T()",
                                                                ClassName.get(splitterModel.getComponentInterface()
                                                                                           .getPackage(),
                                                                              splitterModel.getComponentInterface()
                                                                                           .getSimpleName()),
                                                                ClassName.get(splitterModel.getComponent()
                                                                                           .getPackage(),
                                                                              splitterModel.getComponent()
                                                                                           .getSimpleName()))
                                                  .addStatement("component.setController(splitter)")
                                                  .addStatement("sb01 = new $T()",
                                                                ClassName.get(StringBuilder.class))
                                                  .addStatement("sb01.append(\"component >>\").append(component.getClass().getCanonicalName()).append(\"<< --> created and controller instance injected\")")
                                                  .addStatement("$T.get().logDetailed(sb01.toString(), 3)",
                                                                ClassName.get(ClientLogger.class))
                                                  .addStatement("splitter.setComponent(component)")
                                                  .addStatement("sb01 = new $T()",
                                                                ClassName.get(StringBuilder.class))
                                                  .addStatement("sb01.append(\"splitter >>\").append(splitter.getClass().getCanonicalName()).append(\"<< --> instance of >>\").append(component.getClass().getCanonicalName()).append(\"<< injected\")")
                                                  .addStatement("$T.get().logDetailed(sb01.toString(), 3)",
                                                                ClassName.get(ClientLogger.class))
                                                  .addStatement("component.render()")
                                                  .addStatement("sb01 = new $T()",
                                                                ClassName.get(StringBuilder.class))
                                                  .addStatement("sb01.append(\"component >>\").append(component.getClass().getCanonicalName()).append(\"<< --> rendered\")")
                                                  .addStatement("$T.get().logDetailed(sb01.toString(), 3)",
                                                                ClassName.get(ClientLogger.class))
                                                  .addStatement("component.bind()")
                                                  .addStatement("sb01 = new $T()",
                                                                ClassName.get(StringBuilder.class))
                                                  .addStatement("sb01.append(\"component >>\").append(component.getClass().getCanonicalName()).append(\"<< --> bound\")")
                                                  .addStatement("$T.get().logDetailed(sb01.toString(), 3)",
                                                                ClassName.get(ClientLogger.class))
                                                  .addStatement("$T.get().logSimple(\"splitter >>$L<< created\", 2)",
                                                                ClassName.get(ClientLogger.class),
                                                                splitterModel.getComponent()
                                                                             .getClassName());
      // splitter has parameters?
      if (splitterModel.getParameterAcceptors()
                       .size() > 0) {
        // has the model AccpetParameter ?
        if (splitterModel.getParameterAcceptors()
                         .size() > 0) {
          createMethod.beginControlFlow("if (parms != null)");
          for (int i = 0; i < splitterModel.getParameterAcceptors()
                                           .size(); i++) {
            createMethod.beginControlFlow("if (parms.length >= " + Integer.toString(i + 1) + ")")
                        .addStatement("sb01 = new $T()",
                                      ClassName.get(StringBuilder.class))
                        .addStatement("sb01.append(\"splitter >>\").append(splitter.getClass().getCanonicalName()).append(\"<< --> using method >>" + splitterModel.getParameterAcceptors()
                                                                                                                                                                   .get(i)
                                                                                                                                                                   .getMethodName() + "<< to set value >>\").append(parms[" + Integer.toString(i) + "]).append(\"<<\")")
                        .addStatement("$T.get().logDetailed(sb01.toString(), 2)",
                                      ClassName.get(ClientLogger.class))
                        .addStatement("splitter." + splitterModel.getParameterAcceptors()
                                                                   .get(i)
                                                                   .getMethodName() + "(parms[" + Integer.toString(i) + "])")
                        .endControlFlow();
          }
          createMethod.endControlFlow();
        }
      }
      createMethod.addStatement("return splitter");

      loadSplittersMethodBuilder.addComment("create SplitterCreator for: " +
                                            splitterModel.getProvider()
                                                         .getPackage() +
                                            "." +
                                            splitterModel.getProvider()
                                                         .getSimpleName())
                                .addStatement("$T.get().registerSplitter($S, $L)",
                                              ClassName.get(SplitterFactory.class),
                                              splitterModel.getProvider()
                                                           .getPackage() +
                                              "." +
                                              splitterModel.getProvider()
                                                           .getSimpleName(),
                                              TypeSpec.anonymousClassBuilder("")
                                                      .addSuperinterface(SplitterCreator.class)
                                                      .addMethod(createMethod.build())
                                                      .build());
    }
    typeSpec.addMethod(loadSplittersMethodBuilder.build());
  }

  private String createParaemter(List<String> parameters) {
    StringBuilder sb = new StringBuilder();
    IntStream.range(0,
                    parameters.size())
             .forEach(i -> {
               sb.append("\"")
                 .append(parameters.get(i))
                 .append("\"");
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

    ApplicationMetaModel applicationMetaModel;
    TypeSpec.Builder     typeSpec;

    /**
     * Set the EventBusMetaModel of the currently generated eventBus
     *
     * @param applicationMetaModel meta data model of the eventbus
     * @return the Builder
     */
    public Builder applicationMetaModel(ApplicationMetaModel applicationMetaModel) {
      this.applicationMetaModel = applicationMetaModel;
      return this;
    }

    /**
     * Set the typeSpec of the currently generated eventBus
     *
     * @param typeSpec ttype spec of the crruent eventbus
     * @return the Builder
     */
    Builder typeSpec(TypeSpec.Builder typeSpec) {
      this.typeSpec = typeSpec;
      return this;
    }

    public SplitterControllerGenerator build() {
      return new SplitterControllerGenerator(this);
    }
  }
}
