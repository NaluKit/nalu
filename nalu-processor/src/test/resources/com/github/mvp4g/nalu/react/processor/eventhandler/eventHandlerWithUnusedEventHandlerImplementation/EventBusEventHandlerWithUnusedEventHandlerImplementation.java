package com.github.mvp4g.nalu.react.processor.eventhandler.eventHandlerWithUnusedEventHandlerImplementation;

import com.github.mvp4g.mvp4g2.core.eventbus.IsEventBus;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.EventBus;

@EventBus(shell = MockShellPresenter01.class)
public interface EventBusEventHandlerWithUnusedEventHandlerImplementation
  extends IsEventBus {

}