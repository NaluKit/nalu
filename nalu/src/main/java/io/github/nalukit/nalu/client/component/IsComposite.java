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

import io.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

public interface IsComposite<W> {

  /**
   * Returns the root element which will be attached to the DOM
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   *
   * @return root element
   */
  @NaluInternalUse
  W asElement();

  /**
   * Gets the parent controller associated with this instance of the composite
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   *
   * @return the name of the class using this composite
   */
  @NaluInternalUse
  String getParentClassName();

  /**
   * Sets the parent controller associated with this instance of the composite
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   *
   * @param parentClassName the name of the class using this composite
   */
  @NaluInternalUse
  void setParentClassName(String parentClassName);

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

}
