package com.github.mvp4g.nalu.processor.debug.debugAnnotationOnAClass;

import com.github.mvp4g.nalu.client.application.IsApplication;
import com.github.mvp4g.nalu.client.application.annotation.Application;
import com.github.mvp4g.nalu.client.application.annotation.Debug;
import com.github.mvp4g.nalu.processor.common.MockContext;
import com.github.mvp4g.nalu.processor.common.MockShell;

@Application(shell = MockShell.class, startRoute = "/search", context = MockContext.class)
@Debug(logLevel = Debug.LogLevel.DETAILED)
public class DebugAnnotationOnAClass
  implements IsApplication {

  public void run() {
  }
}
