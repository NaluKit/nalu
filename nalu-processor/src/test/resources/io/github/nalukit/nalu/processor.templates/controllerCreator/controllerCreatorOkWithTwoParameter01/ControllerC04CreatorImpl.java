package io.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithTwoParameter01;

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
 * Build with Nalu version >>%VERSION_TAG%<< at 2023.03.03-20:27:20
 */
public final class ControllerC04CreatorImpl extends AbstractControllerCreator<MockContext> implements IsControllerCreator {
  public ControllerC04CreatorImpl(IsRouter router, MockContext context, SimpleEventBus eventBus) {
    super(router, context, eventBus);
  }

  @Override
  public ControllerInstance create(String route) {
    ControllerInstance controllerInstance = new ControllerInstance();
    controllerInstance.setControllerCreator(this);
    controllerInstance.setControllerClassName("io.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithTwoParameter01.ControllerC04");
    AbstractComponentController<?, ?, ?> storedController = ControllerFactory.INSTANCE.getControllerFormStore("io.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithTwoParameter01.ControllerC04");
    if (storedController == null) {
      ControllerC04 controller = new ControllerC04();
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
    ControllerC04 controller = (ControllerC04) object;
    IComponent04 component = new Component04();
    component.setController(controller);
    controller.setComponent(component);
    component.render();
    component.bind();
  }

  @Override
  public void setParameter(Object object, List<String> parameterKeys, List<String> parameterValues)
      throws RoutingInterceptionException {
    ControllerC04 controller = (ControllerC04) object;
    if (parameterKeys != null && parameterValues != null) {
      for (int i = 0; i < parameterKeys.size(); i++) {
        if ("parameter01".equals(parameterKeys.get(i))) {
          controller.setParameter01(parameterValues.get(i));
        }
        if ("parameter02".equals(parameterKeys.get(i))) {
          controller.setParameter02(parameterValues.get(i));
        }
      }
    }
  }
}