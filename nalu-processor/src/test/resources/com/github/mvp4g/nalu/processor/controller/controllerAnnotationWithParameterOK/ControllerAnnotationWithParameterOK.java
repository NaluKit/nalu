package com.github.mvp4g.nalu.processor.controller.controllerAnnotationWithParameterOK;

import com.github.mvp4g.nalu.client.application.IsApplication;
import com.github.mvp4g.nalu.client.application.annotation.Application;
import com.github.mvp4g.nalu.processor.common.MockContext;
import com.github.mvp4g.nalu.processor.common.MockShell;

@Application(shell = MockShell.class, startRoute = "/route02", context = MockContext.class)
public interface ControllerAnnotationWithParameterOK
  extends IsApplication {
}
