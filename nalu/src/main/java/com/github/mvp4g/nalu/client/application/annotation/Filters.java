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

package com.github.mvp4g.nalu.client.application.annotation;

import com.github.mvp4g.nalu.client.filter.IsFilter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>This annotation is used to annotate an interface in mvp4g and mark it as mvp4g application.</p>
 * <br><br>
 * The annotation has the following attributes:
 * <ul>
 * <li>eventBus: defines the eventbus of this application</li>
 * <li>loader: a loader that will be executed in case the application loads. If no loader
 * is defined, the NoApplicationLoader.class will be used. In this case, the loader will do nothing.</li>
 * <li>historyOnStart: if true, the current history state will be fired when the application starts.</li>
 * <li>encodeToken: if true, the token will be encoded (very simple). (Experimental, may be, it will be removed ...)</li>
 * </ul>
 *
 * @author Frank Hossfeld
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Filters {

  Class<? extends IsFilter>[] filterClasses();

}
