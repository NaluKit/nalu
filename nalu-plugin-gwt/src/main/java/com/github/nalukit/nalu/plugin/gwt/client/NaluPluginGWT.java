package com.github.nalukit.nalu.plugin.gwt.client;

import com.github.nalukit.nalu.client.internal.ClientLogger;
import com.github.nalukit.nalu.client.plugin.IsPlugin;
import com.github.nalukit.nalu.plugin.gwt.client.selector.SelectorCommand;
import com.github.nalukit.nalu.plugin.gwt.client.selector.SelectorProvider;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import elemental2.dom.DomGlobal;
import elemental2.dom.HashChangeEvent;
import elemental2.dom.Location;
import jsinterop.base.Js;

public class NaluPluginGWT
    implements IsPlugin {

  public NaluPluginGWT() {
    super();
  }

  @Override
  public void alert(String message) {
    Window.alert(message);
  }

  @Override
  public boolean attach(String selector,
                        Object asElement) {
    SelectorCommand selectorCommand = SelectorProvider.get()
                                                      .getSelectorCommands()
                                                      .get(selector);
    if (selectorCommand == null) {
      // TODO better message
      Window.alert("Ups ... selector >>" + selector + "<< not found!");
      return false;
    } else {
      selectorCommand.append(((IsWidget) asElement).asWidget());
      return true;
    }
  }

  @Override
  public boolean confirm(String message) {
    return Window.confirm(message);
  }

  @Override
  public String getStartRoute() {
    String starthash = Window.Location.getHash();
    if (starthash.startsWith("#")) {
      starthash = starthash.substring(1);
    }
    return starthash;
  }

  @Override
  public void register(HashHandler handler) {
    DomGlobal.window.onhashchange = e -> {
      String newUrl = "";
      if (detectIE11()) {
        Location location = Js.uncheckedCast(DomGlobal.location);
        newUrl = location.getHash();
      } else {
        // cast event ...
        HashChangeEvent hashChangeEvent = (HashChangeEvent) e;
        newUrl = hashChangeEvent.newURL;
      }
      if (newUrl.startsWith("#")) {
        newUrl = newUrl.substring(1);
      }
      StringBuilder sb = new StringBuilder();
      sb.append("Router: onhashchange: new url ->>")
        .append(newUrl)
        .append("<<");
      ClientLogger.get()
                  .logSimple(sb.toString(),
                             0);
      // look for a routing ...
      handler.onHashChange(newUrl);
      return null;
    };
  }

  @Override
  public void remove(String selector) {
    Element selectorElement = DOM.getElementById(selector);
    if (selectorElement != null) {
      selectorElement.removeAllChildren();
    }
  }

  @Override
  public void route(String newRoute,
                    boolean replace) {
    if (replace) {
      DomGlobal.window.history.replaceState(null,
                                            null,
                                            "#" + newRoute);
    } else {
      DomGlobal.window.history.pushState(null,
                                         null,
                                         "#" + newRoute);
    }
  }

  /**
   * checks weather the current browser is IE or not.
   * <p>
   * IE 10
   * ua = 'Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)';
   * <p>
   * IE 11
   * ua = 'Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko';
   * <p>
   * Edge 12 (Spartan)
   * ua = 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36 Edge/12.0';
   * <p>
   * Edge 13
   * ua = 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586';
   *
   * @param
   * @return true -> is IE
   */
  private boolean detectIE11() {
    String ua = DomGlobal.window.navigator.userAgent;
    if (ua.indexOf("MSIE ") > 0) {
      return true;
    }
    return ua.indexOf("Trident/") > 0;
  }
}
