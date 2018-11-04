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

package com.github.nalukit.nalu.client.internal.route;

import java.util.ArrayList;
import java.util.List;

public class HashResult {

  private String shell;

  private String route;

  private List<String> parameterValues;

  public HashResult() {
    this(null,
         null,
         new ArrayList<>());
  }

  public HashResult(String shell,
                    String route,
                    List<String> parameterValues) {
    this.shell = shell;
    this.route = route;
    this.parameterValues = parameterValues;
  }

  public HashResult(String shell,
                    String route) {
    this(shell,
         route,
         new ArrayList<>());
  }

  public String getRoute() {
    return route;
  }

  public void setRoute(String route) {
    this.route = route;
  }

  public List<String> getParameterValues() {
    return parameterValues;
  }

  public void setParameterValues(List<String> parameterValues) {
    this.parameterValues = parameterValues;
  }

  public String getShell() {
    return shell;
  }

  public void setShell(String shell) {
    this.shell = shell;
  }
}
