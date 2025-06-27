package io.github.nalukit.nalu.processor.controller.controllerAnnotationWithParameterNotOK.ui.bad;

import io.github.nalukit.nalu.client.component.AbstractComponent;
import java.lang.Override;

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
