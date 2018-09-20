package com.github.nalukit.nalu.client.component;

public interface IsComponent<C extends IsComponent.Controller, W> {

  W asElement();

  void render();

  void bind();

  void onAttach();

  void onDetach();

  C getController();

  void setController(C controller);

  interface Controller {

  }
}
