package com.github.nalukit.nalu.plugin.gwt.client.component;

import com.github.nalukit.nalu.client.Router;
import com.github.nalukit.nalu.client.application.IsContext;
import com.github.nalukit.nalu.client.component.IsShell;
import com.github.nalukit.nalu.client.internal.HandlerRegistrations;
import com.google.gwt.user.client.ui.Composite;
import org.gwtproject.event.shared.SimpleEventBus;

public abstract class AbstractCompositeShell<C extends IsContext>
    extends Composite
    implements IsShell {

  protected Router router;

  protected C context;

  protected SimpleEventBus eventBus;

  protected HandlerRegistrations handlerRegistrations = new HandlerRegistrations();

  public AbstractCompositeShell() {
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

  /**
   * internal framework method! Will be called by the framdework after the
   * stop-method f the controller is called
   *
   * <b>DO NOT CALL THIS METHOD! THIS WILL LEAD TO UNEXPECTED BEHAVIOR!</b>
   */
  @Override
  public void removeHandlers() {
    this.handlerRegistrations.removeHandler();
    this.handlerRegistrations = new HandlerRegistrations();
  }

  @Override
  public void onAttachedComponent() {
    // override this method if you need to do something, after a component is attached!
  }

  /**
   * Will be called in case a shell ist detachred.
   *
   * In case you have something to do if a shell is detached, override this mehtod.
   */
  @Override
  public void detachShell() {
  }
}
