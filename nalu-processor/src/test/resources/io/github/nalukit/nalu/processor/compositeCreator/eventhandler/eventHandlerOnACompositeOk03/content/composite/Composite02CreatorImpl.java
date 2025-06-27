package io.github.nalukit.nalu.processor.compositeCreator.eventhandler.eventHandlerOnACompositeOk03.content.composite;

import io.github.nalukit.nalu.client.IsRouter;
import io.github.nalukit.nalu.client.component.AbstractCompositeController;
import io.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import io.github.nalukit.nalu.client.internal.AbstractCompositeCreator;
import io.github.nalukit.nalu.client.internal.application.CompositeFactory;
import io.github.nalukit.nalu.client.internal.application.CompositeInstance;
import io.github.nalukit.nalu.client.internal.application.IsCompositeCreator;
import io.github.nalukit.nalu.processor.common.MockContext;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import org.gwtproject.event.shared.SimpleEventBus;

/**
 * Build with Nalu version >>HEAD-SNAPSHOT<< at 2023.03.03-17:56:30
 */
public final class Composite02CreatorImpl extends AbstractCompositeCreator<MockContext> implements IsCompositeCreator {
  public Composite02CreatorImpl(IsRouter router, MockContext context, SimpleEventBus eventBus) {
    super(router, context, eventBus);
  }

  public CompositeInstance create(String parentControllerClassName, String selector,
                                  boolean scopeGlobal) throws RoutingInterceptionException {
    CompositeInstance compositeInstance = new CompositeInstance();
    compositeInstance.setCompositeClassName("io.github.nalukit.nalu.processor.compositeCreator.eventhandler.eventHandlerOnACompositeOk03.content.composite.Composite02");
    AbstractCompositeController<?, ?, ?> storedComposite = CompositeFactory.INSTANCE.getCompositeFormStore(parentControllerClassName, "io.github.nalukit.nalu.processor.compositeCreator.eventhandler.eventHandlerOnACompositeOk03.content.composite.Composite02", selector);
    if (storedComposite == null) {
      Composite02 composite = new Composite02();
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
      ICompositeComponent02 component = new CompositeComponent02();
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

  @Override
  public void setParameter(Object object, List<String> parameterKeys, List<String> parameterValues)
      throws RoutingInterceptionException {
  }
}
