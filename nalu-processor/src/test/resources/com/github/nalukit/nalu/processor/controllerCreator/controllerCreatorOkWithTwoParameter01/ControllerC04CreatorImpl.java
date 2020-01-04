package com.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithTwoParameter01;

import com.github.nalukit.nalu.client.Router;
import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.AbstractControllerCreator;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.application.ControllerFactory;
import com.github.nalukit.nalu.client.internal.application.ControllerInstance;
import com.github.nalukit.nalu.client.internal.application.IsControllerCreator;
import com.github.nalukit.nalu.processor.common.MockContext;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import org.gwtproject.event.shared.SimpleEventBus;

/**
 * Build with Nalu version >>2.0.1-SNAPSHOT<< at >>2020.12.08-10:31:40<< */
public final class ControllerC04CreatorImpl extends AbstractControllerCreator<MockContext> implements IsControllerCreator {
  public ControllerC04CreatorImpl(Router router, MockContext context, SimpleEventBus eventBus) {
    super(router, context, eventBus);
  }

  @Override
  public ControllerInstance create() {
    StringBuilder sb01 = new StringBuilder();
    ControllerInstance controllerInstance = new ControllerInstance();
    controllerInstance.setControllerClassName("com.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithTwoParameter01.ControllerC04");
    AbstractComponentController<?, ?, ?> storedController = ControllerFactory.get().getControllerFormStore("com.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithTwoParameter01.ControllerC04");
    if (storedController == null) {
      sb01.append("controller >>com.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithTwoParameter01.ControllerC04<< --> will be created");
      ClientLogger.get().logSimple(sb01.toString(), 3);
      ControllerC04 controller = new ControllerC04();
      controllerInstance.setController(controller);
      controllerInstance.setCached(false);
      controller.setContext(context);
      controller.setEventBus(eventBus);
      controller.setRouter(router);
      controller.setCached(false);
      controller.setRelatedRoute("/mockShell/route01/*/*");
      controller.setRelatedSelector("selector01");
      sb01.setLength(0);
      sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> created and data injected");
      ClientLogger.get().logDetailed(sb01.toString(), 4);
    } else {
      sb01.append("controller >>").append(storedController.getClass().getCanonicalName()).append("<< --> found in cache -> REUSE!");
      ClientLogger.get().logDetailed(sb01.toString(), 4);
      controllerInstance.setController(storedController);
      controllerInstance.setCached(true);
      controllerInstance.getController().setCached(true);
    }
    return controllerInstance;
  }

  @Override
  public void onFinishCreating(Object object) throws RoutingInterceptionException {
    ControllerC04 controller = (ControllerC04) object;
    StringBuilder sb01 = new StringBuilder();
    IComponent04 component = new Component04();
    sb01.setLength(0);
    sb01.append("component >>com.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithTwoParameter01.Component04<< --> created using new");
    ClientLogger.get().logDetailed(sb01.toString(), 4);
    component.setController(controller);
    sb01.setLength(0);
    sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> created and controller instance injected");
    ClientLogger.get().logDetailed(sb01.toString(), 4);
    controller.setComponent(component);
    sb01.setLength(0);
    sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> instance of >>").append(component.getClass().getCanonicalName()).append("<< injected");
    ClientLogger.get().logDetailed(sb01.toString(), 4);
    component.render();
    sb01.setLength(0);
    sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> rendered");
    ClientLogger.get().logDetailed(sb01.toString(), 4);
    component.bind();
    sb01.setLength(0);
    sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> bound");
    ClientLogger.get().logDetailed(sb01.toString(), 4);
    ClientLogger.get().logSimple("controller >>com.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithTwoParameter01.Component04<< created for route >>/mockShell/route01/*/*<<", 3);
  }

  @Override
  public void setParameter(Object object, String... params) throws RoutingInterceptionException {
    ControllerC04 controller = (ControllerC04) object;
    StringBuilder sb01 = new StringBuilder();
    if (params != null) {
      if (params.length >= 1) {
        sb01.setLength(0);
        sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> using method >>setParameter01<< to set value >>").append(params[0]).append("<<");
        ClientLogger.get().logDetailed(sb01.toString(), 4);
        controller.setParameter01(params[0]);
      }
      if (params.length >= 2) {
        sb01.setLength(0);
        sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> using method >>setParameter02<< to set value >>").append(params[1]).append("<<");
        ClientLogger.get().logDetailed(sb01.toString(), 4);
        controller.setParameter02(params[1]);
      }
    }
  }
}
