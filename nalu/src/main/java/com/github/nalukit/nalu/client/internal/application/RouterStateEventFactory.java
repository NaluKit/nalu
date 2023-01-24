package com.github.nalukit.nalu.client.internal.application;

import com.github.nalukit.nalu.client.event.RouterStateEvent;
import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import org.gwtproject.event.shared.EventBus;

import java.util.Objects;

@NaluInternalUse
public class RouterStateEventFactory {

  public final static RouterStateEventFactory INSTANCE = new RouterStateEventFactory();

  private EventBus                     eventBus;
  private String                       lastEventRoute;
  private String[]                     lastEventParams;
  private RouterStateEvent.RouterState lastFiredRouterState;

  private RouterStateEventFactory() {
  }

  public void fireStartRoutingEvent(String route,
                                    String... params) {
    if (Objects.nonNull(this.lastEventRoute)) {
      if (!this.lastEventRoute.equals(route) || this.hasParamsChanged(params)) {
        this.doFireAbortRoutingEvent(this.lastEventRoute,
                                     this.lastEventParams);
        this.clear();
      }
    }
    if (Objects.isNull(this.lastFiredRouterState)) {
      this.fireRouterStateEvent(RouterStateEvent.RouterState.START_ROUTING,
                                route,
                                params);
      this.lastEventRoute  = route;
      this.lastEventParams = params;
    }
  }

  public void fireAbortRoutingEvent(String route,
                                    String... params) {
    if (Objects.nonNull(this.lastFiredRouterState) && RouterStateEvent.RouterState.START_ROUTING == this.lastFiredRouterState) {
      this.doFireAbortRoutingEvent(route,
                                   params);
      this.clear();
    }
  }

  public void fireCancelByUserRoutingEvent(String route,
                                    String... params) {
    this.fireRouterStateEvent(RouterStateEvent.RouterState.ROUTING_CANCELED_BY_USER,
                              route,
                              params);
    this.clear();
  }

  public void fireDoneRoutingEvent(String route,
                                    String... params) {
    this.fireRouterStateEvent(RouterStateEvent.RouterState.ROUTING_DONE,
                              route,
                              params);
    this.clear();
  }

  public void register(EventBus eventBus) {
    this.eventBus = eventBus;
  }

  private void doFireAbortRoutingEvent(String route,
                                      String... params) {
    this.fireRouterStateEvent(RouterStateEvent.RouterState.ROUTING_ABORTED,
                              route,
                              params);
  }

  void clear() {
    this.lastEventRoute       = null;
    this.lastEventParams      = null;
    this.lastFiredRouterState = null;
  }

  private boolean hasParamsChanged(String... params) {
    if (Objects.isNull(this.lastEventParams)) {
      return false;
    }
    if (Objects.isNull(params)) {
      return true;
    }
    if (this.lastEventParams.length != params.length) {
      return true;
    }
    for (int i = 0; i < this.lastEventParams.length; i++) {
      if (!this.lastEventParams[i].equals(params[i])) {
        return true;
      }
    }
    return false;
  }

  /**
   * Fires a router state event to inform the application about the state
   * of routing.
   *
   * @param state  routing state
   * @param route  current route
   * @param params parameter
   */
  private void fireRouterStateEvent(RouterStateEvent.RouterState state,
                                    String route,
                                    String... params) {
    this.lastFiredRouterState = state;
    this.eventBus.fireEvent(new RouterStateEvent(state,
                                                 route,
                                                 params));
  }

}
