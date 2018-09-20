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

package com.github.nalukit.nalu.simpleapplication.client.ui.content.list;

import com.github.nalukit.nalu.client.component.IsComponent;
import com.github.nalukit.nalu.simpleapplication.client.data.model.dto.Person;

import java.util.List;

public interface IListComponent
    extends IsComponent<IListComponent.Controller, String> {

  void resetTable();

  void setData(List<Person> result);

  interface Controller
      extends IsComponent.Controller {

    void doUpdate(Person object);

  }
}
