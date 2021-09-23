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

package com.github.nalukit.nalu.client.context;

import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

import java.util.Date;

/**
 * Marks a class as Nalu application context used inside a multi module project.
 * <p>
 * Keep in mind, that in a Nalu multi module environment your context needs to
 * extend {@link com.github.nalukit.nalu.client.context.AbstractModuleContext}!
 */
public interface IsModuleContext
    extends IsContext {
  
  /**
   * Gets the application context
   *
   * @return application context
   */
  ContextDataStore getApplicationContext();

  /**
   * Sets the application context.
   *
   * <b>DO NOT USE IT. THIS METHOD IS USED BY
   * THE FRAMEWORK AND USING IT MIGHT LEAD TO
   * UNEXPECTED RESULTS!</b>
   *
   * @param applicationContext context of the parent module
   */
  @NaluInternalUse
  void setApplicationContext(ContextDataStore applicationContext);

  /**
   * Returns the application build time set by the Nalu processor.
   *
   * @return build time of the application set by the Nalu processor
   */
  Date getApplicationBuildTime();

  /**
   * Sets the application build time.
   * <p>
   * Note:
   * Keep in mind, that Nalu will use the method.
   * Using the method inside your application will lead to
   * unexpected results.
   *
   * @param applicationBuildTime the application build time
   */
  void setApplicationBuildTime(Date applicationBuildTime);

  /**
   * Returns the application version. The value can be set by using
   * the @Version annotation.
   *
   * @return version of the applicaiton set by the Version annotation
   */
  String getApplicationVersion();

  /**
   * Sets the application version.
   * <p>
   * Note:
   * Keep in mind, that Nalu will use the method.
   * Using the method inside your application will lead to
   * unexpected results.
   *
   * @param applicationVersion the application version
   */
  void setApplicationVersion(String applicationVersion);

}
