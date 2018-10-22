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

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.IsComponentCreator;
import com.github.nalukit.nalu.client.component.annotation.AcceptParameter;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;
import com.github.nalukit.nalu.processor.model.ApplicationMetaModel;
import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;
import com.github.nalukit.nalu.processor.model.intern.ControllerModel;
import com.github.nalukit.nalu.processor.model.intern.ParameterAcceptor;
import com.github.nalukit.nalu.processor.scanner.validation.AcceptParameterAnnotationValidator;
import com.github.nalukit.nalu.processor.scanner.validation.ControllerAnnotationValidator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.*;
import javax.lang.model.util.SimpleTypeVisitor6;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ControllerAnnotationScanner {

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private ApplicationMetaModel applicationMetaModel;

  @SuppressWarnings("unused")
  private ControllerAnnotationScanner(Builder builder) {
    super();
    this.processingEnvironment = builder.processingEnvironment;
    this.applicationMetaModel = builder.applicationMetaModel;
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

  ApplicationMetaModel scan(RoundEnvironment roundEnvironment)
      throws ProcessorException {
    // handle ProvidesSelector-annotation
    for (Element element : roundEnvironment.getElementsAnnotatedWith(Controller.class)) {
      ControllerModel controllerModel = this.handleController(roundEnvironment,
                                                              element);
      // handle AcceptParameter annotation
      handleAcceptParameters(roundEnvironment,
                             element,
                             controllerModel);
      // Composites-Annotation
      controllerModel = CompositesAnnotationScanner.builder()
                                                   .processingEnvironment(processingEnvironment)
                                                   .controllerModel(controllerModel)
                                                   .controllerTypeElement((TypeElement) element)
                                                   .build()
                                                   .scan(roundEnvironment);

      // add model to routes ...
      this.applicationMetaModel.getController()
                               .add(controllerModel);
    }
    // TODO validiere alle controller und Router!
    return this.applicationMetaModel;
  }

  private ControllerModel handleController(RoundEnvironment roundEnvironment,
                                           Element element)
      throws ProcessorException {
    // do validation
    ControllerAnnotationValidator.builder()
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
    // check, if the controller implements IsComponentController
    boolean componentController = this.checkIsComponentCreator(element,
                                                               componentInterfaceTypeElement);
    // save model ...
    return new ControllerModel(annotation.route(),
                               getRoute(annotation.route()),
                               annotation.selector(),
                               getParametersFromRoute(annotation.route()),
                               new ClassNameModel(element.toString()),
                               new ClassNameModel(componentInterfaceTypeElement.toString()),
                               new ClassNameModel(componentTypeElement.toString()),
                               new ClassNameModel(componentTypeTypeMirror.toString()),
                               new ClassNameModel(element.toString()),
                               componentController);
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
                                       "<< is declared as IsComponentCreator, but the used reference of the component interface does not match with the one inside the controller.");
    }
    return true;
  }

  private void handleAcceptParameters(RoundEnvironment roundEnvironment,
                                      Element element,
                                      ControllerModel controllerModel)
      throws ProcessorException {
    TypeElement typeElement = (TypeElement) element;
    List<Element> annotatedElements = this.processorUtils.getMethodFromTypeElementAnnotatedWith(this.processingEnvironment,
                                                                                                typeElement,
                                                                                                AcceptParameter.class);
    // validate
    AcceptParameterAnnotationValidator.builder()
                                      .roundEnvironment(roundEnvironment)
                                      .processingEnvironment(processingEnvironment)
                                      .controllerModel(controllerModel)
                                      .listOfAnnotatedElements(annotatedElements)
                                      .build()
                                      .validate();
    // add to ControllerModel ...
    for (Element annotatedElement : annotatedElements) {
      ExecutableElement executableElement = (ExecutableElement) annotatedElement;
      AcceptParameter annotation = executableElement.getAnnotation(AcceptParameter.class);
      controllerModel.getParameterAcceptors()
                     .add(new ParameterAcceptor(annotation.value(),
                                                executableElement.getSimpleName()
                                                                 .toString()));
    }
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

  private TypeMirror getComponentType(final TypeMirror typeMirror) {
    final TypeMirror[] result = { null };
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

  private String getRoute(String route) {
    String tmpRoute = route;
    if (tmpRoute.startsWith("/")) {
      tmpRoute = tmpRoute.substring(1);
    }
    if (tmpRoute.length() == 0) {
      return "/";
    }
    StringBuilder sbRoute = new StringBuilder();
    Stream.of(tmpRoute.split("/"))
          .collect(Collectors.toList())
          .stream()
          .filter(s -> !s.startsWith(":"))
          .forEach(s -> sbRoute.append("/")
                               .append(s));
    return sbRoute.toString();
  }

  private List<String> getParametersFromRoute(String route) {
    return Stream.of(route.split("/"))
                 .collect(Collectors.toList())
                 .stream()
                 .filter(s -> s.startsWith(":"))
                 .map(p -> p.substring(1))
                 .collect(Collectors.toList());
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

    public ControllerAnnotationScanner build() {
      return new ControllerAnnotationScanner(this);
    }
  }
}
