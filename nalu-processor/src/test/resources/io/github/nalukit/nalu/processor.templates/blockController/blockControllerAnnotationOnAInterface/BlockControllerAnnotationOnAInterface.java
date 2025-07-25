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

package io.github.nalukit.nalu.processor.blockController.blockControllerAnnotationOnAInterface;

import io.github.nalukit.nalu.client.component.IsBlockComponentController;
import io.github.nalukit.nalu.client.component.annotation.BlockController;
import io.github.nalukit.nalu.processor.blockController.common.block01.BlockComponent01;
import io.github.nalukit.nalu.processor.blockController.common.block01.IBlockComponent01;

@BlockController(name = "test",
                 componentInterface = IBlockComponent01.class,
                 component = BlockComponent01.class)
public interface BlockControllerAnnotationOnAInterface
    extends IsBlockComponentController {
}
