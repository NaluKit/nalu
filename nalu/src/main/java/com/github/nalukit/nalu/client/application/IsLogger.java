/*
 * Copyright (c) 2018 Frank Hossfeld
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

import com.github.nalukit.nalu.client.context.IsContext;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

import java.util.List;

/**
 * Interface that defines a logger used for events logging.
 *
 * @author Frank Hossfeld
 */
public interface IsLogger<C extends IsContext> {

  /**
   * Sets the context.
   *
   * @param context applciaiton context
   */
  @NaluInternalUse
  void setContext(C context);

  /**
   * Writes a log message to the browser console during development
   *
   * @param messages list of messages to log
   * @param sdmOnly  indicates if the log should only appear in SDM
   */
  void log(List<String> messages,
           boolean sdmOnly);

}
