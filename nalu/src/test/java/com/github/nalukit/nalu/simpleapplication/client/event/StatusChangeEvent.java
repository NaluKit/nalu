package com.github.nalukit.nalu.simpleapplication.client.event;

import org.gwtproject.event.shared.Event;

public class StatusChangeEvent
    extends Event<StatusChangeEvent.StatusChangeHandler> {

  public static Type<StatusChangeEvent.StatusChangeHandler> TYPE = new Type<>();

  private String status;

  public StatusChangeEvent(String status) {
    super();

    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  @Override
  public Type<StatusChangeEvent.StatusChangeHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(StatusChangeEvent.StatusChangeHandler handler) {
    handler.onStatusChange(this);
  }

  public interface StatusChangeHandler {

    void onStatusChange(StatusChangeEvent event);

  }
}
