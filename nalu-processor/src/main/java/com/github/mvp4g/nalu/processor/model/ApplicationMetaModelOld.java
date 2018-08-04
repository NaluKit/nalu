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

package com.github.mvp4g.nalu.processor.model;

import com.github.mvp4g.nalu.processor.model.intern.ClassNameModel;
import com.github.mvp4g.nalu.processor.model.intern.IsMetaModel;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class ApplicationMetaModelOld
  implements IsMetaModel {

  private static final String KEY_APPLICATION      = "application";
  private static final String KEY_LOADER           = "loader";
  private static final String KEY_SHELL            = "historyOnStart";

  private static final String KEY_HAS_DEBUG_ANNOTATION = "hasDebugAnnotation";
  private static final String KEY_DEBUG_LOG_LEVEL      = "debugLogLevel";
  private static final String KEY_DEBUG_LOGGER         = "debugLogger";

  private static final String KEY_SELECTORS = "selectors";

  private ClassNameModel application;
  private ClassNameModel loader;
  private ClassNameModel shell;


  private String         hasDebugAnnotation;
  private String         debugLogLevel;
  private ClassNameModel debugLogger;


  private List<String> selectors;


  public ApplicationMetaModelOld(Properties properties) {
    this.application = new ClassNameModel(properties.getProperty(ApplicationMetaModelOld.KEY_APPLICATION));
    this.loader = new ClassNameModel(properties.getProperty(ApplicationMetaModelOld.KEY_LOADER));
    this.shell = new ClassNameModel(properties.getProperty(ApplicationMetaModelOld.KEY_SHELL));

    this.hasDebugAnnotation = properties.getProperty(ApplicationMetaModelOld.KEY_HAS_DEBUG_ANNOTATION);
    this.debugLogger = new ClassNameModel(properties.getProperty(ApplicationMetaModelOld.KEY_DEBUG_LOGGER));
    this.debugLogLevel = properties.getProperty(ApplicationMetaModelOld.KEY_DEBUG_LOG_LEVEL);

    this.selectors = Arrays.stream(properties.getProperty(ApplicationMetaModelOld.KEY_SELECTORS)
                                             .split("\\s*,\\s*"))
                           .collect(Collectors.toList());
  }

  public ApplicationMetaModelOld(String application,
                                 String loader,
                                 String shell) {
    this.application = new ClassNameModel(application);
    this.loader = new ClassNameModel(loader);
    this.shell = new ClassNameModel(shell);
  }

  public ClassNameModel getApplication() {
    return application;
  }

  public ClassNameModel getLoader() {
    return loader;
  }

  public ClassNameModel getShell() {
    return shell;
  }

  public boolean hasDebugAnnotation() {
    return "true".equals(hasDebugAnnotation);
  }

  public void setHasDebugAnnotation(String hasDebugAnnotation) {
    this.hasDebugAnnotation = hasDebugAnnotation;
  }

  public String getDebugLogLevel() {
    return debugLogLevel;
  }

  public void setDebugLogger(String debugLogger) {
    this.debugLogger = new ClassNameModel(debugLogger);
  }

  public ClassNameModel getDebugLogger() {
    return debugLogger;
  }

  public void setDebugLogLevel(String debugLogLevel) {
    this.debugLogLevel = debugLogLevel;
  }

  public List<String> getSelectors() {
    return selectors;
  }

  public Properties createPropertes() {
    Properties properties = new Properties();
    properties.setProperty(ApplicationMetaModelOld.KEY_APPLICATION,
                           this.application.getClassName());
    properties.setProperty(ApplicationMetaModelOld.KEY_LOADER,
                           this.loader.getClassName());
    properties.setProperty(ApplicationMetaModelOld.KEY_SHELL,
                           this.shell.getClassName());
    properties.setProperty(ApplicationMetaModelOld.KEY_HAS_DEBUG_ANNOTATION,
                           this.hasDebugAnnotation);
    properties.setProperty(ApplicationMetaModelOld.KEY_DEBUG_LOGGER,
                           this.debugLogger.getClassName());
    properties.setProperty(ApplicationMetaModelOld.KEY_DEBUG_LOG_LEVEL,
                           this.debugLogLevel);
    properties.setProperty(ApplicationMetaModelOld.KEY_SELECTORS,
                           String.join(",",
                                       String.join(",",
                                                   this.selectors)));
    return properties;
  }
}
