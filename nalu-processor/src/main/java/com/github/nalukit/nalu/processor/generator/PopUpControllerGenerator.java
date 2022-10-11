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

import com.github.nalukit.nalu.client.component.AlwaysShowPopUp;
import com.github.nalukit.nalu.client.internal.application.PopUpConditionFactory;
import com.github.nalukit.nalu.client.internal.application.PopUpControllerFactory;
import com.github.nalukit.nalu.processor.ProcessorConstants;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopUpControllerGenerator {

  private MetaModel            metaModel;
  private TypeSpec.Builder     typeSpec;
  private Map<String, Integer> variableCounterMap;

  @SuppressWarnings("unused")
  private PopUpControllerGenerator() {
  }

  private PopUpControllerGenerator(Builder builder) {
    this.metaModel = builder.metaModel;
    this.typeSpec  = builder.typeSpec;

    this.variableCounterMap = new HashMap<>();
  }

  public static Builder builder() {
    return new Builder();
  }

  void generate() {
    MethodSpec.Builder loadPopUpControllerFactoryMethodBuilder = MethodSpec.methodBuilder("loadPopUpControllerFactory")
                                                                           .addModifiers(Modifier.PUBLIC)
                                                                           .addAnnotation(Override.class);
    List<String> generatedConditionClassNames = new ArrayList<>();
    this.metaModel.getPopUpControllers()
                  .forEach(popUpControllerModel -> {
                    loadPopUpControllerFactoryMethodBuilder.addStatement("$T.get().registerPopUpController($S, new $L(router, context, eventBus))",
                                                                         ClassName.get(PopUpControllerFactory.class),
                                                                         popUpControllerModel.getName(),
                                                                         ClassName.get(popUpControllerModel.getController()
                                                                                                           .getPackage(),
                                                                                       popUpControllerModel.getController()
                                                                                                           .getSimpleName() +
                                                                                       ProcessorConstants.CREATOR_IMPL));
                    if (AlwaysShowPopUp.class.getSimpleName()
                                             .equals(popUpControllerModel.getCondition()
                                                                         .getSimpleName())) {
                      loadPopUpControllerFactoryMethodBuilder.addStatement("$T.get().registerCondition($S,  super.alwaysShowPopUp)",
                                                                           ClassName.get(PopUpConditionFactory.class),
                                                                           popUpControllerModel.getName());
                    } else {
                      String conditionVariableName;
                      if (generatedConditionClassNames.contains(popUpControllerModel.getCondition()
                                                                                    .getClassName())) {
                        conditionVariableName = this.setFirstCharacterToLowerCase(popUpControllerModel.getCondition()
                                                                                                      .getSimpleName()) +
                                                this.getNameWithVariableCount(popUpControllerModel.getCondition(),
                                                                              false);
                      } else {
                        conditionVariableName = this.setFirstCharacterToLowerCase(popUpControllerModel.getCondition()
                                                                                                      .getSimpleName()) +
                                                this.getNameWithVariableCount(popUpControllerModel.getCondition(),
                                                                              true);

                        loadPopUpControllerFactoryMethodBuilder.addStatement("$T $L = new $T()",
                                                                             ClassName.get(popUpControllerModel.getCondition()
                                                                                                               .getPackage(),
                                                                                           popUpControllerModel.getCondition()
                                                                                                               .getSimpleName()),
                                                                             conditionVariableName,
                                                                             ClassName.get(popUpControllerModel.getCondition()
                                                                                                               .getPackage(),
                                                                                           popUpControllerModel.getCondition()
                                                                                                               .getSimpleName()))
                                                               .addStatement("$L.setContext(super.context)",
                                                                             conditionVariableName);
                        // remember generated condition to avoid creating the same class again!
                        generatedConditionClassNames.add(popUpControllerModel.getCondition()
                                                                             .getClassName());
                      }
                      loadPopUpControllerFactoryMethodBuilder.addStatement("$T.get().registerCondition($S,  $L)",
                                                                           ClassName.get(PopUpConditionFactory.class),
                                                                           conditionVariableName);
                    }
                  });
    typeSpec.addMethod(loadPopUpControllerFactoryMethodBuilder.build());
  }

  private String setFirstCharacterToLowerCase(String className) {
    return className.substring(0,
                               1)
                    .toLowerCase() + className.substring(1);
  }

  /**
   * Created a String with a number at the end to get unique condition
   * variable names
   *
   * @param classNameModel the condition class name
   * @return uniques string with number
   */
  private String getNameWithVariableCount(ClassNameModel classNameModel,
                                          boolean createNew) {
    if (createNew) {
      // new condition class!
      if (this.variableCounterMap.get(classNameModel.getClassName()) == null) {
        this.variableCounterMap.put(classNameModel.getClassName(),
                                    1);
        return "_1";
      }
      // already used condition class
      Integer counter    = this.variableCounterMap.get(classNameModel.getClassName());
      Integer newCounter = counter + 1;
      this.variableCounterMap.put(classNameModel.getClassName(),
                                  newCounter);
      return "_" + newCounter;
    } else {
      Integer count = this.variableCounterMap.get(classNameModel.getClassName());
      return "_" + count;
    }
  }

  public static final class Builder {

    MetaModel metaModel;

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

    public PopUpControllerGenerator build() {
      return new PopUpControllerGenerator(this);
    }

  }

}
