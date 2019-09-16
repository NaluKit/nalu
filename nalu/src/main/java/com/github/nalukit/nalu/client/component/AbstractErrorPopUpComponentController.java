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
import com.github.nalukit.nalu.client.event.NaluErrorEvent;
import com.github.nalukit.nalu.client.event.model.ErrorInfo.ErrorType;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

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
   * @param component instance fo the component
   */
  @Override
  public void setComponent(V component) {
    this.component = component;
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
   * The method is called right after the isntace is created.
   * <p>
   * <b>DO NOT CALL OR OVERRIDE THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   */
  @NaluInternalUse
  public void onLoad() {
    this.eventBus.addHandler(NaluErrorEvent.TYPE,
                             e -> handleErrorEvent(e));
  }

  private void handleErrorEvent(NaluErrorEvent e) {
    StringBuilder sb01 = new StringBuilder();
    sb01.append("caught NaluErrorEvent");
    ClientLogger.get()
                .logDetailed(sb01.toString(),
                             1);
    sb01.setLength(0);
    sb01.append("caught NaluErrorEvent: type >>")
        .append(e.getErrorEventType()
                 .toString())
        .append("<<");
    ClientLogger.get()
                .logDetailed(sb01.toString(),
                             2);
    sb01.setLength(0);
    sb01.append("caught NaluErrorEvent: route >>")
        .append(e.getRoute())
        .append("<<");
    ClientLogger.get()
                .logDetailed(sb01.toString(),
                             2);
    sb01.setLength(0);
    sb01.append("caught NaluErrorEvent: message >>")
        .append(e.getMessage())
        .append("<<");
    ClientLogger.get()
                .logDetailed(sb01.toString(),
                             2);

    this.route = e.getRoute();
    this.message = e.getMessage();
    this.errorEventType = e.getErrorEventType();
    e.getDataStore()
     .keySet()
     .forEach(k -> {
       sb01.setLength(0);
       sb01.append("caught NaluErrorEvent: data -> key >>")
           .append(k)
           .append("<< - value >>")
           .append(e.getDataStore()
                    .get(k))
           .append("<<");
       ClientLogger.get()
                   .logDetailed(sb01.toString(),
                                2);

       this.dataStore.put(k,
                          e.getDataStore()
                           .get(k));
     });
    sb01.setLength(0);
    sb01.append("call onBeforeShow()");
    ClientLogger.get()
                .logDetailed(sb01.toString(),
                             3);
    this.onBeforeShow();

    sb01.setLength(0);
    sb01.append("call show()");
    ClientLogger.get()
                .logDetailed(sb01.toString(),
                             3);
    this.show();
  }

  /**
   * Method will be called in case an error event gets fired and the popup gets visible!
   */
  protected abstract void show();

}
