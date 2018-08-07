/*
 * Copyright (c) 2018 - Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *
 */

package com.github.mvp4g.nalu.react.processor.eventbus;

import java.util.ArrayList;

import javax.tools.JavaFileObject;

import com.github.mvp4g.nalu.react.processor.NaluReactProcessor;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import static com.google.testing.compile.Compiler.javac;

public class EventbusTest {

  @Test
  public void testEventBusAnnotationOnAMethod() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventbus/eventBusAnnotationOnAMethod/EventBusAnnotationOnAMethod.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Eventbus can only be used on a type (interface)");
  }

  @Test
  public void testEventBusAnnotationOnAClass() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventbus/eventBusAnnotationOnAClass/EventBusAnnotationOnAClass.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Eventbus can only be used with an interface");
  }

  @Test
  public void testEventBusNotExtendingAbstractEventHandlerd() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventbus/eventBusNotExtendingIsEventBus/EventBusNotExtendingIsEventBus.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Eventbus must extend IsEventBus.class!");
  }

  @Test
  public void testStartEventTestEventBusWithMoreThanOneStartAnnotation() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/startEventTestEventBusWithMoreThanOneStartAnnotation/StartEventTestEventBusWithMoreThanOneStartAnnotation.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Start-annotation can only be used a single time in a eventbus interface");
  }

  @Test
  public void testStartEventTestWithNonZeroArgumentMethod() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/startEventTestWithNonZeroArgumentMethod/StartEventTestWithNonZeroArgumentMethod.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Start-annotation can only be used on zero argument methods");
  }

  @Test
  public void testEventWithHandlerAttributeNotImplemented01() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventbus/eventWithHandlerAttributeNotImplemented01/EventBusEventWithHandlerAttributeNotImplemented.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventbus/eventWithHandlerAttributeNotImplemented01/MockShellPresenter01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventbus/eventWithHandlerAttributeNotImplemented01/IMockShellView01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventbus/eventWithHandlerAttributeNotImplemented01/MockShellView01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("NaluProcessor: presenter >>MockShellPresenter01<< -> event >>onDoSomething()<< is not handled by presenter/handler");
  }

  @Test
  public void testEventWithHandlerAttributeNotImplemented02() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventbus/eventWithHandlerAttributeNotImplemented02/EventBusEventWithHandlerAttributeNotImplemented.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventbus/eventWithHandlerAttributeNotImplemented02/MockShellPresenter01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventbus/eventWithHandlerAttributeNotImplemented02/IMockShellView01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventbus/eventWithHandlerAttributeNotImplemented02/MockShellView01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("MockShellPresenter01<< -> event >>onDoSomething(java.lang.String)<< is not handled by presenter/handler");
  }
}
