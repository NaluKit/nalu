package com.github.mvp4g.nalu.client.component;

import com.github.mvp4g.nalu.client.Router;
import com.github.mvp4g.nalu.client.application.IsContext;
import org.gwtproject.event.shared.SimpleEventBus;

public abstract class AbstractShell<C extends IsContext>
  implements IsShell {

  protected Router router;

  protected C context;

  protected SimpleEventBus eventBus;

  public AbstractShell() {
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

  public void bind() {
    // override this method if you need to bind something inside the Shell
  }

  @Override
  public void onAttachedChild() {
    // override this method if you need to do something, after a child is attached!
  }
}
