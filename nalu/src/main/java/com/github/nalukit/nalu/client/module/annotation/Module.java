/*
 * Copyright (c) 2018 Frank Hossfeld
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

import com.github.nalukit.nalu.client.context.IsModuleContext;
import com.github.nalukit.nalu.client.internal.module.NoModuleLoader;
import com.github.nalukit.nalu.client.module.AbstractModuleLoader;

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
 * <li>name: name of the module.</li>
 * <li>context: the context of the class. Nalu will create an instance of this class and inject
 * the instance into all controllers, filters, handlers and the application loader.</li>
 * <li>loader: a loader that will be executed in case the module loads. If no loader
 * is defined, the NoMoculeLoader.class will be used. In this case, the loader will do nothing.</li>
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

  /**
   * The module loader of the module. Will be executed in case the
   * module gets loaded. This is a good place to load module specific data.
   * F.e.: Meta-data, store values, etc.
   * <br>
   * The module loader is optional.
   *
   * @return the module loader
   */
  Class<? extends AbstractModuleLoader<?>> loader() default NoModuleLoader.class;

}
