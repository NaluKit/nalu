package com.github.nalukit.nalu.processor.blockController.eventhandler.eventHandlerOnABlockControllerOk03;

import com.github.nalukit.nalu.client.IsRouter;
import com.github.nalukit.nalu.client.component.AlwaysShowBlock;
import com.github.nalukit.nalu.client.internal.AbstractBlockControllerCreator;
import com.github.nalukit.nalu.client.internal.application.BlockControllerInstance;
import com.github.nalukit.nalu.client.internal.application.IsBlockControllerCreator;
import com.github.nalukit.nalu.processor.blockController.common.block01.BlockComponent01;
import com.github.nalukit.nalu.processor.blockController.common.block01.IBlockComponent01;
import com.github.nalukit.nalu.processor.common.MockContext;
import java.lang.Override;
import org.gwtproject.event.shared.SimpleEventBus;

/**
 * Build with Nalu version >>%VERSION_TAG%<< at >>2022.04.16-20:35:13<<
 */
public final class BlockControllerEventHandler02CreatorImpl extends AbstractBlockControllerCreator<MockContext> implements IsBlockControllerCreator {
  public BlockControllerEventHandler02CreatorImpl(IsRouter router, MockContext context,
                                                  SimpleEventBus eventBus) {
    super(router, context, eventBus);
  }

  @Override
  public BlockControllerInstance create() {
    BlockControllerInstance blockControllerInstance = new BlockControllerInstance();
    blockControllerInstance.setBlockControllerClassName("com.github.nalukit.nalu.processor.blockController.eventhandler.eventHandlerOnABlockControllerOk03.BlockControllerEventHandler02");
    blockControllerInstance.setCondition(new AlwaysShowBlock());
    BlockControllerEventHandler02 controller = new BlockControllerEventHandler02();
    blockControllerInstance.setController(controller);
    controller.setContext(context);
    controller.setEventBus(eventBus);
    controller.setRouter(router);
    controller.setName("blockController02");
    IBlockComponent01 component = new BlockComponent01();
    component.setController(controller);
    controller.setComponent(component);
    component.render();
    component.bind();
    controller.bind();
    return blockControllerInstance;
  }
}
