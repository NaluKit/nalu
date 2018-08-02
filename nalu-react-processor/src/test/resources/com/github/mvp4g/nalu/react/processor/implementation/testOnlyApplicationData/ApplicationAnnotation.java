package com.github.mvp4g.nalu.react.processor.implementation.testOnlyApplicationData;

import com.github.mvp4g.mvp4g2.core.application.IsApplication;
import com.github.mvp4g.mvp4g2.core.application.annotation.Application;
import com.github.mvp4g.nalu.react.processor.application.applicationAnnotationOkWithoutLoader.MockEventBus;

@Application(eventBus = MockEventBus.class)
public interface ApplicationAnnotation
  extends IsApplication {
}