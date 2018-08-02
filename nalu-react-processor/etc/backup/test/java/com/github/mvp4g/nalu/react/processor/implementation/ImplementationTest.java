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

package com.github.mvp4g.nalu.react.processor.implementation;

import java.util.ArrayList;

import javax.tools.JavaFileObject;

import com.github.mvp4g.nalu.react.processor.NaluReact-Processor;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import static com.google.testing.compile.Compiler.javac;

/**
 * create tests to check if the processor runs with incomplete data!
 */
public class ImplementationTest {

  @Test
  public void testOnlyApplicationData() {
    Compilation compilation = javac().withProcessors(new NaluReact-Processor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/implementation/testOnlyApplicationData/ApplicationAnnotation.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("no EventBusMetaModel found! Did you forget to create an EventBus for mvp4g2 or forget to annotate the EventBus with @EventBus?");
  }

  @Test
  public void testOnlyEventBusAndHandlerData() {
    Compilation compilation = javac().withProcessors(new NaluReact-Processor())
                                     .compile(new ArrayList<JavaFileObject>() {
                                       {
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/implementation/testOnlyEventBusData/MockEventBus.java"));
                                         add(JavaFileObjects.forResource("com/github/mvp4g/nalu/react/processor/implementation/testOnlyEventBusData/MockShellPresenter.java"));
                                       }
                                     });
    CompilationSubject.assertThat(compilation)
                      .succeeded();
  }
}
