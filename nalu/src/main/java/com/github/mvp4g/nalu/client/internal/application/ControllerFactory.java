package com.github.mvp4g.nalu.client.internal.application;

import com.github.mvp4g.nalu.client.component.AbstractComponentController;
import com.github.mvp4g.nalu.client.exception.RoutingInterceptionException;

import java.util.HashMap;
import java.util.Map;

public class ControllerFactory {

  /* instance of the controller factory */
  private static ControllerFactory instance;

  /* map of components (key: name of class, Value: ControllerCreator */
  private Map<String, ControllerCreator> componentFactory;

  private ControllerFactory() {
    this.componentFactory = new HashMap<>();
  }

  public static ControllerFactory get() {
    if (instance == null) {
      instance = new ControllerFactory();
    }
    return instance;
  }

  public void registerController(String controller,
                                 ControllerCreator creator) {
    this.componentFactory.put(controller,
                              creator);
  }

  public AbstractComponentController<?, ?, ?> controller(String controller,
                                                         String... parms)
    throws RoutingInterceptionException {
    if (this.componentFactory.containsKey(controller)) {
      return this.componentFactory.get(controller)
                                  .create(parms);
    }
    return null;
  }
}
