package com.github.mvp4g.nalu.react.processor.eventhandler.presenterWithNotImplementedEvent;

import com.github.mvp4g.mvp4g2.core.eventbus.IsEventBus;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.Event;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.EventBus;

@EventBus(shell = MockShellPresenter01.class)
public interface EventBusEventHandlerWithNotImplementedEvent
  extends IsEventBus {

  @Event
  void doSomething();

}