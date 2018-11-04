package com.github.nalukit.nalu.processor.controller.controllerAnnotationWithParameterNotOK.ui.bad;

import com.github.nalukit.nalu.client.component.AbstractComponent;

public class BadComponent
    extends AbstractComponent<IBadComponent.Controller, String>
    implements IBadComponent {

  public BadComponent() {
  }

  @Override
  public void render() {
    initElement("badComponent");
  }
}
