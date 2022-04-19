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

public class EventHandlerPopUpControllerTest {


  @Test
  void testEventHandlerOnAControllerOk01() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk01/TestApplication.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk01/content/Controller01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk01/content/Component01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk01/content/IComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk01/popUp/PopUpController01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk01/popUp/PopUpComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk01/popUp/IPopUpComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockErrorShell.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk01/popUp/PopUpController01CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk01/popUp/PopUpController01CreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk01/content/Controller01CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk01/content/Controller01CreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk01/TestApplicationImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk01/TestApplicationImpl.java"));
  }

//  @Test
//  void testEventHandlerOnAControllerOk02() {
//    Compilation compilation = javac().withProcessors(new NaluProcessor())
//                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk02/TestApplication.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk02/content/Controller01.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk02/content/Component01.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk02/content/IComponent01.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockErrorShell.java")));
//    CompilationSubject.assertThat(compilation)
//                      .succeeded();
//    CompilationSubject.assertThat(compilation)
//                      .generatedSourceFile("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk02/content/Controller01CreatorImpl")
//                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk02/content/Controller01CreatorImpl.java"));
//    CompilationSubject.assertThat(compilation)
//                      .generatedSourceFile("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk02/TestApplicationImpl")
//                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk02/TestApplicationImpl.java"));
//  }
//
//  @Test
//  void testEventHandlerOnAControllerOk03() {
//    Compilation compilation = javac().withProcessors(new NaluProcessor())
//                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk03/TestApplication.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk03/content/Controller01.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk03/content/Component01.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk03/content/IComponent01.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk03/content/Controller02.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk03/content/Component02.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk03/content/IComponent02.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk03/content/Controller03.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk03/content/Component03.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk03/content/IComponent03.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
//                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockErrorShell.java")));
//    CompilationSubject.assertThat(compilation)
//                      .succeeded();
//    CompilationSubject.assertThat(compilation)
//                      .generatedSourceFile("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk03/content/Controller01CreatorImpl")
//                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk03/content/Controller01CreatorImpl.java"));
//    CompilationSubject.assertThat(compilation)
//                      .generatedSourceFile("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk03/content/Controller02CreatorImpl")
//                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk03/content/Controller02CreatorImpl.java"));
//    CompilationSubject.assertThat(compilation)
//                      .generatedSourceFile("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk03/content/Controller03CreatorImpl")
//                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk03/content/Controller03CreatorImpl.java"));
//    CompilationSubject.assertThat(compilation)
//                      .generatedSourceFile("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk03/TestApplicationImpl")
//                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerOnAPopUpControllerOk03/TestApplicationImpl.java"));
//  }

  @Test
  void testErrorEventHandlerMethodWithoutParameter() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerMethodWithoutParameter/PopUpController01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerMethodWithoutParameter/PopUpComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerMethodWithoutParameter/IPopUpComponent01.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("NaluProcessor: @EventHandler -> method >> onMockEvent01<< should have only one parameter and that should be an event");
  }

  @Test
  void testErrorEventHandlerMethodWithTwoParameter() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerMethodWithTwoParameter/PopUpController01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerMethodWithTwoParameter/PopUpComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerMethodWithTwoParameter/IPopUpComponent01.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("NaluProcessor: @EventHandler -> method >> onMockEvent01<< should have only one parameter and that should be an event");
  }

  @Test
  void testErrorEventHandlerMethodWithWrongParameter() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerWithWrongParameter/PopUpController01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerWithWrongParameter/PopUpComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/popUpControllerCreator/eventhandler/eventHandlerWithWrongParameter/IPopUpComponent01.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("NaluProcessor: class >>java.lang.String<< - method >>onMockEvent01<< has wrong data type. Parameter needs to extend org.gwtproject.event.shared.Event");
  }

}
