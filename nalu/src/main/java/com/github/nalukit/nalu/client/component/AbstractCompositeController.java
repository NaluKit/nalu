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

import com.github.nalukit.nalu.client.context.IsContext;
import com.github.nalukit.nalu.client.internal.HandlerRegistrations;
import com.github.nalukit.nalu.client.internal.NaluCommand;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

public abstract class AbstractCompositeController<C extends IsContext, V extends IsCompositeComponent<?, W>, W>
    extends AbstractController<C>
    implements IsComposite<W>,
               IsCompositeComponent.Controller {

  /* component of the controller */
  protected V                    component;
  /* list of registered global handlers */
  protected HandlerRegistrations globalHandlerRegistrations = new HandlerRegistrations();
  /* list of registered handlers */
  protected HandlerRegistrations handlerRegistrations       = new HandlerRegistrations();
  /* component of the controller */
  private   String               parentClassName;
  /* selector */
  private   String               selector;
  /* flag, if the controller is cached or not */
  private   boolean              cached;
  /* flag, if the controller is cached or not in Scope GLOBAL! */
  private boolean     cachedGlobal;
  /* internal Nalu request. Don't use this */
  private NaluCommand activateNaluCommand;

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
  public final W asElement() {
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
  public final String getParentClassName() {
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
  public final void setParentClassName(String parentClassName) {
    this.parentClassName = parentClassName;
  }

  /**
   * Method is called during onAttach.
   * Nalu uses the method to call the onAttach-method of the component.
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   */
  @Override
  public final void onAttach() {
    component.onAttach();
  }

  /**
   * Method is called during onDetach.
   * Nalu uses the method to call the onDetach-method of the component.
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   */
  @NaluInternalUse
  @Override
  public final void onDetach() {
    component.onDetach();
  }

  @Override
  public String mayStop() {
    return null;
  }

  /**
   * internal framework method! Will be called by the framework after the
   * stop-method of the controller is called
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   */
  @NaluInternalUse
  @Override
  public final void removeGlobalHandlers() {
    this.globalHandlerRegistrations.removeHandler();
    this.globalHandlerRegistrations = new HandlerRegistrations();
  }

  /**
   * internal framework method! Will be called by the framework after the
   * deactivate-method of the controller is called
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   */
  @NaluInternalUse
  @Override
  public final void removeHandlers() {
    this.handlerRegistrations.removeHandler();
    this.handlerRegistrations = new HandlerRegistrations();
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
   * @return the component of the controller
   */
  public V getComponent() {
    return this.component;
  }

  @NaluInternalUse
  public final void setComponent(V component) {
    this.component = component;
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

  /**
   * Indicates, if the controller is newly created or not
   *
   * @return true: the controller is reused, false: the controller is newly created
   */
  public boolean isCachedGlobal() {
    return cachedGlobal;
  }

  /**
   * Sets the value, if the controller is newly created or cached!
   * <b>This field is used by Nalu! Setting the value can lead to unexpected behavior!</b>
   *
   * @param cachedGlobal true: the controller is reused, false: the controller is newly created
   */
  public void setCachedGlobal(boolean cachedGlobal) {
    this.cachedGlobal = cachedGlobal;
  }

  /**
   * Returns the selector of the composite.
   * <p>
   * Attention:
   * Not set in case of global caching is turned on!
   *
   * @return selector
   */
  public String getSelector() {
    return selector;
  }

  /**
   * sets the selector
   * <p>
   * Attention:
   * Not set in case of global caching is turned on!
   *
   * @param selector THe selector, the composite is ocated inside the DOM
   */
  @NaluInternalUse
  public final void setSelector(String selector) {
    this.selector = selector;
  }

  /**
   * Gets the activate Nalu command. This will be used by Nalu in case the controller gets activated.
   * <b>Do not use this method. This will lead to unexpected results</b>
   *
   * @return
   */
  @NaluInternalUse
  public final NaluCommand getActivateNaluCommand() {
    return activateNaluCommand;
  }

  /**
   * Sets the activate Nalu command. This will be used by Nalu in case the controller gets activated.
   * <b>Do not use this method. This will lead to unexpected results</b>
   *
   * @param activateNaluCommand Nalu activation command
   */
  @NaluInternalUse
  public final void setActivateNaluCommand(NaluCommand activateNaluCommand) {
    this.activateNaluCommand = activateNaluCommand;
  }

  /**
   * Gets the handler registrations of this controller
   * <b>Do not use this method. This will lead to unexpected results</b>
   *
   * @return handler registrations
   */
  @NaluInternalUse
  public HandlerRegistrations getHandlerRegistrations() {
    return this.handlerRegistrations;
  }

}
