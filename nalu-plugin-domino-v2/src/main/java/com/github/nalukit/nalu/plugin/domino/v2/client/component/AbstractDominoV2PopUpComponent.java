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

package com.github.nalukit.nalu.plugin.domino.v2.client.component;

import com.github.nalukit.nalu.client.component.AbstractPopUpComponent;
import org.dominokit.domino.ui.dialogs.DialogStyles;
import org.dominokit.domino.ui.style.DominoCss;
import org.dominokit.domino.ui.utils.ElementsFactory;

public abstract class AbstractDominoV2PopUpComponent<C extends IsDominoV2PopUpComponent.Controller>
    extends AbstractPopUpComponent<C>
    implements IsDominoV2PopUpComponent<C> {

}
