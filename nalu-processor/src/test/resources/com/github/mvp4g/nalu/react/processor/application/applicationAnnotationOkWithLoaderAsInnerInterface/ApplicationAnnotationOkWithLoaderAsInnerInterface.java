package com.github.mvp4g.nalu.react.processor.application.applicationAnnotationOkWithLoaderAsInnerInterface;

import com.github.mvp4g.mvp4g2.core.application.IsApplication;
import com.github.mvp4g.mvp4g2.core.application.IsApplicationLoader;
import com.github.mvp4g.mvp4g2.core.application.annotation.Application;
import com.github.mvp4g.nalu.react.processor.application.applicationAnnotationOkWithLoaderAsInnerInterface.ApplicationAnnotationOkWithLoaderAsInnerInterface.MyApplicationLoader;

@Application(eventBus = MockEventBus.class, loader = MyApplicationLoader.class)
public interface ApplicationAnnotationOkWithLoaderAsInnerInterface
  extends IsApplication {

  class MyApplicationLoader
    implements IsApplicationLoader {

    @Override
    public void load(FinishLoadCommand finishLoadCommand) {
      finishLoadCommand.finishLoading();
    }

  }
}