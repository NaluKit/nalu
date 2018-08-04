package com.github.mvp4g.nalu.client.ui;

import elemental2.dom.HTMLElement;

public interface IsNaluReactComponent {

  String mayStop();

  HTMLElement render();

  void start();

  void stop();

}
