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

import com.github.nalukit.nalu.client.event.annotation.EventHandler;
import com.github.nalukit.nalu.processor.ProcessorException;
import com.github.nalukit.nalu.processor.ProcessorUtils;
import com.github.nalukit.nalu.processor.model.MetaModel;
import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;
import com.github.nalukit.nalu.processor.model.intern.EventHandlerModel;
import com.github.nalukit.nalu.processor.model.intern.EventModel;
import org.gwtproject.event.shared.Event;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.SimpleTypeVisitor8;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EventHandlerAnnotationScanner {

  private ProcessorUtils processorUtils;

  private final ProcessingEnvironment processingEnvironment;

  private final MetaModel metaModel;

  private final Element parentElement;

  @SuppressWarnings("unused")
  private EventHandlerAnnotationScanner(Builder builder) {
    super();
    this.processingEnvironment = builder.processingEnvironment;
    this.metaModel             = builder.metaModel;
    this.parentElement         = builder.parentElement;
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

  public EventMetaData scan()
      throws ProcessorException {
    EventMetaData eventMetaData = new EventMetaData();

    List<Element> eventHandlerElement = this.processorUtils.getMethodFromTypeElementAnnotatedWith(this.processingEnvironment,
                                                                                                  (TypeElement) this.parentElement,
                                                                                                  EventHandler.class);

    for (Element element : eventHandlerElement) {
      TypeElement eventElement = this.getEventElement(element.getAnnotation(EventHandler.class));
      if (Objects.isNull(eventElement)) {
        throw new ProcessorException("@EventHandler: value for event is null");
      }
      if (!(element instanceof ExecutableElement)) {
        throw new ProcessorException("element >>" +
                                     this.parentElement.getSimpleName()
                                                       .toString() +
                                     "<< is not of type ExecutableElement");
      }
      ExecutableElement executableElement = (ExecutableElement) element;
      String methodName = executableElement.getSimpleName()
                                           .toString();

      TypeElement parentTypeElement = (TypeElement) this.parentElement;
      EventHandlerModel eventHandlerModel = new EventHandlerModel(new ClassNameModel(parentTypeElement.getQualifiedName()
                                                                                                      .toString()),
                                                                  new ClassNameModel(eventElement.getQualifiedName()
                                                                                                 .toString()),
                                                                  methodName);

      Optional<EventModel> optional = eventMetaData.getEventModels()
                                                   .stream()
                                                   .filter(e -> e.getEvent()
                                                                 .getClassName()
                                                                 .equals(eventElement.getQualifiedName()
                                                                                     .toString()))
                                                   .findFirst();
      if (!optional.isPresent()) {
        EventModel eventModel = this.scanEvent(eventElement);
        eventMetaData.getEventModels()
                     .add(eventModel);
      }
      eventMetaData.getEventHandlerModels()
                   .add(eventHandlerModel);
    }

    //    EventHandler[] eventHandlerElements = this.parentElement.getAnnotationsByType(EventHandler.class);
    //    for (EventHandler eventHandlerAnnotation : eventHandlerElements) {
    //      TypeElement eventElement = this.getEventElement(eventHandlerAnnotation);
    //      if (Objects.isNull(eventElement)) {
    //        throw new ProcessorException("@EventHandler: value for event is null");
    //      }
    //      //      if (!(this.parentElement instanceof ExecutableElement)) {
    //      //        throw new ProcessorException("element >>" +
    //      //                                     this.parentElement.getSimpleName()
    //      //                                                       .toString() +
    //      //                                     "<< is not of type ExecutableElement");
    //      //      }
    //      //      ExecutableElement executableElement = (ExecutableElement) this.parentElement;
    //      //      String methodName = executableElement.getSimpleName()
    //      //                                           .toString();
    //      //
    //      //            EventHandlerModel eventHandlerModel = new EventHandlerModel(new ClassNameModel(parentElement.getQualifiedName()
    //      //                                                                                                        .toString()),
    //      //                                                                        new ClassNameModel(eventElement.getQualifiedName()
    //      //                                                                                                       .toString()),
    //      //                                                                        methodName);
    //
    //    }

    //    EventHandler eventHandlerAnnotation = parentElement.getAnnotation(EventHandler.class);
    //    if (!isNull(eventHandlerAnnotation)) {
    //      TypeElement eventElement = this.getEventElement(eventHandlerAnnotation);
    //      if (Objects.isNull(eventElement)) {
    //        throw new ProcessorException("annotation >>" + eventHandlerAnnotation + "<< is null");
    //      }
    //      TypeElement parentElement = (TypeElement) this.parentElement.getEnclosingElement();
    //      if (!(this.parentElement instanceof ExecutableElement)) {
    //        throw new ProcessorException("element >>" +
    //                                     this.parentElement.getSimpleName()
    //                                                       .toString() +
    //                                     "<< is not of type ExecutableElement");
    //      }
    //      ExecutableElement executableElement = (ExecutableElement) this.parentElement;
    //      String methodName = executableElement.getSimpleName()
    //                                           .toString();
    //
    //      EventHandlerModel eventHandlerModel = new EventHandlerModel(new ClassNameModel(parentElement.getQualifiedName()
    //                                                                                                  .toString()),
    //                                                                  new ClassNameModel(eventElement.getQualifiedName()
    //                                                                                                 .toString()),
    //                                                                  methodName);
    //      // handle the elated event
    //      this.scanEvent(eventElement);
    //      // Save EventhandlerModel to MetaModel (cause now, the event exists ...)
    //
    //      this.metaModel.getEventHandlerModels()
    //                    .add(eventHandlerModel);
    //    }

    return eventMetaData;
  }

  private EventModel scanEvent(TypeElement eventElement)
      throws ProcessorException {
    ProcessorUtils processorUtils = ProcessorUtils.builder()
                                                  .processingEnvironment(this.processingEnvironment)
                                                  .build();

    TypeMirror gwtEventMirror = processorUtils.getFlattenedSupertype(this.processingEnvironment.getTypeUtils(),
                                                                     eventElement.asType(),
                                                                     this.processorUtils.getElements()
                                                                                        .getTypeElement(Event.class.getCanonicalName())
                                                                                        .asType());
    if (Objects.isNull(gwtEventMirror)) {
      throw new ProcessorException("class >> " +
                                   eventElement.getQualifiedName() +
                                   "<< does not extend org.gwtproject.event.shared.Event");
    }
    if (!processorUtils.supertypeHasGeneric(this.processingEnvironment.getTypeUtils(),
                                            gwtEventMirror,
                                            this.processorUtils.getElements()
                                                               .getTypeElement(Event.class.getCanonicalName())
                                                               .asType())) {
      throw new ProcessorException("class >> " +
                                   eventElement.getQualifiedName() +
                                   "<< does not use a generic with org.gwtproject.event.shared.Event");
    }

    TypeMirror gwtEventHandlerTypeMirror = this.getGwtEventHandlerType(eventElement.asType());
    if (Objects.isNull(gwtEventHandlerTypeMirror)) {
      throw new ProcessorException("class >> " +
                                   eventElement.getQualifiedName() +
                                   "<< does not have a generic EventHandler defined");
    }
    TypeElement gwtEventHandlerElement = (TypeElement) this.processingEnvironment.getTypeUtils()
                                                                                 .asElement(gwtEventHandlerTypeMirror);

    List<ExecutableElement> methodList = new ArrayList<>();
    for (Element element : gwtEventHandlerElement.getEnclosedElements()) {
      if (element.getKind() == ElementKind.METHOD) {
        methodList.add((ExecutableElement) element);
      }
    }
    if (methodList.size() > 1) {
      throw new ProcessorException("class >> " +
                                   gwtEventHandlerElement.getQualifiedName() +
                                   "<< should not have more than one method defined");
    }
    if (methodList.get(0)
                  .getParameters()
                  .size() == 0 ||
        methodList.get(0)
                  .getParameters()
                  .size() > 1) {
      throw new ProcessorException("class >> " +
                                   gwtEventHandlerElement.getQualifiedName() +
                                   "<< should have only one parameter and that should be the event");
    }
    // Save to MetaModel
    return new EventModel(new ClassNameModel(eventElement.getQualifiedName()
                                                         .toString()),
                          new ClassNameModel(gwtEventHandlerElement.getQualifiedName()
                                                                   .toString()),
                          methodList.get(0)
                                    .getSimpleName()
                                    .toString());
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

  private TypeMirror getGwtEventHandlerType(final TypeMirror typeMirror) {
    final TypeMirror[] result = { null };
    TypeMirror type = this.processorUtils.getFlattenedSupertype(this.processingEnvironment.getTypeUtils(),
                                                                typeMirror,
                                                                this.processorUtils.getElements()
                                                                                   .getTypeElement(Event.class.getCanonicalName())
                                                                                   .asType());
    if (type == null) {
      return result[0];
    }
    type.accept(new SimpleTypeVisitor8<Void, Void>() {

                  @Override
                  protected Void defaultAction(TypeMirror typeMirror,
                                               Void v) {
                    throw new UnsupportedOperationException();
                  }

                  @Override
                  public Void visitPrimitive(PrimitiveType primitiveType,
                                             Void v) {
                    return null;
                  }

                  @Override
                  public Void visitArray(ArrayType arrayType,
                                         Void v) {
                    return null;
                  }

                  @Override
                  public Void visitDeclared(DeclaredType declaredType,
                                            Void v) {
                    List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
                    if (!typeArguments.isEmpty()) {
                      if (typeArguments.size() == 1) {
                        result[0] = typeArguments.get(0);
                      }
                    }
                    return null;
                  }

                  @Override
                  public Void visitError(ErrorType errorType,
                                         Void v) {
                    return null;
                  }

                  @Override
                  public Void visitTypeVariable(TypeVariable typeVariable,
                                                Void v) {
                    return null;
                  }
                },
                null);
    return result[0];
  }

  public static class Builder {

    ProcessingEnvironment processingEnvironment;

    MetaModel metaModel;

    Element parentElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder metaModel(MetaModel metaModel) {
      this.metaModel = metaModel;
      return this;
    }

    public Builder parentElement(Element parentElement) {
      this.parentElement = parentElement;
      return this;
    }

    public EventHandlerAnnotationScanner build() {
      return new EventHandlerAnnotationScanner(this);
    }

  }



  public class EventMetaData {

    private List<EventHandlerModel> eventHandlerModels;
    private List<EventModel>        eventModels;

    public EventMetaData() {
      this.eventHandlerModels = new ArrayList<>();
      this.eventModels        = new ArrayList<>();
    }

    public List<EventHandlerModel> getEventHandlerModels() {
      return eventHandlerModels;
    }

    public List<EventModel> getEventModels() {
      return eventModels;
    }
  }

}
