package com.github.mvp4g.nalu.react.processor.eventhandler.presenterOK;

import com.github.mvp4g.mvp4g2.core.internal.ui.AbstractHandlerMetaData;
import com.github.mvp4g.mvp4g2.core.internal.ui.PresenterMetaData;
import com.github.mvp4g.mvp4g2.core.ui.annotation.Presenter;

public final class Com_github_mvp4g_mvp4g2_processor_eventhandler_presenterOK_PresenterOKMetaData
  extends PresenterMetaData<PresenterOK, IMockOneView> {
  public Com_github_mvp4g_mvp4g2_processor_eventhandler_presenterOK_PresenterOKMetaData() {
    super("PresenterOK",
          AbstractHandlerMetaData.Kind.PRESENTER,
          false,
          Presenter.VIEW_CREATION_METHOD.FRAMEWORK);
    super.presenter = new PresenterOK();
    super.view = (IMockOneView) new MockOneView();
  }
}
