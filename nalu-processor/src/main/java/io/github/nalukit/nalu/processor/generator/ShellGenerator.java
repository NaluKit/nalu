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
package io.github.nalukit.nalu.processor.generator;

import io.github.nalukit.nalu.client.component.AlwaysLoadComposite;
import io.github.nalukit.nalu.client.internal.application.CompositeConditionFactory;
import io.github.nalukit.nalu.client.internal.application.ShellFactory;
import io.github.nalukit.nalu.client.internal.route.ShellConfig;
import io.github.nalukit.nalu.processor.ProcessorConstants;
import io.github.nalukit.nalu.processor.model.MetaModel;
import io.github.nalukit.nalu.processor.model.intern.ClassNameModel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ShellGenerator {

  private MetaModel            metaModel;
  private TypeSpec.Builder     typeSpec;
  private Map<String, Integer> variableCounterMap;

  @SuppressWarnings("unused")
  private ShellGenerator() {
  }

  private ShellGenerator(Builder builder) {
    this.metaModel = builder.metaModel;
    this.typeSpec  = builder.typeSpec;

    this.variableCounterMap = new HashMap<>();
  }

  public static Builder builder() {
    return new Builder();
  }

  void generate() {
    generateLoadShells();
    generateLoadShellFactory();

    this.variableCounterMap = new HashMap<>();
  }

  private void generateLoadShells() {
    // generate method 'generateLoadShells()'
    MethodSpec.Builder loadShellsMethodBuilder = MethodSpec.methodBuilder("loadShells")
                                                           .addModifiers(Modifier.PUBLIC)
                                                           .addAnnotation(Override.class);

    this.metaModel.getShells()
                  .forEach(shellModel -> loadShellsMethodBuilder.addStatement("super.shellConfiguration.getShells().add(new $T($S, $S))",
                                                                              ClassName.get(ShellConfig.class),
                                                                              "/" + shellModel.getName(),
                                                                              shellModel.getShell()
                                                                                        .getClassName()));
    typeSpec.addMethod(loadShellsMethodBuilder.build());
  }

  private void generateLoadShellFactory() {
    // generate method 'generateLoadShells()'
    MethodSpec.Builder loadShellFactoryMethodBuilder = MethodSpec.methodBuilder("loadShellFactory")
                                                                 .addModifiers(Modifier.PUBLIC)
                                                                 .addAnnotation(Override.class);
    this.metaModel.getShells()
                  .forEach(shellModel -> {
                    // add return statement
                    loadShellFactoryMethodBuilder.addStatement("$T.INSTANCE.registerShell($S, new $L(router, context, eventBus))",
                                                               ClassName.get(ShellFactory.class),
                                                               shellModel.getShell()
                                                                         .getPackage() +
                                                               "." +
                                                               shellModel.getShell()
                                                                         .getSimpleName(),
                                                               ClassName.get(shellModel.getShell()
                                                                                       .getPackage(),
                                                                             shellModel.getShell()
                                                                                       .getSimpleName() +
                                                                             ProcessorConstants.CREATOR_IMPL));

                    if (!shellModel.getComposites()
                                   .isEmpty()) {
                      shellModel.getComposites()
                                .forEach(controllerCompositeModel -> {
                                  if (AlwaysLoadComposite.class.getSimpleName()
                                                               .equals(controllerCompositeModel.getCondition()
                                                                                               .getSimpleName())) {
                                    loadShellFactoryMethodBuilder.addStatement("$T.INSTANCE.registerCondition($S, $S, super.alwaysLoadComposite)",
                                                                               ClassName.get(CompositeConditionFactory.class),
                                                                               shellModel.getShell()
                                                                                         .getPackage() +
                                                                               "." +
                                                                               shellModel.getShell()
                                                                                         .getSimpleName(),
                                                                               controllerCompositeModel.getComposite()
                                                                                                       .getPackage() +
                                                                               "." +
                                                                               controllerCompositeModel.getComposite()
                                                                                                       .getSimpleName());
                                  } else {
                                    String conditionVariableName;
                                    if (this.metaModel.isCondtionAlreadyGenerated(controllerCompositeModel.getCondition())) {
                                      conditionVariableName = this.setFirstCharacterToLowerCase(controllerCompositeModel.getCondition()
                                                                                                                        .getSimpleName()) +
                                                              this.getNameWithVariableCount(controllerCompositeModel.getCondition(),
                                                                                            false);
                                    } else {
                                      conditionVariableName = this.setFirstCharacterToLowerCase(controllerCompositeModel.getCondition()
                                                                                                                        .getSimpleName()) +
                                                              this.getNameWithVariableCount(controllerCompositeModel.getCondition(),
                                                                                            true);

                                      loadShellFactoryMethodBuilder.addStatement("$T $L = new $T()",
                                                                                 ClassName.get(controllerCompositeModel.getCondition()
                                                                                                                       .getPackage(),
                                                                                               controllerCompositeModel.getCondition()
                                                                                                                       .getSimpleName()),
                                                                                 conditionVariableName,
                                                                                 ClassName.get(controllerCompositeModel.getCondition()
                                                                                                                       .getPackage(),
                                                                                               controllerCompositeModel.getCondition()
                                                                                                                       .getSimpleName()))
                                                                   .addStatement("$L.setContext(super.context)",
                                                                                 conditionVariableName);
                                      // remember generated condition to avoid creating the same class again!
                                      this.metaModel.addGeneratedCondition(controllerCompositeModel.getCondition());
                                    }
                                    loadShellFactoryMethodBuilder.addStatement("$T.INSTANCE.registerCondition($S, $S, $L)",
                                                                               ClassName.get(CompositeConditionFactory.class),
                                                                               shellModel.getShell()
                                                                                         .getPackage() +
                                                                               "." +
                                                                               shellModel.getShell()
                                                                                         .getSimpleName(),
                                                                               controllerCompositeModel.getComposite()
                                                                                                       .getPackage() +
                                                                               "." +
                                                                               controllerCompositeModel.getComposite()
                                                                                                       .getSimpleName(),
                                                                               conditionVariableName);
                                  }
                                });
                    }
                  });
    typeSpec.addMethod(loadShellFactoryMethodBuilder.build());
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

    public ShellGenerator build() {
      return new ShellGenerator(this);
    }

  }

}
