package com.github.mvp4g.nalu.plugin.elemental2.client;

import com.github.mvp4g.nalu.client.internal.ClientLogger;
import com.github.mvp4g.nalu.client.plugin.IsPlugin;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.HashChangeEvent;

public class NaluPluginElemental2
  implements IsPlugin {

  public NaluPluginElemental2() {
    super();
  }

  @Override
  public void alert(String message) {
    DomGlobal.window.alert(message);
  }

  @Override
  public boolean attach(String selector,
                        Object asElement) {
    Element selectorElement = DomGlobal.document.querySelector("#" + selector);
    if (selectorElement == null) {
      // TODO better message
      DomGlobal.window.alert("Ups ... selector >>" + selector + "<< not found!");
      return false;
    } else {
      selectorElement.appendChild((HTMLElement) asElement);
      return true;
    }
  }

  @Override
  public boolean confirm(String message) {
    return DomGlobal.window.confirm(message);
  }

  @Override
  public String getStartRoute() {
    String starthash = DomGlobal.window.location.getHash();
    if (starthash.startsWith("#")) {
      starthash = starthash.substring(1);
    }
    return starthash;
  }

  @Override
  public void register(HashHandler handler) {
    DomGlobal.window.onhashchange = e -> {
      // cast event ...
      HashChangeEvent hashChangeEvent = (HashChangeEvent) e;
      String newUrl = hashChangeEvent.newURL;
      if (newUrl.startsWith("#")) {
        newUrl = newUrl.substring(1);
      }
      StringBuilder sb = new StringBuilder();
      sb.append("Router: onhashchange: new url ->>")
        .append(hashChangeEvent.newURL)
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
    Element selectorElement = DomGlobal.document.querySelector("#" + selector);
    if (selectorElement != null) {
      if (selectorElement.childNodes.length > 0) {
        for (int i = selectorElement.childNodes.asList().size() - 1; i > -1; i--) {
          selectorElement.removeChild(selectorElement.childNodes.asList().get(i));
        }
      }
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
}
