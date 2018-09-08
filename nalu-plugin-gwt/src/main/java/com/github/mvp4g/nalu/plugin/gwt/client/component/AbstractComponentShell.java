package com.github.mvp4g.nalu.plugin.gwt.client.component;

import com.github.mvp4g.nalu.client.Router;
import com.github.mvp4g.nalu.client.application.IsContext;
import com.github.mvp4g.nalu.client.component.IsShell;
import com.google.gwt.user.client.ui.Composite;
import org.gwtproject.event.shared.SimpleEventBus;

public abstract class AbstractComponentShell<C extends IsContext>
  extends Composite
  implements IsShell {

  protected Router router;

  protected C context;

  protected SimpleEventBus eventBus;

  public AbstractComponentShell() {
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
}
