package com.github.nalukit.nalu.client.component.event;

import org.gwtproject.event.shared.Event;

import java.util.HashMap;
import java.util.Map;

public class ShowPopUpEvent
    extends Event<ShowPopUpEvent.ShowPopUpHandler> {

  public final static Type<ShowPopUpEvent.ShowPopUpHandler> TYPE = new Type<>();

  private String              name;
  private Map<String, String> dataStore;

  private ShowPopUpEvent(String name) {
    super();
    this.name = name;
    this.dataStore = new HashMap<>();
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

  public Map<String, String> getDataStore() {
    return dataStore;
  }

  public interface ShowPopUpHandler {

    void onShowPopUp(ShowPopUpEvent event);

  }

}
