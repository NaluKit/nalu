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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ControllerModelTest {

  @Test
  void match01() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/shell01/route01",
                                                                "route01");
    Assertions.assertTrue(cm.match("/shell01/route01"));
  }

  @Test
  void match02() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/shell01/route01",
                                                                "route01");
    Assertions.assertTrue(cm.match("shell01/route01"));
  }

  @Test
  void match03() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/shell01/route01",
                                                                "route01");
    Assertions.assertFalse(cm.match("/shell02/route01"));
  }

  @Test
  void match04() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/shell01/route01",
                                                                "route01");
    Assertions.assertFalse(cm.match("shell01/route02"));
  }

  @Test
  void match05() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/[shell01|shell02]/route01",
                                                                "route01");
    Assertions.assertTrue(cm.match("/shell01/route01"));
  }

  @Test
  void match06() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/[shell01|shell02]/route01",
                                                                "route01");
    Assertions.assertTrue(cm.match("shell01/route01"));
  }

  @Test
  void match07() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/[shell01|shell02]/route01",
                                                                "route01");
    Assertions.assertFalse(cm.match("/shell03/route01"));
  }

  @Test
  void match08() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/[shell01|shell02]/route01",
                                                                "route01");
    Assertions.assertFalse(cm.match("shell03/route02"));
  }

  @Test
  void match09() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/*/route01",
                                                                "route01");
    Assertions.assertTrue(cm.match("/shell01/route01"));
  }

  @Test
  void match10() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/*/route01",
                                                                "route01");
    Assertions.assertTrue(cm.match("shell01/route01"));
  }

  @Test
  void match11() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/*/route01",
                                                                "route01");
    Assertions.assertFalse(cm.match("/shell02/route02"));
  }

  @Test
  void match12() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/*/route01",
                                                                "route01");
    Assertions.assertFalse(cm.match("shell01/route02"));
  }

  @Test
  void match13() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/shell01/route01/:parameter",
                                                                "route01");
    Assertions.assertTrue(cm.match("/shell01/route01"));
  }

  @Test
  void match14() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/shell01/route01/:parameter",
                                                                "route01");
    Assertions.assertTrue(cm.match("shell01/route01"));
  }

  @Test
  void match15() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/shell01/route01/:parameter",
                                                                "route01");
    Assertions.assertFalse(cm.match("/shell02/route01"));
  }

  @Test
  void match16() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/shell01/route01/:parameter",
                                                                "route01");
    Assertions.assertFalse(cm.match("shell01/route02"));
  }

  @Test
  void match17() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/[shell01|shell02]/route01/:parameter",
                                                                "route01");
    Assertions.assertTrue(cm.match("/shell01/route01"));
  }

  @Test
  void match18() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/[shell01|shell02]/route01/:parameter",
                                                                "route01");
    Assertions.assertTrue(cm.match("shell01/route01"));
  }

  @Test
  void match19() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/[shell01|shell02]/route01/:parameter",
                                                                "route01");
    Assertions.assertFalse(cm.match("/shell03/route01"));
  }

  @Test
  void match20() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/[shell01|shell02]/route01/:parameter",
                                                                "route01");
    Assertions.assertFalse(cm.match("shell03/route02"));
  }

  @Test
  void match21() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/*/route01/:parameter",
                                                                "route01");
    Assertions.assertTrue(cm.match("/shell01/route01"));
  }

  @Test
  void match22() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/*/route01/:parameter",
                                                                "route01");
    Assertions.assertTrue(cm.match("shell01/route01"));
  }

  @Test
  void match23() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/*/route01/:parameter",
                                                                "route01");
    Assertions.assertFalse(cm.match("/shell02/route02"));
  }

  @Test
  void match24() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/*/route01/:parameter",
                                                                "route01");
    Assertions.assertFalse(cm.match("shell01/route02"));
  }

  @Test
  void match25() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/shell01/route01/route02/:parameter",
                                                                "route01/route02");
    Assertions.assertTrue(cm.match("/shell01/route01/route02"));
  }

  @Test
  void match26() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/shell01/route01/route02/:parameter",
                                                                "route01/route02");
    Assertions.assertTrue(cm.match("shell01/route01/route02"));
  }

  @Test
  void match27() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/shell01/route01/route02/:parameter",
                                                                "route01/route02");
    Assertions.assertFalse(cm.match("/shell02/route01/route02"));
  }

  @Test
  void match28() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/shell01/route01/route02/:parameter",
                                                                "route01/route02");
    Assertions.assertFalse(cm.match("shell01/route02"));
  }

  @Test
  void match29() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/[shell01|shell02]/route01/route02/:parameter",
                                                                "route01/route02");
    Assertions.assertTrue(cm.match("/shell01/route01/route02"));
  }

  @Test
  void match30() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/[shell01|shell02]/route01/route02/:parameter",
                                                                "route01/route02");
    Assertions.assertTrue(cm.match("shell01/route01/route02"));
  }

  @Test
  void match31() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/[shell01|shell02]/route01/route02/:parameter",
                                                                "route01/route02");
    Assertions.assertFalse(cm.match("/shell03/route01/route02"));
  }

  @Test
  void match32() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/[shell01|shell02]/route01/route02/:parameter",
                                                                "route01/route02");
    Assertions.assertFalse(cm.match("shell03/route02/route02"));
  }

  @Test
  void match33() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/*/route01/route02/:parameter",
                                                                "route01/route02");
    Assertions.assertTrue(cm.match("/shell01/route01/route02"));
  }

  @Test
  void match34() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/*/route01/route02/:parameter",
                                                                "route01/route02");
    Assertions.assertFalse(cm.match("shell01/route01/route03"));
  }

  @Test
  void match35() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/*/route01/route02/:parameter",
                                                                "route01");
    Assertions.assertFalse(cm.match("/shell02/route03/route02"));
  }

  @Test
  void match36() {
    ControllerModel cm = this.getControllerModelForMatchingTest("/*/route01/:parameter",
                                                                "route01");
    Assertions.assertFalse(cm.match("shell01/route02/route02"));
  }

  @SuppressWarnings("serial")
  private ControllerModel getControllerModelForMatchingTest(String route,
                                                            String routeWithoutShell) {
    return new ControllerModel(route,
                               routeWithoutShell,
                               "selector,",
                               Arrays.asList("parameter01",
                                             "parameter02"),
                               new ClassNameModel("com.github.nalukit.nalu.processor.common.ui.component01,Controller01"),
                               new ClassNameModel("com.github.nalukit.nalu.processor.common.ui.component01,IComponent01"),
                               new ClassNameModel("com.github.nalukit.nalu.processor.common.ui.component01,Component01"),
                               new ClassNameModel("com.github.nalukit.nalu.processor.common.ui.component01,Component01"),
                               new ClassNameModel("com.github.nalukit.nalu.processor.common.ui.component01,Component01"),
                               new ClassNameModel("com.github.nalukit.nalu.processor.common.ui.component01,Component01"),
                               false);

  }

}