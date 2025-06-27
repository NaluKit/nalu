package io.github.nalukit.nalu.client.component.event;

import org.gwtproject.event.shared.Event;

import java.util.HashMap;
import java.util.Map;

public class ShowPopUpEvent
    extends Event<ShowPopUpEvent.ShowPopUpHandler> {

  public final static Type<ShowPopUpEvent.ShowPopUpHandler> TYPE = new Type<>();

  private final String                    name;
  private final Map<String, PopUpCommand> commandStore;
  private final Map<String, String>       dataStore;
  private final Map<String, Object>       dataObjectStore;

  @SuppressWarnings("unused")
  private ShowPopUpEvent() {
    this("");
  }

  private ShowPopUpEvent(String name) {
    super();
    this.name            = name;
    this.commandStore    = new HashMap<>();
    this.dataStore       = new HashMap<>();
    this.dataObjectStore = new HashMap<>();
  }

  public static ShowPopUpEvent show(String name) {
    return new ShowPopUpEvent(name);
  }

  /**
   * Adds String-data to the String-data store.
   *
   * @param key   key of the parameter
   * @param value value of the parameter
   * @return instance of the event
   */
  public ShowPopUpEvent using(String key,
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
  public ShowPopUpEvent usingObject(String key,
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
  public ShowPopUpEvent add(String key,
                            PopUpCommand command) {
    this.commandStore.put(key,
                          command);
    return this;
  }

  @Override
  public Type<ShowPopUpEvent.ShowPopUpHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ShowPopUpEvent.ShowPopUpHandler handler) {
    handler.onShowPopUp(this);
  }

  public String getName() {
    return name;
  }

  /**
   * Returns the command store
   *
   * @return the store containing key-value-pairs where value is a command
   */
  public Map<String, PopUpCommand> getCommandStore() {
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

  @FunctionalInterface
  public interface ShowPopUpHandler {

    void onShowPopUp(ShowPopUpEvent event);

  }



  @FunctionalInterface
  public interface PopUpCommand {

    void execute();

  }

}
