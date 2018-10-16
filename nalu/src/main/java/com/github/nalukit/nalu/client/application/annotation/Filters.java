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

package com.github.nalukit.nalu.client.application.annotation;

import com.github.nalukit.nalu.client.filter.IsFilter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation may be used to set route filters for the framework.<br>
 * <br>
 * This annotation can be used only on classes that implement <code>IsApplication</code> and that are
 * annotated with <code>@@Application</code>.<br>
 * <br>
 * The annotation has the following attributes:
 * <ul>
 * <li>filterClasses: classes of the filter to use.</li>
 * </ul>
 *
 * @author Frank Hossfeld
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Filters {

  Class<? extends IsFilter>[] filterClasses();

}
