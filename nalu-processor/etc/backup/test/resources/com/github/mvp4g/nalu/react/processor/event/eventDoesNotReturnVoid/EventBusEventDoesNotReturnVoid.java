package com.github.mvp4g.nalu.react.processor.event.eventDoesNotReturnVoid;

import com.github.mvp4g.mvp4g2.core.eventbus.IsEventBus;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.Event;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.EventBus;

@EventBus(shell = MockShellPresenter01.class)
public interface EventBusEventDoesNotReturnVoid
  extends IsEventBus {

  @Event(handlers = MockShellPresenter01.class)
  String doSomething(String oneAttribute);

}