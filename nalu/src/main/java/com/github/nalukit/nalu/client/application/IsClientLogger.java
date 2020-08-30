/*
 * Copyright (c) 2018 - 2020 - Frank Hossfeld
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
package com.github.nalukit.nalu.client.application;

/**
 * Interface that defines a logger used for events logging.
 *
 * @author Frank Hossfeld
 */
public interface IsClientLogger {
  
  /**
   * Writes a log message to the browser console
   *
   * @param message message to log
   */
  void log(String message);
  
  /**
   * Writes a log message to the browser console during development
   *
   * @param message message to log
   * @param depth   depth of the log
   */
  @Deprecated
  void log(String message,
           int depth);
  
}
