package com.github.nalukit.nalu.plugin.gwt.client.selector;

import java.util.HashMap;
import java.util.Map;

public class SelectorProvider {

  private static SelectorProvider instance = new SelectorProvider();

  private Map<String, SelectorCommand> selectorCommands;

  private SelectorProvider() {
    this.selectorCommands = new HashMap<>();
  }

  public static SelectorProvider get() {
    return instance;
  }

  public Map<String, SelectorCommand> getSelectorCommands() {
    return selectorCommands;
  }
}
