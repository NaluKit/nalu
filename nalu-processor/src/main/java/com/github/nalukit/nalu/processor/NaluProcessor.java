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

package com.github.nalukit.nalu.processor;

import com.github.nalukit.nalu.client.application.annotation.Application;
import com.github.nalukit.nalu.client.application.annotation.Debug;
import com.github.nalukit.nalu.client.application.annotation.Filters;
import com.github.nalukit.nalu.client.component.annotation.CompositeController;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import com.github.nalukit.nalu.client.component.annotation.Shell;
import com.github.nalukit.nalu.client.handler.annotation.Handler;
import com.github.nalukit.nalu.client.plugin.annotation.Plugin;
import com.github.nalukit.nalu.client.plugin.annotation.Plugins;
import com.github.nalukit.nalu.processor.generator.*;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.*;
import com.github.nalukit.nalu.processor.scanner.*;
import com.github.nalukit.nalu.processor.scanner.validation.*;
import com.google.auto.service.AutoService;
import com.google.gson.Gson;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.of;

@AutoService(Processor.class)
public class NaluProcessor
    extends AbstractProcessor {

  private final static String APPLICATION_PROPERTIES = "nalu.properties";

  private ProcessorUtils processorUtils;

  private ApplicationAnnotationScanner applicationAnnotationScanner;

  private MetaModel metaModel = new MetaModel();

  public NaluProcessor() {
    super();
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.RELEASE_8;
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return of(Application.class.getCanonicalName(),
              CompositeController.class.getCanonicalName(),
              Controller.class.getCanonicalName(),
              Debug.class.getCanonicalName(),
              Filters.class.getCanonicalName(),
              Handler.class.getCanonicalName(),
              Plugin.class.getCanonicalName(),
              Plugins.class.getCanonicalName(),
              Shell.class.getCanonicalName()).collect(toSet());
  }

  @Override
  public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    // set up processor
    setUp();
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations,
                         RoundEnvironment roundEnv) {
    //    processorUtils.createNoteMessage("Nalu-Processor triggered: " + System.currentTimeMillis());
    try {
      if (roundEnv.processingOver()) {
        if (!roundEnv.errorRaised()) {
          this.validate(roundEnv);
          this.generateLastRound();
          this.store(metaModel);
        }
      } else {
        if (annotations.size() > 0) {
          for (TypeElement annotation : annotations) {
            if (Application.class.getCanonicalName()
                                 .equals(annotation.toString())) {
              handleApplicationAnnotation(roundEnv);
            } else if (CompositeController.class.getCanonicalName()
                                                .equals(annotation.toString())) {
              handleCompositeControllerAnnotation(roundEnv);
            } else if (Controller.class.getCanonicalName()
                                       .equals(annotation.toString())) {
              handleControllerAnnotation(roundEnv);
            } else if (Debug.class.getCanonicalName()
                                  .equals(annotation.toString())) {
              handleDebugAnnotation(roundEnv);
            } else if (Filters.class.getCanonicalName()
                                    .equals(annotation.toString())) {
              handleFiltersAnnotation(roundEnv);
            } else if (Handler.class.getCanonicalName()
                                    .equals(annotation.toString())) {
              handleHandlerAnnotation(roundEnv);
            } else if (Plugin.class.getCanonicalName()
                                   .equals(annotation.toString())) {
              handlePluginAnnotation(roundEnv);
            } else if (Plugins.class.getCanonicalName()
                                    .equals(annotation.toString())) {
              handlePluginsAnnotation(roundEnv);
            } else if (Shell.class.getCanonicalName()
                                  .equals(annotation.toString())) {
              handleShellAnnotation(roundEnv);
            }
          }
        }
      }
      //      return true;
    } catch (ProcessorException e) {
      this.processorUtils.createErrorMessage(e.getMessage());
      //    }/
      return true;
    }
    return true;
  }

  private void handlePluginAnnotation(RoundEnvironment roundEnv)
      throws ProcessorException {
    for (Element pluginElement : roundEnv.getElementsAnnotatedWith(Plugin.class)) {
      // validate application element
      PluginAnnotationValidator.builder()
                               .processingEnvironment(processingEnv)
                               .pluginElement(pluginElement)
                               .build()
                               .validate();
      // scan application element
      PluginModel pluginModel = PluginAnnotationScanner.builder()
                                                       .processingEnvironment(processingEnv)
                                                       .pluginElement(pluginElement)
                                                       .build()
                                                       .scan(roundEnv);
      // store model
      this.metaModel.setPluginModel(pluginModel);
    }
  }

  private void handlePluginsAnnotation(RoundEnvironment roundEnv)
      throws ProcessorException {
    for (Element pluginsElement : roundEnv.getElementsAnnotatedWith(Plugins.class)) {
      // validate application element
      PluginsAnnotationValidator.builder()
                                .processingEnvironment(processingEnv)
                                .pluginsElement(pluginsElement)
                                .build()
                                .validate();
      // scan application element
      PluginsAnnotationScanner.builder()
                              .processingEnvironment(processingEnv)
                              .pluginsElement(pluginsElement)
                              .metaModel(metaModel)
                              .build()
                              .scan(roundEnv);
    }
  }

  private void handleApplicationAnnotation(RoundEnvironment roundEnv)
      throws ProcessorException {
    for (Element applicationElement : roundEnv.getElementsAnnotatedWith(Application.class)) {
      // validate application element
      ApplicationAnnotationValidator.builder()
                                    .processingEnvironment(processingEnv)
                                    .applicationElement(applicationElement)
                                    .build()
                                    .validate();
      // scan application element
      ApplicationAnnotationScanner.builder()
                                  .processingEnvironment(processingEnv)
                                  .applicationElement(applicationElement)
                                  .metaModel(metaModel)
                                  .build()
                                  .scan();
    }
  }

  private void handleShellAnnotation(RoundEnvironment roundEnv)
      throws ProcessorException {
    List<ShellModel> shellsModels = new ArrayList<>();
    for (Element shellElement : roundEnv.getElementsAnnotatedWith(Shell.class)) {
      // validate shellCreator!
      ShellAnnotationValidator.builder()
                              .processingEnvironment(processingEnv)
                              .roundEnvironment(roundEnv)
                              .build()
                              .validate(shellElement);
      // generate ShellCreator
      ShellModel shellModel = ShellAnnotationScanner.builder()
                                                    .processingEnvironment(processingEnv)
                                                    .metaModel(this.metaModel)
                                                    .shellElement(shellElement)
                                                    .build()
                                                    .scan(roundEnv);
      // generate ShellCreator
      ShellCreatorGenerator.builder()
                           .processingEnvironment(processingEnv)
                           .metaModel(this.metaModel)
                           .shellModel(shellModel)
                           .build()
                           .generate();
      shellsModels.add(shellModel);
    }
    // save handler data in metaModel
    this.metaModel.getShells()
                  .addAll(shellsModels);
  }

  private void handleCompositeControllerAnnotation(RoundEnvironment roundEnv)
      throws ProcessorException {
    for (Element compositeElement : roundEnv.getElementsAnnotatedWith(CompositeController.class)) {
      // validate handler element
      CompositeControllerAnnotationValidator.builder()
                                            .processingEnvironment(processingEnv)
                                            .roundEnvironment(roundEnv)
                                            .compositeElement(compositeElement)
                                            .build()
                                            .validate();
      // scan controller element
      CompositeModel compositeModel = CompositeControllerAnnotationScanner.builder()
                                                                          .processingEnvironment(processingEnv)
                                                                          .metaModel(this.metaModel)
                                                                          .compositeElement(compositeElement)
                                                                          .build()
                                                                          .scan(roundEnv);

      // create the ControllerCreator
      CompositeCreatorGenerator.builder()
                               .metaModel(this.metaModel)
                               .processingEnvironment(processingEnv)
                               .compositeModel(compositeModel)
                               .build()
                               .generate();
      // save controller data in metaModel
      this.metaModel.getCompositeModels()
                    .add(compositeModel);
    }
  }

  private void handleControllerAnnotation(RoundEnvironment roundEnv)
      throws ProcessorException {
    for (Element controllerElement : roundEnv.getElementsAnnotatedWith(Controller.class)) {
      // validate handler element
      ControllerAnnotationValidator.builder()
                                   .processingEnvironment(processingEnv)
                                   .roundEnvironment(roundEnv)
                                   .controllerElement(controllerElement)
                                   .build()
                                   .validate();
      // scan controller element
      ControllerModel controllerModel = ControllerAnnotationScanner.builder()
                                                                   .processingEnvironment(processingEnv)
                                                                   .metaModel(this.metaModel)
                                                                   .controllerElement(controllerElement)
                                                                   .build()
                                                                   .scan(roundEnv);

      // Composites-Annotation in controller
      controllerModel = CompositesAnnotationScanner.builder()
                                                   .processingEnvironment(processingEnv)
                                                   .controllerModel(controllerModel)
                                                   .controllerElement(controllerElement)
                                                   .build()
                                                   .scan(roundEnv);
      // create the ControllerCreator
      ControllerCreatorGenerator.builder()
                                .metaModel(this.metaModel)
                                .processingEnvironment(processingEnv)
                                .controllerModel(controllerModel)
                                .build()
                                .generate();
      //

      // save controller data in metaModel
      this.metaModel.getController()
                    .add(controllerModel);
    }
  }

  private void handleHandlerAnnotation(RoundEnvironment roundEnv)
      throws ProcessorException {
    for (Element handlerElement : roundEnv.getElementsAnnotatedWith(Handler.class)) {
      // validate handler element
      HandlerAnnotationValidator.builder()
                                .processingEnvironment(processingEnv)
                                .roundEnvironment(roundEnv)
                                .handlerElement(handlerElement)
                                .build()
                                .validate();
      // scan handler element
      ClassNameModel handlerModel = HandlerAnnotationScanner.builder()
                                                            .processingEnvironment(processingEnv)
                                                            .metaModel(this.metaModel)
                                                            .handlerElement(handlerElement)
                                                            .build()
                                                            .scan();
      // save handler data in metaModel
      this.metaModel.getHandlers()
                    .add(handlerModel);
    }
  }

  private void handleFiltersAnnotation(RoundEnvironment roundEnv)
      throws ProcessorException {
    for (Element filtersElement : roundEnv.getElementsAnnotatedWith(Filters.class)) {
      // validate filter element
      FiltersAnnotationValidator.builder()
                                .roundEnvironment(roundEnv)
                                .processingEnvironment(processingEnv)
                                .build()
                                .validate(filtersElement);
      // scan filter element
      List<ClassNameModel> filterModels = FiltersAnnotationScanner.builder()
                                                                  .processingEnvironment(processingEnv)
                                                                  .metaModel(this.metaModel)
                                                                  .filtersElement(filtersElement)
                                                                  .build()
                                                                  .scan(roundEnv);
      // save filter data in metaModel
      this.metaModel.getFilters()
                    .addAll(filterModels);

    }
  }

  private void handleDebugAnnotation(RoundEnvironment roundEnv)
      throws ProcessorException {
    for (Element debugElement : roundEnv.getElementsAnnotatedWith(Debug.class)) {
      // validate filter element
      DebugAnnotationValidator.builder()
                              .roundEnvironment(roundEnv)
                              .processingEnvironment(processingEnv)
                              .debugElement(debugElement)
                              .build()
                              .validate();
      // scan filter element and save data in metaModel
      this.metaModel = DebugAnnotationScanner.builder()
                                             .processingEnvironment(processingEnv)
                                             .metaModel(this.metaModel)
                                             .debugElement(debugElement)
                                             .build()
                                             .scan(roundEnv);

    }
  }

  private void setUp() {
    this.processorUtils = ProcessorUtils.builder()
                                        .processingEnvironment(processingEnv)
                                        .build();
    // get stored MEta Model and use it, if there is one!
    MetaModel restoredModel = this.restore();
    if (!Objects.isNull(restoredModel)) {
      this.metaModel = restoredModel;
    }
  }

  private void generateLastRound()
      throws ProcessorException {
    if (!isNull(this.metaModel)) {
      ApplicationGenerator.builder()
                          .processingEnvironment(this.processingEnv)
                          .build()
                          .generate(this.metaModel);
      // check if pluginModel is not null!
      // if pluginModel is null, we have nothing to do here,
      // otherwise we nead to generate a plugin-Impl class
      if (!Objects.isNull(metaModel.getPluginModel())) {
        PluginGenerator.builder()
                       .processingEnvironment(processingEnv)
                       .metaModel(this.metaModel)
                       .build()
                       .generate();
      }
    }
  }

  private void validate(RoundEnvironment roundEnv)
      throws ProcessorException {
    if (!isNull(this.metaModel)) {
      ConsistenceValidator.builder()
                          .roundEnvironment(roundEnv)
                          .processingEnvironment(this.processingEnv)
                          .metaModel(this.metaModel)
                          .build()
                          .validate();
    }
  }

  private MetaModel restore() {
    Gson gson = new Gson();
    try {
      FileObject resource = processingEnv.getFiler()
                                         .getResource(StandardLocation.CLASS_OUTPUT,
                                                      "",
                                                      this.createRelativeFileName());
      return gson.fromJson(resource.getCharContent(true)
                                   .toString(),
                           MetaModel.class);
    } catch (IOException e) {
      // every thing is ok -> no operation
      return null;
    }
  }

  private void store(MetaModel model)
      throws ProcessorException {
    Gson gson = new Gson();
    try {
      FileObject fileObject = processingEnv.getFiler()
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
    return ProcessorConstants.META_INF + "/" + ProcessorConstants.NALU_REACT_FOLDER_NAME + "/" + NaluProcessor.APPLICATION_PROPERTIES;
  }
}
