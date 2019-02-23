package com.github.nalukit.nalu.client.internal;

import java.util.Objects;

public class PropertyFactory {

  private static PropertyFactory instance;

  // does the application have history
  private boolean hasHistory;
  // is the application using hash in url?
  private boolean usingHash;
  // is the application using colon in url for parameter?
  private boolean usingColonForParametersInUrl;

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
  public boolean hasHistory() {
    return this.hasHistory;
  }

  /**
   * Will Nalu use a ahs for Navigation?
   *
   * @return true: Nalus uses a hash
   */
  public boolean isUsingHash() {
    return this.usingHash;
  }

  /**
   * Will Nalu use colons to mark parameters inside the url?
   *
   * @return true: Nalu will use colon
   */
  public boolean isUsingColonForParametersInUrl() {
    return this.usingColonForParametersInUrl;
  }

  /**
   * Do NOT cll this method!
   *
   * @param hasHistory                   Will Nalu support a histroy toekn?
   * @param usingHash                    Will Nalu use a hash for Navigation?
   * @param usingColonForParametersInUrl Will Nalu use colons to mark parameters inside the url?
   */
  public void register(boolean hasHistory,
                       boolean usingHash,
                       boolean usingColonForParametersInUrl) {
    this.hasHistory = hasHistory;
    this.usingHash = usingHash;
    this.usingColonForParametersInUrl = usingColonForParametersInUrl;
  }

}
