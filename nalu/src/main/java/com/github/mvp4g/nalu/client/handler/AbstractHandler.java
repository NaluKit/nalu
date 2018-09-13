package com.github.mvp4g.nalu.client.handler;

import com.github.mvp4g.nalu.client.Router;
import com.github.mvp4g.nalu.client.application.IsContext;
import org.gwtproject.event.shared.SimpleEventBus;

public abstract class AbstractHandler<C extends IsContext>
  implements IsHandler {

  protected C context;

  protected SimpleEventBus eventBus;

  protected Router router;

  public AbstractHandler() {
    super();
  }

  public void setContext(C context) {
    this.context = context;
  }

  public void setEventBus(SimpleEventBus eventBus) {
    this.eventBus = eventBus;
  }

  public void setRouter(Router router) {
    this.router = router;
  }
}
