package com.github.nalukit.nalu.processor.common;

import com.github.nalukit.nalu.client.plugin.IsPlugin;

public class MockPlugin
  implements IsPlugin {

  @Override
  public void alert(String message) {

  }

  @Override
  public boolean attach(String selector,
                        Object asElement) {
    return true;
  }

  @Override
  public boolean confirm(String message) {
    return true;
  }

  @Override
  public String getStartRoute() {
    return "/";
  }

  @Override
  public void register(HashHandler handler) {

  }

  @Override
  public void remove(String selector) {

  }

  @Override
  public void route(String newRoute,
                    boolean replace) {

  }
}
