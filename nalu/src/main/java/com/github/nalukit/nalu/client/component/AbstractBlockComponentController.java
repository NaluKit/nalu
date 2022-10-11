/*
 * Copyright (c) 2018 Frank Hossfeld
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

import com.github.nalukit.nalu.client.component.event.HideBlockComponentEvent;
import com.github.nalukit.nalu.client.component.event.ShowBlockComponentEvent;
import com.github.nalukit.nalu.client.context.IsContext;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

public abstract class AbstractBlockComponentController<C extends IsContext, V extends IsBlockComponent<?>>
    extends AbstractController<C>
    implements IsBlockComponentController<V>,
               IsComponent.Controller {

  /* name of the popup controller */
  protected String  name;
  /* component of the controller */
  protected V       component;
  /* flag to indicate if the cblockComponent is visible or not */
  private   boolean visible;

  public AbstractBlockComponentController() {
    super();
  }

  /**
   * Nalu uses thie method to bind the handler to the event bus.
   */
  @NaluInternalUse
  public final void bind() {
    super.eventBus.addHandler(HideBlockComponentEvent.TYPE,
                              this::onHideBlockComponent);
    super.eventBus.addHandler(ShowBlockComponentEvent.TYPE,
                              this::onShowBlockComponent);
    this.visible = true;
  }

  /**
   * Called in case the {@link HideBlockComponentEvent} gets catched
   *
   * @param event the hide event - can be used to get parameters
   */
  private void onHideBlockComponent(HideBlockComponentEvent event) {
    if (this.getName()
            .equals(event.getName())) {
      if (this.visible) {
        this.onBeforeHide(event);
        this.hide();
        this.visible = false;
      }
    }
  }

  /**
   * called in case the {@link ShowBlockComponentEvent} gets catched.
   *
   * @param event the show event - can be used to get parameters
   */
  private void onShowBlockComponent(ShowBlockComponentEvent event) {
    if (this.getName()
            .equals(event.getName())) {
      if (!this.visible) {
        this.onBeforeShow(event);
        this.show();
        this.visible = true;
      }
    }
  }

  /**
   * hides the block
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   */
  private void hide() {
    component.hide();
  }

  /**
   * Shows the block
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   */
  private void show() {
    component.show();
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
  public void setComponent(V component) {
    this.component = component;
  }

  /**
   * Returns the name of the BlockController.
   *
   * @return name of the BlockController
   */
  @NaluInternalUse
  public final String getName() {
    return name;
  }

  /**
   * Sets the name of the BlockController
   *
   * @param name name of the BlockController
   */
  @NaluInternalUse
  public final void setName(String name) {
    this.name = name;
  }

  /**
   * The method is called before the show-method.
   * A good place to do some initialization.
   * <p>
   * If you want to do some initialization before you get the
   * control, just override the method.
   *
   * @param event the show event - can be used to get parameters
   */
  @Override
  public void onBeforeShow(ShowBlockComponentEvent event) {
  }

  /**
   * The method is called before the hide-method.
   * A good place to do some clean up.
   * <p>
   * If you want to do some blean up before you get the
   * control, just override the method.
   *
   * @param event the hide event - can be used to get parameters
   */
  @Override
  public void onBeforeHide(HideBlockComponentEvent event) {
  }

  /**
   * append the element
   */
  @Override
  public void append() {
    component.append();
  }

}
