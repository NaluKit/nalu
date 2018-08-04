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
package com.github.mvp4g.nalu.processor.generator;

import com.github.mvp4g.nalu.client.application.annotation.Debug;
import com.github.mvp4g.nalu.processor.ProcessorException;
import com.github.mvp4g.nalu.processor.model.ApplicationMetaModel;
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

  public void generate()
    throws ProcessorException {
    // method must always be created!
    MethodSpec.Builder loadDebugConfigurationMethod = MethodSpec.methodBuilder("loadDebugConfiguration")
                                                                .addAnnotation(Override.class)
                                                                .addModifiers(Modifier.PUBLIC);
    if (applicationMetaModel.isHavingDebugAnnotation()) {
      loadDebugConfigurationMethod.addStatement("super.setDebugEnabled(true)")
                                  .addStatement("super.setLogger(new $T())",
                                                applicationMetaModel.getDebugLogger()
                                                                    .getTypeName())
                                  .addStatement("super.setLogLevel($T.LogLevel.$L)",
                                                Debug.class,
                                                applicationMetaModel.getDebugLogLevel());
    } else {
      loadDebugConfigurationMethod.addStatement("super.setDebugEnabled(false)");
    }
    typeSpec.addMethod(loadDebugConfigurationMethod.build());
  }

  public static final class Builder {

    ApplicationMetaModel applicationMetaModel;
    TypeSpec.Builder  typeSpec;

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
    public Builder typeSpec(TypeSpec.Builder typeSpec) {
      this.typeSpec = typeSpec;
      return this;
    }

    public DebugGenerator build() {
      return new DebugGenerator(this);
    }
  }
}
