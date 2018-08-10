package com.github.mvp4g.nalu.client.component;

import elemental2.dom.HTMLElement;

public abstract class AbstractComponent<C extends IsComponent.Controller>
  implements IsComponent<C> {

  private C controller;

  private HTMLElement element;

  public AbstractComponent() {
    this.element = render();
  }

  @Override
  public HTMLElement asElement() {
    assert element != null : "not alement set!";
    return this.element;
  }

  @Override
  public C getController() {
    return this.controller;
  }

  @Override
  public void setController(C controller) {
    this.controller = controller;
  }

  protected abstract HTMLElement render();
}
