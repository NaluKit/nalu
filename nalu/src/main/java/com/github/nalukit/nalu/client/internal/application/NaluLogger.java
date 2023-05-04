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

package com.github.nalukit.nalu.client.internal.application;

import com.github.nalukit.nalu.client.application.IsClientLogger;
import com.github.nalukit.nalu.client.application.IsLogger;
import com.github.nalukit.nalu.client.application.event.LogEvent;
import com.github.nalukit.nalu.client.context.IsContext;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import org.gwtproject.event.shared.SimpleEventBus;

import java.util.Objects;

@NaluInternalUse
public class NaluLogger<C extends IsContext> {

  private SimpleEventBus eventBus;
  private IsClientLogger clientLogger;
  private IsLogger<C>    customLogger;

  protected NaluLogger() {
  }

  /**
   * Binds the {@link LogEvent}
   */
  @NaluInternalUse
  public final void bind() {
    this.eventBus.addHandler(LogEvent.TYPE,
                             this::onLog);
  }

  /**
   * Method will be called in case a log event occurs.
   *
   * @param event the Log Event containing all information
   */
  private void onLog(LogEvent event) {
    if (event.isSdmOnly()) {
      if (!this.isSDM()) {
        return;
      }
    }

    if (!Objects.isNull(this.clientLogger)) {
        event.getMessages()
             .forEach(m -> this.clientLogger.log(m));
    }

    if (!Objects.isNull(this.customLogger)) {
      this.customLogger.log(event.getMessages(),
                            event.isSdmOnly());
    }
  }

  private boolean isSDM() {
    return "on".equals(System.getProperty("superdevmode",
                                          "off"));
  }

  /**
   * Sets the client logger.
   *
   * @param clientLogger applciation context
   */
  @NaluInternalUse
  public final void setClientLogger(IsClientLogger clientLogger) {
    this.clientLogger = clientLogger;
  }

  /**
   * Sets the event bus
   *
   * @param eventBus application event bus
   */
  @NaluInternalUse
  public final void setEventBus(SimpleEventBus eventBus) {
    this.eventBus = eventBus;
  }

  /**
   * Sets the custom logger
   *
   * @param customLogger custom logger
   */
  @NaluInternalUse
  public final void setCustomLogger(IsLogger<C> customLogger) {
    this.customLogger = customLogger;
  }

}
