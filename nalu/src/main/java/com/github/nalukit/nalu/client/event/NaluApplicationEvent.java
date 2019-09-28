package com.github.nalukit.nalu.client.event;

import org.gwtproject.event.shared.Event;

import java.util.HashMap;
import java.util.Map;

/**
 * This event can be used to communicate between client side modules
 * in a client side multi module implementation. Using this event will
 * avoid using a common module to share application wide events.
 * <p>
 * Use the message type to handle different event types.
 * <p>
 * In case parameter values need to be send with the event, use the store.
 * <p>
 * The store will save the parameters as objects. It is the
 * responsibility of the developer to cast the parameters to the
 * correct class when reading teh parameters.
 */
public class NaluApplicationEvent
    extends Event<NaluApplicationEvent.NaluApplicationEventHandler> {

  public final static Type<NaluApplicationEvent.NaluApplicationEventHandler> TYPE = new Type<>();

  private String              event;
  private Map<String, Object> dataStore;

  private NaluApplicationEvent() {
    super();
    this.dataStore = new HashMap<>();
  }

  /**
   * Creates a new NaluMessageEvent.
   *
   * @return new Message event
   */
  public static NaluApplicationEvent create() {
    return new NaluApplicationEvent();
  }

  /**
   * Sets the message type
   *
   * @param event message type of the event
   * @return instance of the event
   */
  public NaluApplicationEvent event(String event) {
    this.event = event;
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
  public NaluApplicationEvent data(String key,
                                   Object value) {
    this.dataStore.put(key,
                       value);
    return this;
  }

  /**
   * Returns the type of the message.
   *
   * @return the message type of the event
   */
  public String getEvent() {
    return event;
  }

  /**
   * Returns the value for the given key.
   *
   * <b>Keep in mind to cast the return value.</b>
   *
   * @param key key of the stored parameter
   * @return the value of the stored parameter using the key or null
   */
  public Object get(String key) {
    return this.dataStore.get(key);
  }

  @Override
  public Type<NaluApplicationEvent.NaluApplicationEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(NaluApplicationEvent.NaluApplicationEventHandler handler) {
    handler.onNaluApplicationEvent(this);
  }

  public interface NaluApplicationEventHandler {

    void onNaluApplicationEvent(NaluApplicationEvent event);

  }

}
