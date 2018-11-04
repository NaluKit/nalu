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

import com.github.nalukit.nalu.client.application.annotation.Application;
import com.github.nalukit.nalu.processor.ProcessorConstants;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;
import com.github.nalukit.nalu.processor.model.ApplicationMetaModel;
import com.github.nalukit.nalu.processor.scanner.validation.ApplicationAnnotationValidator;
import com.google.gson.Gson;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.isNull;

public class ApplicationAnnotationScanner {

  private final static String APPLICATION_PROPERTIES = "application.properties";

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private RoundEnvironment roundEnvironment;

  @SuppressWarnings("unused")
  private ApplicationAnnotationScanner(Builder builder) {
    super();
    this.processingEnvironment = builder.processingEnvironment;
    this.roundEnvironment = builder.roundEnvironment;
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

  public ApplicationMetaModel scan()
      throws ProcessorException {
    // First we try to read an already created resource ...
    ApplicationMetaModel model = this.restore();
    // Check if we have an element annotated with @Application
    if (!roundEnvironment.getElementsAnnotatedWith(Application.class)
                         .isEmpty()) {
      // check, whether we have o do something ...
      ApplicationAnnotationValidator validator = ApplicationAnnotationValidator.builder()
                                                                               .roundEnvironment(roundEnvironment)
                                                                               .processingEnvironment(this.processingEnvironment)
                                                                               .build();
      validator.validate();
      // should only be one, so we can search for the first! ...
      Optional<? extends Element> optionalElement = this.roundEnvironment.getElementsAnnotatedWith(Application.class)
                                                                         .stream()
                                                                         .findFirst();
      if (optionalElement.isPresent()) {
        Element applicationAnnotationElement = optionalElement.get();
        validator.validate(applicationAnnotationElement);
        Application applicationAnnotation = applicationAnnotationElement.getAnnotation(Application.class);
        if (!isNull(applicationAnnotation)) {
          TypeElement applicationLoaderTypeElement = this.getApplicationLoaderTypeElement(applicationAnnotation);
          //          TypeElement shellTypeElement = this.getShellTypeElement(applicationAnnotation);
          TypeElement contextTypeElement = this.getContextTypeElement(applicationAnnotation);
          model = new ApplicationMetaModel(this.processorUtils.getPackageAsString(applicationAnnotationElement),
                                           applicationAnnotationElement.toString(),
                                           isNull(applicationLoaderTypeElement) ? "" : applicationLoaderTypeElement.toString(),
                                           //                                           isNull(shellTypeElement) ? "" : shellTypeElement.toString(),
                                           Objects.requireNonNull(contextTypeElement)
                                                  .toString(),
                                           applicationAnnotation.startRoute(),
                                           applicationAnnotation.routeErrorRoute());
          // Shell-Annotation
          model = ShellsAnnotationScanner.builder()
                                         .processingEnvironment(processingEnvironment)
                                         .applicationTypeElement((TypeElement) applicationAnnotationElement)
                                         .applicationMetaModel(model)
                                         .build()
                                         .scan(roundEnvironment);
          // Debug-Annotation
          model = DebugAnnotationScanner.builder()
                                        .processingEnvironment(processingEnvironment)
                                        .applicationTypeElement((TypeElement) applicationAnnotationElement)
                                        .applicationMetaModel(model)
                                        .build()
                                        .scan(roundEnvironment);
          // Controller-Annotation
          model = ControllerAnnotationScanner.builder()
                                             .processingEnvironment(processingEnvironment)
                                             .applicationMetaModel(model)
                                             .build()
                                             .scan(roundEnvironment);
          // Filter-Annotation
          model = FiltersAnnotationScanner.builder()
                                          .processingEnvironment(processingEnvironment)
                                          .applicationTypeElement((TypeElement) applicationAnnotationElement)
                                          .applicationMetaModel(model)
                                          .build()
                                          .scan(roundEnvironment);
          // CompositeController-Annotation (must be executed after then ControllerAnnotationScanner!)
          model = CompositeControllerAnnotationScanner.builder()
                                                      .processingEnvironment(processingEnvironment)
                                                      .applicationMetaModel(model)
                                                      .build()
                                                      .scan(roundEnvironment);

          // let's store the updated model
          this.store(model);
        }
      }
    }
    return model;
  }

  private ApplicationMetaModel restore() {
    Gson gson = new Gson();
    try {
      FileObject resource = this.processingEnvironment.getFiler()
                                                      .getResource(StandardLocation.CLASS_OUTPUT,
                                                                   "",
                                                                   this.createRelativeFileName());
      return gson.fromJson(resource.getCharContent(true)
                                   .toString(),
                           ApplicationMetaModel.class);
    } catch (IOException e) {
      // every thing is ok -> no operation
    }
    return null;
  }

  private TypeElement getApplicationLoaderTypeElement(Application applicationAnnotation) {
    try {
      applicationAnnotation.loader();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  private TypeElement getContextTypeElement(Application applicationAnnotation) {
    try {
      applicationAnnotation.context();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  private void store(ApplicationMetaModel model)
      throws ProcessorException {
    Gson gson = new Gson();
    try {
      FileObject fileObject = processingEnvironment.getFiler()
                                                   .createResource(StandardLocation.CLASS_OUTPUT,
                                                                   "",
                                                                   this.createRelativeFileName());
      PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(fileObject.openOutputStream()));
      printWriter.print(gson.toJson(model));
      printWriter.flush();
      printWriter.close();
    } catch (IOException e) {
      throw new ProcessorException("NaluProcessor: Unable to write file: >>" + this.createRelativeFileName() + "<< -> exception: " + e.getMessage());
    }
  }

  private String createRelativeFileName() {
    return ProcessorConstants.META_INF + "/" + ProcessorConstants.NALU_REACT_FOLDER_NAME + "/" + ApplicationAnnotationScanner.APPLICATION_PROPERTIES;
  }

  public static class Builder {

    ProcessingEnvironment processingEnvironment;

    RoundEnvironment roundEnvironment;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }

    public ApplicationAnnotationScanner build() {
      return new ApplicationAnnotationScanner(this);
    }
  }
}
