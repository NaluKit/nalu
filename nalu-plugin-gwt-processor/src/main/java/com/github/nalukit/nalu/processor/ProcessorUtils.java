/*
 * Copyright (c) 2018 - 2019 - Frank Hossfeld
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

package com.github.nalukit.nalu.processor;

import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProcessorUtils {

  private ProcessingEnvironment processingEnvironment;

  private Types types;

  private Messager messager;

  private Filer filer;

  private Elements elements;

  @SuppressWarnings("unused")
  private ProcessorUtils(Builder builder) {
    super();

    this.processingEnvironment = builder.processingEnvironment;

    this.types = this.processingEnvironment.getTypeUtils();
    this.messager = this.processingEnvironment.getMessager();
    this.filer = this.processingEnvironment.getFiler();
    this.elements = this.processingEnvironment.getElementUtils();
  }

  public static Builder builder() {
    return new Builder();
  }

  public boolean implementsInterface(ProcessingEnvironment processingEnvironment,
                                     TypeElement typeElement,
                                     TypeMirror implementedInterface) {
    return processingEnvironment.getTypeUtils()
                                .isAssignable(typeElement.asType(),
                                              implementedInterface);
  }

  public String getPackageAsString(Element type) {
    return this.getPackage(type)
               .getQualifiedName()
               .toString();
  }

  public PackageElement getPackage(Element type) {
    while (type.getKind() != ElementKind.PACKAGE) {
      type = type.getEnclosingElement();
    }
    return (PackageElement) type;
  }

  public Elements getElements() {
    return this.elements;
  }

  /**
   * checks if a class or interface is implemented.
   *
   * @param types       types
   * @param typeMirror  of the class to check
   * @param toImplement the type mirror to implement
   * @return true - class is implemented
   */
  public boolean extendsClassOrInterface(Types types,
                                         TypeMirror typeMirror,
                                         TypeMirror toImplement) {
    String clearedToImplement = this.removeGenericsFromClassName(toImplement.toString());
    Set<TypeMirror> setOfSuperType = this.getFlattenedSupertypeHierarchy(types,
                                                                         typeMirror);
    for (TypeMirror mirror : setOfSuperType) {
      if (clearedToImplement.equals(this.removeGenericsFromClassName(mirror.toString()))) {
        return true;
      }
    }
    return false;
  }

  private String removeGenericsFromClassName(String className) {
    if (className.contains("<")) {
      className = className.substring(0,
                                      className.indexOf("<"));
    }
    return className;
  }

  /**
   * Returns all of the superclasses and superinterfaces for a given generator
   * including the generator itself. The returned set maintains an internal
   * breadth-first ordering of the generator, followed by its interfaces (and their
   * super-interfaces), then the supertype and its interfaces, and so on.
   *
   * @param types      types
   * @param typeMirror of the class to check
   * @return Set of implemented super types
   */
  public Set<TypeMirror> getFlattenedSupertypeHierarchy(Types types,
                                                        TypeMirror typeMirror) {
    List<TypeMirror> toAdd = new ArrayList<>();
    LinkedHashSet<TypeMirror> result = new LinkedHashSet<>();
    toAdd.add(typeMirror);
    for (int i = 0; i < toAdd.size(); i++) {
      TypeMirror type = toAdd.get(i);
      if (result.add(type)) {
        toAdd.addAll(types.directSupertypes(type));
      }
    }
    return result;
  }

  public boolean supertypeHasGeneric(Types types,
                                     TypeMirror typeMirror,
                                     TypeMirror implementsMirror) {
    TypeMirror superTypeMirror = this.getFlattenedSupertype(types,
                                                            typeMirror,
                                                            implementsMirror);
    if (superTypeMirror == null) {
      return false;
    }
    return superTypeMirror.toString()
                          .contains("<");
  }

  public TypeMirror getFlattenedSupertype(Types types,
                                          TypeMirror typeMirror,
                                          TypeMirror implementsMirror) {
    String implementsMirrorWithoutGeneric = this.removeGenericsFromClassName(implementsMirror.toString());
    Set<TypeMirror> implementedSuperTypes = this.getFlattenedSupertypeHierarchy(types,
                                                                                typeMirror);
    for (TypeMirror typeMirrorSuperType : implementedSuperTypes) {
      String tn1WithoutGenric = this.removeGenericsFromClassName(typeMirrorSuperType.toString());
      if (implementsMirrorWithoutGeneric.equals(tn1WithoutGenric)) {
        return typeMirrorSuperType;
      }
    }
    return null;
  }

  //  public String createNameWithleadingUpperCase(String name) {
  //    return name.substring(0,
  //                          1)
  //               .toUpperCase() + name.substring(1);
  //  }

  public void createErrorMessage(String errorMessage) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    pw.println(errorMessage);
    pw.close();
    messager.printMessage(Diagnostic.Kind.ERROR,
                          sw.toString());

  }

  public String createFullClassName(String packageName,
                                    String className) {
    return packageName.replace(".",
                               "_") + "_" + className;
  }

  public void createNoteMessage(String noteMessage) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    pw.println(noteMessage);
    pw.close();
    messager.printMessage(Diagnostic.Kind.NOTE,
                          sw.toString());
  }

  public void createWarningMessage(String warningMessage) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    pw.println(warningMessage);
    pw.close();
    messager.printMessage(Diagnostic.Kind.WARNING,
                          sw.toString());
  }

  public <A extends Annotation> List<Element> getMethodFromTypeElementAnnotatedWith(ProcessingEnvironment processingEnvironment,
                                                                                    TypeElement element,
                                                                                    Class<A> annotation) {
    List<Element> annotatedMethods = processingEnvironment.getElementUtils()
                                                          .getAllMembers(element)
                                                          .stream()
                                                          .filter(methodElement -> methodElement.getAnnotation(annotation) != null)
                                                          .collect(Collectors.toList());
    return annotatedMethods;
  }

  public String createInternalEventName(ExecutableElement executableElement) {
    String internalEventName = executableElement.getSimpleName()
                                                .toString();
    for (VariableElement variableElement : executableElement.getParameters()) {
      internalEventName += ProcessorConstants.PARAMETER_DELIMITER;
      internalEventName += variableElement.asType()
                                          .toString()
                                          .replace(".",
                                                   "_");
    }
    return internalEventName;
  }

  public boolean doesExist(ClassNameModel typeElementClassName) {
    return this.processingEnvironment.getElementUtils()
                                     .getTypeElement(typeElementClassName.getClassName()) != null;
  }

  public String createHistoryMetaDataClassName(String historyConverterClassName) {
    return this.setFirstCharacterToUpperCase(this.createHistoryMetaDataVariableName(historyConverterClassName)) + "_" + ProcessorConstants.META_DATA;
  }

  public String setFirstCharacterToUpperCase(String className) {
    return className.substring(0,
                               1)
                    .toUpperCase() + className.substring(1);
  }

  public String createHistoryMetaDataVariableName(String historyConverterClassName) {
    return this.createFullClassName(historyConverterClassName);
  }

  public String createFullClassName(String className) {
    return className.replace(".",
                             "_");
  }

  public String createEventHandlingMethodName(String eventName) {
    return "on" +
        eventName.substring(0,
                            1)
                 .toUpperCase() +
        eventName.substring(1);
  }

  public String createEventNameFromHandlingMethod(String event) {
    return event.substring(2,
                           3)
                .toLowerCase() + event.substring(3);
  }

  public static class Builder {

    ProcessingEnvironment processingEnvironment;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public ProcessorUtils build() {
      return new ProcessorUtils(this);
    }

  }

}
