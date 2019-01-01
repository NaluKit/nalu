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

package com.github.nalukit.nalu.processor.scanner;

import com.github.nalukit.nalu.client.plugin.annotation.Plugins;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PluginsAnnotationScanner {

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private MetaModel metaModel;

  private Element pluginsElement;

  @SuppressWarnings("unused")
  private PluginsAnnotationScanner(Builder builder) {
    super();
    this.processingEnvironment = builder.processingEnvironment;
    this.metaModel = builder.metaModel;
    this.pluginsElement = builder.pluginsElement;
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

  public void scan(RoundEnvironment roundEnvironment)
      throws ProcessorException {
    TypeMirror pluginsTypeMirror = this.processingEnvironment.getElementUtils()
                                                             .getTypeElement(Plugins.class.getName())
                                                             .asType();

    List<String> pluginClasses = this.pluginsElement.getAnnotationMirrors()
                                                    .stream()
                                                    .filter(annotationMirror -> annotationMirror.getAnnotationType()
                                                                                                .equals(pluginsTypeMirror))
                                                    .flatMap(annotationMirror -> annotationMirror.getElementValues()
                                                                                                 .entrySet()
                                                                                                 .stream())
                                                    .findFirst()
                                                    .map(entry -> Arrays.stream(entry.getValue()
                                                                                     .toString()
                                                                                     .replace("{",
                                                                                              "")
                                                                                     .replace("}",
                                                                                              "")
                                                                                     .replace(" ",
                                                                                              "")
                                                                                     .split(","))
                                                                        .filter(c -> c.contains(".class"))
                                                                        .map((v) -> v.substring(0,
                                                                                                v.indexOf(".class")))
                                                                        .collect(Collectors.toList()))
                                                    .orElse(null);
    this.metaModel.getPlugins()
                  .clear();
    if (!Objects.isNull(pluginClasses)) {
      this.metaModel.getPlugins()
                    .addAll(pluginClasses.stream()
                                         .map(ClassNameModel::new)
                                         .collect(Collectors.toList()));
    }
  }

  public static class Builder {

    ProcessingEnvironment processingEnvironment;

    MetaModel metaModel;

    Element pluginsElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder metaModel(MetaModel metaModel) {
      this.metaModel = metaModel;
      return this;
    }

    public Builder pluginsElement(Element pluginsElement) {
      this.pluginsElement = pluginsElement;
      return this;
    }

    public PluginsAnnotationScanner build() {
      return new PluginsAnnotationScanner(this);
    }

  }

}
