package com.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithOneParameter01;

import com.github.nalukit.nalu.client.Router;
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
 * Build with Nalu version >>2.7.2-gwt-2.8.2<< at >>2020.08.31-11:52:30<<
 */
public final class ControllerC02CreatorImpl extends AbstractControllerCreator<MockContext> implements IsControllerCreator {
  public ControllerC02CreatorImpl(Router router, MockContext context, SimpleEventBus eventBus) {
    super(router, context, eventBus);
  }
  
  @Override
  public ControllerInstance create(String route) {
    ControllerInstance controllerInstance = new ControllerInstance();
    controllerInstance.setControllerCreator(this);
    controllerInstance.setControllerClassName("com.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithOneParameter01.ControllerC02");
    AbstractComponentController<?, ?, ?> storedController = ControllerFactory.get().getControllerFormStore("com.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithOneParameter01.ControllerC02");
    if (storedController == null) {
      ControllerC02 controller = new ControllerC02();
      controllerInstance.setController(controller);
      controllerInstance.setCached(false);
      controller.setContext(context);
      controller.setEventBus(eventBus);
      controller.setRouter(router);
      controller.setCached(false);
      controller.setRelatedRoute(route);
      controller.setRelatedSelector("selector01");
    } else {
      controllerInstance.setController(storedController);
      controllerInstance.setCached(true);
      controllerInstance.getController().setCached(true);
    }
    return controllerInstance;
  }
  
  @Override
  public void onFinishCreating(Object object) {
    ControllerC02 controller = (ControllerC02) object;
    IComponent02 component = new Component02();
    component.setController(controller);
    controller.setComponent(component);
    component.render();
    component.bind();
  }
  
  @Override
  public void setParameter(Object object, String... params) throws RoutingInterceptionException {
    ControllerC02 controller = (ControllerC02) object;
    if (params != null) {
      if (params.length >= 1) {
        controller.setParameter01(params[0]);
      }
    }
  }
}
