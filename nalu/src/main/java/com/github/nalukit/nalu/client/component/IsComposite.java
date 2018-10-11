package com.github.nalukit.nalu.client.component;

public interface IsComposite<W> {

  W asElement();

  void onAttach();

  void onDetach();

  String mayStop();

  void removeHandlers();

  void start();

  void stop();

}
