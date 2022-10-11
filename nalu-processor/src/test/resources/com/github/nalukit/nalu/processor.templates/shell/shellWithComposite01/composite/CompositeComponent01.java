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

package com.github.nalukit.nalu.processor.shell.shellWithComposite01.composite;

import com.github.nalukit.nalu.client.component.AbstractCompositeComponent;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;

import static elemental2.dom.DomGlobal.document;

public class CompositeComponent01
    extends AbstractCompositeComponent<ICompositeComponent01.Controller, HTMLElement>
    implements ICompositeComponent01 {
  
  public CompositeComponent01() {
  }
  
  @Override
  public void render() {
    HTMLDivElement divElemet = (HTMLDivElement) document.createElement("div");
    initElement(divElemet);
  }
  
}
