/*
 * Copyright (c) 2018 Frank Hossfeld
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
public class HandlerTest {

  @Test
  void testHandlerAnnotationOnAClass() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/handler/handlerAnnotationOnAClass/HandlerAnnotationOnAClass.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
  }

  @Test
  void testHandlerAnnotationOnAInterface() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/handler/handlerAnnotationOnAInterface/HandlerAnnotationOnAInterface.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: @Handler can only be used with an class");
  }

  @Test
  void testHandlerAnnotationOnAClassThatDoesNotExtendIsHandler() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/handler/handlerAnnotationOnAClassThatDoesNotExtendIsHandler/HandlerAnnotationOnAClassThatDoesNotExtendIsHandler.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: @Handler can only be used on a class that implements IsHandler");
  }

  @Test
  void testHandlerAnnotationOnAClassThatDoesExtendIsHandler() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/handler/handlerAnnotationOnAClassThatExtendIsHandler/HandlerAnnotationOnAClassThatDoesExtendIsHandler.java")));

    CompilationSubject.assertThat(compilation)
                      .succeeded();
  }

  @Test
  void testHandlerAnnotationOnAClassThatDoesNotExtendAbstractHandler() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/handler/handlerAnnotationOnAClassThatDoesNotExtendAbstractHandler/HandlerAnnotationOnAClassThatDoesNotExtendAbstractHandler.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: @Handler can only be used on a class that extends AbstractHandler");
  }

  @Test
  void testHandlerAnnotationOnAClassThatDoesExtendAbstractHandler() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/handler/handlerAnnotationOnAClassThatExtendAbstractHandler/HandlerAnnotationOnAClass.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
  }

}
