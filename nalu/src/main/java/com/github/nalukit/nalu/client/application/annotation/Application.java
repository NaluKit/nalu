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

import com.github.nalukit.nalu.client.Nalu;
import com.github.nalukit.nalu.client.application.AbstractApplicationLoader;
import com.github.nalukit.nalu.client.application.IsContext;
import com.github.nalukit.nalu.client.component.IsShell;
import com.github.nalukit.nalu.client.internal.application.NoApplicationLoader;

import javax.xml.bind.annotation.XmlElementDecl;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>This annotation is used to annotate an interface in Nalu and mark it as a Nalu application.</p>
 * <br><br>
 * The annotation has the following attributes:
 * <ul>
 * <li>loader: a loader that will be executed in case the application loads. If no loader
 * is defined, the NoApplicationLoader.class will be used. In this case, the loader will do nothing.</li>
 * <li>startRoute: in case the application is called without a bookmark, is this the initial route.</li>
 * <li>context: the context of the class. Nalu will create an instance of this class and inject
 * the instance into all controllers, filters, handlers and the application loader.</li>
 * </ul>
 *
 * @author Frank Hossfeld
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Application {

  Class<? extends AbstractApplicationLoader<?>> loader() default NoApplicationLoader.class;

  String startRoute();

  Class<? extends IsContext> context();

  @Deprecated
  String routeErrorRoute() default Nalu.NO_ROUTE;

}
