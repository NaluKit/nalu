package com.github.mvp4g.nalu.client.ui;


import elemental2.dom.HTMLElement;

public interface IsController {

  HTMLElement asElement();

  String mayStop();

  void start();

  void stop();

}
