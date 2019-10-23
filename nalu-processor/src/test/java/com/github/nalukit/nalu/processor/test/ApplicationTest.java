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
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;
import java.util.ArrayList;
import java.util.Arrays;

import static com.google.testing.compile.Compiler.javac;

@SuppressWarnings("serial")
public class ApplicationTest {

  @Test
  void testApplicationAnnotationStartRouteDoesNotBeginWithSlash() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/startRouteNotBeginWithSlash/StartRouteNotBeginWithSlash.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor:@Application - startRoute attribute muss begin with a '/'");
  }

  @Test
  void testApplicationAnnotationRouteErrorDoesNotBeginWithSlash() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/routeErrorNotBeginWithSlash/RouteErrorNotBeginWithSlash.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockErrorShell.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/error/ErrorController.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/error/IErrorComponent.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/error/ErrorComponent.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor:@Application - routeError attribute muss begin with a '/'");
  }

  @Test
  void testApplicationAnnotationOnAClass() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/applicationAnnotationOnClass/ApplicationAnnotationInterfaceOnAClass.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Application annotated must be used with an interface");
  }

  @Test
  void testApplicationInterfaceWithoutExtendsIsApplication() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/applicationInterfaceWithoutExtendsIsApplication/ApplicationInterfaceWithoutExtendsIsApplication.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Application must implement IsApplication interface");
  }

  @Test
  void testApplicationAnnotationOnAMethod() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/applicationAnnotationOnAMethod/ApplicationAnnotationOnAMethod.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("@Application can only be used on a type (interface)");
  }

  @Test
  void testApplicationAnnotationOkWithLoader() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/applicationAnnotationOkWithLoader/ApplicationAnnotationOkWithLoader.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/common/application/applicationAnnotationOkWithLoader/ApplicationAnnotationOkWithLoaderImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/applicationAnnotationOkWithLoader/ApplicationAnnotationOkWithLoaderImpl.java"));
  }

  @Test
  void testApplicationAnnotationOkWithoutLoader() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/applicationAnnotationOkWithoutLoader/ApplicationAnnotationOkWithoutLoader.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/common/application/applicationAnnotationOkWithoutLoader/ApplicationAnnotationOkWithoutLoaderImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/applicationAnnotationOkWithoutLoader/ApplicationAnnotationOkWithoutLoaderImpl.java"));
  }

  @Test
  void testApplicationAnnotationOkWithoutLoaderAsInnerInterface() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/applicationAnnotationOkWithoutLoaderAsInnerInterface/ApplicationAnnotationOkWithoutLoaderAsInnerInterface.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/common/application/applicationAnnotationOkWithoutLoaderAsInnerInterface/MyApplicationImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/applicationAnnotationOkWithoutLoaderAsInnerInterface/MyApplicationImpl.java"));
  }

  @Test
  void testApplicationAnnotationOkWithLoaderAsInnerInterface() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/applicationAnnotationOkWithLoaderAsInnerInterface/ApplicationAnnotationOkWithLoaderAsInnerInterface.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Controller01.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/IComponent01.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component01/Component01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/common/application/applicationAnnotationOkWithLoaderAsInnerInterface/ApplicationAnnotationOkWithLoaderAsInnerInterfaceImpl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/applicationAnnotationOkWithLoaderAsInnerInterface/ApplicationAnnotationOkWithLoaderAsInnerInterfaceImpl.java"));
  }

  @Test
  void testApplicationWithComposite01() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite01/ControllerWithComposite01.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite01/IComponent01.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite01/Component01.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite01/composite/CompositeController01.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite01/composite/ICompositeComponent01.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite01/composite/CompositeComponent01.java"));

                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/applicationWithComposite01/ApplicationWithComposite01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/common/application/applicationWithComposite01/ApplicationWithComposite01Impl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/applicationWithComposite01/ApplicationWithComposite01Impl.java"));
  }

  @Test
  void testApplicationWithComposite02() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite02/ControllerWithComposite02.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite02/IComponent02.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite02/Component02.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite02/composite/CompositeController02.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite02/composite/ICompositeComponent02.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite02/composite/CompositeComponent02.java"));

                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/applicationWithComposite02/ApplicationWithComposite02.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/common/application/applicationWithComposite02/ApplicationWithComposite02Impl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/applicationWithComposite02/ApplicationWithComposite02Impl.java"));
  }

  @Test
  void testApplicationWithComposite03() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite03/ControllerWithComposite03.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite03/IComponent03.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite03/Component03.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite03/composite/CompositeController03.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite03/composite/ICompositeComponent03.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite03/CompositeCondition03.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite03/composite/CompositeComponent03.java"));

                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/applicationWithComposite03/ApplicationWithComposite03.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/common/application/applicationWithComposite03/ApplicationWithComposite03Impl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/applicationWithComposite03/ApplicationWithComposite03Impl.java"));
  }

  @Test
  void testApplicationWithTwoCompositeAndDifferentConditions() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite04/ControllerWithComposite04.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite04/IComponent04.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite04/Component04.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite04/CompositeCondition01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite04/CompositeCondition02.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite04/composite/CompositeController03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite04/composite/ICompositeComponent03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite04/composite/CompositeComponent03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite04/composite/CompositeController04.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite04/composite/ICompositeComponent04.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite04/composite/CompositeComponent04.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/applicationWithComposite04/ApplicationWithComposite04.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/common/application/applicationWithComposite04/ApplicationWithComposite04Impl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/applicationWithComposite04/ApplicationWithComposite04Impl.java"));
  }

  @Test
  void testApplicationWithTwoCompositeAndSameConditions() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite05/ControllerWithComposite05.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite05/IComponent05.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite05/Component05.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite05/CompositeCondition01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite05/composite/CompositeController03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite05/composite/ICompositeComponent03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite05/composite/CompositeComponent03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite05/composite/CompositeController04.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite05/composite/ICompositeComponent04.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite05/composite/CompositeComponent04.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/applicationWithComposite05/ApplicationWithComposite05.java")));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/common/application/applicationWithComposite05/ApplicationWithComposite05Impl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/applicationWithComposite05/ApplicationWithComposite05Impl.java"));
  }

  @Test
  void testApplicationWithTwoControllersAndTwoCompositeAndSameConditions_1() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite06/ControllerWithComposite05.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite06/ControllerWithComposite06.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite06/IComponent05.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite06/Component05.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite06/CompositeCondition01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite06/IComponent06.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite06/Component06.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite06/composite/CompositeController03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite06/composite/ICompositeComponent03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite06/composite/CompositeComponent03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite06/composite/CompositeController04.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite06/composite/ICompositeComponent04.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite06/composite/CompositeComponent04.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/applicationWithComposite06/ApplicationWithComposite06.java")

                                     ));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/common/application/applicationWithComposite06/ApplicationWithComposite06Impl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/applicationWithComposite06/ApplicationWithComposite06Impl.java"));
  }

  @Test
  void testApplicationWithTwoControllersAndTwoCompositeAndSameConditions_2() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite07/ControllerWithComposite05.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite07/ControllerWithComposite06.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/MockContext.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite07/IComponent05.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite07/Component05.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite07/CompositeCondition01.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite07/IComponent06.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite07/Component06.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite07/composite/CompositeController03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite07/composite/ICompositeComponent03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite07/composite/CompositeComponent03.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite07/composite/CompositeController04.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite07/composite/ICompositeComponent04.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite07/composite/CompositeComponent04.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite07/composite/CompositeController05.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite07/composite/ICompositeComponent05.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/controllerWithComposite07/composite/CompositeComponent05.java"),
                                                            JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/applicationWithComposite07/ApplicationWithComposite07.java")

                                     ));
    CompilationSubject.assertThat(compilation)
                      .succeeded();
    CompilationSubject.assertThat(compilation)
                      .generatedSourceFile("com/github/nalukit/nalu/processor/common/application/applicationWithComposite07/ApplicationWithComposite07Impl")
                      .hasSourceEquivalentTo(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/application/applicationWithComposite07/ApplicationWithComposite07Impl.java"));
  }

}
