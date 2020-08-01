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

package com.github.nalukit.nalu.client.application.annotation;

import com.github.nalukit.nalu.client.application.AbstractApplicationLoader;
import com.github.nalukit.nalu.client.context.IsContext;
import com.github.nalukit.nalu.client.internal.NoCustomAlertPresenter;
import com.github.nalukit.nalu.client.internal.NoCustomConfirmPresenter;
import com.github.nalukit.nalu.client.internal.application.NoApplicationLoader;
import com.github.nalukit.nalu.client.plugin.IsCustomAlertPresenter;
import com.github.nalukit.nalu.client.plugin.IsCustomConfirmPresenter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>This annotation is used to annotate an interface in Nalu and mark it as a Nalu application.</p>
 * <p>
 * The annotation has the following attributes:
 * <ul>
 * <li>loader: a loader that will be executed in case the application loads. If no loader
 * is defined, the NoApplicationLoader.class will be used. In this case, the loader will do nothing.</li>
 * <li>startRoute: in case the application is called without a bookmark, is this the initial route.</li>
 * <li>context: the context of the class. Nalu will create an instance of this class and inject
 * the instance into all controllers, filters, handlers and the application loader.</li>
 * <li>useHash: if useHash is true, use a hash based url, otherwise a non hash based url</li>
 * <li>useColonForParametersInUrl: if useColonForParametersInUrl is true, Nalu expects parameters with a leading colon in urls</li>
 * <li>stayOnSite: if stayOnSite is true, Nalu will replace history with the start-route in case hash is empty, else Nalu will only update it.</li>
 * </ul>
 *
 * @author Frank Hossfeld
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Application {
  
  /**
   * the application loader of the application. Will be executed in case the
   * application is started. This is a good place to load apllication data.
   * F.e.: Meta-data, store values, etc.
   * <br>
   * The applicaition loader is optional.
   *
   * @return the application loader
   */
  Class<? extends AbstractApplicationLoader<?>> loader() default NoApplicationLoader.class;
  
  /**
   * Start route used by Nalu in case the application is started
   *
   * @return return the start route
   */
  String startRoute();
  
  /**
   * The context of the application. it can be compared to the session of the server side.
   * Use the context to store application wide data.
   *
   * @return application context
   */
  Class<? extends IsContext> context();
  
  /**
   * This attribute - if <b>true</b> will tell Nalu to use history.
   * <br>
   * Default is <b>true</b>.
   *
   * @return the configuration value for history
   */
  boolean history() default true;
  
  /**
   * This attribute will tell Nalu to:
   * <ul>
   * <li>use a hash, if <b>true</b></li>
   * <li>use a hashless url, if <b>false</b></li>
   * </ul>
   *
   * @return the configuration value for using hash
   */
  boolean useHash() default true;
  
  /**
   * This attribute - if <b>true</b> will tell Nalu to add a ':' before
   * a variable value inside the url.
   * <br>
   * Default is <b>false</b>.
   *
   * @return the configuration value for useColonForParametersInUrl
   */
  boolean useColonForParametersInUrl() default false;
  
  /**
   * This attribute will tell Nalu, to use the start route in case
   * an empty hash is found (if <p>true</p>).
   * <br>
   * Default is <b>false</b>
   *
   * @return the configuration value for stayOnSite
   */
  boolean stayOnSite() default false;
  
  /**
   * This attribute will tell Nalu to use a custom alert presenter
   *
   * @return the custom alert presenter
   */
  Class<? extends IsCustomAlertPresenter> alertPresenter() default NoCustomAlertPresenter.class;
  
  /**
   * This attribute will tell Nalu to use a custom confirm presenter
   *
   * @return the custom confirm presenter
   */
  Class<? extends IsCustomConfirmPresenter> confirmPresenter() default NoCustomConfirmPresenter.class;
  
}
