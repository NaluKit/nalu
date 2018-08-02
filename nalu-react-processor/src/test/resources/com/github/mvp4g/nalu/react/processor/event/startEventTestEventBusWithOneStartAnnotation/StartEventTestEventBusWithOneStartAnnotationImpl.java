package com.github.mvp4g.nalu.react.processor.event.startEventTestEventBusWithOneStartAnnotation;

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

public final class StartEventTestEventBusWithOneStartAnnotationImpl extends AbstractEventBus<StartEventTestEventBusWithOneStartAnnotation> implements StartEventTestEventBusWithOneStartAnnotation {
  public StartEventTestEventBusWithOneStartAnnotationImpl() {
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
    // handle Com_github_mvp4g_mvp4g2_processor_event_startEventTestEventBusWithOneStartAnnotation_StartEventTestEventBusWithOneStartAnnotation_start
    //
    super.putEventMetaData("start", new Com_github_mvp4g_mvp4g2_processor_event_startEventTestEventBusWithOneStartAnnotation_StartEventTestEventBusWithOneStartAnnotation_start());
  }

  @Override
  public final void start() {
    int startLogDepth = AbstractEventBus.logDepth;
    try {
      execStart();
    } finally {
      AbstractEventBus.logDepth = startLogDepth;
    }
  }

  public final void execStart() {
    super.logEvent(++AbstractEventBus.logDepth, "start");
    ++AbstractEventBus.logDepth;
    if (!super.filterEvent("start")) {
      return;
    }
    EventMetaData<StartEventTestEventBusWithOneStartAnnotation> eventMetaData = super.getEventMetaData("start");
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
        ((MockShellPresenter) metaData.getPresenter()).onStart();
      }
    }, false);
  }

  @Override
  public final void fireStartEvent() {
    this.start();
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
    Com_github_mvp4g_mvp4g2_processor_event_startEventTestEventBusWithOneStartAnnotation_MockShellPresenterMetaData com_github_mvp4g_mvp4g2_processor_event_startEventTestEventBusWithOneStartAnnotation_MockShellPresenterMetaData = new Com_github_mvp4g_mvp4g2_processor_event_startEventTestEventBusWithOneStartAnnotation_MockShellPresenterMetaData();
    super.putPresenterMetaData("MockShellPresenter", com_github_mvp4g_mvp4g2_processor_event_startEventTestEventBusWithOneStartAnnotation_MockShellPresenterMetaData);
    com_github_mvp4g_mvp4g2_processor_event_startEventTestEventBusWithOneStartAnnotation_MockShellPresenterMetaData.getPresenter().setEventBus(this);
    com_github_mvp4g_mvp4g2_processor_event_startEventTestEventBusWithOneStartAnnotation_MockShellPresenterMetaData.getPresenter().setView(com_github_mvp4g_mvp4g2_processor_event_startEventTestEventBusWithOneStartAnnotation_MockShellPresenterMetaData.getView());
    com_github_mvp4g_mvp4g2_processor_event_startEventTestEventBusWithOneStartAnnotation_MockShellPresenterMetaData.getView().setPresenter(com_github_mvp4g_mvp4g2_processor_event_startEventTestEventBusWithOneStartAnnotation_MockShellPresenterMetaData.getPresenter());
    //
    // ===> add the handler to the handler list of the EventMetaData-class
  }
}
