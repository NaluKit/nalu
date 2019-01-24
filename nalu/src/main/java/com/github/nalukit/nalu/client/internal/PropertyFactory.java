package com.github.nalukit.nalu.client.internal;

import java.util.Objects;

public class PropertyFactory {

  private static PropertyFactory instance;

  // is the application using hash in url?
  boolean usingHash;
  // is the application using colon in url for parameter?
  boolean usingColonForParametersInUrl;

  private PropertyFactory() {
  }

  public static PropertyFactory get() {
    if (Objects.isNull(instance)) {
      instance = new PropertyFactory();
    }
    return instance;
  }

  /**
   * Will Nalu use a ahs for Navigation?
   *
   * @return true: Nalus uses a hash
   */
  public boolean isUsingHash() {
    return usingHash;
  }

  /**
   * Will Nalu use colons to mark parameters inside the url?
   *
   * @return true: Nalu will use colon
   */
  public boolean isUsingColonForParametersInUrl() {
    return usingColonForParametersInUrl;
  }

  /**
   * Do NOt cll this method!
   *
   * @param usingHash                    Will Nalu use a ahs for Navigation?
   * @param usingColonForParametersInUrl Will Nalu use colons to mark parameters inside the url?
   */
  public void register(boolean usingHash,
                       boolean usingColonForParametersInUrl) {
    this.usingHash = usingHash;
    this.usingColonForParametersInUrl = usingColonForParametersInUrl;
  }

}
