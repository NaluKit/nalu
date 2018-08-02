package com.github.mvp4g.nalu.react.processor.event.eventTestHandlerNotInBindAndHandlersAttribute;

import com.github.mvp4g.mvp4g2.core.eventbus.PresenterRegistration;
import com.github.mvp4g.mvp4g2.core.Mvp4g2RuntimeException;
import com.github.mvp4g.mvp4g2.core.internal.eventbus.AbstractEventBus;
import com.github.mvp4g.mvp4g2.core.internal.eventbus.EventMetaData;
import com.github.mvp4g.mvp4g2.core.internal.ui.HandlerMetaData;
import com.github.mvp4g.mvp4g2.core.internal.ui.PresenterMetaData;
import com.github.mvp4g.mvp4g2.core.ui.IsPresenter;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public final class EventTestHandlerNotInBindAndHandlersAttributeImpl extends AbstractEventBus<EventTestHandlerNotInBindAndHandlersAttribute> implements EventTestHandlerNotInBindAndHandlersAttribute {
  public EventTestHandlerNotInBindAndHandlersAttributeImpl() {
    super("MockShellPresenter");
  }

  @Override
  public void loadDebugConfiguration() {
    super.setDebugEnable(false);
  }

  @Override
  public void loadFilterConfiguration() {
    super.setFiltersEnable(false);
  }

  @Override
  protected void loadEventMetaData() {
    //
    // ----------------------------------------------------------------------
    //
    // handle Com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_EventTestHandlerNotInBindAndHandlersAttribute_event02
    //
    super.putEventMetaData("event02", new Com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_EventTestHandlerNotInBindAndHandlersAttribute_event02());
    //
    // ----------------------------------------------------------------------
    //
    // handle Com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_EventTestHandlerNotInBindAndHandlersAttribute_event03
    //
    super.putEventMetaData("event03", new Com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_EventTestHandlerNotInBindAndHandlersAttribute_event03());
    //
    // ----------------------------------------------------------------------
    //
    // handle Com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_EventTestHandlerNotInBindAndHandlersAttribute_event01
    //
    super.putEventMetaData("event01", new Com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_EventTestHandlerNotInBindAndHandlersAttribute_event01());
  }

  @Override
  public final void event02() {
    int startLogDepth = AbstractEventBus.logDepth;
    try {
      execEvent02();
    } finally {
      AbstractEventBus.logDepth = startLogDepth;
    }
  }

  public final void execEvent02() {
    super.logEvent(++AbstractEventBus.logDepth, "event02");
    ++AbstractEventBus.logDepth;
    if (!super.filterEvent("event02")) {
      return;
    }
    EventMetaData<EventTestHandlerNotInBindAndHandlersAttribute> eventMetaData = super.getEventMetaData("event02");
    super.createAndBindView(eventMetaData);
    super.bind(eventMetaData);
    super.activate(eventMetaData);
    super.deactivate(eventMetaData);
    List<HandlerMetaData<?>> handlers = null;
    List<PresenterMetaData<?, ?>> presenters = null;
    List<String> listOfExecutedHandlers = new ArrayList<>();
    // handling: MockShellPresenter
    presenters = this.presenterMetaDataMap.get("MockShellPresenter");
    super.executePresenter(eventMetaData, presenters, null, new AbstractEventBus.ExecPresenter() {
      @Override
      public boolean execPass(EventMetaData<?> eventMetaData, PresenterMetaData<?, ?> metaData) {
        return metaData.getPresenter().pass(eventMetaData.getEventName());
      }

      @Override
      public void execEventHandlingMethod(PresenterMetaData<?, ?> metaData) {
        ((MockShellPresenter) metaData.getPresenter()).onEvent02();
      }
    }, false);
  }

  @Override
  public final void event03() {
    int startLogDepth = AbstractEventBus.logDepth;
    try {
      execEvent03();
    } finally {
      AbstractEventBus.logDepth = startLogDepth;
    }
  }

  public final void execEvent03() {
    super.logEvent(++AbstractEventBus.logDepth, "event03");
    ++AbstractEventBus.logDepth;
    if (!super.filterEvent("event03")) {
      return;
    }
    EventMetaData<EventTestHandlerNotInBindAndHandlersAttribute> eventMetaData = super.getEventMetaData("event03");
    super.createAndBindView(eventMetaData);
    super.bind(eventMetaData);
    super.activate(eventMetaData);
    super.deactivate(eventMetaData);
    List<HandlerMetaData<?>> handlers = null;
    List<PresenterMetaData<?, ?>> presenters = null;
    List<String> listOfExecutedHandlers = new ArrayList<>();
    // handling: MockShellPresenter
    presenters = this.presenterMetaDataMap.get("MockShellPresenter");
    super.executePresenter(eventMetaData, presenters, null, new AbstractEventBus.ExecPresenter() {
      @Override
      public boolean execPass(EventMetaData<?> eventMetaData, PresenterMetaData<?, ?> metaData) {
        return metaData.getPresenter().pass(eventMetaData.getEventName());
      }

      @Override
      public void execEventHandlingMethod(PresenterMetaData<?, ?> metaData) {
        ((MockShellPresenter) metaData.getPresenter()).onEvent03();
      }
    }, false);
  }

  @Override
  public final void event01() {
    int startLogDepth = AbstractEventBus.logDepth;
    try {
      execEvent01();
    } finally {
      AbstractEventBus.logDepth = startLogDepth;
    }
  }

  public final void execEvent01() {
    super.logEvent(++AbstractEventBus.logDepth, "event01");
    ++AbstractEventBus.logDepth;
    if (!super.filterEvent("event01")) {
      return;
    }
    EventMetaData<EventTestHandlerNotInBindAndHandlersAttribute> eventMetaData = super.getEventMetaData("event01");
    super.createAndBindView(eventMetaData);
    super.bind(eventMetaData);
    super.activate(eventMetaData);
    super.deactivate(eventMetaData);
    List<HandlerMetaData<?>> handlers = null;
    List<PresenterMetaData<?, ?>> presenters = null;
    List<String> listOfExecutedHandlers = new ArrayList<>();
    // handling: MockOneEventHandler
    handlers = this.handlerMetaDataMap.get("MockOneEventHandler");
    super.executeHandler(eventMetaData, handlers, null, new AbstractEventBus.ExecHandler() {
      @Override
      public boolean execPass(EventMetaData<?> eventMetaData, HandlerMetaData<?> metaData) {
        return metaData.getHandler().pass(eventMetaData.getEventName());
      }

      @Override
      public void execEventHandlingMethod(HandlerMetaData<?> metaData) {
        ((MockOneEventHandler) metaData.getHandler()).onEvent01();
      }
    }, false);
  }

  @Override
  public final void fireStartEvent() {
  }

  @Override
  public final void fireInitHistoryEvent() {
    assert false : "no @InitHistory-event defined";
  }

  @Override
  public final void fireNotFoundHistoryEvent() {
    assert false : "no @NotFoundHistory-event defined";
  }

  @Override
  public PresenterRegistration addHandler(IsPresenter<?, ?> presenter, boolean bind) throws
                                                                                     Mvp4g2RuntimeException {
    throw new Mvp4g2RuntimeException(presenter.getClass().getCanonicalName() + ": can not be used with the addHandler()-method, because it is not defined as multiple presenter!");
  }

  @Override
  protected void loadEventHandlerMetaData() {
    //
    // ===>
    // handle MockShellPresenter (Presenter)
    //
    Com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_MockShellPresenterMetaData com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_MockShellPresenterMetaData = new Com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_MockShellPresenterMetaData();
    super.putPresenterMetaData("MockShellPresenter", com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_MockShellPresenterMetaData);
    com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_MockShellPresenterMetaData.getPresenter().setEventBus(this);
    com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_MockShellPresenterMetaData.getPresenter().setView(com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_MockShellPresenterMetaData.getView());
    com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_MockShellPresenterMetaData.getView().setPresenter(com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_MockShellPresenterMetaData.getPresenter());
    //
    // ===>
    // handle MockOneEventHandler (EventHandler)
    //
    Com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_MockOneEventHandlerMetaData com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_MockOneEventHandlerMetaData = new Com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_MockOneEventHandlerMetaData();
    super.putHandlerMetaData("MockOneEventHandler", com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_MockOneEventHandlerMetaData);
    com_github_mvp4g_mvp4g2_processor_event_eventTestHandlerNotInBindAndHandlersAttribute_MockOneEventHandlerMetaData.getHandler().setEventBus(this);
    //
    // ===> add the handler to the handler list of the EventMetaData-class
    super.getEventMetaData("event01").addHandler("MockOneEventHandler");
  }
}