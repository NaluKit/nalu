package com.github.mvp4g.nalu.react.processor.eventhandler.eventhandlerWithHanderlsAttributeAndEventHandlerAnnotation;

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

public final class EventBusEventhandlerWithHanderlsAttributeAndEventHandlerAnnotationImpl extends AbstractEventBus<EventBusEventhandlerWithHanderlsAttributeAndEventHandlerAnnotation> implements EventBusEventhandlerWithHanderlsAttributeAndEventHandlerAnnotation {
  public EventBusEventhandlerWithHanderlsAttributeAndEventHandlerAnnotationImpl() {
    super("MockShellPresenter01");
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
    // handle Com_github_mvp4g_mvp4g2_processor_eventhandler_eventhandlerWithHanderlsAttributeAndEventHandlerAnnotation_EventBusEventhandlerWithHanderlsAttributeAndEventHandlerAnnotation_doSomething01
    //
    super.putEventMetaData("doSomething01_pPp_java_lang_String", new Com_github_mvp4g_mvp4g2_processor_eventhandler_eventhandlerWithHanderlsAttributeAndEventHandlerAnnotation_EventBusEventhandlerWithHanderlsAttributeAndEventHandlerAnnotation_doSomething01());
  }

  @Override
  public final void doSomething01(final String oneAttribute) {
    int startLogDepth = AbstractEventBus.logDepth;
    try {
      execDoSomething01(oneAttribute);
    } finally {
      AbstractEventBus.logDepth = startLogDepth;
    }
  }

  public final void execDoSomething01(final String oneAttribute) {
    super.logEvent(++AbstractEventBus.logDepth, "doSomething01", oneAttribute);
    ++AbstractEventBus.logDepth;
    if (!super.filterEvent("doSomething01", oneAttribute)) {
      return;
    }
    EventMetaData<EventBusEventhandlerWithHanderlsAttributeAndEventHandlerAnnotation> eventMetaData = super.getEventMetaData("doSomething01_pPp_java_lang_String");
    super.createAndBindView(eventMetaData);
    super.bind(eventMetaData, oneAttribute);
    super.activate(eventMetaData);
    super.deactivate(eventMetaData);
    List<HandlerMetaData<?>> handlers = null;
    List<PresenterMetaData<?, ?>> presenters = null;
    List<String> listOfExecutedHandlers = new ArrayList<>();
    // handling: MockShellPresenter01
    presenters = this.presenterMetaDataMap.get("MockShellPresenter01");
    super.executePresenter(eventMetaData, presenters, null, new AbstractEventBus.ExecPresenter() {
      @Override
      public boolean execPass(EventMetaData<?> eventMetaData, PresenterMetaData<?, ?> metaData) {
        return metaData.getPresenter().pass(eventMetaData.getEventName(), oneAttribute);
      }

      @Override
      public void execEventHandlingMethod(PresenterMetaData<?, ?> metaData) {
        ((MockShellPresenter01) metaData.getPresenter()).onDoSomething01(oneAttribute);
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
    // handle MockShellPresenter01 (Presenter)
    //
    Com_github_mvp4g_mvp4g2_processor_eventhandler_eventhandlerWithHanderlsAttributeAndEventHandlerAnnotation_MockShellPresenter01MetaData com_github_mvp4g_mvp4g2_processor_eventhandler_eventhandlerWithHanderlsAttributeAndEventHandlerAnnotation_MockShellPresenter01MetaData = new Com_github_mvp4g_mvp4g2_processor_eventhandler_eventhandlerWithHanderlsAttributeAndEventHandlerAnnotation_MockShellPresenter01MetaData();
    super.putPresenterMetaData("MockShellPresenter01", com_github_mvp4g_mvp4g2_processor_eventhandler_eventhandlerWithHanderlsAttributeAndEventHandlerAnnotation_MockShellPresenter01MetaData);
    com_github_mvp4g_mvp4g2_processor_eventhandler_eventhandlerWithHanderlsAttributeAndEventHandlerAnnotation_MockShellPresenter01MetaData.getPresenter().setEventBus(this);
    com_github_mvp4g_mvp4g2_processor_eventhandler_eventhandlerWithHanderlsAttributeAndEventHandlerAnnotation_MockShellPresenter01MetaData.getPresenter().setView(com_github_mvp4g_mvp4g2_processor_eventhandler_eventhandlerWithHanderlsAttributeAndEventHandlerAnnotation_MockShellPresenter01MetaData.getView());
    com_github_mvp4g_mvp4g2_processor_eventhandler_eventhandlerWithHanderlsAttributeAndEventHandlerAnnotation_MockShellPresenter01MetaData.getView().setPresenter(com_github_mvp4g_mvp4g2_processor_eventhandler_eventhandlerWithHanderlsAttributeAndEventHandlerAnnotation_MockShellPresenter01MetaData.getPresenter());
    //
    // ===> add the handler to the handler list of the EventMetaData-class
  }
}