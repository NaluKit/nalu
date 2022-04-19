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

import static com.google.testing.compile.Compiler.javac;

@SuppressWarnings("serial")
public class EventHandlerCompositeTest {

  @Test
  void testEventHandlerOnACompositeOk01() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk01/TestApplication.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk01/content/composite/Composite01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk01/content/composite/CompositeComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk01/content/composite/ICompositeComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk01/content/Controller01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk01/content/Component01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk01/content/IComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockErrorShell.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk01/content/composite/Composite01CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk01/content/composite/Composite01CreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk01/content/Controller01CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk01/content/Controller01CreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk01/TestApplicationImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk01/TestApplicationImpl.java"));
  }

  @Test
  void testEventHandlerOnACompositeOk02() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk02/TestApplication.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk02/content/composite/Composite01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk02/content/composite/CompositeComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk02/content/composite/ICompositeComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk02/content/Controller01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk02/content/Component01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk02/content/IComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockErrorShell.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk02/content/composite/Composite01CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk02/content/composite/Composite01CreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk02/content/Controller01CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk02/content/Controller01CreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk02/TestApplicationImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk02/TestApplicationImpl.java"));
  }

  @Test
  void testEventHandlerOnACompositeOk03() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk03/TestApplication.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk03/content/composite/Composite01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk03/content/composite/CompositeComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk03/content/composite/ICompositeComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk03/content/composite/Composite02.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk03/content/composite/CompositeComponent02.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk03/content/composite/ICompositeComponent02.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk03/content/composite/Composite03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk03/content/composite/CompositeComponent03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk03/content/composite/ICompositeComponent03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk03/content/Controller01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk03/content/Component01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk03/content/IComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockErrorShell.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk03/content/composite/Composite01CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk03/content/composite/Composite01CreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk03/content/composite/Composite02CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk03/content/composite/Composite02CreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk03/content/composite/Composite03CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk03/content/composite/Composite03CreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk03/content/Controller01CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk03/content/Controller01CreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk03/TestApplicationImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerOnACompositeOk03/TestApplicationImpl.java"));
  }

  @Test
  void testErrorEventHandlerMethodWithoutParameter() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerMethodWithoutParameter/Composite01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerMethodWithoutParameter/CompositeComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerMethodWithoutParameter/ICompositeComponent01.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("NaluProcessor: @EventHandler -> method >> onMockEvent01<< should have only one parameter and that should be an event");
  }

  @Test
  void testErrorEventHandlerMethodWithTwoParameter() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerMethodWithTwoParameter/Composite01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerMethodWithTwoParameter/CompositeComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerMethodWithTwoParameter/ICompositeComponent01.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("NaluProcessor: @EventHandler -> method >> onMockEvent01<< should have only one parameter and that should be an event");
  }

  @Test
  void testErrorEventHandlerMethodWithWrongParameter() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerWithWrongParameter/CompositeComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerWithWrongParameter/Composite01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/compositeCreator/eventhandler/eventHandlerWithWrongParameter/ICompositeComponent01.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("NaluProcessor: class >>java.lang.String<< - method >>onMockEvent01<< has wrong data type. Parameter needs to extend org.gwtproject.event.shared.Event");
  }

}
