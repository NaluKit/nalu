package com.github.mvp4g.nalu.react.processor.eventhandler.presenterAnnotationUsingViewClassAsInterface;

import com.github.mvp4g.mvp4g2.core.ui.LazyReverseView;

public class MockOneView
  extends LazyReverseView<IMockOneView.Presenter>
  implements IMockOneView {

  public MockOneView() {
    super();
  }

  public void bind() {
  }

  public void createView() {
  }
}
