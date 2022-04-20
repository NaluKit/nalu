package com.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk02;

import com.github.nalukit.nalu.client.IsRouter;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.AbstractShellCreator;
import com.github.nalukit.nalu.client.internal.application.IsShellCreator;
import com.github.nalukit.nalu.client.internal.application.ShellInstance;
import com.github.nalukit.nalu.processor.common.MockContext;
import com.github.nalukit.nalu.processor.common.event.MockEvent01;
import com.github.nalukit.nalu.processor.common.event.MockEvent02;
import java.lang.Override;
import org.gwtproject.event.shared.SimpleEventBus;

/**
 * Build with Nalu version >>%VERSION_TAG%<< at >>2022.04.20-11:54:41<<
 */
public final class MockShell01CreatorImpl extends AbstractShellCreator<MockContext> implements IsShellCreator {
  public MockShell01CreatorImpl(IsRouter router, MockContext context, SimpleEventBus eventBus) {
    super(router, context, eventBus);
  }

  @Override
  public ShellInstance create() {
    ShellInstance shellInstance = new ShellInstance();
    shellInstance.setShellClassName("com.github.nalukit.nalu.processor.shellCreator.eventhandler.eventHandlerOnAShellCreatorOk02.MockShell01");
    MockShell01 shell = new MockShell01();
    shellInstance.setShell(shell);
    shell.setContext(context);
    shell.setEventBus(eventBus);
    shell.setRouter(router);
    super.eventBus.addHandler(MockEvent01.TYPE, e -> shell.onMockEvent01(e));
    super.eventBus.addHandler(MockEvent02.TYPE, e -> shell.onMockEvent02(e));
    return shellInstance;
  }

  @Override
  public void onFinishCreating() throws RoutingInterceptionException {
  }
}
