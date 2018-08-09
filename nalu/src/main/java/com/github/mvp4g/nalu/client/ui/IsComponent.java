package com.github.mvp4g.nalu.client.ui;


import elemental2.dom.HTMLElement;

public interface IsComponent<C extends IsComponent.Controller> {

  HTMLElement asElement();

  C getController();

  void setController(C controller);

  interface Controller {

  }
}
