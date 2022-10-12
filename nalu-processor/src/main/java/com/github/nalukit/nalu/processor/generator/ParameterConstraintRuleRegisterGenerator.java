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

import com.github.nalukit.nalu.client.internal.constrain.ParameterConstraintRuleFactory;
import com.github.nalukit.nalu.processor.ProcessorConstants;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

public class ParameterConstraintRuleRegisterGenerator {

  private MetaModel        metaModel;
  private TypeSpec.Builder typeSpec;
  //  private Map<String, Integer> variableCounterMap;

  @SuppressWarnings("unused")
  private ParameterConstraintRuleRegisterGenerator() {
  }

  private ParameterConstraintRuleRegisterGenerator(Builder builder) {
    this.metaModel = builder.metaModel;
    this.typeSpec  = builder.typeSpec;
    //
    //    this.variableCounterMap = new HashMap<>();
  }

  public static Builder builder() {
    return new Builder();
  }

  void generate() {
    generateLoadParameterConstraintRules();
  }

  private void generateLoadParameterConstraintRules() {
    // generate method 'generateLoadParameterConstraintRules()'
    MethodSpec.Builder loadParameterConstraintRulesMethodBuilder = MethodSpec.methodBuilder("loadParameterConstraintRules")
                                                                             .addModifiers(Modifier.PUBLIC)
                                                                             .addAnnotation(Override.class);
    this.metaModel.getUsedParameterConstraints()
                  .forEach(m -> loadParameterConstraintRulesMethodBuilder.addStatement("$T.get().registerParameterConstraintRule($S, new $L())",
                                                                                       ClassName.get(ParameterConstraintRuleFactory.class),
                                                                                       m.getPackage() + "." + m.getSimpleName(),
                                                                                       ClassName.get(m.getPackage(),
                                                                                                     m.getSimpleName() +
                                                                                                     ProcessorConstants.IMPL)));
    typeSpec.addMethod(loadParameterConstraintRulesMethodBuilder.build());
  }

  public static final class Builder {

    MetaModel        metaModel;
    TypeSpec.Builder typeSpec;

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

    public ParameterConstraintRuleRegisterGenerator build() {
      return new ParameterConstraintRuleRegisterGenerator(this);
    }

  }

}
