package com.github.nalukit.nalu.client.event;

import org.gwtproject.event.shared.Event;

public class RouterStateEvent
    extends Event<RouterStateEvent.RouterStateHandler> {

  public static final Type<RouterStateEvent.RouterStateHandler> TYPE = new Type<>();

  private RouterState state;
  private String      route;
  private String[]    params;

  public RouterStateEvent(RouterState state,
                          String route,
                          String[] params) {
    super();
    this.state = state;
    this.route = route;
    this.params = params;
  }

  public RouterState getState() {
    return state;
  }

  public String getRoute() {
    return route;
  }

  public String[] getParams() {
    return params;
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
