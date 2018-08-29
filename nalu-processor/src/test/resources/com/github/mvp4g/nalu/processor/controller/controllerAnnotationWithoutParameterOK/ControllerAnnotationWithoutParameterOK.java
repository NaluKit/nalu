package com.github.mvp4g.nalu.processor.controller.controllerAnnotationWithoutParameterOK;

import com.github.mvp4g.nalu.client.application.IsApplication;
import com.github.mvp4g.nalu.client.application.annotation.Application;
import com.github.mvp4g.nalu.processor.common.MockContext;
import com.github.mvp4g.nalu.processor.common.MockShell;

@Application(shell = MockShell.class, startRoute = "/route01", context = MockContext.class)
public interface ControllerAnnotationWithoutParameterOK
  extends IsApplication {
}
