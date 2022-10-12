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

package com.github.nalukit.nalu.processor;

import com.github.nalukit.nalu.processor.model.MetaModelTest;
import com.github.nalukit.nalu.processor.model.intern.ControllerModelTest;
import com.github.nalukit.nalu.processor.test.*;
import com.github.nalukit.nalu.processor.test.model.intern.ClassNameModelTest;
import com.github.nalukit.nalu.processor.test.model.property.PropertiesTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({ ApplicationTest.class,
                 BlockControllerTest.class,
                 ClassNameModelTest.class,
                 CompositeCreatorTest.class,
                 CompositeTest.class,
                 CompositesTest.class,
                 ConsistenceTest.class,
                 ControllerCreatorTest.class,
                 ControllerModelTest.class,
                 ControllerTest.class,
                 ErrorPopUpControllerTest.class,
                 EventHandlerTest.class,
                 EventHandlerBlockControllerTest.class,
                 EventHandlerCompositeTest.class,
                 EventHandlerControllerTest.class,
                 EventHandlerErrorPopUpControllerTest.class,
                 EventHandlerHandlerTest.class,
                 EventHandlerPopUpControllerTest.class,
                 EventHandlerShellTest.class,
                 FiltersTest.class,
                 HandlerTest.class,
                 LoggerTest.class,
                 MetaModelTest.class,
                 ModuleTest.class,
                 ParameterConstraintRuleTest.class,
                 ParameterConstraintTest.class,
                 PopUpControllerCreatorTest.class,
                 PopUpFiltersTest.class,
                 PropertiesTest.class,
                 ShellCreatorTest.class,
                 ShellTest.class,
                 TrackerTest.class,
                 VersionTest.class })
public class AllTests {
}
