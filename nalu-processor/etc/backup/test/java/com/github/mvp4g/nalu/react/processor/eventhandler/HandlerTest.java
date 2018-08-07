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

package com.github.mvp4g.nalu.react.processor.eventhandler;

import java.util.ArrayList;

import javax.tools.JavaFileObject;

import com.github.mvp4g.nalu.react.processor.NaluReact-Processor;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjectSubject;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import static com.google.testing.compile.Compiler.javac;

public class HandlerTest {

  @Test
  public void testEventHandlerAnnotationAnnotatedAbstractClass() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlerAnnotationAnnotatedOnAbstractClass/EventHandlerAnnotationAnnotatedOnAbstractClass.java"));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Handler can not be ABSTRACT");

  }

  @Test
  public void testEventHandlerAnnotationAnnotatedOnAInterface() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlerAnnotationAnnotatedOnAInterface/EventHandlerAnnotationAnnotatedOnAInterface.java"));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Handler can only be used with a class");
  }

  @Test
  public void testEventHandlerNotExtendingAbstractEventHandlerd() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlerNotExtendingAbstractEventHandler/EventHandlerNotExtendingAbstractEventHandler.java"));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Handler must extend AbstractHandler.class!");
  }

  @Test
  public void testEventHandlerOK() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlerOK/EventHandlerOK.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlerOK/MockEventBus.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlerOK/MockShellPresenter.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlerOK/MockOneEventHandler.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    JavaFileObjectSubject.assertThat(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlerOK/Com_github_mvp4g_mvp4g2_processor_eventhandler_eventHandlerOK_EventHandlerOKMetaData.java"))
                         .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlerOK/Com_github_mvp4g_mvp4g2_processor_eventhandler_eventHandlerOK_EventHandlerOKMetaData.java"));
  }

  @Test
  public void testEventHandlerWithNotImplementedEvent() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/handlerWithNotImplementedEvent/EventBusHandlerWithNotImplementedEvent.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/handlerWithNotImplementedEvent/MockOneEventHandler.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/handlerWithNotImplementedEvent/MockShellPresenter01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/handlerWithNotImplementedEvent/IMockShellView01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/handlerWithNotImplementedEvent/MockShellView01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("event >>doSomethingInHandler()<< is never handled by a presenter or handler");
  }

  @Test
  public void testHandlerWithWrongImplementation01() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/handlerWithWrongImplementation01/EventBusHandlerWithNotImplementedEvent.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/handlerWithWrongImplementation01/MockOneEventHandler.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/handlerWithWrongImplementation01/MockShellPresenter01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/handlerWithWrongImplementation01/IMockShellView01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/handlerWithWrongImplementation01/MockShellView01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("event >>doSomethingInHandler(java.lang.String)<< is never handled by a presenter or handler");
  }

  @Test
  public void testHandlerWithWrongImplementation02() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/handlerWithWrongImplementation02/EventBusHandlerWithNotImplementedEvent.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/handlerWithWrongImplementation02/MockOneEventHandler.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/handlerWithWrongImplementation02/MockShellPresenter01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/handlerWithWrongImplementation02/IMockShellView01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/handlerWithWrongImplementation02/MockShellView01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("event >>doSomethingInHandler()<< is never handled by a presenter or handler");
  }

  @Test
  public void testEventHandlingMethodDoesNotReturnVoid01() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlingMethodDoesNotReturnVoid01/EventBusEventHandlingMethodDoesNotReturnVoid.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlingMethodDoesNotReturnVoid01/MockOneEventHandler.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlingMethodDoesNotReturnVoid01/MockShellPresenter01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlingMethodDoesNotReturnVoid01/IMockShellView01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlingMethodDoesNotReturnVoid01/MockShellView01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("NaluProcessor: >>MockOneEventHandler<< -> EventElement: >>onDoSomethingInHandler(java.lang.String)<< must return 'void'");
  }

  @Test
  public void testEventHandlingMethodDoesNotReturnVoid02() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlingMethodDoesNotReturnVoid02/EventBusEventHandlingMethodDoesNotReturnVoid.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlingMethodDoesNotReturnVoid02/MockOneEventHandler.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlingMethodDoesNotReturnVoid02/MockShellPresenter01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlingMethodDoesNotReturnVoid02/IMockShellView01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlingMethodDoesNotReturnVoid02/MockShellView01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("NaluProcessor: >>MockOneEventHandler<< -> EventElement: >>onDoSomethingInHandler(java.lang.String)<< must return 'void'");
  }

  @Test
  public void testHandlerWithWrongImplementation03() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/handlerWithWrongImplementation03/EventBusHandlerWithNotImplementedEvent.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/handlerWithWrongImplementation03/MockOneEventHandler.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/handlerWithWrongImplementation03/MockShellPresenter01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/handlerWithWrongImplementation03/IMockShellView01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/handlerWithWrongImplementation03/MockShellView01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
  }
}
