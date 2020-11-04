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

import com.github.nalukit.nalu.client.component.event.ShowPopUpEvent;
import com.github.nalukit.nalu.client.context.IsContext;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPopUpComponentController<C extends IsContext, V extends IsPopUpComponent<?>>
    extends AbstractController<C>
    implements IsPopUpController<V>,
               IsComponent.Controller {
  
  /* name of the popup controller */
  protected String                                   name;
  /* component of the controller */
  protected V                                        component;
  /* command store (taken from the event) */
  protected Map<String, ShowPopUpEvent.PopUpCommand> commandStore;
  /* data store of the parameters (taken from the event) */
  protected Map<String, String>                      dataStore;
  
  public AbstractPopUpComponentController() {
    super();
    this.dataStore = new HashMap<>();
  }
  
  /**
   * Returns the name of the PopUpController.
   * <p>
   * This is the name used inside the ShowPopUp-event to trigger the controller:
   *
   * @return name of the PopUpController used by the ShowPopUp-event
   */
  public String getName() {
    return name;
  }
  
  /**
   * Sets the name of the PopUpController
   *
   * @param name name of the PopUpController used by the ShowPopUp-event
   */
  @NaluInternalUse
  public final void setName(String name) {
    this.name = name;
  }
  
  /**
   * Returns the command store.
   * <p>
   * The command store contains the commands set inside the ShowPopUp-event.
   * Nalu will set the command store with the entries from the event. Commands
   * can be obtained by using a key.
   *
   * @return the command store with the entries from the event
   */
  public Map<String, ShowPopUpEvent.PopUpCommand> getCommandStore() {
    return this.commandStore;
  }
  
  /**
   * sets the command store.
   *
   * @param commandStore the command store
   */
  @NaluInternalUse
  public final void setCommandStore(Map<String, ShowPopUpEvent.PopUpCommand> commandStore) {
    this.commandStore = commandStore;
  }
  
  /**
   * Returns the data store.
   * <p>
   * The data store contains the parameters set inside the ShowPopUp-event.
   * Nalu will set the data store with the entries from the event. Values
   * can be obtained by using a key.
   *
   * @return the data store with the entries from the event
   */
  public Map<String, String> getDataStore() {
    return this.dataStore;
  }
  
  /**
   * sets the data store.
   *
   * @param dataStore the data store
   */
  @NaluInternalUse
  public final void setDataStore(Map<String, String> dataStore) {
    this.dataStore = dataStore;
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
   * will be called, one time, when a popup-controller is created.
   */
  @Override
  public void bind() {
    // if you need to bind some handlers and would like to do this in a separate method
    // just override this method.
  }
  
  /**
   * The method is called before the show-method.
   * A good place to do some initialization.
   * <p>
   * If you want to do some initialization before you get the
   * control, just override the method.
   *
   * @param finishLoadCommand needs to be executed to give the control back to Nalu
   */
  @Override
  public void onBeforeShow(FinishLoadCommand finishLoadCommand) {
    finishLoadCommand.finishLoading();
  }

}
