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

package com.github.nalukit.nalu.processor.test;

import com.github.nalukit.nalu.processor.NaluProcessor;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static com.google.testing.compile.Compiler.javac;

public class EventHandlerShellTest {

  @Test
  void testEventHandlerOnShellCreatorOk01() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk01/TestApplication.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk01/error/ErrorController.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk01/error/ErrorComponent.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk01/error/IErrorComponent.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk01/content/Controller01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk01/content/Component01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk01/content/IComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk01/MockShell01.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk01/MockShell01CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk01/MockShell01CreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk01/content/Controller01CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk01/content/Controller01CreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk01/TestApplicationImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk01/TestApplicationImpl.java"));

  }

  @Test
  void testEventHandlerOnShellCreatorOk02() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk02/TestApplication.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk02/error/ErrorController.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk02/error/ErrorComponent.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk02/error/IErrorComponent.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk02/content/Controller01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk02/content/Component01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk02/content/IComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk02/MockShell01.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk02/MockShell01CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk02/MockShell01CreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk02/content/Controller01CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk02/content/Controller01CreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk02/TestApplicationImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk02/TestApplicationImpl.java"));

  }

  @Test
  void testEventHandlerOnShellCreatorOk03() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/TestApplication.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/error/ErrorController.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/error/ErrorComponent.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/error/IErrorComponent.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/content/Controller01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/content/Component01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/content/IComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/content/Controller02.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/content/Component02.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/content/IComponent02.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/content/Controller03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/content/Component03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/content/IComponent03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/MockShell01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/MockShell02.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/MockShell03.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/MockShell01CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/MockShell01CreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/MockShell02CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/MockShell02CreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/MockShell03CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/MockShell03CreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/content/Controller01CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/content/Controller01CreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/TestApplicationImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerOnAShellCreatorOk03/TestApplicationImpl.java"));

  }

//  @Test
//  void testTwoEventHandlerOnAHandlerOk02() {
//    Compilation compilation = javac().withProcessors(new NaluProcessor())
//                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/twoEventhandlerOnAHandlerOk02/TwoEventHandlerOnAHandlerOk.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/twoEventhandlerOnAHandlerOk02/ApplicationWithHandlerOk.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java")));
//    CompilationSubject.assertThat(compilation)
//                      .succeeded();
//    CompilationSubject.assertThat(compilation)
//                      .generatedSourceFile("com/github/nalukit/nalu/processor/shellCreator/eventhandler/twoEventhandlerOnAHandlerOk02/ApplicationWithHandlerOkImpl")
//                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/twoEventhandlerOnAHandlerOk02/ApplicationWithHandlerOkImpl.java"));
//  }
//
//  @Test
//  void testTwoEventHandlerOnAHandlerOk03() {
//    Compilation compilation = javac().withProcessors(new NaluProcessor())
//                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/twoHandler/HandlerOk01.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/twoHandler/HandlerOk02.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/twoHandler/HandlerOk03.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/twoHandler/ApplicationWithHandlerOk.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java")));
//    CompilationSubject.assertThat(compilation)
//                      .succeeded();
//    CompilationSubject.assertThat(compilation)
//                      .generatedSourceFile("com/github/nalukit/nalu/processor/shellCreator/eventhandler/twoHandler/ApplicationWithHandlerOkImpl")
//                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/twoHandler/ApplicationWithHandlerOkImpl.java"));
//  }

  @Test
  void testErrorEventHandlerMethodWithoutParameter() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerMethodWithoutParameter/MockShell01.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("NaluProcessor: @EventHandler -> method >> onMockEvent01<< should have only one parameter and that should be an event");
  }

  @Test
  void testErrorEventHandlerMethodWithTwoParameter() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerMethodWithTwoParameter/MockShell01.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("NaluProcessor: @EventHandler -> method >> onMockEvent01<< should have only one parameter and that should be an event");
  }

  @Test
  void testErrorEventHandlerMethodWithWrongParameter() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shellCreator/eventhandler/eventHandlerWithWrongParameter/MockShell01.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("NaluProcessor: class >>java.lang.String<< - method >>onMockEvent01<< has wrong data type. Parameter needs to extend org.gwtproject.event.shared.Event");
  }

}
