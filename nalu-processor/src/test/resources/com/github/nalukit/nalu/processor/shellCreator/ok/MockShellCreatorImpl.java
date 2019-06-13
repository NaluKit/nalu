package com.github.nalukit.nalu.processor.common;

import com.github.nalukit.nalu.client.Router;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.AbstractShellCreator;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.application.IsShellCreator;
import com.github.nalukit.nalu.client.internal.application.ShellInstance;
import java.lang.Object;
import java.lang.Override;
import java.lang.StringBuilder;
import org.gwtproject.event.shared.SimpleEventBus;

public final class MockShellCreatorImpl extends AbstractShellCreator<MockContext> implements IsShellCreator {
    public MockShellCreatorImpl(Router router, MockContext context, SimpleEventBus eventBus) {
        super(router, context, eventBus);
    }

    @Override
    public ShellInstance create() {
        StringBuilder sb01 = new StringBuilder();
        ShellInstance shellInstance = new ShellInstance();
        shellInstance.setShellClassName("com.github.nalukit.nalu.processor.common.MockShell");
        sb01.append("shell >>com.github.nalukit.nalu.processor.common.MockShell<< --> will be created");
        ClientLogger.get().logSimple(sb01.toString(), 1);
        MockShell shell = new MockShell();
        shellInstance.setShell(shell);
        shell.setContext(context);
        shell.setEventBus(eventBus);
        shell.setRouter(router);
        sb01 = new StringBuilder();
        sb01.append("shell >>com.github.nalukit.nalu.processor.common.MockShell<< --> created and data injected");
        ClientLogger.get().logDetailed(sb01.toString(), 2);
        sb01 = new StringBuilder();
        return shellInstance;
    }

    @Override
    public void onFinishCreating(Object object) throws RoutingInterceptionException {
        MockShell shell = (MockShell) object;
    }
}
