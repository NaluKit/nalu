package com.github.mvp4g.nalu.react.processor.event.startEventTestEventBusWithOneStartAnnotation;

import com.github.mvp4g.mvp4g2.core.internal.eventbus.EventMetaData;

public final class Com_github_mvp4g_mvp4g2_processor_event_startEventTestEventBusWithOneStartAnnotation_StartEventTestEventBusWithOneStartAnnotation_start
  extends EventMetaData<StartEventTestEventBusWithOneStartAnnotation> {
  public Com_github_mvp4g_mvp4g2_processor_event_startEventTestEventBusWithOneStartAnnotation_StartEventTestEventBusWithOneStartAnnotation_start() {
    super("start",
          "start",
          null,
          null,
          null,
          false,
          false);
    super.addHandler("MockShellPresenter");
  }
}
