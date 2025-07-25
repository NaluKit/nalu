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

package io.github.nalukit.nalu.processor.scanner;

import io.github.nalukit.nalu.client.component.AbstractComponentController;
import io.github.nalukit.nalu.client.component.AbstractController;
import io.github.nalukit.nalu.client.component.IsAbstractComponent;
import io.github.nalukit.nalu.client.component.IsComponent;
import io.github.nalukit.nalu.client.component.IsComponentCreator;
import io.github.nalukit.nalu.client.component.annotation.AcceptParameter;
import io.github.nalukit.nalu.client.component.annotation.Controller;
import io.github.nalukit.nalu.client.constraint.IsParameterConstraintRule;
import io.github.nalukit.nalu.client.constraint.annotation.ParameterConstraint;
import io.github.nalukit.nalu.processor.ProcessorException;
import io.github.nalukit.nalu.processor.ProcessorUtils;
import io.github.nalukit.nalu.processor.model.MetaModel;
import io.github.nalukit.nalu.processor.model.intern.ClassNameModel;
import io.github.nalukit.nalu.processor.model.intern.ControllerModel;
import io.github.nalukit.nalu.processor.model.intern.ParameterAcceptorModel;
import io.github.nalukit.nalu.processor.model.intern.ParameterConstraintModel;
import io.github.nalukit.nalu.processor.scanner.validation.AcceptParameterAnnotationValidator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.SimpleTypeVisitor8;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ControllerAnnotationScanner {

  private final ProcessingEnvironment processingEnvironment;
  private final MetaModel             metaModel;
  private final Element               controllerElement;
  private       ProcessorUtils        processorUtils;

  @SuppressWarnings("unused")
  private ControllerAnnotationScanner(Builder builder) {
    super();
    this.processingEnvironment = builder.processingEnvironment;
    this.metaModel             = builder.metaModel;
    this.controllerElement     = builder.controllerElement;
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

  public ControllerModel scan(RoundEnvironment roundEnvironment)
      throws ProcessorException {
    // handle ProvidesSelector-annotation
    ControllerModel controllerModel = this.handleController();
    // handle AcceptParameter annotation
    handleAcceptParameters(roundEnvironment,
                           controllerElement,
                           controllerModel);
    return controllerModel;
  }

  private ControllerModel handleController()
      throws ProcessorException {
    // get Annotation ...
    Controller annotation = controllerElement.getAnnotation(Controller.class);
    // handle ...
    TypeElement componentTypeElement = this.getComponentTypeElement(annotation);
    if (componentTypeElement == null) {
      throw new ProcessorException("Nalu-Processor: component is null");
    }
    TypeElement componentInterfaceTypeElement = this.getComponentInterfaceTypeElement(annotation);
    if (componentInterfaceTypeElement == null) {
      throw new ProcessorException("Nalu-Processor: @Controller - componentInterface is null");
    }
    TypeMirror componentTypeTypeMirror = this.getComponentType(controllerElement.asType());
    // check and save the component type ...
    if (metaModel.getComponentType() == null) {
      metaModel.setComponentType(new ClassNameModel(componentTypeTypeMirror.toString()));
    } else {
      ClassNameModel compareValue = new ClassNameModel(componentTypeTypeMirror.toString());
      if (!metaModel.getComponentType()
                    .equals(compareValue)) {
        throw new ProcessorException("Nalu-Processor: componentType of >>" +
                                     componentTypeElement +
                                     "<< is different (>>" +
                                     metaModel.getComponentType()
                                              .getClassName() +
                                     "<< versus >>" +
                                     compareValue.getClassName() +
                                     "<<). All controllers, controller interfaces and components must implement the same componentType!");
      }
    }
    // check, if the controller implements IsComponentController
    boolean componentController = this.checkIsComponentCreator(controllerElement,
                                                               componentInterfaceTypeElement);
    // get context!
    String context = this.getContextType(controllerElement);
    if (Objects.isNull(context)) {
      throw new ProcessorException("Nalu-Processor: controller >>" + controllerElement + "<< does not have a generic context!");
    }

    EventHandlerAnnotationScanner.EventMetaData eventMetaData = EventHandlerAnnotationScanner.builder()
                                                                                             .processingEnvironment(this.processingEnvironment)
                                                                                             .parentElement(this.controllerElement)
                                                                                             .build()
                                                                                             .scan();

    // save model ...
    return new ControllerModel(annotation.route(),
                               getRoute(annotation.route()),
                               annotation.selector(),
                               getParametersFromRoute(annotation.route()),
                               new ClassNameModel(context),
                               new ClassNameModel(controllerElement.toString()),
                               new ClassNameModel(componentInterfaceTypeElement.toString()),
                               new ClassNameModel(componentTypeElement.toString()),
                               new ClassNameModel(controllerElement.toString()),
                               componentController,
                               eventMetaData.getEventHandlerModels(),
                               eventMetaData.getEventModels());
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
      ExecutableElement        executableElement             = (ExecutableElement) annotatedElement;
      AcceptParameter          acceptParameterAnnotation     = executableElement.getAnnotation(AcceptParameter.class);
      ParameterConstraint      parameterConstraintAnnotation = executableElement.getAnnotation(ParameterConstraint.class);
      ParameterConstraintModel parameterConstraintModel      = null;
      if (parameterConstraintAnnotation != null) {
        TypeElement parameterConstraintTypeElement = getRuleTypeElement(parameterConstraintAnnotation);
        if (parameterConstraintTypeElement != null) {
          parameterConstraintModel = new ParameterConstraintModel(parameterConstraintTypeElement.toString(),
                                                                  parameterConstraintAnnotation.illegalParameterRoute());
          // register rule so that we know which rule needs to be loaded.
          this.metaModel.addParameterConstraintUsing(new ClassNameModel(parameterConstraintTypeElement.toString()));
        }
      }
      controllerModel.getParameterAcceptors()
                     .add(new ParameterAcceptorModel(acceptParameterAnnotation.value(),
                                                     executableElement.getSimpleName()
                                                                      .toString(),
                                                     parameterConstraintModel));
    }
  }

  private TypeElement getRuleTypeElement(ParameterConstraint annotation) {
    try {
      Class<? extends IsParameterConstraintRule> ignore = annotation.rule();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  private TypeElement getComponentTypeElement(Controller annotation) {
    try {
      Class<? extends IsAbstractComponent> ignore = annotation.component();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  private TypeElement getComponentInterfaceTypeElement(Controller annotation) {
    try {
      Class<? extends IsComponent<?, ?>> ignore = annotation.componentInterface();
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
      throw new ProcessorException("Nalu-Processor: controller >>" + element + "<< is declared as IsComponentCreator, but the used reference of the component interface does not match with the one inside the controller.");
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

  private List<String> getRoute(String[] routes) {
    List<String> convertedRoutes = new ArrayList<>();
    for (String tmpRoute : routes) {
      if (tmpRoute.startsWith("/")) {
        tmpRoute = tmpRoute.substring(1);
      }
      if (tmpRoute.isEmpty()) {
        convertedRoutes.add("/");
      } else {
        StringBuilder sbRoute = new StringBuilder();
        Stream.of(tmpRoute.split("/"))
              .collect(Collectors.toList())
              .forEach(s -> {
                if (s.startsWith(":") || (s.startsWith("{") && s.endsWith("}"))) {
                  sbRoute.append("/")
                         .append("*");
                } else {
                  sbRoute.append("/")
                         .append(s);
                }
              });
        convertedRoutes.add(sbRoute.toString());
      }
    }
    return convertedRoutes;
  }

  private List<String> getParametersFromRoute(String[] routes)
      throws ProcessorException {
    List<String> list   = new ArrayList<>(Arrays.asList(routes[0].split("/")));
    List<String> result = new ArrayList<>();
    for (String s : list) {
      if (s.startsWith(":")) {
        String substring = s.substring(1);
        result.add(substring);
      } else if (s.startsWith("{")) {
        String lastChar = s.substring(s.length() - 1);
        if (lastChar.equals("}")) {
          result.add(s.substring(1,
                                 s.length() - 1));
        } else {
          String sb = "Nalu-Processor: controller >>" +
                      this.controllerElement.getEnclosingElement()
                                            .toString() +
                      "<<  - parameter has illegal definition (missing '}' at the end of the parameter) >>" +
                      s +
                      "<<";
          throw new ProcessorException(sb);
        }
      }
    }
    return result;
  }

  public static class Builder {

    ProcessingEnvironment processingEnvironment;

    MetaModel metaModel;

    Element controllerElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder metaModel(MetaModel metaModel) {
      this.metaModel = metaModel;
      return this;
    }

    public Builder controllerElement(Element controllerElement) {
      this.controllerElement = controllerElement;
      return this;
    }

    public ControllerAnnotationScanner build() {
      return new ControllerAnnotationScanner(this);
    }

  }

}
