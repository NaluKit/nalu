package com.github.mvp4g.nalu.react.processor.event.startEventTestEventBusWithOneStartAnnotation;

import com.github.mvp4g.mvp4g2.core.internal.ui.AbstractHandlerMetaData;
import com.github.mvp4g.mvp4g2.core.internal.ui.PresenterMetaData;
import com.github.mvp4g.mvp4g2.core.ui.annotation.Presenter;

public final class Com_github_mvp4g_mvp4g2_processor_event_startEventTestEventBusWithOneStartAnnotation_MockShellPresenterMetaData
  extends PresenterMetaData<MockShellPresenter, IMockShellView> {
  public Com_github_mvp4g_mvp4g2_processor_event_startEventTestEventBusWithOneStartAnnotation_MockShellPresenterMetaData() {
    super("MockShellPresenter",
          AbstractHandlerMetaData.Kind.PRESENTER,
          false,
          Presenter.VIEW_CREATION_METHOD.FRAMEWORK);
    super.presenter = new MockShellPresenter();
    super.view = (IMockShellView) new MockShellView();
  }
}
