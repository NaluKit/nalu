package com.github.mvp4g.nalu.react.processor.application.applicationAnnotationOkWithLoader;

import com.github.mvp4g.mvp4g2.core.application.IsApplication;
import com.github.mvp4g.mvp4g2.core.application.annotation.Application;

@Application(eventBus = MockEventBus.class, loader = MockApplicationLoader.class)
public interface ApplicationAnnotationOkWithLoader
  extends IsApplication {
}