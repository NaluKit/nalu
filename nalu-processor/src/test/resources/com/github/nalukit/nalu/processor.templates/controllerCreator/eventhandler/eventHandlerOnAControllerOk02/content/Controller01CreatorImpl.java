package com.github.nalukit.nalu.processor.controllerCreator.eventhandler.eventHandlerOnAControllerOk02.content;

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
import org.gwtproject.event.shared.SimpleEventBus;

/**
 * Build with Nalu version >>HEAD-SNAPSHOT<< at >>2022.04.18-15:47:39<<
 */
public final class Controller01CreatorImpl extends AbstractControllerCreator<MockContext> implements IsControllerCreator {
  public Controller01CreatorImpl(IsRouter router, MockContext context, SimpleEventBus eventBus) {
    super(router, context, eventBus);
  }

  @Override
  public ControllerInstance create(String route) {
    ControllerInstance controllerInstance = new ControllerInstance();
    controllerInstance.setControllerCreator(this);
    controllerInstance.setControllerClassName("com.github.nalukit.nalu.processor.controllerCreator.eventhandler.eventHandlerOnAControllerOk02.content.Controller01");
    AbstractComponentController<?, ?, ?> storedController = ControllerFactory.get().getControllerFormStore("com.github.nalukit.nalu.processor.controllerCreator.eventhandler.eventHandlerOnAControllerOk02.content.Controller01");
    if (storedController == null) {
      Controller01 controller = new Controller01();
      controllerInstance.setController(controller);
      controllerInstance.setCached(false);
      controller.setContext(context);
      controller.setEventBus(eventBus);
      controller.setRouter(router);
      controller.setCached(false);
      controller.setRelatedRoute(route);
      controller.setRelatedSelector("selector01");
      controller.setActivateNaluCommand(() -> {
        controller.getHandlerRegistrations().add(this.eventBus.addHandler(com.github.nalukit.nalu.processor.common.event.MockEvent01.TYPE, e -> controller.onMockEvent01(e)));
        controller.getHandlerRegistrations().add(this.eventBus.addHandler(com.github.nalukit.nalu.processor.common.event.MockEvent02.TYPE, e -> controller.onMockEvent02(e)));
      });
    } else {
      controllerInstance.setController(storedController);
      controllerInstance.setCached(true);
      controllerInstance.getController().setCached(true);
    }
    return controllerInstance;
  }

  @Override
  public void onFinishCreating(Object object) {
    Controller01 controller = (Controller01) object;
    IComponent01 component = new Component01();
    component.setController(controller);
    controller.setComponent(component);
    component.render();
    component.bind();
  }

  @Override
  public void setParameter(Object object, String... params) throws RoutingInterceptionException {
  }
}
