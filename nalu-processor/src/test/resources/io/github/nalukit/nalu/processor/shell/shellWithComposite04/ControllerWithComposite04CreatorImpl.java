package io.github.nalukit.nalu.processor.common.ui.controllerWithComposite04;

import io.github.nalukit.nalu.client.IsRouter;
import io.github.nalukit.nalu.client.component.AbstractComponentController;
import io.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import io.github.nalukit.nalu.client.internal.AbstractControllerCreator;
import io.github.nalukit.nalu.client.internal.application.ControllerFactory;
import io.github.nalukit.nalu.client.internal.application.ControllerInstance;
import io.github.nalukit.nalu.client.internal.application.IsControllerCreator;
import io.github.nalukit.nalu.processor.common.MockContext;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import org.gwtproject.event.shared.SimpleEventBus;

/**
 * Build with Nalu version >>HEAD-SNAPSHOT<< at >>2042.10.11-12:34:18<<
 */
public final class ControllerWithComposite04CreatorImpl extends AbstractControllerCreator<MockContext> implements IsControllerCreator {
  public ControllerWithComposite04CreatorImpl(IsRouter router, MockContext context,
                                              SimpleEventBus eventBus) {
    super(router, context, eventBus);
  }

  @Override
  public ControllerInstance create(String route) {
    ControllerInstance controllerInstance = new ControllerInstance();
    controllerInstance.setControllerCreator(this);
    controllerInstance.setControllerClassName("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite04.ControllerWithComposite04");
    AbstractComponentController<?, ?, ?> storedController = ControllerFactory.INSTANCE.getControllerFormStore("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite04.ControllerWithComposite04");
    if (storedController == null) {
      ControllerWithComposite04 controller = new ControllerWithComposite04();
      controllerInstance.setController(controller);
      controllerInstance.setCached(false);
      controller.setContext(context);
      controller.setEventBus(eventBus);
      controller.setRouter(router);
      controller.setCached(false);
      controller.setRelatedRoute(route);
      controller.setRelatedSelector("selector04");
      controller.setActivateNaluCommand(() -> {});
    } else {
      controllerInstance.setController(storedController);
      controllerInstance.setCached(true);
      controllerInstance.getController().setCached(true);
    }
    return controllerInstance;
  }

  @Override
  public void onFinishCreating(Object object) {
    ControllerWithComposite04 controller = (ControllerWithComposite04) object;
    IComponent04 component = new Component04();
    component.setController(controller);
    controller.setComponent(component);
    component.render();
    component.bind();
  }

  @Override
  public void setParameter(Object object, List<String> parameterKeys, List<String> parameterValues)
          throws RoutingInterceptionException {
    ControllerWithComposite04 controller = (ControllerWithComposite04) object;
    if (params != null) {
      if (params.length >= 1) {
        controller.setParameter04(params[0]);
      }
    }
  }
}


