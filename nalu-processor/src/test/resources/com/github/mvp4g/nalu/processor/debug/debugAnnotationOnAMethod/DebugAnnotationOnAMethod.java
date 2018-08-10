package com.github.mvp4g.nalu.processor.debug.debugAnnotationOnAMethod;

import com.github.mvp4g.nalu.client.application.IsApplication;
import com.github.mvp4g.nalu.client.application.annotation.Application;
import com.github.mvp4g.nalu.client.application.annotation.Debug;
import com.github.mvp4g.nalu.processor.common.MockContext;
import com.github.mvp4g.nalu.processor.common.MockShell;

@Application(shell = MockShell.class, startRoute = "/search", context = MockContext.class)
public interface DebugAnnotationOnAMethod
  extends IsApplication {

  @Debug
  void oneEvent();

}