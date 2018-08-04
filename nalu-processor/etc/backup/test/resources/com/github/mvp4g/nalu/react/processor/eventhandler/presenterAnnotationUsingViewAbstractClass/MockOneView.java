package com.github.mvp4g.nalu.react.processor.eventhandler.presenterAnnotationUsingViewAbstractClass;

import com.github.mvp4g.mvp4g2.core.ui.LazyReverseView;

public abstract class MockOneView
  extends LazyReverseView<IMockOneView.Presenter>
  implements IMockOneView {

  public void bind();

  public void createView();

}
