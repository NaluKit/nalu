package com.github.mvp4g.nalu.react.processor.application.applicationAnnotationOkWithLoaderAsInnerInterface;

import com.github.mvp4g.mvp4g2.core.application.IsApplicationLoader;
import com.github.mvp4g.mvp4g2.core.internal.application.AbstractApplication;

import java.lang.Override;

public final class ApplicationAnnotationOkWithLoaderAsInnerInterfaceImpl extends AbstractApplication<MockEventBus> implements ApplicationAnnotationOkWithLoaderAsInnerInterface {
  public ApplicationAnnotationOkWithLoaderAsInnerInterfaceImpl() {
    super();
    super.eventBus = new MockEventBusImpl();
    super.historyOnStart = false;
  }

  @Override
  public IsApplicationLoader getApplicationLoader() {
    return new MyApplicationLoader();
  }
}
