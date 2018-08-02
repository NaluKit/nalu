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

package com.github.mvp4g.nalu.react.processor.model;

import com.github.mvp4g.nalu.react.processor.model.intern.ClassNameModel;
import com.github.mvp4g.nalu.react.processor.model.intern.IsMetaModel;

import java.util.Properties;

public class ApplicationMetaModel
  implements IsMetaModel {

  private static final String KEY_APPLICATION      = "application";
  private static final String KEY_LOADER           = "loader";
  private static final String KEY_SHELL            = "historyOnStart";

  private static final String KEY_HAS_DEBUG_ANNOTATION = "hasDebugAnnotation";
  private static final String KEY_DEBUG_LOG_LEVEL      = "debugLogLevel";
  private static final String KEY_DEBUG_LOGGER         = "debugLogger";

  private ClassNameModel application;
  private ClassNameModel loader;
  private ClassNameModel shell;


  private String         hasDebugAnnotation;
  private String         debugLogLevel;
  private ClassNameModel debugLogger;

  public ApplicationMetaModel(Properties properties) {
    this.application = new ClassNameModel(properties.getProperty(ApplicationMetaModel.KEY_APPLICATION));
    this.loader = new ClassNameModel(properties.getProperty(ApplicationMetaModel.KEY_LOADER));
    this.shell = new ClassNameModel(properties.getProperty(ApplicationMetaModel.KEY_SHELL));

    this.hasDebugAnnotation = properties.getProperty(ApplicationMetaModel.KEY_HAS_DEBUG_ANNOTATION);
    this.debugLogger = new ClassNameModel(properties.getProperty(ApplicationMetaModel.KEY_DEBUG_LOGGER));
    this.debugLogLevel = properties.getProperty(ApplicationMetaModel.KEY_DEBUG_LOG_LEVEL);
  }

  public ApplicationMetaModel(String application,
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

  public void setDebugLogLevel(String debugLogLevel) {
    this.debugLogLevel = debugLogLevel;
  }

  public ClassNameModel getDebugLogger() {
    return debugLogger;
  }

  public void setDebugLogger(String debugLogger) {
    this.debugLogger = new ClassNameModel(debugLogger);
  }

  @Override
  public Properties createPropertes() {
    Properties properties = new Properties();
    properties.setProperty(ApplicationMetaModel.KEY_APPLICATION,
                           this.application.getClassName());
    properties.setProperty(ApplicationMetaModel.KEY_LOADER,
                           this.loader.getClassName());
    properties.setProperty(ApplicationMetaModel.KEY_SHELL,
                           this.shell.getClassName());
    properties.setProperty(ApplicationMetaModel.KEY_HAS_DEBUG_ANNOTATION,
                           this.hasDebugAnnotation);
    properties.setProperty(ApplicationMetaModel.KEY_DEBUG_LOGGER,
                           this.debugLogger.getClassName());
    properties.setProperty(ApplicationMetaModel.KEY_DEBUG_LOG_LEVEL,
                           this.debugLogLevel);
    return properties;
  }
}
