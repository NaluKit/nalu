package com.github.nalukit.nalu.client.internal.application;

import com.github.nalukit.nalu.client.internal.annotation.NaluInternalUse;
import org.gwtproject.event.shared.EventBus;

import java.util.Objects;

@NaluInternalUse
public class RouterStateEventFactory {

  public final static RouterStateEventFactory INSTANCE = new RouterStateEventFactory();

  private EventBus eventBus;
  private String   lastEventRoute;
  private String[] lastEventParams;

  private RouterStateEventFactory() {
  }

  public void fireStartRoutingEvent(String route,
                                    String... params) {
    if (Objects.nonNull(this.lastEventRoute)) {

    }

  }

  public void fireAbortRoutingEvent(String route,
                                    String... params) {
    if (Objects.nonNull(this.lastEventRoute)) {

    }

  }

  public void register(EventBus eventBus) {
    this.eventBus = eventBus;
  }

  public void doFireAbortRoutingEvent(String route,
                                      String... params) {

  }

  private void clear() {
    this.lastEventRoute  = null;
    this.lastEventParams = null;
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
}
