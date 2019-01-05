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

package com.github.nalukit.nalu.processor.model.intern;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ControllerModelTest {

  @Test
  public void match01() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/shell01/route01",
                                                                "route01");
    Assert.assertTrue(cm.match("/shell01/route01"));
  }

  @Test
  public void match02() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/shell01/route01",
                                                                "route01");
    Assert.assertTrue(cm.match("shell01/route01"));
  }

  @Test
  public void match03() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/shell01/route01",
                                                                "route01");
    Assert.assertFalse(cm.match("/shell02/route01"));
  }

  @Test
  public void match04() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/shell01/route01",
                                                                "route01");
    Assert.assertFalse(cm.match("shell01/route02"));
  }

  @Test
  public void match05() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/[shell01|shell02]/route01",
                                                                "route01");
    Assert.assertTrue(cm.match("/shell01/route01"));
  }

  @Test
  public void match06() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/[shell01|shell02]/route01",
                                                                "route01");
    Assert.assertTrue(cm.match("shell01/route01"));
  }

  @Test
  public void match07() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/[shell01|shell02]/route01",
                                                                "route01");
    Assert.assertFalse(cm.match("/shell03/route01"));
  }

  @Test
  public void match08() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/[shell01|shell02]/route01",
                                                                "route01");
    Assert.assertFalse(cm.match("shell03/route02"));
  }

  @Test
  public void match09() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/*/route01",
                                                                "route01");
    Assert.assertTrue(cm.match("/shell01/route01"));
  }

  @Test
  public void match10() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/*/route01",
                                                                "route01");
    Assert.assertTrue(cm.match("shell01/route01"));
  }

  @Test
  public void match11() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/*/route01",
                                                                "route01");
    Assert.assertFalse(cm.match("/shell02/route02"));
  }

  @Test
  public void match12() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/*/route01",
                                                                "route01");
    Assert.assertFalse(cm.match("shell01/route02"));
  }

  @Test
  public void match13() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/shell01/route01/:parameter",
                                                                "route01");
    Assert.assertTrue(cm.match("/shell01/route01"));
  }

  @Test
  public void match14() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/shell01/route01/:parameter",
                                                                "route01");
    Assert.assertTrue(cm.match("shell01/route01"));
  }

  @Test
  public void match15() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/shell01/route01/:parameter",
                                                                "route01");
    Assert.assertFalse(cm.match("/shell02/route01"));
  }

  @Test
  public void match16() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/shell01/route01/:parameter",
                                                                "route01");
    Assert.assertFalse(cm.match("shell01/route02"));
  }

  @Test
  public void match17() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/[shell01|shell02]/route01/:parameter",
                                                                "route01");
    Assert.assertTrue(cm.match("/shell01/route01"));
  }

  @Test
  public void match18() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/[shell01|shell02]/route01/:parameter",
                                                                "route01");
    Assert.assertTrue(cm.match("shell01/route01"));
  }

  @Test
  public void match19() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/[shell01|shell02]/route01/:parameter",
                                                                "route01");
    Assert.assertFalse(cm.match("/shell03/route01"));
  }

  @Test
  public void match20() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/[shell01|shell02]/route01/:parameter",
                                                                "route01");
    Assert.assertFalse(cm.match("shell03/route02"));
  }

  @Test
  public void match21() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/*/route01/:parameter",
                                                                "route01");
    Assert.assertTrue(cm.match("/shell01/route01"));
  }

  @Test
  public void match22() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/*/route01/:parameter",
                                                                "route01");
    Assert.assertTrue(cm.match("shell01/route01"));
  }

  @Test
  public void match23() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/*/route01/:parameter",
                                                                "route01");
    Assert.assertFalse(cm.match("/shell02/route02"));
  }

  @Test
  public void match24() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/*/route01/:parameter",
                                                                "route01");
    Assert.assertFalse(cm.match("shell01/route02"));
  }

  @Test
  public void match25() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/shell01/route01/route02/:parameter",
                                                                "route01/route02");
    Assert.assertTrue(cm.match("/shell01/route01/route02"));
  }

  @Test
  public void match26() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/shell01/route01/route02/:parameter",
                                                                "route01/route02");
    Assert.assertTrue(cm.match("shell01/route01/route02"));
  }

  @Test
  public void match27() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/shell01/route01/route02/:parameter",
                                                                "route01/route02");
    Assert.assertFalse(cm.match("/shell02/route01/route02"));
  }

  @Test
  public void match28() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/shell01/route01/route02/:parameter",
                                                                "route01/route02");
    Assert.assertFalse(cm.match("shell01/route02"));
  }

  @Test
  public void match29() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/[shell01|shell02]/route01/route02/:parameter",
                                                                "route01/route02");
    Assert.assertTrue(cm.match("/shell01/route01/route02"));
  }

  @Test
  public void match30() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/[shell01|shell02]/route01/route02/:parameter",
                                                                "route01/route02");
    Assert.assertTrue(cm.match("shell01/route01/route02"));
  }

  @Test
  public void match31() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/[shell01|shell02]/route01/route02/:parameter",
                                                                "route01/route02");
    Assert.assertFalse(cm.match("/shell03/route01/route02"));
  }

  @Test
  public void match32() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/[shell01|shell02]/route01/route02/:parameter",
                                                                "route01/route02");
    Assert.assertFalse(cm.match("shell03/route02/route02"));
  }

  @Test
  public void match33() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/*/route01/route02/:parameter",
                                                                "route01/route02");
    Assert.assertTrue(cm.match("/shell01/route01/route02"));
  }

  @Test
  public void match34() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/*/route01/route02/:parameter",
                                                                "route01/route02");
    Assert.assertFalse(cm.match("shell01/route01/route03"));
  }

  @Test
  public void match35() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/*/route01/route02/:parameter",
                                                                "route01");
    Assert.assertFalse(cm.match("/shell02/route03/route02"));
  }

  @Test
  public void match36() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/*/route01/:parameter",
                                                                "route01");
    Assert.assertFalse(cm.match("shell01/route02/route02"));
  }

  @SuppressWarnings("serial")
  private ControllerModel getControllerModelForMatchingTest(String route,
                                                            String routeWithoutShell) {
    return new ControllerModel(route,
                               routeWithoutShell,
                               "selector,",
                               new ArrayList<String>() {{
                                 add("parametr01");
                                 add("parameter02");
                               }},
                               new ClassNameModel("com.github.nalukit.nalu.processor.common.ui.component01,Controller01"),
                               new ClassNameModel("com.github.nalukit.nalu.processor.common.ui.component01,IComponent01"),
                               new ClassNameModel("com.github.nalukit.nalu.processor.common.ui.component01,Component01"),
                               new ClassNameModel("com.github.nalukit.nalu.processor.common.ui.component01,Component01"),
                               new ClassNameModel("com.github.nalukit.nalu.processor.common.ui.component01,Component01"),
                               new ClassNameModel("com.github.nalukit.nalu.processor.common.ui.component01,Component01"),
                               false);

  }
}