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
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.SimpleTypeVisitor8;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EventHandlerAnnotationScanner {

  private final ProcessingEnvironment processingEnvironment;
  private final MetaModel metaModel;
  private final Element parentElement;
  private ProcessorUtils processorUtils;

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
      if (!(element instanceof ExecutableElement)) {
        throw new ProcessorException("NaluProcessor: element >>" +
                                     this.parentElement.getSimpleName()
                                                       .toString() +
                                     "<< is not of type ExecutableElement");
      }
      ExecutableElement executableElement = (ExecutableElement) element;
      String methodName = executableElement.getSimpleName()
                                           .toString();
      // check number of paraemters --> should be 1!
      if (executableElement.getParameters()
                           .size() == 0 ||
          executableElement.getParameters()
                           .size() > 1) {
        throw new ProcessorException("NaluProcessor: @EventHandler -> method >> " +
                                     methodName +
                                     "<< should have only one parameter and that should be an event");
      }

      VariableElement variableElement = executableElement.getParameters()
                                                         .get(0);
      TypeElement eventElement = (TypeElement) this.processingEnvironment.getTypeUtils()
                                                                         .asElement(variableElement.asType());

      this.validateEvent(eventElement,
                         methodName);

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
        EventModel eventModel = this.scanEvent(eventElement,
                                               methodName);
        eventMetaData.getEventModels()
                     .add(eventModel);
      }
      eventMetaData.getEventHandlerModels()
                   .add(eventHandlerModel);
    }

    return eventMetaData;
  }

  private void validateEvent(TypeElement eventElement,
                             String methodName)
      throws ProcessorException {
    ProcessorUtils processorUtils = ProcessorUtils.builder()
                                                  .processingEnvironment(this.processingEnvironment)
                                                  .build();
    if (!processorUtils.supertypeHasGeneric(this.processingEnvironment.getTypeUtils(),
                                            eventElement.asType(),
                                            this.processorUtils.getElements()
                                                               .getTypeElement(Event.class.getCanonicalName())
                                                               .asType())) {
      throw new ProcessorException("NaluProcessor: class >>" +
                                   eventElement.getQualifiedName()
                                               .toString() +
                                   "<< - method >>" +
                                   methodName +
                                   "<< has wrong data type. Parameter needs to extend org.gwtproject.event.shared.Event");
    }

    TypeMirror gwtEventHandlerTypeMirror = this.getGwtEventHandlerType(eventElement.asType());
    if (Objects.isNull(gwtEventHandlerTypeMirror)) {
      throw new ProcessorException("NaluProcessor: class >> " +
                                   eventElement.getQualifiedName() +
                                   "<< does not have a generic EventHandler defined");
    }
  }

  private EventModel scanEvent(TypeElement eventElement,
                               String methodName)
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
      throw new ProcessorException("NaluProcessor: class >> " +
                                   eventElement.getQualifiedName() +
                                   "<< does not extend org.gwtproject.event.shared.Event");
    }
    if (!processorUtils.supertypeHasGeneric(this.processingEnvironment.getTypeUtils(),
                                            gwtEventMirror,
                                            this.processorUtils.getElements()
                                                               .getTypeElement(Event.class.getCanonicalName())
                                                               .asType())) {
      throw new ProcessorException("NaluProcessor: class >>" +
                                   eventElement.getQualifiedName()
                                               .toString() +
                                   "<< - method >>" +
                                   methodName +
                                   "<< has wrong data type. Parameter needs to extend org.gwtproject.event.shared.Event");
    }

    TypeMirror gwtEventHandlerTypeMirror = this.getGwtEventHandlerType(eventElement.asType());
    if (Objects.isNull(gwtEventHandlerTypeMirror)) {
      throw new ProcessorException("NaluProcessor: class >> " +
                                   eventElement.getQualifiedName() +
                                   "<< does not have a generic EventHandler defined");
    }
    TypeElement gwtEventHandlerElement = (TypeElement) this.processingEnvironment.getTypeUtils()
                                                                                 .asElement(gwtEventHandlerTypeMirror);
    // Save to MetaModel
    return new EventModel(new ClassNameModel(eventElement.getQualifiedName()
                                                         .toString()),
                          new ClassNameModel(gwtEventHandlerElement.getQualifiedName()
                                                                   .toString()),
                          methodName);
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

    private final List<EventHandlerModel> eventHandlerModels;
    private final List<EventModel>        eventModels;

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
