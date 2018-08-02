package com.github.mvp4g.nalu.react.processor.eventhandler.presenterWithViewCreationMethodPresenter05;

import com.github.mvp4g.mvp4g2.core.eventbus.IsEventBus;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.Event;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.EventBus;

@EventBus(shell = MockShellPresenter05.class)
public interface EventBusPresenterWithViewCreationMethodPresenter05
  extends IsEventBus {

  @Event
  void doSomething();

}
