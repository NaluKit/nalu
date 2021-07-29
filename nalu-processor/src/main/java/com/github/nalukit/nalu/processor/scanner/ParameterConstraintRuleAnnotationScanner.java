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

import com.github.nalukit.nalu.client.constrain.annotation.ParameterConstraintRule;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;
import com.github.nalukit.nalu.processor.model.intern.ParameterConstraintRuleModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

public class ParameterConstraintRuleAnnotationScanner {

  private final ProcessingEnvironment processingEnvironment;
  private final MetaModel             metaModel;
  private final Element               parameterConstraintRuleElement;
  private       ProcessorUtils        processorUtils;

  @SuppressWarnings("unused")
  private ParameterConstraintRuleAnnotationScanner(Builder builder) {
    super();
    this.processingEnvironment          = builder.processingEnvironment;
    this.metaModel                      = builder.metaModel;
    this.parameterConstraintRuleElement = builder.parameterConstraintRuleElement;
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

  public ParameterConstraintRuleModel scan(RoundEnvironment roundEnvironment)
      throws ProcessorException {
    // handle ParameterConstraintRule-annotation
    ParameterConstraintRuleModel model = this.handleParameterConstraintRule();
    //    // handle AcceptParameter annotation
    //    handleAcceptParameters(roundEnvironment,
    //                           controllerElement,
    //                           controllerModel);
    //    // TODO handle alle anderen Annotations
    return model;
  }

  private ParameterConstraintRuleModel handleParameterConstraintRule()
  //      throws ProcessorException
  {
    // get Annotation ...
    ParameterConstraintRule annotation = parameterConstraintRuleElement.getAnnotation(ParameterConstraintRule.class);
    // save model ...
    return new ParameterConstraintRuleModel(new ClassNameModel(parameterConstraintRuleElement.toString()));
  }

  //  private void handleAcceptParameters(RoundEnvironment roundEnvironment,
  //                                      Element element,
  //                                      ControllerModel controllerModel)
  //      throws ProcessorException {
  //    TypeElement typeElement = (TypeElement) element;
  //    List<Element> annotatedElements = this.processorUtils.getMethodFromTypeElementAnnotatedWith(this.processingEnvironment,
  //                                                                                                typeElement,
  //                                                                                                AcceptParameter.class);
  //    // validate
  //    AcceptParameterAnnotationValidator.builder()
  //                                      .roundEnvironment(roundEnvironment)
  //                                      .processingEnvironment(processingEnvironment)
  //                                      .controllerModel(controllerModel)
  //                                      .listOfAnnotatedElements(annotatedElements)
  //                                      .build()
  //                                      .validate();
  //    // add to ControllerModel ...
  //    for (Element annotatedElement : annotatedElements) {
  //      ExecutableElement executableElement = (ExecutableElement) annotatedElement;
  //      AcceptParameter   annotation        = executableElement.getAnnotation(AcceptParameter.class);
  //      controllerModel.getParameterAcceptors()
  //                     .add(new ParameterAcceptor(annotation.value(),
  //                                                executableElement.getSimpleName()
  //                                                                 .toString()));
  //    }
  //  }

  //  private TypeElement getComponentTypeElement(Controller annotation) {
  //    try {
  //      annotation.component();
  //    } catch (MirroredTypeException exception) {
  //      return (TypeElement) this.processingEnvironment.getTypeUtils()
  //                                                     .asElement(exception.getTypeMirror());
  //    }
  //    return null;
  //  }
  //
  //  private TypeElement getComponentInterfaceTypeElement(Controller annotation) {
  //    try {
  //      annotation.componentInterface();
  //    } catch (MirroredTypeException exception) {
  //      return (TypeElement) this.processingEnvironment.getTypeUtils()
  //                                                     .asElement(exception.getTypeMirror());
  //    }
  //    return null;
  //  }
  //
  //  private TypeMirror getComponentType(final TypeMirror typeMirror) {
  //    final TypeMirror[] result = { null };
  //    TypeMirror type = this.processorUtils.getFlattenedSupertype(this.processingEnvironment.getTypeUtils(),
  //                                                                typeMirror,
  //                                                                this.processorUtils.getElements()
  //                                                                                   .getTypeElement(AbstractComponentController.class.getCanonicalName())
  //                                                                                   .asType());
  //    if (type == null) {
  //      return result[0];
  //    }
  //    type.accept(new SimpleTypeVisitor8<Void, Void>() {
  //
  //                  @Override
  //                  protected Void defaultAction(TypeMirror typeMirror,
  //                                               Void v) {
  //                    throw new UnsupportedOperationException();
  //                  }
  //
  //                  @Override
  //                  public Void visitPrimitive(PrimitiveType primitiveType,
  //                                             Void v) {
  //                    return null;
  //                  }
  //
  //                  @Override
  //                  public Void visitArray(ArrayType arrayType,
  //                                         Void v) {
  //                    return null;
  //                  }
  //
  //                  @Override
  //                  public Void visitDeclared(DeclaredType declaredType,
  //                                            Void v) {
  //                    List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
  //                    if (!typeArguments.isEmpty()) {
  //                      if (typeArguments.size() == 3) {
  //                        result[0] = typeArguments.get(2);
  //                      }
  //                    }
  //                    return null;
  //                  }
  //
  //                  @Override
  //                  public Void visitError(ErrorType errorType,
  //                                         Void v) {
  //                    return null;
  //                  }
  //
  //                  @Override
  //                  public Void visitTypeVariable(TypeVariable typeVariable,
  //                                                Void v) {
  //                    return null;
  //                  }
  //                },
  //                null);
  //    return result[0];
  //  }
  //
  //  private boolean checkIsComponentCreator(Element element,
  //                                          TypeElement componentInterfaceTypeElement)
  //      throws ProcessorException {
  //    final TypeMirror[] result = { null };
  //    TypeMirror type = this.processorUtils.getFlattenedSupertype(this.processingEnvironment.getTypeUtils(),
  //                                                                element.asType(),
  //                                                                this.processorUtils.getElements()
  //                                                                                   .getTypeElement(IsComponentCreator.class.getCanonicalName())
  //                                                                                   .asType());
  //    // on case type is null, no IsComponentCreator interface found!
  //    if (type == null) {
  //      return false;
  //    }
  //    // check the generic!
  //    type.accept(new SimpleTypeVisitor8<Void, Void>() {
  //
  //                  @Override
  //                  protected Void defaultAction(TypeMirror typeMirror,
  //                                               Void v) {
  //                    throw new UnsupportedOperationException();
  //                  }
  //
  //                  @Override
  //                  public Void visitPrimitive(PrimitiveType primitiveType,
  //                                             Void v) {
  //                    return null;
  //                  }
  //
  //                  @Override
  //                  public Void visitArray(ArrayType arrayType,
  //                                         Void v) {
  //                    return null;
  //                  }
  //
  //                  @Override
  //                  public Void visitDeclared(DeclaredType declaredType,
  //                                            Void v) {
  //                    List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
  //                    if (!typeArguments.isEmpty()) {
  //                      if (typeArguments.size() == 1) {
  //                        result[0] = typeArguments.get(0);
  //                      }
  //                    }
  //                    return null;
  //                  }
  //
  //                  @Override
  //                  public Void visitError(ErrorType errorType,
  //                                         Void v) {
  //                    return null;
  //                  }
  //
  //                  @Override
  //                  public Void visitTypeVariable(TypeVariable typeVariable,
  //                                                Void v) {
  //                    return null;
  //                  }
  //                },
  //                null);
  //    // check generic!
  //    if (!componentInterfaceTypeElement.toString()
  //                                      .equals(result[0].toString())) {
  //      throw new ProcessorException("Nalu-Processor: controller >>" + element.toString() + "<< is declared as IsComponentCreator, but the used reference of the component interface does not match with the one inside the controller.");
  //    }
  //    return true;
  //  }
  //
  //  private String getContextType(Element element) {
  //    final TypeMirror[] result = { null };
  //    TypeMirror type = this.processorUtils.getFlattenedSupertype(this.processingEnvironment.getTypeUtils(),
  //                                                                element.asType(),
  //                                                                this.processorUtils.getElements()
  //                                                                                   .getTypeElement(AbstractController.class.getCanonicalName())
  //                                                                                   .asType());
  //    // on case type is null, no IsComponentCreator interface found!
  //    if (type == null) {
  //      return null;
  //    }
  //    // check the generic!
  //    type.accept(new SimpleTypeVisitor8<Void, Void>() {
  //
  //                  @Override
  //                  protected Void defaultAction(TypeMirror typeMirror,
  //                                               Void v) {
  //                    throw new UnsupportedOperationException();
  //                  }
  //
  //                  @Override
  //                  public Void visitPrimitive(PrimitiveType primitiveType,
  //                                             Void v) {
  //                    return null;
  //                  }
  //
  //                  @Override
  //                  public Void visitArray(ArrayType arrayType,
  //                                         Void v) {
  //                    return null;
  //                  }
  //
  //                  @Override
  //                  public Void visitDeclared(DeclaredType declaredType,
  //                                            Void v) {
  //                    List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
  //                    if (!typeArguments.isEmpty()) {
  //                      result[0] = typeArguments.get(0);
  //                    }
  //                    return null;
  //                  }
  //
  //                  @Override
  //                  public Void visitError(ErrorType errorType,
  //                                         Void v) {
  //                    return null;
  //                  }
  //
  //                  @Override
  //                  public Void visitTypeVariable(TypeVariable typeVariable,
  //                                                Void v) {
  //                    return null;
  //                  }
  //                },
  //                null);
  //    return result[0].toString();
  //  }
  //
  //  private List<String> getRoute(String[] routes) {
  //    List<String> convertedRoutes = new ArrayList<>();
  //    for (String tmpRoute : routes) {
  //      if (tmpRoute.startsWith("/")) {
  //        tmpRoute = tmpRoute.substring(1);
  //      }
  //      if (tmpRoute.length() == 0) {
  //        convertedRoutes.add("/");
  //      } else {
  //        StringBuilder sbRoute = new StringBuilder();
  //        Stream.of(tmpRoute.split("/"))
  //              .collect(Collectors.toList())
  //              .forEach(s -> {
  //                if (s.startsWith(":")) {
  //                  sbRoute.append("/")
  //                         .append("*");
  //                } else {
  //                  sbRoute.append("/")
  //                         .append(s);
  //                }
  //              });
  //        convertedRoutes.add(sbRoute.toString());
  //      }
  //    }
  //    return convertedRoutes;
  //  }
  //
  //  private List<String> getParametersFromRoute(String[] routes) {
  //    return Stream.of(routes[0].split("/"))
  //                 .collect(Collectors.toList())
  //                 .stream()
  //                 .filter(s -> s.startsWith(":"))
  //                 .map(p -> p.substring(1))
  //                 .collect(Collectors.toList());
  //  }



  public static class Builder {

    ProcessingEnvironment processingEnvironment;
    MetaModel             metaModel;
    Element               parameterConstraintRuleElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder metaModel(MetaModel metaModel) {
      this.metaModel = metaModel;
      return this;
    }

    public Builder parameterConstraintRuleElement(Element parameterConstraintRuleElement) {
      this.parameterConstraintRuleElement = parameterConstraintRuleElement;
      return this;
    }

    public ParameterConstraintRuleAnnotationScanner build() {
      return new ParameterConstraintRuleAnnotationScanner(this);
    }

  }

}
