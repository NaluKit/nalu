package com.github.mvp4g.nalu.react.ui;

import elemental2.dom.HTMLElement;

public interface IsNaluReactView<P extends IsPlace> {

  String mayStop();

  HTMLElement render();

  void start(P place);

  void stop();

}
