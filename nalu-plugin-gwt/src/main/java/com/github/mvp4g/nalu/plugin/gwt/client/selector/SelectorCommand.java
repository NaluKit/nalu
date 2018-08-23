package com.github.mvp4g.nalu.plugin.gwt.client.selector;

import com.google.gwt.user.client.ui.IsWidget;

@FunctionalInterface
public interface SelectorCommand {

  void append(IsWidget widget);

}
