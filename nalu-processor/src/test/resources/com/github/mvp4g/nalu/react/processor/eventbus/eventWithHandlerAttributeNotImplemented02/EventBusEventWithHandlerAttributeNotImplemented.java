package com.github.mvp4g.nalu.react.processor.eventbus.eventWithHandlerAttributeNotImplemented02;

import com.github.mvp4g.mvp4g2.core.eventbus.IsEventBus;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.Event;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.EventBus;

@EventBus(shell = MockShellPresenter01.class)
public interface EventBusEventWithHandlerAttributeNotImplemented
  extends IsEventBus {

  @Event(handlers = MockShellPresenter01.class)
  void doSomething(String oneAttribute);

}