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
package com.github.mvp4g.nalu.client.internal.application;

import com.github.mvp4g.nalu.client.Nalu;
import com.github.mvp4g.nalu.client.internal.annotation.NaluInternalUse;
import com.github.mvp4g.nalu.client.application.IsLogger;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Default implementation of Mvp4gLogger.
 *
 * @author plcoirier
 */
@NaluInternalUse
public class DefaultLogger
  implements IsLogger {

  static final String INDENT = "    ";

  public void log(String message,
                  int depth) {
    Nalu.log(createLog(message,
                       depth));
  }

  String createLog(String message,
                   int depth) {
    if (depth == 0) {
      return "Nalu-Logger -> " + message;
    } else {
      String indent = IntStream.range(0,
                                      depth)
                               .mapToObj(i -> INDENT)
                               .collect(Collectors.joining("",
                                                           "",
                                                           message));
      return "Nalu-Logger -> " + indent;
    }
  }
}
