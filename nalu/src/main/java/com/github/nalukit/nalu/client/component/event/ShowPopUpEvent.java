package com.github.nalukit.nalu.client.component.event;

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
    this.name         = name;
    this.commandStore = new HashMap<>();
    this.dataStore    = new HashMap<>();
    this.dataObjectStore = new HashMap<>();
  }
  
  public static ShowPopUpEvent show(String name) {
    return new ShowPopUpEvent(name);
  }
  
  public ShowPopUpEvent using(String key,
                              String value) {
    this.dataStore.put(key,
                       value);
    return this;
  }

  public ShowPopUpEvent usingObject(String key,
                              Object value) {
    this.dataObjectStore.put(key,
                       value);
    return this;
  }
  
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
  
  public Map<String, PopUpCommand> getCommandStore() {
    return commandStore;
  }
  
  public Map<String, String> getDataStore() {
    return dataStore;
  }

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
