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

package com.github.nalukit.nalu.simpleapplication.client;

import com.github.nalukit.nalu.client.application.IsApplication;

/**
 * Implemantation of the NaluSimpleApplication class.
 * <p>
 * <p>Use the @Application annotation to perform configuration informations.</p>
 * <p>In this case we use 'NaluSimpleApplicationLoader' as loader!</p>
 * <p>Please keep in mind, the services are simulated inside the client (because this is not part of the
 * framework). You can use any technique to call the server!</p>
 */
interface NaluSimpleApplication
    extends IsApplication {
}
