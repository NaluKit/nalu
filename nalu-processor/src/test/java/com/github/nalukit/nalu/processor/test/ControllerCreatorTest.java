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
public class ControllerCreatorTest {

  @Test
  void testControllerCreatorOkWithoutParameter() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithoutParameter/ControllerC01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithoutParameter/IComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithoutParameter/Component01.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithoutParameter/ControllerC01CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithoutParameter/ControllerC01CreatorImpl.java"));
  }

  @Test
  void testControllerCreatorOkWithOneParameter() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithOneParameter01/ControllerC02.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithOneParameter01/IComponent02.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithOneParameter01/Component02.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithOneParameter01/ControllerC02CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithOneParameter01/ControllerC02CreatorImpl.java"));
  }

  @Test
  void testControllerCreatorOkWithOneParameterWithoutAcceptParameter() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithOneParameter01WithoutAcceptParameter/ControllerC03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithOneParameter01WithoutAcceptParameter/IComponent03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithOneParameter01WithoutAcceptParameter/Component03.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithOneParameter01WithoutAcceptParameter/ControllerC03CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithOneParameter01WithoutAcceptParameter/ControllerC03CreatorImpl.java"));
  }

  @Test
  void testControllerCreatorOkWithTwoParameter() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithTwoParameter01/ControllerC04.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithTwoParameter01/IComponent04.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithTwoParameter01/Component04.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithTwoParameter01/ControllerC04CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithTwoParameter01/ControllerC04CreatorImpl.java"));
  }

  @Test
  void testControllerCreatorOkWithTwoParameterWithoutParameterOne() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithTwoParameter03/ControllerC06.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithTwoParameter03/IComponent06.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithTwoParameter03/Component06.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithTwoParameter03/ControllerC06CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithTwoParameter03/ControllerC06CreatorImpl.java"));
  }

  @Test
  void testControllerCreatorOkWithTwoParameterWithoutParameterOneAndTwo() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithTwoParameter04/ControllerC07.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithTwoParameter04/IComponent07.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithTwoParameter04/Component07.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithTwoParameter04/ControllerC07CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/controllerCreator/controllerCreatorOkWithTwoParameter04/ControllerC07CreatorImpl.java"));
  }

}
