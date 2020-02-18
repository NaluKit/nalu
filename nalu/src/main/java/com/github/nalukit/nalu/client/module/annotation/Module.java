/*
 * Copyright (c) 2018 - 2020 - Frank Hossfeld
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

package com.github.nalukit.nalu.client.module.annotation;

import com.github.nalukit.nalu.client.context.module.IsModuleContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>This annotation is used to annotate an interface in Nalu and mark it as a Nalu module.</p>
 * <p>
 * A module is a sub set of screens, filters, handlers, etc. that can be use in Nalu based application.
 * Modules
 * </p>
 * <p>
 * The annotation has the following attributes:
 * <ul>
 * <li>loader: a loader that will be executed in case the application loads. If no loader
 * is defined, the NoApplicationLoader.class will be used. In this case, the loader will do nothing.</li>
 * <li>startRoute: in case the application is called without a bookmark, is this the initial route.</li>
 * <li>context: the context of the class. Nalu will create an instance of this class and inject
 * the instance into all controllers, filters, handlers and the application loader.</li>
 * <li>routeError: in case an error occurs, Nalu will use this route toi display the error</li>
 * <li>useHash: if useHash is true, use a hash based url, otherwise a non hash based url</li>
 * <li>useColonForParametersInUrl: if useColonForParametersInUrl is true, Nalu expects parameters with a leading colon in urls</li>
 * <li>stayOnSite: if stayOnSite is true, Nalu will replace history with the start-route in case hash is empty, else Nalu will only update it.</li>
 * </ul>
 *
 * @author Frank Hossfeld
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Module {

  /**
   * Name of the module
   *
   * @return return the name of the module
   */
  String name();

  /**
   * The context of the module. it can be compared to the session of the server side.
   * Use the context to store application and module data.
   *
   * @return application context
   */
  Class<? extends IsModuleContext> context();

}
