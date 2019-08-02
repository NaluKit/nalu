package com.github.nalukit.nalu.client.application;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract context base class.
 * <p/>
 * Use this class to avoid a common base module in a multi module
 * envirement
 */
public class AbstractContext
    implements IsContext {

  private Map<String, Object> dataStore;

  public AbstractContext() {
    this.dataStore = new HashMap<>();
  }

  public Map<String, Object> getDataStore() {
    return this.dataStore;
  }

}
