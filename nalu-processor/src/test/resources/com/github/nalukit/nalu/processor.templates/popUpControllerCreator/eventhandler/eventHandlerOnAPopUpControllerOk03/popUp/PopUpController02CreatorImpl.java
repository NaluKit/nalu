package com.github.nalukit.nalu.processor.popUpControllerCreator.eventhandler.eventHandlerOnAPopUpControllerOk03.popUp;

import com.github.nalukit.nalu.client.IsRouter;
import com.github.nalukit.nalu.client.internal.AbstractPopUpControllerCreator;
import com.github.nalukit.nalu.client.internal.application.IsPopUpControllerCreator;
import com.github.nalukit.nalu.client.internal.application.PopUpControllerInstance;
import com.github.nalukit.nalu.processor.common.MockContext;
import java.lang.Object;
import java.lang.Override;
import org.gwtproject.event.shared.SimpleEventBus;

/**
 * Build with Nalu version >>2.10.1<< at >>2022.04.19-18:11:55<<
 */
public final class PopUpController02CreatorImpl extends AbstractPopUpControllerCreator<MockContext> implements IsPopUpControllerCreator {
  public PopUpController02CreatorImpl(IsRouter router, MockContext context,
                                      SimpleEventBus eventBus) {
    super(router, context, eventBus);
  }

  @Override
  public PopUpControllerInstance create() {
    PopUpControllerInstance popUpControllerInstance = new PopUpControllerInstance();
    popUpControllerInstance.setPopUpControllerClassName("com.github.nalukit.nalu.processor.popUpControllerCreator.eventhandler.eventHandlerOnAPopUpControllerOk03.popUp.PopUpController02");
    popUpControllerInstance.setAlwaysRenderComponent(false);
    PopUpController02 controller = new PopUpController02();
    popUpControllerInstance.setController(controller);
    controller.setContext(context);
    controller.setEventBus(eventBus);
    controller.setRouter(router);
    controller.setName("PopUpController02");
    return popUpControllerInstance;
  }

  @Override
  public void onFinishCreating(Object object) {
    PopUpController02 controller = (PopUpController02) object;
    IPopUpComponent02 component = new PopUpComponent02();
    component.setController(controller);
    controller.setComponent(component);
    component.render();
    component.bind();
  }
}
