package com.github.nalukit.nalu.processor.parameterConstraint.ok01;

import com.github.nalukit.nalu.client.Router;
import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.constrain.IsParameterConstraintRule;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.AbstractControllerCreator;
import com.github.nalukit.nalu.client.internal.application.ControllerFactory;
import com.github.nalukit.nalu.client.internal.application.ControllerInstance;
import com.github.nalukit.nalu.client.internal.application.IsControllerCreator;
import com.github.nalukit.nalu.client.internal.constrain.ParameterConstraintRuleFactory;
import com.github.nalukit.nalu.processor.common.MockContext;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import org.gwtproject.event.shared.SimpleEventBus;

/**
 * Build with Nalu version >>HEAD-SNAPSHOT<< at >>2021.07.29-07:45:00<<
 */
public final class Controller01CreatorImpl extends AbstractControllerCreator<MockContext> implements IsControllerCreator {
  public Controller01CreatorImpl(Router router, MockContext context, SimpleEventBus eventBus) {
    super(router, context, eventBus);
  }

  @Override
  public ControllerInstance create(String route) {
    ControllerInstance controllerInstance = new ControllerInstance();
    controllerInstance.setControllerCreator(this);
    controllerInstance.setControllerClassName("com.github.nalukit.nalu.processor.parameterConstraint.ok01.Controller01");
    AbstractComponentController<?, ?, ?> storedController = ControllerFactory.get().getControllerFormStore("com.github.nalukit.nalu.processor.parameterConstraint.ok01.Controller01");
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
    Controller01 controller = (Controller01) object;
    if (params != null) {
      if (params.length >= 1) {
        IsParameterConstraintRule rule = ParameterConstraintRuleFactory.get().get("com.github.nalukit.nalu.processor.parameterConstraint.ParameterConstraintRule01");
        if (rule != null) {
          if (!rule.isValid(params[0])) {
            throw new RoutingInterceptionException("Controller01", "asdfasd");
          }
        }
        controller.setParameter01(params[0]);
      }
    }
  }
}
