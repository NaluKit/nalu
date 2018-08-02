package com.github.mvp4g.nalu.react.processor.eventbus.eventBusAnnotationOnAMethod;

import com.github.mvp4g.mvp4g2.core.eventbus.IsEventBus;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.EventBus;
import com.github.mvp4g.nalu.react.processor.mock.MockShellPresenter;

public interface EventBusAnnotationOnAMethod
  extends IsEventBus {

  @EventBus(shell = MockShellPresenter.class)
  void oneEvent();

}