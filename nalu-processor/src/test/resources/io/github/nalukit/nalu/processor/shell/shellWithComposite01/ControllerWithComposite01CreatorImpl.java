package io.github.nalukit.nalu.processor.shell.shellWithComposite01;

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
 * Build with Nalu version >>HEAD-SNAPSHOT<< at >>2022.10.11-12:26:15<<
 */
public final class ControllerWithComposite01CreatorImpl extends AbstractControllerCreator<MockContext> implements IsControllerCreator {
  public ControllerWithComposite01CreatorImpl(IsRouter router, MockContext context,
                                              SimpleEventBus eventBus) {
    super(router, context, eventBus);
  }

  @Override
  public ControllerInstance create(String route) {
    ControllerInstance controllerInstance = new ControllerInstance();
    controllerInstance.setControllerCreator(this);
    controllerInstance.setControllerClassName("io.github.nalukit.nalu.processor.shell.shellWithComposite01.ControllerWithComposite01");
    AbstractComponentController<?, ?, ?> storedController = ControllerFactory.INSTANCE.getControllerFormStore("io.github.nalukit.nalu.processor.shell.shellWithComposite01.ControllerWithComposite01");
    if (storedController == null) {
      ControllerWithComposite01 controller = new ControllerWithComposite01();
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
    ControllerWithComposite01 controller = (ControllerWithComposite01) object;
    IComponent01 component = new Component01();
    component.setController(controller);
    controller.setComponent(component);
    component.render();
    component.bind();
  }

  @Override
  public void setParameter(Object object, List<String> parameterKeys, List<String> parameterValues)
      throws RoutingInterceptionException {
    ControllerWithComposite01 controller = (ControllerWithComposite01) object;
    if (parameterKeys != null && parameterValues != null) {
      for (int i = 0; i < parameterKeys.size(); i++) {
        if ("parameter01".equals(parameterKeys.get(i))) {
          controller.setParameter01(parameterValues.get(i));
        }
      }
    }
  }
}
