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

public class StartEventTest {

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
  public void testStartEventTestEventBusWithOneStartAnnotation() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/startEventTestEventBusWithOneStartAnnotation/StartEventTestEventBusWithOneStartAnnotation.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/startEventTestEventBusWithOneStartAnnotation/MockShellPresenter.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    JavaFileObjectSubject.assertThat(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/startEventTestEventBusWithOneStartAnnotation/StartEventTestEventBusWithOneStartAnnotationImpl.java"))
                         .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/event/startEventTestEventBusWithOneStartAnnotation/StartEventTestEventBusWithOneStartAnnotationImpl.java"));
    JavaFileObjectSubject.assertThat(JavaFileObjects.forResource("com/github/mvp4g/mvp4g2/processor/event/startEventTestEventBusWithOneStartAnnotation/Com_github_mvp4g_mvp4g2_processor_event_startEventTestEventBusWithOneStartAnnotation_MockShellPresenterMetaData.java"))
                         .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/mvp4g/mvp4g2/processor/event/startEventTestEventBusWithOneStartAnnotation/Com_github_mvp4g_mvp4g2_processor_event_startEventTestEventBusWithOneStartAnnotation_MockShellPresenterMetaData.java"));
    JavaFileObjectSubject.assertThat(JavaFileObjects.forResource("com/github/mvp4g/mvp4g2/processor/event/startEventTestEventBusWithOneStartAnnotation/Com_github_mvp4g_mvp4g2_processor_event_startEventTestEventBusWithOneStartAnnotation_StartEventTestEventBusWithOneStartAnnotation_start.java"))
                         .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/mvp4g/mvp4g2/processor/event/startEventTestEventBusWithOneStartAnnotation/Com_github_mvp4g_mvp4g2_processor_event_startEventTestEventBusWithOneStartAnnotation_StartEventTestEventBusWithOneStartAnnotation_start.java"));
  }
}
