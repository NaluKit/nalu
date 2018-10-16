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

import com.github.nalukit.nalu.client.application.annotation.Debug;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.processor.model.ApplicationMetaModel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

public class DebugGenerator {

  private ApplicationMetaModel applicationMetaModel;

  private TypeSpec.Builder     typeSpec;

  @SuppressWarnings("unused")
  private DebugGenerator() {
  }

  private DebugGenerator(Builder builder) {
    this.applicationMetaModel = builder.applicationMetaModel;
    this.typeSpec = builder.typeSpec;
  }

  public static Builder builder() {
    return new Builder();
  }

  void generate() {
    // method must always be created!
    MethodSpec.Builder loadDebugConfigurationMethod = MethodSpec.methodBuilder("loadDebugConfiguration")
                                                                .addAnnotation(Override.class)
                                                                .addModifiers(Modifier.PUBLIC);
    if (applicationMetaModel.isHavingDebugAnnotation()) {
      loadDebugConfigurationMethod.addStatement("$T.get().register($L, new $T(), $T.LogLevel.$L)",
                                                ClassName.get(ClientLogger.class),
                                                "true",
                                                applicationMetaModel.getDebugLogger()
                                                                    .getTypeName(),
                                                ClassName.get(Debug.class),
                                                applicationMetaModel.getDebugLogLevel());

      //    } else {
      //      loadDebugConfigurationMethod.addStatement("$T.get().register($L, new $T(), $T.LogLevel.$L)",
      //                                                ClassName.get(ClientLogger.class),
      //                                                "false",
      //                                                ClassName.get(DefaultLogger.class),
      //                                                ClassName.get(Debug.class),
      //                                                applicationMetaModel.getDebugLogLevel());
    }
    typeSpec.addMethod(loadDebugConfigurationMethod.build());
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

    public DebugGenerator build() {
      return new DebugGenerator(this);
    }
  }
}
