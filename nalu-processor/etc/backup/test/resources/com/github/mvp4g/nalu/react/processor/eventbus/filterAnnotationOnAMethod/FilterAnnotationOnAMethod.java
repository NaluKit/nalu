package com.github.mvp4g.nalu.react.processor.eventbus.filterAnnotationOnAMethod;

import com.github.mvp4g.mvp4g2.core.eventbus.IsEventBus;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.EventBus;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.Filters;

@EventBus(shell = MockShellPresenter.class)
public interface FilterAnnotationOnAMethod
  extends IsEventBus {

  @Filters(filterClasses = MockFilter.class)
  void oneEvent();

}