package com.github.mvp4g.nalu.client.component;

import com.github.mvp4g.nalu.client.application.IsContext;

public abstract class AbstractComponentController<C extends IsContext, V extends IsComponent<?, W>, W>
  extends AbstractController<C>
  implements IsController<W>,
             IsComponent.Controller {

  protected V component;

  public AbstractComponentController() {
    super();
  }

  @Override
  public W asElement() {
    return this.component.asElement();
  }

  @Override
  public final void onAttach() {
    component.onAttach();
  }

  @Override
  public final void onDetach() {
    component.onDetach();
  }

  @Override
  public String mayStop() {
    return null;
  }

  @Override
  public void start() {
  }

  @Override
  public void stop() {
  }

  public void setComponent(V component) {
    this.component = component;
  }
}
