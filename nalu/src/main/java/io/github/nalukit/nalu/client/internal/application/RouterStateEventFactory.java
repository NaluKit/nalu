package io.github.nalukit.nalu.client.internal.application;

import io.github.nalukit.nalu.client.event.RouterStateEvent;
import io.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import io.github.nalukit.nalu.client.util.NaluUtils;
import org.gwtproject.event.shared.EventBus;

import java.util.ArrayList;
import java.util.List;

@NaluInternalUse
public class RouterStateEventFactory {

  public final static RouterStateEventFactory INSTANCE = new RouterStateEventFactory();

  private final List<RouteStateInfo> routeStateInfoList;
  private       EventBus             eventBus;

  private RouterStateEventFactory() {
    this.routeStateInfoList = new ArrayList<>();
  }

  public void clear() {
    this.routeStateInfoList.clear();
  }

  public void fireStartRoutingEvent(String route,
                                    String... params) {
    for (RouteStateInfo info : this.routeStateInfoList) {
      if (!info.isAborted()) {
        this.doFireAbortRoutingEvent(info.route,
                                     info.params);
        info.setAborted(true);
      }
    }
    this.fireRouterStateEvent(RouterStateEvent.RouterState.START_ROUTING,
                              route,
                              params);
    this.routeStateInfoList.add(new RouteStateInfo(route,
                                                   params));
  }

  public void fireAbortRoutingEvent(String route,
                                    String... params) {
    RouteStateInfo info = this.getRouteStateInfo(route,
                                                 params);
    if (info == null) {
      return;
    }
    if (!info.isAborted()) {
      this.doFireAbortRoutingEvent(route,
                                   params);
      info.setAborted(true);
    }
  }

  public void fireCancelByUserRoutingEvent(String route,
                                           String... params) {
    RouteStateInfo info = this.getRouteStateInfo(route,
                                                 params);
    if (info == null) {
      return;
    }
    if (!info.isAborted()) {
      this.fireRouterStateEvent(RouterStateEvent.RouterState.ROUTING_CANCELED_BY_USER,
                                route,
                                params);
      info.setAborted(true);
    }
  }

  public void fireDoneRoutingEvent(String route,
                                   String... params) {
    RouteStateInfo info = this.getRouteStateInfo(route,
                                                 params);
    if (info == null) {
      return;
    }
    if (!info.isAborted()) {
      this.fireRouterStateEvent(RouterStateEvent.RouterState.ROUTING_DONE,
                                route,
                                params);
    }
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
    this.eventBus.fireEvent(new RouterStateEvent(state,
                                                 route,
                                                 params));
  }

  private RouteStateInfo getRouteStateInfo(String route,
                                           String[] params) {
    for (RouteStateInfo info : this.routeStateInfoList) {
      if (NaluUtils.INSTANCE.compareRoutes(info.getRoute(),
                                           route)) {
        if (this.checkParams(info.getParams(),
                             params)) {
          return info;
        }
      }
    }
    return null;
  }

  private boolean checkParams(String[] paramsFromInfo,
                              String[] params) {
    if (paramsFromInfo == null && params == null) {
      return true;
    }
    if (paramsFromInfo == null) {
      return false;
    }
    if (params == null) {
      return false;
    }
    if (paramsFromInfo.length != params.length) {
      return false;
    }
    for (int i = 0; i < paramsFromInfo.length; i++) {
      if (!paramsFromInfo[i].equals(params[i])) {
        return false;
      }
    }
    return true;
  }

  static class RouteStateInfo {

    private final String   route;
    private final String[] params;
    private       boolean  aborted;

    public RouteStateInfo(String route,
                          String[] params) {
      this.route  = route;
      this.params = params;
    }

    public String getRoute() {
      return route;
    }

    public String[] getParams() {
      return params;
    }

    public boolean isAborted() {
      return aborted;
    }

    public void setAborted(boolean aborted) {
      this.aborted = aborted;
    }
  }

}
