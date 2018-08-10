package com.github.mvp4g.nalu.client.component;


import elemental2.dom.HTMLElement;

public interface IsComponent<C extends IsComponent.Controller> {

  HTMLElement asElement();

  C getController();

  void setController(C controller);

  interface Controller {

  }
}
