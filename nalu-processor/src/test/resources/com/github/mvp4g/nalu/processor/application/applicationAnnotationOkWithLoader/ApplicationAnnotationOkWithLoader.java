package com.github.mvp4g.nalu.processor.application.applicationAnnotationOkWithLoader;

import com.github.mvp4g.nalu.client.application.IsApplication;
import com.github.mvp4g.nalu.client.application.annotation.Application;
import com.github.mvp4g.nalu.processor.common.MockContext;
import com.github.mvp4g.nalu.processor.common.MockShell;

@Application(shell = MockShell.class, loader = MockApplicationLoader.class, startRoute = "/search", context = MockContext.class)
public interface ApplicationAnnotationOkWithLoader
  extends IsApplication {
}