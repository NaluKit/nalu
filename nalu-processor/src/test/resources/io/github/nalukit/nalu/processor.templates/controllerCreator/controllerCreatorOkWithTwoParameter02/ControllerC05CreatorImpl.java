package io.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithTwoParameter02;

import io.github.nalukit.nalu.client.component.AbstractComponentController;
import io.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import io.github.nalukit.nalu.client.internal.AbstractControllerCreator;
import io.github.nalukit.nalu.client.internal.ClientLogger;
import io.github.nalukit.nalu.client.internal.application.ControllerFactory;
import io.github.nalukit.nalu.client.internal.application.ControllerInstance;
import io.github.nalukit.nalu.client.internal.application.IsControllerCreator;
import io.github.nalukit.nalu.processor.common.MockContext;

import java.lang.String;
import org.gwtproject.event.shared.SimpleEventBus;

public final class ControllerC05CreatorImpl extends AbstractControllerCreator<MockContext> implements IsControllerCreator {
  public ControllerC05CreatorImpl(Router router, MockContext context, SimpleEventBus eventBus) {
    super(router, context, eventBus);
  }

  public ControllerInstance create(String... params) throws RoutingInterceptionException {
    StringBuilder sb01 = new StringBuilder();
    ControllerInstance controllerInstance = new ControllerInstance();
    controllerInstance.setControllerCreator(this);
    controllerInstance.setControllerClassName("io.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithTwoParameter02.ControllerC05");
    AbstractComponentController<?, ?, ?> storedController = ControllerFactory.INSTANCE.getControllerFormStore("io.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithTwoParameter02.ControllerC05");
    if (storedController == null) {
      sb01.append("controller >>io.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithTwoParameter02.ControllerC05<< --> will be created");
      ClientLogger.get().logSimple(sb01.toString(), 3);
      ControllerC05 controller = new ControllerC05();
      controllerInstance.setController(controller);
      controllerInstance.setCached(false);
      controller.setContext(context);
      controller.setEventBus(eventBus);
      controller.setRouter(router);
      controller.setCached(false);
      sb01 = new StringBuilder();
      sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> created and data injected");
      ClientLogger.get().logDetailed(sb01.toString(), 4);
      IComponent05 component = new Component05();
      sb01 = new StringBuilder();
      sb01.append("component >>io.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithTwoParameter02.Component05<< --> created using new");
      ClientLogger.get().logDetailed(sb01.toString(), 4);
      component.setController(controller);
      sb01 = new StringBuilder();
      sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> created and controller instance injected");
      ClientLogger.get().logDetailed(sb01.toString(), 4);
      controller.setComponent(component);
      sb01 = new StringBuilder();
      sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> instance of >>").append(component.getClass().getCanonicalName()).append("<< injected");
      ClientLogger.get().logDetailed(sb01.toString(), 4);
      component.render();
      sb01 = new StringBuilder();
      sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> rendered");
      ClientLogger.get().logDetailed(sb01.toString(), 4);
      component.bind();
      sb01 = new StringBuilder();
      sb01.append("component >>").append(component.getClass().getCanonicalName()).append("<< --> bound");
      ClientLogger.get().logDetailed(sb01.toString(), 4);
      ClientLogger.get().logSimple("controller >>io.github.nalukit.nalu.processor.controllerCreator.controllerCreatorOkWithTwoParameter02.Component05<< created for route >>/mockShell/route01<<", 3);
      if (params != null) {
        if (params.length >= 1) {
          sb01 = new StringBuilder();
          sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> using method >>setParameter01<< to set value >>").append(params[0]).append("<<");
          ClientLogger.get().logDetailed(sb01.toString(), 4);
          controller.setParameter01(params[0]);
        }
      }
    } else {
      sb01.append("controller >>").append(storedController.getClass().getCanonicalName()).append("<< --> found in cache -> REUSE!");
      ClientLogger.get().logDetailed(sb01.toString(), 4);
      controllerInstance.setController(storedController);
      controllerInstance.setCached(true);
      controllerInstance.getController().setCached(true);
    }
    return controllerInstance;
  }
}
