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

package com.github.nalukit.nalu.client.tracker;

public interface IsTracker {

  /**
   * Right after the instance is created, the bind-method is called.
   * R.e.: this method can be used to bind handler to the event bus.
   */
  void bind();

  /**
   * Method is called to log a routing in case a new route is initiated.
   *
   * @param route  the new route
   * @param params the parameters of the new route
   */
  void track(String route,
             String... params);

}
