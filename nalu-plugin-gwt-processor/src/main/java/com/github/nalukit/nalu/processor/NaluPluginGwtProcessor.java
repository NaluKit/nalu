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

import com.github.nalukit.nalu.plugin.gwt.client.annotation.Selector;
import com.github.nalukit.nalu.plugin.gwt.client.selector.AbstractSelectorProvider;
import com.github.nalukit.nalu.plugin.gwt.client.selector.IsSelectorProvider;
import com.github.nalukit.nalu.plugin.gwt.client.selector.SelectorCommand;
import com.github.nalukit.nalu.plugin.gwt.client.selector.SelectorProvider;
import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;
import com.google.auto.service.AutoService;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.squareup.javapoet.*;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.of;

@AutoService(Processor.class)
public class NaluPluginGwtProcessor
    extends AbstractProcessor {

  private static final String         IMPL_NAME = "SelectorProviderImpl";

  private              ProcessorUtils processorUtils;

  private Map<Element, List<SelectorMetaModel>> models;

  public NaluPluginGwtProcessor() {
    super();
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.RELEASE_8;
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return of(Selector.class.getCanonicalName()).collect(toSet());
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations,
                         RoundEnvironment roundEnv) {
    setUp(roundEnv);
    processorUtils.createNoteMessage("Nalu-Plugin-GWT-Processor triggered: " + System.currentTimeMillis());
    try {
      if (!roundEnv.processingOver()) {
        for (TypeElement annotation : annotations) {
          for (Element element : roundEnv.getElementsAnnotatedWith(Selector.class)) {
            validate(element);
            // get enclosing element
            Element enclosingElement = element.getEnclosingElement();
            // get Annotation
            Selector selectorAnnotation = element.getAnnotation(Selector.class);
            // add data
            this.models.computeIfAbsent(enclosingElement,
                                        s -> new ArrayList<>())
                       .add(new SelectorMetaModel(selectorAnnotation.value(),
                                                  enclosingElement.toString(),
                                                  element));
          }
        }
        // generate providers
        for (Element k : this.models.keySet()) {
          this.generate(k,
                        this.models.get(k));
        }
      }
    } catch (ProcessorException e) {
      this.processorUtils.createErrorMessage(e.getMessage());
    }
    return true;
  }

  private void setUp(RoundEnvironment roundEnv) {
    this.models = new HashMap<>();

    this.processorUtils = ProcessorUtils.builder()
                                        .processingEnvironment(processingEnv)
                                        .build();
  }

  private void validate(Element element)
      throws ProcessorException {
    // @AcceptParameter can only be used on a method
    if (!ElementKind.METHOD.equals(element.getKind())) {
      throw new ProcessorException("Nalu-Processor: @Selector can only be used with a method");
    }
    ExecutableElement executableElement = (ExecutableElement) element;
    if (executableElement.getParameters()
                         .size() != 1) {
      throw new ProcessorException("Nalu-Processor: @Selector can only be used with a method that has one parameter");
    }
    String parameterClass = executableElement.getParameters()
                                             .get(0)
                                             .asType()
                                             .toString();
    if (!(IsWidget.class.getCanonicalName()
                        .equals(parameterClass) ||
              Widget.class.getCanonicalName()
                          .equals(parameterClass))) {
      throw new ProcessorException("Nalu-Processor: @Selector can only be used with a method that has one parameter and the parameter type is com.google.gwt.user.client.ui.IsWidget or com.google.gwt.user.client.ui.WIdget");
    }
  }

  private void generate(Element enclosingElement,
                        List<SelectorMetaModel> models)
      throws ProcessorException {
    ClassNameModel enclosingClassNameModel = new ClassNameModel(enclosingElement.toString());
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(enclosingElement.getSimpleName() + NaluPluginGwtProcessor.IMPL_NAME)
                                        .superclass(ClassName.get(AbstractSelectorProvider.class))
                                        .addModifiers(Modifier.PUBLIC,
                                                      Modifier.FINAL)
                                        .addSuperinterface(ParameterizedTypeName.get(ClassName.get(IsSelectorProvider.class),
                                                                                     enclosingClassNameModel.getTypeName()));

    // constructor ...
    MethodSpec constructor = MethodSpec.constructorBuilder()
                                       .addModifiers(Modifier.PUBLIC)
                                       .addStatement("super()")
                                       .build();
    typeSpec.addMethod(constructor);

    // method "initialize"
    MethodSpec.Builder initializeMethod = MethodSpec.methodBuilder("initialize")
                                                    .addModifiers(Modifier.PUBLIC,
                                                                  Modifier.FINAL)
                                                    .addParameter(ParameterSpec.builder(ClassName.get(enclosingClassNameModel.getPackage(),
                                                                                                      enclosingClassNameModel.getSimpleName()),
                                                                                        "component")
                                                                               .build())
                                                    .addAnnotation(Override.class);
    models.forEach(model -> {
      initializeMethod.addStatement("$T.get().getSelectorCommands().put($S, $L)",
                                    ClassName.get(SelectorProvider.class),
                                    model.selector,
                                    TypeSpec.anonymousClassBuilder("")
                                            .addSuperinterface(SelectorCommand.class)
                                            .addMethod(MethodSpec.methodBuilder("append")
                                                                 .addAnnotation(Override.class)
                                                                 .addModifiers(Modifier.PUBLIC)
                                                                 .addParameter(ParameterSpec.builder(ClassName.get(IsWidget.class),
                                                                                                     "widget")
                                                                                            .build())
                                                                 .addStatement("component.$L(widget.asWidget())",
                                                                               model.getSelectorElement()
                                                                                    .getSimpleName()
                                                                                    .toString())
                                                                 .build())
                                            .build())
                      .build();
    });
    typeSpec.addMethod(initializeMethod.build());

    // method "remove"
    MethodSpec.Builder removeMethod = MethodSpec.methodBuilder("removeSelectors")
                                                .addModifiers(Modifier.PUBLIC,
                                                              Modifier.FINAL)
                                                .addAnnotation(Override.class);
    models.forEach(model -> {
      removeMethod.addStatement("$T.get().getSelectorCommands().remove($S)",
                                ClassName.get(SelectorProvider.class),
                                model.getSelector())
                  .build();
    });
    typeSpec.addMethod(removeMethod.build());

    JavaFile javaFile = JavaFile.builder(enclosingClassNameModel.getPackage(),
                                         typeSpec.build())
                                .build();
    try {
      //      System.out.println(javaFile.toString());
      javaFile.writeTo(this.processingEnv.getFiler());
    } catch (IOException e) {
      throw new ProcessorException("Unable to write generated file: >>" + enclosingElement.getSimpleName() + NaluPluginGwtProcessor.IMPL_NAME + "<< -> exception: " + e.getMessage());
    }
  }

  class SelectorMetaModel {

    private String selector;

    private String enclosingElement;

    private Element selectorElement;

    public SelectorMetaModel(String selector,
                             String enclosingElement,
                             Element selectorElement) {
      this.selector = selector;
      this.enclosingElement = enclosingElement;
      this.selectorElement = selectorElement;
    }

    public String getSelector() {
      return selector;
    }

    public String getEnclosingElement() {
      return enclosingElement;
    }

    public Element getSelectorElement() {
      return selectorElement;
    }

    public ClassNameModel getEnclosingElementModel() {
      return new ClassNameModel(this.enclosingElement);
    }
  }
}
