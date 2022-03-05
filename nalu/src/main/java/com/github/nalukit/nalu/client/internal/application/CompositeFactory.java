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

package com.github.nalukit.nalu.client.internal.application;

import com.github.nalukit.nalu.client.component.AbstractCompositeController;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.Utils;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@NaluInternalUse
public class CompositeFactory {

  private final static String DELIMITER = "<<||>>";

  /* instance of the controller factory */
  private static CompositeFactory                                  instance;
  /* map of components (key: name of class, Value: ControllerCreator */
  private final  Map<String, IsCompositeCreator>                   compositeCreatorFactory;
  /* map of stored components (key: name of class, Value: instance of controller */
  private final  Map<String, AbstractCompositeController<?, ?, ?>> compositeControllerStore;
  /* list of global cached composites */
  private final  Map<String, CompositeInstance>                    cachedGlobalCompositeInstances;

  private CompositeFactory() {
    this.compositeCreatorFactory        = new HashMap<>();
    this.compositeControllerStore       = new HashMap<>();
    this.cachedGlobalCompositeInstances = new HashMap<>();
  }

  public static CompositeFactory get() {
    if (instance == null) {
      instance = new CompositeFactory();
    }
    return instance;
  }

  public void registerComposite(String controller,
                                IsCompositeCreator creator) {
    this.compositeCreatorFactory.put(controller,
                                     creator);
  }

  public CompositeInstance getComposite(String parentControllerClassName,
                                        String compositeControllerClassName,
                                        String selector,
                                        boolean scopeGlobal,
                                        String... params)
      throws RoutingInterceptionException {
    // in case scopeGlobal is true ,check if the instance already exists
    if (scopeGlobal) {
      if (!Objects.isNull(this.cachedGlobalCompositeInstances.get(compositeControllerClassName))) {
        return this.cachedGlobalCompositeInstances.get(compositeControllerClassName);
      }
    }
    // ok, global cache is empty ... create it!
    if (this.compositeCreatorFactory.containsKey(compositeControllerClassName)) {
      IsCompositeCreator compositeCreator = this.compositeCreatorFactory.get(compositeControllerClassName);
      CompositeInstance compositeInstance = compositeCreator.create(parentControllerClassName,
                                                                    selector,
                                                                    scopeGlobal);
      if (scopeGlobal) {
        // oh ... global scope! store the compositeInstance
        compositeInstance.setCached(true);
        compositeInstance.getComposite()
                         .setCachedGlobal(true);
        compositeInstance.getComposite()
                         .setSelector(null);
        this.cachedGlobalCompositeInstances.put(compositeControllerClassName,
                                                compositeInstance);
      }
      compositeCreator.setParameter(compositeInstance.getComposite(),
                                    params);
      return compositeInstance;
    }
    return null;
  }

  public AbstractCompositeController<?, ?, ?> getCompositeFormStore(String parentControllerClassName,
                                                                    String controllerClassName,
                                                                    String selector) {
    String key = this.createKey(parentControllerClassName,
                                controllerClassName,
                                selector);
    return this.compositeControllerStore.get(key);
  }

  private String createKey(String parentClassName,
                           String compositeClassName,
                           String selector) {
    StringBuilder sb = new StringBuilder();
    sb.append(this.classFormatter(parentClassName))
      .append(CompositeFactory.DELIMITER);
    if (selector != null) {
      sb.append(selector)
        .append(CompositeFactory.DELIMITER);
    } else {
      sb.append("*")
        .append(CompositeFactory.DELIMITER);
    }
    sb.append(this.classFormatter(compositeClassName));
    return sb.toString();
  }

  private String classFormatter(String className) {
    return className.replace(".",
                             "_");
  }

  /**
   * Adds the controller to the store of cached controllers.
   * Call this method from inside the controller you like to cache.
   * <p>
   * This method will not call the <code>start</code>- and <code>activate</code>-method
   * of the controller.
   *
   * @param controller the controller to store
   * @param <C>        type of controller
   */
  public <C extends AbstractCompositeController<?, ?, ?>> void storeInCache(C controller) {
    String key = this.createKey(controller.getParentClassName(),
                                controller.getClass()
                                          .getCanonicalName(),
                                controller.getSelector());
    this.compositeControllerStore.put(key,
                                      controller);
  }

  /**
   * Removes the controller from the store of cached controllers.
   * Call this method from inside the controller you like to remove from the cache.
   * In case the controller is not cached, the method does nothing
   * <p>
   * This method will not call the <code>deactivate</code>- and <code>stop</code>-method
   * of the controller.
   *
   * @param controller the controller to remove from the store
   * @param <C>        type of controller
   */
  public <C extends AbstractCompositeController<?, ?, ?>> void removeFromCache(C controller) {
    String key = this.createKey(controller.getParentClassName(),
                                controller.getClass()
                                          .getCanonicalName(),
                                controller.getSelector());
    this.compositeControllerStore.remove(key);
  }

  /**
   * Clears the cache!
   * <p>
   * This method will remove all cached controllers from the store.
   * <p>
   * For every controller the method will call
   * <code>deactivate</code>- and <code>stop</code>-method.
   * <p>
   * DO NOT CALL THIS METHOD INSIDE A CACHED CONTROLLER!
   * If you are inside a cached controller, call
   * <code>removeFromCache(this)</code> first!
   */
  public void clearCompositeControllerCache() {
    this.compositeControllerStore.values()
                                 .forEach(controller -> {
                                   Utils.get()
                                        .deactivateCompositeController(controller);
                                   Utils.get()
                                        .stopCompositeController(controller);
                                 });
    this.compositeControllerStore.clear();
  }

}
