package com.github.nalukit.nalu.processor.controller.controllerAnnotationWithParameterNotOK;

import com.github.nalukit.nalu.client.application.IsApplication;
import com.github.nalukit.nalu.client.application.annotation.Application;
import com.github.nalukit.nalu.processor.common.MockContext;
import com.github.nalukit.nalu.processor.common.MockShell;

@Application(shell = MockShell.class, startRoute = "/route03", context = MockContext.class)
public interface ControllerAnnotationWithParameterNotOK
  extends IsApplication {
}
