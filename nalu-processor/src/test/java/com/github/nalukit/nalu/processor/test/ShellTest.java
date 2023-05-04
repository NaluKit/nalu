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

@SuppressWarnings("serial")
public class ShellTest {

  @Test
  void testShellOnAInterface() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellOnAInterface/ShellOnAInterface.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: @Shell annotation must be used with a class");
  }

  @Test
  void testShellDoesNotExtendAbstractShell() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellDoesNotExtendAbstractShell01/ShellDoesNotExtendAbstractShell.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: ShellDoesNotExtendAbstractShell: @Shells must extend IsShell interface");
  }

  @Test
  void testShellDoesNotHaveGenericContext() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellDoesNotHaveGenericContext/ShellDoesNotHaveGenericContext.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: shellCreator >>com.github.nalukit.nalu.processor.shellCreator.shellDoesNotHaveGenericContext.ShellDoesNotHaveGenericContext<< does not have a context generic!");
  }

  @Test
  void testShellWithComposite01() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite01/ShellWithCompositeApplication.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite01/ShellWithComposite.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite01/MockComposite.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite01/IMockCompositeComponent.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite01/MockCompositeComponent.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite01/ControllerWithComposite01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite01/IComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite01/Component01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite01/composite/CompositeController01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite01/composite/ICompositeComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite01/composite/CompositeComponent01.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/shell/shellWithComposite01/ShellWithCompositeCreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite01/ShellWithCompositeCreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/shell/shellWithComposite01/ControllerWithComposite01CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite01/ControllerWithComposite01CreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/shell/shellWithComposite01/ShellWithCompositeApplicationImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite01/ShellWithCompositeApplicationImpl.java"));
  }

  @Test
  void testShellWithComposite02() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite02/ShellWithCompositeApplication.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite02/ShellWithComposite.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite02/MockCompositeCondition.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite02/MockComposite.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite02/IMockCompositeComponent.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite02/MockCompositeComponent.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite02/ControllerWithComposite02.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite02/IComponent02.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite02/Component02.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite02/composite/CompositeController02.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite02/composite/ICompositeComponent02.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite02/composite/CompositeComponent02.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/shell/shellWithComposite02/ShellWithCompositeCreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite02/ShellWithCompositeCreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/shell/shellWithComposite02/ShellWithCompositeApplicationImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite02/ShellWithCompositeApplicationImpl.java"));
  }

  @Test
  void testShellWithComposite03() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite03/ShellWithCompositeApplication.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite03/ShellWithComposite.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite03/ControllerWithComposite03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite03/IComponent03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite03/Component03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite03/composite/CompositeController03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite03/composite/ICompositeComponent03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite03/composite/CompositeComponent03.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/shell/shellWithComposite03/ShellWithCompositeCreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite03/ShellWithCompositeCreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/shell/shellWithComposite03/ShellWithCompositeApplicationImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite03/ShellWithCompositeApplicationImpl.java"));
  }

  @Test
  void testShellWithComposite04() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite04/ShellWithCompositeApplication.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite04/ShellWithComposite.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite04/MockComposite.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite04/IMockCompositeComponent.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite04/MockCompositeComponent.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite04/ControllerWithComposite04.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite04/IComponent04.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite04/Component04.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/shell/shellWithComposite04/ShellWithCompositeCreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite04/ShellWithCompositeCreatorImpl.java"));
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/shell/shellWithComposite04/ShellWithCompositeApplicationImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/shellWithComposite04/ShellWithCompositeApplicationImpl.java"));
  }

}
