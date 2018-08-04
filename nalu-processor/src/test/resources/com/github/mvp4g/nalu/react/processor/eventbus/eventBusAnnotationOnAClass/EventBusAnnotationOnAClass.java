package com.github.mvp4g.nalu.react.processor.eventbus.eventBusAnnotationOnAClass;

import com.github.mvp4g.mvp4g2.core.eventbus.IsEventBus;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.Debug;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.EventBus;
import com.github.mvp4g.nalu.react.processor.mock.MockShellPresenter;

@EventBus(shell = MockShellPresenter.class)
public class EventBusAnnotationOnAClass
  implements IsEventBus {

  @Debug
  void event();

}