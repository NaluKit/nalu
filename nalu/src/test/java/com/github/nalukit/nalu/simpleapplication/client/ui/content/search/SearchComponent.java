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

package com.github.nalukit.nalu.simpleapplication.client.ui.content.search;

import com.github.nalukit.nalu.client.component.AbstractComponent;

public class SearchComponent
    extends AbstractComponent<ISearchComponent.Controller, String>
    implements ISearchComponent {

  private String searchName;

  private String searchCity;

  public SearchComponent() {
  }

  @Override
  public void render() {
    //    this.searchName = TextBox.create("Name");
    //    this.searchCity = TextBox.create("City");
    //
    //    return Card.create("Search Parameter (search for: 'S' or 'D')")
    //               .appendContent(Row.create()
    //                                 .addColumn(Column.create(12)
    //                                                  .addElement(this.searchName)))
    //               .appendContent(Row.create()
    //                                 .addColumn(Column.create(12)
    //                                                  .addElement(this.searchCity)))
    //               .appendContent(Row.create()
    //                                 .addColumn(Column.create(12)
    //                                                  .addElement(Button.createPrimary("Search")
    //                                                                    .setStyleProperty("margin-right",
    //                                                                                      "20px")
    //                                                                    .addClickListener(e -> getController().doClickSearchButton(this.searchName.getValue(),
    //                                                                                                                               this.searchCity.getValue())))
    //                                                  .addElement(Button.create("Reset")
    //                                                                    .addClickListener(e -> {
    //                                                                      searchName.setValue("");
    //                                                                      searchCity.setValue("");
    //                                                                    })))
    //                                 .style()
    //                                 .setTextAlign("right"))
    //               .asElement();
    initElement("SearchForm");
  }

  @Override
  public void setSearchName(String searchName) {
    this.searchName = searchName;
  }

  @Override
  public void setSearchCity(String searchCity) {
    this.searchCity = searchCity;
  }
}
