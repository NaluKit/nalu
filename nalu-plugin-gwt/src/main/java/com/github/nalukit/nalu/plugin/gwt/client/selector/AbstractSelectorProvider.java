package com.github.nalukit.nalu.plugin.gwt.client.selector;

import com.google.gwt.user.client.ui.Panel;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractSelectorProvider {

  private Map<String, ? extends Panel> selectors;

  public AbstractSelectorProvider() {
    this.selectors = new HashMap<>();
  }

  public abstract void removeSelectors();

}
