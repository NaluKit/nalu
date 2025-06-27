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

package io.github.nalukit.nalu.processor.composites.filter;

import io.github.nalukit.nalu.client.component.annotation.Composite;
import io.github.nalukit.nalu.client.component.annotation.Composites;
import io.github.nalukit.nalu.client.event.annotation.EventHandler;
import io.github.nalukit.nalu.client.filter.AbstractFilter;
import io.github.nalukit.nalu.processor.common.MockContext;
import io.github.nalukit.nalu.processor.common.event.MockEvent01;

@Composites(@Composite(name = "composite", compositeController = MockComposite.class, selector = "shellComposte"))
public class MockFilter
    extends AbstractFilter<MockContext> {

  @Override
  public boolean filter(String route,
                        String... params) {
    return true;
  }

  @Override
  public String redirectTo() {
    return "/search";
  }

  @Override
  public String[] parameters() {
    return new String[] {};
  }

  @EventHandler
  public void handleEvent(MockEvent01 event01) {

  }
}
