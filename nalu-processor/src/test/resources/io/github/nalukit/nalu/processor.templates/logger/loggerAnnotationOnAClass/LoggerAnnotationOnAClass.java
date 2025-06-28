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

package io.github.nalukit.nalu.processor.logger.loggerAnnotationOnAClass;

import io.github.nalukit.nalu.client.application.IsApplication;
import io.github.nalukit.nalu.client.application.annotation.Application;
import io.github.nalukit.nalu.client.application.annotation.Logger;
import io.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin;
import io.github.nalukit.nalu.processor.common.MockContext;
import io.github.nalukit.nalu.processor.common.MockDebug;
import io.github.nalukit.nalu.processor.common.MockLogger;

@Application(startRoute = "/search",
             context = MockContext.class)
@Logger(clientLogger = MockDebug.class,
        logger = MockLogger.class)
public class LoggerAnnotationOnAClass
    implements IsApplication {

  public void run(IsNaluProcessorPlugin plugin) {
  }
}
