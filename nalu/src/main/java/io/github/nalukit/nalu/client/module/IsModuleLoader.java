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

package io.github.nalukit.nalu.client.module;

import io.github.nalukit.nalu.client.IsRouter;
import io.github.nalukit.nalu.client.context.AbstractModuleContext;
import io.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import org.gwtproject.event.shared.SimpleEventBus;

/**
 * Nalu module loader
 * <br>
 * <p>The Loader is executed during the start sequence of the application.
 * The loader can be used to load meta-information during the load sequence of a module.</p>
 * <p>Once the work is done call finishLoadCommand.finishLoad() to resume with the normal processing.</p>
 * <br>
 * <p><b>Caution: Do not use the router to route inside the loader!Just use it only to inject it!</b></p>
 */
public interface IsModuleLoader<C extends AbstractModuleContext> {

  /**
   * Calls the module loader.
   * <br>
   * Implement here the code you want to execute durng module start.
   * <br>
   * Once you are done, call: <b>finishLoadCommand.finishLoading();</b>
   * <br>
   * <b>Attention:</b>
   * <br>
   * Do not call the method directly!
   *
   * @param finishLoadCommand use this command to give the control back to Nalu
   */
  void load(FinishLoadCommand finishLoadCommand);

  @NaluInternalUse
  void setContext(C context);

  @NaluInternalUse
  void setEventBus(SimpleEventBus eventBus);

  @NaluInternalUse
  void setRouter(IsRouter router);

  interface FinishLoadCommand {

    void finishLoading();

  }

}
