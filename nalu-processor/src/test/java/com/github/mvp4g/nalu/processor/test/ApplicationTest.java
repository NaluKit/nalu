/*
 * Copyright (c) 2018 - Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *
 */

package com.github.mvp4g.nalu.processor.test;

import com.github.mvp4g.nalu.processor.NaluProcessor;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjectSubject;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import javax.tools.JavaFileObject;
import java.util.ArrayList;

import static com.google.testing.compile.Compiler.javac;

@SuppressWarnings("serial")
public class ApplicationTest {

  @Test
  public void testApplicationAnnotationOnAClass() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/application/applicationAnnotationOnClass/ApplicationAnnotationInterfaceOnAClass.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Application annotated must be used with an interface");
  }

  @Test
  public void testApplicationInterfaceWithoutExtendsIsApplication() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/application/applicationInterfaceWithoutExtendsIsApplication/ApplicationInterfaceWithoutExtendsIsApplication.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Application must implement IsApplication interface");
  }

  @Test
  public void testApplictionAnnotationOnAMethod() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/application/applicationAnnotationOnAMethod/ApplicationAnnotationOnAMethod.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Application can only be used on a type (interface)");
  }

  @Test
  public void testApplicationAnnotationOkWithLoader() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/application/applicationAnnotationOkWithLoader/ApplicationAnnotationOkWithLoader.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/common/MockContext.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/common/MockShell.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    JavaFileObjectSubject.assertThat(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/application/applicationAnnotationOkWithLoader/ApplicationAnnotationOkWithLoaderImpl.java"))
                         .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/application/applicationAnnotationOkWithLoader/ApplicationAnnotationOkWithLoaderImpl.java"));
  }

  @Test
  public void testApplicationAnnotationOkWithoutLoader() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/application/applicationAnnotationOkWithoutLoader/ApplicationAnnotationOkWithoutLoader.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/common/MockContext.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/common/MockShell.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    JavaFileObjectSubject.assertThat(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/application/applicationAnnotationOkWithoutLoader/ApplicationAnnotationOkWithoutLoaderImpl.java"))
                         .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/application/applicationAnnotationOkWithoutLoader/ApplicationAnnotationOkWithoutLoaderImpl.java"));
  }

  @Test
  public void testApplicationAnnotationOkWithoutLoaderAsInnerInterface() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/application/applicationAnnotationOkWithoutLoaderAsInnerInterface/ApplicationAnnotationOkWithoutLoaderAsInnerInterface.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/common/MockContext.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/common/MockShell.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    JavaFileObjectSubject.assertThat(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/application/applicationAnnotationOkWithoutLoaderAsInnerInterface/MyApplicationImpl.java"))
                         .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/application/applicationAnnotationOkWithoutLoaderAsInnerInterface/MyApplicationImpl.java"));
  }

  @Test
  public void testApplicationAnnotationOkWithLoaderAsInnerInterface() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/application/applicationAnnotationOkWithLoaderAsInnerInterface/ApplicationAnnotationOkWithLoaderAsInnerInterface.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/common/MockContext.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/common/MockShell.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    JavaFileObjectSubject.assertThat(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/application/applicationAnnotationOkWithLoaderAsInnerInterface/ApplicationAnnotationOkWithLoaderAsInnerInterfaceImpl.java"))
                         .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/application/applicationAnnotationOkWithLoaderAsInnerInterface/ApplicationAnnotationOkWithLoaderAsInnerInterfaceImpl.java"));
  }
}
