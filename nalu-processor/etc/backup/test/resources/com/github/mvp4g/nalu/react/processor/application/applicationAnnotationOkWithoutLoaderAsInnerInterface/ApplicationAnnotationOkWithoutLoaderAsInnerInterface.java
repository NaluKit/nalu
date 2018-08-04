package com.github.mvp4g.nalu.react.processor.application.applicationAnnotationOkWithoutLoaderAsInnerInterface;

import com.github.mvp4g.mvp4g2.core.application.IsApplication;
import com.github.mvp4g.mvp4g2.core.application.annotation.Application;

public class ApplicationAnnotationOkWithoutLoaderAsInnerInterface {

  MyApplication myApplication = new MyApplicationImpl();

  @Application(eventBus = MockEventBus.class)
  public interface MyApplication
    extends IsApplication {
  }
}

