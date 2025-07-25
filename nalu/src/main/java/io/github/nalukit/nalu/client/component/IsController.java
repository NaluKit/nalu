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

package io.github.nalukit.nalu.client.component;

import io.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import io.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

import java.util.Map;

public interface IsController<V, W> {

  /**
   * Returns the root element which will be attached to the DOM
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   *
   * @return root element
   */
  @NaluInternalUse
  W asElement();

  void setComponent(V component);

  /**
   * Method is called during onAttach.
   * Nalu uses the method to call the onAttach-method of the component.
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   */
  @NaluInternalUse
  void onAttach();

  /**
   * Method is called during onDetach.
   * Nalu uses the method to call the onDetach-method of the component.
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   */
  @NaluInternalUse
  void onDetach();

  String mayStop();

  /**
   * internal framework method! Will be called by the framework after the
   * stop-method of the controller is called
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   */
  @NaluInternalUse
  void removeGlobalHandlers();

  /**
   * internal framework method! Will be called by the framework after the
   * deactivate-method of the controller is called
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   */
  @NaluInternalUse
  void removeHandlers();

  /**
   * The activate-method will be called besides the the start-method.
   * In opposite to the start-method, it will also be called in case the controller is cached.
   * <p>
   * If you have to do something in case controller gets active,
   * that's the right place.
   */
  void activate();

  /**
   * The deactivate-method will be called besides the the stop-method.
   * In opposite to the stop-method, it will also be called in case the controller is cached.
   * <p>
   * If you have to do something in case controller gets deactivated,
   * that's the right place.
   */
  void deactivate();

  /**
   * The start-method will be called in case a controller gets instantiated.
   * the method will not be called in case a controller is cached.
   * <p>
   * If you have to do something in case controller gets started,
   * that's the right place.
   */
  void start();

  /**
   * The stop-method will be called in case a controller is stopped.
   * the method will not be called in case a controller is cached.
   * <p>
   * If you have to do something in case controller gets stopped,
   * that's the right place.
   */
  void stop();

  /**
   * Returns the route the controller is related to.
   *
   * @return related route
   */
  String getRelatedRoute();

  /**
   * Returns the handling mode.
   *
   * @return value of {@link Mode}
   */
  Mode getMode();

  /**
   * The bind-method will be called before the component of the
   * controller is created.
   * <p>
   * This method runs before the component and composites are
   * created. This is f.e.: a got place to do some
   * authentication checks.
   * <p>
   * Keep in mind, that the method is asynchronous. Once you have
   * done your work, you have to call <b>loader.continueLoading()</b>.
   * Otherwise Nalu will stop working!
   * <p>
   * Inside the method the routing process can be interrupted
   * by throwing a RoutingInterceptionException.
   * <p>
   * <b>The method will not be called in case a controller is cached!</b>
   * <p>
   * <b>Attention:</b>
   * Do not call super.bind(loader)! Cause this will create a loop.
   *
   * @param loader loader to tell Nalu to continue loading the controller
   * @throws RoutingInterceptionException in case the create controller
   *                                      process should be interrupted
   */
  void bind(ControllerLoader loader)
      throws RoutingInterceptionException;

  /**
   * The unbind-method will be called before the controller is stopped.
   * <p>
   * This method runs after the mayStop-Method is called.
   * <p>
   * Keep in mind, that the method is asynchronous. Once you have
   * done your work, you have to call <b>continueUnloadCommand.continueUnloading()</b>.
   * However, Nalu will stop working!
   * <p>
   * Also, if you want to stop unloading, you need to call
   * <b>stopUnloadCommand.stopUnloadCommand()</b>. This will clear the Nalu state. Otherwise Nalu will stay
   * in a undefined state, which can cause problems with the next routing!
   * <p>
   * Inside the method the routing process cen be interrupted
   * by throwing a RoutingInterceptionException.
   * <p>
   * <b>The method will not be called in case a controller is cached!</b>
   * <p>
   * <b>Attention:</b>
   * Do not call super.unbind(continueUnloadCommand, stopUnloadCommand)! because this will create a loop.
   *
   * @param continueUnloadCommand call to tell Nalu to continue unloading the controller
   * @param stopUnloadCommand     call to tell Nalu to stop unloading the controller
   */
  void unbind(ContinueUnloadCommand continueUnloadCommand,
              StopUnloadCommand stopUnloadCommand);

  /**
   * The map of the depending composites of the shell
   *
   * @return Map of depending composites
   */
  Map<String, AbstractCompositeController<?, ?, ?>> getComposites();

  /**
   * Returns the composite stored under the composite name.
   *
   * @param name the name of the composite
   * @param <S>  type of the composite
   * @return instance of the composite
   */
  <S extends AbstractCompositeController<?, ?, ?>> S getComposite(String name);

  /**
   * Modes, that define Nalu's behavior in case the component is
   * already attached to the DOM.
   */
  enum Mode {

    /**
     * Choose <b>CREATE</b> in case you will always detach/attach
     * the component.
     * <p>
     * This is the default value.
     */
    CREATE,

    /**
     * Choose <b>REUSE</b> in case you will reuse the component as
     * long as the component is already is attached.
     */
    REUSE

  }



  interface ControllerLoader {

    void continueLoading();

  }



  interface ContinueUnloadCommand {

    void continueUnloading();

  }



  interface StopUnloadCommand {

    void stopUnloading();

  }

}
