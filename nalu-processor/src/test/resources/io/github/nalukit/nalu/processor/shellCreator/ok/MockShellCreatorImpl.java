package io.github.nalukit.nalu.processor.common.ui;

import io.github.nalukit.nalu.client.IsRouter;
import io.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import io.github.nalukit.nalu.client.internal.AbstractShellCreator;
import io.github.nalukit.nalu.client.internal.application.IsShellCreator;
import io.github.nalukit.nalu.client.internal.application.ShellInstance;
import io.github.nalukit.nalu.processor.common.MockContext;
import java.lang.Override;
import org.gwtproject.event.shared.SimpleEventBus;

/**
 * Build with Nalu version >>HEAD-SNAPSHOT<< at >>2022.04.16-20:35:13<<
 */
public final class MockShellCreatorImpl extends AbstractShellCreator<MockContext> implements IsShellCreator {
    public MockShellCreatorImpl(IsRouter router, MockContext context, SimpleEventBus eventBus) {
        super(router, context, eventBus);
    }

    @Override
    public ShellInstance create() {
        ShellInstance shellInstance = new ShellInstance();
        shellInstance.setShellClassName("io.github.nalukit.nalu.processor.common.ui.MockShell");
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
