package com.github.nalukit.nalu.client.internal;

import java.util.Objects;

public class PropertyFactory {

  private static PropertyFactory instance;

  // context path
  private String contextPath;

  // start route of the application
  private String startRoute;

  // does the application have history
  private boolean hasHistory;

  // is the application using hash in url?
  private boolean usingHash;

  // is the application using colon in url for parameter?
  private boolean usingColonForParametersInUrl;

  // should the application replace history (stay on side) in case of empty hash
  private boolean stayOnSide;

  private PropertyFactory() {
  }

  public static PropertyFactory get() {
    if (Objects.isNull(instance)) {
      instance = new PropertyFactory();
    }
    return instance;
  }

  /**
   * get the context path for this application
   *
   * @return the context path of the application
   */
  public String getContextPath() {
    return this.contextPath;
  }

  /**
   * DO NOT CALL THIS METHOD!
   *
   * @param contextPath contextPath of the application (only needed when not using hash)
   */
  public void setContextPath(String contextPath) {
    this.contextPath = contextPath;
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
   * should the application replace history (stay on side) in case of empty hash?
   *
   * @return true: replace history, else update
   */
  public boolean isStayOnSide() {
    return stayOnSide;
  }

  /**
   * The start route of the applilcation
   *
   * @return start route of the application
   */
  public String getStartRoute() {
    return this.startRoute;
  }

  /**
   * Do NOT call this method!
   *
   * @param startRoute                   Start route of the application
   * @param hasHistory                   Will Nalu support a history token?
   * @param usingHash                    Will Nalu use a hash for Navigation?
   * @param usingColonForParametersInUrl Will Nalu use colons to mark parameters inside the url?
   */
  public void register(String startRoute,
                       boolean hasHistory,
                       boolean usingHash,
                       boolean usingColonForParametersInUrl,
                       boolean stayOnSide) {
    if (startRoute.startsWith("/")) {
      this.startRoute = startRoute.substring(1);
    } else {
      this.startRoute = startRoute;
    }
    this.hasHistory = hasHistory;
    this.usingHash = usingHash;
    this.usingColonForParametersInUrl = usingColonForParametersInUrl;
    this.stayOnSide = stayOnSide;
  }

}
