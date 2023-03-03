package com.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithTwoParameter03;

import com.github.nalukit.nalu.client.IsRouter;
import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.AbstractControllerCreator;
import com.github.nalukit.nalu.client.internal.application.ControllerFactory;
import com.github.nalukit.nalu.client.internal.application.ControllerInstance;
import com.github.nalukit.nalu.client.internal.application.IsControllerCreator;
import com.github.nalukit.nalu.processor.common.MockContext;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import org.gwtproject.event.shared.SimpleEventBus;

/**
 * Build with Nalu version >>%VERSION_TAG%<< at 2023.03.03-18:04:46
 */
public final class ControllerC06CreatorImpl extends AbstractControllerCreator<MockContext> implements IsControllerCreator {
  public ControllerC06CreatorImpl(IsRouter router, MockContext context, SimpleEventBus eventBus) {
    super(router, context, eventBus);
  }

  @Override
  public ControllerInstance create(String route) {
    ControllerInstance controllerInstance = new ControllerInstance();
    controllerInstance.setControllerCreator(this);
    controllerInstance.setControllerClassName("com.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithTwoParameter03.ControllerC06");
    AbstractComponentController<?, ?, ?> storedController = ControllerFactory.INSTANCE.getControllerFormStore("com.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithTwoParameter03.ControllerC06");
    if (storedController == null) {
      ControllerC06 controller = new ControllerC06();
      controllerInstance.setController(controller);
      controllerInstance.setCached(false);
      controller.setContext(context);
      controller.setEventBus(eventBus);
      controller.setRouter(router);
      controller.setCached(false);
      controller.setRelatedRoute(route);
      controller.setRelatedSelector("selector01");
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
    ControllerC06 controller = (ControllerC06) object;
    IComponent06 component = new Component06();
    component.setController(controller);
    controller.setComponent(component);
    component.render();
    component.bind();
  }

  @Override
  public void setParameter(Object object, List<String> parameterKeys, List<String> parameterValues)
      throws RoutingInterceptionException {
    ControllerC06 controller = (ControllerC06) object;
    if (parameterKeys != null && parameterValues != null) {
      for (int i = 0; i < parameterKeys.size(); i++) {
        if ("parameter02".equals(parameterKeys.get(i))) {
          controller.setParameter02(parameterValues.get(i));
        }
      }
    }
  }
}
