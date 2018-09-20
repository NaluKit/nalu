package com.github.nalukit.nalu.processor.filter.filterAnnotationOnAMethod;

import com.github.nalukit.nalu.client.application.IsApplication;
import com.github.nalukit.nalu.client.application.annotation.Application;
import com.github.nalukit.nalu.client.application.annotation.Filters;
import com.github.nalukit.nalu.processor.common.MockContext;
import com.github.nalukit.nalu.processor.common.MockFilter;
import com.github.nalukit.nalu.processor.common.MockShell;

@Application(shell = MockShell.class, startRoute = "/search", context = MockContext.class)
public interface FilterAnnotationOnAMethod
  extends IsApplication {

  @Filters(filterClasses = MockFilter.class)
  void oneEvent();

}
