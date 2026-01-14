/*
 * Copyright (c) 2020 - Frank Hossfeld
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

package io.github.nalukit.nalu.plugin.core.web.client;

import io.github.nalukit.nalu.client.internal.route.ShellConfiguration;
import io.github.nalukit.nalu.client.plugin.IsNaluProcessorPlugin.RouteChangeHandler;
import io.github.nalukit.nalu.plugin.core.web.client.implementation.DefaultCoreWebPlugin;
import io.github.nalukit.nalu.plugin.core.web.client.model.NaluStartModel;

public class NaluCorePluginFactory
    implements IsNaluCorePlugin {

  public final static NaluCorePluginFactory INSTANCE = new NaluCorePluginFactory();

  private IsNaluCorePlugin plugin;

  public NaluCorePluginFactory() {
    this.plugin = new DefaultCoreWebPlugin();
  }

  public static boolean isSuperDevMode() {
    return "on".equals(System.getProperty("superdevmode",
                                          "off"));
  }

  public void registerPlugin(IsNaluCorePlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void getContextPath(ShellConfiguration shellConfiguration) {
    this.plugin.getContextPath(shellConfiguration);
  }

  @Override
  public NaluStartModel getNaluStartModel() {
    return this.plugin.getNaluStartModel();
  }

  @Override
  public void addPopStateHandler(RouteChangeHandler handler,
                                 String contextPath) {
    this.plugin.addPopStateHandler(handler,
                                   contextPath);
  }

  @Override
  public void route(String newRoute,
                    boolean replace,
                    boolean stealthMode,
                    RouteChangeHandler handler) {
    this.plugin.route(newRoute,
                      replace,
                      stealthMode,
                      handler);
  }

  @Override
  public void addOnHashChangeHandler(RouteChangeHandler handler) {
    this.plugin.addOnHashChangeHandler(handler);
  }
}
