package com.github.nalukit.nalu.client.plugin;

public interface IsPlugin {

  void alert(String message);

  boolean attach(String selector,
                 Object asElement);

  boolean confirm(String message);

  String getStartRoute();

  void register(HashHandler handler);

  void remove(String selector);

  void route(String newRoute,
             boolean replace);

  @FunctionalInterface
  interface HashHandler {

    void onHashChange(String newHash);

  }
}
