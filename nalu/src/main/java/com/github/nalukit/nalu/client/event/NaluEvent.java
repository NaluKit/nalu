package com.github.nalukit.nalu.client.event;

import org.gwtproject.event.shared.Event;

import java.util.HashMap;
import java.util.Map;

/**
 * This event can be used to communicate between client side modules
 * in a client side multi module implementation. Using this event will
 * avoid using a common module to share applicaiton wide events.
 * <p>
 * Use the message type to handle different event types.
 * <p>
 * In case parameter vales need to be send with the event, use the store.
 * <p>
 * The store will save the parameters as objects. It is the
 * responsibility of the developer to cast the parameters to the
 * correct class when reading teh parameters.
 */
public class NaluEvent
    extends Event<NaluEvent.NaluMessageHandler> {

  public static Type<NaluEvent.NaluMessageHandler> TYPE = new Type<>();

  private String              event;
  private Map<String, Object> store;

  private NaluEvent() {
    super();
    this.store = new HashMap<>();
  }

  /**
   * Creates a new NaluMessageEvent.
   *
   * @return new Message event
   */
  public NaluEvent create() {
    return new NaluEvent();
  }

  /**
   * Sets the message type
   *
   * @param event message type of the event
   * @return instance of the event
   */
  public NaluEvent event(String event) {
    this.event = event;
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
  public NaluEvent data(String key,
                        Object value) {
    this.store.put(key,
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
    return this.store.get(key);
  }

  @Override
  public Type<NaluEvent.NaluMessageHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(NaluEvent.NaluMessageHandler handler) {
    handler.onNaluMessage(this);
  }

  public interface NaluMessageHandler {

    void onNaluMessage(NaluEvent event);

  }

}
