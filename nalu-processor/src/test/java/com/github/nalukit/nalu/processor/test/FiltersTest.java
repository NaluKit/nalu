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
public class FiltersTest {

  @Test
  void testFiltersAnnotationOnAMethod() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/filter/filterAnnotationOnAMethod/FilterAnnotationOnAMethod.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: @Filters can only be used on a type (interface)");
  }

  @Test
  void testApplicationWithFilterWithEventHandler() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/filter/eventhandler/eventHandlerOnAFilterOk01/ApplicationWithFilter.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/filter/eventhandler/eventHandlerOnAFilterOk01/MockFilter.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/filter/eventhandler/eventHandlerOnAFilterOk01/ApplicationWithFilterImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/filter/eventhandler/eventHandlerOnAFilterOk01/ApplicationWithFilterImpl.java"));
  }

  @Test
  void testApplicationWithFilterWithEventHandlerAndTwoEvents() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/filter/eventhandler/eventHandlerOnAFilterOk02/ApplicationWithFilter.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/filter/eventhandler/eventHandlerOnAFilterOk02/MockFilter.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/filter/eventhandler/eventHandlerOnAFilterOk02/ApplicationWithFilterImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/filter/eventhandler/eventHandlerOnAFilterOk02/ApplicationWithFilterImpl.java"));
  }

  @Test
  void testApplicationWithTwoFiltersWithEventHandler() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/filter/eventhandler/eventHandlerOnTwoFiltersOk01/ApplicationWithFilter.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/filter/eventhandler/eventHandlerOnTwoFiltersOk01/MockFilter.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/filter/eventhandler/eventHandlerOnTwoFiltersOk01/MockFilter02.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/filter/eventhandler/eventHandlerOnTwoFiltersOk01/ApplicationWithFilterImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/filter/eventhandler/eventHandlerOnTwoFiltersOk01/ApplicationWithFilterImpl.java"));
  }

  @Test
  void testApplicationWithFilterWithEventHandlerWithoutParameter() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/filter/eventhandler/eventHandlerMethodWithoutParameter/ApplicationWithFilter.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/filter/eventhandler/eventHandlerMethodWithoutParameter/MockFilter.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("NaluProcessor: @EventHandler -> method >> handleEvent<< should have only one parameter and that should be an event");
  }

  @Test
  void testApplicationWithFilterWithEventHandlerWithTwoParameter() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/filter/eventhandler/eventHandlerMethodWithTwoParameter/ApplicationWithFilter.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/filter/eventhandler/eventHandlerMethodWithTwoParameter/MockFilter.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("NaluProcessor: @EventHandler -> method >> handleEvent<< should have only one parameter and that should be an event");
  }

  @Test
  void testApplicationWithFilterWithEventHandlerWithWrongParameter() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/filter/eventhandler/eventHandlerWithWrongParameter/ApplicationWithFilter.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/filter/eventhandler/eventHandlerWithWrongParameter/MockFilter.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("NaluProcessor: class >>java.lang.String<< - method >>handleEvent<< has wrong data type. Parameter needs to extend org.gwtproject.event.shared.Event");
  }

}
