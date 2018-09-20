package com.github.nalukit.nalu.processor.application.applicationAnnotationOkWithoutLoader;

import com.github.nalukit.nalu.client.application.IsApplication;
import com.github.nalukit.nalu.client.application.annotation.Application;
import com.github.nalukit.nalu.processor.common.MockContext;
import com.github.nalukit.nalu.processor.common.MockShell;

@Application(shell = MockShell.class, startRoute = "/search", context = MockContext.class)
public interface ApplicationAnnotationOkWithoutLoader
  extends IsApplication {
}
