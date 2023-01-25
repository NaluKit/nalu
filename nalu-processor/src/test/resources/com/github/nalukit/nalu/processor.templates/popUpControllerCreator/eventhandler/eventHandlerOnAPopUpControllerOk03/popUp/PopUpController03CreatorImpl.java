package com.github.nalukit.nalu.processor.popUpControllerCreator.eventhandler.eventHandlerOnAPopUpControllerOk03.popUp;

import com.github.nalukit.nalu.client.IsRouter;
import com.github.nalukit.nalu.client.internal.AbstractPopUpControllerCreator;
import com.github.nalukit.nalu.client.internal.application.IsPopUpControllerCreator;
import com.github.nalukit.nalu.client.internal.route.PopUpControllerInstance;
import com.github.nalukit.nalu.processor.common.MockContext;
import com.github.nalukit.nalu.processor.common.event.MockEvent01;
import java.lang.Object;
import java.lang.Override;
import org.gwtproject.event.shared.SimpleEventBus;

/**
 * Build with Nalu version >>%VERSION_TAG%<< at >>2022.04.19-18:11:55<<
 */
public final class PopUpController03CreatorImpl extends AbstractPopUpControllerCreator<MockContext> implements IsPopUpControllerCreator {
  public PopUpController03CreatorImpl(IsRouter router, MockContext context,
                                      SimpleEventBus eventBus) {
    super(router, context, eventBus);
  }

  @Override
  public PopUpControllerInstance create() {
    PopUpControllerInstance popUpControllerInstance = new PopUpControllerInstance();
    popUpControllerInstance.setPopUpControllerClassName("com.github.nalukit.nalu.processor.popUpControllerCreator.eventhandler.eventHandlerOnAPopUpControllerOk03.popUp.PopUpController03");
    popUpControllerInstance.setAlwaysRenderComponent(false);
    PopUpController03 controller = new PopUpController03();
    popUpControllerInstance.setController(controller);
    controller.setContext(context);
    controller.setEventBus(eventBus);
    controller.setRouter(router);
    controller.setName("PopUpController03");
    super.eventBus.addHandler(MockEvent01.TYPE, e -> controller.onMockEvent01(e));
    return popUpControllerInstance;
  }

  @Override
  public void onFinishCreating(Object object) {
    PopUpController03 controller = (PopUpController03) object;
    IPopUpComponent03 component = new PopUpComponent03();
    component.setController(controller);
    controller.setComponent(component);
    component.render();
    component.bind();
  }
}
