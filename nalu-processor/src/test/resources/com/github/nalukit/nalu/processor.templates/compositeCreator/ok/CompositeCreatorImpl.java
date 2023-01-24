/*
 * Copyright © 2018 Frank Hossfeld
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nalukit.nalu.processor.compositeCreator.ok;

import com.github.nalukit.nalu.client.IsRouter;
import com.github.nalukit.nalu.client.component.AbstractCompositeController;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.AbstractCompositeCreator;
import com.github.nalukit.nalu.client.internal.application.CompositeFactory;
import com.github.nalukit.nalu.client.internal.application.CompositeInstance;
import com.github.nalukit.nalu.client.internal.application.IsCompositeCreator;
import com.github.nalukit.nalu.processor.common.MockContext;
import org.gwtproject.event.shared.SimpleEventBus;

/**
 * Build with Nalu version >>%VERSION_TAG%<< at >>2020.11.08-16:38:33<<
 */
public final class CompositeCreatorImpl extends AbstractCompositeCreator<MockContext> implements IsCompositeCreator {
  public CompositeCreatorImpl(IsRouter router, MockContext context, SimpleEventBus eventBus) {
    super(router, context, eventBus);
  }
  
  public CompositeInstance create(String parentControllerClassName, String selector,
                                  boolean scopeGlobal) throws RoutingInterceptionException {
    CompositeInstance compositeInstance = new CompositeInstance();
    compositeInstance.setCompositeClassName("com.github.nalukit.nalu.processor.compositeCreator.ok.Composite");
    AbstractCompositeController<?, ?, ?> storedComposite = CompositeFactory.INSTANCE.getCompositeFormStore(parentControllerClassName,
                                                                                                           "com.github.nalukit.nalu.processor.compositeCreator.ok.Composite",
                                                                                                           selector);
    if (storedComposite == null) {
      Composite composite = new Composite();
      compositeInstance.setComposite(composite);
      composite.setParentClassName(parentControllerClassName);
      composite.setCached(false);
      composite.setContext(context);
      composite.setEventBus(eventBus);
      composite.setRouter(router);
      composite.setCached(false);
      if (!scopeGlobal) {
        composite.setSelector(selector);
      }
      composite.setActivateNaluCommand(() -> {});
      ICompositeComponent component = new CompositeComponent();
      component.setController(composite);
      composite.setComponent(component);
      component.render();
      component.bind();
    } else {
      compositeInstance.setComposite(storedComposite);
      compositeInstance.setCached(true);
      compositeInstance.getComposite().setCached(true);
    }
    return compositeInstance;
  }
  
  public void setParameter(Object object, String... params) throws RoutingInterceptionException {
  }
}
