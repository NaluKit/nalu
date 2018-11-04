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

import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;
import com.github.nalukit.nalu.processor.model.ApplicationMetaModel;
import com.github.nalukit.nalu.processor.model.intern.ControllerModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import java.util.Optional;

public class ConsistenceValidator {

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private RoundEnvironment roundEnvironment;

  private ApplicationMetaModel applicationMetaModel;

  @SuppressWarnings("unused")
  private ConsistenceValidator() {
  }

  private ConsistenceValidator(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;
    this.roundEnvironment = builder.roundEnvironment;
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
    // check startroute parameter
    this.validateStartRoute();
  }

  private void validateStartRoute()
      throws ProcessorException {
    Optional<String> optionalShell = this.applicationMetaModel.getShells()
                                                              .stream()
                                                              .map(m -> m.getName())
                                                              .filter(s -> s.equals(this.applicationMetaModel.getShellOfStartRoute()))
                                                              .findFirst();
    if (!optionalShell.isPresent()) {
      throw new ProcessorException("Nalu-Processor: The shell of the startRoute >>" + this.applicationMetaModel.getShellOfStartRoute() + "<< does not exist!");
    }

    Optional<ControllerModel> optionalRoute = this.applicationMetaModel.getController()
                                                                       .stream()
                                                                       .filter(m -> m.match(this.applicationMetaModel.getStartRoute()))
                                                                       .findAny();
    if (!optionalRoute.isPresent()) {
      throw new ProcessorException("Nalu-Processor: The route of the startRoute >>" + this.applicationMetaModel.getStartRoute() + "<< does not exist!");
    }
  }

  public static final class Builder {

    ProcessingEnvironment processingEnvironment;

    RoundEnvironment roundEnvironment;

    ApplicationMetaModel applicationMetaModel;

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

    public ConsistenceValidator build() {
      return new ConsistenceValidator(this);
    }
  }
}
