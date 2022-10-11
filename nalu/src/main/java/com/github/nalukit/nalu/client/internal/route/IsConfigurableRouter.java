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

package com.github.nalukit.nalu.client.internal.route;

import com.github.nalukit.nalu.client.IsRouter;
import com.github.nalukit.nalu.client.component.AlwaysLoadComposite;
import com.github.nalukit.nalu.client.component.AlwaysShowPopUp;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import com.github.nalukit.nalu.client.module.IsModule;
import org.gwtproject.event.shared.SimpleEventBus;

public interface IsConfigurableRouter
    extends IsRouter {

  /**
   * handle router exception.
   *
   * @param hash hash on start
   * @param e    the RouterException
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
   * Sets the alwaysLoadComposite instance
   *
   * @param alwaysLoadComposite the alwaysLoadComposite instance
   */
  @NaluInternalUse
  void setAlwaysLoadComposite(AlwaysLoadComposite alwaysLoadComposite);

  /**
   * Sets the alwaysShowPopUp instance
   *
   * @param alwaysShowPopUp the alwaysShowPopUp instance
   */
  @NaluInternalUse
  void setAlwaysShowPopUp(AlwaysShowPopUp alwaysShowPopUp);

  /**
   * Sets the event bus inside the router
   *
   * @param eventBus Nalu application event bus
   */
  @NaluInternalUse
  void setEventBus(SimpleEventBus eventBus);

  /**
   * Add a module to the application.
   * <p>
   * The method wil inject the router, event bus, context
   * and the alwaysLoadComposite-flag into the module.
   * <p>
   * Besides that, the method adds the shell- and
   * route-configuration and also the controller-configurations.
   *
   * @param module the new module to add to the application
   * @param <M>    Type of the module.
   */
  <M extends IsModule<?>> void addModule(M module);

}
