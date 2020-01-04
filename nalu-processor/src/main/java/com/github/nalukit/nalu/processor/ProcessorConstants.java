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

package com.github.nalukit.nalu.processor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProcessorConstants {

  public final static String META_DATA           = "MetaData";
  public final static String META_INF            = "META-INF";
  public final static String NALU_FOLDER_NAME    = "nalu-route";
  public final static String PARAMETER_DELIMITER = "_pPp_";
  public final static String TYPE_DELIMITER      = "_tTt_";
  public final static String PROPERTIES_POSTFIX  = ".properties";
  public final static String CREATOR_IMPL        = "CreatorImpl";
  public final static String MODULE_IMPL         = "ModuleImpl";
  public final static String PLUGIN_IMPL         = "PluginImpl";
  public final static String PROCESSOR_VERSION   = "2.0.1-SNAPSHOT";
  public final static String BUILD_TIME          = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss").format(new Date(System.currentTimeMillis()));

}
