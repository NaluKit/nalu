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

import com.github.nalukit.nalu.client.component.AbstractComponent;

public class NavigationComponent
    extends AbstractComponent<INavigationComponent.Controller, String>
    implements INavigationComponent {

  //  private TreeItem searchItem;
  //  private TreeItem listItem;

  public NavigationComponent() {
  }

  @Override
  public void render() {
    //    this.searchItem = TreeItem.create("Search persons",
    //                                      Icons.ALL.search())
    //                              .addClickListener(e -> getController().doShowSearch());
    //
    //    this.listItem = TreeItem.create("List persons",
    //                                    Icons.ALL.list())
    //                            .addClickListener(e -> getController().doShowList());
    //    return Tree.create("Navigation")
    //               .addTreeItem(this.searchItem)
    //               .addTreeItem(this.listItem)
    //               .asElement();
    initElement("navigation");
  }

  @Override
  public void select(String toString) {
    switch (toString) {
    case "SEARCH":
      //        this.searchItem.activate();
      //        this.listItem.deactivate();
      break;
    case "LIST":
      //        this.listItem.activate();
      //        this.searchItem.deactivate();
      break;
    default:
      //        this.listItem.deactivate();
      //        this.searchItem.deactivate();
      break;
    }
  }
}
