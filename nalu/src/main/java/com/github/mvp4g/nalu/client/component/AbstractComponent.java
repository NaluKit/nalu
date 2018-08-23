package com.github.mvp4g.nalu.client.component;

public abstract class AbstractComponent<C extends IsComponent.Controller, W>
  implements IsComponent<C, W> {

  private C controller;

  private W element;

  public AbstractComponent() {
    this.element = render();
    this.bind();
  }

  protected abstract W render();

  protected void bind() {
    // if you need to bind some handlers and would like to do this in a seperate method
    // just override this method.
  }

  @Override
  public W asElement() {
    assert element != null : "not alement set!";
    return this.element;
  }

  @Override
  public void onAttach() {
    // if you need to do something in case the widget is added to the DOM tree
  }

  @Override
  public void onDetach() {
    // if you need to do something in case the widget is removed from the DOM tree
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
