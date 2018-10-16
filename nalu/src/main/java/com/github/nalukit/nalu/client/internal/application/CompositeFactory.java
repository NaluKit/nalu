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

package com.github.nalukit.nalu.client.internal.application;

import com.github.nalukit.nalu.client.component.AbstractCompositeController;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

import java.util.HashMap;
import java.util.Map;

@NaluInternalUse
public class CompositeFactory {

  /* instance of the controller factory */
  private static CompositeFactory instance;

  /* map of components (key: name of class, Value: CompositeCreatorF */
  private Map<String, CompositeCreator> compositeFactory;

  private CompositeFactory() {
    this.compositeFactory = new HashMap<>();
  }

  public static CompositeFactory get() {
    if (instance == null) {
      instance = new CompositeFactory();
    }
    return instance;
  }

  public void registerComposite(String name,
                                CompositeCreator creator) {
    this.compositeFactory.put(name,
                              creator);
  }

  public AbstractCompositeController<?, ?, ?> getComposite(String composite,
                                                           String... parms)
      throws RoutingInterceptionException {
    if (this.compositeFactory.containsKey(composite)) {
      return this.compositeFactory.get(composite)
                                  .create(parms);
    }
    return null;
  }
}
