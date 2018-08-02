package com.github.mvp4g.nalu.react.processor.eventhandler.presenterWithViewCreationMethodPresenter01;

import com.github.mvp4g.mvp4g2.core.eventbus.IsEventBus;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.Event;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.EventBus;

@EventBus(shell = MockShellPresenter01.class)
public interface EventBusPresenterWithViewCreationMethodPresenter01
  extends IsEventBus {

  @Event
  void doSomething();

}
