/*
 * Copyright (c) 2018 - Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *
 */

package com.github.nalukit.nalu.simpleapplication.client.ui.navigation;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.simpleapplication.client.NaluSimpleApplicationContext;
import com.github.nalukit.nalu.simpleapplication.client.event.SelectEvent;

public class NavigationController
    extends AbstractComponentController<NaluSimpleApplicationContext, INavigationComponent, String>
    implements INavigationComponent.Controller {

  public NavigationController() {
  }

  @Override
  public void start() {
    this.eventBus.addHandler(SelectEvent.TYPE,
                             e -> component.select(e.getSelect()
                                                    .toString()));
  }

  @Override
  public void doShowSearch() {
    this.router.route("/search",
                      this.context.getSearchName(),
                      this.context.getSearchCity());
  }

  @Override
  public void doShowList() {
    this.router.route("/list",
                      this.context.getSearchName(),
                      this.context.getSearchCity());
  }
}
