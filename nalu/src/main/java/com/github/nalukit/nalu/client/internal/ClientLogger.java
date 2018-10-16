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

package com.github.nalukit.nalu.client.internal;

import com.github.nalukit.nalu.client.application.IsLogger;
import com.github.nalukit.nalu.client.application.annotation.Debug;

public class ClientLogger {

  private static ClientLogger instance = new ClientLogger();

  /* debug enabled? */
  private boolean debugEnabled = false;

  /* logger */
  private IsLogger logger;

  /* log level */
  private Debug.LogLevel logLevel;

  private ClientLogger() {
  }

  public static ClientLogger get() {
    if (instance == null) {
      instance = new ClientLogger();
    }
    return instance;
  }

  public void register(boolean debugEnabled,
                       IsLogger logger,
                       Debug.LogLevel logLevel) {
    this.debugEnabled = debugEnabled;
    this.logger = logger;
    this.logLevel = logLevel;
  }

  public void logDetailed(String message,
                          int depth) {
    if (this.debugEnabled) {
      if (Debug.LogLevel.DETAILED.equals(this.logLevel)) {
        if (logger != null) {
          logger.log(message,
                     depth);
        }
      }
    }
  }

  public void logSimple(String message,
                        int depth) {
    if (this.debugEnabled) {
      if (logger != null) {
        logger.log(message,
                   depth);
      }
    }
  }
}
