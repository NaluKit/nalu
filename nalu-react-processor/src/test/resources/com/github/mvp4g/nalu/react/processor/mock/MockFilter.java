package com.github.mvp4g.nalu.react.processor.mock;

import com.github.mvp4g.mvp4g2.core.eventbus.IsEventFilter;

public class MockFilter
  extends IsEventFilter<MockEventBus> {

  @Override
  public boolean filterEvent(MockEventBus eventBus,
                             String eventName,
                             Object... params) {
    return true;
  }

}
