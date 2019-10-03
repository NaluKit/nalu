/*
 * Copyright (c) 2018 - 2019 - Frank Hossfeld
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

import com.github.nalukit.nalu.processor.NaluPluginGwtProcessor;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static com.google.testing.compile.Compiler.javac;

@SuppressWarnings("serial")
public class ProviderTest {

  @Test
  void testSelectorAnnotationOnAClass() {
    Compilation compilation = javac().withProcessors(new NaluPluginGwtProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/selectorOnAClass/SelectorAnnotationOnAClass.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
  }

  @Test
  void testSelectorAnnotationOnMethodWithOneParameter() {
    Compilation compilation = javac().withProcessors(new NaluPluginGwtProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/selectorOnAMethodWithOneParameter/SelectorAnnotationOnAMethodWithOneParameter.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
  }

  @Test
  void testSelectorAnnotationOnMethodWithTwoParameter() {
    Compilation compilation = javac().withProcessors(new NaluPluginGwtProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/selectorOnAMethodWithTwoParameter/SelectorAnnotationOnAMethodWithTwoParameter.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: @Selector can only be used with a method that has one parameter");
  }

  @Test
  void testParameterIsNotAWidget() {
    Compilation compilation = javac().withProcessors(new NaluPluginGwtProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/shell/parameterIsNotWidget/ParameterIsNotWidget.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: @Selector can only be used with a method that has one parameter and the parameter type is com.google.gwt.user.client.ui.IsWidget or com.google.gwt.user.client.ui.Widget");
  }

}
