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
import org.gwtproject.regexp.shared.RegExp;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.Arrays;

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
    if (this.parameterConstraintRuleModel.isNotNullCheck()) {
      method.beginControlFlow("if (parameter == null || parameter.length() == 0)")
            .addStatement("return false")
            .endControlFlow();
    }
    if (this.parameterConstraintRuleModel.isMaxLengthCheck()) {
      method.beginControlFlow("if (parameter != null && parameter.length() > " +
                              this.parameterConstraintRuleModel.getMaxLength() +
                              ")")
            .addStatement("return false")
            .endControlFlow();
    }
    if (this.parameterConstraintRuleModel.isPatternCheck()) {
      method.beginControlFlow("if (parameter != null)")
            .addStatement("$T regExp = $T.compile($S)",
                          ClassName.get(RegExp.class),
                          ClassName.get(RegExp.class),
                          this.parameterConstraintRuleModel.getPattern())
            .addStatement("return regExp.test(parameter)")
            .endControlFlow();
    }
    if (this.parameterConstraintRuleModel.isBlackListingCheck()) {
      if (this.parameterConstraintRuleModel.getBlackList().length > 0) {
        method.beginControlFlow("if (parameter != null)")
              .beginControlFlow("if (" + this.createConditionFromArray(false) + ")",
                                ClassName.get(Arrays.class))
              .addStatement("return false")
              .endControlFlow()
              .endControlFlow();
      }
    }
    if (this.parameterConstraintRuleModel.isWhiteListingCheck()) {
      if (this.parameterConstraintRuleModel.getWhiteList().length > 0) {
        method.beginControlFlow("if (parameter != null)")
              .beginControlFlow("if (" + this.createConditionFromArray(true) + ")",
                                ClassName.get(Arrays.class))
              .addStatement("return false")
              .endControlFlow()
              .endControlFlow();
      }
    }
    method.addStatement("return true");
    return method.build();
  }

  private String createConditionFromArray(boolean isWhiteList) {
    boolean       notFirstComma = false;
    StringBuilder sb            = new StringBuilder();
    if (isWhiteList) {
      sb.append("!");
    }
    sb.append("$T.asList(");
    if (isWhiteList) {
      for (String s : this.parameterConstraintRuleModel.getWhiteList()) {
        if (notFirstComma) {
          sb.append(", ");
        }
        notFirstComma = true;
        sb.append("\"")
          .append(s)
          .append("\"");
      }
    } else {
      for (String s : this.parameterConstraintRuleModel.getBlackList()) {
        if (notFirstComma) {
          sb.append(", ");
        }
        notFirstComma = true;
        sb.append("\"")
          .append(s)
          .append("\"");
      }
    }
    sb.append(").contains(parameter)");
    return sb.toString();
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
