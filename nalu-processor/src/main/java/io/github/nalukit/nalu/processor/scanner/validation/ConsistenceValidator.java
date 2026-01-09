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
package io.github.nalukit.nalu.processor.scanner.validation;

import io.github.nalukit.nalu.client.util.NaluUtils;
import io.github.nalukit.nalu.processor.ProcessorException;
import io.github.nalukit.nalu.processor.model.MetaModel;
import io.github.nalukit.nalu.processor.model.intern.BlockControllerModel;
import io.github.nalukit.nalu.processor.model.intern.ControllerModel;
import io.github.nalukit.nalu.processor.model.intern.ShellAndControllerCompositeModel;
import io.github.nalukit.nalu.processor.model.intern.ShellModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.tools.Diagnostic;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ConsistenceValidator {

  private ProcessingEnvironment processingEnvironment;

  private MetaModel metaModel;

  @SuppressWarnings("unused")
  private ConsistenceValidator() {
  }

  private ConsistenceValidator(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;
    this.metaModel             = builder.metaModel;

    setUp();
  }

  public static Builder builder() {
    return new Builder();
  }

  private void setUp() {
  }

  public void validate()
      throws ProcessorException {
    // check startroute parameter
    this.validateStartRoute();
    // check, if there is at least one shell
    this.validateNoShellsDefined();
    // check, is there are duplicate shell names
    this.validateDuplicateShellName();
    // check, is there are duplicate block controller names
    this.validateDuplicateBlockControllerName();
    // check, is there are duplicate names for composites inside one shell
    this.validateDuplicateCompositeNamesInAShell();
    // check, is there are duplicate names for composites inside one controller
    this.validateDuplicateCompositeNamesInAController();
    // check, is there are duplicate block controller names
    this.validateDuplicateBlockControllerName();
    // check, that there is no @ErrorPopUpController used in a sub module
    this.vallidateErrorPopUpControllerInSubModule();
  }

  private void validateStartRoute()
      throws ProcessorException {
    if (!Objects.isNull(metaModel.getApplication())) {
      // Does the shell of the start route exist?
      Optional<String> optionalShell = this.metaModel.getShells()
                                                     .stream()
                                                     .map(ShellModel::getName)
                                                     .filter(s -> s.equals(this.metaModel.getShellOfStartRoute()))
                                                     .findFirst();
      if (optionalShell.isEmpty()) {
        if (!this.metaModel.getModules()
                           .isEmpty()) {
          this.processingEnvironment.getMessager()
                                    .printMessage(Diagnostic.Kind.NOTE,
                                                  "Nalu-Processor: The shell of the startRoute >>" +
                                                  this.metaModel.getShellOfStartRoute() +
                                                  "<< does not exist in this project");
        } else {
          throw new ProcessorException("Nalu-Processor: The shell of the startRoute >>" +
                                       this.metaModel.getShellOfStartRoute() +
                                       "<< does not exist!");
        }
      }

      // Does at least one controller exist for the start route?
      Optional<ControllerModel> optionalRoute = this.metaModel.getControllers()
                                                              .stream()
                                                              .filter(m -> m.match(this.metaModel.getStartRoute()))
                                                              .findAny();
      if (optionalRoute.isEmpty()) {
        if (!this.metaModel.getModules()
                           .isEmpty()) {
          this.processingEnvironment.getMessager()
                                    .printMessage(Diagnostic.Kind.NOTE,
                                                  "Nalu-Processor: The route of the startRoute >>" +
                                                  this.metaModel.getStartRoute() +
                                                  "<< does not exist in this project");
        } else {
          throw new ProcessorException("Nalu-Processor: The route of the startRoute >>" +
                                       this.metaModel.getStartRoute() +
                                       "<< does not exist!");
        }
      }

      // check, that the start route is not only a route cantaining at least only the shell
      String[] routeParts = this.splitRoute(this.metaModel.getStartRoute());
      if (routeParts.length < 2) {
        throw new ProcessorException("Nalu-Processor: The startRoute >>" +
                                     this.metaModel.getStartRoute() +
                                     "<< can not contain only a shell");
      }
    }
  }

  private void validateNoShellsDefined()
      throws ProcessorException {
    if (!Objects.isNull(metaModel.getApplication())) {
      if (!metaModel.getShells()
                    .isEmpty()) {
        return;
      }
      throw new ProcessorException("Nalu-Processor: No shells defined! Please define (at least) one shell.");
    }
  }

  private void validateDuplicateShellName()
      throws ProcessorException {
    List<String> compareList = new ArrayList<>();
    for (ShellModel shellModel : this.metaModel.getShells()) {
      if (compareList.contains(shellModel.getName())) {
        throw new ProcessorException("Nalu-Processor:" +
                                     "@Shell: the name >>" +
                                     shellModel.getName() +
                                     "<< is duplicate! Please use another unique name!");
      }
      compareList.add(shellModel.getName());
    }
  }

  private void validateDuplicateBlockControllerName()
      throws ProcessorException {
    List<String> compareList = new ArrayList<>();
    for (BlockControllerModel blockControllerModel : this.metaModel.getBlockControllers()) {
      if (compareList.contains(blockControllerModel.getName())) {
        throw new ProcessorException("Nalu-Processor:" +
                                     "@BlockController: the name >>" +
                                     blockControllerModel.getName() +
                                     "<< is duplicate! Please use another unique name!");
      }
      compareList.add(blockControllerModel.getName());
    }
  }

  private void validateDuplicateCompositeNamesInAController()
      throws ProcessorException {
    List<String> compareList = new ArrayList<>();
    for (ControllerModel controllerModel : this.metaModel.getControllers()) {
      for (ShellAndControllerCompositeModel shellAndControllerCompositeModel : controllerModel.getComposites()) {
        if (compareList.contains(shellAndControllerCompositeModel.getName())) {
          throw new ProcessorException("Nalu-Processor:" +
                                       "@Compiste: the name >>" +
                                       shellAndControllerCompositeModel.getName() +
                                       "<< is duplicate! Please use another unique name!");
        }
        compareList.add(shellAndControllerCompositeModel.getName());
      }
      // reset compare list
      compareList = new ArrayList<>();
    }
  }

  private void validateDuplicateCompositeNamesInAShell()
      throws ProcessorException {
    List<String> compareList = new ArrayList<>();
    for (ShellModel shellModel : this.metaModel.getShells()) {
      for (ShellAndControllerCompositeModel shellAndControllerCompositeModel : shellModel.getComposites()) {
        if (compareList.contains(shellAndControllerCompositeModel.getName())) {
          throw new ProcessorException("Nalu-Processor:" +
                                       "@Compiste: the name >>" +
                                       shellAndControllerCompositeModel.getName() +
                                       "<< is duplicate! Please use another unique name!");
        }
        compareList.add(shellAndControllerCompositeModel.getName());
      }
      // reset compare list
      compareList = new ArrayList<>();
    }
  }

  private void vallidateErrorPopUpControllerInSubModule()
      throws ProcessorException {
    // current compilation source is a sub module
    if (!Objects.isNull(this.metaModel.getModuleModel())) {
      // current module has a error pop up Controller
      if (!Objects.isNull(this.metaModel.getErrorPopUpController())) {
        throw new ProcessorException("Nalu-Processor:" + "@ErrorPopUpController: can only be use inside a main module");
      }
    }
  }

  private String[] splitRoute(String route) {
    String tmpRoute = route;
    tmpRoute = NaluUtils.INSTANCE.removeLeading("/", tmpRoute);
    return tmpRoute.split("/");
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

    public ConsistenceValidator build() {
      return new ConsistenceValidator(this);
    }

  }

}
