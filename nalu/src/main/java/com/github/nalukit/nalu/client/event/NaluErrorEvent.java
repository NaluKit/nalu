package com.github.nalukit.nalu.client.event;

import org.gwtproject.event.shared.Event;

import java.util.HashMap;
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

  public static Type<NaluErrorEvent.NaluErrorEventHandler> TYPE = new Type<>();

  private ErrorType           errorEventType;
  private String              route;
  private String              message;
  private Map<String, String> dataStore;

  private NaluErrorEvent(ErrorType errorEventType) {
    super();
    this.errorEventType = errorEventType;
    this.dataStore = new HashMap<>();
  }

  /**
   * Creates a new NaluErrorEvent.
   *
   * <b>Used by the framework to fire error events caused by the framework</b>
   *
   * @return new Nalu error event
   */
  public static NaluErrorEvent create() {
    return new NaluErrorEvent(ErrorType.NALU_INTERNAL_ERROR);
  }

  /**
   * Creates a new NaluErrorEvent for a specific type.
   *
   * @param type the error event type
   * @return new Nalu error event
   */
  public static NaluErrorEvent create(ErrorType type) {
    return new NaluErrorEvent(type);
  }

  /**
   * Adds a message to the event.
   *
   * @param message error message
   * @return instance of the event
   */
  public NaluErrorEvent message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Adds a route to the event.
   *
   * @param route route, where the error occurs
   * @return instance of the event
   */
  public NaluErrorEvent route(String route) {
    this.route = route;
    return this;
  }

  /**
   * Adds data to the data store.
   *
   * <b>Keep in mind, all parameters will be stored as objects!</b>
   *
   * @param key   key of the paraemter
   * @param value value of the parameter
   * @return instance of the event
   */
  public NaluErrorEvent data(String key,
                             String value) {
    this.dataStore.put(key,
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
    return this.errorEventType;
  }

  /**
   * Returns the value for the message.
   *
   * @return the message or null
   */
  public String getMessage() {
    return this.message;
  }

  /**
   * Returns the value for the route.
   *
   * @return the route or null
   */
  public String getRoute() {
    return this.route;
  }

  /**
   * Returns the the datastore.
   *
   * @return the data store
   */
  public Map<String, String> getDataStore() {
    return this.dataStore;
  }

  /**
   * Returns the value for the given key.
   *
   * @param key key of the stored parameter
   * @return the value of the stored parameter using the key or null
   */
  public String get(String key) {
    return this.dataStore.get(key);
  }

  @Override
  public Type<NaluErrorEvent.NaluErrorEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(NaluErrorEvent.NaluErrorEventHandler handler) {
    handler.onNaluError(this);
  }

  public enum ErrorType {
    NALU_INTERNAL_ERROR,
    APPLICAITON_ERROR;
  }



  public interface NaluErrorEventHandler {

    void onNaluError(NaluErrorEvent event);

  }

}
