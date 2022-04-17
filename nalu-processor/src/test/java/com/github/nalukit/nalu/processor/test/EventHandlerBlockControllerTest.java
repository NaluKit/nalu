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

@SuppressWarnings("serial")
public class EventHandlerBlockControllerTest {

  @Test
  void testEventHandlerOnABlockControllerOk01() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockcontroller/eventhandler/eventHandlerOnABlockControllerOk01/BlockControllerEventHandler01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockcontroller/eventhandler/eventHandlerOnABlockControllerOk01/TestApplication.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockcontroller/eventhandler/eventHandlerOnABlockControllerOk01/content/Content01Controller.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockcontroller/eventhandler/eventHandlerOnABlockControllerOk01/content/Content01Component.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockcontroller/eventhandler/eventHandlerOnABlockControllerOk01/content/IContent01Component.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockErrorShell.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/blockController/eventhandler/eventHandlerOnABlockControllerOk01/BlockControllerEventHandler01CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockController/eventhandler/eventHandlerOnABlockControllerOk01/BlockControllerEventHandler01CreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/blockController/eventhandler/eventHandlerOnABlockControllerOk01/TestApplicationImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockController/eventhandler/eventHandlerOnABlockControllerOk01/TestApplicationImpl.java"));
  }

  @Test
  void testEventHandlerOnABlockControllerOk02() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockcontroller/eventhandler/eventHandlerOnABlockControllerOk02/BlockControllerEventHandler01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockcontroller/eventhandler/eventHandlerOnABlockControllerOk02/TestApplication.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockcontroller/eventhandler/eventHandlerOnABlockControllerOk02/content/Content01Controller.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockcontroller/eventhandler/eventHandlerOnABlockControllerOk02/content/Content01Component.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockcontroller/eventhandler/eventHandlerOnABlockControllerOk02/content/IContent01Component.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockErrorShell.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/blockController/eventhandler/eventHandlerOnABlockControllerOk02/TestApplicationImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockController/eventhandler/eventHandlerOnABlockControllerOk02/TestApplicationImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/blockController/eventhandler/eventHandlerOnABlockControllerOk02/BlockControllerEventHandler01CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockController/eventhandler/eventHandlerOnABlockControllerOk02/BlockControllerEventHandler01CreatorImpl.java"));
  }

  @Test
  void testEventHandlerOnABlockControllerOk03() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockcontroller/eventhandler/eventHandlerOnABlockControllerOk03/BlockControllerEventHandler01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockcontroller/eventhandler/eventHandlerOnABlockControllerOk03/BlockControllerEventHandler02.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockcontroller/eventhandler/eventHandlerOnABlockControllerOk03/BlockControllerEventHandler03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockcontroller/eventhandler/eventHandlerOnABlockControllerOk03/TestApplication.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockcontroller/eventhandler/eventHandlerOnABlockControllerOk03/content/Content01Controller.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockcontroller/eventhandler/eventHandlerOnABlockControllerOk03/content/Content01Component.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockcontroller/eventhandler/eventHandlerOnABlockControllerOk03/content/IContent01Component.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockErrorShell.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/blockController/eventhandler/eventHandlerOnABlockControllerOk03/TestApplicationImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockController/eventhandler/eventHandlerOnABlockControllerOk03/TestApplicationImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/blockController/eventhandler/eventHandlerOnABlockControllerOk03/BlockControllerEventHandler01CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockController/eventhandler/eventHandlerOnABlockControllerOk03/BlockControllerEventHandler01CreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/blockController/eventhandler/eventHandlerOnABlockControllerOk03/BlockControllerEventHandler02CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockController/eventhandler/eventHandlerOnABlockControllerOk03/BlockControllerEventHandler02CreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/blockController/eventhandler/eventHandlerOnABlockControllerOk03/BlockControllerEventHandler03CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockController/eventhandler/eventHandlerOnABlockControllerOk03/BlockControllerEventHandler03CreatorImpl.java"));
  }

  @Test
  void testErrorEventHandlerMethodWithoutParameter() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockcontroller/eventhandler/eventHandlerMethodWithoutParameter/BlockControllerEventHandler01.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("NaluProcessor: @EventHandler -> method >> onMockEvent01<< should have only one parameter and that should be an event");
  }

  @Test
  void testErrorEventHandlerMethodWithTwoParameter() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockcontroller/eventhandler/eventHandlerMethodWithTwoParameter/BlockControllerEventHandler01.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("NaluProcessor: @EventHandler -> method >> onMockEvent01<< should have only one parameter and that should be an event");
  }

  @Test
  void testErrorEventHandlerMethodWithWrongParameter() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockcontroller/eventhandler/eventHandlerWithWrongParameter/BlockControllerEventHandler01.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("NaluProcessor: class >> java.lang.String<< - method >>onMockEvent01 has wrong data type. Parameter needs to extend org.gwtproject.event.shared.Event");
  }

}
