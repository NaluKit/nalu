package com.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithOneParameter01WithoutAcceptParameter;

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
 * Build with Nalu version >>HEAD-SNAPSHOT<< at >>2020.08.31-11:52:30<<
 */
public final class ControllerC03CreatorImpl extends AbstractControllerCreator<MockContext> implements IsControllerCreator {
  public ControllerC03CreatorImpl(Router router, MockContext context, SimpleEventBus eventBus) {
    super(router, context, eventBus);
  }
  
  @Override
  public ControllerInstance create(String route) {
    ControllerInstance controllerInstance = new ControllerInstance();
    controllerInstance.setControllerCreator(this);
    controllerInstance.setControllerClassName("com.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithOneParameter01WithoutAcceptParameter.ControllerC03");
    AbstractComponentController<?, ?, ?> storedController = ControllerFactory.get().getControllerFormStore("com.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithOneParameter01WithoutAcceptParameter.ControllerC03");
    if (storedController == null) {
      ControllerC03 controller = new ControllerC03();
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
    ControllerC03 controller = (ControllerC03) object;
    IComponent03 component = new Component03();
    component.setController(controller);
    controller.setComponent(component);
    component.render();
    component.bind();
  }
  
  @Override
  public void setParameter(Object object, String... params) throws RoutingInterceptionException {
  }
}
