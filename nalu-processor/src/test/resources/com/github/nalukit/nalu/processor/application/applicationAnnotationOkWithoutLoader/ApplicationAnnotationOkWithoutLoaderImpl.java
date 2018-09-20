package com.github.nalukit.nalu.processor.application.applicationAnnotationOkWithoutLoader;

import com.github.nalukit.nalu.client.application.IsApplicationLoader;
import com.github.nalukit.nalu.client.application.annotation.Debug;
import com.github.nalukit.nalu.core.client.internal.ClientLogger;
import com.github.nalukit.nalu.core.client.internal.application.AbstractApplication;
import com.github.nalukit.nalu.core.client.internal.application.DefaultLogger;
import com.github.nalukit.nalu.core.client.internal.application.NoApplicationLoader;
import com.github.nalukit.nalu.processor.common.MockContext;
import com.github.nalukit.nalu.processor.common.MockShell;

public final class ApplicationAnnotationOkWithoutLoaderImpl
  extends AbstractApplication<MockContext>
  implements ApplicationAnnotationOkWithoutLoader {
  public ApplicationAnnotationOkWithoutLoaderImpl() {
    super();
    super.context = new MockContext();
  }

  @Override
  public void loadDebugConfiguration() {
    ClientLogger.get()
                .register(false,
                          new DefaultLogger(),
                          Debug.LogLevel.SIMPLE);
  }

  @Override
  public void loadSelectors() {
    super.routerConfiguration.getSelectors()
                             .put("content",
                                  "MockShell");
    super.routerConfiguration.getSelectors()
                             .put("footer",
                                  "MockShell");
    super.routerConfiguration.getSelectors()
                             .put("header",
                                  "MockShell");
    super.routerConfiguration.getSelectors()
                             .put("navigation",
                                  "MockShell");
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
  public IsApplicationLoader<MockContext> getApplicationLoader() {
    return new NoApplicationLoader();
  }

  @Override
  public void loadComponents() {
    // shell ...
    MockShell shell = new MockShell();
    shell.setRouter(this.router);
    shell.setEventBus(this.eventBus);
    shell.setContext(this.context);
    super.shell = shell;
    ClientLogger.get()
                .logDetailed("AbstractApplicationImpl: shell created",
                             1);
  }

  @Override
  public void loadStartRoute() {
    this.startRoute = "/search";
  }

  @Override
  public void attachShell() {
    super.shell.attachShell();
  }
}
