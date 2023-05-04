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

import static com.google.testing.compile.Compiler.javac;

@SuppressWarnings("serial")
public class ModuleTest {

  @Test
  void testSubModuleHasErrorPopUpController() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/module/subModuleHasErrorPopUpController/MockSubModule.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/module/MockSubModuleContext.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/errorPopUp01/ErrorEventController01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/errorPopUp01/IErrorEventComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/errorPopUp01/ErrorEventComponent01.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor:@ErrorPopUpController: can only be use inside a main module");
  }

}
