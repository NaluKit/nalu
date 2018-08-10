package com.github.mvp4g.nalu.processor.application.applicationAnnotationOnAMethod;

import com.github.mvp4g.nalu.client.application.annotation.Application;
import com.github.mvp4g.nalu.processor.common.MockContext;
import com.github.mvp4g.nalu.processor.common.MockShell;

public interface ApplicationAnnotationOnAMethod
  extends IsApplication {

  @Application(shell = MockShell.class, startRoute = "/search", context = MockContext.class)
  void oneMethod();

}