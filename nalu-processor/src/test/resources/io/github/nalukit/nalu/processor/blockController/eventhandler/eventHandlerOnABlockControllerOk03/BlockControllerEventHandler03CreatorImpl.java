package io.github.nalukit.nalu.processor.blockController.eventhandler.eventHandlerOnABlockControllerOk03;

import io.github.nalukit.nalu.client.IsRouter;
import io.github.nalukit.nalu.client.component.AlwaysShowBlock;
import io.github.nalukit.nalu.client.internal.AbstractBlockControllerCreator;
import io.github.nalukit.nalu.client.internal.application.BlockControllerInstance;
import io.github.nalukit.nalu.client.internal.application.IsBlockControllerCreator;
import io.github.nalukit.nalu.processor.blockController.common.block01.BlockComponent01;
import io.github.nalukit.nalu.processor.blockController.common.block01.IBlockComponent01;
import io.github.nalukit.nalu.processor.common.MockContext;
import io.github.nalukit.nalu.processor.common.event.MockEvent01;
import java.lang.Override;
import org.gwtproject.event.shared.SimpleEventBus;

/**
 * Build with Nalu version >>HEAD-SNAPSHOT<< at >>2022.04.16-20:35:13<<
 */
public final class BlockControllerEventHandler03CreatorImpl extends AbstractBlockControllerCreator<MockContext> implements IsBlockControllerCreator {
  public BlockControllerEventHandler03CreatorImpl(IsRouter router, MockContext context,
                                                  SimpleEventBus eventBus) {
    super(router, context, eventBus);
  }

  @Override
  public BlockControllerInstance create() {
    BlockControllerInstance blockControllerInstance = new BlockControllerInstance();
    blockControllerInstance.setBlockControllerClassName("io.github.nalukit.nalu.processor.blockController.eventhandler.eventHandlerOnABlockControllerOk03.BlockControllerEventHandler03");
    blockControllerInstance.setCondition(new AlwaysShowBlock());
    BlockControllerEventHandler03 controller = new BlockControllerEventHandler03();
    blockControllerInstance.setController(controller);
    controller.setContext(context);
    controller.setEventBus(eventBus);
    controller.setRouter(router);
    controller.setName("blockController03");
    IBlockComponent01 component = new BlockComponent01();
    component.setController(controller);
    controller.setComponent(component);
    component.render();
    super.eventBus.addHandler(MockEvent01.TYPE, e -> controller.onMockEvent01(e));
    component.bind();
    controller.bind();
    return blockControllerInstance;
  }
}
