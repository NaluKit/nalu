package com.github.nalukit.nalu.processor.controller.controllerAnnotationWithoutParameterOK;

import com.github.nalukit.nalu.client.application.IsApplication;
import com.github.nalukit.nalu.client.application.annotation.Application;
import com.github.nalukit.nalu.processor.common.MockContext;
import com.github.nalukit.nalu.processor.common.MockShell;

@Application(shell = MockShell.class, startRoute = "/route01", context = MockContext.class)
public interface ControllerAnnotationWithoutParameterOK
  extends IsApplication {
}
