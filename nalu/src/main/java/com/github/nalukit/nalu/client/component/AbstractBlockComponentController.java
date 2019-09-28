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

import com.github.nalukit.nalu.client.context.IsContext;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

public abstract class AbstractBlockComponentController<C extends IsContext, V extends IsBlockComponent<?>>
    extends AbstractController<C>
    implements IsBlockComponentController<V>,
               IsComponent.Controller {

  /* name of the popup controller */
  protected String name;
  /* component of the controller */
  protected V      component;

  public AbstractBlockComponentController() {
    super();
  }

  /**
   * append the element
   */
  public void append() {
    component.append();
  }

  /**
   * Returns the name of the BlockController.
   *
   * @return name of the BlockController
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the BlockController
   *
   * @param name name of the BlockController
   */
  @NaluInternalUse
  public void setName(String name) {
    this.name = name;
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
   * @param component instance fo the component
   */
  @Override
  public void setComponent(V component) {
    this.component = component;
  }

  /**
   * Shows the block
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   */
  @Override
  @NaluInternalUse
  public void show() {
    component.show();
  }

  /**
   * hides the block
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   */
  @Override
  @NaluInternalUse
  public void hide() {
    component.hide();
  }

  /**
   * The method is called before the show-method.
   * A good place to do some initialization.
   * <p>
   * If you want to do some initialization before you get the
   * control, just override the method.
   */
  @Override
  public void onBeforeShow() {
  }

  /**
   * The method is called before the hide-method.
   * A good place to do some clean up.
   * <p>
   * If you want to do some blean up before you get the
   * control, just override the method.
   */
  @Override
  public void onBeforeHide() {
  }

}
