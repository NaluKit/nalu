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

import java.util.Collections;

import static com.google.testing.compile.Compiler.javac;

@SuppressWarnings("serial")
public class DebugTest {
  
  @Test
  void testDebugAnnotationOnAMethod() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/debug/debugAnnotationOnAMethod/DebugAnnotationOnAMethod.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Debug can only be used on a type (interface)");
  }
  
  @Test
  void testDebugAnnotationOnAClass() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/debug/debugAnnotationOnAClass/DebugAnnotationOnAClass.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: @Debug can only be used on a type (interface)");
  }
  
  @Test
  void testDebugAnnotationWithoutExtendsIsApplication() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/debug/debugAnnotationWithoutExtendsIsApplication/DebugAnnotationWithoutExtendsIsApplication.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: @Debug can only be used on interfaces that extends IsApplication");
  }
  
  @Test
  void testDebugAnnotationOnClassWithoutApplicationAnnotation() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/debug/debugAnnotationOnClassWithoutApplicationAnnotation/DebugAnnotationOnClassWithoutApplicationAnnotation.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: @Debug can only be used with an interfaces annotated with @Application");
  }
  
}
