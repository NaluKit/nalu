package com.github.mvp4g.nalu.react.processor.application.applicationAnnotationOkWithoutLoader;

import com.github.mvp4g.mvp4g2.core.application.IsApplicationLoader;
import com.github.mvp4g.mvp4g2.core.internal.application.AbstractApplication;
import com.github.mvp4g.mvp4g2.core.internal.application.NoApplicationLoader;
import java.lang.Override;

public final class ApplicationAnnotationOkWithoutLoaderImpl extends AbstractApplication<MockEventBus> implements ApplicationAnnotationOkWithoutLoader {
  public ApplicationAnnotationOkWithoutLoaderImpl() {
    super();
    super.eventBus = new MockEventBusImpl();
    super.historyOnStart = false;
  }

  @Override
  public IsApplicationLoader getApplicationLoader() {
    return new NoApplicationLoader();
  }
}
