package com.github.mvp4g.nalu.client.component;

import com.github.mvp4g.nalu.client.application.IsContext;
import elemental2.dom.HTMLElement;

public abstract class AbstractComponentController<C extends IsContext,
                                                   V extends IsComponent<?>>
  extends AbstractController<C>
  implements IsController,
             IsComponent.Controller {

  protected V component;

  public AbstractComponentController() {
    super();
  }

  @Override
  public HTMLElement asElement() {
    return this.component.asElement();
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
