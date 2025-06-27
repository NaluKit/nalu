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

package io.github.nalukit.nalu.processor.test;

import io.github.nalukit.nalu.processor.NaluProcessor;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.google.testing.compile.Compiler.javac;

@SuppressWarnings("serial")
public class ErrorPopUpControllerTest {

  @Test
  void testControllerCreatorOk() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("io/github/nalukit/nalu/processor/errorPopUpController/errorPopUpAnnotationOk/ErrorPopUpAnnotationOk.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/MockErrorShell.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/component01/Component01.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/errorPopUp01/ErrorEventController01.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/errorPopUp01/IErrorEventComponent01.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/errorPopUp01/ErrorEventComponent01.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("io/github/nalukit/nalu/processor/errorPopUpController/errorPopUpAnnotationOk/ErrorPopUpAnnotationOkImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("io/github/nalukit/nalu/processor/errorPopUpController/errorPopUpAnnotationOk/ErrorPopUpAnnotationOkImpl.java"));
  }

  @Test
  void testErrorPopUpAnnotationAbstractErrorPopUpComponentControllerWithoutgenerics() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("io/github/nalukit/nalu/processor/errorPopUpController/epuAnnoAbstractComConWithoutGenerics/EpuAnnoAbstractErPoUpComConWithoutgenerics.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/errorPopUp01/IErrorEventComponent01.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/errorPopUp01/ErrorEventComponent01.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: controller >>io.github.nalukit.nalu.processor.errorPopUpController.epuAnnoAbstractComConWithoutGenerics.EpuAnnoAbstractErPoUpComConWithoutgenerics<< does not have a context generic!");
  }

  @Test
  void testErrorPopUpAnnotationDoesNotExtendsAbstractErrorPopUpComponentController() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("io/github/nalukit/nalu/processor/errorPopUpController/epuAnnoDoesNotExtendsAbstractErPoUpComCon/EpuAnnoDoesNotExtendsAbstractErPoUpComCon.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/errorPopUp01/IErrorEventComponent01.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/errorPopUp01/ErrorEventComponent01.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@ErrorPopUpController can only be used on a class that extends AbstractErrorPopUpComponentController");
  }

  @Test
  void testErrorPopUpAnnotationOnAInterface() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("io/github/nalukit/nalu/processor/errorPopUpController/errorPopUpAnnotationOnAInterface/ErrorPopUpAnnotationOnAInterface.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/errorPopUp01/IErrorEventComponent01.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/errorPopUp01/ErrorEventComponent01.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: @ErrorPopUpController can only be used with an class");
  }

  @Test
  void testErrorPopUpAnnotationUsedTwoTimes() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/errorPopUp01/ErrorEventController01.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/errorPopUp01/IErrorEventComponent01.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/errorPopUp01/ErrorEventComponent01.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/errorPopUp02/ErrorEventController02.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/errorPopUp02/IErrorEventComponent02.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/errorPopUp02/ErrorEventComponent02.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: more than one class is annotated with @ErrorPopUpController");
  }

}
