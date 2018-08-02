package com.github.mvp4g.nalu.react.processor.mock;

import com.github.mvp4g.mvp4g2.core.internal.ui.PresenterMetaData;
import com.github.mvp4g.mvp4g2.core.ui.annotation.Presenter;
import com.github.mvp4g.mvp4g2.processor.eventhandler.PresenterOK;

public final class Com_github_mvp4g_mvp4g2_processor_mock_MockShellPresenterMetaData
  extends PresenterMetaData<PresenterOK> {
  public Com_github_mvp4g_mvp4g2_processor_mock_MockShellPresenterMetaData() {
    super("MockShellPresenter",
          HandlerMetaData.Kind.PRESENTER,
          new MockShellPresenter(),
          false,
          Presenter.VIEW_CREATION_METHOD.FRAMEWORK);
  }
}
