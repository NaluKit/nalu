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

package com.github.nalukit.nalu.simpleapplication02.client.filters;

import com.github.nalukit.nalu.client.filter.AbstractFilter;
import com.github.nalukit.nalu.simpleapplication02.client.NaluSimpleApplicationContext;

public class BartSimpsonFilter
    extends AbstractFilter<NaluSimpleApplicationContext> {

  @Override
  public boolean filter(String route,
                        String... params) {
    if ("/detail".equals(route)) {
      if ("3".equals(params[0])) {
        System.out.print("Bart Simpson is not selectable -> redirecting to search!");
        return false;
      }
    }
    return true;
  }

  @Override
  public String redirectTo() {
    return "/search";
  }

  @Override
  public String[] parameters() {
    return new String[] { this.context.getSearchName(),
                          this.context.getSearchCity() };
  }

}
