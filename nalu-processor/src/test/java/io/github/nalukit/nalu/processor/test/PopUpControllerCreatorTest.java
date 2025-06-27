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
public class PopUpControllerCreatorTest {

  @Test
  void testControllerCreatorOk() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/MockContext.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/popUpControllerCreator/ok/Controller01.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/popUpControllerCreator/ok/IComponent01.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/popUpControllerCreator/ok/Component01.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/popUpControllerCreator/ok/PopUpController01.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/popUpControllerCreator/ok/IPopUpComponent01.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/popUpControllerCreator/ok/PopUpComponent01.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("io/github/nalukit/nalu/processor/popUpControllerCreator/ok/PopUpController01CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("io/github/nalukit/nalu/processor/popUpControllerCreator/ok/PopUpController01CreatorImpl.java"));
  }

}
