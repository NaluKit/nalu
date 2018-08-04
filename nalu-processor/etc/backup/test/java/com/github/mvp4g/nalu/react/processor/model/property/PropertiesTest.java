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

package com.github.mvp4g.nalu.react.processor.model.property;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import com.github.mvp4g.nalu.react.processor.model.ApplicationMetaModel;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class PropertiesTest {

  @Test
  public void testApplicationMetaModel() {
    Properties properties = new Properties();
    try {
      properties.load(ClassLoader.getSystemResourceAsStream("META-INF" + File.separator + "mvp4g2" + File.separator + "application.properties"));
    } catch (IOException e) {
      assert false : "IOException reading application.properties";
    }

    ApplicationMetaModel metaModel = new ApplicationMetaModel(properties);
    Properties newProperties = metaModel.createPropertes();

    assertEquals(properties,
                 newProperties);
  }

  @Test
  public void testEventBusMetaModel() {
    Properties properties = new Properties();
    try {
      properties.load(ClassLoader.getSystemResourceAsStream("META-INF" + File.separator + "mvp4g2" + File.separator + "eventBus.properties"));
    } catch (IOException e) {
      assert false : "IOException reading eventBus.properties";
    }

    EventBusMetaModel metaModel = new EventBusMetaModel(properties);
    for (String eventInternalName : properties.getProperty("events").split(",")) {
      try {
        Properties eventProperties = new Properties();
        eventProperties.load(ClassLoader.getSystemResourceAsStream("META-INF" + File.separator + "mvp4g2" + File.separator + eventInternalName + ".properties"));
        metaModel.add(new EventMetaModel(eventProperties));
      } catch (IOException e) {
        assert false : "IOException reading " + eventInternalName + ".properties";
      }
    }

    Properties newProperties = metaModel.createPropertes();

    assertEquals(properties,
                 newProperties);
  }

  @Test
  public void testEventMetaModel() {
    Properties properties = new Properties();
    try {
      properties.load(ClassLoader.getSystemResourceAsStream("META-INF" + File.separator + "mvp4g2" + File.separator + "gotoList_pPp_java_lang_String_pPp_java_lang_String.properties"));
    } catch (IOException e) {
      assert false : "IOException reading gotoList_pPp_java_lang_String_pPp_java_lang_String.properties";
    }

    EventMetaModel metaModel = new EventMetaModel(properties);
    Properties newProperties = metaModel.createPropertes();

    assertEquals(properties,
                 newProperties);
  }

  @Test
  public void testHandlerMetaModel() {
    Properties properties = new Properties();
    try {
      properties.load(ClassLoader.getSystemResourceAsStream("META-INF" + File.separator + "mvp4g2" + File.separator + "handler.properties"));
    } catch (IOException e) {
      assert false : "IOException reading handler.properties";
    }

    HandlerMetaModel metaModel = new HandlerMetaModel(properties);
    Properties newProperties = metaModel.createPropertes();

    assertEquals(properties,
                 newProperties);
  }

  @Test
  public void testHistoryMetaModel() {
    Properties properties = new Properties();
    try {
      properties.load(ClassLoader.getSystemResourceAsStream("META-INF" + File.separator + "mvp4g2" + File.separator + "history.properties"));
    } catch (IOException e) {
      assert false : "IOException reading history.properties";
    }

    HistoryMetaModel metaModel = new HistoryMetaModel(properties);
    Properties newProperties = metaModel.createPropertes();

    assertEquals(properties,
                 newProperties);
  }

  @Test
  public void testPresenteryMetaModel() {
    Properties properties = new Properties();
    try {
      properties.load(ClassLoader.getSystemResourceAsStream("META-INF" + File.separator + "mvp4g2" + File.separator + "presenter.properties"));
    } catch (IOException e) {
      assert false : "IOException reading presenter.properties";
    }

    PresenterMetaModel metaModel = new PresenterMetaModel(properties);
    Properties newProperties = metaModel.createPropertes();

    assertEquals(properties,
                 newProperties);
  }
}