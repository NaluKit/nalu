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

package com.github.mvp4g.nalu.react.processor.application;

import java.util.ArrayList;

import javax.tools.JavaFileObject;

import com.github.mvp4g.nalu.react.processor.NaluReactProcessor;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjectSubject;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import static com.google.testing.compile.Compiler.javac;

public class ApplicationTest {

  @Test
  public void testApplicationAnnotationOnAClass() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOnClass/ApplicationAnnotationInterfaceOnAClass.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Application annotated must be used with an interface");
  }

  @Test
  public void testApplicationAnnotationWithoutEventBusAttribute() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationWithoutEventBusAttribute/ApplicationAnnotationWithoutEventBusAttribute.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("is missing a default value for the element 'eventBus");
  }

  @Test
  public void testApplicationInterfaceWithoutExtendsIsApplication() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationInterfaceWithoutExtendsIsApplication/ApplicationInterfaceWithoutExtendsIsApplication.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Application must implement IsApplication interface");
  }

  @Test
  public void testApplictionAnnotationOnAMethod() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOnAMethod/ApplicationAnnotationOnAMethod.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Application can only be used on a type (interface)");
  }

  @Test
  public void testApplicationAnnotationOkWithLoader() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOkWithLoader/ApplicationAnnotationOkWithLoader.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOkWithLoader/MockApplicationLoader.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOkWithLoader/MockEventBus.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOkWithLoader/MockOneEventHandler.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOkWithLoader/MockShellPresenter.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    JavaFileObjectSubject.assertThat(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOkWithLoader/ApplicationAnnotationOkWithLoaderImpl.java"))
                         .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOkWithLoader/ApplicationAnnotationOkWithLoaderImpl.java"));
  }

  @Test
  public void testApplicationAnnotationOkWithoutLoader() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOkWithoutLoader/ApplicationAnnotationOkWithoutLoader.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOkWithoutLoader/MockEventBus.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOkWithoutLoader/MockOneEventHandler.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOkWithoutLoader/MockShellPresenter.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    JavaFileObjectSubject.assertThat(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOkWithoutLoader/ApplicationAnnotationOkWithoutLoaderImpl.java"))
                         .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOkWithoutLoader/ApplicationAnnotationOkWithoutLoaderImpl.java"));
  }

  @Test
  public void testApplicationAnnotationOkWithoutLoaderAsInnerInterface() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOkWithoutLoaderAsInnerInterface/ApplicationAnnotationOkWithoutLoaderAsInnerInterface.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOkWithoutLoaderAsInnerInterface/MockEventBus.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOkWithoutLoaderAsInnerInterface/MockOneEventHandler.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOkWithoutLoaderAsInnerInterface/MockShellPresenter.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    JavaFileObjectSubject.assertThat(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOkWithoutLoaderAsInnerInterface/MyApplicationImpl.java"))
                         .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOkWithoutLoaderAsInnerInterface/MyApplicationImpl.java"));
  }

  @Test
  public void testApplicationAnnotationOkWithLoaderAsInnerInterface() {
    Compilation compilation = javac().withProcessors(new NaluReactProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOkWithLoaderAsInnerInterface/ApplicationAnnotationOkWithLoaderAsInnerInterface.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOkWithLoaderAsInnerInterface/MockEventBus.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOkWithLoaderAsInnerInterface/MockOneEventHandler.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOkWithLoaderAsInnerInterface/MockShellPresenter.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    JavaFileObjectSubject.assertThat(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOkWithLoaderAsInnerInterface/ApplicationAnnotationOkWithLoaderAsInnerInterfaceImpl.java"))
                         .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/application/applicationAnnotationOkWithLoaderAsInnerInterface/ApplicationAnnotationOkWithLoaderAsInnerInterfaceImpl.java"));
  }
}
