package com.github.mvp4g.nalu.react.processor.eventhandler.presenterWithViewCreationMethodPresenter02;

import com.github.mvp4g.mvp4g2.core.eventbus.IsEventBus;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.Event;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.EventBus;

@EventBus(shell = MockShellPresenter02.class)
public interface EventBusPresenterWithViewCreationMethodPresenter02
  extends IsEventBus {

  @Event
  void doSomething(String oneAttribute);

}
