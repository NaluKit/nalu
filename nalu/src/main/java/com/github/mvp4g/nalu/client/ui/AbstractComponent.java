package com.github.mvp4g.nalu.client.ui;

public abstract class AbstractComponent
  extends AbstractNaluComponent
  implements IsNaluReactComponent {

  public AbstractComponent() {
  }

  @Override
  public String mayStop() {
    return null;
  }

  @Override
  public void stop() {
  }
}
