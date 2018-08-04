package com.github.mvp4g.nalu.client.route;

import com.github.mvp4g.nalu.client.application.IsNaluLogger;
import com.github.mvp4g.nalu.client.application.annotation.Debug;
import com.github.mvp4g.nalu.client.internal.application.ComponentFactory;
import com.github.mvp4g.nalu.client.internal.route.RouteConfig;
import com.github.mvp4g.nalu.client.internal.route.RouterConfiguration;
import com.github.mvp4g.nalu.client.ui.AbstractComponent;
import elemental2.dom.*;

import java.util.*;

public final class Router {

  /* list of added lements */
  private Map<String, HTMLElement> addedElements;

  /* List of the routes of the application */
  private RouterConfiguration               routerConfiguration;
  /* List of the Selectors of the application */
  private Map<String, String>               selectors;
  /* List of active components */
  private List<? extends AbstractComponent> activeComponents;

  /* debugging enabled */
  private boolean        debugEnabled = false;
  /* logger */
  private IsNaluLogger   logger;
  /* log level */
  private Debug.LogLevel logLevel;


  public Router(RouterConfiguration routerConfiguration) {
    super();
    // initialize lists
    this.addedElements = new HashMap<>();
    // save the router configuration reference
    this.routerConfiguration = routerConfiguration;
    // inistantiate lists, etc.
    this.activeComponents = new ArrayList<>();
    // register event handler
    this.registerEventHandler();
  }

  private void registerEventHandler() {
    // the onBeforeUnload event handling!
    DomGlobal.window.onbeforeunload = this::handleOnBeforeUnloadEvent;
    // the OnHashChange event handling
    DomGlobal.window.onhashchange = e -> {
      // cast event ...
      HashChangeEvent hashChangeEvent = (HashChangeEvent) e;
      // look for a routing ...
      return this.handleOnHashChangeEvent(this.getHash(hashChangeEvent.newURL));
    };
  }

  private String handleOnHashChangeEvent(String hash) {
    // search for a matching routing
    List<RouteConfig> routeConfiguraions = this.routerConfiguration.match(hash);
    for (RouteConfig routeConfiguraion : routeConfiguraions) {
      AbstractComponent component = ComponentFactory.get()
                                                    .component(routeConfiguraion.getClassName());
      if (!Objects.isNull(component)) {
        component.setRouter(this);
        this.addElementToDOM(routeConfiguraion.getSelector(),
                             component);
        component.start();
      } else {
        DomGlobal.window.alert("Ups ... not found!");
      }
    }
    return null;
  }

  private String getHash(String newURL) {
    return newURL.substring(newURL.indexOf("#") + 1);
  }

  private void addElementToDOM(String selector,
                               AbstractComponent component) {
    Element selectorElement = DomGlobal.document.querySelector("#" + selector);
    if (selectorElement == null) {
      DomGlobal.window.alert("Ups ... selector not found!");
    } else {
      // first, remove all childs
      if (this.addedElements.containsKey(selector)) {
        this.addedElements.get(selector)
                          .remove();
        this.addedElements.remove(selector);
      }
      // add component
      HTMLElement newElement = component.render();
      selectorElement.appendChild(newElement);
      if (this.addedElements.containsKey(selector)) {

      }
      this.addedElements.put(selector,
                             newElement);
    }
  }

  private String handleOnBeforeUnloadEvent(Event e) {
    DomGlobal.window.alert("catch 'onbeforeunload' event!");
//      // ask the current active compoments if we can leave ....
//      if (this.activeComponents.size() > 0) {
//        for (AbstractComponent component : this.activeComponents) {
//          String mayStopMessage = component.mayStop();
//          if (mayStopMessage != null) {
//            return mayStopMessage;
//          }
//        }
//      }
//      // no one stops our navigation!
//      return null;
    // ask the current active compoments if we can leave ....
    return this.activeComponents.stream()
                                .map(AbstractComponent::mayStop)
                                .filter(Objects::nonNull)
                                .findFirst()
                                .orElse(null);
  }

  public void setDebug(boolean debugEnabled,
                       IsNaluLogger logger,
                       Debug.LogLevel logLevel) {
    this.debugEnabled = debugEnabled;
    this.logger = logger;
    this.logLevel = logLevel;
  }

  public void route(String newRoute) {
    if (newRoute.startsWith("/")) {
      newRoute = newRoute.substring(1);
    }
    DomGlobal.window.history.pushState(null,
                                       null,
                                       "#" + newRoute);
    this.handleOnHashChangeEvent(newRoute);
  }
}
