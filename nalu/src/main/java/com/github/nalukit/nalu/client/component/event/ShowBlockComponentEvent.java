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
  
  private final String              name;
  private final Map<String, String> dataStore;
  
  private ShowBlockComponentEvent(String name) {
    super();
    this.name      = name;
    this.dataStore = new HashMap<>();
  }
  
  public static ShowBlockComponentEvent show(String name) {
    return new ShowBlockComponentEvent(name);
  }
  
  public ShowBlockComponentEvent using(String key,
                                       String value) {
    this.dataStore.put(key,
                       value);
    return this;
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
  
  public Map<String, String> getDataStore() {
    return dataStore;
  }
  
  @FunctionalInterface
  public interface ShowBlockComponentHandler {
    
    void onShowBlockComponent(ShowBlockComponentEvent event);
    
  }
  
}
