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

package com.github.nalukit.nalu.processor.compositeCreator.ok;

import com.github.nalukit.nalu.client.Router;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.AbstractCompositeCreator;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.application.CompositeInstance;
import com.github.nalukit.nalu.client.internal.application.IsCompositeCreator;
import com.github.nalukit.nalu.processor.common.MockContext;
import java.lang.String;
import java.lang.StringBuilder;
import org.gwtproject.event.shared.SimpleEventBus;

public final class CompositeCreatorImpl extends AbstractCompositeCreator<MockContext> implements IsCompositeCreator {
  public CompositeCreatorImpl(Router router, MockContext context, SimpleEventBus eventBus) {
    super(router, context, eventBus);
  }

  public CompositeInstance create(String... parms) throws RoutingInterceptionException {
    StringBuilder sb01 = new StringBuilder();
    sb01.append("compositeModel >>com.github.nalukit.nalu.processor.compositeCreator.ok.Composite<< --> will be created");
    ClientLogger.get().logDetailed(sb01.toString(), 4);
    Composite compositeModel = new Composite();
    compositeModel.setContext(context);
    compositeModel.setEventBus(eventBus);
    compositeModel.setRouter(router);
    sb01 = new StringBuilder();
    sb01.append("compositeModel >>com.github.nalukit.nalu.processor.compositeCreator.ok.Composite<< --> created and data injected");
    ClientLogger.get().logDetailed(sb01.toString(), 5);
    ICompositeComponent component = new CompositeComponent();
    sb01 = new StringBuilder();
    sb01.append("component >>com.github.nalukit.nalu.processor.compositeCreator.ok.CompositeComponent<< --> created using new");
    ClientLogger.get().logDetailed(sb01.toString(), 4);
    component.setController(compositeModel);
    sb01 = new StringBuilder();
    sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> created and controller instance injected");
    ClientLogger.get().logDetailed(sb01.toString(), 5);
    compositeModel.setComponent(component);
    sb01 = new StringBuilder();
    sb01.append("compositeModel >>").append(compositeModel.getClass().getCanonicalName()).append("<< --> instance of >>").append(component.getClass().getCanonicalName()).append("<< injected");
    ClientLogger.get().logDetailed(sb01.toString(), 5);
    component.render();
    sb01 = new StringBuilder();
    sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> rendered");
    ClientLogger.get().logDetailed(sb01.toString(), 5);
    component.bind();
    sb01 = new StringBuilder();
    sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> bound");
    ClientLogger.get().logDetailed(sb01.toString(), 5);
    ClientLogger.get().logSimple("compositeModel >>com.github.nalukit.nalu.processor.compositeCreator.ok.CompositeComponent<< created", 4);
    CompositeInstance compositeInstance = new CompositeInstance();
    compositeInstance.setCompositeClassName(compositeModel.getClass().getCanonicalName());
    compositeInstance.setComposite(compositeModel);
    return compositeInstance;
  }
}
