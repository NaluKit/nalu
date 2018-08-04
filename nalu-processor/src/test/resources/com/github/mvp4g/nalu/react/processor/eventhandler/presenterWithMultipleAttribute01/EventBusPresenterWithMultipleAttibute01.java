package com.github.mvp4g.nalu.react.processor.eventhandler.presenterWithMultipleAttribute01;

import com.github.mvp4g.mvp4g2.core.eventbus.IsEventBus;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.Event;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.EventBus;

@EventBus(shell = MockShellPresenter01.class)
public interface EventBusPresenterWithMultipleAttibute01

  extends IsEventBus {

  @Event(handlers = MockShellPresenter01.class)
  void doSomething01(String oneAttribute);

  @Event(handlers = MockShellPresenter01.class)
  void doSomething02(String oneAttribute);

  @Event(handlers = MockMultiplePresenter01.class)
  void doSomethingInMultiplePresenter();

}