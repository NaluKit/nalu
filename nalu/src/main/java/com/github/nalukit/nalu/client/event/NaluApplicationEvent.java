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
 * correct class when reading the parameters.
 */
public class NaluApplicationEvent
    extends Event<NaluApplicationEvent.NaluApplicationEventHandler> {

  public final static Type<NaluApplicationEvent.NaluApplicationEventHandler> TYPE = new Type<>();

  private final Map<String, NaluApplicationEvent.ApplicationCommand> commandStore;
  private final Map<String, String>                                  dataStore;
  private final Map<String, Object>                                  dataObjectStore;
  private       String                                               event;

  private NaluApplicationEvent() {
    super();
    this.commandStore    = new HashMap<>();
    this.dataStore       = new HashMap<>();
    this.dataObjectStore = new HashMap<>();
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
   * Adds String-data to the String-data store.
   *
   * @param key   key of the parameter
   * @param value value of the parameter
   * @return instance of the event
   */
  public NaluApplicationEvent using(String key,
                                    String value) {
    this.dataStore.put(key,
                       value);
    return this;
  }

  /**
   * Adds data to the data store.
   *
   * <b>Keep in mind, all parameters will be stored as objects!</b>
   *
   * <p>Deprecated (will be removed in a future version of Nalu):
   * please use the <b>usingObject</b>-method</p>
   *
   * @param key   key of the parameter
   * @param value value of the parameter
   * @return instance of the event
   */
  @Deprecated
  public NaluApplicationEvent data(String key,
                                   Object value) {
    this.usingObject(key,
                     value);
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
  public NaluApplicationEvent usingObject(String key,
                                          Object value) {
    this.dataObjectStore.put(key,
                             value);
    return this;
  }

  /**
   * Adds a command to the command store.
   *
   * @param key     key of the parameter
   * @param command command to store
   * @return instance of the event
   */
  public NaluApplicationEvent add(String key,
                                  NaluApplicationEvent.ApplicationCommand command) {
    this.commandStore.put(key,
                          command);
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
   * <p>Deprecated (will be removed in a future version of Nalu):
   * please use <b>event.getDataObjectStore().get(key);</b> instead</p>
   *
   * @param key key of the stored parameter
   * @return the value of the stored parameter using the key or null
   */
  @Deprecated
  public Object get(String key) {
    return this.getDataObjectStore()
               .get(key);
  }

  /**
   * Returns the command store
   *
   * @return the store containing key-value-pairs where value is a command
   */
  public Map<String, NaluApplicationEvent.ApplicationCommand> getCommandStore() {
    return commandStore;
  }

  /**
   * Returns the data store where all String are stored.
   *
   * @return the store containing key-value-pairs where value is a String
   */
  public Map<String, String> getDataStore() {
    return dataStore;
  }

  /**
   * Returns the data object store where all object are stored.
   *
   * @return the store containing key-value-pairs where value is an Object that needs to be cast
   */
  public Map<String, Object> getDataObjectStore() {
    return dataObjectStore;
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



  @FunctionalInterface
  public interface ApplicationCommand {

    void execute();

  }

}
