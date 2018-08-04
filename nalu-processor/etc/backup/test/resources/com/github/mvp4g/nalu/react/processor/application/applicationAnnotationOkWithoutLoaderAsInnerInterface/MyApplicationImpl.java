package com.github.mvp4g.nalu.react.processor.application.applicationAnnotationOkWithoutLoaderAsInnerInterface;

import com.github.mvp4g.mvp4g2.core.application.IsApplicationLoader;
import com.github.mvp4g.mvp4g2.core.internal.application.AbstractApplication;
import com.github.mvp4g.mvp4g2.core.internal.application.NoApplicationLoader;
import com.github.mvp4g.nalu.react.processor.application.applicationAnnotationOkWithoutLoaderAsInnerInterface.ApplicationAnnotationOkWithoutLoaderAsInnerInterface.MyApplication;
import java.lang.Override;

public final class MyApplicationImpl extends AbstractApplication<MockEventBus> implements MyApplication {
  public MyApplicationImpl() {
    super();
    super.eventBus = new MockEventBusImpl();
    super.historyOnStart = false;
  }

  @Override
  public IsApplicationLoader getApplicationLoader() {
    return new NoApplicationLoader();
  }
}
