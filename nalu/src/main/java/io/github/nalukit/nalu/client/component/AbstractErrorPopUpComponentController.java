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

import io.github.nalukit.nalu.client.context.IsContext;
import io.github.nalukit.nalu.client.event.NaluErrorEvent;
import io.github.nalukit.nalu.client.event.model.ErrorInfo.ErrorType;
import io.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractErrorPopUpComponentController<C extends IsContext, V extends IsErrorPopUpComponent<?>>
    extends AbstractController<C>
    implements IsErrorPopUpController<V>,
               IsErrorPopUpComponent.Controller {

  /* component of the controller */
  protected V                   component;
  /* error type */
  protected ErrorType           errorEventType;
  /* route in error (only filled by default in case type is NaluErrorEvent.NALU_INTERNAL_ERROR */
  protected String              route;
  /* error message */
  protected String              message;
  /* data store  */
  protected Map<String, String> dataStore;

  public AbstractErrorPopUpComponentController() {
    super();
    this.dataStore = new HashMap<>();
  }

  /**
   * Sets the component inside the controller
   * <b>Do not use this method. This will lead to unexpected results</b>
   *
   * @param component instance of the component
   */
  @Override
  public void setComponent(V component) {
    this.component = component;
  }

  /**
   * will be called, one time, when a popup-controller is created.
   */
  @Override
  public void bind() {
    // if you need to bind some handlers and would like to do this in a separate method
    // just override this method.
  }

  /**
   * The method is called right after the istance is created.
   * <p>
   * <b>DO NOT CALL OR OVERRIDE THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   */
  @NaluInternalUse
  public final void onLoad() {
    this.eventBus.addHandler(NaluErrorEvent.TYPE,
                             e -> handleErrorEvent(e));
  }

  private void handleErrorEvent(NaluErrorEvent e) {
    this.route          = e.getRoute();
    this.message        = e.getMessage();
    this.errorEventType = e.getErrorEventType();
    e.getDataStore()
     .keySet()
     .forEach(k -> {
       this.dataStore.put(k,
                          e.getDataStore()
                           .get(k));
     });
    this.onBeforeShow();
    this.show();
  }

  /**
   * The method is called before the show-method.
   * A good place to do some initialization.
   * <p>
   * If you want to do some initialization before you get the
   * control, just override the method.
   */
  public void onBeforeShow() {
  }

  /**
   * Method will be called in case an error event gets fired and the popup gets visible!
   */
  protected abstract void show();

}
