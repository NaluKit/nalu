/*
 * Copyright (c) 2018 - 2019 - Frank Hossfeld
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

package com.github.nalukit.nalu.client.component;

import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;

public interface IsController<V, W> {

  W asElement();

  void setComponent(V component);

  void onAttach();

  void onDetach();

  String mayStop();

  void removeHandlers();

  /**
   * The activate-method will be called instead of the start-method
   * in case the controller is cached.
   * <p>
   * If you have to do something in case controller gets active,
   * that's the right place.
   */
  void activate();

  /**
   * The deactivate-method will be called instead of the stop-method
   * in case the controller is cached.
   * <p>
   * If you have to do something in case controller gets deactivated,
   * that's the right place.
   */
  void deactivate();

  void start();

  void stop();

  /**
   * The bind-method will be called before the component of the
   * controller is created.
   * <p>
   * This method runs before the component and composites are
   * created. This is f.e.: a got place to do some
   * authentification checks.
   * <p>
   * Keep in mind, that the method is asynchron. Once you have
   * done your work, you have to call <b>loader.continueLoading()</b>.
   * Otherwise Nalu will stop working!
   * <p>
   * Inside the method can the routing process gets interrupted
   * by throwing a RoutingInterceptionException.
   * <p>
   * The method will not be called in case a controller is cached!
   * <p>
   * Attention:
   * Do not call super.bind(loader)! Cause this will tell Nalu to
   * continue laoding!
   *
   * @param loader loader to tell Nalu to continue loading the controller
   * @throws RoutingInterceptionException in case the create contrioller
   *                                      process should be interrupted
   */
  void bind(ControllerLoader loader)
      throws RoutingInterceptionException;

  interface ControllerLoader {

    void continueLoading();

  }

}
