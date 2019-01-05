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
public class ConsistenceTest {

  @Test
  public void testStarRouteShellDoesNotExists() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/consistence/startRouteShellDoesNotExist/StartRouteShellDoesNotExistApplication.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component03/Controller03.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component03/IComponent03.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component03/Component03.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: The shell of the startRoute >>MockShell<< does not exist!");
  }

  @Test
  public void testStarRouteDoesNotExists() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/consistence/startRouteDeosNotExist/StartRouteDoesNotExistApplication.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component03/Controller03.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component03/IComponent03.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component03/Component03.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: The shell of the startRoute >>mockShell<< does not exist!");
  }

  @Test
  public void testShellOfSelection() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/consistence/shellOfSelection/ShellOfSelectionApplication.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component04/Controller04.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell02.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component04/IComponent04.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component04/Component04.java"));
                                       }
                                     });
        CompilationSubject.assertThat(compilation)
                          .succeeded();
  }

  @Test
  public void testShellOfSelectionWildCard() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/consistence/shellOfSelectionWildCard/ShellOfSelectionWildCardApplication.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell02.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component05/Controller05.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component05/IComponent05.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component05/Component05.java"));
                                       }
                                     });
        CompilationSubject.assertThat(compilation)
                          .succeeded();
  }

  @Test
  public void testDuplicateShellName() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/consistence/dubplicateShellName/DubplicateShellNameApplication.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell02.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockErrorShell.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShellDuplicateName.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component03/Controller03.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component03/IComponent03.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component03/Component03.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/error/ErrorController.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/error/IErrorComponent.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/error/ErrorComponent.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor:@Shell: the name >>mockShell02<< is duplicate! Please use another unique name!");
  }

  @Test
  public void testNoDuplicateShellName() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/consistence/dubplicateShellName/DubplicateShellNameApplication.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell02.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockErrorShell.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell01.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component03/Controller03.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component03/IComponent03.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component03/Component03.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/error/ErrorController.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/error/IErrorComponent.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/error/ErrorComponent.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
  }

  @Test
  public void testMultipleShells() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/consistence/multiShells/MultiShellApplication.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell02.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component03/Controller03.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component03/IComponent03.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component03/Component03.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
  }

  @Test
  public void testIllegalErrorRoute01() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell02.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/consistence/illegalError/IllegalErrorRouteApplication.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component03/Controller03.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component03/IComponent03.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component03/Component03.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component04/Controller04.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component04/IComponent04.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component04/Component04.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: The shell of the errorRoute >>errorShellIllegal<< does not exist!");
  }

  @Test
  public void testIllegalErrorRoute02() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockShell02.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/MockErrorShellIllegal.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/consistence/illegalError/IllegalErrorRouteApplication.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component03/Controller03.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component03/IComponent03.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component03/Component03.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component04/Controller04.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component04/IComponent04.java"));
                                         add(JavaFileObjects.forResource("com/github/nalukit/nalu/processor/common/ui/component04/Component04.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: The route of the errorRoute >>/errorShellIllegal/error<< does not exist!");
  }

}
