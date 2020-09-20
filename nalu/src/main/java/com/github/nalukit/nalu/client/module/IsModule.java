/*
 * Copyright (c) 2018 - 2020 - Frank Hossfeld
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

package com.github.nalukit.nalu.client.module;

import com.github.nalukit.nalu.client.Router;
import com.github.nalukit.nalu.client.component.AlwaysLoadComposite;
import com.github.nalukit.nalu.client.context.IsModuleContext;
import com.github.nalukit.nalu.client.internal.CompositeControllerReference;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import com.github.nalukit.nalu.client.internal.route.RouteConfig;
import com.github.nalukit.nalu.client.internal.route.RouterConfiguration;
import com.github.nalukit.nalu.client.internal.route.ShellConfig;
import org.gwtproject.event.shared.SimpleEventBus;

import java.util.List;

public interface IsModule<C extends IsModuleContext> {
  
  /**
   * Sets the alwaysLoadComposite flag inside the router
   *
   * @param alwaysLoadComposite the alwaysLoadComposite flag
   */
  @NaluInternalUse
  void setAlwaysLoadComposite(AlwaysLoadComposite alwaysLoadComposite);
  
  /**
   * Sets the event bus inside the router
   *
   * @param eventBus Nalu application event bus
   */
  @NaluInternalUse
  void setEventBus(SimpleEventBus eventBus);
  
  /**
   * Sets the router inside the router
   *
   * @param router Nalu application router
   */
  @NaluInternalUse
  void setRouter(Router router);
  
  @NaluInternalUse
  void loadModule(RouterConfiguration routeConfiguration);
  
  @NaluInternalUse
  List<ShellConfig> getShellConfigs();
  
  @NaluInternalUse
  List<RouteConfig> getRouteConfigs();
  
  @NaluInternalUse
  List<CompositeControllerReference> getCompositeReferences();
  
}
