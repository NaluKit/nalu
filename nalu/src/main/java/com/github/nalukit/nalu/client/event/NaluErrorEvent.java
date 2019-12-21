package com.github.nalukit.nalu.client.event;

import com.github.nalukit.nalu.client.event.model.ErrorInfo;
import com.github.nalukit.nalu.client.event.model.ErrorInfo.ErrorType;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import org.gwtproject.event.shared.Event;

import java.util.Map;

/**
 * This event is used by Nalu or the application developer to propagate errors.
 * <p>
 * In case no error route is defined, Nalu will - only -  fire this event and stopped handling
 * the current task.
 * <p>
 * The error event can also be used to handle the application errors.
 */
public class NaluErrorEvent
    extends Event<NaluErrorEvent.NaluErrorEventHandler> {

  public final static Type<NaluErrorEvent.NaluErrorEventHandler> TYPE = new Type<>();

  /* the error info object */
  private ErrorInfo errorInfo;

  private NaluErrorEvent(ErrorType errorEventType) {
    super();
    this.errorInfo = new ErrorInfo();
    this.errorInfo.setErrorEventType(errorEventType);
  }

  /**
   * Creates a new NaluErrorEvent.
   *
   * <b>Used by the framework to fire error events caused by the framework. DO NOT USE THIS METHOD!</b>
   *
   * @return new Nalu error event
   */
  @NaluInternalUse
  public static NaluErrorEvent createNaluError() {
    return new NaluErrorEvent(ErrorType.NALU_INTERNAL_ERROR);
  }

  /**
   * Creates a new NaluErrorEvent for a specific type.
   *
   * @return new Nalu error event
   */
  public static NaluErrorEvent createApplicationError() {
    return new NaluErrorEvent(ErrorType.APPLICATION_ERROR);
  }

  /**
   * Adds a error id to the event.
   *
   * @param errorId error id
   * @return instance of the event
   */
  public NaluErrorEvent errorId(String errorId) {
    this.errorInfo.setErrorId(errorId);
    return this;
  }

  /**
   * Adds a message to the event.
   *
   * @param message error message
   * @return instance of the event
   */
  public NaluErrorEvent message(String message) {
    this.errorInfo.setMessage(message);
    return this;
  }

  /**
   * Adds a route to the event.
   *
   * @param route route, where the error occurs
   * @return instance of the event
   */
  public NaluErrorEvent route(String route) {
    this.errorInfo.setRoute(route);
    return this;
  }

  /**
   * Adds data to the data store.
   *
   * <b>Keep in mind, all parameters will be stored as objects!</b>
   *
   * @param key   key of the parameter
   * @param value value of the parameter
   * @return instance of the event
   */
  public NaluErrorEvent data(String key,
                             String value) {
    this.errorInfo.getDataStore()
                  .put(key,
                       value);
    return this;
  }

  /**
   * Returns the event type of the error event.
   * <br>
   * <b>A value of 'NaluError' indicates that it is an error event
   * produces by the framework!</b>
   *
   * @return the errorEventType
   */
  public ErrorType getErrorEventType() {
    return this.errorInfo.getErrorEventType();
  }

  /**
   * Returns the id of the error.
   *
   * @return the error id
   */
  public String getErrorId() {
    return this.errorInfo.getErrorId();
  }

  /**
   * Returns the value for the message.
   *
   * @return the message or null
   */
  public String getMessage() {
    return this.errorInfo.getMessage();
  }

  /**
   * Returns the value for the route.
   *
   * @return the route or null
   */
  public String getRoute() {
    return this.errorInfo.getRoute();
  }

  /**
   * Returns the the data store.
   *
   * @return the data store
   */
  public Map<String, String> getDataStore() {
    return this.errorInfo.getDataStore();
  }

  /**
   * Gets the error info.
   *
   * @return the error info
   */
  public ErrorInfo getErrorInfo() {
    return this.errorInfo;
  }

  /**
   * Returns the value for the given key.
   *
   * @param key key of the stored parameter
   * @return the value of the stored parameter using the key or null
   */
  public String get(String key) {
    return this.errorInfo.getDataStore()
                         .get(key);
  }

  @Override
  public Type<NaluErrorEvent.NaluErrorEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(NaluErrorEvent.NaluErrorEventHandler handler) {
    handler.onNaluError(this);
  }

  public interface NaluErrorEventHandler {

    void onNaluError(NaluErrorEvent event);

  }

}
