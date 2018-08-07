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

package com.github.mvp4g.nalu.react.processor.event;

import java.util.ArrayList;

import javax.tools.JavaFileObject;

import com.github.mvp4g.nalu.react.processor.NaluReactProcessor;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjectSubject;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import static com.google.testing.compile.Compiler.javac;

public class EventTest {

  @Test
  public void testEventTestHistoryNamesNotUnique() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventTestHistoryNamesNotUnique/EventTestHistoryNamesNotUnique.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("using a already used historyName");
  }

  @Test
  public void testEventTestHandlerInBindAndHandlersAttribute01() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventTestHandlerInBindAndHandlersAttribute01/EventTestHandlerInBindAndHandlersAttribute.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("can not be set in bind- and handlers-attribute");
  }

  @Test
  public void testEventTestHandlerInBindAndHandlersAttribute02() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventTestHandlerInBindAndHandlersAttribute02/EventTestHandlerInBindAndHandlersAttribute.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventTestHandlerInBindAndHandlersAttribute02/MockShellPresenter.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
  }

  @Test
  public void testEventTestHandlerNotInBindAndHandlersAttribute() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventTestHandlerNotInBindAndHandlersAttribute/EventTestHandlerNotInBindAndHandlersAttribute.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventTestHandlerNotInBindAndHandlersAttribute/MockOneEventHandler.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventTestHandlerNotInBindAndHandlersAttribute/MockShellPresenter.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    JavaFileObjectSubject.assertThat(JavaFileObjects.forResource("com/github/mvp4g/mvp4g2/processor/event/eventTestHandlerNotInBindAndHandlersAttribute/Com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_EventTestHandlerNotInBindAndHandlersAttribute_event01.java"))
                         .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/mvp4g/mvp4g2/processor/event/eventTestHandlerNotInBindAndHandlersAttribute/Com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_EventTestHandlerNotInBindAndHandlersAttribute_event01.java"));
    JavaFileObjectSubject.assertThat(JavaFileObjects.forResource("com/github/mvp4g/mvp4g2/processor/event/eventTestHandlerNotInBindAndHandlersAttribute/Com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_EventTestHandlerNotInBindAndHandlersAttribute_event02.java"))
                         .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/mvp4g/mvp4g2/processor/event/eventTestHandlerNotInBindAndHandlersAttribute/Com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_EventTestHandlerNotInBindAndHandlersAttribute_event02.java"));
    JavaFileObjectSubject.assertThat(JavaFileObjects.forResource("com/github/mvp4g/mvp4g2/processor/event/eventTestHandlerNotInBindAndHandlersAttribute/Com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_EventTestHandlerNotInBindAndHandlersAttribute_event03.java"))
                         .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/mvp4g/mvp4g2/processor/event/eventTestHandlerNotInBindAndHandlersAttribute/Com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_EventTestHandlerNotInBindAndHandlersAttribute_event03.java"));
    JavaFileObjectSubject.assertThat(JavaFileObjects.forResource("com/github/mvp4g/mvp4g2/processor/event/eventTestHandlerNotInBindAndHandlersAttribute/Com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_MockOneEventHandlerMetaData.java"))
                         .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/mvp4g/mvp4g2/processor/event/eventTestHandlerNotInBindAndHandlersAttribute/Com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_MockOneEventHandlerMetaData.java"));
    JavaFileObjectSubject.assertThat(JavaFileObjects.forResource("com/github/mvp4g/mvp4g2/processor/event/eventTestHandlerNotInBindAndHandlersAttribute/Com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_MockShellPresenterMetaData.java"))
                         .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/mvp4g/mvp4g2/processor/event/eventTestHandlerNotInBindAndHandlersAttribute/Com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_MockShellPresenterMetaData.java"));
    JavaFileObjectSubject.assertThat(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventTestHandlerNotInBindAndHandlersAttribute/EventTestHandlerNotInBindAndHandlersAttributeImpl.java"))
                         .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventTestHandlerNotInBindAndHandlersAttribute/EventTestHandlerNotInBindAndHandlersAttributeImpl.java"));
  }

  @Test
  public void testEventTestPasiveEventWithBindAttribute() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventTestPasiveEventWithBindAttribute/EventTestPasiveEventWithBindAttribute.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("a passive event can not have a bind-attribute");
  }


  @Test
  public void testEventBusWithMoreThanOneInitHistoryAnnodation() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventBusWithMoreThanOneInitHistoryAnnodation/EventBusWithMoreThanOneInitHistoryAnnodation.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventBusWithMoreThanOneInitHistoryAnnodation/MockShellPresenter.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@InitHistory can only be set a single time inside a event bus");
  }

  @Test
  public void testEventBusWithMoreThanOnNotFoundHistoryAnnodation() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventBusWithMoreThanOnNotFoundHistoryAnnodation/EventBusWithMoreThanOnNotFoundHistoryAnnodation.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventBusWithMoreThanOnNotFoundHistoryAnnodation/MockShellPresenter.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@NotFoundHistory can only be set a single time inside a event bus");
  }

  @Test
  public void testEventBusWithOneInitHistoryAnnotationAndNonZeroArgmentsSignature() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventBusWithOneInitHistoryAnnotationAndNonZeroArgmentsSignature/EventBusWithOneInitHistoryAnnotationAndNonZeroArgmentsSignature.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventBusWithOneInitHistoryAnnotationAndNonZeroArgmentsSignature/MockShellPresenter.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@InitHistory can only be used on a method with no arguments");
  }

  @Test
  public void testEventBusWithOneNotFoundHistoryAnnotationAndNonZeroArgmentsSignature() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventBusWithOneNotFoundHistoryAnnotationAndNonZeroArgmentsSignature/EventBusWithOneNotFoundHistoryAnnotationAndNonZeroArgmentsSignature.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventBusWithOneNotFoundHistoryAnnotationAndNonZeroArgmentsSignature/MockShellPresenter.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@NotFoundHistory can only be used on a method with no arguments");
  }

  @Test
  public void testEventDoesNotReturnVoid() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventDoesNotReturnVoid/EventBusEventDoesNotReturnVoid.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventDoesNotReturnVoid/MockShellPresenter01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventDoesNotReturnVoid/IMockShellView01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventDoesNotReturnVoid/MockShellView01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("NaluProcessor: EventElement: >>doSomething<< must return 'void'");
  }

  @Test
  public void testEventAnnotationInsideAOtherClassThenEventBus() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventAnnotationInsideAOtherClassThenEventBus/EventAnnotationInsideAOtherClassThenEventBus.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventAnnotationInsideAOtherClassThenEventBus/MockShellPresenter.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventAnnotationInsideAOtherClassThenEventBus/IMockShellView.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/eventAnnotationInsideAOtherClassThenEventBus/MockShellView.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("NaluProcessor: @Event can only be used inside a event bus! >>MockShellPresenter<< does no implement IsEventBus");
  }
}
