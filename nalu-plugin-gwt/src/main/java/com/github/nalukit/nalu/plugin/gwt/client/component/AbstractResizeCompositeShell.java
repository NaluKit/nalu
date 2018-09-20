package com.github.nalukit.nalu.plugin.gwt.client.component;

import com.github.nalukit.nalu.client.Router;
import com.github.nalukit.nalu.client.application.IsContext;
import com.github.nalukit.nalu.client.component.IsShell;
import com.google.gwt.user.client.ui.ResizeComposite;
import org.gwtproject.event.shared.SimpleEventBus;

public abstract class AbstractResizeCompositeShell<C extends IsContext>
    extends ResizeComposite
    implements IsShell {

  protected Router router;

  protected C context;

  protected SimpleEventBus eventBus;

  public AbstractResizeCompositeShell() {
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
  public void onAttachedComponent() {
    // override this method if you need to do something, after a component is attached!
  }
}
