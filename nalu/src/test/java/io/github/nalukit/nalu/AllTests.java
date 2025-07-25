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

package io.github.nalukit.nalu;

import io.github.nalukit.nalu.client.NaluTest;
import io.github.nalukit.nalu.client.RouterImplTest;
import io.github.nalukit.nalu.client.RouterUtilsTest;
import io.github.nalukit.nalu.client.RoutingTest;
import io.github.nalukit.nalu.client.internal.application.RouterStateEventFactoryTest;
import io.github.nalukit.nalu.client.internal.route.RouteConfigTest;
import io.github.nalukit.nalu.client.internal.route.RouteParserTest;
import io.github.nalukit.nalu.client.internal.route.RouterHashImplTest;
import io.github.nalukit.nalu.client.internal.validation.RouteValidationTest;
import io.github.nalukit.nalu.client.util.NaluUtilsTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite()
@SelectClasses({ NaluTest.class,
                 NaluUtilsTest.class,
                 RouteConfigTest.class,
                 RouteParserTest.class,
                 RouterHashImplTest.class,
                 RouterImplTest.class,
                 RouterUtilsTest.class,
                 RoutingTest.class,
                 RouteValidationTest.class,
                 RouterStateEventFactoryTest.class })
public class AllTests {
}
