package com.github.nalukit.nalu.client.component.event;

import org.gwtproject.event.shared.Event;

import java.util.HashMap;
import java.util.Map;

/**
 * Show a BlockComponent.
 */
public class ShowBlockComponentEvent
    extends Event<ShowBlockComponentEvent.ShowBlockComponentHandler> {

  public final static Type<ShowBlockComponentEvent.ShowBlockComponentHandler> TYPE = new Type<>();

  private final Map<String, ShowBlockComponentEvent.ShowBlockComponentCommand> commandStore;
  private final Map<String, String>                                            dataStore;
  private final Map<String, Object>                                            dataObjectStore;
  private final String                                                         name;

  @SuppressWarnings("unused")
  private ShowBlockComponentEvent() {
    this("");
  }

  private ShowBlockComponentEvent(String name) {
    super();
    this.name            = name;
    this.commandStore    = new HashMap<>();
    this.dataStore       = new HashMap<>();
    this.dataObjectStore = new HashMap<>();
  }

  public static ShowBlockComponentEvent show(String name) {
    return new ShowBlockComponentEvent(name);
  }

  @Override
  public Type<ShowBlockComponentEvent.ShowBlockComponentHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ShowBlockComponentEvent.ShowBlockComponentHandler handler) {
    handler.onShowBlockComponent(this);
  }

  public String getName() {
    return name;
  }

  /**
   * Returns the command store
   *
   * @return the store containing key-value-pairs where value is a command
   */
  public Map<String, ShowBlockComponentEvent.ShowBlockComponentCommand> getCommandStore() {
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

  /**
   * Adds String-data to the String-data store.
   *
   * @param key   key of the parameter
   * @param value value of the parameter
   * @return instance of the event
   */
  public ShowBlockComponentEvent using(String key,
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
   * @param key   key of the parameter
   * @param value value of the parameter
   * @return instance of the event
   */
  public ShowBlockComponentEvent usingObject(String key,
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
  public ShowBlockComponentEvent add(String key,
                                     ShowBlockComponentEvent.ShowBlockComponentCommand command) {
    this.commandStore.put(key,
                          command);
    return this;
  }

  @FunctionalInterface
  public interface ShowBlockComponentHandler {

    void onShowBlockComponent(ShowBlockComponentEvent event);

  }



  @FunctionalInterface
  public interface ShowBlockComponentCommand {

    void execute();

  }

}
