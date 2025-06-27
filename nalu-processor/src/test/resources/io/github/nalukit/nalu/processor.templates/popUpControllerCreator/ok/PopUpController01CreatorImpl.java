package io.github.nalukit.nalu.processor.popUpControllerCreator.ok;

import io.github.nalukit.nalu.client.IsRouter;
import io.github.nalukit.nalu.client.internal.AbstractPopUpControllerCreator;
import io.github.nalukit.nalu.client.internal.application.IsPopUpControllerCreator;
import io.github.nalukit.nalu.client.internal.route.PopUpControllerInstance;
import io.github.nalukit.nalu.processor.common.MockContext;
import java.lang.Object;
import java.lang.Override;
import org.gwtproject.event.shared.SimpleEventBus;

/**
 * Build with Nalu version >>%VERSION_TAG%<< at >>2021.07.31-20:00:49<<
 */
public final class PopUpController01CreatorImpl extends AbstractPopUpControllerCreator<MockContext> implements IsPopUpControllerCreator {
  public PopUpController01CreatorImpl(IsRouter router, MockContext context, SimpleEventBus eventBus) {
    super(router, context, eventBus);
  }

  @Override
  public PopUpControllerInstance create() {
    PopUpControllerInstance popUpControllerInstance = new PopUpControllerInstance();
    popUpControllerInstance.setPopUpControllerClassName("io.github.nalukit.nalu.processor.popUpControllerCreator.ok.PopUpController01");
    popUpControllerInstance.setAlwaysRenderComponent(false);
    PopUpController01 controller = new PopUpController01();
    popUpControllerInstance.setController(controller);
    controller.setContext(context);
    controller.setEventBus(eventBus);
    controller.setRouter(router);
    controller.setName("PopUpController01");
    return popUpControllerInstance;
  }

  @Override
  public void onFinishCreating(Object object) {
    PopUpController01 controller = (PopUpController01) object;
    IPopUpComponent01 component = new PopUpComponent01();
    component.setController(controller);
    controller.setComponent(component);
    component.render();
    component.bind();
  }
}
