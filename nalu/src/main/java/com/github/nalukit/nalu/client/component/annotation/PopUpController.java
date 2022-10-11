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

package com.github.nalukit.nalu.client.component.annotation;

import com.github.nalukit.nalu.client.component.AlwaysShowPopUp;
import com.github.nalukit.nalu.client.component.IsAbstractPopUpComponent;
import com.github.nalukit.nalu.client.component.IsPopUpComponent;
import com.github.nalukit.nalu.client.component.IsShowPopUpCondition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>This annotation is used to annotate a popup-controller</p>
 * <br><br>
 * The annotation has the following attributes:
 * <ul>
 * <li>name:               name of the popup used to identifiy the conroller in case an event is fired.</li>
 * <li>componentInterface: interface of the component</li>
 * <li>component:          class of the component</li>
 * </ul>
 *
 * @author Frank Hossfeld
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PopUpController {

  String name();

  Class<? extends IsPopUpComponent<?>> componentInterface();

  Class<? extends IsAbstractPopUpComponent> component();

  /**
   * Conditional class to show the popup depending on the return result
   * of the showPopUp()-method.
   * <p>
   * This parameter is optional.
   * <p>
   * Default is AlwaysShowPopUp.class, which will always show hte popup.
   *
   * @return the showp popup condition class
   */
  Class<? extends IsShowPopUpCondition> condition() default AlwaysShowPopUp.class;

  /**
   * Defines the behavior, how often the component is created.
   * <p>
   * Default is, that the component is created once the controller is created,
   * at the time the popup event is fired. After that, the component is always
   * reused.
   * <p>
   * In case the popup component should be rendered every time  the popup event
   * is fired, set this attribute to true.
   * <p>
   * (Default is false)
   *
   * @return true: always render the component.
   */
  boolean alwaysRenderComponent() default false;

}
