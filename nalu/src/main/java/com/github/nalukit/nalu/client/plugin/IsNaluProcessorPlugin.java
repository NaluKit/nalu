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

package com.github.nalukit.nalu.client.plugin;

import com.github.nalukit.nalu.client.internal.route.ShellConfiguration;

import java.util.Map;

public interface IsNaluProcessorPlugin {
  
  /**
   * Display an alert message.
   * <p>
   * Note:<br>
   * application flow needs to stop after calling this method.
   * </p>
   *
   * @param message message to display
   */
  void alert(String message);
  
  boolean attach(String selector,
                 Object asElement);
  
  void confirm(String message,
               ConfirmHandler handler);
  
  String getStartRoute();
  
  Map<String, String> getQueryParameters();
  
  void register(RouteChangeHandler handler);
  
  void remove(String selector);
  
  void route(String newRoute,
             boolean replace);
  
  void initialize(ShellConfiguration shellConfiguration);
  
  void updateTitle(String title);
  
  void updateMetaNameContent(String name,
                             String content);
  
  void updateMetaPropertyContent(String property,
                                 String content);
  
  String decode(String route);
  
  void setCustomAlertPresenter(IsCustomAlertPresenter presenter);
  
  void setCustomConfirmPresenter(IsCustomConfirmPresenter presenter);
  
  @FunctionalInterface
  interface RouteChangeHandler {
    
    void onRouteChange(String newRoute);
    
  }
  
  
  
  interface ConfirmHandler {
    
    void onOk();
    
    void onCancel();
    
  }
  
}