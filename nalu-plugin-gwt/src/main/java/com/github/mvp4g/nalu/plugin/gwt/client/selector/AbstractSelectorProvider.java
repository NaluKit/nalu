package com.github.mvp4g.nalu.plugin.gwt.client.selector;

import com.github.mvp4g.nalu.client.component.IsComponent;
import com.google.gwt.user.client.ui.Panel;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractSelectorProvider<C extends IsComponent<?, ?>> {

  private Map<String, Panel> selectors;

  public AbstractSelectorProvider() {
    this.selectors = new HashMap<>();
  }

  public abstract void initialize(C component);
}
