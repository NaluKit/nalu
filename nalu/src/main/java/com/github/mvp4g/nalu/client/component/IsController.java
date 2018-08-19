package com.github.mvp4g.nalu.client.component;

public interface IsController<W> {

  W asElement();

  void attach();

  void detach();

  String mayStop();

  void start();

  void stop();

}
