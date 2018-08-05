package com.github.mvp4g.nalu.client.ui;


import elemental2.dom.HTMLElement;

public interface IsComponent<C extends IsComponent.Controller> {

  C getController();

  void setController(C controller);

  HTMLElement render();

  interface Controller {

  }
}
