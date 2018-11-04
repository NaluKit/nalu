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

package com.github.nalukit.nalu.client.component;

/**
 * <p>Marks an class as a Nalu's shell.</p>
 */
public interface IsShell {

  /**
   * <p>
   * This method is used by the framework, to delegate the adding
   * of the shell to the user. Here the user has to add the shell
   * of the application to the viewport.
   * </p>
   * <code>
   * public void attachShell() {
   * RootLayoutPanel.get().add(view.asWidget());
   * }
   * </code>
   * <p>This will make the framework indepent of GWT or user implemantations!</p>
   */
  void attachShell();

  /**
   * <p>
   * This method is used by the framework, to delegate the removing
   * of the shell to the user. Here the user has to remove the shell
   * from the viewport.
   * </p>
   * <p>
   * It is a good idea to use a presenter/view pair as shell:
   * </p>
   * <code>
   * public void detachShell() {
   * view.removeFromParent();
   * }
   * </code>
   * <p>This will make the framework indepent of GWT or user implemantations!</p>
   */
  void detachShell();

  /**
   * Method will be called after a component is attached.
   * <p>
   * This is a good place to do a 'forceLayout()'.
   * f.e.: if you are working with GXT!
   */
  void onAttachedComponent();

  /**
   * Removes all registered handlers.
   */
  void removeHandlers();

}
