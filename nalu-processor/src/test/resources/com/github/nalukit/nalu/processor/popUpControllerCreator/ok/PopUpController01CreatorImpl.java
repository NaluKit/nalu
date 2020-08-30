package com.github.nalukit.nalu.processor.popUpControllerCreator.ok;

import com.github.nalukit.nalu.client.Router;
import com.github.nalukit.nalu.client.internal.AbstractPopUpControllerCreator;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.application.IsPopUpControllerCreator;
import com.github.nalukit.nalu.client.internal.application.PopUpControllerInstance;
import com.github.nalukit.nalu.processor.common.MockContext;
import java.lang.Override;
import java.lang.StringBuilder;
import org.gwtproject.event.shared.SimpleEventBus;

/**
 * Build with Nalu version >>HEAD-SNAPSHOT<< at >>2020.09.14-14:19:31<< */
public final class PopUpController01CreatorImpl extends AbstractPopUpControllerCreator<MockContext> implements IsPopUpControllerCreator {
  public PopUpController01CreatorImpl(Router router, MockContext context, SimpleEventBus eventBus) {
    super(router, context, eventBus);
  }

  @Override
  public PopUpControllerInstance create() {
    StringBuilder sb01 = new StringBuilder();
    PopUpControllerInstance popUpControllerInstance = new PopUpControllerInstance();
    popUpControllerInstance.setPopUpControllerClassName("com.github.nalukit.nalu.processor.popUpControllerCreator.ok.PopUpController01");
    sb01.append("popUpController >>com.github.nalukit.nalu.processor.popUpControllerCreator.ok.PopUpController01<< --> will be created");
    ClientLogger.get().logSimple(sb01.toString(), 3);
    PopUpController01 controller = new PopUpController01();
    popUpControllerInstance.setController(controller);
    controller.setContext(context);
    controller.setEventBus(eventBus);
    controller.setRouter(router);
    controller.setName("PopUpController01");
    sb01.setLength(0);
    sb01.append("controller >>").append(controller.getClass().getCanonicalName()).append("<< --> created and data injected");
    ClientLogger.get().logDetailed(sb01.toString(), 4);
    IPopUpComponent01 component = new PopUpComponent01();
    sb01.setLength(0);
    sb01.append("component >>com.github.nalukit.nalu.processor.popUpControllerCreator.ok.PopUpComponent01<< --> created using new");
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
    ClientLogger.get().logSimple("controller >>com.github.nalukit.nalu.processor.popUpControllerCreator.ok.PopUpController01<< created for event >>PopUpController01<<", 3);
    return popUpControllerInstance;
  }
}