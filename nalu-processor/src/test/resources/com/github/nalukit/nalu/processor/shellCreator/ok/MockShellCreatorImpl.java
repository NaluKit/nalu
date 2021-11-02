package com.github.nalukit.nalu.processor.common;

import com.github.nalukit.nalu.client.Router;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.AbstractShellCreator;
import com.github.nalukit.nalu.client.internal.application.IsShellCreator;
import com.github.nalukit.nalu.client.internal.application.ShellInstance;
import java.lang.Override;
import org.gwtproject.event.shared.SimpleEventBus;

/**
 * Build with Nalu version >>gwt-2.8.2-HEAD-SNAPSHOT<< at >>2020.09.16-22:14:22<<
 */
public final class MockShellCreatorImpl extends AbstractShellCreator<MockContext> implements IsShellCreator {
    public MockShellCreatorImpl(Router router, MockContext context, SimpleEventBus eventBus) {
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
