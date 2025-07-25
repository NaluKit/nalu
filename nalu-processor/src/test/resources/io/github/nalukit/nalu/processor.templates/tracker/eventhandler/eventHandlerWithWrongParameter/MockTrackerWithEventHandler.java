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

package io.github.nalukit.nalu.processor.tracker.eventhandler.eventHandlerWithWrongParameter;

import io.github.nalukit.nalu.client.event.annotation.EventHandler;
import io.github.nalukit.nalu.client.tracker.AbstractTracker;
import io.github.nalukit.nalu.client.tracker.IsTracker;
import io.github.nalukit.nalu.processor.common.MockContext;

/**
 * Default implementation of Nalu's logger.
 *
 * @author Frank Hossfeld
 */
public class MockTrackerWithEventHandler
    extends AbstractTracker<MockContext>
    implements IsTracker {

  @EventHandler
  public void handleEvent(String event01) {

  }

  @Override
  public void bind() {
  }

  @Override
  public void track(String route,
                    String... params) {
  }

}

