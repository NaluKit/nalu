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

/**
 * <p>Marks an class as a Nalu's shellCreator.</p>
 */
public interface IsShell {

  /**
   * <p>
   * This method is used by the framework, to delegate the adding
   * of the shellCreator to the user. Here the user has to add the shellCreator
   * of the application to the viewport.
   * </p>
   * <code>
   * public void attachShell() {
   * RootLayoutPanel.get().add(view.asWidget());
   * }
   * </code>
   * <p>This will make the framework independent of GWT or user implementations!</p>
   */
  void attachShell();

  /**
   * <p>
   * This method is used by the framework, to delegate the removing
   * of the shellCreator to the user. Here the user has to remove the shellCreator
   * from the viewport.
   * </p>
   * <p>
   * It is a good idea to use a presenter/view pair as shellCreator:
   * </p>
   * <code>
   * public void detachShell() {
   * view.removeFromParent();
   * }
   * </code>
   * <p>This will make the framework independent of GWT or user implementations!</p>
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

  /**
   * The bind-method will be called before the shell is added to the viewport.
   * <p>
   * This method runs before the component and composites are
   * created. This is f.e.: a got place to do some
   * authentication checks.
   * <p>
   * Keep in mind, that the method is asynchronous. Once you have
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
   * continue loading!
   *
   * @param loader loader to tell Nalu to continue loading the controller
   * @throws RoutingInterceptionException in case the create controller
   *                                      process should be interrupted
   */
  void bind(ShellLoader loader)
      throws RoutingInterceptionException;

  interface ShellLoader {

    void continueLoading();

  }

}
