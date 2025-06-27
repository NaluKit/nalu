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

package io.github.nalukit.nalu.processor;

import io.github.nalukit.nalu.client.application.annotation.Application;
import io.github.nalukit.nalu.client.application.annotation.Filters;
import io.github.nalukit.nalu.client.application.annotation.Logger;
import io.github.nalukit.nalu.client.application.annotation.PopUpFilters;
import io.github.nalukit.nalu.client.application.annotation.Version;
import io.github.nalukit.nalu.client.component.annotation.BlockController;
import io.github.nalukit.nalu.client.component.annotation.CompositeController;
import io.github.nalukit.nalu.client.component.annotation.Composites;
import io.github.nalukit.nalu.client.component.annotation.Controller;
import io.github.nalukit.nalu.client.component.annotation.ErrorPopUpController;
import io.github.nalukit.nalu.client.component.annotation.PopUpController;
import io.github.nalukit.nalu.client.component.annotation.Shell;
import io.github.nalukit.nalu.client.constraint.annotation.ParameterConstraintRule;
import io.github.nalukit.nalu.client.event.annotation.EventHandler;
import io.github.nalukit.nalu.client.handler.annotation.Handler;
import io.github.nalukit.nalu.client.module.annotation.Module;
import io.github.nalukit.nalu.client.module.annotation.Modules;
import io.github.nalukit.nalu.client.tracker.annotation.Tracker;
import io.github.nalukit.nalu.processor.generator.ApplicationGenerator;
import io.github.nalukit.nalu.processor.generator.BlockControllerCreatorGenerator;
import io.github.nalukit.nalu.processor.generator.CompositeCreatorGenerator;
import io.github.nalukit.nalu.processor.generator.ControllerCreatorGenerator;
import io.github.nalukit.nalu.processor.generator.ModuleGenerator;
import io.github.nalukit.nalu.processor.generator.ParameterConstraintRuleImplGenerator;
import io.github.nalukit.nalu.processor.generator.PopUpControllerCreatorGenerator;
import io.github.nalukit.nalu.processor.generator.ShellCreatorGenerator;
import io.github.nalukit.nalu.processor.model.MetaModel;
import io.github.nalukit.nalu.processor.model.intern.BlockControllerModel;
import io.github.nalukit.nalu.processor.model.intern.ClassNameModel;
import io.github.nalukit.nalu.processor.model.intern.CompositeModel;
import io.github.nalukit.nalu.processor.model.intern.ControllerModel;
import io.github.nalukit.nalu.processor.model.intern.ErrorPopUpControllerModel;
import io.github.nalukit.nalu.processor.model.intern.FilterModel;
import io.github.nalukit.nalu.processor.model.intern.HandlerModel;
import io.github.nalukit.nalu.processor.model.intern.ModuleModel;
import io.github.nalukit.nalu.processor.model.intern.ParameterConstraintRuleModel;
import io.github.nalukit.nalu.processor.model.intern.PopUpControllerModel;
import io.github.nalukit.nalu.processor.model.intern.ShellModel;
import io.github.nalukit.nalu.processor.model.intern.TrackerModel;
import io.github.nalukit.nalu.processor.scanner.ApplicationAnnotationScanner;
import io.github.nalukit.nalu.processor.scanner.BlockControllerAnnotationScanner;
import io.github.nalukit.nalu.processor.scanner.CompositeControllerAnnotationScanner;
import io.github.nalukit.nalu.processor.scanner.ControllerAnnotationScanner;
import io.github.nalukit.nalu.processor.scanner.ControllerCompositesAnnotationScanner;
import io.github.nalukit.nalu.processor.scanner.ErrorPopUpControllerAnnotationScanner;
import io.github.nalukit.nalu.processor.scanner.FiltersAnnotationScanner;
import io.github.nalukit.nalu.processor.scanner.HandlerAnnotationScanner;
import io.github.nalukit.nalu.processor.scanner.LoggerAnnotationScanner;
import io.github.nalukit.nalu.processor.scanner.ModuleAnnotationScanner;
import io.github.nalukit.nalu.processor.scanner.ModulesAnnotationScanner;
import io.github.nalukit.nalu.processor.scanner.ParameterConstraintRuleAnnotationScanner;
import io.github.nalukit.nalu.processor.scanner.PopUpControllerAnnotationScanner;
import io.github.nalukit.nalu.processor.scanner.PopUpFiltersAnnotationScanner;
import io.github.nalukit.nalu.processor.scanner.ShellAnnotationScanner;
import io.github.nalukit.nalu.processor.scanner.ShellCompositesAnnotationScanner;
import io.github.nalukit.nalu.processor.scanner.TrackerAnnotationScanner;
import io.github.nalukit.nalu.processor.scanner.VersionAnnotationScanner;
import io.github.nalukit.nalu.processor.scanner.validation.ApplicationAnnotationValidator;
import io.github.nalukit.nalu.processor.scanner.validation.BlockControllerAnnotationValidator;
import io.github.nalukit.nalu.processor.scanner.validation.CompositeControllerAnnotationValidator;
import io.github.nalukit.nalu.processor.scanner.validation.CompositesAnnotationValidator;
import io.github.nalukit.nalu.processor.scanner.validation.ConsistenceValidator;
import io.github.nalukit.nalu.processor.scanner.validation.ControllerAnnotationValidator;
import io.github.nalukit.nalu.processor.scanner.validation.ErrorPopUpControllerAnnotationValidator;
import io.github.nalukit.nalu.processor.scanner.validation.FiltersAnnotationValidator;
import io.github.nalukit.nalu.processor.scanner.validation.HandlerAnnotationValidator;
import io.github.nalukit.nalu.processor.scanner.validation.LoggerAnnotationValidator;
import io.github.nalukit.nalu.processor.scanner.validation.ModuleAnnotationValidator;
import io.github.nalukit.nalu.processor.scanner.validation.ModulesAnnotationValidator;
import io.github.nalukit.nalu.processor.scanner.validation.ParameterConstraintRuleAnnotationValidator;
import io.github.nalukit.nalu.processor.scanner.validation.PopUpControllerAnnotationValidator;
import io.github.nalukit.nalu.processor.scanner.validation.PopUpFiltersAnnotationValidator;
import io.github.nalukit.nalu.processor.scanner.validation.ShellAnnotationValidator;
import io.github.nalukit.nalu.processor.scanner.validation.TrackerAnnotationValidator;
import io.github.nalukit.nalu.processor.scanner.validation.VersionAnnotationValidator;
import com.google.auto.service.AutoService;
import com.google.common.base.Stopwatch;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toSet;

