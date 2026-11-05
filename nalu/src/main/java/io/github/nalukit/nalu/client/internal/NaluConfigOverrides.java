package io.github.nalukit.nalu.client.internal;

import io.github.nalukit.nalu.client.application.RoutingMode;
import io.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;

/**
 * Holds optional values set by the fluent configuration (NaluApp.configure()). A value that
 * is <b>not set</b> (null) does not override the value of the @Application-annotation.
 */
@NaluInternalUse
public class NaluConfigOverrides {

  public static final NaluConfigOverrides INSTANCE = new NaluConfigOverrides();

  private RoutingMode routingMode;
  private String      basePath;
  private String      startRoute;
  private Boolean     usingBaseHref;
  private Boolean     usingTrailingSlash;

  private NaluConfigOverrides() {
  }

  /**
   * removes all overrides
   */
  public void reset() {
    this.routingMode        = null;
    this.basePath           = null;
    this.startRoute         = null;
    this.usingBaseHref      = null;
    this.usingTrailingSlash = null;
  }

  public void setRoutingMode(RoutingMode routingMode) {
    this.routingMode = routingMode;
  }

  public void setBasePath(String basePath) {
    this.basePath = basePath;
  }

  public void setStartRoute(String startRoute) {
    this.startRoute = startRoute;
  }

  public void setUsingBaseHref(Boolean usingBaseHref) {
    this.usingBaseHref = usingBaseHref;
  }

  public void setUsingTrailingSlash(Boolean usingTrailingSlash) {
    this.usingTrailingSlash = usingTrailingSlash;
  }

  public boolean resolveUsingHash(boolean annotationValue) {
    return this.routingMode == null ?
           annotationValue :
           this.routingMode == RoutingMode.HASH;
  }

  public String resolveBasePath(String annotationValue) {
    return this.basePath == null ?
           annotationValue :
           this.basePath;
  }

  public String resolveStartRoute(String annotationValue) {
    return this.startRoute == null ?
           annotationValue :
           this.startRoute;
  }

  public boolean resolveUsingBaseHref(boolean annotationValue) {
    return this.usingBaseHref == null ?
           annotationValue :
           this.usingBaseHref;
  }

  public boolean resolveUsingTrailingSlash(boolean annotationValue) {
    return this.usingTrailingSlash == null ?
           annotationValue :
           this.usingTrailingSlash;
  }

}
