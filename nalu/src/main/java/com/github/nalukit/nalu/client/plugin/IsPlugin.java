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

package com.github.nalukit.nalu.client.plugin;

import com.github.nalukit.nalu.client.application.IsContext;
import com.github.nalukit.nalu.client.internal.route.RouteConfig;
import com.github.nalukit.nalu.client.internal.route.ShellConfig;

import java.util.List;

public interface IsPlugin<C extends IsContext> {

  /**
   * Loads the plugin features.
   * <p>
   * FeEatures are controller, composite controllers, shells, handlers and filters
   */
  void loadPlugin();
//
//  /**
//   * returns the list of shells defined in this plugin.
//   * // TODO loadShells ist das
//   *
//   * @return List of shell configs
//   */
//  List<ShellConfig> getShellConfig();
//
//  /**
//   * Add the routes to route configurations
//   *
//   * @retuns a list of route configs
//   */
//  List<RouteConfig> getRouteConfig();
//
//  /**
//   * Adds the ShellCreator to the ShellFactory
//   */
//  void loadShellFactory();
//
//  /**
//   * Register the CompositeController at the CompositeFactory
//   */
//  void loadCompositeController();
//
//  /**
//   * Register the ControllerCerator at the ControllerFactory
//   */
//  void loadComponents();
//
//  /**
//   * Loads the filters of the plugin.
//   */
//  void loadFilters();
//
//  /**
//   * Loads the handlers of the plugin
//   */
//  void loadHandlers();
//
//  /**
//   * Loads the composite references
//   */
//  void loadCompositeReferences();

}
