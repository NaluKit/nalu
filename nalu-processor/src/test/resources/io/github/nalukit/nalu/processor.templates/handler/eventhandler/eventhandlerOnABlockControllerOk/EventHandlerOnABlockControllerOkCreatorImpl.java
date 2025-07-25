package io.github.nalukit.nalu.processor.compositeCreator.ok;

import io.github.nalukit.nalu.client.IsRouter;
import io.github.nalukit.nalu.client.component.AbstractCompositeController;
import io.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import io.github.nalukit.nalu.client.internal.AbstractCompositeCreator;
import io.github.nalukit.nalu.client.internal.application.CompositeFactory;
import io.github.nalukit.nalu.client.internal.application.CompositeInstance;
import io.github.nalukit.nalu.client.internal.application.IsCompositeCreator;
import io.github.nalukit.nalu.processor.common.MockContext;

import java.util.List;
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
    compositeInstance.setCompositeClassName("io.github.nalukit.nalu.processor.compositeCreator.ok.Composite");
    AbstractCompositeController<?, ?, ?> storedComposite = CompositeFactory.INSTANCE.getCompositeFormStore(parentControllerClassName,
                                                                                                           "io.github.nalukit.nalu.processor.compositeCreator.ok.Composite",
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
  
  public void setParameter(Object object, List<String> parameterKeys, List<String> parameterValues)
          throws RoutingInterceptionException {
  }
}
