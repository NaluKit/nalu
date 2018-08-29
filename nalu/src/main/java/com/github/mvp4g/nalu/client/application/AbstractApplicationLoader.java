package com.github.mvp4g.nalu.client.application;

public abstract class AbstractApplicationLoader<C extends IsContext>
  implements IsApplicationLoader<C> {

  protected C context;

  public void setContext(C context) {
    this.context = context;
  }

}
