package com.github.nalukit.nalu.processor.application.applicationAnnotationOnAMethod;

import com.github.nalukit.nalu.client.application.annotation.Application;
import com.github.nalukit.nalu.processor.common.MockContext;
import com.github.nalukit.nalu.processor.common.MockShell;

public interface ApplicationAnnotationOnAMethod
  extends IsApplication {

  @Application(shell = MockShell.class, startRoute = "/search", context = MockContext.class)
  void oneMethod();

}
