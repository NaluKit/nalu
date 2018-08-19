package com.github.mvp4g.nalu.plugin.elemental2.client;

import com.github.mvp4g.nalu.client.internal.ClientLogger;
import com.github.mvp4g.nalu.client.plugin.IsPlugin;
import elemental2.dom.*;

import java.util.stream.Stream;

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
      while (selectorElement.childNodes.length > 0) {
        selectorElement.firstElementChild.remove();
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
