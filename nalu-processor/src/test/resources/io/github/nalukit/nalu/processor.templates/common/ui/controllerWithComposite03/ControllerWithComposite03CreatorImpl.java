package io.github.nalukit.nalu.processor.common.ui.controllerWithComposite03;

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
 * Build with Nalu version >>%VERSION_TAG%<< at 2023.03.03-18:01:15
 */
public final class ControllerWithComposite03CreatorImpl extends AbstractControllerCreator<MockContext> implements IsControllerCreator {
  public ControllerWithComposite03CreatorImpl(IsRouter router, MockContext context,
                                              SimpleEventBus eventBus) {
    super(router, context, eventBus);
  }

  @Override
  public ControllerInstance create(String route) {
    ControllerInstance controllerInstance = new ControllerInstance();
    controllerInstance.setControllerCreator(this);
    controllerInstance.setControllerClassName("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.ControllerWithComposite03");
    AbstractComponentController<?, ?, ?> storedController = ControllerFactory.INSTANCE.getControllerFormStore("io.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.ControllerWithComposite03");
    if (storedController == null) {
      ControllerWithComposite03 controller = new ControllerWithComposite03();
      controllerInstance.setController(controller);
      controllerInstance.setCached(false);
      controller.setContext(context);
      controller.setEventBus(eventBus);
      controller.setRouter(router);
      controller.setCached(false);
      controller.setRelatedRoute(route);
      controller.setRelatedSelector("selector03");
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
    ControllerWithComposite03 controller = (ControllerWithComposite03) object;
    IComponent03 component = new Component03();
    component.setController(controller);
    controller.setComponent(component);
    component.render();
    component.bind();
  }

  @Override
  public void setParameter(Object object, List<String> parameterKeys, List<String> parameterValues)
      throws RoutingInterceptionException {
    ControllerWithComposite03 controller = (ControllerWithComposite03) object;
    if (parameterKeys != null && parameterValues != null) {
      for (int i = 0; i < parameterKeys.size(); i++) {
        if ("parameter03".equals(parameterKeys.get(i))) {
          controller.setParameter03(parameterValues.get(i));
        }
      }
    }
  }
}
