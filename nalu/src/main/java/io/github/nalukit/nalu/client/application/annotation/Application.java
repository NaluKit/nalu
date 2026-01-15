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

package io.github.nalukit.nalu.client.application.annotation;

import io.github.nalukit.nalu.client.application.AbstractLoader;
import io.github.nalukit.nalu.client.context.IsContext;
import io.github.nalukit.nalu.client.internal.NoCustomAlertPresenter;
import io.github.nalukit.nalu.client.internal.NoCustomConfirmPresenter;
import io.github.nalukit.nalu.client.internal.application.DefaultLoader;
import io.github.nalukit.nalu.client.plugin.IsCustomAlertPresenter;
import io.github.nalukit.nalu.client.plugin.IsCustomConfirmPresenter;

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
 * <li>usingHash: if usingHash is true, use a hash based url, otherwise a non hash based url</li>
 * <li>usingUnderscoreForParametersInUrl: if usingUnderscoreForParametersInUrl is true, Nalu expects parameters with a leading colon in urls</li>
 * <li>stayOnSite: if stayOnSite is true, Nalu will replace history with the start-route in case hash is empty, else Nalu will only update it.</li>
 * </ul>
 *
 * @author Frank Hossfeld
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Application {

  /**
   * the application loader of the application. Will be executed in case the
   * application is started. This is a good place to load application data.
   * F.e.: Meta-data, store values, etc.
   * <br>
   * This loader will be executed before any module is loaded!
   * <br>
   * The application loader is optional.
   *
   * @return the application loader
   */
  Class<? extends AbstractLoader<?>> loader() default DefaultLoader.class;

  /**
   * the post application start loader of the application. Will be executed in
   * case the application is started and after all modules have loaded. This is
   * a good place to load application data that is relevant also for modules.
   * F.e.: Meta-data, store values, etc.
   * <br>
   * This loader will be executed after all modules are loaded! If the Nalu
   * application does not have modules, the loader is executed immediately
   * after the loader (if exists).
   * <br>
   * The application loader is optional.
   *
   * @return the post application laoder loader
   */
  Class<? extends AbstractLoader<?>> postLoader() default DefaultLoader.class;

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
  boolean isUsingHistory() default true;

  /**
   * This attribute will tell Nalu to:
   * <ul>
   * <li>use a hash, if <b>true</b></li>
   * <li>use a hashless url, if <b>false</b></li>
   * </ul>
   *
   * @return the configuration value for using hash
   */
  boolean usingHash() default true;

  /**
   * This attribute - if <b>true</b> will tell Nalu to add a ':' before
   * a variable value inside the url.
   * <br>
   * Default is <b>false</b>.
   *
   * @return the configuration value for usingUnderscoreForParametersInUrl
   */
  boolean usingUnderscoreForParametersInUrl() default false;

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

  /**
   * This attribute will tell Nalu to route to a specific route in case the route is illegal. Using
   * the default value will cause an error messages. Setting a value will route to that target.
   * <br>
   * Defaul is <b>empty route</b>
   *
   * @return empty String (default) or the route to go to in case the route is illegal.
   */
  String illegalRouteTarget() default "";

  /**
   * This attribute will tell Nalu to handle the usingBaseHref.
   * <br>
   * Defaul is a<b>false</b> value, which means usingBaseHref-handling is disabled
   *
   * @return a boolean.
   */
  boolean usingBaseHref() default false;

  /**
   * This attribute will tell Nalu if there is a trailing slahs at the end of the hash or not the.
   * <br>
   * Defaul is a<b>false</b> value, which means no trailing slash at the end of the hash
   *
   * @return a boolean.
   */
  boolean usingTrailingSlash() default false;

}
