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

import com.github.nalukit.nalu.processor.NaluProcessor;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import javax.tools.JavaFileObject;
import java.util.ArrayList;

import static com.google.testing.compile.Compiler.javac;

@SuppressWarnings("serial")
public class AcceptParameterTest {

  @Test
  public void testAcceptParameterAnnotationOnAClass() {
//    Compilation compilation = javac().withProcessors(new NaluProcessor())
//                                     .compile(new ArrayList<JavaFileObject>() {
//                                       {
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/application/applicationAnnotationOnClass/AcceptParameterAnnotationInterfaceOnAClass.java"));
//                                       }
//                                     });
//    CompilationSubject.assertThat(compilation)
//                      .failed();
//    CompilationSubject.assertThat(compilation)
//                      .hadErrorContaining("@Application annotated must be used with an interface");
  }
//
//  @Test
//  public void testApplicationInterfaceWithoutExtendsIsApplication() {
//    Compilation compilation = javac().withProcessors(new NaluProcessor())
//                                     .compile(new ArrayList<JavaFileObject>() {
//                                       {
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/application/applicationInterfaceWithoutExtendsIsApplication/ApplicationInterfaceWithoutExtendsIsApplication.java"));
//                                       }
//                                     });
//    CompilationSubject.assertThat(compilation)
//                      .failed();
//    CompilationSubject.assertThat(compilation)
//                      .hadErrorContaining("@Application must implement IsApplication interface");
//  }
//
//  @Test
//  public void testApplictionAnnotationOnAMethod() {
//    Compilation compilation = javac().withProcessors(new NaluProcessor())
//                                     .compile(new ArrayList<JavaFileObject>() {
//                                       {
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/application/applicationAnnotationOnAMethod/AcceptParameterAnnotationOnAMethod.java"));
//                                       }
//                                     });
//    CompilationSubject.assertThat(compilation)
//                      .failed();
//    CompilationSubject.assertThat(compilation)
//                      .hadErrorContaining("@Application can only be used on a type (interface)");
//  }
//
//  @Test
//  public void testAcceptParameterAnnotationOkWithLoader() {
//    Compilation compilation = javac().withProcessors(new NaluProcessor())
//                                     .compile(new ArrayList<JavaFileObject>() {
//                                       {
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/application/applicationWithTracker/AcceptParameterAnnotationOkWithLoader.java"));
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"));
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockShell.java"));
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"));
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"));
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java"));
//                                       }
//                                     });
//    CompilationSubject.assertThat(compilation)
//                      .succeeded();
//    CompilationSubject.assertThat(compilation)
//                      .generatedSourceFile("com/github/nalukit/nalu/processor/application/applicationWithTracker/AcceptParameterAnnotationOkWithLoaderImpl")
//                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/application/applicationWithTracker/AcceptParameterAnnotationOkWithLoaderImpl.java"));
//  }
//
//  @Test
//  public void testAcceptParameterAnnotationOkWithoutLoader() {
//    Compilation compilation = javac().withProcessors(new NaluProcessor())
//                                     .compile(new ArrayList<JavaFileObject>() {
//                                       {
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/application/applicationAnnotationOkWithoutLoader/AcceptParameterAnnotationOkWithoutLoader.java"));
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"));
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockShell.java"));
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"));
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"));
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java"));
//                                       }
//                                     });
//    CompilationSubject.assertThat(compilation)
//                      .succeeded();
//    CompilationSubject.assertThat(compilation)
//                      .generatedSourceFile("com/github/nalukit/nalu/processor/application/applicationAnnotationOkWithoutLoader/AcceptParameterAnnotationOkWithoutLoaderImpl")
//                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/application/applicationAnnotationOkWithoutLoader/AcceptParameterAnnotationOkWithoutLoaderImpl.java"));
//  }
//
//  @Test
//  public void testAcceptParameterAnnotationOkWithoutLoaderAsInnerInterface() {
//    Compilation compilation = javac().withProcessors(new NaluProcessor())
//                                     .compile(new ArrayList<JavaFileObject>() {
//                                       {
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/application/applicationAnnotationOkWithoutLoaderAsInnerInterface/AcceptParameterAnnotationOkWithoutLoaderAsInnerInterface.java"));
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"));
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockShell.java"));
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"));
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"));
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java"));
//                                       }
//                                     });
//    CompilationSubject.assertThat(compilation)
//                      .succeeded();
//    CompilationSubject.assertThat(compilation)
//                      .generatedSourceFile("com/github/nalukit/nalu/processor/application/applicationAnnotationOkWithoutLoaderAsInnerInterface/MyApplicationImpl")
//                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/application/applicationAnnotationOkWithoutLoaderAsInnerInterface/MyApplicationImpl.java"));
//  }
//
//  @Test
//  public void testAcceptParameterAnnotationOkWithLoaderAsInnerInterface() {
//    Compilation compilation = javac().withProcessors(new NaluProcessor())
//                                     .compile(new ArrayList<JavaFileObject>() {
//                                       {
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/application/applicationAnnotationOkWithLoaderAsInnerInterface/AcceptParameterAnnotationOkWithLoaderAsInnerInterface.java"));
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"));
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockShell.java"));
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"));
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"));
//                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java"));
//                                       }
//                                     });
//    CompilationSubject.assertThat(compilation)
//                      .succeeded();
//    CompilationSubject.assertThat(compilation)
//                      .generatedSourceFile("com/github/nalukit/nalu/processor/application/applicationAnnotationOkWithLoaderAsInnerInterface/AcceptParameterAnnotationOkWithLoaderAsInnerInterfaceImpl")
//                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/application/applicationAnnotationOkWithLoaderAsInnerInterface/AcceptParameterAnnotationOkWithLoaderAsInnerInterfaceImpl.java"));
//  }
}
