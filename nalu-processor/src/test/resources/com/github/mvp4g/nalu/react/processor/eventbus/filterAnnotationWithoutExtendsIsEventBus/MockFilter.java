package com.github.mvp4g.nalu.react.processor.eventbus.filterAnnotationWithoutExtendsIsEventBus;

import com.github.mvp4g.mvp4g2.core.eventbus.IsEventFilter;

public class MockFilter
  implements IsEventFilter<FilterAnnotationWithoutExtendsIsEventBus> {

  @Override
  public boolean filterEvent(FilterAnnotationWithoutExtendsIsEventBus eventBus,
                             String eventName,
                             Object... params) {
    return true;
  }

}
