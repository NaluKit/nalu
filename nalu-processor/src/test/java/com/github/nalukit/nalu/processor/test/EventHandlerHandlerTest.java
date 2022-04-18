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

package com.github.nalukit.nalu.processor.test;

import com.github.nalukit.nalu.processor.NaluProcessor;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static com.google.testing.compile.Compiler.javac;

public class EventHandlerHandlerTest {

  @Test
  void testEventHandlerOnAHandlerOk() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/handler/eventhandler/eventhandlerOnAHandlerOK/EventHandlerOnAHandlerOk.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/handler/eventhandler/eventhandlerOnAHandlerOK/ApplicationWithHandlerOk.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockErrorShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/handler/eventhandler/eventhandlerOnAHandlerOK/ApplicationWithHandlerOkImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/handler/eventhandler/eventhandlerOnAHandlerOK/ApplicationWithHandlerOkImpl.java"));
  }

  @Test
  void testTwoEventHandlerOnAHandlerOk() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/handler/eventhandler/twoEventhandlerOnAHandlerOk/TwoEventHandlerOnAHandlerOk.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/handler/eventhandler/twoEventhandlerOnAHandlerOk/ApplicationWithHandlerOk.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/handler/eventhandler/twoEventhandlerOnAHandlerOk/ApplicationWithHandlerOkImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/handler/eventhandler/twoEventhandlerOnAHandlerOk/ApplicationWithHandlerOkImpl.java"));
  }

  @Test
  void testTwoEventHandlerOnAHandlerOk02() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/handler/eventhandler/twoEventhandlerOnAHandlerOk02/TwoEventHandlerOnAHandlerOk.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/handler/eventhandler/twoEventhandlerOnAHandlerOk02/ApplicationWithHandlerOk.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/handler/eventhandler/twoEventhandlerOnAHandlerOk02/ApplicationWithHandlerOkImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/handler/eventhandler/twoEventhandlerOnAHandlerOk02/ApplicationWithHandlerOkImpl.java"));
  }

  @Test
  void testTwoEventHandlerOnAHandlerOk03() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/handler/eventhandler/twoHandler/HandlerOk01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/handler/eventhandler/twoHandler/HandlerOk02.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/handler/eventhandler/twoHandler/HandlerOk03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/handler/eventhandler/twoHandler/ApplicationWithHandlerOk.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/handler/eventhandler/twoHandler/ApplicationWithHandlerOkImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/handler/eventhandler/twoHandler/ApplicationWithHandlerOkImpl.java"));
  }

  @Test
  void testErrorEventHandlerMethodWithoutParameter() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/handler/eventhandler/eventHandlerMethodWithoutParameter/EventHandlerMethodWithoutParameter.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("NaluProcessor: @EventHandler -> method >> onMockEvent01<< should have only one parameter and that should be an event");
  }

  @Test
  void testErrorEventHandlerMethodWithTwoParameter() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/handler/eventhandler/eventHandlerMethodWithTwoParameter/EventHandlerMethodWithTwoParameter.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("NaluProcessor: @EventHandler -> method >> onMockEvent01<< should have only one parameter and that should be an event");
  }

  @Test
  void testErrorEventHandlerMethodWithWrongParameter() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/handler/eventhandler/eventHandlerWithWrongParameter/EventHandleWithWrongParameter.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("NaluProcessor: class >>java.lang.String<< - method >>onMockEvent01<< has wrong data type. Parameter needs to extend org.gwtproject.event.shared.Event");
  }

}
