package com.github.nalukit.nalu.processor.common;

import com.github.nalukit.nalu.client.IsRouter;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.AbstractShellCreator;
import com.github.nalukit.nalu.client.internal.application.IsShellCreator;
import com.github.nalukit.nalu.client.internal.application.ShellInstance;
import java.lang.Override;
import org.gwtproject.event.shared.SimpleEventBus;

/**
 * Build with Nalu version >>%VERSION_TAG%<< at >>2020.09.16-22:14:22<<
 */
public final class MockShellCreatorImpl extends AbstractShellCreator<MockContext> implements IsShellCreator {
    public MockShellCreatorImpl(IsRouter router, MockContext context, SimpleEventBus eventBus) {
        super(router, context, eventBus);
    }
    
    @Override
    public ShellInstance create() {
        ShellInstance shellInstance = new ShellInstance();
        shellInstance.setShellClassName("com.github.nalukit.nalu.processor.common.MockShell");
        MockShell shell = new MockShell();
        shellInstance.setShell(shell);
        shell.setContext(context);
        shell.setEventBus(eventBus);
        shell.setRouter(router);
        return shellInstance;
    }
    
    @Override
    public void onFinishCreating() throws RoutingInterceptionException {
    }
}
