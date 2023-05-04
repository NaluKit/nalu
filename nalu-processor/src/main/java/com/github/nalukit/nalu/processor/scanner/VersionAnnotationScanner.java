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

import com.github.nalukit.nalu.client.application.annotation.Version;
import com.github.nalukit.nalu.processor.model.MetaModel;

import javax.lang.model.element.Element;

import static java.util.Objects.isNull;

public class VersionAnnotationScanner {

  private final Element versionElement;

  private final MetaModel metaModel;

  @SuppressWarnings("unused")
  private VersionAnnotationScanner(Builder builder) {
    super();
    this.versionElement = builder.versionElement;
    this.metaModel      = builder.metaModel;
    setUp();
  }

  public static Builder builder() {
    return new Builder();
  }

  private void setUp() {
  }

  public MetaModel scan() {
    // handle debug-annotation
    Version versionAnnotation = versionElement.getAnnotation(Version.class);
    if (isNull(versionAnnotation)) {
      this.metaModel.setApplicationVersion("APPLICATION-VERSION-NOT-AVAILABLE");
    } else {
      this.metaModel.setApplicationVersion(versionAnnotation.value());
    }
    return this.metaModel;
  }

  public static class Builder {

    Element versionElement;

    MetaModel metaModel;

    public Builder versionElement(Element versionElement) {
      this.versionElement = versionElement;
      return this;
    }

    public Builder metaModel(MetaModel metaModel) {
      this.metaModel = metaModel;
      return this;
    }

    public VersionAnnotationScanner build() {
      return new VersionAnnotationScanner(this);
    }

  }

}
