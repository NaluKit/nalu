package io.github.nalukit.nalu.processor.popUpControllerCreator.eventhandler.eventHandlerOnAPopUpControllerOk03.popUp;

import io.github.nalukit.nalu.client.IsRouter;
import io.github.nalukit.nalu.client.internal.AbstractPopUpControllerCreator;
import io.github.nalukit.nalu.client.internal.application.IsPopUpControllerCreator;
import io.github.nalukit.nalu.client.internal.route.PopUpControllerInstance;
import io.github.nalukit.nalu.processor.common.MockContext;
import io.github.nalukit.nalu.processor.common.event.MockEvent01;
import io.github.nalukit.nalu.processor.common.event.MockEvent02;
import java.lang.Object;
import java.lang.Override;
import org.gwtproject.event.shared.SimpleEventBus;

/**
 * Build with Nalu version >>%VERSION_TAG%<< at >>2022.04.19-17:12:38<<
 */
public final class PopUpController01CreatorImpl extends AbstractPopUpControllerCreator<MockContext> implements IsPopUpControllerCreator {
  public PopUpController01CreatorImpl(IsRouter router, MockContext context,
                                      SimpleEventBus eventBus) {
    super(router, context, eventBus);
  }

  @Override
  public PopUpControllerInstance create() {
    PopUpControllerInstance popUpControllerInstance = new PopUpControllerInstance();
    popUpControllerInstance.setPopUpControllerClassName("io.github.nalukit.nalu.processor.popUpControllerCreator.eventhandler.eventHandlerOnAPopUpControllerOk03.popUp.PopUpController01");
    popUpControllerInstance.setAlwaysRenderComponent(false);
    PopUpController01 controller = new PopUpController01();
    popUpControllerInstance.setController(controller);
    controller.setContext(context);
    controller.setEventBus(eventBus);
    controller.setRouter(router);
    controller.setName("PopUpController01");
    super.eventBus.addHandler(MockEvent01.TYPE, e -> controller.onMockEvent01(e));
    super.eventBus.addHandler(MockEvent02.TYPE, e -> controller.onMockEvent02(e));
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
