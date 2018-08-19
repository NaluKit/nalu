package com.github.mvp4g.nalu.processor.filter.filterAnnotationOnAMethod;

import com.github.mvp4g.nalu.client.application.IsApplication;
import com.github.mvp4g.nalu.client.application.annotation.Application;
import com.github.mvp4g.nalu.client.application.annotation.Filters;
import com.github.mvp4g.nalu.processor.common.MockContext;
import com.github.mvp4g.nalu.processor.common.MockFilter;
import com.github.mvp4g.nalu.processor.common.MockShell;

@Application(shell = MockShell.class, startRoute = "/search", context = MockContext.class)
public interface FilterAnnotationOnAMethod
  extends IsApplication {

  @Filters(filterClasses = MockFilter.class)
  void oneEvent();

}
