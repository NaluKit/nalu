package com.github.nalukit.nalu.simpleapplication.client.event;

import org.gwtproject.event.shared.Event;

public class SelectEvent
    extends Event<SelectEvent.StatusChangeHandler> {

  public static Type<SelectEvent.StatusChangeHandler> TYPE = new Type<>();

  private Select select;

  public SelectEvent(SelectEvent.Select select) {
    super();

    this.select = select;
  }

  public Select getSelect() {
    return select;
  }

  @Override
  public Type<SelectEvent.StatusChangeHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(SelectEvent.StatusChangeHandler handler) {
    handler.onSelect(this);
  }

  public enum Select {
    SEARCH,
    LIST,
    DETAIL
  }



  public interface StatusChangeHandler {

    void onSelect(SelectEvent event);

  }
}
