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

import io.github.nalukit.nalu.client.module.IsModule;
import io.github.nalukit.nalu.client.module.annotation.Module;
import io.github.nalukit.nalu.processor.ProcessorException;
import io.github.nalukit.nalu.processor.ProcessorUtils;
import io.github.nalukit.nalu.processor.model.intern.ClassNameModel;
import io.github.nalukit.nalu.processor.model.intern.ModuleModel;

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

import static java.util.Objects.isNull;

public class ModuleAnnotationScanner {

  private ProcessorUtils processorUtils;

  private final ProcessingEnvironment processingEnvironment;

  private final Element moduleElement;

  @SuppressWarnings("unused")
  private ModuleAnnotationScanner(Builder builder) {
    super();
    this.processingEnvironment = builder.processingEnvironment;
    this.moduleElement         = builder.moduleElement;
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

  public ModuleModel scan(RoundEnvironment roundEnvironment)
      throws ProcessorException {
    Module moduleAnnotation = this.moduleElement.getAnnotation(Module.class);
    // get context!
    String context = this.getContextType(moduleElement);
    if (Objects.isNull(context)) {
      throw new ProcessorException("Nalu-Processor: module >>" +
                                   moduleElement.toString() +
                                   "<< does not have a generic context!");
    }
    TypeElement moduleLoaderTypeElement = this.getLoaderType(moduleAnnotation);
    return new ModuleModel(moduleAnnotation.name(),
                           new ClassNameModel(moduleElement.toString()),
                           new ClassNameModel(context),
                           new ClassNameModel(isNull(moduleLoaderTypeElement) ? "" : moduleLoaderTypeElement.toString()));
  }

  private String getContextType(Element element) {
    final TypeMirror[] result = { null };
    TypeMirror type = this.processorUtils.getFlattenedSupertype(this.processingEnvironment.getTypeUtils(),
                                                                element.asType(),
                                                                this.processorUtils.getElements()
                                                                                   .getTypeElement(IsModule.class.getCanonicalName())
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

  private TypeElement getLoaderType(Module moduleAnnotation) {
    try {
      moduleAnnotation.loader();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  public static class Builder {

    ProcessingEnvironment processingEnvironment;

    Element moduleElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder moduleElement(Element moduleElement) {
      this.moduleElement = moduleElement;
      return this;
    }

    public ModuleAnnotationScanner build() {
      return new ModuleAnnotationScanner(this);
    }

  }

}
