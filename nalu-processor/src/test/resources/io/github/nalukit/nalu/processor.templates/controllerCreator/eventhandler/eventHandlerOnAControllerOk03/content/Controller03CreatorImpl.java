package io.github.nalukit.nalu.processor.controllerCreator.eventhandler.eventHandlerOnAControllerOk03.content;

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
 * Build with Nalu version >>%VERSION_TAG%<< at >>2022.04.18-15:47:39<<
 */
public final class Controller03CreatorImpl extends AbstractControllerCreator<MockContext> implements IsControllerCreator {
  public Controller03CreatorImpl(IsRouter router, MockContext context, SimpleEventBus eventBus) {
    super(router, context, eventBus);
  }

  @Override
  public ControllerInstance create(String route) {
    ControllerInstance controllerInstance = new ControllerInstance();
    controllerInstance.setControllerCreator(this);
    controllerInstance.setControllerClassName("io.github.nalukit.nalu.processor.controllerCreator.eventhandler.eventHandlerOnAControllerOk03.content.Controller03");
    AbstractComponentController<?, ?, ?> storedController = ControllerFactory.INSTANCE.getControllerFormStore("io.github.nalukit.nalu.processor.controllerCreator.eventhandler.eventHandlerOnAControllerOk03.content.Controller03");
    if (storedController == null) {
      Controller03 controller = new Controller03();
      controllerInstance.setController(controller);
      controllerInstance.setCached(false);
      controller.setContext(context);
      controller.setEventBus(eventBus);
      controller.setRouter(router);
      controller.setCached(false);
      controller.setRelatedRoute(route);
      controller.setRelatedSelector("selector01");
      controller.setActivateNaluCommand(() -> {
        controller.getHandlerRegistrations().add(this.eventBus.addHandler(io.github.nalukit.nalu.processor.common.event.MockEvent01.TYPE, e -> controller.onMockEvent01(e)));
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
    Controller03 controller = (Controller03) object;
    IComponent03 component = new Component03();
    component.setController(controller);
    controller.setComponent(component);
    component.render();
    component.bind();
  }

  @Override
  public void setParameter(Object object, List<String> parameterKeys, List<String> parameterValues)
          throws RoutingInterceptionException {
  }
}
