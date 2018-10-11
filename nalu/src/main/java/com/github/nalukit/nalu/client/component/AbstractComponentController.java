package com.github.nalukit.nalu.client.component;

import com.github.nalukit.nalu.client.application.IsContext;
import com.github.nalukit.nalu.client.internal.HandlerRegistrations;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractComponentController<C extends IsContext, V extends IsComponent<?, W>, W>
    extends AbstractController<C>
    implements IsController<W>,
               IsComponent.Controller {

  protected V component;

  protected HandlerRegistrations handlerRegistrations = new HandlerRegistrations();

  private Map<String, AbstractCompositeController<?, ?, ?>> compositeComtrollers;

  public AbstractComponentController() {
    super();
    this.compositeComtrollers = new HashMap<>();
  }

  @Override
  public W asElement() {
    return this.component.asElement();
  }

  @Override
  public final void onAttach() {
    component.onAttach();
  }

  @Override
  public final void onDetach() {
    component.onDetach();
  }

  @Override
  public String mayStop() {
    return null;
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
    this.handlerRegistrations = null;
  }

  /**
   * The stop-method will be called at the start of the controller's life cycle.
   * <p>
   * If you have to do something in case controller gets active,
   * that's the right place.
   */
  @Override
  public void start() {
  }

  /**
   * The stop-method will be called at the end of the controller's life cycle.
   * <p>
   * If you have to do something in case controller gets inactive,
   * that's the right place.
   */
  @Override
  public void stop() {
  }

  public void setComponent(V component) {
    this.component = component;
  }

  public Map<String, AbstractCompositeController<?, ?, ?>> getComposites() {
    return compositeComtrollers;
  }

  @SuppressWarnings("unchecked")
  public <S extends AbstractCompositeController<?, ?, ?>> S getComposite(String name) {
    return (S) this.getComposites()
                   .get(name);
  }
}
