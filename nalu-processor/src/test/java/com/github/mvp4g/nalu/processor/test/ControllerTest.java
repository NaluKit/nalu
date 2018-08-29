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
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import javax.tools.JavaFileObject;
import java.util.ArrayList;

import static com.google.testing.compile.Compiler.javac;

@SuppressWarnings("serial")
public class ControllerTest {

  @Test
  public void testControllerAnnotationWithoutParameterOK() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/controller/controllerAnnotationWithoutParameterOK/ControllerAnnotationWithoutParameterOK.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/common/MockContext.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/common/MockShell.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/common/ui/component01/Controller01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/common/ui/component01/IComponent01.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/common/ui/component01/Component01.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
  }

  @Test
  public void testControllerAnnotationWithParameterNotOK() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/controller/controllerAnnotationWithParameterNotOK/ControllerAnnotationWithParameterNotOK.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/common/MockContext.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/common/MockShell.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/controller/controllerAnnotationWithParameterNotOK/ui/bad/BadController.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/controller/controllerAnnotationWithParameterNotOK/ui/bad/IBadComponent.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/controller/controllerAnnotationWithParameterNotOK/ui/bad/BadComponent.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: @Controller >>com.github.mvp4g.nalu.processor.controller.controllerAnnotationWithParameterNotOK.ui.bad.BadController<< does not implement the method: >>setVariable01<< which is required for accepting a variable from the route");
  }

  @Test
  public void testControllerAnnotationWithParameterOK() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/controller/controllerAnnotationWithParameterOK/ControllerAnnotationWithParameterOK.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/common/MockContext.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/common/MockShell.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/common/ui/component02/Controller02.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/common/ui/component02/IComponent02.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/processor/common/ui/component02/Component02.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
  }
}
