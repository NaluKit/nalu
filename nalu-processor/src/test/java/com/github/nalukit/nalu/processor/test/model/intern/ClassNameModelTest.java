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

package com.github.nalukit.nalu.processor.test.model.intern;

import com.github.nalukit.nalu.client.application.IsApplication;
import com.github.nalukit.nalu.processor.model.intern.ClassNameModel;
import com.squareup.javapoet.ClassName;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClassNameModelTest {

  private ClassNameModel classNameModel;

  @Before
  public void before() {
    classNameModel = new ClassNameModel(IsApplication.class.getCanonicalName());
  }

  @Test
  public void getClassName() {
    assertEquals(IsApplication.class.getCanonicalName(),
                 this.classNameModel.getClassName());
  }

  @Test
  public void getTypeName() {
    ClassName expectedtypeName = ClassName.get(IsApplication.class);
    assertEquals(expectedtypeName,
                 this.classNameModel.getTypeName());
  }

  @Test
  public void getPackage() {
    assertEquals(IsApplication.class.getPackage()
                                    .getName(),
                 this.classNameModel.getPackage());
  }

  @Test
  public void getSimpleName() {
    assertEquals(IsApplication.class.getPackage()
                                    .getName(),
                 this.classNameModel.getPackage());
  }

  @Test
  public void equals() {
    assertTrue(this.classNameModel.equals(new ClassNameModel(IsApplication.class.getCanonicalName())));
  }
}
