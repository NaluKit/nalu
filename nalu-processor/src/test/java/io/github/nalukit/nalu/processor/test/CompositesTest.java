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
public class CompositesTest {

  @Test
  public void testCompositesOnFilter() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("io/github/nalukit/nalu/processor/composites/filter/MockFilter.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/composites/filter/MockComposite.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/composites/filter/IMockCompositeComponent.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/composites/filter/MockCompositeComponent.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/MockContext.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: -> >>io.github.nalukit.nalu.processor.composites.filter.MockFilter<< - @Composites can only be used on a class that extends IsController or IsShell");
  }

  @Test
  public void testCompositesOnHandler() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("io/github/nalukit/nalu/processor/composites/handler/MockHandler.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/composites/handler/MockComposite.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/composites/handler/IMockCompositeComponent.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/composites/handler/MockCompositeComponent.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/MockContext.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: -> >>io.github.nalukit.nalu.processor.composites.handler.MockHandler<< - @Composites can only be used on a class that extends IsController or IsShell");
  }

  @Test
  public void testCompositesOnLogger() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("io/github/nalukit/nalu/processor/composites/logger/MockLogger.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/composites/logger/MockComposite.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/composites/logger/IMockCompositeComponent.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/composites/logger/MockCompositeComponent.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/MockContext.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: -> >>io.github.nalukit.nalu.processor.composites.logger.MockLogger<< - @Composites can only be used on a class that extends IsController or IsShell");
  }

  @Test
  public void testCompositesOnPopup() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("io/github/nalukit/nalu/processor/composites/popup/IPopUpComponent01.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/composites/popup/PopUpComponent01.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/composites/popup/PopUpController01.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/composites/popup/MockComposite.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/composites/popup/IMockCompositeComponent.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/composites/popup/MockCompositeComponent.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/MockContext.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: -> >>io.github.nalukit.nalu.processor.composites.popup.PopUpController01<< - @Composites can only be used on a class that extends IsController or IsShell");
  }

  @Test
  public void testCompositesOnTracker() {
    Compilation compilation = javac().withProcessors(new NaluProcessor())
                                     .compile(Arrays.asList(JavaFileObjects.forResource("io/github/nalukit/nalu/processor/composites/tracker/MockTracker.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/composites/tracker/MockComposite.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/composites/tracker/IMockCompositeComponent.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/composites/tracker/MockCompositeComponent.java"),
                                                            JavaFileObjects.forResource("io/github/nalukit/nalu/processor/common/MockContext.java")));
    CompilationSubject.assertThat(compilation)
                      .failed();
    CompilationSubject.assertThat(compilation)
                      .hadErrorContaining("Nalu-Processor: -> >>io.github.nalukit.nalu.processor.composites.tracker.MockTracker<< - @Composites can only be used on a class that extends IsController or IsShell");
  }

}
