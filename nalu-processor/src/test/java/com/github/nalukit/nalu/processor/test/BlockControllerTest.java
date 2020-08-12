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
public class BlockControllerTest {
  // TOD implementiere Tests
  
  //  @Test
  //  void testControllerCreatorOk() {
  //    Compilation compilation = javac().withProcessors(new NaluProcessor())
  //                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/errorPopUpController/errorPopUpAnnotationOk/ErrorPopUpAnnotationOk.java"),
  //                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
  //                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockErrorShell.java"),
  //                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"),
  //                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"),
  //                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java"),
  //                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/errorPopUp01/ErrorEventController01.java"),
  //                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/errorPopUp01/IErrorEventComponent01.java"),
  //                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/errorPopUp01/ErrorEventComponent01.java")));
  //    CompilationSubject.assertThat(compilation)
  //                      .succeeded();
  //    CompilationSubject.assertThat(compilation)
  //                      .generatedSourceFile("com/github/nalukit/nalu/processor/errorPopUpController/errorPopUpAnnotationOk/ErrorPopUpAnnotationOkImpl")
  //                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/errorPopUpController/errorPopUpAnnotationOk/ErrorPopUpAnnotationOkImpl.java"));
  //  }
  //
  //  @Test
  //  void testErrorPopUpAnnotationAbstractErrorPopUpComponentControllerWithoutgenerics() {
  //    Compilation compilation = javac().withProcessors(new NaluProcessor())
  //                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/errorPopUpController/errorPopUpAnnotationAbstractErrorPopUpComponentControllerWithoutgenerics/ErrorPopUpAnnotationAbstractErrorPopUpComponentControllerWithoutgenerics.java"),
  //                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/errorPopUp01/IErrorEventComponent01.java"),
  //                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/errorPopUp01/ErrorEventComponent01.java")));
  //    CompilationSubject.assertThat(compilation)
  //                      .failed();
  //    CompilationSubject.assertThat(compilation)
  //                      .hadErrorContaining("Nalu-Processor: controller >>com.github.nalukit.nalu.processor.errorPopUpController.errorPopUpAnnotationAbstractErrorPopUpComponentControllerWithoutgenerics.ErrorPopUpAnnotationAbstractErrorPopUpComponentControllerWithoutgenerics<< does not have a context generic!");
  //  }
  //
  //  @Test
  //  void testErrorPopUpAnnotationDoesNotExtendsAbstractErrorPopUpComponentController() {
  //    Compilation compilation = javac().withProcessors(new NaluProcessor())
  //                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/errorPopUpController/errorPopUpAnnotationDoesNotExtendsAbstractErrorPopUpComponentController/ErrorPopUpAnnotationDoesNotExtendsAbstractErrorPopUpComponentController.java"),
  //                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/errorPopUp01/IErrorEventComponent01.java"),
  //                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/errorPopUp01/ErrorEventComponent01.java")));
  //    CompilationSubject.assertThat(compilation)
  //                      .failed();
  //    CompilationSubject.assertThat(compilation)
  //                      .hadErrorContaining("@ErrorPopUpController can only be used on a class that extends AbstractErrorPopUpComponentController");
  //  }
  
  @Test
  void testBlockControllerAnnotationOnAInterface() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockController/blockControllerAnnotationOnAInterface/BlockControllerAnnotationOnAInterface.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockController/common/block01/IBlockComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockController/common/block01/BlockComponent01.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: @BlockController can only be used with an class");
  }
  
  @Test
  void testBlockControllerEmptyName() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockController/blockControllerNameEmpty/BlockControllerEmptyName.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockController/common/block01/IBlockComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockController/common/block01/BlockComponent01.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@BlockController - name attribute should not be empty");
  }
  
  @Test
  void testBlockControllerDuplicateName() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockController/blockControllerNameDuplicate/BlockControllerDuplicateName01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockController/blockControllerNameDuplicate/BlockControllerDuplicateName02.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockController/common/block01/IBlockComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/blockController/common/block01/BlockComponent01.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@BlockController: the name >>SameName<< is duplicate! Please use another unique name!");
  }
  
}
