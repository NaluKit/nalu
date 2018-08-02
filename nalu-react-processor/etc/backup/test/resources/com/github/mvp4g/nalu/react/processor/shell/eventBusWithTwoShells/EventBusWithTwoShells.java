package com.github.mvp4g.nalu.react.processor.shell.eventBusWithTwoShells;

import com.github.mvp4g.mvp4g2.core.eventbus.IsEventBus;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.Event;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.EventBus;

@EventBus(shell = MockShellPresenter01.class)
public interface EventBusWithTwoShells
  extends IsEventBus {

  @Event
  void doSomethingInPresenter02();

}