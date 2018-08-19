package com.github.mvp4g.nalu.client.component;

public interface IsController<W> {

  W asElement();

  String mayStop();

  void start();

  void stop();

}
