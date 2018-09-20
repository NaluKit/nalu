package com.github.nalukit.nalu.plugin.gwt.client.selector;

import com.google.gwt.user.client.ui.IsWidget;

@FunctionalInterface
public interface SelectorCommand {

  void append(IsWidget widget);

}
