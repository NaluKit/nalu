package com.github.mvp4g.nalu.plugin.gwt.client.util;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import elemental2.dom.HTMLElement;
import jsinterop.base.Js;

/**
 * Helper methods for working with {@link com.google.gwt.user.client.ui.Widget}s.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTML/Element">https://developer.mozilla.org/en-US/docs/Web/HTML/Element</a>
 */
@SuppressWarnings({"unused",
                   "WeakerAccess"})
public final class Widgets {

  // this is a static helper class which must never be instantiated!
  private Widgets() {
  }

  /**
   * Converts from {@link IsElement} &rarr; {@link Widget}.
   */
  public static SimplePanel asWidget(IsElement element) {
    return asWidget(element.asElement());
  }

  /**
   * Converts from {@link HTMLElement} &rarr; {@link Widget}.
   */
  public static SimplePanel asWidget(HTMLElement element) {
    return new ElementWidget(element);
  }

  /**
   * Converts from {@link IsWidget} &rarr; {@link HTMLElement}.
   */
  public static HTMLElement asElement(IsWidget widget) {
    return asElement(widget.asWidget());
  }

  /**
   * Converts from {@link Widget} &rarr; {@link HTMLElement}.
   */
  public static HTMLElement asElement(Widget widget) {
    return asElement(widget.getElement());
  }

  /**
   * Converts from {@link com.google.gwt.dom.client.Element} &rarr; {@link HTMLElement}.
   */
  public static HTMLElement asElement(com.google.gwt.dom.client.Element element) {
    return Js.cast(element);
  }

  private static class ElementWidget
    extends SimplePanel {

    ElementWidget(HTMLElement element) {
      setElement(com.google.gwt.dom.client.Element.as(Js.cast(element)));
    }
  }
}
