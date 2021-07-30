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

import com.github.nalukit.nalu.client.component.AbstractCompositeController;
import com.github.nalukit.nalu.client.component.IsComponentCreator;
import com.github.nalukit.nalu.client.component.annotation.AcceptParameter;
import com.github.nalukit.nalu.client.component.annotation.CompositeController;
import com.github.nalukit.nalu.client.constraint.annotation.ParameterConstraint;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.*;
import com.github.nalukit.nalu.processor.scanner.validation.AcceptParameterAnnotationValidator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.*;
import javax.lang.model.util.SimpleTypeVisitor8;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CompositeControllerAnnotationScanner {

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private MetaModel metaModel;

  private Element compositeElement;

  @SuppressWarnings("unused")
  private CompositeControllerAnnotationScanner(Builder builder) {
    super();
    this.processingEnvironment = builder.processingEnvironment;
    this.metaModel             = builder.metaModel;
    this.compositeElement      = builder.compositeElement;
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

  public CompositeModel scan(RoundEnvironment roundEnvironment)
      throws ProcessorException {
    // handle CompositeController-annotation
    CompositeModel compositeModel = handleComposite(this.compositeElement);
    // handle AcceptParameter annotation
    handleAcceptParameters(roundEnvironment,
                           this.compositeElement,
                           compositeModel);
    // add model to configuration ...
    this.metaModel.getCompositeModels()
                  .add(compositeModel);
    return compositeModel;
  }

  private CompositeModel handleComposite(Element element)
      throws ProcessorException {
    // get Annotation ...
    CompositeController annotation = element.getAnnotation(CompositeController.class);
    // handle ...
    TypeElement componentTypeElement = this.getComponentTypeElement(annotation);
    if (componentTypeElement == null) {
      throw new ProcessorException("Nalu-Processor: componentTypeElement is null");
    }
    TypeElement componentInterfaceTypeElement = this.getComponentInterfaceTypeElement(annotation);
    TypeMirror  componentTypeTypeMirror       = this.getComponentType(element.asType());
    if (Objects.isNull(componentTypeTypeMirror)) {
      throw new ProcessorException("Nalu-Processor: componentTypeTypeMirror is null");
    }
    // check and save the component type ...
    if (metaModel.getComponentType() == null) {
      metaModel.setComponentType(new ClassNameModel(componentTypeTypeMirror.toString()));
    } else {
      ClassNameModel compareValue = new ClassNameModel(componentTypeTypeMirror.toString());
      if (!metaModel.getComponentType()
                    .equals(compareValue)) {
        throw new ProcessorException("Nalu-Processor: componentType >>" +
                                     compareValue.getClassName() +
                                     "<< is different. All composite controllers must implement the componentType!");
      }
    }
    // check, if the controller implements IsComponentController
    boolean componentController = this.checkIsComponentCreator(element,
                                                               componentInterfaceTypeElement);
    // get context!
    String context = this.getContextType(element);
    if (Objects.isNull(context)) {
      throw new ProcessorException("Nalu-Processor: composite controller >>" + element + "<< does not have a context generic!");
    }
    // create model ...
    if (Objects.isNull(componentInterfaceTypeElement)) {
      throw new ProcessorException("Nalu-Processor: componentInterfaceTypeElement is null!");
    }
    return new CompositeModel(new ClassNameModel(context),
                              new ClassNameModel(element.toString()),
                              new ClassNameModel(componentInterfaceTypeElement.toString()),
                              new ClassNameModel(componentTypeElement.toString()),
                              componentController);
  }

  private void handleAcceptParameters(RoundEnvironment roundEnvironment,
                                      Element element,
                                      CompositeModel compositeModel)
      throws ProcessorException {
    TypeElement typeElement = (TypeElement) element;
    List<Element> annotatedElements = this.processorUtils.getMethodFromTypeElementAnnotatedWith(this.processingEnvironment,
                                                                                                typeElement,
                                                                                                AcceptParameter.class);
    // get all controllers, that use the compositeModel (for validation)
    for (ControllerModel model : this.getControllerUsingComposite(element)) {
      // validate
      AcceptParameterAnnotationValidator.builder()
                                        .roundEnvironment(roundEnvironment)
                                        .processingEnvironment(processingEnvironment)
                                        .controllerModel(model)
                                        .listOfAnnotatedElements(annotatedElements)
                                        .build()
                                        .validate();
    }
    // add to ControllerModel ...
    for (Element annotatedElement : annotatedElements) {
      ExecutableElement        executableElement             = (ExecutableElement) annotatedElement;
      AcceptParameter          acceptParameterAnnotation     = executableElement.getAnnotation(AcceptParameter.class);
      ParameterConstraint      parameterConstraintAnnotation = executableElement.getAnnotation(ParameterConstraint.class);
      ParameterConstraintModel parameterConstraintModel      = null;
      if (parameterConstraintAnnotation != null) {
        TypeElement parameterConstraintTypeElement = getRuleTypeElement(parameterConstraintAnnotation);
        if (parameterConstraintTypeElement != null) {
          parameterConstraintModel = new ParameterConstraintModel(parameterConstraintTypeElement.toString(),
                                                                  parameterConstraintAnnotation.illegalParameterRoute());
        }
      }
      compositeModel.getParameterAcceptors()
                    .add(new ParameterAcceptorModel(acceptParameterAnnotation.value(),
                                                    executableElement.getSimpleName()
                                                                     .toString(),
                                                    parameterConstraintModel));

    }
  }

  private TypeElement getRuleTypeElement(ParameterConstraint annotation) {
    try {
      annotation.rule();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  private TypeElement getComponentTypeElement(CompositeController annotation) {
    try {
      annotation.component();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  private TypeElement getComponentInterfaceTypeElement(CompositeController annotation) {
    try {
      annotation.componentInterface();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  private TypeMirror getComponentType(final TypeMirror typeMirror) {
    final TypeMirror[] result = { null };
    TypeMirror type = this.processorUtils.getFlattenedSupertype(this.processingEnvironment.getTypeUtils(),
                                                                typeMirror,
                                                                this.processorUtils.getElements()
                                                                                   .getTypeElement(AbstractCompositeController.class.getCanonicalName())
                                                                                   .asType());
    if (type == null) {
      return null;
    }
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

  private boolean checkIsComponentCreator(Element element,
                                          TypeElement componentInterfaceTypeElement)
      throws ProcessorException {
    final TypeMirror[] result = { null };
    TypeMirror type = this.processorUtils.getFlattenedSupertype(this.processingEnvironment.getTypeUtils(),
                                                                element.asType(),
                                                                this.processorUtils.getElements()
                                                                                   .getTypeElement(IsComponentCreator.class.getCanonicalName())
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
      throw new ProcessorException("Nalu-Processor: compositeModel controller >>" +
                                   element +
                                   "<< is declared as IsComponentCreator, but the used reference of the component interface does not match with the one inside the controller.");
    }
    return true;
  }

  private String getContextType(Element element) {
    final TypeMirror[] result = { null };
    TypeMirror type = this.processorUtils.getFlattenedSupertype(this.processingEnvironment.getTypeUtils(),
                                                                element.asType(),
                                                                this.processorUtils.getElements()
                                                                                   .getTypeElement(AbstractCompositeController.class.getCanonicalName())
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

  private List<ControllerModel> getControllerUsingComposite(Element element) {
    List<ControllerModel> models = new ArrayList<>();
    this.metaModel.getControllers()
                  .forEach(controllerModel -> models.addAll(controllerModel.getComposites()
                                                                           .stream()
                                                                           .filter(controllerCompositeModel -> element.toString()
                                                                                                                      .equals(controllerCompositeModel.getComposite()
                                                                                                                                                      .getClassName()))
                                                                           .map(controllerCompositeModel -> controllerModel)
                                                                           .collect(Collectors.toList())));
    return models;
  }

  public static class Builder {

    ProcessingEnvironment processingEnvironment;

    MetaModel metaModel;

    Element compositeElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder metaModel(MetaModel metaModel) {
      this.metaModel = metaModel;
      return this;
    }

    public Builder compositeElement(Element compositeElement) {
      this.compositeElement = compositeElement;
      return this;
    }

    public CompositeControllerAnnotationScanner build() {
      return new CompositeControllerAnnotationScanner(this);
    }

  }

}
