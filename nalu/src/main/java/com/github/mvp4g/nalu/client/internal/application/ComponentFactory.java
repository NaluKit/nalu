package com.github.mvp4g.nalu.client.internal.application;

import com.github.mvp4g.nalu.client.ui.AbstractComponent;

import java.util.HashMap;
import java.util.Map;

public class ComponentFactory {

  /* instance of the component factory */
  private static ComponentFactory instance;

  /* map of components (key: name of class, Value: ComponentCreator */
  private Map<String, ComponentCreator> componentFactory;

  private ComponentFactory() {
    this.componentFactory = new HashMap<>();
  }

  public static ComponentFactory get() {
    if (instance == null) {
      instance = new ComponentFactory();
    }
    return instance;
  }

  public void registerComponent(String component,
                                ComponentCreator creator) {
    this.componentFactory.put(component,
                              creator);
  }

  public AbstractComponent component(String component) {
    if (this.componentFactory.containsKey(component)) {
      return this.componentFactory.get(component)
                                  .create();
    }
    return null;
  }
}
