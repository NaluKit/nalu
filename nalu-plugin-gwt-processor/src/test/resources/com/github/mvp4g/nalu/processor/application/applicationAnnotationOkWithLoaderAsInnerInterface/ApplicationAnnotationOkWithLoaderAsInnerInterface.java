package com.github.mvp4g.nalu.processor.application.applicationAnnotationOkWithLoaderAsInnerInterface;

import com.github.mvp4g.nalu.client.application.IsApplication;
import com.github.mvp4g.nalu.client.application.IsApplicationLoader;
import com.github.mvp4g.nalu.client.application.annotation.Application;
import com.github.mvp4g.nalu.processor.application.applicationAnnotationOkWithLoaderAsInnerInterface.ApplicationAnnotationOkWithLoaderAsInnerInterface.MyApplicationLoader;
import com.github.mvp4g.nalu.processor.common.MockContext;
import com.github.mvp4g.nalu.processor.common.MockShell;

@Application(shell = MockShell.class, loader = MyApplicationLoader.class, startRoute = "/search", context = MockContext.class)
public interface ApplicationAnnotationOkWithLoaderAsInnerInterface
  extends IsApplication {

  public static class MyApplicationLoader
    implements IsApplicationLoader {

    @Override
    public void load(FinishLoadCommand finishLoadCommand) {
      finishLoadCommand.finishLoading();
    }

  }
}
