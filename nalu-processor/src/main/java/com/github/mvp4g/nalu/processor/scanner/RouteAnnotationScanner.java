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

package com.github.mvp4g.nalu.processor.scanner;

import com.github.mvp4g.nalu.client.component.AbstractComponentController;
import com.github.mvp4g.nalu.client.component.annotation.Controller;
import com.github.mvp4g.nalu.processor.ProcessorException;
import com.github.mvp4g.nalu.processor.ProcessorUtils;
import com.github.mvp4g.nalu.processor.model.ApplicationMetaModel;
import com.github.mvp4g.nalu.processor.model.intern.ClassNameModel;
import com.github.mvp4g.nalu.processor.model.intern.ControllerModel;
import com.github.mvp4g.nalu.processor.scanner.validation.RouteAnnotationValidator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.*;
import javax.lang.model.util.SimpleTypeVisitor6;
import java.util.List;

public class RouteAnnotationScanner {

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private ApplicationMetaModel applicationMetaModel;

  @SuppressWarnings("unused")
  private RouteAnnotationScanner(Builder builder) {
    super();
    this.processingEnvironment = builder.processingEnvironment;
    this.applicationMetaModel = builder.applicationMetaModel;
    setUp();
  }

  private void setUp() {
    this.processorUtils = ProcessorUtils.builder()
                                        .processingEnvironment(this.processingEnvironment)
                                        .build();
  }

  public static Builder builder() {
    return new Builder();
  }

  ApplicationMetaModel scan(RoundEnvironment roundEnvironment)
    throws ProcessorException {
    // handle ProvidesSelector-annotation
    for (Element element : roundEnvironment.getElementsAnnotatedWith(Controller.class)) {
      // do validation
      RouteAnnotationValidator.builder()
                              .roundEnvironment(roundEnvironment)
                              .processingEnvironment(processingEnvironment)
                              .controllerElement(element)
                              .build()
                              .validate();
      // get Annotation ...
      Controller annotation = element.getAnnotation(Controller.class);
      // handle ...
      TypeElement componentTypeElement = this.getComponentTypeElement(annotation);
      if (componentTypeElement == null) {
        throw new ProcessorException("Nalu-Processor: componentTypeElement is null");
      }
      TypeElement componentInterfaceTypeElement = this.getComponentInterfaceTypeElement(annotation);
      TypeMirror componentTypeTypeMirror = this.getComponentType(element.asType());
      // check and save the component type ...
      if (applicationMetaModel.getComponentType() == null) {
        applicationMetaModel.setComponentType(new ClassNameModel(componentTypeTypeMirror.toString()));
      } else {
        ClassNameModel compareValue = new ClassNameModel(componentTypeTypeMirror.toString());
        if (!applicationMetaModel.getComponentType()
                                 .equals(compareValue)) {
          throw new ProcessorException("Nalu-Processor: componentType >>" + compareValue + "<< is different. All controllers must implement the componentType!");
        }
      }
      // update route ...
      this.applicationMetaModel.getRoutes()
                               .add(new ControllerModel(annotation.route(),
                                                        annotation.selector(),
                                                        new ClassNameModel(element.toString()),
                                                        new ClassNameModel(componentInterfaceTypeElement.toString()),
                                                        new ClassNameModel(componentTypeElement.toString()),
                                                        new ClassNameModel(componentTypeTypeMirror.toString()),
                                                        new ClassNameModel(element.toString())));
    }
    return this.applicationMetaModel;
  }

  private TypeElement getComponentTypeElement(Controller annotation) {
    try {
      annotation.component();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  private TypeElement getComponentInterfaceTypeElement(Controller annotation) {
    try {
      annotation.componentInterface();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  public TypeMirror getComponentType(final TypeMirror typeMirror) {
    final TypeMirror[] result = {null};
    TypeMirror type = this.processorUtils.getFlattenedSupertype(this.processingEnvironment.getTypeUtils(),
                                                                typeMirror,
                                                                this.processorUtils.getElements()
                                                                                   .getTypeElement(AbstractComponentController.class.getCanonicalName())
                                                                                   .asType());
    if (type == null) {
      return result[0];
    }
    type.accept(new SimpleTypeVisitor6<Void, Void>() {
                  @Override
                  protected Void defaultAction(TypeMirror typeMirror,
                                               Void v) {
                    throw new UnsupportedOperationException();
                  }

                  @Override
                  public Void visitPrimitive(PrimitiveType primitiveType,
                                             Void v) {
                    return null;
                  }

                  @Override
                  public Void visitArray(ArrayType arrayType,
                                         Void v) {
                    return null;
                  }

                  @Override
                  public Void visitDeclared(DeclaredType declaredType,
                                            Void v) {
                    List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
                    if (!typeArguments.isEmpty()) {
                      if (typeArguments.size() == 3) {
                        result[0] = typeArguments.get(2);
                      }
                    }
                    return null;
                  }

                  @Override
                  public Void visitError(ErrorType errorType,
                                         Void v) {
                    return null;
                  }

                  @Override
                  public Void visitTypeVariable(TypeVariable typeVariable,
                                                Void v) {
                    return null;
                  }
                },
                null);
    return result[0];
  }

  public static class Builder {

    ProcessingEnvironment processingEnvironment;

    ApplicationMetaModel applicationMetaModel;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder applicationMetaModel(ApplicationMetaModel applicationMetaModel) {
      this.applicationMetaModel = applicationMetaModel;
      return this;
    }

    public RouteAnnotationScanner build() {
      return new RouteAnnotationScanner(this);
    }
  }
}
