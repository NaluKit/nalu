/*
 * Copyright (c) 2018 - 2010 - Frank Hossfeld
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

package com.github.nalukit.nalu.processor.controller.multiRouteSupport02;

import com.github.nalukit.nalu.client.component.AbstractComponent;

public class MultiRouteComponent02
    extends AbstractComponent<IMultiRouteComponent02.Controller, String>
    implements IMultiRouteComponent02 {

  public MultiRouteComponent02() {
  }

  @Override
  public void render() {
    initElement("MultiRouteComponent02");
  }
}
