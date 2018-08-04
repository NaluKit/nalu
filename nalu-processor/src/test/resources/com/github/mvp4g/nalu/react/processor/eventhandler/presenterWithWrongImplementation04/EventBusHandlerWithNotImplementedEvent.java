package com.github.mvp4g.nalu.react.processor.eventhandler.presenterWithWrongImplementation04;

import com.github.mvp4g.mvp4g2.core.eventbus.IsEventBus;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.Event;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.EventBus;

@EventBus(shell = MockShellPresenter01.class)
public interface EventBusHandlerWithNotImplementedEvent
  extends IsEventBus {

  @Event(handlers = MockShellPresenter01.class)
  void doSomething01(String oneAttribute);

  @Event(handlers = MockShellPresenter01.class)
  void doSomething02(String oneAttribute);

}