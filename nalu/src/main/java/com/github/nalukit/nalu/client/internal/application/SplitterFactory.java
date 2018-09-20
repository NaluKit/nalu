package com.github.nalukit.nalu.client.internal.application;

import com.github.nalukit.nalu.client.component.AbstractSplitterController;
import com.github.nalukit.nalu.client.exception.RoutingInterceptionException;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

import java.util.HashMap;
import java.util.Map;

@NaluInternalUse
public class SplitterFactory {

  /* instance of the controller factory */
  private static SplitterFactory instance;

  /* map of components (key: name of class, Value: SplitterCreator */
  private Map<String, SplitterCreator> splitterFactory;

  private SplitterFactory() {
    this.splitterFactory = new HashMap<>();
  }

  public static SplitterFactory get() {
    if (instance == null) {
      instance = new SplitterFactory();
    }
    return instance;
  }

  public void registerSplitter(String name,
                               SplitterCreator creator) {
    this.splitterFactory.put(name,
                             creator);
  }

  public AbstractSplitterController<?, ?, ?> splitter(String splitter,
                                                      String... parms)
    throws RoutingInterceptionException {
    if (this.splitterFactory.containsKey(splitter)) {
      return this.splitterFactory.get(splitter)
                                 .create(parms);
    }
    return null;
  }
}
