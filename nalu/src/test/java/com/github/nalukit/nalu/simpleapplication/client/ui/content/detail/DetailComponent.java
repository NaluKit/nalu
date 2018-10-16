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

package com.github.nalukit.nalu.simpleapplication.client.ui.content.detail;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import com.github.nalukit.nalu.simpleapplication.client.data.model.dto.Person;

public class DetailComponent
    extends AbstractComponent<IDetailComponent.Controller, String>
    implements IDetailComponent {

  private String detailFirstName;

  private String detailName;

  private String detailStreet;

  private String detailZip;

  private String detailCity;

  //  private HTMLButtonElement saveButton;
  //
  //  private HTMLButtonElement revertButton;

  public DetailComponent() {
  }

  @Override
  public void render() {
    //    this.detailFirstName = TextBox.create("First name");
    //    this.detailName = TextBox.create("Name");
    //    this.detailStreet = TextBox.create("Name");
    //    this.detailZip = TextBox.create("Name");
    //    this.detailCity = TextBox.create("Name");
    //
    //
    //    return Card.create("Details")
    //               .appendContent(Row.create()
    //                                 .addColumn(Column.create(12)
    //                                                  .addElement(this.detailFirstName)))
    //               .appendContent(Row.create()
    //                                 .addColumn(Column.create(12)
    //                                                  .addElement(this.detailName)))
    //               .appendContent(Row.create()
    //                                 .addColumn(Column.create(12)
    //                                                  .addElement(this.detailStreet)))
    //               .appendContent(Row.create()
    //                                 .addColumn(Column.create(12)
    //                                                  .addElement(this.detailZip)))
    //               .appendContent(Row.create()
    //                                 .addColumn(Column.create(12)
    //                                                  .addElement(this.detailCity)))
    //               .appendContent(Row.create()
    //                                 .addColumn(Column.create(12)
    //                                                  .addElement(Button.createPrimary("Save")
    //                                                                    .setStyleProperty("margin-right",
    //                                                                                      "20px")
    //                                                                    .addClickListener(e -> getController().doUpdate()))
    //                                                  .addElement(Button.create("Reset")
    //                                                                    .addClickListener(e -> getController().doRevert())))
    //                                 .style()
    //                                 .setTextAlign("right"))
    //               .asElement();
    initElement("DetailForm");
  }

  @Override
  public void edit(Person result) {
    if (result != null) {
      detailFirstName = result.getFirstName();
      detailName = result.getName();
      detailStreet = result.getAddress()
                           .getStreet();
      detailZip = result.getAddress()
                        .getZip();
      detailCity = result.getAddress()
                         .getCity();
    }
  }

  @Override
  public boolean isDirty() {
    //    boolean notDirty = (getController().getPerson()
    //                                       .getFirstName()
    //                                       .equals(detailFirstName.getValue())) &&
    //        (getController().getPerson()
    //                        .getName()
    //                        .equals(detailName.getValue())) &&
    //        (getController().getPerson()
    //                        .getAddress()
    //                        .getStreet()
    //                        .equals(detailStreet.getValue())) &&
    //        (getController().getPerson()
    //                        .getAddress()
    //                        .getZip()
    //                        .equals(detailZip.getValue())) &&
    //        (getController().getPerson()
    //                        .getAddress()
    //                        .getCity()
    //                        .equals(detailCity.getValue()));
    return false;
  }

  @Override
  public Person flush(Person person) {
    person.setFirstName(detailFirstName);
    person.setName(detailName);
    person.getAddress()
          .setStreet(detailStreet);
    person.getAddress()
          .setZip(detailZip);
    person.getAddress()
          .setCity(detailCity);
    return person;
  }
}
