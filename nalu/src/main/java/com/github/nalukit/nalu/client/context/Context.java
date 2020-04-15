package com.github.nalukit.nalu.client.context;

import java.util.HashMap;
import java.util.Map;

/**
 * Context to store data
 */
public class Context {
  
  /* data store */
  private Map<String, Object> dataStore;
  
  public Context() {
    this.dataStore = new HashMap<>();
  }
  
  /**
   * Gets a value from the data store
   *
   * @param key key of the stored data
   * @return the stored value
   */
  public Object get(String key) {
    return this.dataStore.get(key);
  }
  
  /**
   * Sets a value in the data store
   *
   * @param key   key of the stored data
   * @param value value to store
   */
  public void put(String key,
                  Object value) {
  
    this.dataStore.put(key,
                       value);
  }
  
}
