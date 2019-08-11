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

import com.github.nalukit.nalu.client.application.IsContext;
import com.github.nalukit.nalu.client.internal.HandlerRegistrations;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

public abstract class AbstractCompositeController<C extends IsContext, V extends IsCompositeComponent<?, W>, W>
    extends AbstractController<C>
    implements IsComposite<W>,
               IsCompositeComponent.Controller {

  /* component of the controller */
  private String                    parentClassName;
  /* component of the controller */
  protected V                    component;
  /* list of registered handlers */
  protected HandlerRegistrations handlerRegistrations = new HandlerRegistrations();
  /* flag, if the controller is cached or not */
  private   boolean              cached;

  public AbstractCompositeController() {
    super();
  }

  /**
   * Returns the root element which will be attached to the DOM
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   *
   * @return root element
   */
  @NaluInternalUse
  @Override
  public W asElement() {
    return this.component.asElement();
  }

  /**
   * Gets the parent controller associated with this instance of the composite
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   *
   * @return the name of the class using this composite
   */
  @NaluInternalUse
  @Override
  public String getParentClassName() {
    return this.parentClassName;
  }

  /**
   * Sets the parent controller associated with this instance of the composite
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   *
   * @param parentClassName the name of the class using this composite
   */
  @NaluInternalUse
  @Override
  public void setParentClassName(String parentClassName) {
    this.parentClassName = parentClassName;
  }

  /**
   * Method is called during onAttach.
   * Nalu uses the method to call the onAttach-method of the compoent.
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   */
  @Override
  public final void onAttach() {
    component.onAttach();
  }

  /**
   * Method is called during onDetach.
   * Nalu uses the method to call the onDetach-method of the compoent.
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   */
  @Override
  public final void onDetach() {
    component.onDetach();
  }

  /**
   * internal framework method! Will be called by the framdework after the
   * stop-method of the controller is called
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   */
  @Override
  public final void removeHandlers() {
    this.handlerRegistrations.removeHandler();
    this.handlerRegistrations = new HandlerRegistrations();
  }

  @Override
  public String mayStop() {
    return null;
  }

  /**
   * The activate-method will be called instead of the start-method
   * in case the controller is cached.
   * <p>
   * If you have to do something in case controller gets active,
   * that's the right place.
   */
  @Override
  public void activate() {
  }

  /**
   * The deactivate-method will be called instead of the stop-method
   * in case the controller is cached.
   * <p>
   * If you have to do something in case controller gets deactivated,
   * that's the right place.
   */
  @Override
  public void deactivate() {
  }

  /**
   * The stop-method will be called at the start of the controller's life cycle.
   * <p>
   * If you have to do something in case controller gets active,
   * that's the right place.
   */
  @Override
  public void start() {
  }

  /**
   * The stop-method will be called at the end of the controller's life cycle.
   * <p>
   * If you have to do something in case controller gets inactive,
   * that's the right place.
   */
  @Override
  public void stop() {
  }

  /**
   * Get the component
   *
   * @return the compoment of the controller
   */
  public V getComponent() {
    return this.component;
  }

  public void setComponent(V component) {
    this.component = component;
  }

  /**
   * Removes all composite from the DOM by calling
   * the remove method of the composite component!
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   */
  @Override
  public void remove() {
    this.component.remove();
  }

  /**
   * Indicates, if the controller is newly created or not
   *
   * @return true: the controller is reused, false: the controller is newly created
   */
  public boolean isCached() {
    return cached;
  }

  /**
   * Sets the value, if the controller is newly created or cached!
   * <b>This field is used by Nalu! Setting the value can lead to unexpected behavior!</b>
   *
   * @param cached true: the controller is reused, false: the controller is newly created
   */
  public void setCached(boolean cached) {
    this.cached = cached;
  }

}