@AutoService(Processor.class)
public class NaluProcessor
    extends AbstractProcessor {

  private final static String APPLICATION_PROPERTIES = "nalu.properties";

  private ProcessorUtils processorUtils;
  private Stopwatch      stopwatch;
  private MetaModel      metaModel = new MetaModel();

  public NaluProcessor() {
    super();
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return Stream.of(Application.class.getCanonicalName(),
                     BlockController.class.getCanonicalName(),
                     CompositeController.class.getCanonicalName(),
                     Composites.class.getCanonicalName(),
                     Controller.class.getCanonicalName(),
                     ErrorPopUpController.class.getCanonicalName(),
                     EventHandler.class.getCanonicalName(),
                     Filters.class.getCanonicalName(),
                     Logger.class.getCanonicalName(),
                     Handler.class.getCanonicalName(),
                     Module.class.getCanonicalName(),
                     Modules.class.getCanonicalName(),
                     ParameterConstraintRule.class.getCanonicalName(),
                     PopUpController.class.getCanonicalName(),
                     PopUpFilters.class.getCanonicalName(),
                     Shell.class.getCanonicalName(),
                     Tracker.class.getCanonicalName(),
                     Version.class.getCanonicalName())
                 .collect(toSet());
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  @Override
  public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    this.stopwatch = Stopwatch.createStarted();
    setUp();
    this.processorUtils.createNoteMessage("Nalu-Processor started ...");
    this.processorUtils.createNoteMessage("Nalu-Processor version >>" + ProcessorConstants.PROCESSOR_VERSION + "<<");
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations,
                         RoundEnvironment roundEnv) {
    try {
      if (roundEnv.processingOver()) {
        if (!roundEnv.errorRaised()) {
          this.validate(roundEnv);
          this.generateLastRound();
          this.store(metaModel);
        }
        this.processorUtils.createNoteMessage("Nalu-Processor finished ... processing takes: " +
                                              this.stopwatch.stop()
                                                            .toString());
      } else {
        if (!annotations.isEmpty()) {
          for (TypeElement annotation : annotations) {
            if (Application.class.getCanonicalName()
                                 .equals(annotation.toString())) {
              handleApplicationAnnotation(roundEnv);
            } else if (BlockController.class.getCanonicalName()
                                            .equals(annotation.toString())) {
              handleBlockControllerAnnotation(roundEnv);
            } else if (CompositeController.class.getCanonicalName()
                                                .equals(annotation.toString())) {
              handleCompositeControllerAnnotation(roundEnv);
            } else if (Composites.class.getCanonicalName()
                                       .equals(annotation.toString())) {
              handleCompositesAnnotation(roundEnv);
            } else if (Controller.class.getCanonicalName()
                                       .equals(annotation.toString())) {
              handleControllerAnnotation(roundEnv);
            } else if (ErrorPopUpController.class.getCanonicalName()
                                                 .equals(annotation.toString())) {
              handleErrorPopUpControllerAnnotation(roundEnv);
            } else if (Filters.class.getCanonicalName()
                                    .equals(annotation.toString())) {
              handleFiltersAnnotation(roundEnv);
            } else if (Handler.class.getCanonicalName()
                                    .equals(annotation.toString())) {
              handleHandlerAnnotation(roundEnv);
            } else if (Logger.class.getCanonicalName()
                                   .equals(annotation.toString())) {
              handleLoggerAnnotation(roundEnv);
            } else if (Module.class.getCanonicalName()
                                   .equals(annotation.toString())) {
              handleModuleAnnotation(roundEnv);
            } else if (Modules.class.getCanonicalName()
                                    .equals(annotation.toString())) {
              handleModulesAnnotation(roundEnv);
            } else if (ParameterConstraintRule.class.getCanonicalName()
                                                    .equals(annotation.toString())) {
              handleParameterConstraintRule(roundEnv);
            } else if (PopUpController.class.getCanonicalName()
                                            .equals(annotation.toString())) {
              handlePopUpControllerAnnotation(roundEnv);
            } else if (PopUpFilters.class.getCanonicalName()
                                         .equals(annotation.toString())) {
              handlePopUpFiltersAnnotation(roundEnv);
            } else if (Shell.class.getCanonicalName()
                                  .equals(annotation.toString())) {
              handleShellAnnotation(roundEnv);
            } else if (Tracker.class.getCanonicalName()
                                    .equals(annotation.toString())) {
              handleTrackerAnnotation(roundEnv);
            } else if (Version.class.getCanonicalName()
                                    .equals(annotation.toString())) {
              handleVersionAnnotation(roundEnv);
            }
          }
        }
      }
    } catch (ProcessorException e) {
      this.processorUtils.createErrorMessage(e.getMessage());
      return true;
    }
    return true;
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

  private void generateLastRound()
      throws ProcessorException {
    if (!isNull(this.metaModel)) {
      ApplicationGenerator.builder()
                          .processingEnvironment(this.processingEnv)
                          .build()
                          .generate(this.metaModel);
      // check if moduleModel is not null!
      // if moduleModel is null, we have nothing to do here,
      // otherwise we need to generate a module-Impl class
      if (!Objects.isNull(metaModel.getModuleModel())) {
        ModuleGenerator.builder()
                       .processingEnvironment(processingEnv)
                       .metaModel(this.metaModel)
                       .build()
                       .generate();
      }
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
      PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(fileObject.openOutputStream(),
                                                                       UTF_8));
      printWriter.print(gson.toJson(model));
      printWriter.flush();
      printWriter.close();
    } catch (IOException e) {
      throw new ProcessorException("NaluProcessor: Unable to write file: >>" +
                                   this.createRelativeFileName() +
                                   "<< -> exception: " +
                                   e.getMessage());
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

  private void handleBlockControllerAnnotation(RoundEnvironment roundEnv)
      throws ProcessorException {
    List<BlockControllerModel> blockControllerModels = new ArrayList<>();
    for (Element blockControllerElement : roundEnv.getElementsAnnotatedWith(BlockController.class)) {
      // validate
      BlockControllerAnnotationValidator.builder()
                                        .processingEnvironment(processingEnv)
                                        .blockControllerElement(blockControllerElement)
                                        .build()
                                        .validate();
      // create PopUpControllerModel
      BlockControllerModel blockControllerModel = BlockControllerAnnotationScanner.builder()
                                                                                  .processingEnvironment(processingEnv)
                                                                                  .metaModel(this.metaModel)
                                                                                  .blockControllerElement(blockControllerElement)
                                                                                  .build()
                                                                                  .scan(roundEnv);
      // generate BlockControllerCreator
      BlockControllerCreatorGenerator.builder()
                                     .processingEnvironment(processingEnv)
                                     .metaModel(this.metaModel)
                                     .blockControllerModel(blockControllerModel)
                                     .build()
                                     .generate();
      blockControllerModels.add(blockControllerModel);
    }
    // check, if the one of the popUpController in the list is already
    // added to the the meta model
    //
    // in case it is, remove it.
    blockControllerModels.forEach(model -> {
      Optional<BlockControllerModel> optional = this.metaModel.getBlockControllers()
                                                              .stream()
                                                              .filter(s -> model.getController()
                                                                                .getClassName()
                                                                                .equals(s.getController()
                                                                                         .getClassName()))
                                                              .findFirst();
      optional.ifPresent(optionalBlockControllerModel -> this.metaModel.getBlockControllers()
                                                                       .remove(optionalBlockControllerModel));
    });
    // save data in metaModel
    this.metaModel.getBlockControllers()
                  .addAll(blockControllerModels);
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
    }
  }

  private void handleCompositesAnnotation(RoundEnvironment roundEnv)
      throws ProcessorException {
    for (Element element : roundEnv.getElementsAnnotatedWith(Composites.class)) {
      // validate annodation
      CompositesAnnotationValidator.builder()
                                   .processingEnvironment(processingEnv)
                                   .roundEnvironment(roundEnv)
                                   .element(element)
                                   .build()
                                   .validate();
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
      controllerModel = ControllerCompositesAnnotationScanner.builder()
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
      // check, if the controller is already
      // added to the the meta model
      //
      // in case it is, remove it.
      final String controllerClassname = controllerModel.getController()
                                                        .getClassName();
      Optional<ControllerModel> optional = this.metaModel.getControllers()
                                                         .stream()
                                                         .filter(s -> controllerClassname.equals(s.getController()
                                                                                                  .getClassName()))
                                                         .findFirst();
      optional.ifPresent(optionalControllerModel -> this.metaModel.getControllers()
                                                                  .remove(optionalControllerModel));
      // save controller data in metaModel
      this.metaModel.getControllers()
                    .add(controllerModel);
    }
  }

  private void handleErrorPopUpControllerAnnotation(RoundEnvironment roundEnv)
      throws ProcessorException {
    Set<? extends Element> listOfAnnotatedElements = roundEnv.getElementsAnnotatedWith(ErrorPopUpController.class);
    if (listOfAnnotatedElements.size() > 1) {
      throw new ProcessorException("Nalu-Processor: more than one class is annotated with @ErrorPopUpController");
    }
    List<ErrorPopUpControllerModel> errorPopUpControllerModels = new ArrayList<>();
    for (Element errorPopUpControllerElement : roundEnv.getElementsAnnotatedWith(ErrorPopUpController.class)) {
      // validate
      ErrorPopUpControllerAnnotationValidator.builder()
                                             .processingEnvironment(processingEnv)
                                             .errorPopUpControllerElement(errorPopUpControllerElement)
                                             .build()
                                             .validate();
      // create PopUpControllerModel
      ErrorPopUpControllerModel errorPopUpControllerModel = ErrorPopUpControllerAnnotationScanner.builder()
                                                                                                 .processingEnvironment(processingEnv)
                                                                                                 .metaModel(this.metaModel)
                                                                                                 .popUpControllerElement(errorPopUpControllerElement)
                                                                                                 .build()
                                                                                                 .scan(roundEnv);
      errorPopUpControllerModels.add(errorPopUpControllerModel);
    }
    // save data in metaModel
    if (!errorPopUpControllerModels.isEmpty()) {
      this.metaModel.setErrorPopUpController(errorPopUpControllerModels.get(0));
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
      List<FilterModel> filterModels = FiltersAnnotationScanner.builder()
                                                               .processingEnvironment(processingEnv)
                                                               .metaModel(this.metaModel)
                                                               .filtersElement(filtersElement)
                                                               .build()
                                                               .scan(roundEnv);
      // check, if the one of the shell in the list is already
      // added to the the meta model
      // in case it is, remove it.
      filterModels.forEach(model -> {
        Optional<FilterModel> optional = this.metaModel.getFilters()
                                                       .stream()
                                                       .filter(s -> model.getFilter()
                                                                         .getClassName()
                                                                         .equals(s.getFilter()
                                                                                  .getClassName()))
                                                       .findFirst();
        optional.ifPresent(optionalFilter -> this.metaModel.getFilters()
                                                           .remove(optionalFilter));
      });
      // save filter data in metaModel
      this.metaModel.getFilters()
                    .addAll(filterModels);

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
      HandlerModel handlerModel = HandlerAnnotationScanner.builder()
                                                          .processingEnvironment(processingEnv)
                                                          .metaModel(this.metaModel)
                                                          .handlerElement(handlerElement)
                                                          .build()
                                                          .scan();
      // check, if the handler is already
      // added to the the meta model
      //
      // in case it is, remove it.
      final String handlerClassname = handlerModel.getHandler()
                                                  .getClassName();
      Optional<HandlerModel> optional = this.metaModel.getHandlers()
                                                      .stream()
                                                      .filter(s -> handlerClassname.equals(s.getHandler()
                                                                                            .getClassName()))
                                                      .findFirst();
      optional.ifPresent(optionalHandler -> this.metaModel.getHandlers()
                                                          .remove(optionalHandler));
      // save handler data in metaModel
      this.metaModel.getHandlers()
                    .add(handlerModel);
    }
  }

  private void handleLoggerAnnotation(RoundEnvironment roundEnv)
      throws ProcessorException {
    for (Element loggerElement : roundEnv.getElementsAnnotatedWith(Logger.class)) {
      LoggerAnnotationValidator.builder()
                               .roundEnvironment(roundEnv)
                               .processingEnvironment(processingEnv)
                               .loggerElement(loggerElement)
                               .build()
                               .validate();
      // scan filter element and save data in metaModel
      this.metaModel = LoggerAnnotationScanner.builder()
                                              .processingEnvironment(processingEnv)
                                              .metaModel(this.metaModel)
                                              .loggerElement(loggerElement)
                                              .build()
                                              .scan(roundEnv);

    }
  }

  private void handleModuleAnnotation(RoundEnvironment roundEnv)
      throws ProcessorException {
    for (Element moduleElement : roundEnv.getElementsAnnotatedWith(Module.class)) {
      // validate application element
      ModuleAnnotationValidator.builder()
                               .processingEnvironment(processingEnv)
                               .moduleElement(moduleElement)
                               .build()
                               .validate();
      // scan application element
      ModuleModel moduleModel = ModuleAnnotationScanner.builder()
                                                       .processingEnvironment(processingEnv)
                                                       .moduleElement(moduleElement)
                                                       .build()
                                                       .scan(roundEnv);
      // store model
      this.metaModel.setModuleModel(moduleModel);
    }
  }

  private void handleModulesAnnotation(RoundEnvironment roundEnv)
      throws ProcessorException {
    for (Element modulesElement : roundEnv.getElementsAnnotatedWith(Modules.class)) {
      // validate application element
      ModulesAnnotationValidator.builder()
                                .processingEnvironment(processingEnv)
                                .modulesElement(modulesElement)
                                .build()
                                .validate();
      // scan application element
      ModulesAnnotationScanner.builder()
                              .processingEnvironment(processingEnv)
                              .modulesElement(modulesElement)
                              .metaModel(metaModel)
                              .build()
                              .scan(roundEnv);
    }
  }

  private void handleParameterConstraintRule(RoundEnvironment roundEnv)
      throws ProcessorException {
    List<ParameterConstraintRuleModel> parameterConstraintRuleModelList = new ArrayList<>();
    for (Element parameterConstraintRuleElement : roundEnv.getElementsAnnotatedWith(ParameterConstraintRule.class)) {
      // validate
      ParameterConstraintRuleAnnotationValidator.builder()
                                                .processingEnvironment(processingEnv)
                                                .parameterConstraintRuleElement(parameterConstraintRuleElement)
                                                .build()
                                                .validate();
      // create ParameterConstraintRule-Model
      ParameterConstraintRuleModel model = ParameterConstraintRuleAnnotationScanner.builder()
                                                                                   .parameterConstraintRuleElement(parameterConstraintRuleElement)
                                                                                   .build()
                                                                                   .scan(roundEnv);
      parameterConstraintRuleModelList.add(model);

      // create the Impl-class
      ParameterConstraintRuleImplGenerator.builder()
                                          .metaModel(this.metaModel)
                                          .processingEnvironment(processingEnv)
                                          .parameterConstraintRuleModel(model)
                                          .build()
                                          .generate();
    }
    this.metaModel.setParameterConstraintRules(parameterConstraintRuleModelList);
  }

  private void handlePopUpControllerAnnotation(RoundEnvironment roundEnv)
      throws ProcessorException {
    List<PopUpControllerModel> popUpControllerModels = new ArrayList<>();
    for (Element popUpControllerElement : roundEnv.getElementsAnnotatedWith(PopUpController.class)) {
      // validate
      PopUpControllerAnnotationValidator.builder()
                                        .processingEnvironment(processingEnv)
                                        .popUpControllerElement(popUpControllerElement)
                                        .build()
                                        .validate();
      // create PopUpControllerModel
      PopUpControllerModel popUpControllerModel = PopUpControllerAnnotationScanner.builder()
                                                                                  .processingEnvironment(processingEnv)
                                                                                  .metaModel(this.metaModel)
                                                                                  .popUpControllerElement(popUpControllerElement)
                                                                                  .build()
                                                                                  .scan(roundEnv);
      // generate PopUpControllerCreator
      PopUpControllerCreatorGenerator.builder()
                                     .processingEnvironment(processingEnv)
                                     .metaModel(this.metaModel)
                                     .popUpControllerModel(popUpControllerModel)
                                     .build()
                                     .generate();
      popUpControllerModels.add(popUpControllerModel);
    }
    // check, if the one of the popUpController in the list is already
    // added to the the meta model
    //
    // in case it is, remove it.
    popUpControllerModels.forEach(model -> {
      Optional<PopUpControllerModel> optional = this.metaModel.getPopUpControllers()
                                                              .stream()
                                                              .filter(s -> model.getController()
                                                                                .getClassName()
                                                                                .equals(s.getController()
                                                                                         .getClassName()))
                                                              .findFirst();
      optional.ifPresent(optionalPopUpControllerModel -> this.metaModel.getPopUpControllers()
                                                                       .remove(optionalPopUpControllerModel));
    });
    // save data in metaModel
    this.metaModel.getPopUpControllers()
                  .addAll(popUpControllerModels);
  }

  private void handlePopUpFiltersAnnotation(RoundEnvironment roundEnv)
      throws ProcessorException {
    for (Element popUpFiltersElement : roundEnv.getElementsAnnotatedWith(PopUpFilters.class)) {
      // validate filter element
      PopUpFiltersAnnotationValidator.builder()
                                     .roundEnvironment(roundEnv)
                                     .processingEnvironment(processingEnv)
                                     .popUpFilterElement(popUpFiltersElement)
                                     .build()
                                     .validate(popUpFiltersElement);
      // scan filter element
      List<ClassNameModel> popUpFilterModels = PopUpFiltersAnnotationScanner.builder()
                                                                            .processingEnvironment(processingEnv)
                                                                            .metaModel(this.metaModel)
                                                                            .filtersElement(popUpFiltersElement)
                                                                            .build()
                                                                            .scan(roundEnv);
      // check, if the one of the shell in the list is already
      // added to the the meta model
      //
      // in case it is, remove it.
      popUpFilterModels.forEach(model -> {
        Optional<ClassNameModel> optional = this.metaModel.getPopUpFilters()
                                                          .stream()
                                                          .filter(s -> model.getClassName()
                                                                            .equals(s.getClassName()))
                                                          .findFirst();
        optional.ifPresent(optionalFilter -> this.metaModel.getPopUpFilters()
                                                           .remove(optionalFilter));
      });
      // save filter data in metaModel
      this.metaModel.getPopUpFilters()
                    .addAll(popUpFilterModels);

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

      // Composites-Annotation in controller
      shellModel = ShellCompositesAnnotationScanner.builder()
                                                   .processingEnvironment(processingEnv)
                                                   .shellModel(shellModel)
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
    // check, if the one of the shell in the list is already
    // added to the the meta model
    //
    // in case it is, remove it.
    shellsModels.forEach(model -> {
      Optional<ShellModel> optional = this.metaModel.getShells()
                                                    .stream()
                                                    .filter(s -> model.getShell()
                                                                      .getClassName()
                                                                      .equals(s.getShell()
                                                                               .getClassName()))
                                                    .findFirst();
      optional.ifPresent(optionalShellModel -> this.metaModel.getShells()
                                                             .remove(optionalShellModel));
    });
    // save shell data in metaModel
    this.metaModel.getShells()
                  .addAll(shellsModels);
  }

  private void handleTrackerAnnotation(RoundEnvironment roundEnv)
      throws ProcessorException {
    //TODO why to iterate over different Tackers?  It should be only one element?
    for (Element trackerElement : roundEnv.getElementsAnnotatedWith(Tracker.class)) {
      // validate filter element
      TrackerAnnotationValidator.builder()
                                .roundEnvironment(roundEnv)
                                .processingEnvironment(processingEnv)
                                .trackerElement(trackerElement)
                                .build()
                                .validate();

      // scan tracker element and save data in metaModel
      TrackerModel trackerModel = TrackerAnnotationScanner.builder()
                                                          .processingEnvironment(processingEnv)
                                                          .trackerElement(trackerElement)
                                                          .build()
                                                          .scan(roundEnv);

      this.metaModel.setTracker(trackerModel);
    }
  }

  private void handleVersionAnnotation(RoundEnvironment roundEnv)
      throws ProcessorException {
    for (Element trackerElement : roundEnv.getElementsAnnotatedWith(Version.class)) {
      // validate filter element
      VersionAnnotationValidator.builder()
                                .roundEnvironment(roundEnv)
                                .processingEnvironment(processingEnv)
                                .versionElement(trackerElement)
                                .build()
                                .validate();
      // scan version element and save data in metaModel
      this.metaModel = VersionAnnotationScanner.builder()
                                               .metaModel(this.metaModel)
                                               .versionElement(trackerElement)
                                               .build()
                                               .scan();

    }
  }

  private void setUp() {
    this.processorUtils = ProcessorUtils.builder()
                                        .processingEnvironment(processingEnv)
                                        .build();
    // get stored Meta Model and use it, if there is one!
    MetaModel restoredModel = this.restore();
    if (!Objects.isNull(restoredModel)) {
      this.metaModel = restoredModel;
    }
  }

  private MetaModel restore() {
    Gson gson = new Gson();
    try {
      FileObject resource = processingEnv.getFiler()
                                         .getResource(StandardLocation.CLASS_OUTPUT,
                                                      "",
                                                      this.createRelativeFileName());
      MetaModel model = gson.fromJson(resource.getCharContent(true)
                                                   .toString(),
                                           MetaModel.class);
      // clear working lists
      model.clearGeneratedConditionList();
      return model;
    } catch (IOException e) {
      // every thing is ok -> no operation
      return null;
    }
  }

  private String createRelativeFileName() {
    return ProcessorConstants.META_INF + "/" + ProcessorConstants.NALU_FOLDER_NAME + "/" + NaluProcessor.APPLICATION_PROPERTIES;
  }

}
