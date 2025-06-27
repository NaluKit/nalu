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
package io.github.nalukit.nalu.client.application.annotation;

import io.github.nalukit.nalu.client.application.IsClientLogger;
import io.github.nalukit.nalu.client.application.IsLogger;
import io.github.nalukit.nalu.client.internal.NoCustomLogger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation let you add a logger to Nalu. Logging messages will be triggered by events.
 * The annotation has the following attribute:
 * <ul>
 * <li>logger: logger class</li>
 * </ul>
 * <br>
 * This annotation should be used only on interfaces that extend <code>IsApplication</code>.
 * <br>
 * The annotation is optional.
 *
 * @author Frank Hossfeld
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Logger {

  /**
   * Defines the logger.
   *
   * @return the logger
   */
  Class<? extends IsLogger<?>> logger() default NoCustomLogger.class;

  /**
   * Defines the client logger.
   *
   * @return the custom logger
   */
  Class<? extends IsClientLogger> clientLogger();

}
