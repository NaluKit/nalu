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

package com.github.nalukit.nalu.processor.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MetaModelTest {

  private MetaModel metaModel;

  @BeforeEach
  void before() {
    this.metaModel = new MetaModel();
  }

  @Test
  void getShellOfStartRoute01() {
    this.metaModel.setStartRoute("/MockShell/route01");
    Assertions.assertEquals("MockShell",
                            this.metaModel.getShellOfStartRoute());
  }

  @Test
  void getShellOfStartRoute02() {
    this.metaModel.setStartRoute("MockShell/route01");
    Assertions.assertEquals("MockShell",
                            this.metaModel.getShellOfStartRoute());
  }

}