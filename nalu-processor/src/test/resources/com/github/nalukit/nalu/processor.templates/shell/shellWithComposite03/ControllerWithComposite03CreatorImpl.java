package com.github.nalukit.nalu.processor.common.ui.controllerWithComposite03;

import com.github.nalukit.nalu.client.IsRouter;
import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.AbstractControllerCreator;
import com.github.nalukit.nalu.client.internal.application.ControllerFactory;
import com.github.nalukit.nalu.client.internal.application.ControllerInstance;
import com.github.nalukit.nalu.client.internal.application.IsControllerCreator;
import com.github.nalukit.nalu.processor.common.MockContext;
import org.gwtproject.event.shared.SimpleEventBus;

/**
 * Build with Nalu version >>HEAD-SNAPSHOT<< at >>2032.10.11-12:34:18<<
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
    controllerInstance.setControllerClassName("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.ControllerWithComposite03");
    AbstractComponentController<?, ?, ?> storedController = ControllerFactory.INSTANCE.getControllerFormStore("com.github.nalukit.nalu.processor.common.ui.controllerWithComposite03.ControllerWithComposite03");
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
  public void setParameter(Object object, String... params) throws RoutingInterceptionException {
    ControllerWithComposite03 controller = (ControllerWithComposite03) object;
    if (params != null) {
      if (params.length >= 1) {
        controller.setParameter03(params[0]);
      }
    }
  }
}


