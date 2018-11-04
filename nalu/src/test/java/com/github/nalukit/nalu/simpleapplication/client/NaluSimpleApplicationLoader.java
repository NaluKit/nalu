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

package com.github.nalukit.nalu.simpleapplication.client;

import com.github.nalukit.nalu.client.application.AbstractApplicationLoader;

/**
 * A application loader of the NaluSimpleApplication
 */
public class NaluSimpleApplicationLoader
    extends AbstractApplicationLoader<NaluSimpleApplicationContext> {

  /**
   * The laoder of the application.
   * <p>
   * Will be executed at the start of the application
   * and before the first event is executed.
   *
   * @param finishLoadCommand has to be called after the application has finieshed loading
   */
  @Override
  public void load(FinishLoadCommand finishLoadCommand) {
    finishLoadCommand.finishLoading();
  }
}
