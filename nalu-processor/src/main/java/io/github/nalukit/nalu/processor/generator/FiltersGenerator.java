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

import io.github.nalukit.nalu.processor.ProcessorUtils;
import io.github.nalukit.nalu.processor.model.MetaModel;
import io.github.nalukit.nalu.processor.model.intern.EventModel;
import io.github.nalukit.nalu.processor.model.intern.FilterModel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import java.util.Objects;

public class FiltersGenerator {

  private ProcessingEnvironment processingEnvironment;

  private ProcessorUtils processorUtils;

  private MetaModel metaModel;

  private TypeSpec.Builder typeSpec;

  @SuppressWarnings("unused")
  private FiltersGenerator() {
    super();
  }

  private FiltersGenerator(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;
    this.metaModel             = builder.metaModel;
    this.typeSpec              = builder.typeSpec;

    setUp();
  }

  public static Builder builder() {
    return new Builder();
  }

  private void setUp() {
    this.processorUtils = ProcessorUtils.builder()
                                        .processingEnvironment(this.processingEnvironment)
                                        .build();
  }

  void generate() {
    // method must always be created!
    MethodSpec.Builder loadFiltersMethod = MethodSpec.methodBuilder("loadFilters")
                                                     .addAnnotation(Override.class)
                                                     .addModifiers(Modifier.PUBLIC);

    for (FilterModel filterModel : this.metaModel.getFilters()) {
      String filterClass = this.processorUtils.createFullClassName(filterModel.getFilter()
                                                                              .getClassName());
      loadFiltersMethod.addStatement("$T $L = new $T()",
                                     ClassName.get(filterModel.getFilter()
                                                              .getPackage(),
                                                   filterModel.getFilter()
                                                              .getSimpleName()),
                                     filterClass,
                                     ClassName.get(filterModel.getFilter()
                                                              .getPackage(),
                                                   filterModel.getFilter()
                                                              .getSimpleName()))
                       .addStatement("$L.setContext(super.context)",
                                     filterClass)
                       .addStatement("$L.setEventBus(super.eventBus)",
                                     filterClass);
      filterModel.getEventHandlers()
                 .forEach(m -> {
                   EventModel eventModel = filterModel.getEventModel(m.getEvent()
                                                                      .getClassName());
                   if (!Objects.isNull(eventModel)) {
                     loadFiltersMethod.addStatement("super.eventBus.addHandler($T.TYPE, e -> $L.$L(e))",
                                                    ClassName.get(eventModel.getEvent()
                                                                            .getPackage(),
                                                                  eventModel.getEvent()
                                                                            .getSimpleName()),
                                                    filterClass,
                                                    m.getMethodName());
                   }
                 });
      loadFiltersMethod.addStatement("super.routerConfiguration.getFilters().add($L)",
                                     filterClass);
    }

    typeSpec.addMethod(loadFiltersMethod.build());
  }

  public static final class Builder {

    ProcessingEnvironment processingEnvironment;

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

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public FiltersGenerator build() {
      return new FiltersGenerator(this);
    }

  }

}
