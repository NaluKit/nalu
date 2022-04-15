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

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.Utils;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NaluInternalUse
public class EventHandlerFactory {

  /* instance of the controller factory */
  private static EventHandlerFactory                   instance;
  /* map of components (key: name of parent class, Value: list of EventHandlerConfig */
  private final  Map<String, List<EventHandlerConfig>> eventHandlerConfigs;

  private EventHandlerFactory() {
    this.eventHandlerConfigs = new HashMap<>();
  }

  public static EventHandlerFactory get() {
    if (instance == null) {
      instance = new EventHandlerFactory();
    }
    return instance;
  }

  public void registerEventHandler(String parentClass,
                                   EventHandlerConfig config) {
    if (this.eventHandlerConfigs.containsKey(parentClass)) {
      if (!this.containsEventHandlerConfig(parentClass,
                                           config)) {
        this.eventHandlerConfigs.get(parentClass)
                                .add(config);
      }
    } else {
      this.eventHandlerConfigs.put(parentClass,
                                   Collections.singletonList(config));
    }
  }

  private boolean containsEventHandlerConfig(String parentClass,
                                             EventHandlerConfig config) {
    return this.eventHandlerConfigs.get(parentClass)
                                   .stream()
                                   .anyMatch(c -> c.getEventClassName()
                                                   .equals(config.getEventClassName()) &&
                                                  c.getMethodName()
                                                   .equals(config.getMethodName()));
  }
  //
  //  public void controller(String route,
  //                         String controller,
  //                         ControllerCallback callback,
  //                         String... params) {
  //    if (this.eventHandlerFactory.containsKey(controller)) {
  //      IsControllerCreator controllerCreator  = this.eventHandlerFactory.get(controller);
  //      ControllerInstance  controllerInstance = controllerCreator.create(route);
  //      try {
  //        controllerCreator.setParameter(controllerInstance.getController(),
  //                                       params);
  //      } catch (RoutingInterceptionException e) {
  //        callback.onRoutingInterceptionException(e);
  //        return;
  //      }
  //      if (controllerInstance.isCached()) {
  //        callback.onFinish(controllerInstance);
  //      } else {
  //        try {
  //          controllerInstance.getController()
  //                            .bind(() -> {
  //                              controllerCreator.onFinishCreating(controllerInstance.getController());
  //                              callback.onFinish(controllerInstance);
  //                            });
  //        } catch (RoutingInterceptionException e) {
  //          callback.onRoutingInterceptionException(e);
  //        }
  //      }
  //    }
  //  }
  //
  //  public AbstractComponentController<?, ?, ?> getControllerFormStore(String controllerClassName) {
  //    return this.controllerStore.get(this.classFormatter(controllerClassName));
  //  }
  //
  //  private String classFormatter(String className) {
  //    return className.replace(".",
  //                             "_");
  //  }
  //
  //  /**
  //   * Adds the controller to the store of cached controllers.
  //   * Call this method from inside the controller you like to cache.
  //   * <p>
  //   * This method will not call the <code>start</code>- and <code>activate</code>-method of the controller.
  //   *
  //   * @param controller the controller to store
  //   * @param <C>        type of controller
  //   */
  //  public <C extends AbstractComponentController<?, ?, ?>> void storeInCache(C controller) {
  //    String key = this.classFormatter(controller.getClass()
  //                                               .getCanonicalName());
  //    this.controllerStore.put(key,
  //                             controller);
  //  }
  //
  //  /**
  //   * Removes the controller from the store of cached controllers.
  //   * Call this method from inside the controller you like to remove from the cache.
  //   * In case the controller is not cached, the method does nothing
  //   * <p>
  //   * This method will not call the <code>deactivate</code>- and <code>stop</code>-method
  //   * of the controller.
  //   *
  //   * @param controller the controller to remove from the store
  //   * @param <C>        type of controller
  //   */
  //  public <C extends AbstractComponentController<?, ?, ?>> void removeFromCache(C controller) {
  //    String key = this.classFormatter(controller.getClass()
  //                                               .getCanonicalName());
  //    this.controllerStore.remove(key);
  //  }
  //
  //  /**
  //   * Clears the cache!
  //   * <p>
  //   * This method will remove all cached controllers from the store.
  //   * <p>
  //   * For every controller the method will iterate over
  //   * the composites and call <code>deactivate</code>- and
  //   * <code>stop</code>-method.
  //   * Once done, it call the <code>deactivate</code>- and
  //   * <code>stop</code>-method of the controller.
  //   * <p>
  //   * DO NOT CALL THIS METHOD INSIDE A CACHED CONTROLLER!
  //   * If you are inside a cached controller, call
  //   * <code>removeFromCache(this)</code> first!
  //   */
  //  public void clearControllerCache() {
  //    this.controllerStore.values()
  //                        .forEach(controller -> {
  //                          controller.getComposites()
  //                                    .values()
  //                                    .forEach(compositeController -> {
  //                                      Utils.get()
  //                                           .deactivateCompositeController(compositeController);
  //                                      Utils.get()
  //                                           .stopCompositeController(compositeController);
  //                                    });
  //                          Utils.get()
  //                               .deactivateController(controller,
  //                                                     false);
  //                          Utils.get()
  //                               .stopController(controller);
  //                        });
  //    this.controllerStore.clear();
  //  }

}
