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

import com.github.mvp4g.nalu.react.processor.NaluReactProcessor;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjectSubject;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import static com.google.testing.compile.Compiler.javac;

public class PresenterTest {

  @Test
  public void testPresenterAnnotationAnnotatedOnAbstractClass() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/mvp4g2/processor/eventhandler/presenterAnnotationAnnotatedOnAbstractClass" + "/PresenterAnnotationAnnotatedOnAbstractClass.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Presenter can not be ABSTRACT");
  }

  @Test
  public void testPresenterAnnotationAnnotatedOnAInterface() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterAnnotationAnnotatedOnAInterface/PresenterAnnotationAnnotatedOnAInterface.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Presenter can only be used with a class");
  }

  @Test
  public void testPresenterNotExtendingAbstractPresenter() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterAnnotationNotExtendingAbstractPresenter/PresenterAnnotationNotExtendingAbstractPresenter.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Presenter must extend AbstractPresenter.class");
  }

  @Test
  public void testPresenterAnnotationUsingViewClassAsInterface() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterAnnotationUsingViewClassAsInterface/PresenterAnnotationUsingViewClassAsInterface.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("the viewInterface-attribute of a @Presenter must be a interface!");
  }

  @Test
  public void testPresenterAnnotationUsingViewAbstractClass() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterAnnotationUsingViewAbstractClass/PresenterAnnotationUsingViewAbstractClass.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("class-attribute of @Presenter can not be ABSTRACT");
  }

  @Test
  public void testPresenterOK() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterOK/PresenterOK.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterOK/MockEventBus.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterOK/MockShellPresenter.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    JavaFileObjectSubject.assertThat(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterOK/Com_github_mvp4g_mvp4g2_processor_eventhandler_presenterOK_PresenterOKMetaData.java"))
                         .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterOK/Com_github_mvp4g_mvp4g2_processor_eventhandler_presenterOK_PresenterOKMetaData.java"));
  }

  @Test
  public void testEventHandlerWithUnusedEventHandlerImplementation() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlerWithUnusedEventHandlerImplementation/EventBusEventHandlerWithUnusedEventHandlerImplementation.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlerWithUnusedEventHandlerImplementation/MockShellPresenter01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlerWithUnusedEventHandlerImplementation/IMockShellView01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlerWithUnusedEventHandlerImplementation/MockShellView01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
  }

  @Test
  public void testEventHandlerWithNotImplementedEvent() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithNotImplementedEvent/EventBusEventHandlerWithNotImplementedEvent.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithNotImplementedEvent/MockShellPresenter01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithNotImplementedEvent/IMockShellView01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithNotImplementedEvent/MockShellView01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("event >>doSomething()<< is never handled by a presenter or handler");
  }

  @Test
  public void testPresenterWithWrongImplementation01() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithWrongImplementation01/EventBusHandlerWithNotImplementedEvent.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithWrongImplementation01/MockShellPresenter01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithWrongImplementation01/IMockShellView01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithWrongImplementation01/MockShellView01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("event >>doSomething()<< is never handled by a presenter or handler");
  }

  @Test
  public void testHandlerWithWrongImplementation02() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithWrongImplementation02/EventBusHandlerWithNotImplementedEvent.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithWrongImplementation02/MockShellPresenter01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithWrongImplementation02/IMockShellView01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithWrongImplementation02/MockShellView01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("event >>doSomething(java.lang.String)<< is never handled by a presenter or handler");
  }

  @Test
  public void testHandlerWithWrongImplementation03() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithWrongImplementation03/EventBusHandlerWithNotImplementedEvent.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithWrongImplementation03/MockShellPresenter01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithWrongImplementation03/IMockShellView01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithWrongImplementation03/MockShellView01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
  }

  @Test
  public void testHandlerWithWrongImplementation04() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithWrongImplementation04/EventBusHandlerWithNotImplementedEvent.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithWrongImplementation04/MockShellPresenter01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithWrongImplementation04/IMockShellView01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithWrongImplementation04/MockShellView01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
  }

  @Test
  public void testEventHandlingMethodDoesNotReturnVoid03() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlingMethodDoesNotReturnVoid03/EventBusEventHandlingMethodDoesNotReturnVoid.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlingMethodDoesNotReturnVoid03/MockShellPresenter01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlingMethodDoesNotReturnVoid03/IMockShellView01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlingMethodDoesNotReturnVoid03/MockShellView01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("NaluReactProcessor: >>MockShellPresenter01<< -> EventElement: >>onDoSomething()<< must return 'void'");
  }

  @Test
  public void testEventHandlingMethodDoesNotReturnVoid04() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlingMethodDoesNotReturnVoid04/EventBusEventHandlingMethodDoesNotReturnVoid.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlingMethodDoesNotReturnVoid04/MockShellPresenter01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlingMethodDoesNotReturnVoid04/IMockShellView01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventHandlingMethodDoesNotReturnVoid04/MockShellView01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("NaluReactProcessor: >>MockShellPresenter01<< -> EventElement: >>onDoSomething()<< must return 'void'");
  }

  /**
   * Check, that compilation works, if handler-attribute and EventHandler annotation is used for one event
   */
  @Test
  public void testEventBusEventhandlerWithHanderlsAttributeAndEventHandlerAnnotation() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventhandlerWithHanderlsAttributeAndEventHandlerAnnotation/EventBusEventhandlerWithHanderlsAttributeAndEventHandlerAnnotation.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventhandlerWithHanderlsAttributeAndEventHandlerAnnotation/MockShellPresenter01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventhandlerWithHanderlsAttributeAndEventHandlerAnnotation/IMockShellView01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventhandlerWithHanderlsAttributeAndEventHandlerAnnotation/MockShellView01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    JavaFileObjectSubject.assertThat(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventhandlerWithHanderlsAttributeAndEventHandlerAnnotation/EventBusEventhandlerWithHanderlsAttributeAndEventHandlerAnnotationImpl.java"))
                         .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/eventhandlerWithHanderlsAttributeAndEventHandlerAnnotation/EventBusEventhandlerWithHanderlsAttributeAndEventHandlerAnnotationImpl.java"));
  }

  @Test
  public void testPresenterWithMultipleAttribute01() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithMultipleAttribute01/EventBusPresenterWithMultipleAttibute01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithMultipleAttribute01/MockShellPresenter01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithMultipleAttribute01/IMockShellView01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithMultipleAttribute01/MockShellView01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithMultipleAttribute01/MockMultiplePresenter01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithMultipleAttribute01/IMockMultipleView01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithMultipleAttribute01/MockMultipleView01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
  }

  // missng IsViewCerator-interface
  @Test
  public void testPresenterWithViewCreationMethodPresenter01() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter01/EventBusPresenterWithViewCreationMethodPresenter01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter01/MockShellPresenter01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter01/IMockShellView01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter01/MockShellView01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter01/MockPresenter01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter01/IMockView01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter01/MockView01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Presenter must implement the IsViewCreator interface");
  }

  // ok
  @Test
  public void testPresenterWithViewCreationMethodPresenter02() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter02/EventBusPresenterWithViewCreationMethodPresenter02.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter02/MockShellPresenter02.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter02/IMockShellView02.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter02/MockShellView02.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter02/MockPresenter02.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter02/IMockView02.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter02/MockView02.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
  }

  // IsViewCreator interface without generic
  @Test
  public void testPresenterWithViewCreationMethodPresenter03() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter03/EventBusPresenterWithViewCreationMethodPresenter03.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter03/MockShellPresenter03.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter03/IMockShellView03.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter03/MockShellView03.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter03/MockPresenter03.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter03/IMockView03.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter03/MockView03.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("IsViewCreator interface needs a generic parameter");
  }

  // IsViewCreator interface used without generic viewCreator = Presenter.VIEW_CREATION_METHOD.PRESENTER
  @Test
  public void testPresenterWithViewCreationMethodPresenter04() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter04/EventBusPresenterWithViewCreationMethodPresenter04.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter04/MockShellPresenter04.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter04/IMockShellView04.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter04/MockShellView04.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter04/MockPresenter04.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter04/IMockView04.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter04/MockView04.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("the IsViewCreator interface can only be used in case of viewCreator = Presenter.VIEW_CREATION_METHOD.PRESENTER");
  }

  // IsViewCreator: check that the generic parameter is the view interface
  @Test
  public void testPresenterWithViewCreationMethodPresenter05() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter05/EventBusPresenterWithViewCreationMethodPresenter05.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter05/MockShellPresenter05.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter05/IMockShellView05.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter05/MockShellView05.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter05/MockPresenter05.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter05/IMockView05.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterWithViewCreationMethodPresenter05/MockView05.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("IsViewCreator interface only allows the generic parameter ->");
  }

  // a shell presenter can not implement is multiple!
  @Test
  public void testPresenterIsShellAndIsMultipleNotOk() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterIsShellAndIsMultipleNotOk/EventBusPresenterIsShellAndIsMultipleNotOk.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterIsShellAndIsMultipleNotOk/MockShellPresenter01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterIsShellAndIsMultipleNotOk/IMockShellView01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/eventhandler/presenterIsShellAndIsMultipleNotOk/MockShellView01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("IsShell interface can not be used on a presenter which is defiend as multiple = true");
  }






  //  @Test
  //  public void testPresenterWithMultipleAttribute02() {
  //    ASSERT.about(javaSources())
  //          .that(
  //            new ArrayList<JavaFileObject>() {
  //              {
  //                add(JavaFileObjects.forResource("com/github/mvp4g/mvp4g2/processor/eventhandler/presenterWithMultipleAttribute02/EventBusPresenterWithMultipleAttibute02.java"));
  //                add(JavaFileObjects.forResource("com/github/mvp4g/mvp4g2/processor/eventhandler/presenterWithMultipleAttribute02/MockShellPresenter01.java"));
  //                add(JavaFileObjects.forResource("com/github/mvp4g/mvp4g2/processor/eventhandler/presenterWithMultipleAttribute02/IMockShellView01.java"));
  //                add(JavaFileObjects.forResource("com/github/mvp4g/mvp4g2/processor/eventhandler/presenterWithMultipleAttribute02/MockShellView01.java"));
  //                add(JavaFileObjects.forResource("com/github/mvp4g/mvp4g2/processor/eventhandler/presenterWithMultipleAttribute02/MockMultiplePresenter01.java"));
  //                add(JavaFileObjects.forResource("com/github/mvp4g/mvp4g2/processor/eventhandler/presenterWithMultipleAttribute02/IMockMultipleView01.java"));
  //                add(JavaFileObjects.forResource("com/github/mvp4g/mvp4g2/processor/eventhandler/presenterWithMultipleAttribute02/MockMultipleView01.java"));
  //                add(JavaFileObjects.forResource("com/github/mvp4g/mvp4g2/processor/eventhandler/presenterWithMultipleAttribute02/MockMultiplePresenter02.java"));
  //                add(JavaFileObjects.forResource("com/github/mvp4g/mvp4g2/processor/eventhandler/presenterWithMultipleAttribute02/IMockMultipleView02.java"));
  //                add(JavaFileObjects.forResource("com/github/mvp4g/mvp4g2/processor/eventhandler/presenterWithMultipleAttribute02/MockMultipleView02.java"));
  //              }
  //            })
  //          .processedWith(new NaluReactProcessor())
  //          .compilesWithoutError();
  //  }
}
