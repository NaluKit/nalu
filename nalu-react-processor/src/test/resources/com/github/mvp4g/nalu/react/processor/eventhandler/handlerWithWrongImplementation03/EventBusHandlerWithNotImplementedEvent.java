package com.github.mvp4g.nalu.react.processor.eventhandler.handlerWithWrongImplementation03;

import com.github.mvp4g.mvp4g2.core.eventbus.IsEventBus;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.Event;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.EventBus;

@EventBus(shell = MockShellPresenter01.class)
public interface EventBusHandlerWithNotImplementedEvent
  extends IsEventBus {

  @Event
  void doSomething();

  @Event
  void doSomethingInHandler01();

  @Event
  void doSomethingInHandler02(String oneAttribute);

}