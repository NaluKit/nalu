package com.github.mvp4g.nalu.client.component;

public interface IsComponent<C extends IsComponent.Controller, W> {

  W asElement();

  void attach();

  void detach();

  C getController();

  void setController(C controller);

  interface Controller {

  }
}
