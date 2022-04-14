/*
 * Copyright (c) 2018 - 2020 - Frank Hossfeld
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
import com.github.nalukit.nalu.client.context.IsModuleContext;
import com.github.nalukit.nalu.client.event.annotation.EventHandler;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;
import com.github.nalukit.nalu.processor.model.intern.EventHandlerModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.naming.ServiceUnavailableException;

import static java.util.Objects.isNull;

public class EventHandlerAnnotationScanner {

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;

  private MetaModel metaModel;

  private Element eventHandlerElement;

  @SuppressWarnings("unused")
  private EventHandlerAnnotationScanner(Builder builder) {
    super();
    this.processingEnvironment = builder.processingEnvironment;
    this.metaModel             = builder.metaModel;
    this.eventHandlerElement   = builder.eventHandlerElement;
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

  public EventHandlerModel scan(RoundEnvironment roundEnvironment)
      throws ProcessorException {
    EventHandler eventHandlerAnnotation = eventHandlerElement.getAnnotation(EventHandler.class);
    if (!isNull(eventHandlerAnnotation)) {
      TypeElement eventElement  = this.getEventElement(eventHandlerAnnotation);
      TypeElement parentElement = (TypeElement) eventHandlerElement.getEnclosingElement();
      if (!(this.eventHandlerElement instanceof ExecutableElement)) {
        throw new ProcessorException("element >>" +
                                     this.eventHandlerElement.getSimpleName()
                                                             .toString() +
                                     "<< is not of type ExecutableElement");
      }
      ExecutableElement executableElement = (ExecutableElement) this.eventHandlerElement;
      String            methodName        = executableElement.getSimpleName()
                                                             .toString();
      this.metaModel.getEventHandlerModels()
                    .add(new EventHandlerModel(new ClassNameModel(parentElement.getQualifiedName()
                                                                               .toString()),
                                               new ClassNameModel(eventElement.getQualifiedName()
                                                                              .toString()),
                                               methodName));
      //      TypeElement postLoaderTypeElement             = this.getPostLoaderTypeElement(applicationAnnotation);
      //      TypeElement contextTypeElement                = this.getContextTypeElement(applicationAnnotation);
      //      TypeElement customAlertPresenterTypeElement   = this.getCustomAlertPresenterTypeElement(applicationAnnotation);
      //      TypeElement customConfirmPresenterTypeElement = this.getCustomConfirmPresenterTypeElement(applicationAnnotation);
      //      metaModel.setGenerateToPackage(this.processorUtils.getPackageAsString(applicationElement));
      //      metaModel.setApplication(new ClassNameModel(applicationElement.toString()));
      //      metaModel.setLoader(new ClassNameModel(isNull(loaderTypeElement) ? "" : loaderTypeElement.toString()));
      //      metaModel.setPostLoader(new ClassNameModel(isNull(postLoaderTypeElement) ? "" : postLoaderTypeElement.toString()));
      //      if (isNull(contextTypeElement)) {
      //        throw new ProcessorException("Nalu-Processor: context in application annotation is null!");
      //      } else {
      //        metaModel.setContext(new ClassNameModel(contextTypeElement.toString()));
      //      }
      //      metaModel.setStartRoute(applicationAnnotation.startRoute());
      //      metaModel.setIllegalRouteTarget(applicationAnnotation.illegalRouteTarget());
      //      metaModel.setRemoveUrlParameterAtStart(applicationAnnotation.removeUrlParameterAtStart());
      //      metaModel.setUsingHash(applicationAnnotation.useHash());
      //      metaModel.setUsingColonForParametersInUrl(applicationAnnotation.useColonForParametersInUrl());
      //      metaModel.setStayOnSide(applicationAnnotation.stayOnSite());
      //      metaModel.setHistory(applicationAnnotation.history());
      //      metaModel.setCustomAlertPresenter(new ClassNameModel(isNull(customAlertPresenterTypeElement) ? "" : customAlertPresenterTypeElement.toString()));
      //      metaModel.setCustomConfirmPresenter(new ClassNameModel(isNull(customConfirmPresenterTypeElement) ? "" : customConfirmPresenterTypeElement.toString()));
      //      metaModel.setExtendingIsModuleContext(this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
      //                                                                                        contextTypeElement.asType(),
      //                                                                                        this.processingEnvironment.getElementUtils()
      //                                                                                                                  .getTypeElement(IsModuleContext.class.getCanonicalName())
      //                                                                                                                  .asType()));
      System.out.print("test");
    } return null;
  }

  private TypeElement getEventElement(EventHandler eventHandlerAnnotation) {
    try {
      eventHandlerAnnotation.value();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  private TypeElement getPostLoaderTypeElement(Application applicationAnnotation) {
    try {
      applicationAnnotation.postLoader();
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

  private TypeElement getCustomAlertPresenterTypeElement(Application applicationAnnotation) {
    try {
      applicationAnnotation.alertPresenter();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  private TypeElement getCustomConfirmPresenterTypeElement(Application applicationAnnotation) {
    try {
      applicationAnnotation.confirmPresenter();
    } catch (MirroredTypeException exception) {
      return (TypeElement) this.processingEnvironment.getTypeUtils()
                                                     .asElement(exception.getTypeMirror());
    }
    return null;
  }

  public static class Builder {

    ProcessingEnvironment processingEnvironment;

    MetaModel metaModel;

    Element eventHandlerElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder metaModel(MetaModel metaModel) {
      this.metaModel = metaModel;
      return this;
    }

    public Builder eventHandlerElement(Element eventHandlerElement) {
      this.eventHandlerElement = eventHandlerElement;
      return this;
    }

    public EventHandlerAnnotationScanner build() {
      return new EventHandlerAnnotationScanner(this);
    }

  }

}
