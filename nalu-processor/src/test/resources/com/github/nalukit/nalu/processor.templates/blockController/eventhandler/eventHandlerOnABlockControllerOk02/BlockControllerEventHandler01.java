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

package com.github.nalukit.nalu.processor.blockController.eventhandler.eventHandlerOnABlockControllerOk02;

import com.github.nalukit.nalu.client.component.AbstractBlockComponentController;
import com.github.nalukit.nalu.client.component.annotation.BlockController;
import com.github.nalukit.nalu.client.event.annotation.EventHandler;
import com.github.nalukit.nalu.processor.blockController.common.block01.BlockComponent01;
import com.github.nalukit.nalu.processor.blockController.common.block01.IBlockComponent01;
import com.github.nalukit.nalu.processor.common.MockContext;
import com.github.nalukit.nalu.processor.common.event.MockEvent01;
import com.github.nalukit.nalu.processor.common.event.MockEvent02;

@BlockController(name = "blockController01",
                 componentInterface = IBlockComponent01.class,
                 component = BlockComponent01.class)
public class BlockControllerEventHandler01
    extends AbstractBlockComponentController<MockContext, IBlockComponent01>
    implements IBlockComponent01.Controller {

  @EventHandler
  public void onMockEvent01(MockEvent01 event01) {
  }

  @EventHandler
  public void onMockEvent02(MockEvent02 event02) {
  }

}
