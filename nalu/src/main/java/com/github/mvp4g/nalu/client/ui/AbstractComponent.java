package com.github.mvp4g.nalu.client.ui;

public abstract class AbstractComponent<C extends IsComponent.Controller>
  implements IsComponent<C> {

  private C controller;

  public AbstractComponent() {
  }

  @Override
  public C getController() {
    return this.controller;
  }

  @Override
  public void setController(C controller) {
    this.controller = controller;
  }
}
