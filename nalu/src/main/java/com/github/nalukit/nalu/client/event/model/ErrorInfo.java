package com.github.nalukit.nalu.client.event.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Class contains information about the error.
 */
public class ErrorInfo {
  
  /* type of error [NALU_INTERNAL_ERROR\APPLICATION_ERROR] */
  private ErrorType           errorEventType;
  /* error id */
  private String              errorId;
  /* route of error (optional) */
  private String              route;
  /* error message */
  private String              message;
  /* additional data */
  private Map<String, String> dataStore;
  
  public ErrorInfo() {
    this.dataStore = new HashMap<>();
  }
  
  /**
   * Get error type {@link ErrorType}
   *
   * @return get error type
   */
  public ErrorType getErrorEventType() {
    return errorEventType;
  }
  
  /**
   * Set the error type.
   *
   * @param errorEventType the new error type
   */
  public void setErrorEventType(ErrorType errorEventType) {
    this.errorEventType = errorEventType;
  }
  
  /**
   * Id of error
   *
   * @return id of error or null
   */
  public String getErrorId() {
    return errorId;
  }
  
  /**
   * Set the error id.
   *
   * @param errorId the new error id
   */
  public void setErrorId(String errorId) {
    this.errorId = errorId;
  }
  
  /**
   * Set the route.
   *
   * @return the route or null
   */
  public String getRoute() {
    return route;
  }
  
  /**
   * Set the toute
   *
   * @param route new Route
   */
  public void setRoute(String route) {
    this.route = route;
  }
  
  /**
   * Get the error message.
   *
   * @return error message
   */
  public String getMessage() {
    return message;
  }
  
  /**
   * Sets the error message.
   *
   * @param message the new error message
   */
  public void setMessage(String message) {
    this.message = message;
  }
  
  /**
   * Gets the data store.
   *
   * @return the data store
   */
  public Map<String, String> getDataStore() {
    return dataStore;
  }
  
  /**
   * Defines the type of error.
   *
   * <ul>
   *   <li>NALU_INTERNAL_ERROR: Nalu internal error (do not use this type!)</li>
   *   <li>APPLICATION_ERROR: application error (use this type to mark application errors)</li>
   * </ul>
   */
  public enum ErrorType {
    NALU_INTERNAL_ERROR,
    APPLICATION_ERROR;
  }
  
}
