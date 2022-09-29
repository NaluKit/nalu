/*
 * Copyright (c) 2018 - 2020 - Frank Hossfeld
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

package com.github.nalukit.nalu;

import com.github.nalukit.nalu.client.NaluTest;
import com.github.nalukit.nalu.client.RouterImplTest;
import com.github.nalukit.nalu.client.RouterUtilsTest;
import com.github.nalukit.nalu.client.RoutingTest;
import com.github.nalukit.nalu.client.internal.route.RouteConfigTest;
import com.github.nalukit.nalu.client.internal.route.RouteParserTest;
import com.github.nalukit.nalu.client.internal.route.RouterHashImplTest;
import com.github.nalukit.nalu.client.internal.validation.RouteValidationTest;
import com.github.nalukit.nalu.client.util.NaluUtilsTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@SelectClasses({ NaluTest.class,
                 NaluUtilsTest.class,
                 RouteConfigTest.class,
                 RouteParserTest.class,
                 RouterHashImplTest.class,
                 RouterImplTest.class,
                 RouterUtilsTest.class,
                 RoutingTest.class,
                 RouteValidationTest.class })
public class AllTests {
}
