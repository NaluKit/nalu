package com.github.mvp4g.nalu.processor.controller.controllerAnnotationWithParameterNotOK.ui.bad;

import com.github.mvp4g.nalu.client.component.AbstractComponent;

public class BadComponent
  extends AbstractComponent<IBadComponent.Controller, String>
  implements IBadComponent {

  public BadComponent() {
  }

  @Override
  public String render() {
    return "BadComponent";
  }
}
