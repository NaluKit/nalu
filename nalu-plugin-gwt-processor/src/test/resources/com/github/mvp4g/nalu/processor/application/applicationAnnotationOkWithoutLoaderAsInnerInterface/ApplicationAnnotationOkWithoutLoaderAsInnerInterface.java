package com.github.mvp4g.nalu.processor.application.applicationAnnotationOkWithoutLoaderAsInnerInterface;

import com.github.mvp4g.nalu.client.application.IsApplication;
import com.github.mvp4g.nalu.client.application.annotation.Application;
import com.github.mvp4g.nalu.processor.common.MockContext;
import com.github.mvp4g.nalu.processor.common.MockShell;

public class ApplicationAnnotationOkWithoutLoaderAsInnerInterface {

  MyApplication myApplication = new MyApplicationImpl();

  @Application(shell = MockShell.class, startRoute = "/search", context = MockContext.class)
  public interface MyApplication
    extends IsApplication {
  }
}

