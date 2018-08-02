package com.github.mvp4g.nalu.react.processor.application.applicationAnnotationOkWithoutLoader;

import com.github.mvp4g.mvp4g2.core.application.IsApplication;
import com.github.mvp4g.mvp4g2.core.application.annotation.Application;

@Application(eventBus = MockEventBus.class)
public interface ApplicationAnnotationOkWithoutLoader
  extends IsApplication {
}