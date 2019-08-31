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

package com.github.nalukit.nalu.client.internal.route;

import com.github.nalukit.nalu.client.Router;
import org.gwtproject.event.shared.SimpleEventBus;

public interface ConfiguratableRouter
    extends Router {

  /**
   * handle router exception.
   *
   * @param hash hash on start
   * @param e the RouterException
   */
  void handleRouterException(String hash,
                             RouterException e);

  /**
   * Parse the route!
   *
   * @param route the route to be parsed ...
   * @return the hashResult class of this route!
   * @throws RouterException in case the parsing fails
   */
  RouteResult parse(String route)
      throws RouterException;

  /**
   * Set's the error route inside the router.
   *
   * @param routeError the route to be executed in case of error!
   */
  void setRouteError(String routeError);

  /**
   * sets the eventbus inside the router
   *
   * @param eventBus Nalu application eventbus
   */
  void setEventBus(SimpleEventBus eventBus);

}
