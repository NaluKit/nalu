package com.github.nalukit.nalu.client.internal.application;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

import java.util.HashMap;
import java.util.Map;

@NaluInternalUse
public class ControllerFactory {

  /* instance of the controller factory */
  private static ControllerFactory instance;

  /* map of components (key: name of class, Value: ControllerCreator */
  private Map<String, ControllerCreator> coontrollerFactory;

  private ControllerFactory() {
    this.coontrollerFactory = new HashMap<>();
  }

  public static ControllerFactory get() {
    if (instance == null) {
      instance = new ControllerFactory();
    }
    return instance;
  }

  public void registerController(String controller,
                                 ControllerCreator creator) {
    this.coontrollerFactory.put(controller,
                                creator);
  }

  public AbstractComponentController<?, ?, ?> controller(String controller,
                                                         String... parms)
    throws RoutingInterceptionException {
    if (this.coontrollerFactory.containsKey(controller)) {
      return this.coontrollerFactory.get(controller)
                                    .create(parms);
    }
    return null;
  }
}
