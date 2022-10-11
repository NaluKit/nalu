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

import com.github.nalukit.nalu.client.tracker.IsTracker;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.EventModel;
import com.github.nalukit.nalu.processor.model.intern.TrackerModel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.Objects;

public class TrackerGenerator {

  private MetaModel metaModel;

  private TypeSpec.Builder typeSpec;

  @SuppressWarnings("unused")
  private TrackerGenerator() {
  }

  private TrackerGenerator(Builder builder) {
    this.metaModel = builder.metaModel;
    this.typeSpec     = builder.typeSpec;
  }

  public static Builder builder() {
    return new Builder();
  }

  void generate() {
    // method must always be created!
    MethodSpec.Builder loadTrackerConfigurationMethod = MethodSpec.methodBuilder("loadTrackerConfiguration")
                                                                  .addAnnotation(Override.class)
                                                                  .addModifiers(Modifier.PUBLIC)
                                                                  .returns(ClassName.get(IsTracker.class));
    TrackerModel trackerModel = metaModel.getTracker();
    if (!Objects.isNull(trackerModel)) {
      loadTrackerConfigurationMethod.addStatement("$T tracker = new $T()",
                                                  ClassName.get(trackerModel.getTracker()
                                                                            .getPackage(),
                                                                trackerModel.getTracker()
                                                                            .getSimpleName()),
                                                  ClassName.get(trackerModel.getTracker()
                                                                            .getPackage(),
                                                                trackerModel.getTracker()
                                                                            .getSimpleName()));
      loadTrackerConfigurationMethod.addStatement("tracker.setContext(super.context)");
      loadTrackerConfigurationMethod.addStatement("tracker.setEventBus(super.eventBus)");

      trackerModel.getEventHandlers()
             .forEach(m -> {
               EventModel eventModel = trackerModel.getEventModel(m.getEvent()
                                                              .getClassName());
               if (!Objects.isNull(eventModel)) {
                 loadTrackerConfigurationMethod.addStatement("super.eventBus.addHandler($T.TYPE, e -> tracker.$L(e))",
                                                 ClassName.get(eventModel.getEvent()
                                                                         .getPackage(),
                                                               eventModel.getEvent()
                                                                         .getSimpleName()),
                                                 m.getMethodName());
               }
             });
      loadTrackerConfigurationMethod.addStatement("tracker.bind()");
      loadTrackerConfigurationMethod.addStatement("return tracker");
    } else {
      loadTrackerConfigurationMethod.addStatement("return null");
    }
    typeSpec.addMethod(loadTrackerConfigurationMethod.build());
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

    public TrackerGenerator build() {
      return new TrackerGenerator(this);
    }

  }

}
