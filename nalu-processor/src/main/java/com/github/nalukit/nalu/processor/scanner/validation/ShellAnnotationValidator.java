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

import com.github.nalukit.nalu.client.application.IsApplication;
import com.github.nalukit.nalu.client.application.annotation.Shell;
import com.github.nalukit.nalu.client.application.annotation.Shells;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;
import com.github.nalukit.nalu.processor.model.ApplicationMetaModel;
import com.github.nalukit.nalu.processor.model.intern.ShellModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.*;
import java.util.stream.Collectors;

public class ShellAnnotationValidator {

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private RoundEnvironment roundEnvironment;

  private ApplicationMetaModel applicationMetaModel;

  private TypeElement applicationTypeElement;

  @SuppressWarnings("unused")
  private ShellAnnotationValidator() {
  }

  private ShellAnnotationValidator(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;
    this.roundEnvironment = builder.roundEnvironment;
    this.applicationTypeElement = builder.applicationTypeElement;
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

  public void validate()
      throws ProcessorException {
    // get elements annotated with Application annotation
    Set<? extends Element> elementsWithShellsAnnotation = this.roundEnvironment.getElementsAnnotatedWith(Shells.class);
    // at least there should exatly one Application annotation!
    if (elementsWithShellsAnnotation.size() == 0) {
      throw new ProcessorException("Nalu-Processor: @Shells is missing for IsApplication interface");
    }
    // at least there should only one Application annotation!
    if (elementsWithShellsAnnotation.size() > 1) {
      throw new ProcessorException("Nalu-Processor: There should be at least only one interface, that is annotated with @Shells");
    }
  }

  public void validate(Element element)
      throws ProcessorException {
    if (element instanceof TypeElement) {
      TypeElement typeElement = (TypeElement) element;
      // annotated element has to be a interface
      if (!typeElement.getKind()
                      .isInterface()) {
        throw new ProcessorException("Nalu-Processor: @Shells annotated must be used with an interface");
      }
      // check, that the typeElement implements IsApplication
      if (!this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
                                                       typeElement.asType(),
                                                       this.processingEnvironment.getElementUtils()
                                                                                 .getTypeElement(IsApplication.class.getCanonicalName())
                                                                                 .asType())) {
        throw new ProcessorException("Nalu-Processor: " +
                                     typeElement.getSimpleName()
                                                .toString() +
                                     ": @Shells must implement IsApplication interface");
      }
    } else {
      throw new ProcessorException("Nalu-Processor:" + "@Shells can only be used on a type (interface)");
    }
    // check the name of the shells for duplicates
    Shells shellsAnnotation = element.getAnnotation(Shells.class);
    if (!Objects.isNull(shellsAnnotation)) {
      List<String>compareList = new ArrayList<>();
      for (int i = 0; i < shellsAnnotation.value().length; i++) {
        Shell shell = shellsAnnotation.value()[i];
        if (compareList.contains(shell.name())) {
          throw new ProcessorException("Nalu-Processor:" + "@Shell: the name >>" + shell.name() + "<< is dunplicate! Please use another unique name!");
        }
        compareList.add(shell.name());
      }
    }
  }

  public void validateName(String name)
      throws ProcessorException {
    Optional<ShellModel> optionalShellModel = this.applicationMetaModel.getShells()
                                                                       .stream()
                                                                       .filter(m -> name.equals(m.getName()))
                                                                       .findAny();
    if (optionalShellModel.isPresent()) {
      throw new ProcessorException("Nalu-Processor:" + "@Shell: the shell ame >>" + name + "<< is already used!");
    }
  }

  public static final class Builder {

    ProcessingEnvironment processingEnvironment;

    RoundEnvironment roundEnvironment;

    ApplicationMetaModel applicationMetaModel;

    TypeElement applicationTypeElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }

    public Builder applicationMetaModel(ApplicationMetaModel applicationMetaModel) {
      this.applicationMetaModel = applicationMetaModel;
      return this;
    }

    public Builder applicationTypeElement(TypeElement applicationTypeElement) {
      this.applicationTypeElement = applicationTypeElement;
      return this;
    }

    public ShellAnnotationValidator build() {
      return new ShellAnnotationValidator(this);
    }
  }
}
