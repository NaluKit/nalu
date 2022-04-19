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

package com.github.nalukit.nalu.processor.scanner;

import com.github.nalukit.nalu.client.component.AbstractController;
import com.github.nalukit.nalu.client.component.IsPopUpComponentCreator;
import com.github.nalukit.nalu.client.component.annotation.PopUpController;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;
import com.github.nalukit.nalu.processor.model.intern.PopUpControllerModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.SimpleTypeVisitor8;
import java.util.List;
import java.util.Objects;

public class PopUpControllerAnnotationScanner {

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private Element popUpControllerElement;

  @SuppressWarnings("unused")
  private PopUpControllerAnnotationScanner() {
  }

  //  @SuppressWarnings("unused")
  private PopUpControllerAnnotationScanner(Builder builder) {
    super();
    this.processingEnvironment  = builder.processingEnvironment;
    this.popUpControllerElement = builder.popUpControllerElement;
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

  public PopUpControllerModel scan(RoundEnvironment roundEnvironment)
      throws ProcessorException {
    return this.handlePopUpController();
  }

  private PopUpControllerModel handlePopUpController()
      throws ProcessorException {
    // get Annotation ...
    PopUpController annotation = this.popUpControllerElement.getAnnotation(PopUpController.class);
    // handle ...
    TypeElement componentTypeElement = this.getComponentTypeElement(annotation);
    if (componentTypeElement == null) {
      throw new ProcessorException("Nalu-Processor: @PopUpController - componentTypeElement is null");
    }
    TypeElement componentInterfaceTypeElement = this.getComponentInterfaceTypeElement(annotation);
    if (componentInterfaceTypeElement == null) {
      throw new ProcessorException("Nalu-Processor: @PopUpController - componentInterfaceTypeElement is null");
    }
    // check, if the controller implements IsComponentController
    boolean componentController = this.checkIsComponentCreator(this.popUpControllerElement,
                                                               componentInterfaceTypeElement);
    // get context!
    String context = this.getContextType(this.popUpControllerElement);
    if (Objects.isNull(context)) {
      throw new ProcessorException("Nalu-Processor: controller >>" +
                                   this.popUpControllerElement.toString() +
                                   "<< does not have a generic context!");
    }
    // always render component???
    boolean alwaysRenderComponent = annotation.alwaysRenderComponent();

    EventHandlerAnnotationScanner.EventMetaData eventMetaData = EventHandlerAnnotationScanner.builder()
                                                                                             .processingEnvironment(this.processingEnvironment)
                                                                                             .parentElement(this.popUpControllerElement)
                                                                                             .build()
                                                                                             .scan();

    // save model ...
    return new PopUpControllerModel(annotation.name(),
                                    new ClassNameModel(context),
                                    new ClassNameModel(popUpControllerElement.toString()),
                                    new ClassNameModel(componentInterfaceTypeElement.toString()),
                                    new ClassNameModel(componentTypeElement.toString()),
                                    new ClassNameModel(popUpControllerElement.toString()),
                                    new ClassNameModel(Objects.requireNonNull(getPopUpConditionElement(annotation))
                                                              .toString()),
                                    componentController,
                                    alwaysRenderComponent,
                                    eventMetaData.getEventHandlerModels(),
                                    eventMetaData.getEventModels());
  }

  private TypeElement getComponentTypeElement(PopUpController annotation) {
    try {
      annotation.component();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  private TypeElement getComponentInterfaceTypeElement(PopUpController annotation) {
    try {
      annotation.componentInterface();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  private TypeElement getPopUpConditionElement(PopUpController annotation) {
    try {
      annotation.condition();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  private boolean checkIsComponentCreator(Element element,
                                          TypeElement componentInterfaceTypeElement)
      throws ProcessorException {
    final TypeMirror[] result = { null };
    TypeMirror type = this.processorUtils.getFlattenedSupertype(this.processingEnvironment.getTypeUtils(),
                                                                element.asType(),
                                                                this.processorUtils.getElements()
                                                                                   .getTypeElement(IsPopUpComponentCreator.class.getCanonicalName())
                                                                                   .asType());
    // on case type is null, no IsComponentCreator interface found!
    if (type == null) {
      return false;
    }
    // check the generic!
    type.accept(new SimpleTypeVisitor8<Void, Void>() {

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
                      if (typeArguments.size() == 1) {
                        result[0] = typeArguments.get(0);
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
    // check generic!
    if (!componentInterfaceTypeElement.toString()
                                      .equals(result[0].toString())) {
      throw new ProcessorException("Nalu-Processor: controller >>" +
                                   element.toString() +
                                   "<< is declared as IsPopUpComponentCreator, but the used reference of the component interface does not match with the one inside the controller.");
    }
    return true;
  }

  private String getContextType(Element element) {
    final TypeMirror[] result = { null };
    TypeMirror type = this.processorUtils.getFlattenedSupertype(this.processingEnvironment.getTypeUtils(),
                                                                element.asType(),
                                                                this.processorUtils.getElements()
                                                                                   .getTypeElement(AbstractController.class.getCanonicalName())
                                                                                   .asType());
    // on case type is null, no IsComponentCreator interface found!
    if (type == null) {
      return null;
    }
    // check the generic!
    type.accept(new SimpleTypeVisitor8<Void, Void>() {

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
                      result[0] = typeArguments.get(0);
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
    return result[0].toString();
  }

  public static class Builder {

    ProcessingEnvironment processingEnvironment;

    MetaModel metaModel;

    Element popUpControllerElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder metaModel(MetaModel metaModel) {
      this.metaModel = metaModel;
      return this;
    }

    public Builder popUpControllerElement(Element popUpControllerElement) {
      this.popUpControllerElement = popUpControllerElement;
      return this;
    }

    public PopUpControllerAnnotationScanner build() {
      return new PopUpControllerAnnotationScanner(this);
    }

  }

}
