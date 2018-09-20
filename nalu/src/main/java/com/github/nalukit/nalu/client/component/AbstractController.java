package com.github.nalukit.nalu.client.component;

import com.github.nalukit.nalu.client.Router;
import com.github.nalukit.nalu.client.application.IsContext;
import org.gwtproject.event.shared.SimpleEventBus;

public abstract class AbstractController<C extends IsContext> {

  protected Router router;

  protected C context;

  protected SimpleEventBus eventBus;

  public AbstractController() {
    super();
  }

  public void setRouter(Router router) {
    this.router = router;
  }

  public void setContext(C context) {
    this.context = context;
  }

  public void setEventBus(SimpleEventBus eventBus) {
    this.eventBus = eventBus;
  }
}
