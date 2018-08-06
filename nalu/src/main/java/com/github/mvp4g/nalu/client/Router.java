package com.github.mvp4g.nalu.client;

import com.github.mvp4g.nalu.client.application.IsLogger;
import com.github.mvp4g.nalu.client.application.annotation.Debug;
import com.github.mvp4g.nalu.client.internal.application.ControllerFactory;
import com.github.mvp4g.nalu.client.internal.route.HashResult;
import com.github.mvp4g.nalu.client.internal.route.RouteConfig;
import com.github.mvp4g.nalu.client.internal.route.RouterConfiguration;
import com.github.mvp4g.nalu.client.ui.AbstractComponentController;
import com.github.mvp4g.nalu.client.ui.IsConfirmator;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.HashChangeEvent;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Router {

  /* list of added lements */
  private Map<String, HTMLElement> addedElements;

  /* List of the routes of the application */
  private RouterConfiguration                     routerConfiguration;
  /* List of the Selectors of the application */
  private Map<String, String>                     selectors;
  /* List of active components */
  private List<AbstractComponentController<?, ?>> activeComponents;

  /* debugging enabled */
  private boolean        debugEnabled = false;
  /* logger */
  private IsLogger       logger;
  /* log level */
  private Debug.LogLevel logLevel;

  //* hash of last successful routing */
  private String lastExecutedHash;


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
    // the OnHashChange event handling
    DomGlobal.window.onhashchange = e -> {
      // cast event ...
      HashChangeEvent hashChangeEvent = (HashChangeEvent) e;
      // look for a routing ...
      return this.handleRouting(this.getHash(hashChangeEvent.newURL));
    };
  }

  private String handleRouting(String hash) {
    // chech weather or not the routing is possible ...
    if (this.confirmRouting()) {
      // clear list of active elements
      this.activeComponents.clear();
      // check weather we can route or not
      // TODO HOOKS einbauen!

      // ok, everything is fine, route!
      // parse hash ...
      HashResult hashResult = this.parse(hash);
      // search for a matching routing
      List<RouteConfig> routeConfiguraions = this.routerConfiguration.match(hashResult.getRoute());
      for (RouteConfig routeConfiguraion : routeConfiguraions) {
        AbstractComponentController<?, ?> component = ControllerFactory.get()
                                                                       .controller(routeConfiguraion.getClassName(),
                                                                                   hashResult.getParameterValues()
                                                                                             .toArray(new String[0]));
        if (!Objects.isNull(component)) {
          component.setRouter(this);
          this.addElementToDOM(routeConfiguraion.getSelector(),
                               component);
          component.start();
          this.lastExecutedHash = hash;
        } else {
          DomGlobal.window.alert("Ups ... not found!");
        }
      }
    } else {
      // we don not want to leave!
      DomGlobal.window.history.pushState(null,
                                         null,
                                         "#" + this.lastExecutedHash);
    }
    return null;
  }

  private String getHash(String newURL) {
    return newURL.substring(newURL.indexOf("#") + 1);
  }

  private boolean confirmRouting() {
    String message = this.activeComponents.stream()
                                          .filter(c -> c instanceof IsConfirmator)
                                          .map(AbstractComponentController::mayStop)
                                          .filter(Objects::nonNull)
                                          .findFirst()
                                          .orElse(null);
    if (message != null) {
      return DomGlobal.window.confirm(message);
    }
    return true;
  }

  public HashResult parse(String hash) {
    HashResult hashResult = new HashResult();
    String hashValue = hash;
    // extract route first:
    if (hash.startsWith("/")) {
      hashValue = hashValue.substring(1);
    }
    if (hashValue.contains("/")) {
      hashResult.setRoute(hashValue.substring(0,
                                              hashValue.indexOf("/")));
      String parametersFromHash = hashValue.substring(hashValue.indexOf("/") + 1);
      // lets get the parameters!
      List<String> paramsList = Stream.of(parametersFromHash.split("/"))
                                      .collect(Collectors.toList());
      // reset sledge in parms ....
      paramsList.forEach(parm -> hashResult.getParameterValues()
                                           .add(parm.replace(Nalu.NALU_SLEDGE_REPLACEMENT,
                                                             "/")));
    } else {
      hashResult.setRoute(hashValue);
    }
    return hashResult;
  }

  private void addElementToDOM(String selector,
                               AbstractComponentController<?, ?> component) {
    Element selectorElement = DomGlobal.document.querySelector("#" + selector);
    if (selectorElement == null) {
      // TODO better message
      DomGlobal.window.alert("Ups ... selector not found!");
    } else {
      // first, remove all childs
      if (this.addedElements.containsKey(selector)) {
        this.addedElements.get(selector)
                          .remove();
        this.addedElements.remove(selector);
      }
      // add controller
      HTMLElement newElement = component.render();
      selectorElement.appendChild(newElement);
      this.addedElements.put(selector,
                             newElement);
      // save controller
      if (component instanceof IsConfirmator) {
        this.activeComponents.add(component);
      }
    }
  }

  public void setDebug(boolean debugEnabled,
                       IsLogger logger,
                       Debug.LogLevel logLevel) {
    this.debugEnabled = debugEnabled;
    this.logger = logger;
    this.logLevel = logLevel;
  }

  public void route(String newRoute,
                    String... parms) {
    StringBuilder sb = new StringBuilder();
    if (newRoute.startsWith("/")) {
      sb.append(newRoute.substring(1));
    } else {
      sb.append(newRoute);
    }
    if (parms != null) {
      Stream.of(parms)
            .forEach(s -> sb.append("/")
                            .append(s.replace("/",
                                              Nalu.NALU_SLEDGE_REPLACEMENT)));
    }
    String newRouteWithParams = sb.toString();
    DomGlobal.window.history.pushState(null,
                                       null,
                                       "#" + newRouteWithParams);
    this.handleRouting(newRouteWithParams);
  }
}
