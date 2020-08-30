package com.github.nalukit.nalu.client.component.event;

import org.gwtproject.event.shared.Event;

import java.util.HashMap;
import java.util.Map;

/**
 * Hides a BlockComponent.
 */
public class HideBlockComponentEvent
    extends Event<HideBlockComponentEvent.HideBlockComponentHandler> {
  
  public final static Type<HideBlockComponentEvent.HideBlockComponentHandler> TYPE = new Type<>();
  
  private final String              name;
  private final Map<String, String> dataStore;
  
  @SuppressWarnings("unused")
  private HideBlockComponentEvent() {
    this("");
  }
  
  private HideBlockComponentEvent(String name) {
    super();
    this.name      = name;
    this.dataStore = new HashMap<>();
  }
  
  public static HideBlockComponentEvent hide(String name) {
    return new HideBlockComponentEvent(name);
  }
  
  public HideBlockComponentEvent using(String key,
                                       String value) {
    this.dataStore.put(key,
                       value);
    return this;
  }
  
  @Override
  public Type<HideBlockComponentEvent.HideBlockComponentHandler> getAssociatedType() {
    return TYPE;
  }
  
  @Override
  protected void dispatch(HideBlockComponentEvent.HideBlockComponentHandler handler) {
    handler.onHideBlockComponent(this);
  }
  
  public String getName() {
    return name;
  }
  
  public Map<String, String> getDataStore() {
    return dataStore;
  }
  
  @FunctionalInterface
  public interface HideBlockComponentHandler {
    
    void onHideBlockComponent(HideBlockComponentEvent event);
    
  }
  
}
