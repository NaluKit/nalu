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
package com.github.nalukit.nalu.processor.scanner.validation;

import com.github.nalukit.nalu.client.component.AbstractShell;
import com.github.nalukit.nalu.client.component.IsShell;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.ShellModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.SimpleTypeVisitor8;
import java.util.List;
import java.util.Optional;

public class ShellAnnotationValidator {

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private MetaModel metaModel;

  @SuppressWarnings("unused")
  private ShellAnnotationValidator() {
  }

  private ShellAnnotationValidator(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;
    this.metaModel             = builder.metaModel;

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

  public void validate(Element element)
      throws ProcessorException {

    if (element instanceof TypeElement) {
      TypeElement typeElement = (TypeElement) element;
      // annotated element has to be a class
      if (!typeElement.getKind()
                      .isClass()) {
        throw new ProcessorException("Nalu-Processor: @Shell annotation must be used with a class");
      }
      // check, that the typeElement implements AbstractShell
      if (!this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
                                                       typeElement.asType(),
                                                       this.processingEnvironment.getElementUtils()
                                                                                 .getTypeElement(IsShell.class.getCanonicalName())
                                                                                 .asType())) {
        throw new ProcessorException("Nalu-Processor: " +
                                     typeElement.getSimpleName()
                                                .toString() +
                                     ": @Shells must extend IsShell interface");
      }
    } else {
      throw new ProcessorException("Nalu-Processor:" + "@Shells can only be used on a type (class)");
    }
    // check context!
    this.getContextType(element);
  }

  private String getContextType(Element element)
      throws ProcessorException {
    final TypeMirror[] result = { null };
    TypeMirror type = this.processorUtils.getFlattenedSupertype(this.processingEnvironment.getTypeUtils(),
                                                                element.asType(),
                                                                this.processorUtils.getElements()
                                                                                   .getTypeElement(AbstractShell.class.getCanonicalName())
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
                      if (typeArguments.size() > 0) {
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
    try {
      return result[0].toString();
    } catch (NullPointerException e) {
      throw new ProcessorException("Nalu-Processor: shellCreator >>com.github.nalukit.nalu.processor.shellCreator.shellDoesNotHaveGenericContext.ShellDoesNotHaveGenericContext<< does not have a context generic!");
    }
  }

  public void validateName(String name)
      throws ProcessorException {
    Optional<ShellModel> optionalShellModel = this.metaModel.getShells()
                                                            .stream()
                                                            .filter(m -> name.equals(m.getName()))
                                                            .findAny();
    if (optionalShellModel.isPresent()) {
      throw new ProcessorException("Nalu-Processor:" + "@Shell: the shell name >>" + name + "<< is already used!");
    }
  }

  public static final class Builder {

    ProcessingEnvironment processingEnvironment;

    RoundEnvironment roundEnvironment;

    MetaModel metaModel;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }

    public Builder metaModel(MetaModel metaModel) {
      this.metaModel = metaModel;
      return this;
    }

    public ShellAnnotationValidator build() {
      return new ShellAnnotationValidator(this);
    }

  }

}
