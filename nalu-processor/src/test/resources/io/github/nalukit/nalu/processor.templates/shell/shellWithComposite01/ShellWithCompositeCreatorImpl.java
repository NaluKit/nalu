package io.github.nalukit.nalu.processor.shell.shellWithComposite01;

import io.github.nalukit.nalu.client.IsRouter;
import io.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import io.github.nalukit.nalu.client.internal.AbstractShellCreator;
import io.github.nalukit.nalu.client.internal.application.IsShellCreator;
import io.github.nalukit.nalu.client.internal.application.ShellInstance;
import io.github.nalukit.nalu.processor.common.MockContext;
import java.lang.Override;
import org.gwtproject.event.shared.SimpleEventBus;

/**
 * Build with Nalu version >>%VERSION_TAG%<< at >>2022.10.11-07:35:31<<
 */
public final class ShellWithCompositeCreatorImpl extends AbstractShellCreator<MockContext> implements IsShellCreator {
  public ShellWithCompositeCreatorImpl(IsRouter router, MockContext context,
                                       SimpleEventBus eventBus) {
    super(router, context, eventBus);
  }

  @Override
  public ShellInstance create() {
    ShellInstance shellInstance = new ShellInstance();
    shellInstance.setShellClassName("io.github.nalukit.nalu.processor.shell.shellWithComposite01.ShellWithComposite");
    ShellWithComposite shell = new ShellWithComposite();
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
