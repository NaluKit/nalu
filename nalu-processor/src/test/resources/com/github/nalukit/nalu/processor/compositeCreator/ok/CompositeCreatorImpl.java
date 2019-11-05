package com.github.nalukit.nalu.processor.compositeCreator.ok;

import com.github.nalukit.nalu.client.Router;
import com.github.nalukit.nalu.client.component.AbstractCompositeController;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.AbstractCompositeCreator;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.application.CompositeFactory;
import com.github.nalukit.nalu.client.internal.application.CompositeInstance;
import com.github.nalukit.nalu.client.internal.application.IsCompositeCreator;
import com.github.nalukit.nalu.processor.common.MockContext;
import java.lang.Object;
import java.lang.String;
import java.lang.StringBuilder;
import org.gwtproject.event.shared.SimpleEventBus;

/**
 * Build with Nalu version >>2.0.0<< at >>2019.09.14-14:16:18<< */
public final class CompositeCreatorImpl extends AbstractCompositeCreator<MockContext> implements IsCompositeCreator {
  public CompositeCreatorImpl(Router router, MockContext context, SimpleEventBus eventBus) {
    super(router, context, eventBus);
  }

  public CompositeInstance create(String parentControllerClassName) throws
                                                                    RoutingInterceptionException {
    StringBuilder sb01 = new StringBuilder();
    CompositeInstance compositeInstance = new CompositeInstance();
    compositeInstance.setCompositeClassName("com.github.nalukit.nalu.processor.compositeCreator.ok.Composite");
    AbstractCompositeController<?, ?, ?> storedComposite = CompositeFactory.get().getCompositeFormStore(parentControllerClassName, "com.github.nalukit.nalu.processor.compositeCreator.ok.Composite");
    if (storedComposite == null) {
      sb01.append("composite >>com.github.nalukit.nalu.processor.compositeCreator.ok.Composite<< --> will be created");
      ClientLogger.get().logDetailed(sb01.toString(), 4);
      Composite composite = new Composite();
      compositeInstance.setComposite(composite);
      composite.setParentClassName(parentControllerClassName);
      composite.setCached(false);
      composite.setContext(context);
      composite.setEventBus(eventBus);
      composite.setRouter(router);
      composite.setCached(false);
      sb01.setLength(0);
      sb01.append("composite >>com.github.nalukit.nalu.processor.compositeCreator.ok.Composite<< --> created and data injected");
      ClientLogger.get().logDetailed(sb01.toString(), 5);
      ICompositeComponent component = new CompositeComponent();
      sb01.setLength(0);
      sb01.append("component >>com.github.nalukit.nalu.processor.compositeCreator.ok.CompositeComponent<< --> created using new");
      ClientLogger.get().logDetailed(sb01.toString(), 4);
      component.setController(composite);
      sb01.setLength(0);
      sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> created and controller instance injected");
      ClientLogger.get().logDetailed(sb01.toString(), 5);
      composite.setComponent(component);
      sb01.setLength(0);
      sb01.append("composite >>").append(composite.getClass().getCanonicalName()).append("<< --> instance of >>").append(component.getClass().getCanonicalName()).append("<< injected");
      ClientLogger.get().logDetailed(sb01.toString(), 5);
      component.render();
      sb01.setLength(0);
      sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> rendered");
      ClientLogger.get().logDetailed(sb01.toString(), 5);
      component.bind();
      sb01.setLength(0);
      sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> bound");
      ClientLogger.get().logDetailed(sb01.toString(), 5);
      ClientLogger.get().logSimple("compositeModel >>com.github.nalukit.nalu.processor.compositeCreator.ok.CompositeComponent<< created", 4);
    } else {
      sb01.append("composite >>").append(storedComposite.getClass().getCanonicalName()).append("<< --> found in cache -> REUSE!");
      ClientLogger.get().logDetailed(sb01.toString(), 4);
      compositeInstance.setComposite(storedComposite);
      compositeInstance.setCached(true);
      compositeInstance.getComposite().setCached(true);
    }
    return compositeInstance;
  }

  public void setParameter(Object object, String... params) throws RoutingInterceptionException {
    Composite composite = (Composite) object;
    StringBuilder sb01 = new StringBuilder();
  }
}