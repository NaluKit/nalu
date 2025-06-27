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

package io.github.nalukit.nalu.client.component.annotation;

import io.github.nalukit.nalu.client.component.AbstractCompositeController;
import io.github.nalukit.nalu.client.component.AlwaysLoadComposite;
import io.github.nalukit.nalu.client.component.IsLoadCompositeCondition;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Defines a composite
 *
 * @author Frank Hossfeld
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Composite {

  /**
   * Name of the composite.
   * <p>
   * Can be used to get access to the instance of the CompositeController.
   *
   * @return reference of the composite controller
   */
  String name();

  /**
   * Composite controller class of this composite
   *
   * @return the composite controller class
   */
  Class<? extends AbstractCompositeController<?, ?, ?>> compositeController();

  /**
   * Name of the selector where to add the composite
   *
   * @return name of the selector
   */
  String selector();

  /**
   * Conditional class to load the composite depending on the return result
   * of the loadComposite()-method.
   * <p>
   * This parameter is optional.
   * <p>
   * Default is AlwaysLoadComposite.class, which will always load the composite.
   *
   * @return the composite loader condition class
   */
  Class<? extends IsLoadCompositeCondition> condition() default AlwaysLoadComposite.class;

  /**
   * Scope of the composite:
   * <ul>
   *   <li>LOCAL: use this in case you do not wish to cache the composite, or, you wish to stored the instance of the composite for this component </li>
   *   <li>GLOBAL: use this in case you wish to use the instance of the composite in any places where the composite is referenced and the scope is Global.</li>
   * </ul>
   * In case Scope.GLOBAL is selected Nalu will automatically store the instance in the cache (the first time the component is visited)
   *
   * @return Scope of the composite
   */
  Scope scope() default Scope.LOCAL;

  /**
   * Scope of the composite:
   * <ul>
   *   <li>LOCAL: in case of caching the cached composite is only used for the component</li>
   *   <li>GLOBAL: in case of caching the cached composite is used in all places the composite is reference with Scope.GLOBAL</li>
   * </ul>
   */
  enum Scope {
    LOCAL,
    GLOBAL
  }

}
