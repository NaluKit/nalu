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

package com.github.nalukit.nalu.client.component;

import com.github.nalukit.nalu.client.context.IsContext;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.HandlerRegistrations;
import com.github.nalukit.nalu.client.internal.NaluCommand;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractComponentController<C extends IsContext, V extends IsComponent<?, W>, W>
    extends AbstractController<C>
    implements IsController<V, W>,
               IsComponent.Controller {

  /* list fo composite controllers */
  private final Map<String, AbstractCompositeController<?, ?, ?>> compositeControllers;
  /* component of the controller */
  protected     V                                                 component;
  /* list of registered global handlers */
  protected     HandlerRegistrations                              globalHandlerRegistrations = new HandlerRegistrations();
  /* list of registered handlers */
  protected     HandlerRegistrations                              handlerRegistrations       = new HandlerRegistrations();
  /* the route the controller is related to */
  private       String                                            relatedRoute;
  /* the selector the controller is related to */
  private       String                                            relatedSelector;
  /* flag, if the controller is cached or not */
  private       boolean                                           cached;
  /* redraw mode */
  private       Mode                                              mode;
  /* internal Nalu request. Don't use this */
  private       NaluCommand                                       activateNaluCommand;

  public AbstractComponentController() {
    super();
    this.compositeControllers = new HashMap<>();
    // set the default redrawMode
    this.mode = Mode.CREATE;
  }

  /**
   * Returns the element of the component. Will be used by Nalu
   * to add it to the DOM.
   *
   * @return the element of the component
   */
  @Override
  @NaluInternalUse
  public final W asElement() {
    return this.component.asElement();
  }

  /**
   * Returns the composite stored under the composite name.
   *
   * @param name the name of the composite
   * @param <S>  type of the composite
   * @return instance of the composite
   */
  @SuppressWarnings({ "unchecked",
                      "TypeParameterUnusedInFormals" })
  public <S extends AbstractCompositeController<?, ?, ?>> S getComposite(String name) {
    return (S) this.getComposites()
                   .get(name);
  }

  /**
   * The map of the depending composites of the controller
   *
   * @return Map of depending composites
   */
  public Map<String, AbstractCompositeController<?, ?, ?>> getComposites() {
    return compositeControllers;
  }

  /**
   * The selector the controller is related to.
   *
   * @return related selector
   */
  public String getRelatedSelector() {
    return relatedSelector;
  }

  /**
   * Sets the related selector of the controller. (Will be used by the framework!)
   * <b>Do not use this method. This will lead to unexpected results</b>
   *
   * @param relatedSelector related route of the controller
   */
  @NaluInternalUse
  public final void setRelatedSelector(String relatedSelector) {
    this.relatedSelector = relatedSelector;
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
   * Get the component
   *
   * @return the component of the controller
   */
  public V getComponent() {
    return this.component;
  }

  /**
   * Sets the component inside the controller
   * <b>Do not use this method. This will lead to unexpected results</b>
   *
   * @param component instance of the component
   */
  @Override
  @NaluInternalUse
  public final void setComponent(V component) {
    this.component = component;
  }

  /**
   * Method will be called in case the element is attached to the DOM.
   * <p>
   * The method is used by the framework!
   * <p>
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   */
  @NaluInternalUse
  @Override
  public final void onAttach() {
    component.onAttach();
  }

  /**
   * Method will be called in case the element is removed from the DOM
   * <p>
   * The method is used by the framework!
   * <p>
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   */
  @NaluInternalUse
  @Override
  public final void onDetach() {
    component.onDetach();
  }

  /**
   * This method will be called in case a routing occurs and this instance is
   * a currently attached controller
   *
   * @return null: routing is ok, String value: routing will be interrupted and
   * the String will be displayed in a message window
   */
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
   * <p>
   * The method is used by the framework!
   * <p>
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
   * The start-method will be called at the start of the controller's life cycle.
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
   * The route the controller is related to.
   *
   * @return related route
   */
  public String getRelatedRoute() {
    return relatedRoute;
  }

  /**
   * Sets the related route of the controller. (Will be used by the framework!)
   * <b>Do not use this method. This will lead to unexpected results</b>
   *
   * @param relatedRoute related route of the controller
   */
  @NaluInternalUse
  public final void setRelatedRoute(String relatedRoute) {
    this.relatedRoute = relatedRoute;
  }

  /**
   * Returns the redrawMode of this controller.
   *
   * @return the current redrawMode
   */
  public Mode getMode() {
    return this.mode;
  }

  /**
   * Sets the redrawMode for this controller.
   *
   * @param mode the new redrawMode
   */
  public void setMode(Mode mode) {
    this.mode = mode;
  }

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
   * The method will not be called in case a controller is cached!
   * <p>
   * Attention:
   * Do not call super.bind(loader)! Cause this will tell Nalu to
   * continue loading!
   *
   * @param loader loader to tell Nalu to continue loading the controller
   * @throws RoutingInterceptionException in case the bind controller
   *                                      process should be interrupted
   */
  @Override
  public void bind(ControllerLoader loader)
      throws RoutingInterceptionException {

    loader.continueLoading();

  }

  /**
   * Gets the activate Nalu command. This will be used by Nalu in case the controller gets activated.
   * <b>Do not use this method. This will lead to unexpected results</b>
   *
   * @return the activated Nalu command
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
