/*
 * Copyright (c) 2018 - 2020 - Frank Hossfeld
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

import com.github.nalukit.nalu.client.application.event.LogEvent;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.util.Objects;

public class ErrorPopUpControllerGenerator {
  
  private MetaModel metaModel;
  
  private TypeSpec.Builder typeSpec;
  
  @SuppressWarnings("unused")
  private ErrorPopUpControllerGenerator() {
  }
  
  private ErrorPopUpControllerGenerator(Builder builder) {
    this.metaModel = builder.metaModel;
    this.typeSpec  = builder.typeSpec;
  }
  
  public static Builder builder() {
    return new Builder();
  }
  
  void generate() {
    MethodSpec.Builder createErrorPopUpControllerMethodBuilder = MethodSpec.methodBuilder("loadErrorPopUpController")
                                                                           .addModifiers(Modifier.PUBLIC)
                                                                           .addAnnotation(Override.class);
    if (Objects.isNull(this.metaModel.getErrorPopUpController())) {
      createErrorPopUpControllerMethodBuilder.addStatement("this.eventBus.fireEvent($T.create()" +
                                                           ".sdmOnly(true)" +
                                                           ".addMessage(\"no ErrorPopUpController found!\"))",
                                                           ClassName.get(LogEvent.class));
    } else {
      createErrorPopUpControllerMethodBuilder.addStatement("$T errorPopUpController = new $T()",
                                                           ClassName.get(this.metaModel.getErrorPopUpController()
                                                                                       .getController()
                                                                                       .getPackage(),
                                                                         this.metaModel.getErrorPopUpController()
                                                                                       .getController()
                                                                                       .getSimpleName()),
                                                           ClassName.get(this.metaModel.getErrorPopUpController()
                                                                                       .getController()
                                                                                       .getPackage(),
                                                                         this.metaModel.getErrorPopUpController()
                                                                                       .getController()
                                                                                       .getSimpleName()))
                                             .addStatement("errorPopUpController.setContext(context)")
                                             .addStatement("errorPopUpController.setEventBus(eventBus)")
                                             .addStatement("errorPopUpController.setRouter(router)");
      if (this.metaModel.getErrorPopUpController()
                        .isComponentCreator()) {
        createErrorPopUpControllerMethodBuilder.addStatement("$T component = controller.createErrorPopUpComponent()",
                                                             ClassName.get(this.metaModel.getErrorPopUpController()
                                                                                         .getComponentInterface()
                                                                                         .getPackage(),
                                                                           this.metaModel.getErrorPopUpController()
                                                                                         .getComponentInterface()
                                                                                         .getSimpleName()));
      } else {
        createErrorPopUpControllerMethodBuilder.addStatement("$T component = new $T()",
                                                             ClassName.get(this.metaModel.getErrorPopUpController()
                                                                                         .getComponentInterface()
                                                                                         .getPackage(),
                                                                           this.metaModel.getErrorPopUpController()
                                                                                         .getComponentInterface()
                                                                                         .getSimpleName()),
                                                             ClassName.get(this.metaModel.getErrorPopUpController()
                                                                                         .getComponent()
                                                                                         .getPackage(),
                                                                           this.metaModel.getErrorPopUpController()
                                                                                         .getComponent()
                                                                                         .getSimpleName()));
      }
      createErrorPopUpControllerMethodBuilder.addStatement("component.setController(errorPopUpController)")
                                             .addStatement("errorPopUpController.setComponent(component)")
                                             .addStatement("component.render()")
                                             .addStatement("component.bind()")
                                             .addStatement("errorPopUpController.bind()")
                                             .addStatement("errorPopUpController.onLoad()");
    }
    typeSpec.addMethod(createErrorPopUpControllerMethodBuilder.build());
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
    
    public ErrorPopUpControllerGenerator build() {
      return new ErrorPopUpControllerGenerator(this);
    }
    
  }
  
}
