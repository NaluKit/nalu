/*
 * Copyright (c) 2018 Frank Hossfeld
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

import com.github.nalukit.nalu.client.component.IsController;
import com.github.nalukit.nalu.client.component.annotation.AcceptParameter;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ControllerAnnotationValidator {

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private Element controllerElement;

  @SuppressWarnings("unused")
  private ControllerAnnotationValidator() {
  }

  private ControllerAnnotationValidator(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;
    this.controllerElement     = builder.controllerElement;
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
    TypeElement typeElement = (TypeElement) this.controllerElement;
    // @Controller can only be used on a class
    if (!typeElement.getKind()
                    .isClass()) {
      throw new ProcessorException("Nalu-Processor: @Controller can only be used with an class");
    }
    // @Controller can only be used on a interface that extends IsController
    if (!this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
                                                     typeElement.asType(),
                                                     this.processingEnvironment.getElementUtils()
                                                                               .getTypeElement(IsController.class.getCanonicalName())
                                                                               .asType())) {
      throw new ProcessorException("Nalu-Processor: @Controller can only be used on a class that extends IsController or IsShell");
    }
    // check if route start with "/"
    Controller controllerAnnotation = this.controllerElement.getAnnotation(Controller.class);
    for (String route : controllerAnnotation.route()) {
      if (!route.startsWith("/")) {
        throw new ProcessorException("Nalu-Processor: @Controller - route attribute muss begin with a '/'");
      }
    }
    String[] routes = controllerAnnotation.route();
    // validate routes
    // 1. check, that all routes are valid
    for (String route : routes) {
      validateRoute(route);
    }
    validateParameterNamesAreUnique(routes[0]);
    if (routes.length > 1) {
      // only, if we have more than one route!
      validateParameters(routes);
      searchForDuplicateRoutes(routes);
    }
    // AcceptParameter annotation
    for (String route : controllerAnnotation.route()) {
      List<String> parametersFromRoute = this.getParametersFromRoute(route);
      for (Element element : this.processingEnvironment.getElementUtils()
                                                       .getAllMembers((TypeElement) this.controllerElement)) {
        if (ElementKind.METHOD.equals(element.getKind())) {
          if (!Objects.isNull(element.getAnnotation(AcceptParameter.class))) {
            AcceptParameter annotation = element.getAnnotation(AcceptParameter.class);
            if (!parametersFromRoute.contains(annotation.value())) {
              throw new ProcessorException("Nalu-Processor: controller >>" +
                                           controllerElement.toString() +
                                           "<< - @AcceptParameter with value >>" +
                                           annotation.value() +
                                           "<< is not represented in the route as parameter");
            }
            ExecutableType             executableType = (ExecutableType) element.asType();
            List<? extends TypeMirror> parameters     = executableType.getParameterTypes();
            if (parameters.size() != 1) {
              throw new ProcessorException("Nalu-Processor: controller >>" +
                                           controllerElement.toString() +
                                           "<< - @AcceptParameter annotated on >>" +
                                           executableType.toString() +
                                           "<< need one parameter of type String");
            }
            if (!String.class.getCanonicalName()
                             .equals(parameters.get(0)
                                               .toString())) {
              throw new ProcessorException("Nalu-Processor: controller >>" +
                                           controllerElement.toString() +
                                           "<< - @AcceptParameter on >>" +
                                           element.toString() +
                                           "<< parameter has the wrong type -> must be a String");
            }
          }
        }
      }
    }
  }

  @SuppressWarnings("StringSplitter")
  private void validateRoute(String route)
      throws ProcessorException {
    // extract route first:
    if (route.startsWith("/")) {
      route = route.substring(1);
    }
    // initial routes do not need a validation!
    if (route.length() == 0) {
      return;
    }
    String[] splits      = route.split("/");
    int      partCounter = 0;
    for (String s : splits) {
      partCounter++;
      // handle "//" -> not allowed
      if (s.length() == 0) {
        throw new ProcessorException("Nalu-Processor: controller >>" +
                                     this.controllerElement.getEnclosingElement()
                                                           .toString() +
                                     "<<  - illegal route >>" +
                                     route +
                                     "<< -> '//' not allowed!");
      }
      // check if it is a parameter definition (starting with ':' at first position)
      if (s.startsWith(":")) {
        // starts with a parameter ==> error (must be a shell name)
        if (partCounter == 1) {
          throw new ProcessorException("Nalu-Processor: controller >>" +
                                       this.controllerElement.getEnclosingElement()
                                                             .toString() +
                                       "<<  - illegal route >>" +
                                       route +
                                       "<< -> route cannot start with parameter (Part 1 wrong)");
        }
        // starts with a parameter ==> error (must be a shell name)
        if (partCounter == 2) {
          throw new ProcessorException("Nalu-Processor: controller >>" +
                                       this.controllerElement.getEnclosingElement()
                                                             .toString() +
                                       "<<  - illegal route >>" +
                                       route +
                                       "<< -> route cannot start with parameter (Part 2 wrong)");
        }
        if (s.length() == 1) {
          throw new ProcessorException("Nalu-Processor: controller >>" +
                                       this.controllerElement.getEnclosingElement()
                                                             .toString() +
                                       "<<  - illegal route >>" +
                                       route +
                                       "<< -> illegal parameter name!");
        }
        //      } else {
        //        if (handlingParameter) {
        //          throw new ProcessorException("Nalu-Processor: controller >>" +
        //                                       this.controllerElement.getEnclosingElement()
        //                                                             .toString() +
        //                                       "<<  - illegal route >>" +
        //                                       route +
        //                                       "<< -> illegal route!");
        //        }
      }
    }
  }

  private void validateParameterNamesAreUnique(String route)
      throws ProcessorException {
    List<String> parametersOfFirstRoute = this.getParametersFromRoute(route);
    List<String> duplicateParameters = parametersOfFirstRoute.stream()
                                                             .collect(Collectors.groupingBy(Function.identity()))
                                                             .entrySet()
                                                             .stream()
                                                             .filter(e -> e.getValue()
                                                                           .size() > 1)
                                                             .map(Map.Entry::getKey)
                                                             .collect(Collectors.toList());
    if (duplicateParameters.size() > 0) {
      StringBuilder sb = new StringBuilder();
      sb.append("Nalu-Processor: controller >>")
        .append(this.controllerElement.getEnclosingElement()
                                      .toString())
        .append("<<  - parameter names needs to be unique (route >>")
        .append(route)
        .append("<< duplicate parameter: ");
      IntStream.range(0,
                      duplicateParameters.size())
               .forEachOrdered(i -> {
                 sb.append(">>")
                   .append(duplicateParameters.get(i))
                   .append("<<");
                 if (i < duplicateParameters.size() - 1) {
                   sb.append(", ");
                 }
               });
      sb.append(")");
      throw new ProcessorException(sb.toString());
    }
  }

  private void validateParameters(String[] routes)
      throws ProcessorException {
    List<String> parametersOfFirstRoute = this.getParametersFromRoute(routes[0]);
    for (int i = 1; i < routes.length; i++) {
      List<String> compareParameters = this.getParametersFromRoute(routes[i]);
      if (parametersOfFirstRoute.size() != compareParameters.size()) {
        throw new ProcessorException("Nalu-Processor: controller >>" +
                                     this.controllerElement.getEnclosingElement()
                                                           .toString() +
                                     "<<  - parameter needs to be equal for all routes (source-route >>" +
                                     routes[0] +
                                     "<< -> compare route >>" +
                                     routes[i] +
                                     "<" +
                                     "<<)[0]");
      }
      for (String parameterFromFirstRoute : parametersOfFirstRoute) {
        compareParameters.remove(parameterFromFirstRoute);
      }
      if (compareParameters.size() > 0) {
        StringBuilder sb = new StringBuilder();
        sb.append("Nalu-Processor: controller >>")
          .append(this.controllerElement.getEnclosingElement()
                                        .toString())
          .append("<<  - parameter needs to be equal for all routes (source-route >>")
          .append(routes[i])
          .append("<< illegal parameter: ");
        IntStream.range(0,
                        compareParameters.size())
                 .forEachOrdered(j -> {
                   sb.append(">>")
                     .append(compareParameters.get(j))
                     .append("<<");
                   if (j < compareParameters.size() - 1) {
                     sb.append(", ");
                   }
                 });
        sb.append(")[1]");
        throw new ProcessorException(sb.toString());
      }
    }
  }

  private void searchForDuplicateRoutes(String[] routes)
      throws ProcessorException {
    List<String> convertedRoutes = Arrays.stream(routes)
                                         .map(this::convertRoute)
                                         .collect(Collectors.toList());
    List<String> duplicateRoutes = convertedRoutes.stream()
                                                  .collect(Collectors.groupingBy(Function.identity()))
                                                  .entrySet()
                                                  .stream()
                                                  .filter(e -> e.getValue()
                                                                .size() > 1)
                                                  .map(Map.Entry::getKey)
                                                  .collect(Collectors.toList());
    if (duplicateRoutes.size() > 0) {
      StringBuilder sb = new StringBuilder();
      sb.append("Nalu-Processor: controller >>")
        .append(this.controllerElement.getEnclosingElement()
                                      .toString())
        .append("<< duplicate route: ");
      IntStream.range(0,
                      duplicateRoutes.size())
               .forEachOrdered(i -> {
                 sb.append(">>")
                   .append(duplicateRoutes.get(i))
                   .append("<<");
                 if (i < duplicateRoutes.size() - 1) {
                   sb.append(", ");
                 }
               });
      sb.append(")");
      throw new ProcessorException(sb.toString());
    }

  }

  private List<String> getParametersFromRoute(String route)
      throws ProcessorException {
    List<String> list   = Arrays.asList(route.split("/"));
    List<String> result = new ArrayList<>();
    for (String s : list) {
      if (s.startsWith(":")) {
        String substring = s.substring(1);
        result.add(substring);
      } else if (s.startsWith("{")) {
        String lastChar = s.substring(s.length() - 1);
        if (lastChar.equals("}")) {
          result.add(s.substring(1,
                                 s.length() - 1));
        } else {
          StringBuilder sb = new StringBuilder();
          sb.append("Nalu-Processor: controller >>")
            .append(this.controllerElement.getEnclosingElement()
                                          .toString())
            .append("<<  - parameter has illegal definition (missing '}' at the end of the parameter) >>")
            .append(s)
            .append("<<");
          throw new ProcessorException(sb.toString());
        }
      }
    }
    return result;
  }

  /**
   * Converts the parameter parts with '*'
   * <p>
   * A route containing null will be converted to an empty string!
   *
   * @param route route to convert
   * @return converted route
   */
  @SuppressWarnings("StringSplitter")
  private String convertRoute(String route) {
    if (Objects.isNull(route)) {
      return "";
    }
    if ("/".equals(route)) {
      return route;
    }
    String[]      splits   = route.split("/");
    StringBuilder newRoute = new StringBuilder();
    for (int i = 1; i < splits.length; i++) {
      String s = splits[i];
      if (!Objects.isNull(s)) {
        if ("*".equals(s)) {
          newRoute.append("/*");
        } else if (s.startsWith(":")) {
          newRoute.append("/*");
        } else {
          newRoute.append("/")
                  .append(s);
        }
      }
    }
    return newRoute.toString();
  }

  public static final class Builder {

    ProcessingEnvironment processingEnvironment;

    RoundEnvironment roundEnvironment;

    Element controllerElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }

    public Builder controllerElement(Element controllerElement) {
      this.controllerElement = controllerElement;
      return this;
    }

    public ControllerAnnotationValidator build() {
      return new ControllerAnnotationValidator(this);
    }

  }

}
