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
public class LoggerTest {
  
  @Test
  void testLoggerAnnotationOnAMethod() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/logger/loggerAnnotationOnAMethod/LoggerAnnotationOnAMethod.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Logger can only be used on a type (interface)");
  }
  
  @Test
  void testLoggerAnnotationOnAClass() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/logger/loggerAnnotationOnAClass/LoggerAnnotationOnAClass.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Application annotated must be used with an interface");
  }
  
  @Test
  void testLoggerAnnotationWithoutExtendsIsApplication() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/logger/loggerAnnotationWithoutExtendsIsApplication/LoggerAnnotationWithoutExtendsIsApplication.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: LoggerAnnotationWithoutExtendsIsApplication: @Application must implement IsApplication interface");
  }
  
  @Test
  void testLoggerAnnotationOnClassWithoutApplicationAnnotation() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/logger/loggerAnnotationOnClassWithoutApplicationAnnotation/LoggerAnnotationOnClassWithoutApplicationAnnotation.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: @Logger can only be used with an interfaces annotated with @Application");
  }
  
  @Test
  void testLoggerAnnotationOk01() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/logger/loggerAnnotationOk01/LoggerAnnotationOk01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java")));
    
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/logger/loggerAnnotationOk01/LoggerAnnotationOk01Impl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/logger/loggerAnnotationOk01/LoggerAnnotationOk01Impl.java"));
  }
  
  @Test
  void testLoggerAnnotationOk02() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/logger/loggerAnnotationOk02/LoggerAnnotationOk02.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/logger/loggerAnnotationOk02/LoggerAnnotationOk02Impl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/logger/loggerAnnotationOk02/LoggerAnnotationOk02Impl.java"));
  }
  
}
