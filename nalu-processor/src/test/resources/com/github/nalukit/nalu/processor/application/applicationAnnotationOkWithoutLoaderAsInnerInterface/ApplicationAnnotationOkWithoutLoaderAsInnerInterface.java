package com.github.nalukit.nalu.processor.application.applicationAnnotationOkWithoutLoaderAsInnerInterface;

import com.github.nalukit.nalu.client.application.IsApplication;
import com.github.nalukit.nalu.client.application.annotation.Application;
import com.github.nalukit.nalu.processor.common.MockContext;
import com.github.nalukit.nalu.processor.common.MockShell;

public class ApplicationAnnotationOkWithoutLoaderAsInnerInterface {

  MyApplication myApplication = new MyApplicationImpl();

  @Application(shell = MockShell.class, startRoute = "/search", context = MockContext.class)
  public interface MyApplication
    extends IsApplication {
  }
}

