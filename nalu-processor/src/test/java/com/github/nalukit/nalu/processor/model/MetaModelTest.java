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

import com.github.nalukit.nalu.client.internal.route.HashResult;
import com.github.nalukit.nalu.client.internal.route.RouterException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MetaModelTest {

  private MetaModel metaModel;

  @Before
  public void before() {
    this.metaModel = new MetaModel();
  }

  @Test
  public void getShellOfStartRoute01() {
    this.metaModel.setStartRoute("/MockShell/route01");
    Assert.assertEquals("start route test with leading '/'",
                        "MockShell",
                        this.metaModel.getShellOfStartRoute());
  }

  @Test
  public void getShellOfStartRoute02() {
    this.metaModel.setStartRoute("MockShell/route01");
    Assert.assertEquals("start route test without leading '/'",
                        "MockShell",
                        this.metaModel.getShellOfStartRoute());
  }

  @Test
  public void getShellOfErrorRoute01() {
    this.metaModel.setRouteError("/ErrorShell/error");
    Assert.assertEquals("error route test without leading '/'",
                        "ErrorShell",
                        this.metaModel.getShellOfErrorRoute());
  }

  @Test
  public void getShellOfErrorRoute02() {
    this.metaModel.setRouteError("ErrorShell/error");
    Assert.assertEquals("error route test without leading '/'",
                        "ErrorShell",
                        this.metaModel.getShellOfErrorRoute());
  }
}