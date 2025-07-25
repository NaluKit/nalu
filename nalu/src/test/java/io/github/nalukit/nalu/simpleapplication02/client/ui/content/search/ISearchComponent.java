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

package io.github.nalukit.nalu.simpleapplication02.client.ui.content.search;

import io.github.nalukit.nalu.client.component.IsComponent;

public interface ISearchComponent
    extends IsComponent<ISearchComponent.Controller, String> {

  void setSearchName(String searchName);

  void setSearchCity(String searchCity);

  interface Controller
      extends IsComponent.Controller {

    void doClickSearchButton(String searchName,
                             String searchCity);

  }

}
