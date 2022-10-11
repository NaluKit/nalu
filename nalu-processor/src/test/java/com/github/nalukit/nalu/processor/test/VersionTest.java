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

import java.util.Arrays;
import java.util.Collections;

import static com.google.testing.compile.Compiler.javac;

@SuppressWarnings("serial")
public class VersionTest {

  @Test
  void testVersionAnnotationOnAMethod() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/version/versionAnnotationOnAMethod/VersionAnnotationOnAMethod.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Version can only be used on a type (interface)");
  }

  @Test
  void testVersionAnnotationOnAClass() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/version/versionAnnotationOnAClass/VersionAnnotationOnAClass.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
  }

  @Test
  void testVersionAnnotationWithoutExtendsIsApplication() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/version/versionAnnotationWithoutExtendsIsApplication/VersionAnnotationWithoutExtendsIsApplication.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
  }

  @Test
  void testVersionAnnotationOnClassWithoutApplicationAnnotation() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Collections.singletonList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/version/versionAnnotationOnClassWithoutApplicationAnnotation/VersionAnnotationOnClassWithoutApplicationAnnotation.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Version can only be used with an interfaces annotated with @Annotation");
  }

  @Test
  void testVersionAnnotationOk01() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/version/applicationWithVersionExtendingIsModuleContest/VersionAnnotation.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockModuleContext.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockModuleShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/ControllerModule01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java")));

    CompilationSubject.assertThat(compilation)
                      .succeeded();
    // does not work due to different Timestamps (System.currentTimeMillis() value
    //    CompilationSubject.assertThat(compilation)
    //                      .generatedSourceFile("com/github/nalukit/nalu/processor/version/applicationWithVersionExtendingIsModuleContest/VersionAnnotationImpl")
    //                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/version/applicationWithVersionExtendingIsModuleContest/VersionAnnotationImpl.java"));
  }

  @Test
  void testVersionAnnotationOk02() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/version/applicationWithVersionNotExtendingIsModuleContest/VersionAnnotation.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java")));

    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/version/applicationWithVersionNotExtendingIsModuleContest/VersionAnnotationImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/version/applicationWithVersionNotExtendingIsModuleContest/VersionAnnotationImpl.java"));
  }

}
