package com.github.nalukit.nalu.processor.application.applicationAnnotationOkWithoutLoader;

import com.github.nalukit.nalu.client.application.IsApplicationLoader;
import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.internal.application.AbstractApplication;
import com.github.nalukit.nalu.processor.common.MockContext;
import com.github.nalukit.nalu.processor.common.MockShell;
import java.lang.Override;

public final class ApplicationAnnotationOkWithoutLoaderImpl extends AbstractApplication<MockContext> implements ApplicationAnnotationOkWithoutLoader {
  public ApplicationAnnotationOkWithoutLoaderImpl() {
    super();
    super.context = new com.github.nalukit.nalu.processor.common.MockContext();
  }

  @Override
  public void loadDebugConfiguration() {
  }

  @Override
  public void loadCompositeController() {
  }

  @Override
  public void loadComponents() {
    // shell ...
    MockShell shell = new MockShell();
    shell.setRouter(this.router);
    shell.setEventBus(this.eventBus);
    shell.setContext(this.context);
    super.shell = shell;
    super.router.setShell(this.shell);
    shell.bind();
    ClientLogger.get().logDetailed("AbstractApplicationImpl: shell created", 1);
  }

  @Override
  public void loadRoutes() {
  }

  @Override
  public void loadFilters() {
  }

  @Override
  public void loadHandlers() {
  }

  @Override
  public void loadCompositeReferences() {
  }

  @Override
  public IsApplicationLoader<MockContext> getApplicationLoader() {
    return null;
  }

  @Override
  public void loadDefaultRoutes() {
    this.startRoute = "/search";
    this.errorRoute = "WhenShallWeThreeMeetAgainInThunderLightningOrInRain";
  }
}
