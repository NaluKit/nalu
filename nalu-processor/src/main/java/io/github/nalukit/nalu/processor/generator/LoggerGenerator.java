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

import io.github.nalukit.nalu.client.internal.NoCustomLogger;
import io.github.nalukit.nalu.processor.model.MetaModel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.util.Objects;

public class LoggerGenerator {

  private MetaModel metaModel;

  private TypeSpec.Builder typeSpec;

  @SuppressWarnings("unused")
  private LoggerGenerator() {
  }

  private LoggerGenerator(Builder builder) {
    this.metaModel = builder.metaModel;
    this.typeSpec  = builder.typeSpec;
  }

  public static Builder builder() {
    return new Builder();
  }

  void generate() {
    // method must always be created!
    MethodSpec.Builder loadLoggerConfigurationMethod = MethodSpec.methodBuilder("loadLoggerConfiguration")
                                                                 .addAnnotation(Override.class)
                                                                 .addModifiers(Modifier.PUBLIC);
    if (!Objects.isNull(metaModel.getLogger()) &&
        !metaModel.getLogger()
                  .getClassName()
                  .equals(NoCustomLogger.class.getCanonicalName())) {
      loadLoggerConfigurationMethod.addStatement("$T clientLogger = new $T()",
                                                 ClassName.get(metaModel.getClientLogger()
                                                                        .getPackage(),
                                                               metaModel.getClientLogger()
                                                                        .getSimpleName()),
                                                 ClassName.get(metaModel.getClientLogger()
                                                                        .getPackage(),
                                                               metaModel.getClientLogger()
                                                                        .getSimpleName()));
      loadLoggerConfigurationMethod.addStatement("this.naluLogger.setClientLogger(clientLogger)");
      loadLoggerConfigurationMethod.addStatement("$T customLogger = new $T()",
                                                 ClassName.get(metaModel.getLogger()
                                                                        .getPackage(),
                                                               metaModel.getLogger()
                                                                        .getSimpleName()),
                                                 ClassName.get(metaModel.getLogger()
                                                                        .getPackage(),
                                                               metaModel.getLogger()
                                                                        .getSimpleName()));
      loadLoggerConfigurationMethod.addStatement("customLogger.setContext(this.context)");
      loadLoggerConfigurationMethod.addStatement("this.naluLogger.setCustomLogger(customLogger)");
    }
    typeSpec.addMethod(loadLoggerConfigurationMethod.build());
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

    public LoggerGenerator build() {
      return new LoggerGenerator(this);
    }

  }

}
