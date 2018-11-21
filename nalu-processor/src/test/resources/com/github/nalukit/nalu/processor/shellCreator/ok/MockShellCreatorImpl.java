package com.github.nalukit.nalu.processor.common;

import com.github.nalukit.nalu.client.Router;
import com.github.nalukit.nalu.client.internal.AbstractShellCreator;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.application.IsShellCreator;
import com.github.nalukit.nalu.client.internal.application.ShellInstance;
import java.lang.StringBuilder;
import org.gwtproject.event.shared.SimpleEventBus;

public final class MockShellCreatorImpl extends AbstractShellCreator<MockContext> implements IsShellCreator {
    public MockShellCreatorImpl(Router router, MockContext context, SimpleEventBus eventBus) {
        super(router, context, eventBus);
    }

    public ShellInstance create() {
        StringBuilder sb01 = new StringBuilder();
        ShellInstance shellInstance = new ShellInstance();
        shellInstance.setShellClassName("com.github.nalukit.nalu.processor.common.MockShell");
        sb01.append("compositeModel >>com.github.nalukit.nalu.processor.common.MockShell<< --> will be created");
        ClientLogger.get().logSimple(sb01.toString(), 1);
        MockShell compositeModel = new MockShell();
        compositeModel.setContext(context);
        compositeModel.setEventBus(eventBus);
        compositeModel.setRouter(router);
        sb01 = new StringBuilder();
        sb01.append("compositeModel >>com.github.nalukit.nalu.processor.common.MockShell<< --> created and data injected");
        ClientLogger.get().logDetailed(sb01.toString(), 2);
        sb01 = new StringBuilder();
        sb01.append("compositeModel >>com.github.nalukit.nalu.processor.common.MockShell<< --> call bind()-method");
        ClientLogger.get().logDetailed(sb01.toString(), 2);
        compositeModel.bind();
        sb01 = new StringBuilder();
        sb01.append("compositeModel >>com.github.nalukit.nalu.processor.common.MockShell<< --> called bind()-method");
        ClientLogger.get().logDetailed(sb01.toString(), 2);
        shellInstance.setShell(compositeModel);
        return shellInstance;
    }
}