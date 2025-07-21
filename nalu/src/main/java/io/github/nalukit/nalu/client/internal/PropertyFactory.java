package io.github.nalukit.nalu.client.internal;

public class PropertyFactory {

  public static PropertyFactory INSTANCE = new PropertyFactory();

  // context path
  private String  contextPath;
  // start route of the application
  private String  startRoute;
  // baseUrl of the application
  private String baseUrl;
  // illegal route target of the application
  private String  illegalRouteTarget;
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
   * @return true: Nalu uses a hash
   */
  public boolean hasHistory() {
    return this.hasHistory;
  }

  /**
   * Will Nalu use a ahs for Navigation?
   *
   * @return true: Nalu uses a hash
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
   * The start route of the application
   *
   * @return start route of the application
   */
  public String getStartRoute() {
    return this.startRoute;
  }

  /**
   * The baeUrl of the application
   *
   * @return baseUrl of the application
   */
  public String getBaseUrl() {
    return this.baseUrl;
  }

  /**
   * The illegal route target of the applilcation
   *
   * @return illegal route target of the application
   */
  public String getIllegalRouteTarget() {
    return this.illegalRouteTarget;
  }

  /**
   * Do NOT call this method!
   *
   * @param baseUrl                      tells Nalu the baseUrl of the application
   * @param startRoute                   Start route of the application
   * @param illegalRouteTarget           illegal route target used, in case of a illegal route
   * @param hasHistory                   Will Nalu support a history token?
   * @param usingHash                    Will Nalu use a hash for Navigation?
   * @param usingColonForParametersInUrl Will Nalu use colons to mark parameters inside the url?
   * @param stayOnSide                   tells Nalu how do ahndle empty hash
   */
  public void register(String baseUrl,
                       String startRoute,
                       String illegalRouteTarget,
                       boolean hasHistory,
                       boolean usingHash,
                       boolean usingColonForParametersInUrl,
                       boolean stayOnSide) {
    if (baseUrl.startsWith("/")) {
      this.baseUrl = baseUrl.substring(1);
    } else {
      this.baseUrl = baseUrl;
    }
    if (startRoute.startsWith("/")) {
      this.startRoute = startRoute.substring(1);
    } else {
      this.startRoute = startRoute;
    }
    this.illegalRouteTarget           = illegalRouteTarget;
    this.hasHistory                   = hasHistory;
    this.usingHash                    = usingHash;
    this.usingColonForParametersInUrl = usingColonForParametersInUrl;
    this.stayOnSide                   = stayOnSide;
  }

}
