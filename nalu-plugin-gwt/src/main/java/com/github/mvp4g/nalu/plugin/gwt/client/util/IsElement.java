package com.github.mvp4g.nalu.plugin.gwt.client.util;

import elemental2.dom.HTMLElement;

/** The GWT Elemental counterpart to {@link com.google.gwt.user.client.ui.IsWidget}. */
@FunctionalInterface
public interface IsElement<E extends HTMLElement> {

  E asElement();
}
