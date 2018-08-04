package com.github.mvp4g.nalu.react.processor.event.eventTestHandlerNotInBindAndHandlersAttribute;

import com.github.mvp4g.mvp4g2.core.internal.eventbus.EventMetaData;

public final class Com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_EventTestHandlerNotInBindAndHandlersAttribute_event03
  extends EventMetaData<EventTestHandlerNotInBindAndHandlersAttribute> {
  public Com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_EventTestHandlerNotInBindAndHandlersAttribute_event03() {
    super("event03",
          "event03",
          null,
          null,
          null,
          false,
          false);
    super.addBindHandler("MockOneEventHandler");
    super.addHandler("MockShellPresenter");
  }
}
