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

package io.github.nalukit.nalu.processor.controller.multiRouteSupport01;

import io.github.nalukit.nalu.client.component.AbstractComponent;
import java.lang.Override;

public class MultiRouteComponent01
    extends AbstractComponent<IMultiRouteComponent01.Controller, String>
    implements IMultiRouteComponent01 {

  public MultiRouteComponent01() {
  }

  @Override
  public void render() {
    initElement("MultiRouteComponent01");
  }
}
