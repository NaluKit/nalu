package com.github.nalukit.nalu.client.router.event;

import org.gwtproject.event.shared.Event;

public class RouterStateEvent
    extends Event<RouterStateEvent.RouterStateHandler> {

  public static Type<RouterStateEvent.RouterStateHandler> TYPE = new Type<>();

  private RouterState state;

  public RouterStateEvent(RouterState state) {
    super();
    this.state = state;
  }

  public RouterState getState() {
    return state;
  }

  @Override
  public Type<RouterStateEvent.RouterStateHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(RouterStateEvent.RouterStateHandler handler) {
    handler.onRouterState(this);
  }

  public enum RouterState {
    START_ROUTING,
    ROUTING_ABORTED,
    ROUTING_DONE;
  }



  public interface RouterStateHandler {

    void onRouterState(RouterStateEvent event);

  }

}
