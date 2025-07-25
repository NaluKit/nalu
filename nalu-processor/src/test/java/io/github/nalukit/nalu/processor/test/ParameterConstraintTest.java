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

public class ParameterConstraintTest {

  //  @Test
  //  public void testParaemterConstraintRule01() {
  //    Compilation compilation = javac().withProcessors(new NaluProcessor())
  //                                     .compile(Arrays.asList(JavaFileObjects.forResource("io/github/nalukit/nalu/processor/parameterConstraintRule/notAnInterface/ParameterConstraintRuleNotAnInterface.java"),
  //                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/MockShell.java"),
  //                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/MockContext.java"),
  //                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"),
  //                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"),
  //                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/component01/Component01.java")));
  //    CompilationSubject.assertThat(compilation)
  //                      .failed();
  //    CompilationSubject.assertThat(compilation)
  //                      .hadErrorContaining("Nalu-Processor: @ParameterConstraintRule can only be used with an interface");
  //  }
  //
  //  @Test
  //  public void testParaemterConstraintRule02() {
  //    Compilation compilation = javac().withProcessors(new NaluProcessor())
  //                                     .compile(Arrays.asList(JavaFileObjects.forResource("io/github/nalukit/nalu/processor/parameterConstraintRule/wrongExtends/ParameterConstraintRuleNoExtends.java"),
  //                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/MockShell.java"),
  //                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/MockContext.java"),
  //                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"),
  //                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"),
  //                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/component01/Component01.java")));
  //    CompilationSubject.assertThat(compilation)
  //                      .failed();
  //    CompilationSubject.assertThat(compilation)
  //                      .hadErrorContaining("Nalu-Processor: @ParameterConstraintRule can only be used on an interface that extends IsParameterConstraintRule");
  //  }
  //
  //  @Test
  //  public void testParaemterConstraintRule03() {
  //    Compilation compilation = javac().withProcessors(new NaluProcessor())
  //                                     .compile(Arrays.asList(JavaFileObjects.forResource("io/github/nalukit/nalu/processor/parameterConstraintRule/wrongExtends/ParameterConstraintRuleNoExtends.java"),
  //                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/MockShell.java"),
  //                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/MockContext.java"),
  //                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"),
  //                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"),
  //                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/component01/Component01.java")));
  //    CompilationSubject.assertThat(compilation)
  //                      .failed();
  //    CompilationSubject.assertThat(compilation)
  //                      .hadErrorContaining("Nalu-Processor: @ParameterConstraintRule can only be used on an interface that extends IsParameterConstraintRule");
  //  }

  @Test
  public void testParaemterConstraintRule99() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("io/github/nalukit/nalu/processor/parameterConstraint/ok01/Controller01.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/parameterConstraint/ParameterConstraintRule01.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/MockContext.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/ui/component01/Component01.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("io/github/nalukit/nalu/processor/parameterConstraint/ok01/Controller01CreatorImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("io/github/nalukit/nalu/processor/parameterConstraint/ok01/Controller01CreatorImpl.java"));
  }

}
